package app.controllers;

import app.entities.Game;
import app.entities.User;
import app.entities.dtos.GameDto;

public interface GameController {

    String add(GameDto gameDto);

    String edit(Long id, GameDto gameDto) throws IllegalAccessException, InstantiationException;

    String delete(Long id);

    String viewAll();

    String details(String gameTitle);

    String viewOwned(User user);

    Game getGame(String gameTitle);
}
