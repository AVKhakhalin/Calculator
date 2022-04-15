package ru.geekbrains.lessions2345.calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.presenter.main.MainPresenter;
import ru.geekbrains.lessions2345.calculator.view.ui_main.ViewMainContract;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.times;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS.BRACKETS_EMPTY;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS.BRACKET_DISBALANCE;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS.NO;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS.SQRT_MINUS;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS.ZERO_DIVIDE;

public class MainPresenterTest {

    public MainPresenter mainPresenter;

    @Mock
    public ViewMainContract viewMainContract;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(viewMainContract);
    }

    @Test
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

    @Test // Проверка корректности выполнения метода createOutput()
    public void createOutput_Test() {
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("2");
        assertEquals(mainPresenter.createOutput(), "2");
    }

    @Test // Проверка корректности выполнения метода getFinalResult()
    public void getFinalResult_Test() {
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("2");
        mainPresenter.setEqual();
        assertEquals(mainPresenter.getFinalResult(), "2");
    }

    @Test // Проверка корректности выполнения setBracketOpen()
    public void setBracketOpen_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("(");
    }

    @Test // Проверка корректности выполнения setBracketClose()
    public void setBracketClose_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("(");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("()");
    }

    // Проверка корректности выполнения setBracketClose()
    // при попытке её закрыть в самом начале строки
    @Test
    public void setBracketClose_IN_BEGIN_ROW_Test() {
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("");
    }

    @Test // Проверка корректности выполнения действия возведения в степень
    public void setNewAction_ACT_STEP_Test() {
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("2");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_STEP);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("2^");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("2^3");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("8");
    }

    @Test // Проверка корректности выполнения действия произведения на процент от числа
    public void setNewAction_ACT_PERS_MULTY_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9*");
        mainPresenter.addNumeral(10);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9*10");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERS_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9*10%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("8,1");
    }

    @Test // Проверка корректности выполнения действия деления на процент от числа
    public void setNewAction_ACT_PERS_DIV_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9/");
        mainPresenter.addNumeral(10);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9/10");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERS_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9/10%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("10");
    }

    @Test // Проверка корректности выполнения действия вычитания процента от числа
    public void setNewAction_ACT_PERS_MINUS_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9-");
        mainPresenter.addNumeral(20);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9-20");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERS_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9-20%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("7,2");
    }

    @Test // Проверка корректности выполнения действия сложения с процентом от числа
    public void setNewAction_ACT_PERS_PLUS_Test() {
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9+");
        mainPresenter.addNumeral(20);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9+20");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PERS_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("9+20%");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("10,8");
    }

    @Test // Проверка корректности выполнения действия умножения
    public void setNewAction_ACT_MULTY_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1*");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1*1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("1");
    }

    @Test // Проверка корректности выполнения действия деления
    public void setNewAction_ACT_DIV_Test() {
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("6");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("6/");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("6/2");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("3");
    }

    @Test // Проверка корректности выполнения действия вычитания
    public void setNewAction_ACT_MINUS_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1-");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1-1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("0");
    }

    @Test // Проверка корректности выполнения действия сложения
    public void setNewAction_ACT_PLUS_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
            setInputedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
            setInputedHistoryText("1+");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
            setInputedHistoryText("1+1");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
            setOutputResultText("2");
    }

    // Проверка установки максимального количества символов для отображения в результирующем поле
    @Test
    public void setMaxNumberSymbolsInOutputTextField_Test() {
        mainPresenter.setMaxNumberSymbolsInOutputTextField(6);
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("12");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("123");
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1234");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("12345");
        mainPresenter.addNumeral(6);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("123456");
        mainPresenter.addNumeral(7);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1234567");
        mainPresenter.addNumeral(8);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("12345678");
        mainPresenter.addNumeral(9);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("123456789");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1234567890");
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("12345678901");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("123456789012");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1234567890123");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("1,234568e+12");
    }

    @Test // Проверка корректности работы метод calculate()
    public void calculate_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-5/");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-5/2");
        mainPresenter.calculate();
        assertEquals(mainPresenter.getFinalResult(), "4,5");
    }

    @Test // Проверка корректности работы метода setEqual()
    public void setEqual_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_PLUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MULTY);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*");
        mainPresenter.addNumeral(3);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-");
        mainPresenter.addNumeral(5);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-5");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-5/");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1+2*3-5/2");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("4,5");
    }

    @Test // Проверка корректности работы метода setCurZapitay()
    public void setCurZapitay_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.setCurZapitay();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1.");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1.2");
        mainPresenter.setCurZapitay();
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("12.2");
    }

    @Test // Проверка корректности работы метода getPressedZapitay()
    public void getPressedZapitay_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.setCurZapitay();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1.");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1.2");
        assertEquals(mainPresenter.getPressedZapitay(), true);
        mainPresenter.setCurZapitay();
        assertEquals(mainPresenter.getPressedZapitay(), false);
    }

    @Test // Проверка корректности работы метода clearAll()
    public void clearAll_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("12");
        mainPresenter.clearAll();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("");
    }

    @Test // Проверка корректности работы метода clearOne()
    public void clearOne_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("12");
        mainPresenter.clearOne();
        // TODO: Не понятно, почему метод setInputedHistoryText() вызывается 2 раза.
        //  Надо потом разобраться. Благодаря тестированию это было установлено ;)
        Mockito.verify(viewMainContract, times(2)).
                setInputedHistoryText("1");
    }

    @Test // Проверка корректности работы метода clearTwo()
    public void clearTwo_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_MINUS);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1-");
        mainPresenter.addNumeral(2);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1-2");
        mainPresenter.clearTwo();
        // TODO: Не понятно, почему метод setInputedHistoryText() вызывается 2 раза.
        //  Надо потом разобраться. Благодаря тестированию это было установлено ;)
        Mockito.verify(viewMainContract, times(2)).
                setInputedHistoryText("1");
    }

    @Test // Проверка корректности работы метода changeSign()
    public void changeSign_Test() {
        mainPresenter.addNumeral(1);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("1");
        mainPresenter.changeSign();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("-1");
    }

    @Test // Проверка корректности выполнения setNewFunction()
    public void setNewFunction_Test() {
        mainPresenter.setNewFunction(Constants.FUNCTIONS.FUNC_SQRT);
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("SQRT(4");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("SQRT(4)");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("2");
        // TODO: Благодаря тестированию выявлена следующая ошибка:
        //  при нажатии на кнопку Kx знак корня не отображается, он отображается только тогда,
        //  когда вводится число, к которому нужно применить функцию извлечения квадратичного корня.
    }

    @Test // Проверка корректности выполнения getError() без ошибок
    public void getError_NO_ERRORS_Test() {
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("4");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setErrorText(NO);
    }

    @Test // Проверка корректности выполнения getError() для подкоренного значения
    public void getError_SQRT_MINUS_Test() {
        mainPresenter.setNewFunction(Constants.FUNCTIONS.FUNC_SQRT);
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("SQRT(4");
        mainPresenter.changeSign();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("SQRT((-4)");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("SQRT((-4))");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setErrorText(SQRT_MINUS);
    }

    @Test // Проверка корректности выполнения getError() для равенства открытых и закрытых скобок
    public void getError_EQUALS_OPEN_CLOSED_BRACKETS_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("(");
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("(4");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setErrorText(BRACKET_DISBALANCE);
    }

    @Test // Проверка корректности выполнения getError() для деления на ноль
    public void getError_ZERO_DIVIDE_Test() {
        mainPresenter.addNumeral(4);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("4");
        mainPresenter.setNewAction(Constants.ACTIONS.ACT_DIV);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("4/");
        mainPresenter.addNumeral(0);
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("4/0");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setErrorText(ZERO_DIVIDE);
    }

    @Test // Проверка корректности выполнения getError() для пустого выражения в скобках
    public void getError_EMPTY_INSIGHT_BRACKETS_Test() {
        mainPresenter.setBracketOpen();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("(");
        mainPresenter.setBracketClose();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("()");
        mainPresenter.setEqual();
        Mockito.verify(viewMainContract, times(1)).
                setErrorText(BRACKETS_EMPTY);
    }

    @Test // Проверка корректности выполнения getInit()
    public void getInit_Test() {
        mainPresenter.getInit();
        Mockito.verify(viewMainContract, times(1)).
                setInputedHistoryText("");
        Mockito.verify(viewMainContract, times(1)).
                setOutputResultText("0");
    }
}