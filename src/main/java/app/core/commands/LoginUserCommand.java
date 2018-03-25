package app.core.commands;

import app.core.BaseCommand;

public class LoginUserCommand extends BaseCommand {
    @Override
    public String execute() {
        String email = super.getInputParams()[1];
        String password = super.getInputParams()[2];
        return super.getHomeController().loginUser(email, password);
    }
}
