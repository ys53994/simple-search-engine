package com.ys53994.sse.server.storage;

import java.util.Collection;

public interface DataStorage<K, I, V> {

    Collection<V> getByIndex(I key);

    V getByKey(K key);

    V put(K key, V value, boolean overrideIfExists);
}
