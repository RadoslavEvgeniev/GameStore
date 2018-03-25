package app.core;

import app.annotations.Inject;
import app.controllers.HomeController;

public abstract class BaseCommand implements Command {

    @Inject
    private String[] inputParams;
    @Inject
    private HomeController homeController;

    public BaseCommand() {
    }

    public String[] getInputParams() {
        return this.inputParams;
    }

    public HomeController getHomeController() {
        return this.homeController;
    }
}
