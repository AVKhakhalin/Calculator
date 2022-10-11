package ru.geekbrains.lessions2345.calculator.core.model;

import android.annotation.SuppressLint;

import java.util.LinkedList;

import ru.geekbrains.lessions2345.calculator.core.Constants;

// Класс для хранения единичных данных, используется в списке
public class InputData implements Constants {
    // Признак, является ли данный элемент скобкой: true = да; false = нет;
    // открывающаяся скобка является пустым объектом, в неё потом размещается результат вычислений
    // всех операций в данной скобке; пустой объект имеет вначале value = 0d;
    // закрывающая скобка всегда создаётся отдельным элементом без числа;
    // последний объект в скобке имеет свойство isBracket = true,
    // что говорит о том, что скобка закрылась
    private boolean isBracket;
    // Признак закрывающей скобки; у последнего элемента в скобке будет isClose = true,
    // а у остальных элементов в скобке isClose = false
    // открывающая скобка определяется по двум признакам: isBracket = true; isClose = true;
    private boolean isClose;
    // Тип функции, которую нужно применить ко всей скобке;
    // все типы фукнции описаны в интерфейсе Constants
    private FUNCTIONS typeFuncInBracket;
    // Уровень скобки: чем выше уровень, тем более глубоко вложена данная скобка
    // в другие скобки; начальный уровень - 0
    private int bracketLevel;
    // Знак числа: либо +1, либо -1; по-умолчанию, значение +1
    private int sign;
    // Числовое значение
    private double value;
    // Список цифр целой части вводимого числа
    private final LinkedList<Integer> integerPartValue;
    // Список цифр вещественной части вводимого числа
    private final LinkedList<Integer> realPartValue;
    // Признак задания числа; по-умолчанию, все числа задаются 0d; если хотя бы одну цифру
    // в число внесли, то isValue = true; по-умолчанию isValue = false
    private boolean isValue;
    // Производимые действия над числом (по-умолчанию стоит сумма ACT_PLUS):
    // ACT_PERC_MULTY   - вычисление произведения на процент от числа (ACT_PERC_MULTY);
    // ACT_PERC_DIV     - вычисление деления на процент от числа (ACT_PERC_DIV);
    // ACT_PERC_MINUS   - вычисление вычистания процента от числа (ACT_PERC_MINUS);
    // ACT_PERC_PLUS    - вычисление сложения с процентом от числа (ACT_PERC_PLUS);
    // ACT_MULTY        - умножение (ACT_MULTY);
    // ACT_STEP         - возведение в степень (ACT_STEP);
    // ACT_DIV          - деление (ACT_DIV);
    // ACT_MINUS        - вычитание (ACT_MINUS)
    // ACT_PLUS         - сложение (ACT_PLUS);
    private ACTIONS action;
    // Является ли данный элемент процентом: true = да; false = нет
    private boolean isPercent;
    // Количество разрядов после запятой; по-умолчанию -1, что означает, что разрядов нет;
    // при нажатии на кнопку с запятой, к данному числу прибавляется единица
    // и оно становится равным нулю;
    // при дальнейшем нажатии (после запятой) на любую из цифр,
    // к numberZapitay прибавляется единица и к числу прибавляется
    // следующая величина: Math.pow(10, -numberZapitay) * NUMERAL
    private int numberZapitay;
    // Выключение режима ввода дробных знаков при повторном нажатии на запятую;
    // по-умолчанию turnOffZapitay = true, т.е. вводятся только цифры целого числа;
    // если один раз нажать на запятую, то turnOffZapitay = false
    // и начнут вводиться цифры после запятой;
    // если ещё раз нажать на запятую, то turnOffZapitay = false
    // опять начнут вводиться цифры только в целую часть числа и т.д.
    private boolean turnOffZapitay;

