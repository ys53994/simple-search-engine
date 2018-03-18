package com.ys53994.sse.client.cli.command.impl;

import com.ys53994.sse.client.cli.command.Command;
import com.ys53994.sse.client.service.DocumentServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.ys53994.sse.client.cli.util.CliUtil.println;
import static java.util.Arrays.asList;

@Component
@AllArgsConstructor
public class SearchKeysCommand implements Command {

    private final DocumentServiceClient searchEngineClient;

    @Override
    public void execute(String rawArguments) {
        final Collection<String> keys = searchEngineClient.search(asList(rawArguments.split("\\s+")));

        if (!keys.isEmpty()) {
            println("Found keys: " + keys.stream().collect(Collectors.joining(",")));
        } else {
            println("No any keys found");
        }
    }

    @Override
    public String getCommandName() {
        return "s";
    }
}
