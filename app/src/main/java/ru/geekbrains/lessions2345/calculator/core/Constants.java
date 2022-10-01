package ru.geekbrains.lessions2345.calculator.core;

public interface Constants {
    int MAX_NUMBER_SYMBOLS_IN_OUTPUT_TEXT_FIELD = 12;
    int MAX_FRACTIONAL_SYMBOLS_IN_SMALL_NUMBER_OUTPUT_TEXT_FIELD = 7;

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
    }

    // Функции
    enum FUNCTIONS {
        FUNC_NO,     // нет функции
        // (с данным типом открывается и закрывается обычная скобка)
        FUNC_SQRT    // извлечение квадратного корня (FUNC_SQRT)
        // TODO: сюда можно дописать другие функции sin, cos, tang, exp, log и т.д.
    }

    // Ошибки
    // Ошибки во введённой строке
    enum ERRORS_IN_STRING {
        NO,                  // ошибок нет
        SQRT_MINUS,          // подкоренное значение меньше нуля
        BRACKET_DISBALANCE,  // количество открытых скобок
        // и закрытых скобок не равно друг другу
        ZERO_DIVIDE,         // деление на ноль
        BRACKETS_EMPTY      // внутри скобок отсутствует число
    }

    // Ошибки при вводе строки
    enum ERRORS_INPUTTING {
        NUMBER_AFTER_BRACKET,      // ставится число сразу же после скобки
                                   // без указания действий с ним
        MANY_ZERO_IN_INTEGER_PART, // Показать уведомление о том,
                                   // что для задания целой части числа вполне хватит и одного нуля
        INPUT_NUMBER_FIRST,        // нужно сначала ввести число
        PERCENT_NEEDS_TWO_NUMBERS, // для применения процента нужно ввести два числа и любую
                                   // следующую арифметическую операцию между ними: *, /, +, -
        CHANGE_SIGN_EMPTY,         // нельзя производить смену знака,
                                   // предварительно не задав число или функцию
        CLOSE_BRACKET_ON_EMPTY,    // нельзя поставить закрывающую скобку, если предварительно
                                   // не поставить ей соответствующую открывающую скобку
    }
}
