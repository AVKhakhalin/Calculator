package ru.geekbrains.lessions2345.calculator.view.ui_main;

import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public interface ViewMainContract extends ViewContract {
    void setInputtedHistoryText(String newText);
    void setOutputResultText(String newText);
    void showErrorInString(Constants.ERRORS_IN_STRING error);
    void showErrorInputting(Constants.ERRORS_INPUTTING error);
}
