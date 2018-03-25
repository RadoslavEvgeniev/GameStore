package app.controllers;

import app.annotations.Price;
import app.annotations.Size;
import app.entities.Game;
import app.entities.Order;
import app.entities.OwnedGame;
import app.entities.User;
import app.entities.dtos.GameDto;
import app.entities.dtos.LoginUserDto;
import app.entities.dtos.RegisterUserDto;
import app.repositories.OrderRepository;
import app.utilities.Constants;
import app.utilities.GameValidator;
import app.utilities.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Primary
@Transactional
public class HomeControllerImpl implements HomeController {

    private User loggedInUser;
    private UserController userController;
    private GameController gameController;
    private OrderRepository orderRepository;

    @Autowired
    public HomeControllerImpl(UserController userController, GameController gameController, OrderRepository orderRepository) {
        this.userController = userController;
        this.gameController = gameController;
        this.orderRepository = orderRepository;
    }

    public String registerUser(String email, String password, String confirmPassword, String fullName) {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setConfirmPassword(confirmPassword);
        userDto.setFullName(fullName);
        return this.userController.register(userDto);
    }

    @Override
    public String loginUser(String email, String password) {
        LoginUserDto userDto = new LoginUserDto();
        userDto.setEmail(email);
        userDto.setPassword(password);
        if (UserValidator.checkForLoggedInUser(this.loggedInUser)) {
            return Constants.ANOTHER_USER_LOGGED_IN;
        }
        String loginResult = this.userController.login(userDto);
        if (UserValidator.loggedInSuccessful(loginResult)) {
            this.loggedInUser = this.userController.getUser(email);
        }
        return loginResult;
    }

    @Override
    public String logoutUser() {
        if (!UserValidator.checkForLoggedInUser(this.loggedInUser)) {
            return Constants.NO_USER_LOGGED_IN;
        }
        this.loggedInUser = null;
        return Constants.SUCCESSFUL_LOGOUT;
    }

    public String giveAdminRights(String email) {
        if (!UserValidator.checkForLoggedInUser(this.loggedInUser)) {
            return Constants.ADMIN_MUST_BE_LOGGED_IN;
        } else if (!UserValidator.userIsAdmin(this.loggedInUser)) {
            return Constants.ADMIN_PERMISSION_REQUIRED;
        }
        return this.userController.giveAdminRights(this.loggedInUser, email);
    }

    public String addGame(String title, BigDecimal price, Double size, String trailer, String imageUrl, String description, Date releaseDate) {
        if (!UserValidator.checkForLoggedInUser(this.loggedInUser)) {
            return Constants.ADMIN_MUST_BE_LOGGED_IN;
        } else if (!UserValidator.userIsAdmin(this.loggedInUser)) {
            return Constants.ADMIN_PERMISSION_REQUIRED;
        }
        GameDto gameDto = new GameDto();
        gameDto.setTitle(title);
        gameDto.setPrice(price);
        gameDto.setSize(size);
        gameDto.setTrailer(trailer);
        gameDto.setImageUrl(imageUrl);
        gameDto.setDescription(description);
        gameDto.setReleaseDate(releaseDate);
        return this.gameController.add(gameDto);
    }

    @Override
    public String editGame(Long id, String[] params) throws IllegalAccessException, InstantiationException {
        if (!UserValidator.checkForLoggedInUser(this.loggedInUser)) {
            return Constants.ADMIN_MUST_BE_LOGGED_IN;
        } else if (!UserValidator.userIsAdmin(this.loggedInUser)) {
            return Constants.ADMIN_PERMISSION_REQUIRED;
        }
        GameDto gameDto = new GameDto();
        Field[] gameDtoFields = gameDto.getClass().getDeclaredFields();
        for (String param : params) {
            String[] args = param.split("=");
            for (Field gameDtoField : gameDtoFields) {
                if (gameDtoField.getName().equals(args[0])) {
                    gameDtoField.setAccessible(true);
                    if (gameDtoField.isAnnotationPresent(Price.class)) {
                        BigDecimal price = new BigDecimal(args[1]);
                        gameDtoField.set(gameDto, price);
                    } else if (gameDtoField.isAnnotationPresent(Size.class)) {
                        Double size = Double.parseDouble(args[1]);
                        gameDtoField.set(gameDto, size);
                    } else {
                        gameDtoField.set(gameDto, args[1]);
                    }
                }
            }
        }
        return this.gameController.edit(id, gameDto);
    }

