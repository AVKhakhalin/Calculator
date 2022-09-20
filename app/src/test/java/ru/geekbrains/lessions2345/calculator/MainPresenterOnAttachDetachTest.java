package ru.geekbrains.lessions2345.calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.geekbrains.lessions2345.calculator.view.ui_main.MainPresenter;
import ru.geekbrains.lessions2345.calculator.view.ui_main.ViewMainContract;

import static org.mockito.Mockito.times;

/** Покрытие методов onAttach() и onDetach() тестами */
public class MainPresenterOnAttachDetachTest {

    /** Задание переменных */ //region
    public MainPresenter mainPresenter;
    //endregion

    @Mock // Задание переменной для вью
    public ViewMainContract viewMain;

    @Before // Предварительная установка
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter();
        mainPresenter.onAttach(viewMain);
    }

    @Test // Провека работоспособности метода onAttach()
    public void onAttach_Test() {
        mainPresenter.getInit();
        // Вызыв метода setInputedHistoryText() говорит о том, что viewMain != null
        Mockito.verify(viewMain, times(1)).
                setInputedHistoryText("");
    }
    @Test // Провека работоспособности метода onDetach()
    public void onDetach_Test() {
        mainPresenter.onDetach();
        mainPresenter.addNumeral(1);
        // Отсутствие вызыва метод setInputedHistoryText() говорит о том, что viewMain = null
        Mockito.verify(viewMain, times(0)).
                setInputedHistoryText("1");
    }
}
