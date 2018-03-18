package com.ys53994.sse.server.storage.impl;

import com.google.common.collect.Multimap;
import com.ys53994.sse.server.domain.Document;
import com.ys53994.sse.server.storage.DataStorage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Multimaps.newSetMultimap;
import static com.ys53994.sse.server.util.DocumentUtil.getDocumentDataAsStream;

@Component
public class SimpleInMemoryDocumentStorage implements DataStorage<Document.DocumentKey, String, Document> {

    private final Map<Document.DocumentKey, Document> keyValueStorage = new ConcurrentHashMap<>();

    private final Multimap<String, Document> indexStorage = newSetMultimap(new ConcurrentHashMap<>(), ConcurrentHashMap::newKeySet);

    @Override
    public Collection<Document> getByIndex(String key) {
        return indexStorage.get(key);
    }

    @Override
    public Document getByKey(Document.DocumentKey key) {
        return keyValueStorage.get(key);
    }

    @Override
    public Document put(Document.DocumentKey documentKey, Document document, boolean overrideIfExists) {
        return keyValueStorage.compute(document.getKey(), (key, value) -> {
            if (value == null || (value != null && overrideIfExists)) {
                getDocumentDataAsStream(document).forEach(token -> indexStorage.put(token, document));
                return document;
            }
            return value;
        });
    }
}
