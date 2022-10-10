package ru.geekbrains.lessions2345.calculator.core;

public interface Constants {
    int MAX_NUMBER_SYMBOLS_IN_OUTPUT_TEXT_FIELD = 12;
    int MAX_FRACTIONAL_SYMBOLS_IN_SMALL_NUMBER_OUTPUT_TEXT_FIELD = 7;

    // Действия
    enum ACTIONS {
        ACT_STEP,         // возведение в степень (ACT_STEP)
        ACT_PERC_MULTY,   // вычисление произведения на процент от числа (ACT_PERC_MULTY)
        ACT_PERC_DIV,     // вычисление деления на процент от числа (ACT_PERC_DIV)
        ACT_PERC_MINUS,   // вычисление вычитания процента от числа (ACT_PERC_MINUS)
        ACT_PERC_PLUS,    // вычисление сложения с процентом от числа (ACT_PERC_PLUS)
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
        // TODO: сюда можно дописать другие функции sin, cos, tang, exp, log, факториал (!) и т.д.
    }

    // Ошибки
    // Ошибки во введённой строке
    int ERRORS_IN_STRING_TYPE = 0;
    enum ERRORS_IN_STRING {
        NO,                  // ошибок нет
        SQRT_MINUS,          // подкоренное значение меньше нуля
        BRACKET_DISBALANCE,  // количество открытых скобок
                             // и закрытых скобок не равно друг другу
        ZERO_DIVIDE,         // деление на ноль
    }

    // Ошибки при вводе строки
    int ERRORS_INPUTTING_TYPE = 1;
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
        OPEN_BRACKET_ON_EMPTY_ACTION, // нельзя создать новую открытую скобку,
                                      // потому что не указано перед скобкой действие
        CLOSE_BRACKET_ON_EMPTY_OPEN_BRACKET, // нельзя поставить закрывающую скобку, если
                                    // предварительно не поставить ей соответствующую
                                    // открывающую скобку
        CLOSE_BRACKET_ON_EMPTY,     // нельзя закрывать пустую скобку,
                                    // в скобке как минимум должно быть какое-то число
        CLOSE_BRACKET_ON_ACTION_WITHOUT_NUMBER,  // нельзя закрывающую скобку ставить
                                                // на действии без указания числа
        MULTIPLE_PERCENT_IN_BRACKET // без скобок или в рамках одной скобки нельзя вводить знак
                                    // процента больше одного раза. Если нужно произвести
                                    // вычисление процента несколько раз, то нужно каждую такую
                                    // конструкцию оборачивать в отдельную скобку
    }
}
