package io;

import common.Commands;
import core.Manager;
import core.ManagerImpl;

import java.util.List;

public class CommandProcessor {
    private Manager manager;

    public CommandProcessor() {
        this.manager = new ManagerImpl();
    }

    public String execute(String command, List<String> args) {
        String result = null;

        if (command.toUpperCase().equals(Commands.REACTOR.name())) {
            result = this.manager.reactorCommand(args);

        } else if (command.toUpperCase().equals(Commands.MODULE.name())) {
            result = this.manager.moduleCommand(args);

        } else if (command.toUpperCase().equals(Commands.REPORT.name())) {
            result = this.manager.reportCommand(args);

        } else if (command.toUpperCase().equals(Commands.EXIT.name())) {
            result = this.manager.exitCommand(args);
        }else {
            throw new IllegalArgumentException("Invalid command");
        }
        return result;
    }
}
