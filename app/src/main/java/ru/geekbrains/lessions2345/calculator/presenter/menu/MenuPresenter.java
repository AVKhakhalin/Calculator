package ru.geekbrains.lessions2345.calculator.presenter.menu;

import ru.geekbrains.lessions2345.calculator.view.ViewContract;
import ru.geekbrains.lessions2345.calculator.view.ui_menu.ViewMenuContract;

public class MenuPresenter implements PresenterMenuContract {

    private ViewMenuContract viewMenu;

    /** Задание различных конструкторов для презентера */ //region
    public MenuPresenter() {
    }
    public MenuPresenter(ViewMenuContract viewMenu) {
        this.viewMenu = viewMenu;
    }
    //endregion

    @Override
    public void onAttach(ViewContract viewContract) {
        viewMenu = (ViewMenuContract) viewContract;
    }

    @Override
    public void onDetach() {
        viewMenu = null;
    }
}
