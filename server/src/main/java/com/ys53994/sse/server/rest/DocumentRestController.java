package com.ys53994.sse.server.rest;

import com.ys53994.sse.common.model.DocumentDto;
import com.ys53994.sse.common.rest.RestApiDescriptor;
import com.ys53994.sse.server.domain.Document;
import com.ys53994.sse.server.repository.Query;
import com.ys53994.sse.server.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ys53994.sse.common.rest.RestApiDescriptor.DOCUMENT_PUBLIC_REST_API;
import static com.ys53994.sse.common.rest.RestApiDescriptor.TOKEN_REQUEST_PARAM;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(DOCUMENT_PUBLIC_REST_API)
@Slf4j
public class DocumentRestController {

    private final ConversionService conversionService;

    private final DocumentService documentService;

    @Autowired
    public DocumentRestController(ConversionService conversionService, DocumentService documentService) {
        this.conversionService = conversionService;
        this.documentService = documentService;
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<DocumentDto> getByKey(@PathVariable String key) {
        final Optional<Document> document = documentService.find(Document.DocumentKey.of(key));

        if (document.isPresent()) {
            return ok(conversionService.convert(document.get(), DocumentDto.class));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Collection<String>> searchByTokens(@RequestParam(TOKEN_REQUEST_PARAM) List<String> tokens) {
        final Collection<String> result = documentService.search(Query.of(tokens))
                .stream()
                .map(Document.DocumentKey::getKey)
                .collect(Collectors.toSet());

        if (!result.isEmpty()) {
            return ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Document> saveDocument(@RequestBody DocumentDto documentDto) throws URISyntaxException {
        final Document document = documentService.save(conversionService.convert(documentDto, Document.class));

        return ok(document);

    }


}
