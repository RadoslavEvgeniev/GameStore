package app.engines;

import app.core.CommandHandler;
import app.iomanagers.Reader;
import app.iomanagers.TerminalReader;
import app.iomanagers.TerminalWriter;
import app.iomanagers.Writer;
import app.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class Engine implements CommandLineRunner {

    private Reader reader;
    private CommandHandler commandHandler;
    private Writer writer;

    @Autowired
    public Engine(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.reader = new TerminalReader();
        this.writer = new TerminalWriter();
    }

    @Override
    public void run(String... args) throws Exception {
        this.writer.writeLine("Application started!!!");
        while (true) {
            try {
                String[] inputParams = this.reader.readLine().trim().split("\\|");
                if ("Close".equals(inputParams[0])) {
                    break;
                }
                this.writer.writeLine(this.commandHandler.dispatchCommand(inputParams));
            } catch (ClassNotFoundException cnfe) {
                this.writer.writeLine(Constants.INVALID_COMMAND);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                this.writer.writeLine(Constants.INVALID_NUMBER_OF_PARAMS);
            }

        }
        this.writer.writeLine("Application closed!!!");
    }
}