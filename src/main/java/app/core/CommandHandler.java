package app.core;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public interface CommandHandler {

    String dispatchCommand(String[] inputParams) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ParseException;
}
