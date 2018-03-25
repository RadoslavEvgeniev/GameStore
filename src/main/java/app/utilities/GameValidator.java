package app.utilities;

import app.entities.Game;
import app.entities.User;
import app.entities.dtos.GameDto;

import java.math.BigDecimal;

public final class GameValidator {

    public static boolean checkGameName(GameDto gameDto) {
        if (!Character.isUpperCase(gameDto.getTitle().charAt(0)) || gameDto.getTitle().length() < 3 || gameDto.getTitle().length() > 100) {
            return false;
        }
        return true;
    }

    public static boolean checkGamePrice(GameDto gameDto) {
        if (gameDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return true;
    }

    public static boolean checkGameSize(GameDto gameDto) {
        if (gameDto.getSize() < 0) {
            return false;
        }
        return true;
    }

    public static boolean checkGameVideoUrl(GameDto gameDto) {
        if (gameDto.getTrailer().length() != 11) {
            return false;
        }
        return true;
    }

    public static boolean checkGamePictureUrl(GameDto gameDto) {
        if (!gameDto.getImageUrl().startsWith("http://") && !gameDto.getImageUrl().startsWith("https://")) {
            return false;
        }
        return true;
    }

    public static boolean checkGameDescription(GameDto gameDto) {
        if (gameDto.getDescription().length() < 20) {
            return false;
        }
        return true;
    }

    public static boolean gameExists(Game game) {
        if (game == null) {
            return false;
        }
        return true;
    }

    public static boolean userOwnAnyGames(User user) {
        if (user.getGames().size() == 0) {
            return false;
        }
        return true;
    }
}
