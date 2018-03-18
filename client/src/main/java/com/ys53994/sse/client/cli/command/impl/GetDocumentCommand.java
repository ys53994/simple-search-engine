package com.ys53994.sse.client.cli.command.impl;

import com.ys53994.sse.client.cli.command.Command;
import com.ys53994.sse.client.service.DocumentServiceClient;
import com.ys53994.sse.common.model.DocumentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.google.common.base.Strings.nullToEmpty;
import static com.ys53994.sse.client.cli.util.CliUtil.println;
import static java.lang.String.format;

@Component
@AllArgsConstructor
public class GetDocumentCommand implements Command {

    private final DocumentServiceClient searchEngineClient;

    @Override
    public void execute(String rawArguments) {
        final Optional<DocumentDto> documentDto = searchEngineClient.get(nullToEmpty(rawArguments).trim());

        if (documentDto.isPresent()) {
            println(format("Found document: %s %s", documentDto.get().getKey(), documentDto.get().getData()));
        } else {
            println("Document not found");
        }

    }

    @Override
    public String getCommandName() {
        return "g";
    }
}
