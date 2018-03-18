package com.ys53994.sse.client.cli;


import com.ys53994.sse.client.cli.command.Command;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Arrays.stream;

@Component
public class CliCommandExecutor {

    private final Collection<Command> commands;

    @Autowired
    public CliCommandExecutor(Collection<Command> commands) {
        this.commands = commands;
    }

    public void execute(CommandLine commandLine) {
        stream(commandLine.getOptions()).forEach(option -> commands.stream()
                .filter(command -> command.getCommandName().equals(option.getOpt()))
                .findFirst()
                .ifPresent(command -> command.execute(option.getValue())));
    }
}