    public InputData(boolean _isBracket, boolean _isClose, FUNCTIONS _typeFuncInBracket,
                     int _bracketLevel, int _sign, double _value, boolean _isValue, ACTIONS _action,
                     boolean _isPercent) {
        isBracket = _isBracket;
        isClose = _isClose;
        typeFuncInBracket = _typeFuncInBracket;
        bracketLevel = _bracketLevel;
        sign = _sign;
        value = _value;
        integerPartValue = new LinkedList<>();
        realPartValue = new LinkedList<>();
        isValue = _isValue;
        action = _action;
        isPercent = _isPercent;
        numberZapitay = -1;
        turnOffZapitay = true;
    }

    public InputData(boolean _isBracket, boolean _isClose, FUNCTIONS _typeFuncInBracket,
                     int _bracketLevel, int _sign, double _value, boolean _isValue, ACTIONS _action,
                     boolean _isPercent, int _numberZapitay, boolean _turnOffZapitay) {
        isBracket = _isBracket;
        isClose = _isClose;
        typeFuncInBracket = _typeFuncInBracket;
        bracketLevel = _bracketLevel;
        sign = _sign;
        value = _value;
        integerPartValue = new LinkedList<>();
        realPartValue = new LinkedList<>();
        isValue = _isValue;
        action = _action;
        isPercent = _isPercent;
        numberZapitay = _numberZapitay;
        turnOffZapitay = _turnOffZapitay;
    }

    public boolean getIsBracket() {
        return isBracket;
    }

    public boolean getIsClose() {
        return isClose;
    }

    public FUNCTIONS getTypeFuncInBracket() {
        return typeFuncInBracket;
    }

    public int getBracketLevel() {
        return bracketLevel;
    }

    public int getSign() {
        return sign;
    }

    public double getValue() {
        return value;
    }

    @SuppressLint("DefaultLocale")
    public String getIntegerPartValue() {
        String resultString = "";
        for (Integer integer: integerPartValue) {
            resultString = String.format("%s%d", resultString, integer);
        }
        return resultString;
    }

    @SuppressLint("DefaultLocale")
    public String getRealPartValue() {
        String resultString = "";
        for (Integer integer: realPartValue) {
            resultString = String.format("%s%d", resultString, integer);
        }
        return resultString;
    }

    public boolean getIsValue() {
        return isValue;
    }

    public ACTIONS getAction() {
        return action;
    }

    public boolean getIsPercent() {
        return isPercent;
    }

    public int getNumberZapitay()
    {
        return numberZapitay;
    }

    public boolean getTurnOffZapitay() {
        return turnOffZapitay;
    }

    public void setIsBracket(boolean bracket) {
        isBracket = bracket;
    }

    public void setIsClose(boolean close)
    {
        isClose = close;
    }

    public void setTypeFuncInBracket(FUNCTIONS _typeFuncInBracket) {
        typeFuncInBracket = _typeFuncInBracket;
    }

    public void setBracketLevel(int _bracketLevel) {
        bracketLevel = _bracketLevel;
    }

    public void setSign(int _sign) {
        sign = _sign;
    }

    public void setValue(double _value) {
        value = _value;
    }

    public void setIntegerPartValue(Integer numeral)
    {
        integerPartValue.add(numeral);
    }

    public void setRealPartValue(Integer numeral)
    {
        realPartValue.add(numeral);
    }

    public void setIntegerPartValueDecreaseOne()
    {
        integerPartValue.removeLast();
    }

    public void setRealPartValueDecreaseOne()
    {
        realPartValue.removeLast();
    }

    public void setIsValue(boolean _isValue) {
        isValue = _isValue;
    }

    public void setAction(ACTIONS _action) {
        action = _action;
    }

    public void setIsPercent(boolean percent) {
        isPercent = percent;
    }

    public void setNumberZapitay(int _numberZapitay)
    {
        numberZapitay = _numberZapitay;
    }

    public void setTurnOffZapitay(boolean _turnOffZapitay) {
        turnOffZapitay = _turnOffZapitay;
    }
}
