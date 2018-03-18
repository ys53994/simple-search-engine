package com.ys53994.sse.server.repository.impl;

import com.ys53994.sse.server.domain.Document;
import com.ys53994.sse.server.repository.DocumentRepository;
import com.ys53994.sse.server.repository.Query;
import com.ys53994.sse.server.storage.DataStorage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {


    private final DataStorage<Document.DocumentKey, String, Document> storageStrategy;

    @Autowired
    public DocumentRepositoryImpl(DataStorage<Document.DocumentKey, String, Document> documentStorage) {
        this.storageStrategy = documentStorage;
    }

    @Override
    public Document save(@NonNull Document document) {
        storageStrategy.put(document.getKey(), document, false);
        return document;
    }

    @Override
    public Optional<Document> find(@NonNull Document.DocumentKey key) {
        return ofNullable(storageStrategy.getByKey(key));
    }

    @Override
    public Collection<Document.DocumentKey> search(@NonNull Query query) {
        return query
                .getTokens()
                .stream()
                .map(storageStrategy::getByIndex)
                .flatMap(Collection::stream)
                .map(Document::getKey)
                .collect(toSet());
    }
}