    @Override
    public String deleteGame(Long id) {
        if (!UserValidator.checkForLoggedInUser(this.loggedInUser)) {
            return Constants.ADMIN_MUST_BE_LOGGED_IN;
        } else if (!UserValidator.userIsAdmin(this.loggedInUser)) {
            return Constants.ADMIN_PERMISSION_REQUIRED;
        }
        return this.gameController.delete(id);
    }

    @Override
    public String gameDetails(String gameTitle) {
        return this.gameController.details(gameTitle);
    }

    @Override
    public String viewAllGames() {
        return this.gameController.viewAll();
    }

    @Override
    public String viewUsersOwnedGames() {
        if (!UserValidator.checkForLoggedInUser(this.loggedInUser)) {
            return Constants.NO_USER_LOGGED_IN;
        }
        return this.gameController.viewOwned(this.loggedInUser);
    }

    @Override
    public String addGameToCart(String gameTitle) {
        User user = this.userController.getUser(this.loggedInUser.getEmail());
        if (!UserValidator.checkForLoggedInUser(user)) {
            return Constants.LOGIN_TO_ADD_GAMES;
        }
        Game game = this.gameController.getGame(gameTitle);
        if (!GameValidator.gameExists(game)) {
            return Constants.GAME_DOES_NOT_EXIST;
        } else if (UserValidator.userOwnsThatGame(user, game)) {
            return Constants.USER_ALREADY_HAS_THAT_GAME;
        }
        if (!UserValidator.userHasActiveOrder(user)) {
            user.setOrder(new Order());
        } else if (UserValidator.userAddedThatGame(user, game)) {
            return Constants.USER_ADDED_THAT_GAME;
        }

        game.getOrders().add(user.getOrder());
        user.getOrder().getProducts().add(game);
        user.getOrder().setUser(user);
        this.userController.saveAndFlush(user);
        this.loggedInUser = user;
        return String.format(Constants.GAME_ADDED_TO_CART_SUCCESSFULLY, game.getTitle());
    }

    @Override
    public String removeGameFromCart(String gameTitle) {
        User user = this.userController.getUser(this.loggedInUser.getEmail());
        Game game = this.gameController.getGame(gameTitle);
        if (!UserValidator.checkForLoggedInUser(user)) {
            return Constants.NO_USER_LOGGED_IN;
        } else if (!UserValidator.userHasActiveOrder(user)) {
            return Constants.CART_EMPTY;
        } else if (!GameValidator.gameExists(game)) {
            return Constants.GAME_DOES_NOT_EXIST;
        } else if (!UserValidator.userAddedThatGame(user, game)) {
            return Constants.USER_DID_NOT_ADD_THAT_GAME;
        }
        Order order = user.getOrder();
        order.setProducts(user.getOrder().getProducts().stream().filter(g -> !g.getTitle().equals(game.getTitle())).collect(Collectors.toSet()));
        order.setId(user.getOrder().getId());
        order.setUser(user.getOrder().getUser());
        user.setOrder(order);
        game.setOrders(game.getOrders().stream().filter(o -> !o.getId().equals(order.getId())).collect(Collectors.toSet()));
        this.userController.saveAndFlush(user);
        this.loggedInUser = user;
        return String.format(Constants.GAME_REMOVED_FROM_CART_SUCCESSFULLY, game.getTitle());
    }

    @Override
    public String buyGames() {
        User user = this.userController.getUser(this.loggedInUser.getEmail());
        if (!UserValidator.checkForLoggedInUser(user)) {
            return Constants.LOGIN_TO_BUY_GAMES;
        } else if (!UserValidator.userHasActiveOrder(user)) {
            return Constants.CART_EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SUCCESSFULLY_BOUGHT_GAMES).append("\n");
        for (Game game : user.getOrder().getProducts()) {
            ModelMapper modelMapper = new ModelMapper();
            OwnedGame ownGame = modelMapper.map(game, OwnedGame.class);
            sb.append("- ").append(ownGame.getTitle()).append("\n");
            user.getGames().add(ownGame);
            ownGame.getUsers().add(user);
        }
        user.setOrder(null);
        this.userController.saveAndFlush(user);
        this.loggedInUser = user;
        return sb.toString().trim();
    }
}
