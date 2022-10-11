package ru.geekbrains.lessions2345.calculator.view.main;

import static android.content.Context.MODE_PRIVATE;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING_TYPE;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_IN_STRING_TYPE;
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
import java.io.Serializable;
import java.util.Locale;
import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.core.CalcLogic;
import ru.geekbrains.lessions2345.calculator.core.CalcLogicImpl;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewConstants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public class MainPresenter implements PresenterMainContract, Serializable, Parcelable {
    /** Исходные данные */ //region
    private ViewMainContract viewMain;
    private final CalcLogic calcLogicImpl = new CalcLogicImpl(this::showErrorInputting);
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
        if (viewMain != null) viewMain.showErrorInputting(errorInputting, ERRORS_INPUTTING_TYPE);
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
        calcLogicImpl.setMaxNumberSymbolsInOutputTextField(maxNumberSymbolsInOutputTextField);
    }

    @Override
    public double addNumeral(int newNumeral) {
        double result = calcLogicImpl.addNumeral(newNumeral);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        return result;
    }

    @Override
    public void calculate() {
        calcLogicImpl.calculate();
    }

    @Override
    public void setCurZapitay() {
        calcLogicImpl.setCurZapitay();
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearAll() {
        calcLogicImpl.clearAll();
        calculate();
        getError();
        if (viewMain != null) viewMain.setOutputResultText(calcLogicImpl.getFinalStringResult());
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearOne() {
        if (calcLogicImpl.clearOne()) {
            // TODO: Действие, если нужно как-то по-особому обновить поле с результатом
        }
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearTwo() {
        if (calcLogicImpl.clearTwo()) {
            // TODO: Действие, если нужно как-то по-особому обновить поле с результатом
        }
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void setNewAction(Constants.ACTIONS action) {
        calcLogicImpl.setNewAction(action);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void changeSign() {
        calcLogicImpl.changeSign();
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public String setNewFunction(Constants.FUNCTIONS typeFuncInBracket) {
        String result = calcLogicImpl.setNewFunction(typeFuncInBracket);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        return result;
    }

    @Override
    public boolean getPressedZapitay() {
        return calcLogicImpl.getPressedZapitay();
    }

    @Override
    public String createOutput() {
        return calcLogicImpl.createOutput();
    }

    @Override
    public String getFinalResult() {
        return calcLogicImpl.getFinalStringResult();
    }

    @Override
    public void setEqual() {
        calcLogicImpl.calculate();
        getError();
        if (viewMain != null) viewMain.setOutputResultText(calcLogicImpl.getFinalStringResult());
    }

    @Override
    public void getError() {
        if (viewMain != null) viewMain.showErrorInString(
            calcLogicImpl.getErrorCode(), ERRORS_IN_STRING_TYPE);
        calcLogicImpl.clearErrorCode();
    }

    @Override
    public void setBracketOpen() {
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", setNewFunction(Constants.FUNCTIONS.FUNC_NO)));
    }

    @Override
    public void setBracketClose() {
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", calcLogicImpl.closeBracket()));
    }

    @Override
    public void getInit() {
        if (viewMain != null) {
            viewMain.setOutputResultText(calcLogicImpl.getFinalStringResult());
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