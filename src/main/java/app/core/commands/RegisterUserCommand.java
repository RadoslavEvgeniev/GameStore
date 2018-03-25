package app.core.commands;

import app.core.BaseCommand;

public class RegisterUserCommand extends BaseCommand {
    @Override
    public String execute() {
        String email = super.getInputParams()[1];
        String password = super.getInputParams()[2];
        String confirmPassword = super.getInputParams()[3];
        String fullName = super.getInputParams()[4];
        return super.getHomeController().registerUser(email, password, confirmPassword, fullName);
    }
}
