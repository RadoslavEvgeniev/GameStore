package app.iomanagers;

import java.util.Scanner;

public class TerminalReader implements Reader {

    private Scanner scanner;

    public TerminalReader() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return this.scanner.nextLine();
    }
}
