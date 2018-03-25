package app.core.commands;

import app.core.BaseCommand;

import java.text.ParseException;

public class BuyItemCommand extends BaseCommand {
    @Override
    public String execute() throws ParseException, InstantiationException, IllegalAccessException {
        return super.getHomeController().buyGames();
    }
}
