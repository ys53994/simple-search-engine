package com.ys53994.sse.client.cli.command;

public interface Command {

    void execute(String rawArguments);

    String getCommandName();
}
