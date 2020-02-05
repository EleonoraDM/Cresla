package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputReaderImpl implements InputReader {
    private BufferedReader reader;
    private CommandProcessor processor;

    public InputReaderImpl() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.processor = new CommandProcessor();
    }

    @Override
    public String readLine() {
        String command = "";
        List<String> args = new ArrayList<>();

        try {
            String line = this.reader.readLine();
            String[] tokens = line.split("\\s+");
            command = tokens[0];
            args = Arrays.stream(tokens).skip(1).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.processor.execute(command, args);
    }
}
