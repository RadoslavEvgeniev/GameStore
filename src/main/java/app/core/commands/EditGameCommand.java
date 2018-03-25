package app.core.commands;

import app.core.BaseCommand;

import java.text.ParseException;
import java.util.Arrays;

public class EditGameCommand extends BaseCommand {
    @Override
    public String execute() throws ParseException, InstantiationException, IllegalAccessException {
        Long id = Long.parseLong(super.getInputParams()[1]);
        String[] params = Arrays.stream(super.getInputParams()).skip(2).toArray(String[]::new);
        return super.getHomeController().editGame(id, params);
    }
}
