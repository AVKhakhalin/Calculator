package ru.geekbrains.lessions2345.calculator.view.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewConstants;
import ru.geekbrains.lessions2345.calculator.view.errormessages.ErrorMessagesFragment;
import ru.geekbrains.lessions2345.calculator.view.menu.MenuActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ViewConstants, Constants, ViewMainContract {
    /** Исходные данные */ //region
    private TextView outputResultText;
    private TextView inputtedHistoryText;
    private Button buttonZero;
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private Button buttonFour;
    private Button buttonFive;
    private Button buttonSix;
    private Button buttonSeven;
    private Button buttonEight;
    private Button buttonNine;
    private Button buttonEqual;
    private Button buttonZapitay;
    private Button buttonBracketClose;
    private Button buttonBackspace;
    private Button buttonBackspaceOne;
    private Button buttonBackspaceTwo;
    private Button buttonBracketOpen;
    private Button buttonDivide;
    private Button buttonMinus;
    private Button buttonMultiply;
    private Button buttonPercent;
    private Button buttonPlus;
    private Button buttonPlusMinus;
    private Button buttonSqrt;
    private Button buttonStepen;
    private Button buttonChangeTheme;
    private final List<Button> buttonsNumbersGroups = new ArrayList();
    private final List<Button> buttonsActionsGroups = new ArrayList();

    private THEMES currentTheme;

    private MainPresenter mainPresenter = new MainPresenter();
    private int displayWidth = 0;
    public int curRadiusButtons;
    //endregion

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MAIN_PRESENTER_KEY, mainPresenter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainPresenter = (MainPresenter) savedInstanceState.getSerializable(MAIN_PRESENTER_KEY);
    }

    // Данный метод нужен только для тестирования приложения
    @Override
    public void setDisplayWidthAndCurRadiusButtons(int displayWidth, int curRadiusButtons) {
        this.displayWidth = displayWidth;
        this.curRadiusButtons = curRadiusButtons;
        // Инициализация или перестройка текстовых полей
        initTextFields();
    }
    @Override
    public int getCurRadiusButtons() {
        return curRadiusButtons;
    }
    @Override
    public void setCurRadiusButtons(int curRadiusButtons) {
        this.curRadiusButtons = curRadiusButtons;
    }
    @Override
    public int getDisplayWidth() {
        return displayWidth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Отслеживание первичного запуска или перезапуска активити
        // Установка параметра дисплея текущего устройства
        displayWidth = getResources().getDisplayMetrics().widthPixels;
        firstRunDetection(savedInstanceState);
        // Передача вью MainActivity в MainPresenter в рамках шаблона MVP
        mainPresenter.onAttach(this);
        currentTheme = mainPresenter.getSettings(this);
        // Установка темы
        setCalculatorTheme(currentTheme);
        // Установка макета MainActivity
        setContentView(R.layout.calc_keyboard_layout);
        // Инициализация или перестройка текстовых полей
        initTextFields();
        // Инициализация кнопок
        initButtons();
        // Проверка режима ввода вещественных чисел
        buttonZapitayChange();
        // Установка обновлённого максимального значения высоты текстового поля с историей ввода
        // (_input_history и _input_history_night)
        setNewMaxHeightForInputHistory();
        // Установка обновлённых значений размеров элементов сцены
        setNewSizesElements(Math.round(
            curRadiusButtons * this.getResources().getDisplayMetrics().density));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDetach();
    }

    // Установка новых значений высоты для полей _input_history и _input_history_night
    // (важно при повороте экрана)
    private void setNewMaxHeightForInputHistory()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int newMaxHeightDp = Math.round(convertPixelsToDp(getApplicationContext(),
            Math.round(height / KOEFF_RESIZE_HEIGHT)));

        // Смена значения поля в constraintLayout
        ConstraintLayout constraintLayout = findViewById(R.id.run_calculator);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.constrainMaxHeight(R.id.input_history, newMaxHeightDp);
        constraintSet.applyTo(constraintLayout);
    }

    // Конвертирование px в dp
    private float convertPixelsToDp(Context context, float pixels) {
        return pixels / context.getResources().getDisplayMetrics().density;
    }

    // Установка темы калькулятора
    private void setCalculatorTheme(THEMES currentTheme) {
        if (currentTheme == THEMES.DAY_THEME) {
            setTheme(R.style.Day);
        } else {
            setTheme(R.style.Night);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Установка темы
        THEMES savedTheme = mainPresenter.getSettings(this);
        if ((currentTheme != savedTheme) || (mainPresenter.doChangeRadius)) {
            currentTheme = savedTheme;
            mainPresenter.doChangeRadius = false;
            mainPresenter.saveSettings(currentTheme, this);
            recreate();
        }
    }

    @Override
    public void setInputtedHistoryText(String newText) {
        inputtedHistoryText.setText(newText);
    }

    @Override
    public void setOutputResultText(String newText) {
        outputResultText.setText(newText);
    }

    @Override
    public void showErrorInString(ERRORS_IN_STRING error) {
        // Отобразить информацию о текущих ошибках
        switch (error) {
            case BRACKET_DISBALANCE:
                showErrorMessage(getResources().
                    getString(R.string.error_different_number_brackets));
                break;
            case SQRT_MINUS:
                showErrorMessage(getResources().
                    getString(R.string.error_undersquare_low_zero));
                break;
            case ZERO_DIVIDE:
                showErrorMessage(getResources().
                    getString(R.string.error_divide_on_zero));
                break;
            case BRACKETS_EMPTY:
                showErrorMessage(getResources().
                    getString(R.string.error_inside_brackets_empty));
                break;
            default:
                break;
        }
    }

    @Override
    public void showErrorInputting(ERRORS_INPUTTING error) {
        // Отобразить информацию о текущих ошибках
        switch (error) {
            case NUMBER_AFTER_BRACKET:
                showErrorMessage(getResources().
                        getString(R.string.error_number_after_bracket));
                break;
            case MANY_ZERO_IN_INTEGER_PART:
                showErrorMessage(getResources().
                        getString(R.string.error_many_zero_in_integer_part));
                break;
            case INPUT_NUMBER_FIRST:
                showErrorMessage(getResources().
                        getString(R.string.error_input_number_first));
                break;
            case PERCENT_NEEDS_TWO_NUMBERS:
                showErrorMessage(getResources().
                        getString(R.string.error_needs_two_numbers));
                break;
            case CHANGE_SIGN_EMPTY:
                showErrorMessage(getResources().
                        getString(R.string.error_change_sign_empty));
                break;
            case OPEN_BRACKET_ON_EMPTY_ACTION:
                showErrorMessage(getResources().
                        getString(R.string.error_open_bracket_on_empty_action));
                break;
            case CLOSE_BRACKET_ON_EMPTY_OPEN_BRACKET:
                showErrorMessage(getResources().
                        getString(R.string.error_close_bracket_on_empty_open_bracket));
                break;
            case CLOSE_BRACKET_ON_EMPTY:
                showErrorMessage(getResources().
                        getString(R.string.error_close_bracket_on_empty));
                break;
            case CLOSE_BRACKET_ON_ACTION_WITHOUT_NUMBER:
                showErrorMessage(getResources().
                        getString(R.string.error_close_bracket_on_action_without_number));
                break;
            case MULTIPLE_PERCENT_IN_BRACKET:
                showErrorMessage(getResources().
                        getString(R.string.error_multiple_percent_in_bracket));
                break;
            default:
                break;
        }
    }

    private void showErrorMessage(String message) {
        new ErrorMessagesFragment().newInstance(message).
                show(getSupportFragmentManager(), ERROR_MESSAGE_FRAGMENT_KEY);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonZero.getId()) {
            mainPresenter.addNumeral(0);
        } else if (v.getId() == buttonOne.getId()) {
            mainPresenter.addNumeral(1);
        } else if (v.getId() == buttonTwo.getId()) {
            mainPresenter.addNumeral(2);
        } else if (v.getId() == buttonThree.getId()) {
            mainPresenter.addNumeral(3);
        } else if (v.getId() == buttonFour.getId()) {
            mainPresenter.addNumeral(4);
        } else if (v.getId() == buttonFive.getId()) {
            mainPresenter.addNumeral(5);
        } else if (v.getId() == buttonSix.getId()) {
            mainPresenter.addNumeral(6);
        } else if (v.getId() == buttonSeven.getId()) {
            mainPresenter.addNumeral(7);
        } else if (v.getId() == buttonEight.getId()) {
            mainPresenter.addNumeral(8);
        } else if (v.getId() == buttonNine.getId()) {
            mainPresenter.addNumeral(9);
        } else if (v.getId() == buttonEqual.getId()) {
            mainPresenter.setEqual();
        } else if (v.getId() == buttonZapitay.getId()) {
            mainPresenter.setCurZapitay();
        } else if (v.getId() == buttonBracketOpen.getId()) {
            mainPresenter.setBracketOpen();
        } else if (v.getId() == buttonBracketClose.getId()) {
            mainPresenter.setBracketClose();
        } else if (v.getId() == buttonBackspace.getId()) {
            mainPresenter.clearAll();
        } else if (v.getId() == buttonBackspaceOne.getId()) {
            mainPresenter.clearOne();
        } else if (v.getId() == buttonBackspaceTwo.getId()) {
            mainPresenter.clearTwo();
        } else if (v.getId() == buttonDivide.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_DIV);
        } else if (v.getId() == buttonMinus.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_MINUS);
        } else if (v.getId() == buttonMultiply.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_MULTY);
        } else if (v.getId() == buttonPlus.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_PLUS);
        } else if (v.getId() == buttonPercent.getId()) {
            // Задаётся универсальное значение ACT_PERS_MULTY и оно уточняется в методе setNewAction
            mainPresenter.setNewAction(ACTIONS.ACT_PERS_MULTY);
        } else if (v.getId() == buttonPlusMinus.getId()) {
            mainPresenter.changeSign();
        } else if (v.getId() == buttonStepen.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_STEP);
        } else if (v.getId() == buttonSqrt.getId()) {
            mainPresenter.setNewFunction(FUNCTIONS.FUNC_SQRT);
        } else if (v.getId() == buttonChangeTheme.getId()) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        buttonZapitayChange();
    }

    // Инициализаци текстовых полей
    private void initTextFields() {
        if ((displayWidth >= BORDER_WIDTH) &&
            (curRadiusButtons >= DEFAULT_BUTTON_BORDER_RADIUS)) {
            // Инициализация текстовых полей
            outputResultText = findViewById(R.id.result);
            inputtedHistoryText = findViewById(R.id.inputted_history_text);
            // Отображение и скрытие контейнеров
            findViewById(R.id.input_history).setVisibility(View.VISIBLE);
            findViewById(R.id.input_history_small).setVisibility(View.GONE);
            findViewById(R.id.inputted_history_text_small).setVisibility(View.GONE);
            findViewById(R.id.result_small).setVisibility(View.GONE);
        } else {
            // Инициализация текстовых полей
            outputResultText = findViewById(R.id.result_small);
            inputtedHistoryText = findViewById(R.id.inputted_history_text_small);
            // Отображение и скрытие контейнеров
            findViewById(R.id.input_history).setVisibility(View.INVISIBLE);
            findViewById(R.id.input_history_small).setVisibility(View.VISIBLE);
            findViewById(R.id.inputted_history_text).setVisibility(View.INVISIBLE);
            findViewById(R.id.result).setVisibility(View.INVISIBLE);
        }
        // Показать текущие текстовые поля
        outputResultText.setVisibility(View.VISIBLE);
        inputtedHistoryText.setVisibility(View.VISIBLE);
        // Получение текущей ошибки, если она есть
        mainPresenter.getError();
        // Получение текущего результирующего значения, если оно есть
        mainPresenter.getInit();
    }

    // Инициализация кнопок
    private void initButtons() {
        // Установка кнопок с числами в дневном режиме
        buttonZero = findViewById(R.id.zero);
        buttonOne = findViewById(R.id.one);
        buttonTwo = findViewById(R.id.two);
        buttonThree = findViewById(R.id.three);
        buttonFour = findViewById(R.id.four);
        buttonFive = findViewById(R.id.five);
        buttonSix = findViewById(R.id.six);
        buttonSeven = findViewById(R.id.seven);
        buttonEight = findViewById(R.id.eight);
        buttonNine = findViewById(R.id.nine);

        // Установка кнопок с действиями в дневном режиме
        buttonEqual = findViewById(R.id.equal);
        buttonZapitay = findViewById(R.id.zapitay);
        buttonBracketClose = findViewById(R.id.bracket_close);
        buttonBackspace = findViewById(R.id.backspace);
        buttonBackspaceOne = findViewById(R.id.backspace_one);
        buttonBackspaceTwo = findViewById(R.id.backspace_two);
        buttonBracketOpen = findViewById(R.id.bracket_open);
        buttonDivide = findViewById(R.id.divide);
        buttonMinus = findViewById(R.id.minus);
        buttonMultiply = findViewById(R.id.multiply);
        buttonPercent = findViewById(R.id.percent);
        buttonPlus = findViewById(R.id.plus);
        buttonPlusMinus = findViewById(R.id.plus_minus);
        buttonSqrt = findViewById(R.id.sqrt);
        buttonStepen = findViewById(R.id.stepen);
        buttonChangeTheme = findViewById(R.id.menu_theme);

        // Установка слушателей событий на кнопки
        buttonsNumbersGroups.addAll(Arrays.asList(buttonZero, buttonOne, buttonTwo, buttonThree,
            buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine));
        buttonsNumbersGroups.forEach( button -> {
            button.setOnClickListener(this);
        });
        buttonsActionsGroups.addAll(Arrays.asList(buttonEqual, buttonZapitay,
            buttonBracketClose, buttonBackspace, buttonBackspaceOne, buttonBackspaceTwo,
            buttonBracketOpen, buttonDivide, buttonMinus, buttonMultiply, buttonPercent,
            buttonPlus, buttonPlusMinus, buttonSqrt, buttonStepen));
        buttonsActionsGroups.forEach( button -> {
            button.setOnClickListener(this);
        });
        buttonChangeTheme.setOnClickListener(this);
    }

    // Отобразить индикатор ввода вещественного числа
    private void buttonZapitayChange() {
        if (!mainPresenter.getPressedZapitay()) {
            if (currentTheme == THEMES.NIGHT_THEME) {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_night);
            } else {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_day);
            }
        } else {
            if (currentTheme == THEMES.NIGHT_THEME) {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_night);
            } else {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_day);
            }
        }
    }

    private void setNewSizesElements(int newRadius) {
        ConstraintLayout constraintLayout = findViewById(R.id.run_calculator);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        int buttonsNumbersWidth = (int) getResources().getDimension(
                R.dimen.button_number_width_standard);
        int buttonsNumbersHeight = (int) getResources().getDimension(
                R.dimen.button_number_height_standard);
        int buttonsActionsWidth = (int) getResources().getDimension(
                R.dimen.button_action_width_standard);
        int buttonsActionsHeight = (int) getResources().getDimension(
                R.dimen.button_action_height_standard);
        // Задание новых размеров для кнопок
        if ((displayWidth < BORDER_WIDTH) ||
            (curRadiusButtons < DEFAULT_BUTTON_BORDER_RADIUS)) {
            buttonsNumbersWidth = (int) getResources().getDimension(
                R.dimen.button_number_width_small);
            buttonsNumbersHeight = (int) getResources().getDimension(
                R.dimen.button_number_height_small);
            buttonsActionsWidth = (int) getResources().getDimension(
                R.dimen.button_action_width_small);
            buttonsActionsHeight = (int) getResources().getDimension(
                R.dimen.button_action_height_small);
        }

        // Задание нового радиуса кнопок с цифрами и их новых размеров
        int curAngle = 0;
        for (Button button: buttonsNumbersGroups) {
            constraintSet.constrainCircle(button.getId(), R.id.result, newRadius, curAngle);
            curAngle += DEFAULT_BUTTONS_DELTA_ANGLE;
            constraintSet.constrainWidth(button.getId(), buttonsNumbersWidth);
            constraintSet.constrainHeight(button.getId(), buttonsNumbersHeight);
        }
        // Задание нового радиуса кнопок с действиями
        for (Button button: buttonsActionsGroups) {
            constraintSet.constrainWidth(button.getId(), buttonsActionsWidth);
            constraintSet.constrainHeight(button.getId(), buttonsActionsHeight);
        }
        constraintSet.applyTo(constraintLayout);
    }

    // Отслеживание первичного запуска или перезапуска активити
    private void firstRunDetection(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            // Восстановление класса mainPresenter после поворота экрана
            mainPresenter = (MainPresenter) savedInstanceState.getSerializable(MAIN_PRESENTER_KEY);
    }
}