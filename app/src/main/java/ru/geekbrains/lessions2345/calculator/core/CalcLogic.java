package ru.geekbrains.lessions2345.calculator.core;

import java.io.Serializable;

public interface CalcLogic extends Serializable {
    // Перевести калькулятор в режим работы с целой частью числа (true)
    // или с его дробной частью (false)
    void setCurZapitay();
    // Получить текущее значение режима работы с целой (true) и дробной чатью (false) числа
    boolean getPressedZapitay();
    // Добавить число из 1 цифры или ещё одну цифру к текущему числу
    // (в зависимости от режима работы с целой или с дробной частью, добавление происходит
    // или в целую часть, или в дробную часть
    double addNumeral(int newNumeral);
    // Удаление всей введённой строки
    void clearAll();
    // Удаление цифры в числе или элемента - скобки, числа (состоящего из 1 цифры), функции.
    // В зависимости от режима работы с целой или с дробной частью,
    // удаляется или 1 цифра в целой части, или 1 цифра в дробной части числа
    boolean clearOne();
    // Удаление текущего элемента - скобки, числа, функции
    boolean clearTwo();
    // Задание действия
    void setNewAction(Constants.ACTIONS action);
    // Изменение знака числа или функции (для действий плюс и минус эта операция также приводит
    // к их смене на противоположное действие)
    void changeSign();
    // Создание открывающей скобки (с параметром Constants.FUNCTIONS.FUNC_NO) и функции
    String setNewFunction(Constants.FUNCTIONS typeFuncInBracket);
    // Создание закрывающей скобки
    String closeBracket();
    // Установка максимального количества символов, из которых состоит конечный результат
    void setMaxNumberSymbolsInOutputTextField(int maxNumberSymbolsInOutputTextField);
    // Вычисление результата на основании введённой строки скобок, чисел, действий и функций
    void calculate();
    // Получение результата вычислений в виде переменной типа String
    String getFinalResult();
    // Вывод информации о введённой строке
    String createOutput();
    // Получение кода ошибки
    Constants.ERRORS_IN_STRING getErrorCode();
    // Очистка кода ошибки во введённых данных для проведения повторного расчёта результата
    void clearErrorCode();
}
