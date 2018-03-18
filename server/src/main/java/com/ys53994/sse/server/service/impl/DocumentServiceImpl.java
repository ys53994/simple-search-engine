package com.ys53994.sse.server.service.impl;

import com.ys53994.sse.server.domain.Document;
import com.ys53994.sse.server.repository.DocumentRepository;
import com.ys53994.sse.server.repository.Query;
import com.ys53994.sse.server.service.DocumentService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentStorage;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentStorage) {
        this.documentStorage = documentStorage;
    }

    @Override
    public Optional<Document> find(@NonNull Document.DocumentKey key) {
        return documentStorage.find(key);
    }

    @Override
    public Collection<Document.DocumentKey> search(@NonNull Query query) {
        return documentStorage.search(query);
    }

    @Override
    public Document save(@NonNull Document document) {
        return documentStorage.save(document);
    }
}
