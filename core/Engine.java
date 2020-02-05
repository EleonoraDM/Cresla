package core;

import common.Commands;
import io.InputReader;
import io.InputReaderImpl;
import io.OutputWriter;
import io.OutputWriterImpl;

public class Engine {
    private InputReader reader;
    private OutputWriter writer;

    public Engine() {
        this.reader = new InputReaderImpl();
        this.writer = new OutputWriterImpl();
    }

    public void run() {

        while (true) {

            String result = this.reader.readLine();

            if (result.equals(Commands.EXIT.name())) {
                break;
            }
            this.writer.writeLine(result);
        }
    }
}
