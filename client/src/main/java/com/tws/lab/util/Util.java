package com.tws.lab.util;

import com.tws.lab.command.Command;
import com.tws.lab.command.FilterCarCommand;
import com.tws.lab.rest.CarRestClient;

import java.util.HashMap;
import java.util.Map;

public class Util {
    public static Map<String, Command> produceRestCommands(CarRestClient carRestClient) {
        Map<String, Command> commands = new HashMap<>();
        Command filterCommand = new FilterCarCommand(carRestClient);
        commands.put(filterCommand.getName(), filterCommand);
        return commands;
    }
} 