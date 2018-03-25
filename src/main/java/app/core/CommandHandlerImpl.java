package app.core;

import app.annotations.Inject;
import app.controllers.HomeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

@Service
@Primary
@Transactional
public class CommandHandlerImpl implements CommandHandler {

    private String[] inputParams;
    private HomeController homeController;

    @Autowired
    public CommandHandlerImpl(HomeController homeController) {
        this.homeController = homeController;
    }

    @SuppressWarnings("unchecked")
    public String dispatchCommand(String[] inputParams) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ParseException {
        this.inputParams = inputParams;
        Class<Command> commandClass = (Class<Command>) Class.forName("app.core.commands." + this.inputParams[0] + "Command");
        Constructor<Command> commandConstructor = commandClass.getDeclaredConstructor();
        Command command = commandConstructor.newInstance();
        inject(command);
        return command.execute();
    }

    private void inject(Command command) throws IllegalAccessException {
        Field[] commandFields = command.getClass().getSuperclass().getDeclaredFields();
        for (Field commandField : commandFields) {
            if (commandField.isAnnotationPresent(Inject.class)) {
                Field[] thisFields = this.getClass().getDeclaredFields();
                for (Field thisField : thisFields) {
                    if (commandField.getType().equals(thisField.getType())) {
                        commandField.setAccessible(true);
                        commandField.set(command, thisField.get(this));
                    }
                }
            }
        }
    }
}
