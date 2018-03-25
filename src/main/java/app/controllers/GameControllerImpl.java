package app.controllers;

import app.entities.Game;
import app.entities.OwnedGame;
import app.entities.User;
import app.entities.dtos.GameDto;
import app.repositories.GameRepository;
import app.utilities.Constants;
import app.utilities.GameValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Primary
@Transactional
public class GameControllerImpl implements GameController {

    private GameRepository gameRepository;

    @Autowired
    public GameControllerImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public String add(GameDto gameDto) {
        String validationMessage = gameValidation(gameDto);
        if (!validationMessage.equals("")) {
            return validationMessage;
        }
        ModelMapper modelMapper = new ModelMapper();
        Game game = modelMapper.map(gameDto, Game.class);
        this.gameRepository.saveAndFlush(game);
        return String.format(Constants.SUCCESSFUL_GAME_ADD, game.getTitle());

    }

    @Override
    public String edit(Long id, GameDto gameDto) throws IllegalAccessException, InstantiationException {
        Game game = this.gameRepository.findOne(id);
        if (!GameValidator.gameExists(game)) {
            return Constants.GAME_DOES_NOT_EXIST;
        }
        Field[] gameDtoFields = gameDto.getClass().getDeclaredFields();
        Field[] gameFields = game.getClass().getDeclaredFields();
        for (Field gameDtoField : gameDtoFields) {
            gameDtoField.setAccessible(true);
            Object value = gameDtoField.get(gameDto);
            if (value == null) {
                continue;
            }
            for (Field gameField : gameFields) {
                if (gameDtoField.getName().equals(gameField.getName())) {
                    gameField.setAccessible(true);
                    gameField.set(game, value);
                }
            }
        }
        ModelMapper modelMapper = new ModelMapper();
        gameDto = modelMapper.map(game, GameDto.class);
        String validationMessage = gameValidation(gameDto);
        if (!validationMessage.equals("")) {
            return validationMessage;
        }
        this.gameRepository.saveAndFlush(game);
        return String.format(Constants.SUCCESSFUL_GAME_EDIT, game.getTitle());
    }

    @Override
    public String delete(Long id) {
        Game game = this.gameRepository.findOne(id);
        if (!GameValidator.gameExists(game)) {
            return Constants.GAME_DOES_NOT_EXIST;
        }
        this.gameRepository.delete(game);
        return String.format(Constants.SUCCESSFUL_GAME_DELETE, game.getTitle());
    }

    @Override
    public String viewAll() {
        List<Game> games = this.gameRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (Game game : games) {
            sb.append(game.getTitle()).append(" ").append(String.format("%.2f", game.getPrice())).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public String details(String gameTitle) {
        Game game = this.gameRepository.findByTitle(gameTitle);
        if (!GameValidator.gameExists(game)) {
            return Constants.GAME_DOES_NOT_EXIST;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Title: %s", game.getTitle())).append("\n");
        sb.append(String.format("Price: %.2f", game.getPrice())).append("\n");
        sb.append(String.format("Description: %s", game.getDescription())).append("\n");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(game.getReleaseDate());
        sb.append(String.format("Release date: %s", date));
        return sb.toString();
    }

    @Override
    public String viewOwned(User user) {
        if (!GameValidator.userOwnAnyGames(user)) {
            return String.format(Constants.USER_DO_NOT_OWN_ANY_GAMES, user.getFullName());
        }
        StringBuilder sb = new StringBuilder();
        for (OwnedGame game : user.getGames()) {
            sb.append(game.getTitle()).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public Game getGame(String gameTitle) {
        return this.gameRepository.findByTitle(gameTitle);
    }

    private String gameValidation(GameDto gameDto) {
        if (!GameValidator.checkGameName(gameDto)) {
            return Constants.INVALID_GAME_NAME;
        } else if (!GameValidator.checkGamePrice(gameDto)) {
            return Constants.NEGATIVE_GAME_PRICE;
        } else if (!GameValidator.checkGameSize(gameDto)) {
            return Constants.INVALID_GAME_SIZE;
        } else if (!GameValidator.checkGameVideoUrl(gameDto)) {
            return Constants.INVALID_GAME_VIDEO_URL;
        } else if (!GameValidator.checkGamePictureUrl(gameDto)) {
            return Constants.INVALID_GAME_PICTURE_URL;
        } else if (!GameValidator.checkGameDescription(gameDto)) {
            return Constants.INVALID_DESCRIPTION_LENGTH;
        }
        return "";
    }


}
