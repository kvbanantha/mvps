package com.lloyds.goalsobjectives.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lloyds.goalsobjectives.domain.Objective;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JiraAlignRepository {

    @Value("${spring.jira.external.endpoint}")
    String endPoint;

    private ApiRepository<String, Long> restfulApiRepository;

    public JiraAlignRepository(RestfulApiRepository restfulApiRepository) {
        this.restfulApiRepository = restfulApiRepository;
    }

    public List<?> getStoriesByObjective(Objective objective) throws JsonProcessingException {
        String jsonStoryList = restfulApiRepository.get(objective.getId(), endPoint, Map.of());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonStoryList, List.class);
    }
}
