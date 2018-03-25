package app.core;

import java.text.ParseException;

public interface Command {

    String execute() throws ParseException, InstantiationException, IllegalAccessException;
}
