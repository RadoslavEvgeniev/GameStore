package app.controllers;

import java.math.BigDecimal;
import java.util.Date;

public interface HomeController {

    String registerUser(String email, String password, String confirmPassword, String fullName);

    String loginUser(String email, String password);

    String logoutUser();

    String giveAdminRights(String email);

    String addGame(String title, BigDecimal price, Double size, String trailer, String imageUrl, String description, Date releaseDate);

    String editGame(Long id, String[] params) throws IllegalAccessException, InstantiationException;

    String deleteGame(Long id);

    String viewAllGames();

    String gameDetails(String gameTitle);

    String viewUsersOwnedGames();

    String addGameToCart(String gameTitle);

    String removeGameFromCart(String gameTitle);

    String buyGames();

}
