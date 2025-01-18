package com.tws.lab.command;

import java.util.Scanner;

public interface CliCommand {
    void execute(Scanner scanner);
    String getName();
    String getDescription();
}
