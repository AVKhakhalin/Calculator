package ru.geekbrains.lessions2345.calculator.view.ui_main;

import static android.content.Context.MODE_PRIVATE;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.BORDER_WIDTH;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_RADIUS_SMALL;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_CURRENT_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_CURRENT_THEME;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_DOCHANGE_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_SETTINGS;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Locale;
import ru.geekbrains.lessions2345.calculator.core.CalcLogic;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.core.ErrorMessages;
import ru.geekbrains.lessions2345.calculator.view.ViewConstants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public class MainPresenter implements PresenterMainContract, ErrorMessages {
    /** Исходные данные */ //region
    private final CalcLogic calcLogic = new CalcLogic((ErrorMessages) this);
    private ViewMainContract viewMain;
    public int curRadiusButtons;
    public boolean doChangeRadius = false;
    //endregion

    @Override
    public void getErrorInputting(Constants.ERRORS_INPUTTING errorInputting) {
        if (viewMain != null) viewMain.showErrorInputting(errorInputting);
    }

    /** Задание различных конструкторов для презентера */ //region
    public MainPresenter() {
    }
    // Нужно для тестирования
    public MainPresenter(ViewMainContract viewMain) {
        this.viewMain = viewMain;
    }
    //endregion

    @Override
    public void setMaxNumberSymbolsInOutputTextField(int maxNumberSymbolsInOutputTextField) {
        calcLogic.setMaxNumberSymbolsInOutputTextField(maxNumberSymbolsInOutputTextField);
    }

    @Override
    public double addNumeral(int newNumeral) {
        double result = calcLogic.addNumeral(newNumeral);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        return result;
    }

    @Override
    public void calculate() {
        calcLogic.calculate();
    }

    @Override
    public void setCurZapitay() {
        calcLogic.setCurZapitay();
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearAll() {
        calcLogic.clearAll();
        calculate();
        getError();
        if (viewMain != null) viewMain.setOutputResultText(calcLogic.getFinalResult());
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearOne() {
        if (calcLogic.clearOne()) {
            // TODO: Действие, если нужно как-то по-особому обновить поле с результатом
        }
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearTwo() {
        if (calcLogic.clearTwo()) {
            // TODO: Действие, если нужно как-то по-особому обновить поле с результатом
        }
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void setNewAction(Constants.ACTIONS action) {
        calcLogic.setNewAction(action);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void changeSign() {
        calcLogic.changeSign();
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public String setNewFunction(Constants.FUNCTIONS typeFuncInBracket) {
        String result = calcLogic.setNewFunction(typeFuncInBracket);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        return result;
    }

    @Override
    public boolean getPressedZapitay() {
        return calcLogic.getPressedZapitay();
    }

    @Override
    public String createOutput() {
        return calcLogic.createOutput();
    }

    @Override
    public String getFinalResult() {
        return calcLogic.getFinalResult();
    }

    @Override
    public void setEqual() {
        calcLogic.calculate();
        getError();
        if (viewMain != null) viewMain.setOutputResultText(calcLogic.getFinalResult());
    }

    @Override
    public void getError() {
        if (viewMain != null) viewMain.showErrorInString(calcLogic.getErrorCode());
        calcLogic.clearErrorCode();
    }

    @Override
    public void setBracketOpen() {
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", setNewFunction(Constants.FUNCTIONS.FUNC_NO)));
    }

    @Override
    public void setBracketClose() {
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", calcLogic.closeBracket()));
    }

    @Override
    public void getInit() {
        if (viewMain != null) {
            viewMain.setOutputResultText(calcLogic.getFinalResult());
            viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                    "%s", createOutput()));
        }
    }

    @Override
    public void onAttach(ViewContract viewContract) {
        if (viewContract != null) viewMain = (ViewMainContract) viewContract;
    }

    @Override
    public void onDetach() {
        if (viewMain != null) viewMain = null;
    }

    // Считать информацию о текущих настройках программы (теме и радиусе кнопок)
    ViewConstants.THEMES getSettings(Context context) {
        SharedPreferences sharedPreferences =
            context.getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(KEY_CURRENT_THEME, 1);
        if (context.getResources().getDisplayMetrics().widthPixels >= BORDER_WIDTH)
            curRadiusButtons = sharedPreferences.getInt(KEY_CURRENT_RADIUS, DEFAULT_BUTTON_RADIUS);
        else
            curRadiusButtons = sharedPreferences.getInt(
                    KEY_CURRENT_RADIUS, DEFAULT_BUTTON_RADIUS_SMALL);
        doChangeRadius = sharedPreferences.getBoolean(KEY_DOCHANGE_RADIUS, false);
        if (currentTheme == 0) {
            return ViewConstants.THEMES.NIGHT_THEME;
        } else {
            // Установка по умолчанию - дневная тема,
            // если в настройках стоит 1 или ничего не будет стоять
            return ViewConstants.THEMES.DAY_THEME;
        }
    }

    void saveSettings(ViewConstants.THEMES currentTheme, Context context) {
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
        editor.putInt(KEY_CURRENT_RADIUS, curRadiusButtons);
        // Ставим false, что говорит о том, что изменения радиуса отработали
        editor.putBoolean(KEY_DOCHANGE_RADIUS, false);
        editor.apply();
    }
}