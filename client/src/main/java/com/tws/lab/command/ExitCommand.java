package com.tws.lab.command;

import java.util.Scanner;

public class ExitCommand implements CliCommand {

    @Override
    public void execute(Scanner scanner) {
        System.out.println(getDescription());
        System.exit(0);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Выход из приложения";
    }
}
