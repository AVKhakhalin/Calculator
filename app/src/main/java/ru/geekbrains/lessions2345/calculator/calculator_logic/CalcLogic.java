package ru.geekbrains.lessions2345.calculator.calculator_logic;

import android.content.Context;
import android.content.res.Resources;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.R;

public class CalcLogic implements Constants {
    LinkedList<Dates> inputNumbers;                // Создание класса со значениями
    int maxBracketLevel;                           // Максимальный уровень вложенности во всех скобках
    int curBracketLevel;                           // Создание переменной с текущим максимальный уровнем вложенности скобок
    int curNumber;                                 // Текущий индекс создаваемой операции (в операцию входит как сама операция, так и число в ней участвующее)
    LinkedList<Dates> inputNumbersForBracketCalc;  // Класс со значениями вместе со скобками;
                                                   // в результате всех вычислений, в данном классе не останется ни одной скобки
    LinkedList<Dates> inputNumbersForBaseCalc;     // Класс со значениями для вычислений внутри скобок
    ListIterator<Dates> iterInputNumbersForCalc;   // Создание итератора для навигации по списку со значениями
    boolean pressedZapitay = false;                // Признак нажатой кнопки запятой
    double finalResult = 0d;                       // Переменная, хранящая окончательный результат вычислений
    private ERRORS errorCode = ERRORS.NO;          // Ошибки вычислений

    public CalcLogic() {
        inputNumbers = new LinkedList<>();
        maxBracketLevel = 0;
        curBracketLevel = 0;
        curNumber = 0;
        inputNumbersForBaseCalc = new LinkedList<>();
        iterInputNumbersForCalc = inputNumbersForBaseCalc.listIterator();

        // Создание первого пустого элемента
        add(false, false, FUNCTIONS.FUNC_NO, 1, 0d, false, ACTIONS.ACT_PLUS, false);
    }

