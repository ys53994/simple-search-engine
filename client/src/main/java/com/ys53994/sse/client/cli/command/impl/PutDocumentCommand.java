package com.ys53994.sse.client.cli.command.impl;

import com.ys53994.sse.client.cli.command.Command;
import com.ys53994.sse.client.service.DocumentServiceClient;
import com.ys53994.sse.common.model.DocumentDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ys53994.sse.client.cli.util.CliUtil.println;

@Component
@AllArgsConstructor
public class PutDocumentCommand implements Command {

    private final DocumentServiceClient searchEngineClient;

    @Override
    public void execute(String rawArguments) {
        final String[] parts = rawArguments.split("\\s+", 2);
        if(parts.length != 2){
            println("Invalid input parameter "+rawArguments);
            return;
        }
        searchEngineClient.put(new DocumentDto(parts[0], parts[1]));
        println("Document has been added successfully.");

    }

    @Override
    public String getCommandName() {
        return "p";
    }
}
