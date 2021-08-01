package ru.geekbrains.lessions2345.calculator.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.calculator_logic.CalcLogic;
import ru.geekbrains.lessions2345.calculator.calculator_logic.Constants;

public class CalculatorKeyboardActivity extends Activity implements View.OnClickListener, Constants {
    private TextView outputResultText;
    private TextView inputedHistoryText;
    private CalcLogic calcLogic;
    Button button_0;
    Button button_1;
    Button button_2;
    Button button_3;
    Button button_4;
    Button button_5;
    Button button_6;
    Button button_7;
    Button button_8;
    Button button_9;
    Button button_equal;
    Button button_zapitay;
    Button button_bracket_close;
    Button button_backspace;
    Button button_backspace_one;
    Button button_backspace_two;
    Button button_bracket_open;
    Button button_divide;
    Button button_minus;
    Button button_multiply;
    Button button_percent;
    Button button_plus;
    Button button_plus_minus;
    Button button_sqrt;
    Button button_stepen;
    Button button_change_theme;

    static final String KEY_CURRENT_THEME = "CurrentTheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Night); // Переключение на ночную тему приложения
                                 // Сделаю переключение между темами в следующем домашнем задании
        setContentView(R.layout.calc_keyboard_layout);

        // Восстановление класса сalcLogic после поворота экрана
        calcLogic = (CalcLogic) getLastNonConfigurationInstance();
        if (calcLogic == null) {
            calcLogic = new CalcLogic();
        }
        // Инициализация текстовых полей
        initTextFields();
        // Инициализация кнопок
        initButtons();

        // Вывод сообщения приветстия
        if (savedInstanceState == null) {
            Toast.makeText(this, "Разработчик научного калькулятора Хахалин Андрей Владимирович, 2021 г.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Сохранение класса calcLogic перед поворотом экрана
        onRetainNonConfigurationInstance();
    }

    // Метод для сохранения ссылки на класс calcLogic при повороте экрана
    public Object onRetainNonConfigurationInstance() {
        return calcLogic;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_0.getId())
        {
            calcLogic.addNumeral(0);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_1.getId())
        {
            calcLogic.addNumeral(1);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_2.getId())
        {
            calcLogic.addNumeral(2);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_3.getId())
        {
            calcLogic.addNumeral(3);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_4.getId())
        {
            calcLogic.addNumeral(4);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_5.getId())
        {
            calcLogic.addNumeral(5);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_6.getId())
        {
            calcLogic.addNumeral(6);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_7.getId())
        {
            calcLogic.addNumeral(7);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_8.getId())
        {
            calcLogic.addNumeral(8);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_9.getId())
        {
            calcLogic.addNumeral(9);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_equal.getId())
        {
            calcLogic.calculate();
            errorInfo();
            outputResultText.setText(calcLogic.getFinalResult(getApplicationContext()));
        }
        else if (v.getId() == button_zapitay.getId())
        {
            calcLogic.setCurZapitay();
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_bracket_open.getId())
        {
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.setNewFunction(FUNCTIONS.FUNC_NO)));
        }
        else if (v.getId() == button_bracket_close.getId())
        {
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.closeBracket()));
        }
        else if (v.getId() == button_backspace.getId())
        {
            calcLogic.clearAll();
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
            calcLogic.calculate();
            errorInfo();
            outputResultText.setText(calcLogic.getFinalResult(getApplicationContext()));
        }
        else if (v.getId() == button_backspace_one.getId())
        {
            if (calcLogic.clearOne() == false) {
                calcLogic.calculate();
                errorInfo();
                outputResultText.setText(calcLogic.getFinalResult(getApplicationContext()));
            }
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_backspace_two.getId())
        {
            if (calcLogic.clearTwo() == false) {
                calcLogic.calculate();
                errorInfo();
                outputResultText.setText(calcLogic.getFinalResult(getApplicationContext()));
            }
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_divide.getId())
        {
            calcLogic.setNewAction(ACTIONS.ACT_DIV);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_minus.getId())
        {
            calcLogic.setNewAction(ACTIONS.ACT_MINUS);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_multiply.getId())
        {
            calcLogic.setNewAction(ACTIONS.ACT_MULTY);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_plus.getId())
        {
            calcLogic.setNewAction(ACTIONS.ACT_PLUS);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_percent.getId())
        {
            calcLogic.setNewAction(ACTIONS.ACT_PERS_MULTY); // Задаётся универсальное значение ACT_PERS_MULTY и оно уточняется в методе setNewAction
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_plus_minus.getId())
        {
            calcLogic.changeSign();
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_stepen.getId())
        {
            calcLogic.setNewAction(ACTIONS.ACT_STEP);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_sqrt.getId())
        {
            calcLogic.setNewFunction(FUNCTIONS.FUNC_SQRT);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
        }
        else if (v.getId() == button_change_theme.getId())
        {

            setContentView(R.layout.menu_layout);
//            Toast.makeText(this, "Здесь будет реализовано меню управления темой калькулятора", Toast.LENGTH_SHORT).show();
        }
        buttonZapitayChange();
    }

    private void initTextFields() {
//        if (getTheme().equals(R.style.Night) == true)
//        {
            outputResultText = findViewById(R.id._RESULT_night);
            calcLogic.calculate();
            errorInfo();
            outputResultText.setText(calcLogic.getFinalResult(getApplicationContext()));
            inputedHistoryText = findViewById(R.id._inputed_history_text_night);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));

            // Show night text
            outputResultText.setVisibility(View.VISIBLE);
            inputedHistoryText.setVisibility(View.VISIBLE);

            // Hide day text
            findViewById(R.id._RESULT).setVisibility(View.INVISIBLE);
            findViewById(R.id._inputed_history_text).setVisibility(View.INVISIBLE);
