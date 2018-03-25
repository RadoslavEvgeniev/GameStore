package app.core.commands;

import app.core.BaseCommand;

import java.text.ParseException;

public class DeleteGameCommand extends BaseCommand {
    @Override
    public String execute() throws ParseException, InstantiationException, IllegalAccessException {
        Long id = Long.parseLong(super.getInputParams()[1]);
        return super.getHomeController().deleteGame(id);
    }
}
