package ru.geekbrains.lessions2345.calculator.view.main;

import static android.content.Context.MODE_PRIVATE;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.BORDER_WIDTH;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_BORDER_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_RADIUS_SMALL;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_CURRENT_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_CURRENT_THEME;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_DOCHANGE_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.KEY_SETTINGS;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.core.CalcLogic;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewConstants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public class MainPresenter implements PresenterMainContract, Serializable, Parcelable {
    /** Исходные данные */ //region
    private ViewMainContract viewMain;
    private final CalcLogic calcLogic = new CalcLogic(this::showErrorInputting);
    public boolean doChangeRadius = false;
    //endregion

    public static final Creator<MainPresenter> CREATOR = new Creator<MainPresenter>() {
        @Override
        public MainPresenter createFromParcel(Parcel in) {
            return new MainPresenter(in);
        }

        @Override
        public MainPresenter[] newArray(int size) {
            return new MainPresenter[size];
        }
    };

    public void showErrorInputting(Constants.ERRORS_INPUTTING errorInputting) {
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
        if (viewMain.getDisplayWidth() >= BORDER_WIDTH)
            viewMain.setCurRadiusButtons(
                sharedPreferences.getInt(KEY_CURRENT_RADIUS, DEFAULT_BUTTON_RADIUS));
        else
            viewMain.setCurRadiusButtons(
                sharedPreferences.getInt(KEY_CURRENT_RADIUS, DEFAULT_BUTTON_RADIUS_SMALL));
        doChangeRadius = sharedPreferences.getBoolean(KEY_DOCHANGE_RADIUS, false);
        // Установка максимального количества выводимых калькулятором цифр в поле с результатом
        if ((viewMain.getDisplayWidth() >= BORDER_WIDTH) &&
            (viewMain.getCurRadiusButtons() >= DEFAULT_BUTTON_BORDER_RADIUS)) {
            setMaxNumberSymbolsInOutputTextField(
                // Умножаем на 2, потому что ширина чисел вдвое меньше величины EMS
                context.getResources().getInteger(R.integer.number_output_symbols_forEMS) * 2);
        } else {
            setMaxNumberSymbolsInOutputTextField(
                context.getResources().getInteger(
                // Умножаем на 2, потому что ширина чисел вдвое меньше величины EMS
                R.integer.number_output_symbols_forEMS_small) * 2);
        }
        // Вывод текущей темы
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
        editor.putInt(KEY_CURRENT_RADIUS, viewMain.getCurRadiusButtons());
        // Ставим false, что говорит о том, что изменения радиуса отработали
        editor.putBoolean(KEY_DOCHANGE_RADIUS, false);
        editor.apply();
    }

    //region Конструктор и методы для парселизации
    protected MainPresenter(Parcel in) {
        doChangeRadius = in.readByte() != 0;
    }
    @Override
    public int describeContents() { return 0; }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (doChangeRadius ? 1 : 0));
    }
    //endregion
}