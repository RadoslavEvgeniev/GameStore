package app.iomanagers;

public class TerminalWriter implements Writer {
    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }
}
