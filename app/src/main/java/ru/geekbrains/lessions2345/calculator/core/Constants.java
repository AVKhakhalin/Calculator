package ru.geekbrains.lessions2345.calculator.core;

public interface Constants {
    // Действия
    enum ACTIONS {
        ACT_STEP,         // возведение в степень (ACT_STEP)
        ACT_PERS_MULTY,   // вычисление произведения на процент от числа (ACT_PERS_MULTY)
        ACT_PERS_DIV,     // вычисление деления на процент от числа (ACT_PERS_DIV)
        ACT_PERS_MINUS,   // вычисление вычитания процента от числа (ACT_PERS_MINUS)
        ACT_PERS_PLUS,    // вычисление сложения с процентом от числа (ACT_PERS_PLUS)
        ACT_MULTY,        // умножение (ACT_MULTY)
        ACT_DIV,          // деление (ACT_DIV)
        ACT_MINUS,        // вычитание (ACT_MINUS)
        ACT_PLUS          // сложение (ACT_PLUS)
    };

    // Функции
    enum FUNCTIONS {
        FUNC_NO,     // нет функции
        // (с данным типом открывается и закрывается обычная скобка)
        FUNC_SQRT    // извлечение квадратного корня (FUNC_SQRT)
        // TODO: сюда можно дописать другие функции sin, cos, tang, exp, log и т.д.
    };

    // Ошибки
    enum ERRORS {
        NO,                  // ошибок нет
        SQRT_MINUS,          // подкоренное значение меньше нуля
        BRACKET_DISBALANCE,  // количество открытых скобок
        // и закрытых скобок не равно друг другу
        ZERO_DIVIDE,         // деление на ноль
        BRACKETS_EMPTY       // внутри скобок отсутствует число
    };
}
