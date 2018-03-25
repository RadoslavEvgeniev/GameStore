package app.controllers;

import app.entities.User;
import app.entities.dtos.LoginUserDto;
import app.entities.dtos.RegisterUserDto;

public interface UserController {

    String register(RegisterUserDto userDto);

    String login(LoginUserDto userDto);

    User getUser(String email);

    void saveAndFlush(User user);

    String giveAdminRights(User user, String email);
}
