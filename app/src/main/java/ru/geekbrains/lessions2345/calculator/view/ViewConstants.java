package ru.geekbrains.lessions2345.calculator.view;

public interface ViewConstants {
    String KEY_SETTINGS = "Settings";
    String KEY_CURRENT_THEME = "CurrentTheme";
    String KEY_CURRENT_RADIUS = "Radius";
    String KEY_DOCHANGE_RADIUS = "DoRedraw";
    int DEFAULT_BUTTONS_DELTA_ANGLE = 30;
    int DEFAULT_BUTTON_RADIUS = 131;
    int DEFAULT_BUTTON_BORDER_RADIUS = 120;
    int DEFAULT_BUTTON_RADIUS_SMALL = 107;
    float KOEFF_RESIZE_HEIGHT = 2.2f;
    int MAX_RADIUS_BUTTONS = 200;
    int BORDER_WIDTH = 1080;
    String ERROR_MESSAGE_KEY = "Error Message";
    String ERROR_TYPE_KEY = "Error Type Message";
    String ERROR_MESSAGE_FRAGMENT_KEY = "Error Message Fragment";
    String MAIN_PRESENTER_KEY = "Main Presenter";

    // Названия тем
    enum THEMES {
        DAY_THEME,         // Дневная тема
        NIGHT_THEME        // Ночная тема
    }

    // Изменение размера текста для отображения степени
    Float SQUARE_TEXT_RELATIVE_SIZE = 0.65f;

}
