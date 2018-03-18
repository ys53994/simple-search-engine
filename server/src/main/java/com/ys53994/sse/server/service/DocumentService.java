package com.ys53994.sse.server.service;

import com.ys53994.sse.server.domain.Document;
import com.ys53994.sse.server.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface DocumentService {

    Optional<Document> find(Document.DocumentKey key);

    Collection<Document.DocumentKey> search(Query query);

    Document save(Document document);
}
