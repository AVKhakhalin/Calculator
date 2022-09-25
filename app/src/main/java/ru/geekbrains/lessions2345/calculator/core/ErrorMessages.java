package ru.geekbrains.lessions2345.calculator.core;

import java.io.Serializable;

public interface ErrorMessages extends Serializable {
    void sendErrorInputting(Constants.ERRORS_INPUTTING errorInputting);
}
