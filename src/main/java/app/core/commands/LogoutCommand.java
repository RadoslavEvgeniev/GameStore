package app.core.commands;

import app.core.BaseCommand;

public class LogoutCommand extends BaseCommand {
    @Override
    public String execute() {
        return super.getHomeController().logoutUser();
    }
}
