package app.core.commands;

import app.core.BaseCommand;

import java.text.ParseException;

public class RemoveItemCommand extends BaseCommand {
    @Override
    public String execute() throws ParseException, InstantiationException, IllegalAccessException {
        String gameTitle = super.getInputParams()[1];
        return super.getHomeController().removeGameFromCart(gameTitle);
    }
}
