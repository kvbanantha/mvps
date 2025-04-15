package com.lloyds.goalsobjectives.repository;

import java.util.Map;

public interface ApiRepository<T, ID> {
    T get(ID id, String endpoint, Map<String, String> headers);
    T post(T entity, String endpoint, Map<String, String> headers);
    T put(ID id, T entity, String endpoint, Map<String, String> headers);
    void delete(ID id, String endpoint, Map<String, String> headers);
}