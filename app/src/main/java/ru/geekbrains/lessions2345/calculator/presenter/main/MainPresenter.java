package ru.geekbrains.lessions2345.calculator.presenter.main;

import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.core.CalcLogic;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;
import ru.geekbrains.lessions2345.calculator.view.ui_main.ViewMainContract;

public class MainPresenter implements PresenterMainContract {
    private CalcLogic calcLogic = new CalcLogic();
    private ViewMainContract viewMain;

    /** Задание различных конструкторов для презентера */ //region
    public MainPresenter() {
    }
    public MainPresenter(ViewMainContract viewMain) {
        this.viewMain = viewMain;
    }
    //endregion

    @Override
    public void setMaxNumberSymbolsInOutputTextField(int maxNumberSymbolsInOutputTextField) {
        calcLogic.setMaxNumberSymbolsInOutputTextField(maxNumberSymbolsInOutputTextField);
    }

    @Override
    public double addNumeral(int newNumeral) {
        double result = calcLogic.addNumeral(newNumeral);
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        return result;
    }

    @Override
    public void calculate() {
        calcLogic.calculate();
    }

    @Override
    public void setCurZapitay() {
        calcLogic.setCurZapitay();
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearAll() {
        calcLogic.clearAll();
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        calculate();
        getError();
        viewMain.setOutputResultText(calcLogic.getFinalResult());
    }

    @Override
    public void clearOne() {
        if (calcLogic.clearOne()) {
            // TODO: Обновление поля с результатом, доделать, если нужно
//            setEqual();
        }
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearTwo() {
        if (calcLogic.clearTwo()) {
            // TODO: Обновление поля с результатом, доделать, если нужно
//            setEqual();
        }
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
            "%s", createOutput()));
    }

    @Override
    public void setNewAction(Constants.ACTIONS action) {
        calcLogic.setNewAction(action);
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void changeSign() {
        calcLogic.changeSign();
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public String setNewFunction(Constants.FUNCTIONS typeFuncInBracket) {
        String result = calcLogic.setNewFunction(typeFuncInBracket);
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        return result;
    }

    @Override
    public boolean getPressedZapitay() {
        return calcLogic.getPressedZapitay();
    }

    @Override
    public String createOutput() {
        return calcLogic.createOutput();
    }

    @Override
    public String getFinalResult() {
        return calcLogic.getFinalResult();
    }

    @Override
    public void setEqual() {
        calcLogic.calculate();
        getError();
        viewMain.setOutputResultText(calcLogic.getFinalResult());
    }

    @Override
    public void getError() {
        viewMain.setErrorText(calcLogic.getErrorCode());
        calcLogic.clearErrorCode();
    }

    @Override
    public void setBracketOpen() {
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", setNewFunction(Constants.FUNCTIONS.FUNC_NO)));
    }

    @Override
    public void setBracketClose() {
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", calcLogic.closeBracket()));
    }

    @Override
    public void getInit() {
        viewMain.setOutputResultText(calcLogic.getFinalResult());
        viewMain.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void onAttach(ViewContract viewContract) {
        viewMain = (ViewMainContract) viewContract;
    }

    @Override
    public void onDetach() {
        viewMain = null;
    }
}