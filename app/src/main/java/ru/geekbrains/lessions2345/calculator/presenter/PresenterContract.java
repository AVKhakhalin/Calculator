package ru.geekbrains.lessions2345.calculator.presenter;

import ru.geekbrains.lessions2345.calculator.view.ViewContract;

public interface PresenterContract {
    void onAttach(ViewContract viewContract);
    void onDetach();
}
