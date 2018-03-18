package com.ys53994.sse.client.service.impl;

import com.ys53994.sse.client.service.DocumentServiceClient;
import com.ys53994.sse.common.model.DocumentDto;
import com.ys53994.sse.common.rest.RestApiDescriptor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
@Slf4j
public class DocumentServiceClientImpl implements DocumentServiceClient {

    private final RestTemplate restTemplate;

    private final String sseServerBaseUrl;

    @Autowired
    public DocumentServiceClientImpl(RestTemplate restTemplate, @Value("${sse.server.base.url}") String sseBaseEndpoint) {
        this.restTemplate = restTemplate;
        this.sseServerBaseUrl = sseBaseEndpoint;
    }


    @Override
    public Optional<DocumentDto> get(@NonNull String key) {

        try {
            final URI uri = new URI(sseServerBaseUrl + RestApiDescriptor.DOCUMENT_PUBLIC_REST_API + "/" + key);
            final ResponseEntity<DocumentDto> responseEntity = withExceptionHandling(() -> restTemplate.getForEntity(uri, DocumentDto.class));

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return Optional.of(responseEntity.getBody());
            }
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return empty();

    }

    @Override
    public void put(DocumentDto documentDto) {
        try {
            final URI uri = new URI(sseServerBaseUrl + RestApiDescriptor.DOCUMENT_PUBLIC_REST_API);
            final ResponseEntity<Void> responseEntity = restTemplate.postForEntity(uri, documentDto, Void.class);
            log.debug("{} ", responseEntity);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public Collection<String> search(Collection<String> tokens) {
        final UriComponentsBuilder builder = fromHttpUrl(sseServerBaseUrl + RestApiDescriptor.DOCUMENT_PUBLIC_REST_API);
        tokens.forEach(token -> builder.queryParam("token", token));
        final RequestEntity requestEntity = new RequestEntity(HttpMethod.GET, builder.build().toUri());
        final ResponseEntity<Collection<String>> responseEntity = restTemplate.exchange(requestEntity,
                new ParameterizedTypeReference<Collection<String>>() {
                });

        return responseEntity.getBody();

    }

    private <T> ResponseEntity<T> withExceptionHandling(Supplier<ResponseEntity<T>> action) {
        try {
            return action.get();
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }

}
