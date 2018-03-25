package app.utilities;

import app.entities.Game;
import app.entities.OwnedGame;
import app.entities.User;
import app.entities.dtos.LoginUserDto;
import app.entities.dtos.RegisterUserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserValidator {

    public static boolean checkUserEmailAtRegistration(RegisterUserDto userDto) {
        Pattern pattern = Pattern.compile("(([A-Za-z0-9]+)([\\._-]?)([A-Za-z0-9]+))(@[a-z]+\\.[a-z]+)");
        Matcher match = pattern.matcher(userDto.getEmail());
        if (match.find()) {
            return true;
        }
        return false;
    }

    public static boolean checkUserPasswordAtRegistration(RegisterUserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return false;
        }
        String password = userDto.getPassword();
        if (password.length() < 6) {
            return false;
        }
        boolean containsLowerCaseLetter = false;
        boolean containsDigit = false;
        boolean containsUpperCaseLetter = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUpperCaseLetter = true;
                break;
            }
        }
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                containsLowerCaseLetter = true;
                break;
            }
        }
        for (char c : password.toCharArray()) {
            if(Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }
        if (containsDigit && containsLowerCaseLetter && containsUpperCaseLetter) {
            return true;
        }
        return false;
    }

    public static boolean areThereAdmins(int size) {
        if (size == 0) {
            return false;
        }
        return true;
    }

    public static boolean userExists(User user) {
        if (user == null) {
            return false;
        }
        return true;
    }

    public static boolean checkUserEmailAtLogin(User user, LoginUserDto userDto) {
        if (!user.getEmail().equals(userDto.getEmail())) {
            return false;
        }
        return true;
    }

    public static boolean checkUserPasswordAtLogin(User user, LoginUserDto userDto) {
        if (!user.getPassword().equals(userDto.getPassword())) {
            return false;
        }
        return true;
    }

    public static boolean checkForLoggedInUser(User user) {
        return UserValidator.userExists(user);
    }

    public static boolean loggedInSuccessful(String loginResult) {
        if (loginResult.contains(" logged in successfully!")) {
            return true;
        }
        return false;
    }

    public static boolean userIsAdmin(User user) {
        if (!user.getAdmin()) {
            return false;
        }
        return true;
    }

    public static boolean userHasActiveOrder(User user) {
        if (user.getOrder() == null) {
            return false;
        }
        return true;
    }

    public static boolean userOwnsThatGame(User user, Game game) {
        for (OwnedGame ownedGame : user.getGames()) {
            if (ownedGame.getTitle().equals(game.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public static boolean userAddedThatGame(User user, Game game) {
        for (Game game1 : user.getOrder().getProducts()) {
            if (game1.getTitle().equals(game.getTitle())) {
                return true;
            }
        }
        return false;
    }
}
