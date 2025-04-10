package com.lloyds.lloydsiam;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.lloyds.lloydsiam.exception.UnauthorizedException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class AuthAspect {

    @Around("@annotation(com.lloyds.lloydsiam.Permission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Permission permissionAnnotation = method.getAnnotation(Permission.class);
        String[] requiredRoles = permissionAnnotation.role();

        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated.");
        }

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> userRoles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            if (hasAllRequiredRoles(userRoles, requiredRoles)) {
                return joinPoint.proceed();
            } else {
                throw new UnauthorizedException("User does not have required roles: " + Arrays.toString(requiredRoles));
            }
        }
        return null;
    }
    
    private boolean hasAllRequiredRoles(List<String> userRoles, String[] requiredRoles) {
        return Arrays.stream(requiredRoles).allMatch(userRoles::contains);
    }
}