//        }
//        else
//        {
/*
            outputResultText = findViewById(R.id._RESULT);
            calcLogic.calculate();
            errorInfo();
            outputResultText.setText(calcLogic.getFinalResult(getApplicationContext()));
            inputedHistoryText = findViewById(R.id._inputed_history_text);
            inputedHistoryText.setText(String.format(Locale.getDefault(), "%s", calcLogic.createOutput()));
*/
//        }
    }

    private void initButtons() {
        // Numbers
//        if (getTheme().equals(R.style.Night) == true)
//        {
            button_0 = findViewById(R.id._0_night);
            button_0.setOnClickListener(this);
            button_0.setVisibility(View.VISIBLE);
            button_1 = findViewById(R.id._1_night);
            button_1.setOnClickListener(this);
            button_2 = findViewById(R.id._2_night);
            button_2.setOnClickListener(this);
            button_3 = findViewById(R.id._3_night);
            button_3.setOnClickListener(this);
            button_4 = findViewById(R.id._4_night);
            button_4.setOnClickListener(this);
            button_5 = findViewById(R.id._5_night);
            button_5.setOnClickListener(this);
            button_6 = findViewById(R.id._6_night);
            button_6.setOnClickListener(this);
            button_7 = findViewById(R.id._7_night);
            button_7.setOnClickListener(this);
            button_8 = findViewById(R.id._8_night);
            button_8.setOnClickListener(this);
            button_9 = findViewById(R.id._9_night);
            button_9.setOnClickListener(this);

            // Actions
            button_equal = findViewById(R.id._equal_night);
            button_equal.setOnClickListener(this);
            button_zapitay = findViewById(R.id._zapitay_night);
            button_zapitay.setOnClickListener(this);
            button_bracket_close = findViewById(R.id._bracket_close_night);
            button_bracket_close.setOnClickListener(this);
            button_backspace = findViewById(R.id._backspace_night);
            button_backspace.setOnClickListener(this);
            button_backspace_one = findViewById(R.id._backspace_one_night);
            button_backspace_one.setOnClickListener(this);
            button_backspace_two = findViewById(R.id._backspace_two_night);
            button_backspace_two.setOnClickListener(this);
            button_bracket_open = findViewById(R.id._bracket_open_night);
            button_bracket_open.setOnClickListener(this);
            button_divide = findViewById(R.id._divide_night);
            button_divide.setOnClickListener(this);
            button_minus = findViewById(R.id._minus_night);
            button_minus.setOnClickListener(this);
            button_multiply = findViewById(R.id._multiply_night);
            button_multiply.setOnClickListener(this);
            button_percent = findViewById(R.id._percent_night);
            button_percent.setOnClickListener(this);
            button_plus = findViewById(R.id._plus_night);
            button_plus.setOnClickListener(this);
            button_plus_minus = findViewById(R.id._plus_minus_night);
            button_plus_minus.setOnClickListener(this);
            button_sqrt = findViewById(R.id._sqrt_night);
            button_sqrt.setOnClickListener(this);
            button_stepen = findViewById(R.id._stepen_night);
            button_stepen.setOnClickListener(this);
            button_change_theme = findViewById(R.id._menu_theme_night);
            button_change_theme.setOnClickListener(this);

            // Show night buttons
            findViewById(R.id._0_night).setVisibility(View.VISIBLE);
            findViewById(R.id._1_night).setVisibility(View.VISIBLE);
            findViewById(R.id._2_night).setVisibility(View.VISIBLE);
            findViewById(R.id._3_night).setVisibility(View.VISIBLE);
            findViewById(R.id._4_night).setVisibility(View.VISIBLE);
            findViewById(R.id._5_night).setVisibility(View.VISIBLE);
            findViewById(R.id._6_night).setVisibility(View.VISIBLE);
            findViewById(R.id._7_night).setVisibility(View.VISIBLE);
            findViewById(R.id._8_night).setVisibility(View.VISIBLE);
            findViewById(R.id._9_night).setVisibility(View.VISIBLE);
            findViewById(R.id._equal_night).setVisibility(View.VISIBLE);
            findViewById(R.id._zapitay_night).setVisibility(View.VISIBLE);
            findViewById(R.id._bracket_close_night).setVisibility(View.VISIBLE);
            findViewById(R.id._backspace_night).setVisibility(View.VISIBLE);
            findViewById(R.id._backspace_one_night).setVisibility(View.VISIBLE);
            findViewById(R.id._backspace_two_night).setVisibility(View.VISIBLE);
            findViewById(R.id._bracket_open_night).setVisibility(View.VISIBLE);
            findViewById(R.id._divide_night).setVisibility(View.VISIBLE);
            findViewById(R.id._minus_night).setVisibility(View.VISIBLE);
            findViewById(R.id._multiply_night).setVisibility(View.VISIBLE);
            findViewById(R.id._percent_night).setVisibility(View.VISIBLE);
            findViewById(R.id._plus_night).setVisibility(View.VISIBLE);
            findViewById(R.id._plus_minus_night).setVisibility(View.VISIBLE);
            findViewById(R.id._sqrt_night).setVisibility(View.VISIBLE);
            findViewById(R.id._stepen_night).setVisibility(View.VISIBLE);
            findViewById(R.id._menu_theme_night).setVisibility(View.VISIBLE);

            // Hide day buttons
            findViewById(R.id._0).setVisibility(View.INVISIBLE);
            findViewById(R.id._1).setVisibility(View.INVISIBLE);
            findViewById(R.id._2).setVisibility(View.INVISIBLE);
            findViewById(R.id._3).setVisibility(View.INVISIBLE);
            findViewById(R.id._4).setVisibility(View.INVISIBLE);
            findViewById(R.id._5).setVisibility(View.INVISIBLE);
            findViewById(R.id._6).setVisibility(View.INVISIBLE);
            findViewById(R.id._7).setVisibility(View.INVISIBLE);
            findViewById(R.id._8).setVisibility(View.INVISIBLE);
            findViewById(R.id._9).setVisibility(View.INVISIBLE);
            findViewById(R.id._equal).setVisibility(View.INVISIBLE);
            findViewById(R.id._zapitay).setVisibility(View.INVISIBLE);
            findViewById(R.id._bracket_close).setVisibility(View.INVISIBLE);
            findViewById(R.id._backspace).setVisibility(View.INVISIBLE);
            findViewById(R.id._backspace_one).setVisibility(View.INVISIBLE);
            findViewById(R.id._backspace_two).setVisibility(View.INVISIBLE);
            findViewById(R.id._bracket_open).setVisibility(View.INVISIBLE);
            findViewById(R.id._divide).setVisibility(View.INVISIBLE);
            findViewById(R.id._minus).setVisibility(View.INVISIBLE);
            findViewById(R.id._multiply).setVisibility(View.INVISIBLE);
            findViewById(R.id._percent).setVisibility(View.INVISIBLE);
            findViewById(R.id._plus).setVisibility(View.INVISIBLE);
            findViewById(R.id._plus_minus).setVisibility(View.INVISIBLE);
            findViewById(R.id._sqrt).setVisibility(View.INVISIBLE);
            findViewById(R.id._stepen).setVisibility(View.INVISIBLE);
            findViewById(R.id._menu_theme).setVisibility(View.INVISIBLE);
//        }
/*        else
        {
            button_0 = findViewById(R.id._0);
            button_0.setOnClickListener(this);
            button_1 = findViewById(R.id._1);
            button_1.setOnClickListener(this);
            button_2 = findViewById(R.id._2);
            button_2.setOnClickListener(this);
            button_3 = findViewById(R.id._3);
            button_3.setOnClickListener(this);
            button_4 = findViewById(R.id._4);
            button_4.setOnClickListener(this);
            button_5 = findViewById(R.id._5);
            button_5.setOnClickListener(this);
            button_6 = findViewById(R.id._6);
            button_6.setOnClickListener(this);
            button_7 = findViewById(R.id._7);
            button_7.setOnClickListener(this);
            button_8 = findViewById(R.id._8);
            button_8.setOnClickListener(this);
            button_9 = findViewById(R.id._9);
            button_9.setOnClickListener(this);

            // Actions
            button_equal = findViewById(R.id._equal);
            button_equal.setOnClickListener(this);
            button_zapitay = findViewById(R.id._zapitay);
            button_zapitay.setOnClickListener(this);
            button_bracket_close = findViewById(R.id._bracket_close);
            button_bracket_close.setOnClickListener(this);
            button_backspace = findViewById(R.id._backspace);
            button_backspace.setOnClickListener(this);
            button_backspace_one = findViewById(R.id._backspace_one);
            button_backspace_one.setOnClickListener(this);
            button_backspace_two = findViewById(R.id._backspace_two);
            button_backspace_two.setOnClickListener(this);
            button_bracket_open = findViewById(R.id._bracket_open);
            button_bracket_open.setOnClickListener(this);
            button_divide = findViewById(R.id._divide);
            button_divide.setOnClickListener(this);
            button_minus = findViewById(R.id._minus);
            button_minus.setOnClickListener(this);
            button_multiply = findViewById(R.id._multiply);
            button_multiply.setOnClickListener(this);
            button_percent = findViewById(R.id._percent);
            button_percent.setOnClickListener(this);
            button_plus = findViewById(R.id._plus);
            button_plus.setOnClickListener(this);
            button_plus_minus = findViewById(R.id._plus_minus);
            button_plus_minus.setOnClickListener(this);
            button_sqrt = findViewById(R.id._sqrt);
            button_sqrt.setOnClickListener(this);
            button_stepen = findViewById(R.id._stepen);
            button_stepen.setOnClickListener(this);
            button_change_theme = findViewById(R.id._menu_theme);
            button_change_theme.setOnClickListener(this);
        }*/
    }

    private void buttonZapitayChange() {
        if (calcLogic.getPressedZapitay() == false) {
            button_zapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_night);
//            button_zapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_day);
        } else {
            button_zapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_night);
//            button_zapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_day);
        }
    }

    private void errorInfo() {
        switch (calcLogic.getErrorCode()) {
            case BRACKET_DISBALANCE:
                Toast.makeText(this, "Ошибка: различное количество открытых и закрытых скобок.", Toast.LENGTH_SHORT).show();
                calcLogic.clearErrorCode();
                break;
            case SQRT_MINUS:
                Toast.makeText(this, "Ошибка: подкоренное выражение меньше нуля.", Toast.LENGTH_SHORT).show();
                calcLogic.clearErrorCode();
                break;
            case ZERO_DIVIDE:
                Toast.makeText(this, "Ошибка: деление на ноль.", Toast.LENGTH_SHORT).show();
                calcLogic.clearErrorCode();
                break;
            case BRACKETS_EMPTY:
                Toast.makeText(this, "Ошибка: внутри скобок ничего нет.", Toast.LENGTH_SHORT).show();
                calcLogic.clearErrorCode();
                break;
            default:
                break;
        }
    }
}
