package ru.geekbrains.lessions2345.calculator.presenter.main;

import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.presenter.PresenterContract;

public interface PresenterMainContract extends PresenterContract {
    void setMaxNumberSymbolsInOutputTextField(int maxNumberSymbolsInOutputTextField);
    double addNumeral(int newNumeral);
    void calculate();
    void setCurZapitay();
    void clearAll();
    void clearOne();
    void clearTwo();
    void setNewAction(Constants.ACTIONS action);
    void changeSign();
    String setNewFunction(Constants.FUNCTIONS typeFuncInBracket);
    String closeBracket();
    boolean getPressedZapitay();
    String createOutput();
    String getFinalResult();
    void setEqual();
    void getError();
    void setBracketOpen();
    void setBracketClose();
    void getInit();
}
