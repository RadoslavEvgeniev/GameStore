package app.core.commands;

import app.core.BaseCommand;

import java.text.ParseException;

public class OwnedGameCommand extends BaseCommand {
    @Override
    public String execute() throws ParseException, InstantiationException, IllegalAccessException {
        return super.getHomeController().viewUsersOwnedGames();
    }
}
