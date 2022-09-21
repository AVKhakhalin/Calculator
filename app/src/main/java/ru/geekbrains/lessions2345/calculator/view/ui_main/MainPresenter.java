package ru.geekbrains.lessions2345.calculator.view.ui_main;

import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.core.CalcLogic;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public class MainPresenter implements PresenterMainContract {
    private final CalcLogic calcLogic = new CalcLogic();
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
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
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
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearAll() {
        calcLogic.clearAll();
        calculate();
        getError();
        if (viewMain != null) viewMain.setOutputResultText(calcLogic.getFinalResult());
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearOne() {
        if (calcLogic.clearOne()) {
            // TODO: Действие, если нужно как-то по-особому обновить поле с результатом
        }
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearTwo() {
        if (calcLogic.clearTwo()) {
            // TODO: Действие, если нужно как-то по-особому обновить поле с результатом
        }
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void setNewAction(Constants.ACTIONS action) {
        calcLogic.setNewAction(action);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void changeSign() {
        calcLogic.changeSign();
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public String setNewFunction(Constants.FUNCTIONS typeFuncInBracket) {
        String result = calcLogic.setNewFunction(typeFuncInBracket);
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
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
        if (viewMain != null) viewMain.setOutputResultText(calcLogic.getFinalResult());
    }

    @Override
    public void getError() {
        if (viewMain != null) viewMain.setErrorText(calcLogic.getErrorCode());
        calcLogic.clearErrorCode();
    }

    @Override
    public void setBracketOpen() {
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", setNewFunction(Constants.FUNCTIONS.FUNC_NO)));
    }

    @Override
    public void setBracketClose() {
        if (viewMain != null) viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                "%s", calcLogic.closeBracket()));
    }

    @Override
    public void getInit() {
        if (viewMain != null) {
            viewMain.setOutputResultText(calcLogic.getFinalResult());
            viewMain.setInputtedHistoryText(String.format(Locale.getDefault(),
                    "%s", createOutput()));
        }
    }

    @Override
    public void onAttach(ViewContract viewContract) {
        if (viewContract != null) viewMain = (ViewMainContract) viewContract;
    }

    @Override
    public void onDetach() {
        if (viewMain != null) viewMain = null;
    }
}