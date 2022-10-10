package ru.geekbrains.lessions2345.calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.main.MainPresenter;
import ru.geekbrains.lessions2345.calculator.view.main.ViewMainContract;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.times;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.CHANGE_SIGN_EMPTY;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.CLOSE_BRACKET_ON_ACTION_WITHOUT_NUMBER;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.CLOSE_BRACKET_ON_EMPTY;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.CLOSE_BRACKET_ON_EMPTY_OPEN_BRACKET;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.INPUT_NUMBER_FIRST;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.MANY_ZERO_IN_INTEGER_PART;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.MULTIPLE_PERCENT_IN_BRACKET;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.NUMBER_AFTER_BRACKET;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.OPEN_BRACKET_ON_EMPTY_ACTION;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.PERCENT_NEEDS_TWO_NUMBERS;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING_TYPE;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_IN_STRING.BRACKET_DISBALANCE;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_IN_STRING.NO;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_IN_STRING.SQRT_MINUS;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_IN_STRING.ZERO_DIVIDE;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_IN_STRING_TYPE;

public class MainPresenterTest {
    /** Задание переменных */ //region
    public MainPresenter mainPresenter;
    //endregion

    @Mock // Задание переменной для вью
    public ViewMainContract viewMainContract;

    @Before // Предварительная установка
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(viewMainContract);
    }

    @Test // 1. Проверка работоспособности метода addNumeral(): 1234567890
    public void addNumeral_Test() {
        assertEquals(mainPresenter.addNumeral(1), 1D);
        assertEquals(mainPresenter.addNumeral(2), 12D);
        assertEquals(mainPresenter.addNumeral(3), 123D);
        assertEquals(mainPresenter.addNumeral(4), 1234D);
        assertEquals(mainPresenter.addNumeral(5), 12345D);
        assertEquals(mainPresenter.addNumeral(6), 123456D);
        assertEquals(mainPresenter.addNumeral(7), 1234567D);
        assertEquals(mainPresenter.addNumeral(8), 12345678D);
        assertEquals(mainPresenter.addNumeral(9), 123456789D);
        assertEquals(mainPresenter.addNumeral(0), 1234567890D);
    }

    @Test // 2. Проверка корректности выполнения метода createOutput(): 2
    public void createOutput_Test() {
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("2");
        assertEquals(mainPresenter.createOutput(), "2");
    }

    @Test // 3. Проверка корректности выполнения метода getFinalResult(): 2
    public void getFinalResult_Test() {
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("2");
        mainPresenter.setEqual();
        assertEquals(mainPresenter.getFinalResult(), "2");
    }

    @Test // 4. Проверка корректности выполнения setBracketOpen(): (
    public void setBracketOpen_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
    }

    @Test // 5. Проверка корректности выполнения setBracketClose(): (1)
    public void setBracketClose_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(1);
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1)");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("1");
    }

    // 6. Проверка выполнения setBracketClose()
    // при попытке её закрыть в самом начале строки: )
    @Test
    public void setBracketClose_IN_BEGIN_ROW_Test() {
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("0");
    }

    @Test // 7. Проверка корректности выполнения действия возведения в степень: 2^3=8
    public void setNewAction_ACT_STEP_Test() {
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("2");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_STEP);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("2^");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("2^3");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("8");
    }

    // 8. Проверка корректности выполнения действия произведения
    // на процент от числа: 9*10%=8,1
    @Test
    public void setNewAction_ACT_PERS_MULTY_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9*");
        mainPresenter.addNumeral(10);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9*10");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9*10%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("8,1");
    }

    @Test // 9. Проверка корректности выполнения действия деления на процент от числа: 9/10%=10
    public void setNewAction_ACT_PERS_DIV_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9/");
        mainPresenter.addNumeral(10);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9/10");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9/10%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("10");
    }

    @Test // 10. Проверка корректности выполнения действия вычитания процента от числа: 9-20%=7.2
    public void setNewAction_ACT_PERS_MINUS_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9-");
        mainPresenter.addNumeral(20);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9-20");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9-20%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("7,2");
    }

    @Test // 11. Проверка корректности выполнения действия сложения с процентом от числа: 9+20%=10.8
    public void setNewAction_ACT_PERS_PLUS_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9+");
        mainPresenter.addNumeral(20);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9+20");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("9+20%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("10,8");
    }

    @Test // 12. Проверка корректности выполнения действия умножения: 8*7=56
    public void setNewAction_ACT_MULTY_Test() {
        mainPresenter.addNumeral(8);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("8");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("8*");
        mainPresenter.addNumeral(7);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("8*7");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("56");
    }

    @Test // 13. Проверка корректности выполнения действия деления: 6/2=3
    public void setNewAction_ACT_DIV_Test() {
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6/");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6/2");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("3");
    }

    @Test // 14. Проверка корректности выполнения действия вычитания: 1-1=0
    public void setNewAction_ACT_MINUS_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1-");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1-1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("0");
    }

    @Test // 15. Проверка корректности выполнения действия сложения: 1+1=2
    public void setNewAction_ACT_PLUS_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
            setOutputResultText("2");
    }

    // 16. Проверка установки максимального количества символов
    // для отображения в результирующем поле: 1234567890123=1,234568e+12, 1234567890123=1,235e+12
    @Test
    public void setMaxNumberSymbolsInOutputTextField_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("1");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("12");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("123");
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("1234");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("12345");
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("123456");
        mainPresenter.addNumeral(7);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("1234567");
        mainPresenter.addNumeral(8);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("12345678");
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("123456789");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("1234567890");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("12345678901");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("123456789012");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
            setInputtedHistoryText("1234567890123");
        mainPresenter.setMaxNumberSymbolsInOutputTextField( 6 * 2);
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
            setOutputResultText("1,234568e+12");
        mainPresenter.setMaxNumberSymbolsInOutputTextField( 5 * 2);
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
            setOutputResultText("1,235e+12");
    }

    @Test // 17. Проверка корректности работы метод calculate(): 1+2*3-5/2=4.5
    public void calculate_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-5/");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-5/2");
        mainPresenter.calculate();
        assertEquals(mainPresenter.getFinalResult(), "4,5");
    }

    @Test // 18. Проверка корректности работы метода setEqual(): 1+2*3-5/2=4.5
    public void setEqual_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-5/");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2*3-5/2");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("4,5");
    }

    @Test // 19. Проверка корректности работы метода setCurZapitay(): 12.2
    public void setCurZapitay_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setCurZapitay();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1.");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1.2");
        mainPresenter.setCurZapitay();
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("12.2");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("12,2");
    }

    @Test // 20. Проверка корректности работы метода getPressedZapitay(): 1.2
    public void getPressedZapitay_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setCurZapitay();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1.");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1.2");
        assertTrue(mainPresenter.getPressedZapitay());
        mainPresenter.setCurZapitay();
        assertFalse(mainPresenter.getPressedZapitay());
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("1,2");
    }

    @Test // 21. Проверка корректности работы метода clearAll(): 12"C"
    public void clearAll_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("12");
        mainPresenter.clearAll();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(2)).
                setOutputResultText("0");
    }

    @Test // 22. Проверка корректности работы метода clearOne(): 12"<-"
    public void clearOne_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("12");
        mainPresenter.clearOne();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("1");
    }

    @Test // 23. Проверка корректности работы метода clearTwo(): 1-2"<--"
    public void clearTwo_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1-");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1-2");
        mainPresenter.clearTwo();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("1");
    }

    @Test // 24. Проверка корректности работы метода changeSign(): 1"+/-"
    public void changeSign_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.changeSign();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("-1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("-1");
    }

    @Test // 25. Проверка корректности выполнения setNewFunction(): √(4)
    public void setNewFunction_Test() {
        mainPresenter.setNewFunction(Constants.FUNCTIONS.FUNC_SQRT);
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("\u221A(4");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("\u221A(4)");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("2");
    }

    @Test // 26. Проверка корректности выполнения getError() без ошибок: 4
    public void getError_NO_ERRORS_Test() {
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("4");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInString(NO, ERRORS_IN_STRING_TYPE);
    }

    @Test // 27. Проверка корректности выполнения getError() для подкоренного выражения: √((-4))
    public void getError_SQRT_MINUS_Test() {
        mainPresenter.setNewFunction(Constants.FUNCTIONS.FUNC_SQRT);
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("\u221A(4");
        mainPresenter.changeSign();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("\u221A((-4)");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("\u221A((-4))");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInString(SQRT_MINUS, ERRORS_IN_STRING_TYPE);
    }

    // 28. Проверка корректности выполнения getError() для равенства открытых и закрытых скобок: (4
    @Test
    public void getError_EQUALS_OPEN_CLOSED_BRACKETS_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(4");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInString(BRACKET_DISBALANCE, ERRORS_IN_STRING_TYPE);
    }

    @Test // 29. Проверка корректности выполнения getError() для деления на ноль: 4/0
    public void getError_ZERO_DIVIDE_Test() {
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("4");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("4/");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("4/0");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInString(ZERO_DIVIDE, ERRORS_IN_STRING_TYPE);
    }

    // 30. Проверка корректности выполнения showErrorInputting()
    // для простановки числа сразу же после скобки без указания действйи с ним: (5)1
    @Test
    public void showErrorInputting_NUMBER_AFTER_BRACKET_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(5");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(5)");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(NUMBER_AFTER_BRACKET, ERRORS_INPUTTING_TYPE);
    }

    // 31. Проверка корректности выполнения showErrorInputting()
    // для показа уведомление о том, что для задания нулевой целой части числа
    // вполне хватит и одного нуля: 00
    @Test
    public void showErrorInputting_MANY_ZERO_IN_INTEGER_PART_Test() {
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("0");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(MANY_ZERO_IN_INTEGER_PART, ERRORS_INPUTTING_TYPE);
    }

    // 32. Проверка корректности выполнения showErrorInputting()
    // для показа уведомления, если не ввести число сначала:
    @Test
    public void showErrorInputting_INPUT_NUMBER_FIRST_Test() {
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_STEP);
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(INPUT_NUMBER_FIRST, ERRORS_INPUTTING_TYPE);
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(PERCENT_NEEDS_TWO_NUMBERS, ERRORS_INPUTTING_TYPE);
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(2)).
                showErrorInputting(INPUT_NUMBER_FIRST, ERRORS_INPUTTING_TYPE);
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(3)).
                showErrorInputting(INPUT_NUMBER_FIRST, ERRORS_INPUTTING_TYPE);
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(4)).
                showErrorInputting(INPUT_NUMBER_FIRST, ERRORS_INPUTTING_TYPE);
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(5)).
                showErrorInputting(INPUT_NUMBER_FIRST, ERRORS_INPUTTING_TYPE);
    }

    // 33. Проверка корректности выполнения showErrorInputting()
    // для показа уведомления о том, что для применения процента нужно ввести два числа и любую
    // следующую арифметическую операцию между ними (*, /, +, -):  %, 1%
    @Test
    public void showErrorInputting_PERCENT_NEEDS_TWO_NUMBERS_Test() {
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(PERCENT_NEEDS_TWO_NUMBERS, ERRORS_INPUTTING_TYPE);
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(2)).
                showErrorInputting(PERCENT_NEEDS_TWO_NUMBERS, ERRORS_INPUTTING_TYPE);
    }

    // 34. Проверка корректности выполнения showErrorInputting()
    // для показа уведомления о том, что нельзя производить смену знака,
    // предварительно не задав число или функцию: (1)"+/-"
    @Test
    public void showErrorInputting_CHANGE_SIGN_EMPTY_Test() {
        mainPresenter.changeSign();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(CHANGE_SIGN_EMPTY, ERRORS_INPUTTING_TYPE);
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1)");
        mainPresenter.changeSign();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(CHANGE_SIGN_EMPTY, ERRORS_INPUTTING_TYPE);
    }

    // 35. Проверка корректности выполнения showErrorInputting()
    // для показа уведомления о том, что нельзя создать новую открытую скобку,
    // потому что не указано перед скобкой действие: (1)(
    @Test
    public void showErrorInputting_OPEN_BRACKET_ON_EMPTY_ACTION_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1)");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(OPEN_BRACKET_ON_EMPTY_ACTION, ERRORS_INPUTTING_TYPE);
    }

    // 36. Проверка корректности выполнения showErrorInputting()
    // для показа уведомления о том, что нельзя поставить закрывающую скобку, если
    // предварительно не поставить ей соответствующую открывающую скобку: (1))
    @Test
    public void showErrorInputting_CLOSE_BRACKET_ON_EMPTY_OPEN_BRACKET_Test() {
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(CLOSE_BRACKET_ON_EMPTY_OPEN_BRACKET, ERRORS_INPUTTING_TYPE);
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1)");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(2)).
                showErrorInputting(CLOSE_BRACKET_ON_EMPTY_OPEN_BRACKET, ERRORS_INPUTTING_TYPE);
    }

    // 37. Проверка корректности выполнения showErrorInputting() для пустого выражения в скобках: ()
    @Test
    public void showErrorInputting_CLOSE_BRACKET_ON_EMPTY_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(CLOSE_BRACKET_ON_EMPTY, ERRORS_INPUTTING_TYPE);
    }

    // 38. Проверка корректности выполнения showErrorInputting()
    // для показа уведомления о том, что нельзя закрывающую скобку ставить
    // на действии без указания числа: (1+)
    @Test
    public void showErrorInputting_CLOSE_BRACKET_ON_ACTION_WITHOUT_NUMBER_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(1+");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(CLOSE_BRACKET_ON_ACTION_WITHOUT_NUMBER, ERRORS_INPUTTING_TYPE);
    }

    // 39. Проверка корректности выполнения showErrorInputting()
    // для показа уведомления о том, что без скобок или в рамках одной скобки нельзя вводить знак
    // процента больше одного раза. Если нужно произвести вычисление процента несколько раз,
    // то нужно каждую такую конструкцию оборачивать в отдельную скобку: 1+2%+3%
    @Test
    public void showErrorInputting_MULTIPLE_PERCENT_IN_BRACKET_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2%");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2%+");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("1+2%+3");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                showErrorInputting(MULTIPLE_PERCENT_IN_BRACKET, ERRORS_INPUTTING_TYPE);
    }

    @Test // 40. Проверка корректности выполнения getInit()
    public void getInit_Test() {
        mainPresenter.getInit();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("");
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("0");
    }

    @Test // 41. Проверка корректности задания процента после скобки с процентом: 6+(6+5%)%
    public void setPercentAfterBracketWithPercent_Test() {
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6+");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("6+(");
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6+(6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6+(6+");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6+(6+5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6+(6+5%");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6+(6+5%)");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("6+(6+5%)%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("6,378");
    }

    @Test // 42. Проверка корректности задания процента во второй скобке подряд: 7+(6+5%)+(6+5%)
    public void setPercentIntoTwoBrackets_Test() {
        mainPresenter.addNumeral(7);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("7+(");
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%)");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%)+");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("7+(6+5%)+(");
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%)+(6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%)+(6+");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%)+(6+5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%)+(6+5%");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("7+(6+5%)+(6+5%)");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("19,6");
    }

    @Test // 43. Проверка корректности задания процента в первой скобке и без скобки: (7+4%)+5%
    public void setPercentIntoFirstBracketAndWithoutBracket_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.addNumeral(7);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7+");
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7+4");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7+4%");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7+4%)");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7+4%)+");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7+4%)+5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERC_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(7+4%)+5%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("7,644");
    }

    @Test // 44. Проверка корректности задания пустых скобок: (((6+5))+(5))
    public void emptyBracketSet_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("((");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(((");
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+5");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+5)");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+5))");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+5))+");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(((6+5))+(");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+5))+(5");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+5))+(5)");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("(((6+5))+(5))");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("16");
    }

    // 45. Проверка корректности задания пустых скобок в различной комбинации уровней:
    // ((((6+5)))+((5))-4)
    @Test
    public void emptyBracketWithDifferentLevelsSet_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("((");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("(((");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("((((");
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5))");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))+");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("((((6+5)))+(");
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("((((6+5)))+((");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))+((5");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))+((5)");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))+((5))");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))+((5))-");
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))+((5))-4");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("((((6+5)))+((5))-4)");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("12");
    }

    @Test // 46. Проверка возможности поставить несколько нулей в целой части числа: 0000.1
    public void setMultipleZeroInDecimalPartOfNumber_Test() {
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("0");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(2)).
                setInputtedHistoryText("0");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(3)).
                setInputtedHistoryText("0");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(4)).
                setInputtedHistoryText("0");
        mainPresenter.setCurZapitay();
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputtedHistoryText("0.1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("0,1");
    }
}