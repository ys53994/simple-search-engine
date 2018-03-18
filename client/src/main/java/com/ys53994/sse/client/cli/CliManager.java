package com.ys53994.sse.client.cli;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CliManager {

    public static final String USAGE_CMD_LINE = "java -jar client.jar <commands>";

    private static final String EXAMPLE_USAGE = "\nExample java -jar client.jar -p \"key1 token1 token2 token3\"";

    private static final String HEADER = "Supported commands are listed below";

    private final CliCommandExecutor commandExecutor;

    private final Options options;

    @Autowired
    public CliManager(CliCommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        options = new Options()
                .addOption("h", false, "display help information")
                .addOption("p", true, "put document to storage. Example -p \"key1 document body\"")
                .addOption("g", true, "get document by key. Example -g key1")
                .addOption("s", true, "search document keys by tokens(words). Example -s \"document body\"");
    }

    public void handle(String[] args) {
        CommandLineParser parser = new PosixParser();
        try {
            final CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("h") || commandLine.getOptions().length == 0) {
                showUsage();
            }
            commandExecutor.execute(commandLine);
        } catch (ParseException e) {
            //happened in case when invalid option entered. can show only message
            log.error(e.getMessage());
            showUsage();
        }
    }


    private void showUsage() {
        new HelpFormatter().printHelp(300, USAGE_CMD_LINE, HEADER,
                options, EXAMPLE_USAGE);
    }

}
