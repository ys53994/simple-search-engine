package com.ys53994.sse.client.service;

import com.ys53994.sse.common.model.DocumentDto;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.Optional;

public interface DocumentServiceClient {

     Optional<DocumentDto> get(String key);

     void put(DocumentDto documentDto);

     Collection<String> search(Collection<String> tokens);

}
