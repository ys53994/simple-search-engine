package com.ys53994.sse.server.converter;

import com.ys53994.sse.common.model.DocumentDto;
import com.ys53994.sse.server.domain.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentConverter implements Converter<DocumentDto, Document> {

    @Override
    public Document convert(DocumentDto documentDto) {
        return Document.of(Document.DocumentKey.of(documentDto.getKey()), documentDto.getData());
    }
}
