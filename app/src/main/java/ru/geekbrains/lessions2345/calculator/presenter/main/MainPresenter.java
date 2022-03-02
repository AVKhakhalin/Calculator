package ru.geekbrains.lessions2345.calculator.presenter.main;

import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.core.CalcLogic;
import ru.geekbrains.lessions2345.calculator.core.Constants;
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

    @Override
    public double addNumeral(int newNumeral) {
        double result = calcLogic.addNumeral(newNumeral);
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
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
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearAll() {
        calcLogic.clearAll();
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
        calculate();
        getError();
        viewMainContract.setOutputResultText(calcLogic.getFinalResult());
    }

    @Override
    public void clearOne() {
        if (!calcLogic.clearOne()) {
            calculate();
            getError();
            viewMainContract.setOutputResultText(getFinalResult());
        }
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void clearTwo() {
        if (!calcLogic.clearTwo()) {
            calculate();
            getError();
            viewMainContract.setOutputResultText(getFinalResult());
        }
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void setNewAction(Constants.ACTIONS action) {
        calcLogic.setNewAction(action);
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public void changeSign() {
        calcLogic.changeSign();
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }

    @Override
    public String setNewFunction(Constants.FUNCTIONS typeFuncInBracket) {
        return calcLogic.setNewFunction(typeFuncInBracket);
    }

    @Override
    public String closeBracket() {
        return calcLogic.closeBracket();
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
        viewMainContract.setOutputResultText(calcLogic.getFinalResult());
    }

    @Override
    public void getError() {
        viewMainContract.setErrorText(calcLogic.getErrorCode());
        calcLogic.clearErrorCode();
    }

    @Override
    public void setBracketOpen() {
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", setNewFunction(Constants.FUNCTIONS.FUNC_NO)));
    }

    @Override
    public void setBracketClose() {
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", closeBracket()));
    }

    @Override
    public void getInit() {
        viewMainContract.setOutputResultText(calcLogic.getFinalResult());
        viewMainContract.setInputedHistoryText(String.format(Locale.getDefault(),
                "%s", createOutput()));
    }
}