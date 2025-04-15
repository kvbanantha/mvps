package com.lloyds.goalsobjectives.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RestfulApiRepository<T, ID> implements ApiRepository<T, ID> {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private Class<T> type;

    public RestfulApiRepository(Class<T> type) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.type = type;
    }

    @Override
    public T get(ID id, String endpoint, Map<String, String> headers) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + "/" + id))
                    .GET();

            headers.forEach(requestBuilder::header);

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), type);
        } catch (Exception e) {
            throw new RuntimeException("GET request failed", e);
        }
    }

    @Override
    public T post(T entity, String endpoint, Map<String, String> headers) {
        try {
            String requestBody = objectMapper.writeValueAsString(entity);

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody));

            headers.forEach(requestBuilder::header);

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), type);
        } catch (Exception e) {
            throw new RuntimeException("POST request failed", e);
        }
    }

    @Override
    public T put(ID id, T entity, String endpoint, Map<String, String> headers) {
        try {
            String requestBody = objectMapper.writeValueAsString(entity);

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + "/" + id))
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody));

            headers.forEach(requestBuilder::header);

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), type);
        } catch (Exception e) {
            throw new RuntimeException("PUT request failed", e);
        }
    }

    @Override
    public void delete(ID id, String endpoint, Map<String, String> headers) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + "/" + id))
                    .DELETE();

            headers.forEach(requestBuilder::header);

            HttpRequest request = requestBuilder.build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("DELETE request failed", e);
        }
    }
}
