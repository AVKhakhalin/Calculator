package ru.geekbrains.lessions2345.calculator;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import ru.geekbrains.lessions2345.calculator.view.ui_main.MainActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
// Устранение ошибки: Failed to create a Robolectric sandbox: Android SDK 30 requires Java 9 (have Java 8)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class MainActivityTest {

    /** Задание переменных */ //region
    // Создание сценария
    private ActivityScenario<MainActivity> scenario;
    // Создание контекста
    private Context context;
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    //endregion

    @Before // Предварительная установка
    public void setup() {
        // launch виртуально стартует активити
        // Это способ проверить базовый сценарий
        scenario = ActivityScenario.launch(MainActivity.class);
        // Получаем контекст
        context = ApplicationProvider.getApplicationContext();
    }

    /** Тестирование корректности загрузки MainActivity */ //region
    @Test
    // Проверка корректности создания класса MainActivity
    public void activity_AssertNotNull() {
        // Получаем объект MainActivity
        scenario.onActivity(Assert::assertNotNull);
    }
    @Test // Проверка прохождения MainActivity через состояние onResume()
    public void activity_IsResumed() {
        // В момент, когда тест запускается, активити находится
        // не в созданном состоянии, а в состоянии Resume
        assertEquals(Lifecycle.State.RESUMED, scenario.getState());
    }
    //endregion

    /** Проверка наличия элементов в макете */ //region
    @Test // Проверка наличия в макете MainActivity фона с id "_background_theme"
    public void activityBackgroundTheme_NotNull() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                ImageView backgroundTheme = (ImageView) activity.findViewById(R.id.background_theme);
                assertNotNull(backgroundTheme);
            }
        });
    }
    @Test // Проверка наличия в макете MainActivity элементов дневной темы
    public void activityDayElements_NotNull() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0 = (Button) activity.findViewById(R.id.zero);
                assertNotNull(button_0);
                Button button_1 = (Button) activity.findViewById(R.id.one);
                assertNotNull(button_1);
                Button button_2 = (Button) activity.findViewById(R.id.two);
                assertNotNull(button_2);
                Button button_3 = (Button) activity.findViewById(R.id.three);
                assertNotNull(button_3);
                Button button_4 = (Button) activity.findViewById(R.id.four);
                assertNotNull(button_4);
                Button button_5 = (Button) activity.findViewById(R.id.five);
                assertNotNull(button_5);
                Button button_6 = (Button) activity.findViewById(R.id.six);
                assertNotNull(button_6);
                Button button_7 = (Button) activity.findViewById(R.id.seven);
                assertNotNull(button_7);
                Button button_8 = (Button) activity.findViewById(R.id.eight);
                assertNotNull(button_8);
                Button button_9 = (Button) activity.findViewById(R.id.nine);
                assertNotNull(button_9);
                Button divide = (Button) activity.findViewById(R.id.divide);
                assertNotNull(divide);
                Button minus = (Button) activity.findViewById(R.id.minus);
                assertNotNull(minus);
                Button zapitay = (Button) activity.findViewById(R.id.zapitay);
                assertNotNull(zapitay);
                Button multiply = (Button) activity.findViewById(R.id.multiply);
                assertNotNull(multiply);
                Button plus = (Button) activity.findViewById(R.id.plus);
                assertNotNull(plus);
                Button bracketOpen = (Button) activity.findViewById(R.id.bracket_open);
                assertNotNull(bracketOpen);
                Button bracketClose = (Button) activity.findViewById(R.id.bracket_close);
                assertNotNull(bracketClose);
                Button percent = (Button) activity.findViewById(R.id.percent);
                assertNotNull(percent);
                Button plusMinus = (Button) activity.findViewById(R.id.plus_minus);
                assertNotNull(plusMinus);
                Button stepen = (Button) activity.findViewById(R.id.stepen);
                assertNotNull(stepen);
                Button sqrt = (Button) activity.findViewById(R.id.sqrt);
                assertNotNull(sqrt);
                Button equal = (Button) activity.findViewById(R.id.equal);
                assertNotNull(equal);
                Button backspaceOne = (Button) activity.findViewById(R.id.backspace_one);
                assertNotNull(backspaceOne);
                Button backspaceTwo = (Button) activity.findViewById(R.id.backspace_two);
                assertNotNull(backspaceTwo);
                Button backspace = (Button) activity.findViewById(R.id.backspace);
                assertNotNull(backspace);
                Button menuTheme = (Button) activity.findViewById(R.id.menu_theme);
                assertNotNull(menuTheme);
                TextView result = (TextView) activity.findViewById(R.id.result);
                assertNotNull(result);
                NestedScrollView inputHistory =
                        (NestedScrollView) activity.findViewById(R.id.input_history);
                assertNotNull(inputHistory);
                TextView inputedHistoryText =
                        (TextView) activity.findViewById(R.id.inputed_history_text);
                assertNotNull(inputedHistoryText);
            }
        });
    }
    @Test // Проверка наличия в макете MainActivity элементов ночной темы
    public void activityNightElements_NotNull() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0_night = (Button) activity.findViewById(R.id.zero_night);
                assertNotNull(button_0_night);
                Button button_1_night = (Button) activity.findViewById(R.id.one_night);
                assertNotNull(button_1_night);
                Button button_2_night = (Button) activity.findViewById(R.id.two_night);
                assertNotNull(button_2_night);
                Button button_3_night = (Button) activity.findViewById(R.id.three_night);
                assertNotNull(button_3_night);
                Button button_4_night = (Button) activity.findViewById(R.id.four_night);
                assertNotNull(button_4_night);
                Button button_5_night = (Button) activity.findViewById(R.id.five_night);
                assertNotNull(button_5_night);
                Button button_6_night = (Button) activity.findViewById(R.id.six_night);
                assertNotNull(button_6_night);
                Button button_7_night = (Button) activity.findViewById(R.id.seven_night);
                assertNotNull(button_7_night);
                Button button_8_night = (Button) activity.findViewById(R.id.eight_night);
                assertNotNull(button_8_night);
                Button button_9_night = (Button) activity.findViewById(R.id.nine_night);
                assertNotNull(button_9_night);
                Button divide_night = (Button) activity.findViewById(R.id.divide_night);
                assertNotNull(divide_night);
                Button minus_night = (Button) activity.findViewById(R.id.minus_night);
                assertNotNull(minus_night);
                Button zapitay_night = (Button) activity.findViewById(R.id.zapitay_night);
                assertNotNull(zapitay_night);
                Button multiply_night = (Button) activity.findViewById(R.id.multiply_night);
                assertNotNull(multiply_night);
                Button plus_night = (Button) activity.findViewById(R.id.plus_night);
                assertNotNull(plus_night);
                Button bracketOpen_night =
                        (Button) activity.findViewById(R.id.bracket_open_night);
                assertNotNull(bracketOpen_night);
                Button bracketClose_night =
                        (Button) activity.findViewById(R.id.bracket_close_night);
                assertNotNull(bracketClose_night);
                Button percent_night = (Button) activity.findViewById(R.id.percent_night);
                assertNotNull(percent_night);
                Button plusMinus_night = (Button) activity.findViewById(R.id.plus_minus_night);
                assertNotNull(plusMinus_night);
                Button stepen_night = (Button) activity.findViewById(R.id.stepen_night);
                assertNotNull(stepen_night);
                Button sqrt_night = (Button) activity.findViewById(R.id.sqrt_night);
                assertNotNull(sqrt_night);
                Button equal_night = (Button) activity.findViewById(R.id.equal_night);
                assertNotNull(equal_night);
                Button backspaceOne_night =
                        (Button) activity.findViewById(R.id.backspace_one_night);
                assertNotNull(backspaceOne_night);
                Button backspaceTwo_night =
                        (Button) activity.findViewById(R.id.backspace_two_night);
                assertNotNull(backspaceTwo_night);
                Button backspace_night = (Button) activity.findViewById(R.id.backspace_night);
                assertNotNull(backspace_night);
                Button menuTheme_night = (Button) activity.findViewById(R.id.menu_theme_night);
                assertNotNull(menuTheme_night);
                TextView result_night = (TextView) activity.findViewById(R.id._result_night);
                assertNotNull(result_night);
                NestedScrollView inputHistory_night =
                        (NestedScrollView) activity.findViewById(R.id.input_history_night);
                assertNotNull(inputHistory_night);
                TextView inputedHistoryText_night =
                        (TextView) activity.findViewById(R.id.inputed_history_text_night);
                assertNotNull(inputedHistoryText_night);
            }
        });
    }
    //endregion

    /** Проверка наличия корректного текста в элементах */ //region
    @Test // Проверка наличия текста в поле text для элементов дневной темы
    public void activityDayElements_HasText() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0 = (Button) activity.findViewById(R.id.zero);
                assertEquals("0", button_0.getText());
                Button button_1 = (Button) activity.findViewById(R.id.one);
                assertEquals("1", button_1.getText());
                Button button_2 = (Button) activity.findViewById(R.id.two);
                assertEquals("2", button_2.getText());
                Button button_3 = (Button) activity.findViewById(R.id.three);
                assertEquals("3", button_3.getText());
                Button button_4 = (Button) activity.findViewById(R.id.four);
                assertEquals("4", button_4.getText());
                Button button_5 = (Button) activity.findViewById(R.id.five);
                assertEquals("5", button_5.getText());
                Button button_6 = (Button) activity.findViewById(R.id.six);
                assertEquals("6", button_6.getText());
                Button button_7 = (Button) activity.findViewById(R.id.seven);
                assertEquals("7", button_7.getText());
                Button button_8 = (Button) activity.findViewById(R.id.eight);
                assertEquals("8", button_8.getText());
                Button button_9 = (Button) activity.findViewById(R.id.nine);
                assertEquals("9", button_9.getText());
                Button divide = (Button) activity.findViewById(R.id.divide);
                assertEquals("/", divide.getText());
                Button minus = (Button) activity.findViewById(R.id.minus);
                assertEquals("-", minus.getText());
                Button zapitay = (Button) activity.findViewById(R.id.zapitay);
                assertEquals(",", zapitay.getText());
                Button multiply = (Button) activity.findViewById(R.id.multiply);
                assertEquals("*", multiply.getText());
                Button plus = (Button) activity.findViewById(R.id.plus);
                assertEquals("+", plus.getText());
                Button bracketOpen = (Button) activity.findViewById(R.id.bracket_open);
                assertEquals("(", bracketOpen.getText());
                Button bracketClose = (Button) activity.findViewById(R.id.bracket_close);
                assertEquals(")", bracketClose.getText());
                Button percent = (Button) activity.findViewById(R.id.percent);
                assertEquals("%", percent.getText());
                Button plusMinus = (Button) activity.findViewById(R.id.plus_minus);
                assertEquals("+/-", plusMinus.getText());
                Button stepen = (Button) activity.findViewById(R.id.stepen);
                assertEquals("x^n", stepen.getText());
                Button sqrt = (Button) activity.findViewById(R.id.sqrt);
                assertEquals("Kx", sqrt.getText());
                Button equal = (Button) activity.findViewById(R.id.equal);
                assertEquals("=", equal.getText());
                Button backspaceOne = (Button) activity.findViewById(R.id.backspace_one);
                assertEquals("<-", backspaceOne.getText());
                Button backspaceTwo = (Button) activity.findViewById(R.id.backspace_two);
                assertEquals("<--", backspaceTwo.getText());
                Button backspace = (Button) activity.findViewById(R.id.backspace);
                assertEquals("C", backspace.getText());
                Button menuTheme = (Button) activity.findViewById(R.id.menu_theme);
                assertEquals("Настройки", menuTheme.getText());
                TextView result = (TextView) activity.findViewById(R.id.result);
                assertEquals("0", result.getText());
                TextView inputedHistoryText =
                        (TextView) activity.findViewById(R.id.inputed_history_text);
                assertEquals("", inputedHistoryText.getText());
            }
        });
    }
    @Test // Проверка наличия текста в поле text для элементов ночной темы
    public void activityNightElements_HasText() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0_night = (Button) activity.findViewById(R.id.zero_night);
                assertEquals("0", button_0_night.getText());
                Button button_1_night = (Button) activity.findViewById(R.id.one_night);
                assertEquals("1", button_1_night.getText());
                Button button_2_night = (Button) activity.findViewById(R.id.two_night);
                assertEquals("2", button_2_night.getText());
                Button button_3_night = (Button) activity.findViewById(R.id.three_night);
                assertEquals("3", button_3_night.getText());
                Button button_4_night = (Button) activity.findViewById(R.id.four_night);
                assertEquals("4", button_4_night.getText());
                Button button_5_night = (Button) activity.findViewById(R.id.five_night);
                assertEquals("5", button_5_night.getText());
                Button button_6_night = (Button) activity.findViewById(R.id.six_night);
                assertEquals("6", button_6_night.getText());
                Button button_7_night = (Button) activity.findViewById(R.id.seven_night);
                assertEquals("7", button_7_night.getText());
                Button button_8_night = (Button) activity.findViewById(R.id.eight_night);
                assertEquals("8", button_8_night.getText());
                Button button_9_night = (Button) activity.findViewById(R.id.nine_night);
                assertEquals("9", button_9_night.getText());
                Button divide_night = (Button) activity.findViewById(R.id.divide_night);
                assertEquals("/", divide_night.getText());
                Button minus_night = (Button) activity.findViewById(R.id.minus_night);
                assertEquals("-", minus_night.getText());
                Button zapitay_night = (Button) activity.findViewById(R.id.zapitay_night);
                assertEquals(",", zapitay_night.getText());
                Button multiply_night = (Button) activity.findViewById(R.id.multiply_night);
                assertEquals("*", multiply_night.getText());
                Button plus_night = (Button) activity.findViewById(R.id.plus_night);
                assertEquals("+", plus_night.getText());
                Button bracketOpen_night =
                        (Button) activity.findViewById(R.id.bracket_open_night);
                assertEquals("(", bracketOpen_night.getText());
                Button bracketClose_night =
                        (Button) activity.findViewById(R.id.bracket_close_night);
                assertEquals(")", bracketClose_night.getText());
                Button percent_night = (Button) activity.findViewById(R.id.percent_night);
                assertEquals("%", percent_night.getText());
                Button plusMinus_night = (Button) activity.findViewById(R.id.plus_minus_night);
                assertEquals("+/-", plusMinus_night.getText());
                Button stepen_night = (Button) activity.findViewById(R.id.stepen_night);
                assertEquals("x^n", stepen_night.getText());
                Button sqrt_night = (Button) activity.findViewById(R.id.sqrt_night);
                assertEquals("Kx", sqrt_night.getText());
                Button equal_night = (Button) activity.findViewById(R.id.equal_night);
                assertEquals("=", equal_night.getText());
                Button backspaceOne_night =
                        (Button) activity.findViewById(R.id.backspace_one_night);
                assertEquals("<-", backspaceOne_night.getText());
                Button backspaceTwo_night =
                        (Button) activity.findViewById(R.id.backspace_two_night);
                assertEquals("<--", backspaceTwo_night.getText());
                Button backspace_night = (Button) activity.findViewById(R.id.backspace_night);
                assertEquals("C", backspace_night.getText());
                Button menuTheme_night = (Button) activity.findViewById(R.id.menu_theme_night);
                assertEquals("Настройки", menuTheme_night.getText());
                TextView result_night = (TextView) activity.findViewById(R.id._result_night);
                assertEquals("0", result_night.getText());
                TextView inputedHistoryText_night =
                        (TextView) activity.findViewById(R.id.inputed_history_text_night);
                assertEquals("", inputedHistoryText_night.getText());
            }
        });
    }
    //endregion

    /** Проверка видимости элементов на макете MainActivity */ //region
    @Test // Проверка видимости элемента с фоном
    public void activityBackgroundTheme_IsVisible() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                ImageView backgroundTheme =
                        (ImageView) activity.findViewById(R.id.background_theme);
                assertEquals(View.VISIBLE, backgroundTheme.getVisibility());
            }
        });
    }
    @Test // Проверка видимости элементов дневной темы
    public void activityDayElements_AreVisible() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0 = (Button) activity.findViewById(R.id.zero);
                assertEquals(View.VISIBLE, button_0.getVisibility());
                Button button_1 = (Button) activity.findViewById(R.id.one);
                assertEquals(View.VISIBLE, button_1.getVisibility());
                Button button_2 = (Button) activity.findViewById(R.id.two);
                assertEquals(View.VISIBLE, button_2.getVisibility());
                Button button_3 = (Button) activity.findViewById(R.id.three);
                assertEquals(View.VISIBLE, button_3.getVisibility());
                Button button_4 = (Button) activity.findViewById(R.id.four);
                assertEquals(View.VISIBLE, button_4.getVisibility());
                Button button_5 = (Button) activity.findViewById(R.id.five);
                assertEquals(View.VISIBLE, button_5.getVisibility());
                Button button_6 = (Button) activity.findViewById(R.id.six);
                assertEquals(View.VISIBLE, button_6.getVisibility());
                Button button_7 = (Button) activity.findViewById(R.id.seven);
                assertEquals(View.VISIBLE, button_7.getVisibility());
                Button button_8 = (Button) activity.findViewById(R.id.eight);
                assertEquals(View.VISIBLE, button_8.getVisibility());
                Button button_9 = (Button) activity.findViewById(R.id.nine);
                assertEquals(View.VISIBLE, button_9.getVisibility());
                Button divide = (Button) activity.findViewById(R.id.divide);
                assertEquals(View.VISIBLE, divide.getVisibility());
                Button minus = (Button) activity.findViewById(R.id.minus);
                assertEquals(View.VISIBLE, minus.getVisibility());
                Button zapitay = (Button) activity.findViewById(R.id.zapitay);
                assertEquals(View.VISIBLE, zapitay.getVisibility());
                Button multiply = (Button) activity.findViewById(R.id.multiply);
                assertEquals(View.VISIBLE, multiply.getVisibility());
                Button plus = (Button) activity.findViewById(R.id.plus);
                assertEquals(View.VISIBLE, plus.getVisibility());
                Button bracketOpen = (Button) activity.findViewById(R.id.bracket_open);
                assertEquals(View.VISIBLE, bracketOpen.getVisibility());
                Button bracketClose = (Button) activity.findViewById(R.id.bracket_close);
                assertEquals(View.VISIBLE, bracketClose.getVisibility());
                Button percent = (Button) activity.findViewById(R.id.percent);
                assertEquals(View.VISIBLE, percent.getVisibility());
                Button plusMinus = (Button) activity.findViewById(R.id.plus_minus);
                assertEquals(View.VISIBLE, plusMinus.getVisibility());
                Button stepen = (Button) activity.findViewById(R.id.stepen);
                assertEquals(View.VISIBLE, stepen.getVisibility());
                Button sqrt = (Button) activity.findViewById(R.id.sqrt);
                assertEquals(View.VISIBLE, sqrt.getVisibility());
                Button equal = (Button) activity.findViewById(R.id.equal);
                assertEquals(View.VISIBLE, equal.getVisibility());
                Button backspaceOne = (Button) activity.findViewById(R.id.backspace_one);
                assertEquals(View.VISIBLE, backspaceOne.getVisibility());
                Button backspaceTwo = (Button) activity.findViewById(R.id.backspace_two);
                assertEquals(View.VISIBLE, backspaceTwo.getVisibility());
                Button backspace = (Button) activity.findViewById(R.id.backspace);
                assertEquals(View.VISIBLE, backspace.getVisibility());
                Button menuTheme = (Button) activity.findViewById(R.id.menu_theme);
                assertEquals(View.VISIBLE, menuTheme.getVisibility());
                TextView result = (TextView) activity.findViewById(R.id.result);
                assertEquals(View.VISIBLE, result.getVisibility());
                NestedScrollView inputHistory =
                        (NestedScrollView) activity.findViewById(R.id.input_history);
                assertEquals(View.VISIBLE, inputHistory.getVisibility());
                TextView inputedHistoryText =
                        (TextView) activity.findViewById(R.id.inputed_history_text);
                assertEquals(View.VISIBLE, inputedHistoryText.getVisibility());
            }
        });
    }
    @Test // Проверка видимости элементов ночной темы
    public void activityNightElements_AreVisible() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0_night = (Button) activity.findViewById(R.id.zero_night);
                assertEquals(View.GONE, button_0_night.getVisibility());
                Button button_1_night = (Button) activity.findViewById(R.id.one_night);
                assertEquals(View.GONE, button_1_night.getVisibility());
                Button button_2_night = (Button) activity.findViewById(R.id.two_night);
                assertEquals(View.GONE, button_2_night.getVisibility());
                Button button_3_night = (Button) activity.findViewById(R.id.three_night);
                assertEquals(View.GONE, button_3_night.getVisibility());
                Button button_4_night = (Button) activity.findViewById(R.id.four_night);
                assertEquals(View.GONE, button_4_night.getVisibility());
                Button button_5_night = (Button) activity.findViewById(R.id.five_night);
                assertEquals(View.GONE, button_5_night.getVisibility());
                Button button_6_night = (Button) activity.findViewById(R.id.six_night);
                assertEquals(View.GONE, button_6_night.getVisibility());
                Button button_7_night = (Button) activity.findViewById(R.id.seven_night);
                assertEquals(View.GONE, button_7_night.getVisibility());
                Button button_8_night = (Button) activity.findViewById(R.id.eight_night);
                assertEquals(View.GONE, button_8_night.getVisibility());
                Button button_9_night = (Button) activity.findViewById(R.id.nine_night);
                assertEquals(View.GONE, button_9_night.getVisibility());
                Button divide_night = (Button) activity.findViewById(R.id.divide_night);
                assertEquals(View.GONE, divide_night.getVisibility());
                Button minus_night = (Button) activity.findViewById(R.id.minus_night);
                assertEquals(View.GONE, minus_night.getVisibility());
                Button zapitay_night = (Button) activity.findViewById(R.id.zapitay_night);
                assertEquals(View.GONE, zapitay_night.getVisibility());
                Button multiply_night = (Button) activity.findViewById(R.id.multiply_night);
                assertEquals(View.GONE, multiply_night.getVisibility());
                Button plus_night = (Button) activity.findViewById(R.id.plus_night);
                assertEquals(View.GONE, plus_night.getVisibility());
                Button bracketOpen_night =
                        (Button) activity.findViewById(R.id.bracket_open_night);
                assertEquals(View.GONE, bracketOpen_night.getVisibility());
                Button bracketClose_night =
                        (Button) activity.findViewById(R.id.bracket_close_night);
                assertEquals(View.GONE, bracketClose_night.getVisibility());
                Button percent_night = (Button) activity.findViewById(R.id.percent_night);
                assertEquals(View.GONE, percent_night.getVisibility());
                Button plusMinus_night = (Button) activity.findViewById(R.id.plus_minus_night);
                assertEquals(View.GONE, plusMinus_night.getVisibility());
                Button stepen_night = (Button) activity.findViewById(R.id.stepen_night);
                assertEquals(View.GONE, stepen_night.getVisibility());
                Button sqrt_night = (Button) activity.findViewById(R.id.sqrt_night);
                assertEquals(View.GONE, sqrt_night.getVisibility());
                Button equal_night = (Button) activity.findViewById(R.id.equal_night);
                assertEquals(View.GONE, equal_night.getVisibility());
                Button backspaceOne_night =
                        (Button) activity.findViewById(R.id.backspace_one_night);
                assertEquals(View.GONE, backspaceOne_night.getVisibility());
                Button backspaceTwo_night =
                        (Button) activity.findViewById(R.id.backspace_two_night);
                assertEquals(View.GONE, backspaceTwo_night.getVisibility());
                Button backspace_night = (Button) activity.findViewById(R.id.backspace_night);
                assertEquals(View.GONE, backspace_night.getVisibility());
                Button menuTheme_night = (Button) activity.findViewById(R.id.menu_theme_night);
                assertEquals(View.GONE, menuTheme_night.getVisibility());
                TextView result_night = (TextView) activity.findViewById(R.id._result_night);
                assertEquals(View.GONE, result_night.getVisibility());
                NestedScrollView inputHistory_night =
                        (NestedScrollView) activity.findViewById(R.id.input_history_night);
                assertEquals(View.GONE, inputHistory_night.getVisibility());
                TextView inputedHistoryText_night =
                        (TextView) activity.findViewById(R.id.inputed_history_text_night);
                assertEquals(View.GONE, inputedHistoryText_night.getVisibility());
            }
        });
    }
    //endregion

    /** Проверка работоспособности элементов */ //region
    @Test // Проверка работоспособности элементов дневной темы
    public void activityDayElements_IsWorking() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0 = (Button) activity.findViewById(R.id.zero);
                assertTrue(button_0.performClick());
                Button button_1 = (Button) activity.findViewById(R.id.one);
                assertTrue(button_1.performClick());
                Button button_2 = (Button) activity.findViewById(R.id.two);
                assertTrue(button_2.performClick());
                Button button_3 = (Button) activity.findViewById(R.id.three);
                assertTrue(button_3.performClick());
                Button button_4 = (Button) activity.findViewById(R.id.four);
                assertTrue(button_4.performClick());
                Button button_5 = (Button) activity.findViewById(R.id.five);
                assertTrue(button_5.performClick());
                Button button_6 = (Button) activity.findViewById(R.id.six);
                assertTrue(button_6.performClick());
                Button button_7 = (Button) activity.findViewById(R.id.seven);
                assertTrue(button_7.performClick());
                Button button_8 = (Button) activity.findViewById(R.id.eight);
                assertTrue(button_8.performClick());
                Button button_9 = (Button) activity.findViewById(R.id.nine);
                assertTrue(button_9.performClick());
                Button divide = (Button) activity.findViewById(R.id.divide);
                assertTrue(divide.performClick());
                Button minus = (Button) activity.findViewById(R.id.minus);
                assertTrue(minus.performClick());
                Button zapitay = (Button) activity.findViewById(R.id.zapitay);
                assertTrue(zapitay.performClick());
                Button multiply = (Button) activity.findViewById(R.id.multiply);
                assertTrue(multiply.performClick());
                Button plus = (Button) activity.findViewById(R.id.plus);
                assertTrue(plus.performClick());
                Button bracketOpen = (Button) activity.findViewById(R.id.bracket_open);
                assertTrue(bracketOpen.performClick());
                Button bracketClose = (Button) activity.findViewById(R.id.bracket_close);
                assertTrue(bracketClose.performClick());
                Button percent = (Button) activity.findViewById(R.id.percent);
                assertTrue(percent.performClick());
                Button plusMinus = (Button) activity.findViewById(R.id.plus_minus);
                assertTrue(plusMinus.performClick());
                Button stepen = (Button) activity.findViewById(R.id.stepen);
                assertTrue(stepen.performClick());
                Button sqrt = (Button) activity.findViewById(R.id.sqrt);
                assertTrue(sqrt.performClick());
                Button equal = (Button) activity.findViewById(R.id.equal);
                assertTrue(equal.performClick());
                Button backspaceOne = (Button) activity.findViewById(R.id.backspace_one);
                assertTrue(backspaceOne.performClick());
                Button backspaceTwo = (Button) activity.findViewById(R.id.backspace_two);
                assertTrue(backspaceTwo.performClick());
                Button backspace = (Button) activity.findViewById(R.id.backspace);
                assertTrue(backspace.performClick());
                Button menuTheme = (Button) activity.findViewById(R.id.menu_theme);
                assertTrue(menuTheme.performClick());
                TextView result = (TextView) activity.findViewById(R.id.result);
                assertFalse(result.performClick());
                NestedScrollView inputHistory =
                        (NestedScrollView) activity.findViewById(R.id.input_history);
                assertFalse(inputHistory.performClick());
                TextView inputedHistoryText =
                        (TextView) activity.findViewById(R.id.inputed_history_text);
                assertFalse(inputedHistoryText.performClick());
            }
        });
    }
    @Test // Проверка работоспособности элементов ночной темы
    public void activityNightElements_IsWorking() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0_night = (Button) activity.findViewById(R.id.zero_night);
                assertFalse(button_0_night.performClick());
                Button button_1_night = (Button) activity.findViewById(R.id.one_night);
                assertFalse(button_1_night.performClick());
                Button button_2_night = (Button) activity.findViewById(R.id.two_night);
                assertFalse(button_2_night.performClick());
                Button button_3_night = (Button) activity.findViewById(R.id.three_night);
                assertFalse(button_3_night.performClick());
                Button button_4_night = (Button) activity.findViewById(R.id.four_night);
                assertFalse(button_4_night.performClick());
                Button button_5_night = (Button) activity.findViewById(R.id.five_night);
                assertFalse(button_5_night.performClick());
                Button button_6_night = (Button) activity.findViewById(R.id.six_night);
                assertFalse(button_6_night.performClick());
                Button button_7_night = (Button) activity.findViewById(R.id.seven_night);
                assertFalse(button_7_night.performClick());
                Button button_8_night = (Button) activity.findViewById(R.id.eight_night);
                assertFalse(button_8_night.performClick());
                Button button_9_night = (Button) activity.findViewById(R.id.nine_night);
                assertFalse(button_9_night.performClick());
                Button divide_night = (Button) activity.findViewById(R.id.divide_night);
                assertFalse(divide_night.performClick());
                Button minus_night = (Button) activity.findViewById(R.id.minus_night);
                assertFalse(minus_night.performClick());
                Button zapitay_night = (Button) activity.findViewById(R.id.zapitay_night);
                assertFalse(zapitay_night.performClick());
                Button multiply_night = (Button) activity.findViewById(R.id.multiply_night);
                assertFalse(multiply_night.performClick());
                Button plus_night = (Button) activity.findViewById(R.id.plus_night);
                assertFalse(plus_night.performClick());
                Button bracketOpen_night =
                        (Button) activity.findViewById(R.id.bracket_open_night);
                assertFalse(bracketOpen_night.performClick());
                Button bracketClose_night =
                        (Button) activity.findViewById(R.id.bracket_close_night);
                assertFalse(bracketClose_night.performClick());
                Button percent_night = (Button) activity.findViewById(R.id.percent_night);
                assertFalse(percent_night.performClick());
                Button plusMinus_night = (Button) activity.findViewById(R.id.plus_minus_night);
                assertFalse(plusMinus_night.performClick());
                Button stepen_night = (Button) activity.findViewById(R.id.stepen_night);
                assertFalse(stepen_night.performClick());
                Button sqrt_night = (Button) activity.findViewById(R.id.sqrt_night);
                assertFalse(sqrt_night.performClick());
                Button equal_night = (Button) activity.findViewById(R.id.equal_night);
                assertFalse(equal_night.performClick());
                Button backspaceOne_night =
                        (Button) activity.findViewById(R.id.backspace_one_night);
                assertFalse(backspaceOne_night.performClick());
                Button backspaceTwo_night =
                        (Button) activity.findViewById(R.id.backspace_two_night);
                assertFalse(backspaceTwo_night.performClick());
                Button backspace_night = (Button) activity.findViewById(R.id.backspace_night);
                assertFalse(backspace_night.performClick());
                Button menuTheme_night = (Button) activity.findViewById(R.id.menu_theme_night);
                assertFalse(menuTheme_night.performClick());
                TextView result_night = (TextView) activity.findViewById(R.id._result_night);
                assertFalse(result_night.performClick());
                NestedScrollView inputHistory_night =
                        (NestedScrollView) activity.findViewById(R.id.input_history_night);
                assertFalse(inputHistory_night.performClick());
                TextView inputedHistoryText_night =
                        (TextView) activity.findViewById(R.id.inputed_history_text_night);
                assertFalse(inputedHistoryText_night.performClick());
            }
        });
    }
    //endregion

    /** Проверка вывода текущей информации в текстовые поля калькулятора */ //region
    @Test // Проверка работоспособности метода setInputedHistoryText()
    public void acitivitySetInputedHistoryText_IsWork() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                activity.setInputtedHistoryText("1000.00");
                TextView inputedHistoryText =
                        (TextView) activity.findViewById(R.id.inputed_history_text);
                assertEquals("1000.00", inputedHistoryText.getText());
                TextView inputedHistoryText_night =
                        (TextView) activity.findViewById(R.id.inputed_history_text_night);
                assertEquals("", inputedHistoryText_night.getText());
            }
        });
    }
    @Test // Проверка работоспособности метода setOutputResultText()
    public void acitivitySetOutputResultText_IsWork() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                activity.setOutputResultText("1000.00");
                TextView result =
                        (TextView) activity.findViewById(R.id.result);
                assertEquals("1000.00", result.getText());
                TextView result_night =
                        (TextView) activity.findViewById(R.id._result_night);
                assertEquals("0", result_night.getText());
            }
        });
    }
    //endregion

    @After // Завершение сценария
    public void close() {
        // Сценарий обязательно должен закончиться,
        // иначе другие тесты будут проходить на незачищенных данных
        scenario.close();
    }
}
