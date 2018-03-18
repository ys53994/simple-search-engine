package com.ys53994.sse.server.repository;

import com.ys53994.sse.server.domain.Document;

import java.util.Collection;
import java.util.Optional;

public interface DocumentRepository {

    Document save(Document document);

    Optional<Document> find(Document.DocumentKey key);

    Collection<Document.DocumentKey> search(Query query);

}