    public double addNumeral(int newNumeral) {
        if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == false)) {
            add(false, false, FUNCTIONS.FUNC_NO, 1, 0d, false, ACTIONS.ACT_PLUS, false);
            curNumber++;
        }
        if ((inputNumbers.get(curNumber).getValue() == 0d) && (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) && (newNumeral == 0) && (pressedZapitay == false)) {
            // Показать уведомление о том, что для задания целой части числа вполне хватит и одного нуля
        } else {
            double intPartValue = 0d;
            double realPartValue = 0d;
            if (pressedZapitay == true) {
                inputNumbers.get(curNumber).setRealPartValue(newNumeral);
                inputNumbers.get(curNumber).setNumberZapitay(inputNumbers.get(curNumber).getNumberZapitay() + 1);
                if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                    intPartValue = Double.parseDouble(inputNumbers.get(curNumber).getIntegerPartValue());
                }
                realPartValue = Double.parseDouble(inputNumbers.get(curNumber).getRealPartValue()) * Math.pow(10, -1 * inputNumbers.get(curNumber).getRealPartValue().length());
            } else {
                if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) // Подчищение введенной ранее нулевой цифры
                {
                    intPartValue = Double.parseDouble(inputNumbers.get(curNumber).getIntegerPartValue());
                    if (intPartValue == 0d) {
                        while (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                            inputNumbers.get(curNumber).setIntegerPartValueDecreaseOne();
                        }
                    }
                }
                inputNumbers.get(curNumber).setIntegerPartValue(newNumeral);
                intPartValue = Double.parseDouble(inputNumbers.get(curNumber).getIntegerPartValue());
                if (inputNumbers.get(curNumber).getRealPartValue().length() > 0) {
                    realPartValue = Double.parseDouble(inputNumbers.get(curNumber).getRealPartValue()) * Math.pow(10, -1 * inputNumbers.get(curNumber).getRealPartValue().length());
                }
            }
            set(curNumber, inputNumbers.get(curNumber).getIsBracket(), false, FUNCTIONS.FUNC_NO, curBracketLevel, inputNumbers.get(curNumber).getSign(), intPartValue + realPartValue, true, inputNumbers.get(curNumber).getAction(), inputNumbers.get(curNumber).getIsPercent());
        }
        return inputNumbers.get(curNumber).getValue();
    }

    // Метод для добавления нового элемента
    public void add(boolean _isBracket, boolean _isClose, FUNCTIONS _typeFuncInBracket, int _sign, double _value, boolean _isValue, ACTIONS _action, boolean _isPercent) {
        inputNumbers.add(new Dates(_isBracket, _isClose, _typeFuncInBracket, curBracketLevel, _sign, _value, _isValue, _action, _isPercent));
    }

    // Метод для установки параметров, необходимых для проведения расчётов
    public void set(int posNumber, boolean _isBracket, boolean _isClose, FUNCTIONS _typeFuncInBracket, int _bracketLevel, int _sign, double _value, boolean _isValue, ACTIONS _action, boolean _isPercent) {
        inputNumbers.get(posNumber).setIsBracket(_isBracket);
        inputNumbers.get(posNumber).setIsClose(_isClose);
        inputNumbers.get(posNumber).setTypeFuncInBracket(_typeFuncInBracket);
        inputNumbers.get(posNumber).setBracketLevel(_bracketLevel);
        inputNumbers.get(posNumber).setSign(_sign);
        inputNumbers.get(posNumber).setValue(_value);
        inputNumbers.get(posNumber).setIsValue(_isValue);
        inputNumbers.get(posNumber).setAction(_action);
        inputNumbers.get(posNumber).setIsPercent(_isPercent);
    }

    // Метод для удаления всех созданных элементов
    public void clearAll() {
        inputNumbers = new LinkedList<>();
        maxBracketLevel = 0;
        curBracketLevel = 0;
        curNumber = 0;
        // Создание первого пустого элемента
        add(false, false, FUNCTIONS.FUNC_NO, 1, 0d, false, ACTIONS.ACT_PLUS, false);
        pressedZapitay = false;
    }

    // Метод для коррекции последнего созданного элемента
    public boolean clearOne() {
        if (inputNumbers.get(curNumber).getIsValue() == true) {
            if (pressedZapitay == true) {
                if (inputNumbers.get(curNumber).getRealPartValue().length() > 0) {
                    inputNumbers.get(curNumber).setRealPartValueDecreaseOne();
                } else if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                    inputNumbers.get(curNumber).setIntegerPartValueDecreaseOne();
                }
            } else {
                if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                    inputNumbers.get(curNumber).setIntegerPartValueDecreaseOne();
                } else if (inputNumbers.get(curNumber).getRealPartValue().length() > 0) {
                    inputNumbers.get(curNumber).setRealPartValueDecreaseOne();
                }
            }

            if ((inputNumbers.get(curNumber).getIntegerPartValue().length() == 0) && (inputNumbers.get(curNumber).getRealPartValue().length() == 0)) {
                inputNumbers.get(curNumber).setSign(1);
                inputNumbers.get(curNumber).setIsValue(false);
                inputNumbers.get(curNumber).setValue(0d);
                inputNumbers.get(curNumber).setTurnOffZapitay(true);
                pressedZapitay = false;
            } else {
                double intPartValue = 0d;
                double realPartValue = 0d;
                if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                    intPartValue = Double.parseDouble(inputNumbers.get(curNumber).getIntegerPartValue());
                }

                if (inputNumbers.get(curNumber).getRealPartValue().length() > 0) {
                    realPartValue = Double.parseDouble(inputNumbers.get(curNumber).getRealPartValue()) * Math.pow(10, -1 * inputNumbers.get(curNumber).getRealPartValue().length());
                }
                inputNumbers.get(curNumber).setValue(intPartValue + realPartValue);
            }
        } else {
            if (curNumber > 0) {
                if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == true)) {
                    if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MULTY) || ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_DIV)) || ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_PLUS)) || ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MINUS))) {
                        // Определение позиции (positionBracketBegin)
                        int positionBracketBegin = curNumber;
                        Dates prevDates;
                        int counter = 0;
                        iterInputNumbersForCalc = inputNumbers.listIterator(curNumber);
                        while (iterInputNumbersForCalc.hasPrevious()) {
                            prevDates = iterInputNumbersForCalc.previous();
                            counter++;
                            if ((prevDates.getBracketLevel() == curBracketLevel + 1) && (prevDates.getIsBracket() == true) && (prevDates.getIsClose() == false)) {
                                positionBracketBegin -= counter;
                                break;
                            }
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_MULTY) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MULTY);
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_DIV) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_DIV);
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_PLUS) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PLUS);
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_MINUS) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MINUS);
                        }
                    }

                    curBracketLevel++;
                    if (maxBracketLevel < curBracketLevel) {
                        maxBracketLevel = curBracketLevel;
                    }
                }
                if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == false)) {
                    curBracketLevel--;
                }
                inputNumbers.remove(curNumber);
                curNumber--;
            } else {
                clearAll();
                return false;
            }
        }
        return true;
    }

    // Метод для удалению последнего созданного элемента
    public boolean clearTwo() {
        if (curNumber > 0) {
            if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == true)) {
                if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MULTY) || ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_DIV)) || ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_PLUS)) || ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MINUS))) {
                    // Определение позиции (positionBracketBegin)
                    int positionBracketBegin = curNumber;
                    Dates prevDates;
                    int counter = 0;
                    iterInputNumbersForCalc = inputNumbers.listIterator(curNumber);
                    while (iterInputNumbersForCalc.hasPrevious()) {
                        prevDates = iterInputNumbersForCalc.previous();
                        counter++;
                        if ((prevDates.getBracketLevel() == curBracketLevel + 1) && (prevDates.getIsBracket() == true) && (prevDates.getIsClose() == false)) {
                            positionBracketBegin -= counter;
                            break;
                        }
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_MULTY) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MULTY);
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_DIV) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_DIV);
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_PLUS) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PLUS);
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PERS_MINUS) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MINUS);
                    }
                }

                curBracketLevel++;
                if (maxBracketLevel < curBracketLevel) {
                    maxBracketLevel = curBracketLevel;
                }
            }
            if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == false)) {
                curBracketLevel--;
            }
            inputNumbers.remove(curNumber);
            curNumber--;
        } else {
            clearAll();
            return false;
        }
        return true;
    }

    // Класс для копирования списков с объектами
    private LinkedList<Dates> copyLinkedList(LinkedList<Dates> originalInputNumbers) {
        Dates curDates;
        LinkedList<Dates> newInputNumbers = new LinkedList<>();

        iterInputNumbersForCalc = originalInputNumbers.listIterator();
        while (iterInputNumbersForCalc.hasNext()) {
            curDates = iterInputNumbersForCalc.next();
            newInputNumbers.add(new Dates(curDates.getIsBracket(), curDates.getIsClose(), curDates.getTypeFuncInBracket(), curDates.getBracketLevel(), curDates.getSign(), curDates.getValue(), curDates.getIsValue(), curDates.getAction(), curDates.getIsPercent(), curDates.getNumberZapitay(), curDates.getTurnOffZapitay()));
        }
        return newInputNumbers;
    }

    // Основной вычисляющий метод
    public void calculate() {
        int counter = -1;
        int startBracketIndex = -1;
        double result = 0d;
        Dates curDates;
        boolean isBracketExist = true;
        boolean equalOpenToClose = true;
        int bracketBalance = 0;

        inputNumbersForBracketCalc = copyLinkedList(inputNumbers); // Создание дубликата класса с числами для обработки выражения со скобками

        // Проверка незакрытых скобок
        // Корректировка открытой, но не закрытой пустой скобки (т.е. если открыли скобку, но за ней не следует никакого числа)
        iterInputNumbersForCalc = inputNumbersForBracketCalc.listIterator();
        while (iterInputNumbersForCalc.hasNext()) {
            curDates = iterInputNumbersForCalc.next();
            if ((curDates.getIsBracket() == true) && (curDates.getIsClose() == false)) {
                bracketBalance++;
            }
            if (curDates.getIsClose() == true) {
                bracketBalance--;
            }
        }

        if (bracketBalance == 0) {
            // Здесь происходит обработка всех скобок
            while (isBracketExist == true) {
                for (int i = maxBracketLevel; i > 0; i--) {
                    iterInputNumbersForCalc = inputNumbersForBracketCalc.listIterator();
                    counter = -1;
                    while (iterInputNumbersForCalc.hasNext()) {
                        counter++;
                        curDates = iterInputNumbersForCalc.next();
                        if (curDates.getBracketLevel() == i) {
                            startBracketIndex = counter; // Не забыть вернуть этой переменной значение -1, когда встретим закрывающую скобку

                            inputNumbersForBaseCalc = new LinkedList<>();
                            while (iterInputNumbersForCalc.hasNext()) {
                                curDates = iterInputNumbersForCalc.next();
                                if ((curDates.getBracketLevel() == i) && (curDates.getIsClose() == false)) {
                                    inputNumbersForBaseCalc.add(new Dates(curDates.getIsBracket(), curDates.getIsClose(), curDates.getTypeFuncInBracket(), curDates.getBracketLevel(), curDates.getSign(), curDates.getValue(), curDates.getIsValue(), curDates.getAction(), curDates.getIsPercent(), curDates.getNumberZapitay(), curDates.getTurnOffZapitay()));
                                    iterInputNumbersForCalc.remove();
                                } else if ((curDates.getBracketLevel() == i) && (curDates.getIsClose() == true)) {
                                    iterInputNumbersForCalc.remove();
                                    break;
                                } else {
                                    if (iterInputNumbersForCalc.hasPrevious() == true) {
                                        iterInputNumbersForCalc.previous();
                                    }
                                    break;
                                }
                            }
                            result = moveOnWithoutBracket(inputNumbersForBaseCalc);
                            if (errorCode != ERRORS.NO) {
                                return;
                            }
                            inputNumbersForBracketCalc.get(startBracketIndex).setValue(doFunction(result, inputNumbersForBracketCalc.get(startBracketIndex).getTypeFuncInBracket()));
                            if (errorCode != ERRORS.NO) {
                                return;
                            }
                            inputNumbersForBracketCalc.get(startBracketIndex).setTypeFuncInBracket(FUNCTIONS.FUNC_NO);
                            inputNumbersForBracketCalc.get(startBracketIndex).setIsValue(true);
                            inputNumbersForBracketCalc.get(startBracketIndex).setBracketLevel(inputNumbersForBracketCalc.get(startBracketIndex).getBracketLevel() - 1);
                            inputNumbersForBracketCalc.get(startBracketIndex).setIsBracket(false);
                            inputNumbersForBracketCalc.get(startBracketIndex).setIsClose(false);

                        }
                    }
                }

                // Проверка наличия скобок в списке
                isBracketExist = false;
                iterInputNumbersForCalc = inputNumbersForBracketCalc.listIterator();
                while (iterInputNumbersForCalc.hasNext()) {
                    curDates = iterInputNumbersForCalc.next();
                    if (curDates.getIsBracket() == true) {
                        isBracketExist = true;
                        break;
                    }
                }
            }

            // Здесь происходит обработка списка, в котором не осталось ни одной скобки
            inputNumbersForBaseCalc = copyLinkedList(inputNumbersForBracketCalc); // Создание дубликата класса с числами для обработки без скобок
            result = moveOnWithoutBracket(inputNumbersForBaseCalc);
        } else {
            // Сообщить об ошибке: в анализируемом выражении есть незакрытые скобки
            errorCode = ERRORS.BRACKET_DISBALANCE;
        }

        // Сохранение результирующего значения
        finalResult = result;
    }

    // Работа функций
    private double doFunction(double value, FUNCTIONS typeFuncInBracket) {
        double result = value;

        switch (typeFuncInBracket) {
            case FUNC_SQRT:
                if (value >= 0) {
                    result = Math.sqrt(value);
                } else {
                    // Добавить сообщение об ошибке: выражение под корнем не может быть меньше нуля!
                    errorCode = ERRORS.SQRT_MINUS;
                }
                break;
            default:
                break;
        }
        return result;
    }

    // Вычисление элементов без скобок
    private double moveOnWithoutBracket(LinkedList<Dates> curNumbersForCals) {
        double result = 0d;
        Dates curDates;
        Dates prevDates;

        // Проход по всем элементам, пока не останется только один элемент с результатом
        if (curNumbersForCals.size() > 0) {
            prevDates = curNumbersForCals.get(0);
        } else {
            // Ошибка: внутри скобки нет никакого числа
            errorCode = ERRORS.BRACKETS_EMPTY;
            return result;
        }
        for (ACTIONS action : ACTIONS.values()) {
            if (action != ACTIONS.ACT_DIV) // Учёт равнозначности операций деления и умножения
            {
                iterInputNumbersForCalc = curNumbersForCals.listIterator();
                while (iterInputNumbersForCalc.hasNext()) {
                    curDates = iterInputNumbersForCalc.next();

                    if (action == ACTIONS.ACT_MULTY) // Учёт равнозначности операций деления и умножения
                    {
                        if ((curDates.getAction() == ACTIONS.ACT_MULTY) && (curNumbersForCals.size() > 1)) {
                            prevDates.setValue(doBaseActions(prevDates.getValue() * prevDates.getSign(), curDates.getValue() * curDates.getSign(), curDates.getAction()));
                            prevDates.setSign(1);
                            iterInputNumbersForCalc.remove();
                        } else if ((curDates.getAction() == ACTIONS.ACT_DIV) && (curNumbersForCals.size() > 1)) {
                            prevDates.setValue(doBaseActions(prevDates.getValue() * prevDates.getSign(), curDates.getValue() * curDates.getSign(), curDates.getAction()));
                            prevDates.setSign(1);
                            iterInputNumbersForCalc.remove();
                        } else {
                            prevDates = curDates;
                        }
                    } else if ((curDates.getAction() == action) && (curNumbersForCals.size() > 1)) // Выполнение всех остальных операций (не деления и не умножения)
                    {
                        prevDates.setValue(doBaseActions(prevDates.getValue() * prevDates.getSign(), curDates.getValue() * curDates.getSign(), curDates.getAction()));
                        prevDates.setSign(1);
                        iterInputNumbersForCalc.remove();
                    } else {
                        prevDates = curDates;
                    }
                }
            }
            if (curNumbersForCals.size() == 1) {
                break;
            }
        }

        result = curNumbersForCals.get(0).getValue() * curNumbersForCals.get(0).getSign();

        return result;
    }

    // Действия над числами
    private double doBaseActions(double number1, double number2, ACTIONS action) {
        double result = 0d;
        switch (action) {
            case ACT_STEP:
                result = Math.pow(number1, number2);
                break;
            case ACT_PERS_DIV:
                result = (number1 * number2 != 0 ? (number1 / (number1 * number2 / 100)) : (0));
                // Ошибка: деление на ноль
                if (number1 * number2 == 0) {
                    errorCode = ERRORS.ZERO_DIVIDE;
                }
                break;
            case ACT_PERS_MULTY:
                result = number1 * (number1 * number2 / 100);
                break;
            case ACT_PERS_PLUS:
                result = number1 + number1 * number2 / 100;
                break;
            case ACT_PERS_MINUS:
                result = number1 - number1 * number2 / 100;
                break;
            case ACT_DIV:
                result = (number2 != 0 ? (number1 / number2) : (0));
                // Ошибка: деление на ноль
                if (number2 == 0) {
                    errorCode = ERRORS.ZERO_DIVIDE;
                }
                break;
            case ACT_MULTY:
                result = number1 * number2;
                break;
            case ACT_PLUS:
                result = number1 + number2;
                break;
            case ACT_MINUS:
                result = number1 - number2;
                break;
            default:
                break;
        }
        return result;
    }

    // Отслеживание нажатия на кнопку с запятой (переключение между режимами работы с вещественными и целыми числами)
    public void setCurZapitay() {
        pressedZapitay = (pressedZapitay == false ? true : false); // Отслеживание нажатия на кнопку "Zapitay"

        if (inputNumbers.get(curNumber).getTurnOffZapitay() == true) {
            inputNumbers.get(curNumber).setTurnOffZapitay(false);
            if (inputNumbers.get(curNumber).getNumberZapitay() < 0) {
                inputNumbers.get(curNumber).setNumberZapitay(0);
            }
        } else {
            inputNumbers.get(curNumber).setTurnOffZapitay(true);
        }
    }

    // Установка для функции, а также новой скобки, поскольку функции без скобок не бывает
    public String setNewFunction(FUNCTIONS typeFuncInBracket) // Данный метод не только устанавливает новую функцию, но и открывает скобку
    {
        if (inputNumbers.get(curNumber).getIsBracket() == true) {
            curBracketLevel++;
            add(true, false, typeFuncInBracket, 1, 0d, false, ACTIONS.ACT_PLUS, false);
            curNumber++;
        } else {
            if ((inputNumbers.get(curNumber).getTypeFuncInBracket() == FUNCTIONS.FUNC_NO) && (inputNumbers.get(curNumber).getIsValue() == false)) {
                curBracketLevel++;
                inputNumbers.get(curNumber).setIsBracket(true);
                inputNumbers.get(curNumber).setBracketLevel(curBracketLevel);
                inputNumbers.get(curNumber).setTypeFuncInBracket(typeFuncInBracket);
            }
        }
        if (maxBracketLevel < curBracketLevel) {
            maxBracketLevel = curBracketLevel;
        }
        return createOutput();
    }

    // Установка закрывающейся скобки
    public String closeBracket() {
        if (curBracketLevel > 0) {
            add(true, true, FUNCTIONS.FUNC_NO, 1, 0d, false, ACTIONS.ACT_PLUS, false);
            curBracketLevel--;
            curNumber++;
        }
        return createOutput();
    }

    // Установка нового действия над числами
    public void setNewAction(ACTIONS action) {
        int positionBracketBegin = curNumber;
        boolean isPrevDatesComplited = false;
        if (curNumber > 0) {
            iterInputNumbersForCalc = inputNumbers.listIterator();
            Dates prevDates = iterInputNumbersForCalc.next();
            if ((prevDates.getIsValue() == true) || ((prevDates.getIsBracket() == true) && (prevDates.getIsClose() == false)) || ((prevDates.getIsBracket() == true) && (prevDates.getIsClose() == true))) {
                isPrevDatesComplited = true;
            }
        } else {
            isPrevDatesComplited = true;
        }

        if ((inputNumbers.get(curNumber).getIsValue() == false) && (inputNumbers.get(curNumber).getIsBracket() == false) && (inputNumbers.get(curNumber).getIsClose() == false) && (action != ACTIONS.ACT_PERS_MULTY)) {
            if (inputNumbers.size() > 1) {
                if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == false)) {
                    // Вывести сообщение о том, что процент нельзя применять к открытой скобке, а нужно применять только к простым арифметическим операциям *, /, +, -
                } else {
                    inputNumbers.get(curNumber).setAction(action);
                }
            } else {
                // Вывести сообщение о том, что нужно сначала ввести число
            }
        } else {
            if ((action == ACTIONS.ACT_PERS_MULTY) && (inputNumbers.get(curNumber).getIsValue() == true)) {
                if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == false)) {
                    // Вывести сообщение о том, что процент нельзя применять к открытой скобке, а нужно применять только к простым арифметическим операциям *, /, +, -
                } else if (curNumber == 0) {
                    // Вывести сообщение о том, что для применения процента нужно ввести два числа и любую следующую арифметическую операцию между ними: *, /, +, -
                } else if (((inputNumbers.get(curNumber - 1).getIsBracket() == true) && (inputNumbers.get(curNumber - 1).getIsClose() == false)) || inputNumbers.get(curNumber).getIsValue() == false) {
                    // Вывести сообщение о том, что для применения процента нужно ввести два числа и любую следующую арифметическую операцию между ними: *, /, +, -
                } else {
                    if ((inputNumbers.get(curNumber).getIsBracket() == true) && (inputNumbers.get(curNumber).getIsClose() == true)) {
                        // Определение позиции (positionBracketBegin)
                        Dates prevDates;
                        int counter = 0;
                        iterInputNumbersForCalc = inputNumbers.listIterator(curNumber);
                        while (iterInputNumbersForCalc.hasPrevious()) {
                            prevDates = iterInputNumbersForCalc.previous();
                            double viewDates = prevDates.getValue();
                            counter++;
                            if ((prevDates.getBracketLevel() == curBracketLevel + 1) && (prevDates.getIsBracket() == true) && (prevDates.getIsClose() == false)) {
                                positionBracketBegin -= counter;
                                break;
                            }
                        }
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_MULTY) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_MULTY);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_MULTY);
                    } else if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_DIV) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_DIV);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_DIV);
                    } else if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_PLUS) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_PLUS);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_PLUS);
                    } else if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_MINUS) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_MINUS);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_MINUS);
                    } else {
                        // Вывести сообщение о том, что процент нужно применять только к простым арифметическим операциям *, /, +, -
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PLUS);
                        inputNumbers.get(curNumber).setIsPercent(false);
                    }
                }
            } else {
                if ((isPrevDatesComplited == true) && (action != ACTIONS.ACT_PERS_MULTY)) {
                    add(false, false, FUNCTIONS.FUNC_NO, 1, 0d, false, action, false);
                    curNumber++;
                }
            }
        }
    }

    // Сменить знак в текущем элементе
    public void changeSign() {

        if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PLUS) && (curNumber > 0) && (((inputNumbers.get(curNumber - 1).getIsBracket() == true) && (inputNumbers.get(curNumber - 1).getIsClose() == true)) || ((inputNumbers.get(curNumber - 1).getIsBracket() == false) && (inputNumbers.get(curNumber - 1).getIsClose() == false)))) {
            inputNumbers.get(curNumber).setAction(ACTIONS.ACT_MINUS);
        } else if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_MINUS) && (curNumber > 0) && (((inputNumbers.get(curNumber - 1).getIsBracket() == true) && (inputNumbers.get(curNumber - 1).getIsClose() == true)) || ((inputNumbers.get(curNumber - 1).getIsBracket() == false) && (inputNumbers.get(curNumber - 1).getIsClose() == false)))) {
            inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PLUS);
        } else {
            inputNumbers.get(curNumber).setSign(inputNumbers.get(curNumber).getSign() * (-1));
        }
    }

    // Основной метод для вывода информации в историю введённых чисел, действий и функций
    public String createOutput() {
        String outputString = "";
        Dates curDates;
        Dates prevDates = null;

        iterInputNumbersForCalc = inputNumbers.listIterator();

        while (iterInputNumbersForCalc.hasNext()) {
            curDates = iterInputNumbersForCalc.next();
            outputString += outputStringActionAndFunction(prevDates, curDates);
            prevDates = curDates;
        }

        return outputString;
    }

    // Метод для правильного отображения функции
    private String outputStringFunctionOpen(Dates curDates) {
        String stringFunction = "";
        if ((curDates.getIsBracket() == true) && (curDates.getIsClose() == false)) {
            switch (curDates.getTypeFuncInBracket()) {
                case FUNC_SQRT:
                    stringFunction = "SQRT(";
                    break;
                // Сюда можно добавить другие функции для их отображения
                default:
                    stringFunction = "(";
                    break;
            }
        }
        return stringFunction;
    }

    // Метод для отображения текущего элемента
    private String outputStringActionAndFunction(Dates prevDates, Dates curDates) {
        boolean isBracket = curDates.getIsBracket();
        boolean isClose = curDates.getIsClose();
        int sign = curDates.getSign();
        double value = curDates.getValue() * sign;
        boolean isValue = curDates.getIsValue();
        ACTIONS action = curDates.getAction();
        boolean turnOffZapitay = curDates.getTurnOffZapitay();

        boolean isPrevBraketOpen = false;
        if ((prevDates != null) && (prevDates.getIsBracket() == true) && (prevDates.getIsClose() == false)) {
            isPrevBraketOpen = true;
        }
        boolean isFirst = (prevDates == null ? true : false);
        String stringAction = "";
        String valueString = "";
        if (isValue == true) {
            valueString = (curDates.getSign() < 0 ? "-" : "") + (curDates.getIntegerPartValue().length() > 0 ? curDates.getIntegerPartValue() : "0");
            if ((turnOffZapitay == false) || (curDates.getRealPartValue().length() > 0)) {
                valueString += ".";
            }
            valueString = valueString + (curDates.getRealPartValue().length() > 0 ? curDates.getRealPartValue() : "");
        }

        if (isFirst == false) {
            switch (action) {
                case ACT_STEP:
                    if (isValue == true) {
                        stringAction = "^" + outputStringFunctionOpen(curDates) + String.format("%s", valueString) + (isClose == true ? ")" : "");
                    } else {
                        stringAction = "^" + outputStringFunctionOpen(curDates);
                    }
                    break;
                case ACT_PERS_MULTY:
                    if ((isBracket == true) && (isClose == false)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if ((isBracket == true) && (isClose == true)) {
                        stringAction = ")%";
                    } else {
                        stringAction = "*" + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + "%" + (isClose == true ? ")" : "");
                    }
                    break;
                case ACT_PERS_DIV:
                    if ((isBracket == true) && (isClose == false)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if ((isBracket == true) && (isClose == true)) {
                        stringAction = ")%";
                    } else {
                        stringAction = "/" + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + "%" + (isClose == true ? ")" : "");
                    }
                    break;
                case ACT_PERS_PLUS:
                    if ((isBracket == true) && (isClose == false)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if ((isBracket == true) && (isClose == true)) {
                        stringAction = ")%";
                    } else {
                        stringAction = (isPrevBraketOpen == false ? "+" : "") + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + "%" + (isClose == true ? ")" : "");
                    }
                    break;
                case ACT_PERS_MINUS:
                    if ((isBracket == true) && (isClose == false)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if ((isBracket == true) && (isClose == true)) {
                        stringAction = ")%";
                    } else {
                        stringAction = "-" + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + "%" + (isClose == true ? ")" : "");
                    }
                    break;
                case ACT_MULTY:
                    if (isValue == true) {
                        stringAction = "*" + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + (isClose == true ? ")" : "");
                    } else {
                        stringAction = "*" + outputStringFunctionOpen(curDates) + (isClose == true ? ")" : "");
                    }
                    break;
                case ACT_DIV:
                    if (isValue == true) {
                        stringAction = "/" + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + (isClose == true ? ")" : "");
                    } else {
                        stringAction = "/" + outputStringFunctionOpen(curDates) + (isClose == true ? ")" : "");
                    }
                    break;
                case ACT_PLUS:
                    if (isValue == true) {
                        stringAction = (isPrevBraketOpen == false ? "+" : "") + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + (isClose == true ? ")" : "");
                    } else {
                        if (curDates.getIsClose() == false) {
                            stringAction = (isPrevBraketOpen == false ? "+" : "") + outputStringFunctionOpen(curDates);
                        } else {
                            stringAction = ")";
                        }
                    }
                    break;
                case ACT_MINUS:
                    if (isValue == true) {
                        stringAction = (isPrevBraketOpen == false ? "-" : "") + outputStringFunctionOpen(curDates) + (value >= 0 ? String.format("%s", valueString) : ("(" + String.format("%s", valueString) + ")")) + (isClose == true ? ")" : "");
                    } else {
                        if (curDates.getIsClose() == false) {
                            stringAction = (isPrevBraketOpen == false ? "-" : "") + outputStringFunctionOpen(curDates);
                        } else {
                            stringAction = ")";
                        }
                    }
                    break;
            }
        } else {
            switch (action) {
                case ACT_PLUS:
                    if (isValue == true) {
                        stringAction = outputStringFunctionOpen(curDates) + String.format("%s", valueString);
                    } else {
                        stringAction = outputStringFunctionOpen(curDates);
                    }
                    break;
            }
        }
        return stringAction;
    }

    public boolean getPressedZapitay() {
        return pressedZapitay;
    }

    public String getFinalResult(Context context) {
        String finalResult_String = "";
        if (errorCode == ERRORS.NO) {
            Resources resources = context.getResources();
            finalResult_String = numberFormatOutput(finalResult, resources.getInteger(R.integer.number_output_symbols_forEMS) * 2); // Умножаем на 2, потому что ширина чисел вдвое меньше величины EMS
        }
        return finalResult_String;
    }

    public static String numberFormatOutput(double number, int numberSymbols) {
        DecimalFormat df;
        double comparedNumber = 1d;
        if (number > 0) {
            for (int i = 0; i < numberSymbols; i++) {
                comparedNumber *= 10d;
                if (number < comparedNumber) {
                    df = new DecimalFormat(createFormat(numberSymbols, i));
                    return (df.format(number).length() > numberSymbols ? String.format(Locale.getDefault(), "%e", number) : df.format(number));
                }
            }
        } else {
            for (int i = 0; i < numberSymbols - 1; i++) {
                comparedNumber *= 10d;
                if (Math.abs(number) < comparedNumber) {
                    df = new DecimalFormat(createFormat(numberSymbols, i));
                    return (df.format(number).length() > numberSymbols ? String.format(Locale.getDefault(), "%e", number) : df.format(number));
                }
            }
        }
        if (number > 0) {
            return String.format(Locale.getDefault(), (number < 10E99d ? "%.6e" : "%.5e"), number);
        }
        else {
            return String.format(Locale.getDefault(), (Math.abs(number) < 10E99d ? "%.6e" : "%.5e"), number);
        }
    }

    private static String createFormat(int numberSymbols, int i) {
        String format = "";
        for (int j = 0; j <= i; j++) {
            format += "#";
        }
        if (i < numberSymbols - 2) {
            format += ".";
            for (int j = 0; j < numberSymbols - 2 - i; j++) {
                format += "#";
            }
        }
        return format;
    }

    public ERRORS getErrorCode() {
        return errorCode;
    }

    public void clearErrorCode() {
        errorCode = ERRORS.NO;
    }
}