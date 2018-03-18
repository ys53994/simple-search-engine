package com.ys53994.sse.server.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "of")
@EqualsAndHashCode(of = "key")
public class Document {

    @Data(staticConstructor = "of")
    public static final class DocumentKey {

        private final String key;
    }


    private final DocumentKey key;

    private final String data;

}
