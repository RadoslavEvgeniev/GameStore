package app.core.commands;

import app.core.BaseCommand;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGameCommand extends BaseCommand {
    @Override
    public String execute() throws ParseException {
        String title = super.getInputParams()[1];
        BigDecimal price = new BigDecimal(super.getInputParams()[2]);
        Double size = Double.parseDouble(super.getInputParams()[3]);
        String trailer = super.getInputParams()[4];
        String imageUrl = super.getInputParams()[5];
        String description = super.getInputParams()[6];
        Date releaseDate = new SimpleDateFormat("dd-MM-yyyy").parse(super.getInputParams()[7]);
        return super.getHomeController().addGame(title, price, size, trailer, imageUrl, description, releaseDate);
    }
}
