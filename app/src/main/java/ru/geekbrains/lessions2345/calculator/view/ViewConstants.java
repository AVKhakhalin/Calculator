package ru.geekbrains.lessions2345.calculator.view;

public interface ViewConstants {
    String KEY_SETTINGS = "Settings";
    String KEY_CURRENT_THEME = "CurrentTheme";
    String KEY_CURRENT_RADIUS = "Radius";
    String KEY_DOCHANGE_RADIUS = "DoRedraw";
    int DEFAULT_BUTTON_RADIUS = 131;
    int DEFAULT_BUTTON_RADIUS_SMALL = 107;
    float KOEFF_RESIZE_HEIGHT = 2.2f;
    int MAX_RADIUS_BUTTONS = 200;
    int BORDER_WIDTH = 1080;

    // Названия тем
    enum THEMES {
        DAY_THEME,         // Дневная тема
        NIGHT_THEME        // Ночная тема
    }
}
