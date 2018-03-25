package app.controllers;

import app.entities.User;
import app.entities.dtos.LoginUserDto;
import app.entities.dtos.RegisterUserDto;
import app.repositories.UserRepository;
import app.utilities.Constants;
import app.utilities.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Transactional
public class UserControllerImpl implements UserController {

    private UserRepository userRepository;

    @Autowired
    public UserControllerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String register(RegisterUserDto userDto) {
        if (!UserValidator.checkUserEmailAtRegistration(userDto)) {
            return Constants.INVALID_USER_EMAIL;
        } else if (!UserValidator.checkUserPasswordAtRegistration(userDto)) {
            return Constants.INVALID_USER_PASSWORD;
        }
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        User check = this.userRepository.findByEmail(user.getEmail());
        if (UserValidator.userExists(check)) {
            return Constants.USER_EXISTS;
        }
        if (!UserValidator.areThereAdmins(this.userRepository.findAll().size())) {
            user.setAdmin(true);
        } else {
            user.setAdmin(false);
        }
        this.userRepository.saveAndFlush(user);
        return String.format(Constants.SUCCESSFUL_REGISTRATION, userDto.getFullName());
    }

    public String login(LoginUserDto userDto) {
        User user = this.userRepository.findByEmail(userDto.getEmail());
        if (!UserValidator.userExists(user)) {
            return Constants.USER_DOES_NOT_EXIST;
        }
        if (!UserValidator.checkUserEmailAtLogin(user, userDto)) {
            return Constants.INVALID_USER_EMAIL;
        } else if (!UserValidator.checkUserPasswordAtLogin(user, userDto)) {
            return Constants.INVALID_USER_PASSWORD;
        }
        return String.format(Constants.SUCCESSFUL_LOGIN, user.getFullName());
    }

    @Override
    public String giveAdminRights(User admin, String email) {
        User user = this.userRepository.findByEmail(email);
        if (!UserValidator.userExists(user)) {
            return Constants.USER_DOES_NOT_EXIST;
        }
        user.setAdmin(true);
        this.userRepository.saveAndFlush(user);
        return String.format(Constants.ADMIN_RIGHTS_GIVEN, admin.getFullName(), user.getFullName());
    }

    @Override
    public User getUser(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void saveAndFlush(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
