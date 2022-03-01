package ru.geekbrains.lessions2345.calculator.presenter.main;

import ru.geekbrains.lessions2345.calculator.core.CalcLogic;
import ru.geekbrains.lessions2345.calculator.view.ui_main.ViewMainContract;

public class MainPresenter implements PresenterMainContract {
    private CalcLogic calcLogic = new CalcLogic();
    private ViewMainContract viewMainContract;

    public MainPresenter(ViewMainContract viewMainContract) {
        this.viewMainContract = viewMainContract;
    }

    @Override
    public void setMaxNumberSymbolsInOutputTextField(int maxNumberSymbolsInOutputTextField) {
        calcLogic.setMaxNumberSymbolsInOutputTextField(maxNumberSymbolsInOutputTextField);
    }

}
