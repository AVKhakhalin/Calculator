package ru.geekbrains.lessions2345.calculator;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.geekbrains.lessions2345.calculator.presenter.main.MainPresenter;
import ru.geekbrains.lessions2345.calculator.view.ui_main.ViewMainContract;

import static org.mockito.Mockito.times;

/** Покрытие методов onAttach() и onDetach() тестами */
public class MainPresenterOnAttachDetachTest {
    public MainPresenter mainPresenter;

    @Mock
    public ViewMainContract viewMain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter();
        mainPresenter.onAttach(viewMain);
    }

    @Test
    public void onAttach_Test() {
        mainPresenter.getInit();
        // Вызыв метода setInputedHistoryText() говорит о том, что viewMain != null
        Mockito.verify(viewMain, times(1)).
                setInputedHistoryText("");
    }
    @Test
    public void onDetach_Test() {
        mainPresenter.onDetach();
        mainPresenter.addNumeral(1);
        // Отсутствие вызыва метод setInputedHistoryText() говорит о том, что viewMain = null
        Mockito.verify(viewMain, times(0)).
                setInputedHistoryText("1");
    }
}
