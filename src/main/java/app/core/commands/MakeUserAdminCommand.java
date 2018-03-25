package app.core.commands;

import app.core.BaseCommand;

import java.text.ParseException;

public class MakeUserAdminCommand extends BaseCommand {
    @Override
    public String execute() throws ParseException, InstantiationException, IllegalAccessException {
        String email = super.getInputParams()[1];
        return super.getHomeController().giveAdminRights(email);
    }
}
