package com.ys53994.sse.server.util;

import com.ys53994.sse.server.domain.Document;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.regex.Pattern.compile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DocumentUtil {

    private final static Pattern SPLIT_PATTERN = compile("\\s+");

    public static Stream<String> getDocumentDataAsStream(Document document){
        return SPLIT_PATTERN.splitAsStream(document.getData());
    }
}
