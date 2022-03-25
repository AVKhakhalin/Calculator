package ru.geekbrains.lessions2345.calculator;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.geekbrains.lessions2345.calculator.view.ui_main.MainActivity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {
    /** Задание переменных */ //region
    private ActivityScenario<MainActivity> scenario;
    //endregion

    @Before // Установка действия до начала выполнения всех тестов
    public void setup() {
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test // Проверка на существование активити
    public void activity_AssertNotNull() {
        scenario.onActivity( it ->
            TestCase.assertNotNull(it)
        );
    }

    @Test // Проверка на прохождение в активити метода onResume()
    public void activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.getState());
    }

    @Test // Проверка наличия элемента с картинкой фона
    public void activityBackgroundThemeImageView_NotNull() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                ImageView backgroundTheme =
                        (ImageView) activity.findViewById(R.id._background_theme);
                            TestCase.assertNotNull(backgroundTheme);
            }
        });
    }

    @Test // Проверка наличия на экране элементов дневной темы
    public void activityDayThemeElements_NotNull() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0 = (Button) activity.findViewById(R.id._0);
                TestCase.assertNotNull(button_0);
                Button button_1 = (Button) activity.findViewById(R.id._1);
                TestCase.assertNotNull(button_1);
                Button button_2 = (Button) activity.findViewById(R.id._2);
                TestCase.assertNotNull(button_2);
                Button button_3 = (Button) activity.findViewById(R.id._3);
                TestCase.assertNotNull(button_3);
                Button button_4 = (Button) activity.findViewById(R.id._4);
                TestCase.assertNotNull(button_4);
                Button button_5 = (Button) activity.findViewById(R.id._5);
                TestCase.assertNotNull(button_5);
                Button button_6 = (Button) activity.findViewById(R.id._6);
                TestCase.assertNotNull(button_6);
                Button button_7 = (Button) activity.findViewById(R.id._7);
                TestCase.assertNotNull(button_7);
                Button button_8 = (Button) activity.findViewById(R.id._8);
                TestCase.assertNotNull(button_8);
                Button button_9 = (Button) activity.findViewById(R.id._9);
                TestCase.assertNotNull(button_9);
                Button divide = (Button) activity.findViewById(R.id._divide);
                TestCase.assertNotNull(divide);
                Button minus = (Button) activity.findViewById(R.id._minus);
                TestCase.assertNotNull(minus);
                Button zapitay = (Button) activity.findViewById(R.id._zapitay);
                TestCase.assertNotNull(zapitay);
                Button multiply = (Button) activity.findViewById(R.id._multiply);
                TestCase.assertNotNull(multiply);
                Button plus = (Button) activity.findViewById(R.id._plus);
                TestCase.assertNotNull(plus);
                Button bracketOpen = (Button) activity.findViewById(R.id._bracket_open);
                TestCase.assertNotNull(bracketOpen);
                Button bracketClose = (Button) activity.findViewById(R.id._bracket_close);
                TestCase.assertNotNull(bracketClose);
                Button percent = (Button) activity.findViewById(R.id._percent);
                TestCase.assertNotNull(percent);
                Button plusMinus = (Button) activity.findViewById(R.id._plus_minus);
                TestCase.assertNotNull(plusMinus);
                Button stepen = (Button) activity.findViewById(R.id._stepen);
                TestCase.assertNotNull(stepen);
                Button sqrt = (Button) activity.findViewById(R.id._sqrt);
                TestCase.assertNotNull(sqrt);
                Button equal = (Button) activity.findViewById(R.id._equal);
                TestCase.assertNotNull(equal);
                Button backspaceOne = (Button) activity.findViewById(R.id._backspace_one);
                TestCase.assertNotNull(backspaceOne);
                Button backspaceTwo = (Button) activity.findViewById(R.id._backspace_two);
                TestCase.assertNotNull(backspaceTwo);
                Button backspace = (Button) activity.findViewById(R.id._backspace);
                TestCase.assertNotNull(backspace);
                Button menuTheme = (Button) activity.findViewById(R.id._menu_theme);
                TestCase.assertNotNull(menuTheme);
                TextView result = (TextView) activity.findViewById(R.id._RESULT);
                TestCase.assertNotNull(result);
                NestedScrollView inputHistory =
                        (NestedScrollView) activity.findViewById(R.id._input_history);
                TestCase.assertNotNull(inputHistory);
                TextView inputedHistoryText =
                        (TextView) activity.findViewById(R.id._inputed_history_text);
                TestCase.assertNotNull(inputedHistoryText);
            }
        });
    }

    @Test // Проверка наличия на экране элементов ночной темы
    public void activityNightThemeElements_NotNull() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                Button button_0_night = (Button) activity.findViewById(R.id._0_night);
                TestCase.assertNotNull(button_0_night);
                Button button_1_night = (Button) activity.findViewById(R.id._1_night);
                TestCase.assertNotNull(button_1_night);
                Button button_2_night = (Button) activity.findViewById(R.id._2_night);
                TestCase.assertNotNull(button_2_night);
                Button button_3_night = (Button) activity.findViewById(R.id._3_night);
                TestCase.assertNotNull(button_3_night);
                Button button_4_night = (Button) activity.findViewById(R.id._4_night);
                TestCase.assertNotNull(button_4_night);
                Button button_5_night = (Button) activity.findViewById(R.id._5_night);
                TestCase.assertNotNull(button_5_night);
                Button button_6_night = (Button) activity.findViewById(R.id._6_night);
                TestCase.assertNotNull(button_6_night);
                Button button_7_night = (Button) activity.findViewById(R.id._7_night);
                TestCase.assertNotNull(button_7_night);
                Button button_8_night = (Button) activity.findViewById(R.id._8_night);
                TestCase.assertNotNull(button_8_night);
                Button button_9_night = (Button) activity.findViewById(R.id._9_night);
                TestCase.assertNotNull(button_9_night);
                Button divide_night = (Button) activity.findViewById(R.id._divide_night);
                TestCase.assertNotNull(divide_night);
                Button minus_night = (Button) activity.findViewById(R.id._minus_night);
                TestCase.assertNotNull(minus_night);
                Button zapitay_night = (Button) activity.findViewById(R.id._zapitay_night);
                TestCase.assertNotNull(zapitay_night);
                Button multiply_night = (Button) activity.findViewById(R.id._multiply_night);
                TestCase.assertNotNull(multiply_night);
                Button plus_night = (Button) activity.findViewById(R.id._plus_night);
                TestCase.assertNotNull(plus_night);
                Button bracketOpen_night =
                        (Button) activity.findViewById(R.id._bracket_open_night);
                TestCase.assertNotNull(bracketOpen_night);
                Button bracketClose_night =
                        (Button) activity.findViewById(R.id._bracket_close_night);
                TestCase.assertNotNull(bracketClose_night);
                Button percent_night = (Button) activity.findViewById(R.id._percent_night);
                TestCase.assertNotNull(percent_night);
                Button plusMinus_night = (Button) activity.findViewById(R.id._plus_minus_night);
                TestCase.assertNotNull(plusMinus_night);
                Button stepen_night = (Button) activity.findViewById(R.id._stepen_night);
                TestCase.assertNotNull(stepen_night);
                Button sqrt_night = (Button) activity.findViewById(R.id._sqrt_night);
                TestCase.assertNotNull(sqrt_night);
                Button equal_night = (Button) activity.findViewById(R.id._equal_night);
                TestCase.assertNotNull(equal_night);
                Button backspaceOne_night =
                        (Button) activity.findViewById(R.id._backspace_one_night);
                TestCase.assertNotNull(backspaceOne_night);
                Button backspaceTwo_night =
                        (Button) activity.findViewById(R.id._backspace_two_night);
                TestCase.assertNotNull(backspaceTwo_night);
                Button backspace_night = (Button) activity.findViewById(R.id._backspace_night);
                TestCase.assertNotNull(backspace_night);
                Button menuTheme_night = (Button) activity.findViewById(R.id._menu_theme_night);
                TestCase.assertNotNull(menuTheme_night);
                TextView result_night = (TextView) activity.findViewById(R.id._RESULT_night);
                TestCase.assertNotNull(result_night);
                NestedScrollView inputHistory_night =
                        (NestedScrollView) activity.findViewById(R.id._input_history_night);
                TestCase.assertNotNull(inputHistory_night);
                TextView inputedHistoryText_night =
                        (TextView) activity.findViewById(R.id._inputed_history_text_night);
                TestCase.assertNotNull(inputedHistoryText_night);
            }
        });
    }

    @Test // Проверка частичного отображения на экране элемента с картинкой фона
    public void activityBackgroundThemeImageView_Displayed() {
        Espresso.onView(withId(R.id._background_theme)).check(matches(isDisplayed()));
    }

    @Test // Проверка частичного отображения на экране элементов дневной темы
    public void activityDayThemeElements_Displayed() {
        Espresso.onView(withId(R.id._0)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._1)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._2)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._3)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._4)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._5)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._6)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._7)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._8)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._9)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._divide)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._minus)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._zapitay)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._multiply)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._plus)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._bracket_open)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._bracket_close)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._percent)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._plus_minus)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._stepen)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._sqrt)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._equal)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._backspace_one)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._backspace_two)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._backspace)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._menu_theme)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._RESULT)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._input_history)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id._inputed_history_text)).check(matches(isDisplayed()));
    }

    @Test // Проверка полного отображения на экране элемента с картинкой фона
    public void activityBackgroundThemeImageView_CompletedDisplayed() {
        onView(withId(R.id._background_theme)).check(matches(isCompletelyDisplayed()));
    }

    @Test // Проверка полного отображения на экране элементов дневной темы
    public void activityDayThemeElements_CompletedDisplayed() {
        Espresso.onView(withId(R.id._0)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._1)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._2)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._3)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._4)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._5)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._6)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._7)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._8)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._9)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._divide)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._minus)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._zapitay)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._multiply)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._plus)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._bracket_open)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._bracket_close)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._percent)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._plus_minus)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._stepen)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._sqrt)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._equal)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._backspace_one)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._backspace_two)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._backspace)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._menu_theme)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._RESULT)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._input_history)).check(matches(isCompletelyDisplayed()));
        Espresso.onView(withId(R.id._inputed_history_text)).check(matches(isCompletelyDisplayed()));
    }
    
    @Test // Проверка свойства видимости элементов ночной темы
    public void activityNightThemeElements_AreEffectiveNotVisible() {
        Espresso.onView(withId(R.id._0_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._1_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._2_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._3_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._4_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._5_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._6_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._7_night)).check(ViewAssertions.matches(ViewMatchers
            .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._8_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._9_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._divide_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._minus_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._zapitay_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._multiply_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._plus_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._bracket_open_night))
                .check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._bracket_close_night))
                .check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._percent_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._plus_minus_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._stepen_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._sqrt_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._equal_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._backspace_one_night))
                .check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._backspace_two_night))
                .check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._backspace_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._menu_theme_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._RESULT_night)).check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._input_history_night))
                .check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id._inputed_history_text_night))
                .check(ViewAssertions.matches(ViewMatchers
                .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test // Проверка отображения корректного текста на элементе с id "toDetailsActivityButton"
    public void activityDayThemeElements_IsCorrectText() {
        Espresso.onView(withId(R.id._0)).check(matches(withText("0")));
        Espresso.onView(withId(R.id._1)).check(matches(withText("1")));
        Espresso.onView(withId(R.id._2)).check(matches(withText("2")));
        Espresso.onView(withId(R.id._3)).check(matches(withText("3")));
        Espresso.onView(withId(R.id._4)).check(matches(withText("4")));
        Espresso.onView(withId(R.id._5)).check(matches(withText("5")));
        Espresso.onView(withId(R.id._6)).check(matches(withText("6")));
        Espresso.onView(withId(R.id._7)).check(matches(withText("7")));
        Espresso.onView(withId(R.id._8)).check(matches(withText("8")));
        Espresso.onView(withId(R.id._9)).check(matches(withText("9")));
        Espresso.onView(withId(R.id._divide)).check(matches(withText("/")));
        Espresso.onView(withId(R.id._minus)).check(matches(withText("-")));
        Espresso.onView(withId(R.id._zapitay)).check(matches(withText(",")));
        Espresso.onView(withId(R.id._multiply)).check(matches(withText("*")));
        Espresso.onView(withId(R.id._plus)).check(matches(withText("+")));
        Espresso.onView(withId(R.id._bracket_open)).check(matches(withText("(")));
        Espresso.onView(withId(R.id._bracket_close)).check(matches(withText(")")));
        Espresso.onView(withId(R.id._percent)).check(matches(withText("%")));
        Espresso.onView(withId(R.id._plus_minus)).check(matches(withText("+/-")));
        Espresso.onView(withId(R.id._stepen)).check(matches(withText("x^n")));
        Espresso.onView(withId(R.id._sqrt)).check(matches(withText("Kx")));
        Espresso.onView(withId(R.id._equal)).check(matches(withText("=")));
        Espresso.onView(withId(R.id._backspace_one)).check(matches(withText("<-")));
        Espresso.onView(withId(R.id._backspace_two)).check(matches(withText("<--")));
        Espresso.onView(withId(R.id._backspace)).check(matches(withText("C")));
        Espresso.onView(withId(R.id._menu_theme)).check(matches(withText("Настройки")));
        Espresso.onView(withId(R.id._RESULT)).check(matches(withText("0")));
        Espresso.onView(withId(R.id._inputed_history_text)).check(matches(withText("")));
    }

    @Test // Проверка корректности работы кнопок калькулятора
    public void activityButtons_IsCorrectWorking() {
        Espresso.onView(withId(R.id._1)).perform(click());
        Espresso.onView(withId(R.id._2)).perform(click());
        Espresso.onView(withId(R.id._3)).perform(click());
        Espresso.onView(withId(R.id._4)).perform(click());
        Espresso.onView(withId(R.id._5)).perform(click());
        Espresso.onView(withId(R.id._6)).perform(click());
        Espresso.onView(withId(R.id._7)).perform(click());
        Espresso.onView(withId(R.id._8)).perform(click());
        Espresso.onView(withId(R.id._zapitay)).perform(click());
        Espresso.onView(withId(R.id._9)).perform(click());
        Espresso.onView(withId(R.id._0)).perform(click());
        Espresso.onView(withId(R.id._zapitay)).perform(click());
        Espresso.onView(withId(R.id._plus)).perform(click());
        Espresso.onView(withId(R.id._sqrt)).perform(click());
        Espresso.onView(withId(R.id._9)).perform(click());
        Espresso.onView(withId(R.id._multiply)).perform(click());
        Espresso.onView(withId(R.id._8)).perform(click());
        Espresso.onView(withId(R.id._percent)).perform(click());
        Espresso.onView(withId(R.id._divide)).perform(click());
        Espresso.onView(withId(R.id._4)).perform(click());
        Espresso.onView(withId(R.id._stepen)).perform(click());
        Espresso.onView(withId(R.id._2)).perform(click());
        Espresso.onView(withId(R.id._bracket_close)).perform(click());
        Espresso.onView(withId(R.id._minus)).perform(click());
        Espresso.onView(withId(R.id._bracket_open)).perform(click());
        Espresso.onView(withId(R.id._3)).perform(click());
        Espresso.onView(withId(R.id._5)).perform(click());
        Espresso.onView(withId(R.id._6)).perform(click());
        Espresso.onView(withId(R.id._8)).perform(click());
        Espresso.onView(withId(R.id._9)).perform(click());
        Espresso.onView(withId(R.id._plus_minus)).perform(click());
        Espresso.onView(withId(R.id._multiply)).perform(click());
        Espresso.onView(withId(R.id._9)).perform(click());
        Espresso.onView(withId(R.id._8)).perform(click());
        Espresso.onView(withId(R.id._8)).perform(click());
        Espresso.onView(withId(R.id._divide)).perform(click());
        Espresso.onView(withId(R.id._3)).perform(click());
        Espresso.onView(withId(R.id._0)).perform(click());
        Espresso.onView(withId(R.id._backspace_one)).perform(click());
        Espresso.onView(withId(R.id._backspace_two)).perform(click());
        Espresso.onView(withId(R.id._bracket_close)).perform(click());
        Espresso.onView(withId(R.id._equal)).perform(click());
        Espresso.onView(withId(R.id._inputed_history_text))
                .check(matches(withText("12345678.90+SQRT(9*8%/4^2)-((-35689)*988)")));
        Espresso.onView(withId(R.id._RESULT)).check(matches(withText("47606411.536")));
        Espresso.onView(withId(R.id._backspace)).perform(click());
        Espresso.onView(withId(R.id._inputed_history_text))
                .check(matches(withText("")));
        Espresso.onView(withId(R.id._RESULT)).check(matches(withText("0")));
    }
    
    @After // Установка действия после завершения выполнения всех тестов
    public void close() {
        scenario.close();
    }
}
