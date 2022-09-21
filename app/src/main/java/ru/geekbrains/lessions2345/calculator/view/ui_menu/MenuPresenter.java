package ru.geekbrains.lessions2345.calculator.view.ui_menu;

import static android.content.Context.MODE_PRIVATE;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.BORDER_WIDTH;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_RADIUS_SMALL;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_CURRENT_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_CURRENT_THEME;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_DOCHANGE_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_SETTINGS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.MAX_RADIUS_BUTTONS;
import android.content.Context;
import android.content.SharedPreferences;
import ru.geekbrains.lessions2345.calculator.view.ViewConstants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;
import ru.geekbrains.lessions2345.calculator.view.ui_main.MainActivity;

public class MenuPresenter implements PresenterMenuContract {
    /** Исходные данные */ //region
    public ViewConstants.THEMES currentTheme;
    public int curRadiusButtons = DEFAULT_BUTTON_RADIUS;
    private ViewMenuContract viewMenu;
    //endregion

    public MenuPresenter() {}

    @Override
    public void onAttach(ViewContract viewContract) {
        viewMenu = (ViewMenuContract) viewContract;
    }

    @Override
    public void onDetach() {
        viewMenu = null;
    }

    public ViewConstants.THEMES getSettings(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(KEY_CURRENT_THEME, 1);
        if (context.getResources().getDisplayMetrics().widthPixels >= BORDER_WIDTH)
            curRadiusButtons = sharedPreferences.getInt(KEY_CURRENT_RADIUS,
                    MainActivity.DEFAULT_BUTTON_RADIUS);
        else
            curRadiusButtons = sharedPreferences.getInt(KEY_CURRENT_RADIUS,
                    DEFAULT_BUTTON_RADIUS_SMALL);
        if (currentTheme == 0) {
            return ViewConstants.THEMES.NIGHT_THEME;
        } else {
            // Установка по умолчанию - дневная тема,
            // если в настройках стоит 1 или ничего не будет стоять
            return ViewConstants.THEMES.DAY_THEME;
        }
    }

    public void saveSettings(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Сохранение темы
        if (currentTheme == ViewConstants.THEMES.DAY_THEME) {
            editor.putInt(KEY_CURRENT_THEME, 1);
        } else if (currentTheme == ViewConstants.THEMES.NIGHT_THEME) {
            editor.putInt(KEY_CURRENT_THEME, 0);
        }
        // Сохранение радиуса кнопок
        int newRadius = Integer.parseInt(viewMenu.getNewRadius());
        if (newRadius < DEFAULT_BUTTON_RADIUS_SMALL)
        {
            newRadius = DEFAULT_BUTTON_RADIUS_SMALL;
        } else if (newRadius > MAX_RADIUS_BUTTONS)
        {
            newRadius = MAX_RADIUS_BUTTONS;
        }
        editor.putInt(KEY_CURRENT_RADIUS, newRadius);
        // Ставим true, что говорит о том, что изменения радиуса ещё не отработали
        editor.putBoolean(KEY_DOCHANGE_RADIUS, true);
        editor.apply();
    }
}
