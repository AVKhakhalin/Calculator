package ru.geekbrains.lessions2345.calculator.view.main;

import java.io.Serializable;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public interface ViewMainContract extends ViewContract, Serializable {
    void setInputtedHistoryText(String newText);
    void setOutputResultText(String newText);
    void showErrorInString(Constants.ERRORS_IN_STRING error);
    void showErrorInputting(Constants.ERRORS_INPUTTING error);
    void setDisplayWidthAndCurRadiusButtons(int displayWidth, int curRadiusButtons);
    int getDisplayWidth();
    int getCurRadiusButtons();
    void setCurRadiusButtons(int curRadiusButtons);
}
