package com.ys53994.sse.server.converter;

import com.ys53994.sse.common.model.DocumentDto;
import com.ys53994.sse.server.domain.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentDtoConverter implements Converter<Document, DocumentDto> {

    @Override
    public DocumentDto convert(Document document) {
        return new DocumentDto(document.getKey().getKey(), document.getData());
    }
}
