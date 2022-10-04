package ru.geekbrains.lessions2345.calculator.view.menu;

import ru.geekbrains.lessions2345.calculator.view.ViewConstants;
import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public interface ViewMenuContract extends ViewContract {
    void setCalculatorTheme(ViewConstants.THEMES currentTheme);
    String getNewRadius();
}
