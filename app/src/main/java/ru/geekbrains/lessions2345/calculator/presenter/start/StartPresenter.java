package ru.geekbrains.lessions2345.calculator.presenter.start;

import ru.geekbrains.lessions2345.calculator.view.ViewContract;
import ru.geekbrains.lessions2345.calculator.view.ui_start.ViewStartContract;

public class StartPresenter implements PresenterStartContract {

    private ViewStartContract viewStart;

    /** Задание различных конструкторов для презентера */ //region
    public StartPresenter() {
    }
    public StartPresenter(ViewStartContract viewStart) {
        this.viewStart = viewStart;
    }
    //endregion

    @Override
    public void onAttach(ViewContract viewContract) {
        viewStart = (ViewStartContract) viewContract;
    }

    @Override
    public void onDetach() {

    }
}
