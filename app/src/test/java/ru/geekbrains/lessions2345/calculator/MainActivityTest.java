package ru.geekbrains.lessions2345.calculator;

import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
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

import ru.geekbrains.lessions2345.calculator.view.main.MainActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.BORDER_WIDTH;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_BORDER_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.DEFAULT_BUTTON_RADIUS;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.SQUARE_TEXT_RELATIVE_SIZE;

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
    @Test // Проверка корректности создания класса MainActivity
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
    @Test // Проверка наличия в макете MainActivity элементов калькулятора
    public void activityElements_NotNull() {
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
                TextView inputtedHistoryText =
                        (TextView) activity.findViewById(R.id.inputted_history_text);
                assertNotNull(inputtedHistoryText);
            }
        });
    }
    //endregion

    /** Проверка наличия корректного текста в элементах */ //region
    @Test // Проверка наличия текста в поле text для элементов калькулятора
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
                assertEquals("xn", stepen.getText().toString());
                Button sqrt = (Button) activity.findViewById(R.id.sqrt);
                assertEquals("\u221Ax", sqrt.getText());
                Button equal = (Button) activity.findViewById(R.id.equal);
                assertEquals("=", equal.getText());
                Button backspaceOne = (Button) activity.findViewById(R.id.backspace_one);
                assertEquals("<-", backspaceOne.getText());
                Button backspaceTwo = (Button) activity.findViewById(R.id.backspace_two);
                assertEquals("<—", backspaceTwo.getText());
                Button backspace = (Button) activity.findViewById(R.id.backspace);
                assertEquals("C", backspace.getText());
                Button menuTheme = (Button) activity.findViewById(R.id.menu_theme);
                assertEquals("Настройки", menuTheme.getText());
                TextView result = (TextView) activity.findViewById(R.id.result);
                assertEquals("0", String.valueOf(result.getText()));
                TextView inputtedHistoryText =
                        (TextView) activity.findViewById(R.id.inputted_history_text);
                assertEquals("", String.valueOf(inputtedHistoryText.getText()));
            }
        });
    }
    //endregion

    /** Проверка видимости элементов на макете MainActivity */ //region
    @Test // Проверка видимости элемента калькулятора с фоном
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
    @Test // Проверка видимости элементов калькулятора
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
                activity.setDisplayWidthAndCurRadiusButtons(BORDER_WIDTH, DEFAULT_BUTTON_RADIUS);
                TextView result = (TextView) activity.findViewById(R.id.result);
                assertEquals(View.VISIBLE, result.getVisibility());
                NestedScrollView inputHistory =
                    (NestedScrollView) activity.findViewById(R.id.input_history);
                assertEquals(View.VISIBLE, inputHistory.getVisibility());
                TextView inputtedHistoryText =
                    (TextView) activity.findViewById(R.id.inputted_history_text);
                assertEquals(View.VISIBLE, inputtedHistoryText.getVisibility());
                TextView resultSmall = (TextView) activity.findViewById(R.id.result_small);
                assertEquals(View.GONE, resultSmall.getVisibility());
                NestedScrollView inputHistorySmall =
                        (NestedScrollView) activity.findViewById(R.id.input_history_small);
                assertEquals(View.GONE, inputHistorySmall.getVisibility());
                TextView inputtedHistoryTextSmall =
                        (TextView) activity.findViewById(R.id.inputted_history_text_small);
                assertEquals(View.GONE, inputtedHistoryTextSmall.getVisibility());
                activity.setDisplayWidthAndCurRadiusButtons(
                        BORDER_WIDTH - 10, DEFAULT_BUTTON_RADIUS);
                assertEquals(View.INVISIBLE, result.getVisibility());
                assertEquals(View.INVISIBLE, inputHistory.getVisibility());
                assertEquals(View.INVISIBLE, inputtedHistoryText.getVisibility());
                assertEquals(View.VISIBLE, resultSmall.getVisibility());
                assertEquals(View.VISIBLE, inputHistorySmall.getVisibility());
                assertEquals(View.VISIBLE, inputtedHistoryTextSmall.getVisibility());
                activity.setDisplayWidthAndCurRadiusButtons(
                        BORDER_WIDTH, DEFAULT_BUTTON_BORDER_RADIUS - 10);
                assertEquals(View.INVISIBLE, result.getVisibility());
                assertEquals(View.INVISIBLE, inputHistory.getVisibility());
                assertEquals(View.INVISIBLE, inputtedHistoryText.getVisibility());
                assertEquals(View.VISIBLE, resultSmall.getVisibility());
                assertEquals(View.VISIBLE, inputHistorySmall.getVisibility());
                assertEquals(View.VISIBLE, inputtedHistoryTextSmall.getVisibility());
            }
        });
    }
    //endregion

    /** Проверка работоспособности элементов */ //region
    @Test // Проверка работоспособности элементов калькулятора
    public void activityElements_IsWorking() {
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
                TextView inputtedHistoryText =
                        (TextView) activity.findViewById(R.id.inputted_history_text);
                assertFalse(inputtedHistoryText.performClick());
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
                // Проверка вывод в текстовое поле inputted_history_text
                activity.setDisplayWidthAndCurRadiusButtons(BORDER_WIDTH, DEFAULT_BUTTON_RADIUS);
                activity.setInputtedHistoryText("1000.00");
                TextView inputtedHistoryText =
                        (TextView) activity.findViewById(R.id.inputted_history_text);
                assertEquals("1000.00", String.valueOf(inputtedHistoryText.getText()));

                // Проверка вывод в текстовое поле inputted_history_text_small
                activity.setDisplayWidthAndCurRadiusButtons(
                        BORDER_WIDTH - 10, DEFAULT_BUTTON_RADIUS);
                activity.setInputtedHistoryText("1000.00");
                TextView inputtedHistoryTextSmall =
                        (TextView) activity.findViewById(R.id.inputted_history_text_small);
                assertEquals("1000.00", String.valueOf(inputtedHistoryTextSmall.getText()));
                activity.setDisplayWidthAndCurRadiusButtons(
                        BORDER_WIDTH, DEFAULT_BUTTON_BORDER_RADIUS - 10);
                activity.setInputtedHistoryText("2000.00");
                assertEquals("2000.00", String.valueOf(inputtedHistoryTextSmall.getText()));
            }
        });
    }
    @Test // Проверка работоспособности метода setOutputResultText()
    public void acitivitySetOutputResultText_IsWork() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                // Проверка вывода в текстовое поле result
                activity.setDisplayWidthAndCurRadiusButtons(BORDER_WIDTH, DEFAULT_BUTTON_RADIUS);
                activity.setOutputResultText("1000.00");
                TextView result = (TextView) activity.findViewById(R.id.result);
                assertEquals("1000.00", String.valueOf(result.getText()));

                // Проверка вывода в текстовое поле result_small
                activity.setDisplayWidthAndCurRadiusButtons(
                        BORDER_WIDTH - 10, DEFAULT_BUTTON_RADIUS);
                activity.setOutputResultText("1000.00");
                TextView resultSmall = (TextView) activity.findViewById(R.id.result_small);
                assertEquals("1000.00", String.valueOf(resultSmall.getText()));
                activity.setDisplayWidthAndCurRadiusButtons(
                        BORDER_WIDTH, DEFAULT_BUTTON_BORDER_RADIUS - 10);
                activity.setOutputResultText("2000.00");
                assertEquals("2000.00", String.valueOf(resultSmall.getText()));
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