package ru.geekbrains.lessions2345.calculator.core;

import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.INPUT_NUMBER_FIRST;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.MANY_ZERO_IN_INTEGER_PART;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.NUMBER_AFTER_BRACKET;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.PERCENT_NEEDS_TWO_NUMBERS;
import static ru.geekbrains.lessions2345.calculator.core.Constants.ERRORS_INPUTTING.PERCENT_ON_OPEN_BRACKET;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.model.Dates;

public class CalcLogic implements Constants, Serializable {
    // Создание класса со значениями
    private LinkedList<Dates> inputNumbers;
    // Максимальный уровень вложенности во всех скобках
    private int maxBracketLevel;
    // Создание переменной с текущим максимальный уровнем вложенности скобок
    private int curBracketLevel;
    // Текущий индекс создаваемой операции
    // (в операцию входит как сама операция, так и число в ней участвующее)
    private int curNumber;
    // Класс со значениями вместе со скобками;
    // в результате всех вычислений, в данном классе не останется ни одной скобки
    private LinkedList<Dates> inputNumbersForBracketCalc;
    // Класс со значениями для вычислений внутри скобок
    private LinkedList<Dates> inputNumbersForBaseCalc;
    // Создание итератора для навигации по списку со значениями
    private ListIterator<Dates> iterInputNumbersForCalc;
    // Признак нажатой кнопки запятой
    private boolean pressedZapitay = false;
    // Переменная, хранящая окончательный результат вычислений
    private double finalResult = 0d;
    // Ошибки вычислений
    private ERRORS_IN_STRING errorCode = ERRORS_IN_STRING.NO;
    // Максимальное количество символов, допустимое для вывода в поле с результатами вычислений
    private int maxNumberSymbolsInOutputTextField = MAX_NUMBER_SYMBOLS_IN_OUTPUT_TEXT_FIELD;
    // Действия при ошибках ввода чисел в калькуляторе
    private final ErrorMessages errorMessages;

    public CalcLogic(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
        inputNumbers = new LinkedList<>();
        maxBracketLevel = 0;
        curBracketLevel = 0;
        curNumber = 0;
        inputNumbersForBaseCalc = new LinkedList<>();
        iterInputNumbersForCalc = inputNumbersForBaseCalc.listIterator();

        // Создание первого пустого элемента
        add(false, false, FUNCTIONS.FUNC_NO, 1, 0d, false,
                ACTIONS.ACT_PLUS, false);
    }

    public void setMaxNumberSymbolsInOutputTextField(int maxNumberSymbolsInOutputTextField) {
        this.maxNumberSymbolsInOutputTextField = maxNumberSymbolsInOutputTextField;
    }

    public double addNumeral(int newNumeral) {
        // Проверка на добавление нового числа сразу после закрытой скобки без задания действия
        if (inputNumbers.get(curNumber).getIsClose()) {
            // Вывести сообщение о том, что нельзя сразу же после закрытой скобки вводить число,
            // предварительно не указав действия с ним
            errorMessages.sendErrorInputting(NUMBER_AFTER_BRACKET);
            return -1.0; // Здесь можно вернуть любое число
        }
        // Добавление нового элемента
        if ((inputNumbers.get(curNumber).getIsBracket()) &&
                (!inputNumbers.get(curNumber).getIsClose())) {
            add(false, false, FUNCTIONS.FUNC_NO, 1, 0d,
                    false, ACTIONS.ACT_PLUS, false);
            curNumber++;
        }
        if ((inputNumbers.get(curNumber).getValue() == 0d) && (inputNumbers.get(curNumber).
                getIntegerPartValue().length() > 0) && (newNumeral == 0) && (!pressedZapitay)) {
            // Показать уведомление о том,
            // что для задания целой части числа вполне хватит и одного нуля
            errorMessages.sendErrorInputting(MANY_ZERO_IN_INTEGER_PART);
        } else {
            double intPartValue = 0d;
            double realPartValue = 0d;
            if (pressedZapitay) {
                inputNumbers.get(curNumber).setRealPartValue(newNumeral);
                inputNumbers.get(curNumber).setNumberZapitay(inputNumbers.get(curNumber).
                        getNumberZapitay() + 1);
                if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                    intPartValue = Double.parseDouble(inputNumbers.get(curNumber).
                            getIntegerPartValue());
                }
                realPartValue = Double.parseDouble(inputNumbers.get(curNumber).
                        getRealPartValue()) * Math.pow(10, -1 * inputNumbers.get(curNumber).
                        getRealPartValue().length());
            } else {
                // Подчищение введенной ранее нулевой цифры
                if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0)
                {
                    intPartValue = Double.parseDouble(inputNumbers.get(curNumber).
                            getIntegerPartValue());
                    if (intPartValue == 0d) {
                        while (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                            inputNumbers.get(curNumber).setIntegerPartValueDecreaseOne();
                        }
                    }
                }
                inputNumbers.get(curNumber).setIntegerPartValue(newNumeral);
                intPartValue = Double.parseDouble(inputNumbers.get(curNumber).
                        getIntegerPartValue());
                if (inputNumbers.get(curNumber).getRealPartValue().length() > 0) {
                    realPartValue = Double.parseDouble(inputNumbers.get(curNumber).
                            getRealPartValue()) * Math.pow(10, -1 * inputNumbers.get(curNumber).
                            getRealPartValue().length());
                }
            }
            set(curNumber, inputNumbers.get(curNumber).getIsBracket(), false,
                    FUNCTIONS.FUNC_NO, curBracketLevel, inputNumbers.get(curNumber).getSign(),
                    intPartValue + realPartValue, true, inputNumbers.get(curNumber).
                            getAction(), inputNumbers.get(curNumber).getIsPercent());
        }
        return inputNumbers.get(curNumber).getValue();
    }

    // Метод для добавления нового элемента
    public void add(boolean _isBracket, boolean _isClose, FUNCTIONS _typeFuncInBracket, int _sign,
                    double _value, boolean _isValue, ACTIONS _action, boolean _isPercent) {
        inputNumbers.add(new Dates(_isBracket, _isClose, _typeFuncInBracket, curBracketLevel,
                _sign, _value, _isValue, _action, _isPercent));
    }

    // Метод для установки параметров, необходимых для проведения расчётов
    public void set(int posNumber, boolean _isBracket, boolean _isClose,
                    FUNCTIONS _typeFuncInBracket, int _bracketLevel, int _sign, double _value,
                    boolean _isValue, ACTIONS _action, boolean _isPercent) {
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
        add(false, false, FUNCTIONS.FUNC_NO, 1, 0d, false,
                ACTIONS.ACT_PLUS, false);
        pressedZapitay = false;
    }

    // Метод для коррекции последнего созданного элемента
    public boolean clearOne() {
        if (inputNumbers.get(curNumber).getIsValue()) {
            if (pressedZapitay) {
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

            if ((inputNumbers.get(curNumber).getIntegerPartValue().length() == 0) &&
                    (inputNumbers.get(curNumber).getRealPartValue().length() == 0)) {
                inputNumbers.get(curNumber).setSign(1);
                inputNumbers.get(curNumber).setIsValue(false);
                inputNumbers.get(curNumber).setValue(0d);
                inputNumbers.get(curNumber).setTurnOffZapitay(true);
                pressedZapitay = false;
            } else {
                double intPartValue = 0d;
                double realPartValue = 0d;
                if (inputNumbers.get(curNumber).getIntegerPartValue().length() > 0) {
                    intPartValue = Double.parseDouble(inputNumbers.get(curNumber).
                            getIntegerPartValue());
                }

                if (inputNumbers.get(curNumber).getRealPartValue().length() > 0) {
                    realPartValue = Double.parseDouble(inputNumbers.get(curNumber).
                            getRealPartValue()) * Math.pow(10, -1 * inputNumbers.get(curNumber).
                            getRealPartValue().length());
                }
                inputNumbers.get(curNumber).setValue(intPartValue + realPartValue);
            }
        } else {
            if (curNumber > 0) {
                if ((inputNumbers.get(curNumber).getIsBracket()) &&
                        (inputNumbers.get(curNumber).getIsClose())) {
                    if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MULTY) ||
                            ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_DIV)) ||
                            ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_PLUS)) ||
                            ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MINUS))) {
                        // Определение позиции (positionBracketBegin)
                        int positionBracketBegin = curNumber;
                        Dates prevDates;
                        int counter = 0;
                        iterInputNumbersForCalc = inputNumbers.listIterator(curNumber);
                        while (iterInputNumbersForCalc.hasPrevious()) {
                            prevDates = iterInputNumbersForCalc.previous();
                            counter++;
                            if ((prevDates.getBracketLevel() == curBracketLevel + 1) &&
                                    (prevDates.getIsBracket()) && (!prevDates.getIsClose())) {
                                positionBracketBegin -= counter;
                                break;
                            }
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() ==
                                ACTIONS.ACT_PERS_MULTY) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MULTY);
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() ==
                                ACTIONS.ACT_PERS_DIV) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_DIV);
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() ==
                                ACTIONS.ACT_PERS_PLUS) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PLUS);
                        }
                        if (inputNumbers.get(positionBracketBegin).getAction() ==
                                ACTIONS.ACT_PERS_MINUS) {
                            inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MINUS);
                        }
                    }

                    curBracketLevel++;
                    if (maxBracketLevel < curBracketLevel) {
                        maxBracketLevel = curBracketLevel;
                    }
                }
                if ((inputNumbers.get(curNumber).getIsBracket()) &&
                        (!inputNumbers.get(curNumber).getIsClose())) {
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
            if ((inputNumbers.get(curNumber).getIsBracket()) &&
                    (inputNumbers.get(curNumber).getIsClose())) {
                if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MULTY) ||
                        ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_DIV)) ||
                        ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_PLUS)) ||
                        ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PERS_MINUS))) {
                    // Определение позиции (positionBracketBegin)
                    int positionBracketBegin = curNumber;
                    Dates prevDates;
                    int counter = 0;
                    iterInputNumbersForCalc = inputNumbers.listIterator(curNumber);
                    while (iterInputNumbersForCalc.hasPrevious()) {
                        prevDates = iterInputNumbersForCalc.previous();
                        counter++;
                        if ((prevDates.getBracketLevel() == curBracketLevel + 1) &&
                                (prevDates.getIsBracket()) && (!prevDates.getIsClose())) {
                            positionBracketBegin -= counter;
                            break;
                        }
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() ==
                            ACTIONS.ACT_PERS_MULTY) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MULTY);
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() ==
                            ACTIONS.ACT_PERS_DIV) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_DIV);
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() ==
                            ACTIONS.ACT_PERS_PLUS) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PLUS);
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() ==
                            ACTIONS.ACT_PERS_MINUS) {
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_MINUS);
                    }
                }

                curBracketLevel++;
                if (maxBracketLevel < curBracketLevel) {
                    maxBracketLevel = curBracketLevel;
                }
            }
            if ((inputNumbers.get(curNumber).getIsBracket()) &&
                    (!inputNumbers.get(curNumber).getIsClose())) {
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
            newInputNumbers.add(new Dates(curDates.getIsBracket(), curDates.getIsClose(),
                    curDates.getTypeFuncInBracket(), curDates.getBracketLevel(), curDates.getSign(),
                    curDates.getValue(), curDates.getIsValue(), curDates.getAction(),
                    curDates.getIsPercent(), curDates.getNumberZapitay(),
                    curDates.getTurnOffZapitay()));
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
        int bracketBalance = 0;

        // Создание дубликата класса с числами для обработки выражения со скобками
        inputNumbersForBracketCalc = copyLinkedList(inputNumbers);

        // Проверка незакрытых скобок
        // Корректировка открытой, но не закрытой пустой скобки
        // (т.е. если открыли скобку, но за ней не следует никакого числа)
        iterInputNumbersForCalc = inputNumbersForBracketCalc.listIterator();
        while (iterInputNumbersForCalc.hasNext()) {
            curDates = iterInputNumbersForCalc.next();
            if ((curDates.getIsBracket()) && (!curDates.getIsClose())) {
                bracketBalance++;
            }
            if (curDates.getIsClose()) {
                bracketBalance--;
            }
        }

        if (bracketBalance == 0) {
            // Здесь происходит обработка всех скобок
            while (isBracketExist) {
                for (int i = maxBracketLevel; i > 0; i--) {
                    iterInputNumbersForCalc = inputNumbersForBracketCalc.listIterator();
                    counter = -1;
                    while (iterInputNumbersForCalc.hasNext()) {
                        counter++;
                        curDates = iterInputNumbersForCalc.next();
                        if (curDates.getBracketLevel() == i) {
                            // Не забыть вернуть этой переменной значение -1,
                            // когда встретим закрывающую скобку
                            startBracketIndex = counter;

                            inputNumbersForBaseCalc = new LinkedList<>();
                            while (iterInputNumbersForCalc.hasNext()) {
                                curDates = iterInputNumbersForCalc.next();
                                if ((curDates.getBracketLevel() == i) && (!curDates.getIsClose())) {
                                    inputNumbersForBaseCalc.add(new Dates(curDates.getIsBracket(),
                                            curDates.getIsClose(), curDates.getTypeFuncInBracket(),
                                            curDates.getBracketLevel(), curDates.getSign(),
                                            curDates.getValue(), curDates.getIsValue(),
                                            curDates.getAction(), curDates.getIsPercent(),
                                            curDates.getNumberZapitay(),
                                            curDates.getTurnOffZapitay()));
                                    iterInputNumbersForCalc.remove();
                                } else if ((curDates.getBracketLevel() == i) &&
                                        (curDates.getIsClose())) {
                                    iterInputNumbersForCalc.remove();
                                    break;
                                } else {
                                    if (iterInputNumbersForCalc.hasPrevious()) {
                                        iterInputNumbersForCalc.previous();
                                    }
                                    break;
                                }
                            }
                            result = moveOnWithoutBracket(inputNumbersForBaseCalc);
                            if (errorCode != ERRORS_IN_STRING.NO) {
                                return;
                            }
                            inputNumbersForBracketCalc.get(startBracketIndex).
                                    setValue(doFunction(result, inputNumbersForBracketCalc.
                                            get(startBracketIndex).getTypeFuncInBracket()));
                            if (errorCode != ERRORS_IN_STRING.NO) {
                                return;
                            }
                            inputNumbersForBracketCalc.get(startBracketIndex).
                                    setTypeFuncInBracket(FUNCTIONS.FUNC_NO);
                            inputNumbersForBracketCalc.get(startBracketIndex).setIsValue(true);
                            inputNumbersForBracketCalc.get(startBracketIndex).
                                    setBracketLevel(inputNumbersForBracketCalc.
                                            get(startBracketIndex).getBracketLevel() - 1);
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
                    if (curDates.getIsBracket()) {
                        isBracketExist = true;
                        break;
                    }
                }
            }

            // Здесь происходит обработка списка, в котором не осталось ни одной скобки
            // Создание дубликата класса с числами для обработки без скобок
            inputNumbersForBaseCalc = copyLinkedList(inputNumbersForBracketCalc);
            result = moveOnWithoutBracket(inputNumbersForBaseCalc);
        } else {
            // Сообщить об ошибке: в анализируемом выражении есть незакрытые скобки
            errorCode = ERRORS_IN_STRING.BRACKET_DISBALANCE;
        }

        // Сохранение результирующего значения
        finalResult = result;
    }

    // Работа функций
    private double doFunction(double value, FUNCTIONS typeFuncInBracket) {
        double result = value;

        if (typeFuncInBracket == FUNCTIONS.FUNC_SQRT) {
            if (value >= 0) {
                result = Math.sqrt(value);
            } else {
                // Добавить сообщение об ошибке: выражение под корнем не может быть меньше нуля!
                errorCode = ERRORS_IN_STRING.SQRT_MINUS;
            }
        }
        // Подключить новую функцию для вычислений здесь
        // TODO
//        else if (typeFuncInBracket == FUNCTIONS.) {
//            result =
//        }
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
            errorCode = ERRORS_IN_STRING.BRACKETS_EMPTY;
            return result;
        }
        for (ACTIONS action : ACTIONS.values()) {
            // Учёт равнозначности операций деления и умножения
            if (action != ACTIONS.ACT_DIV)
            {
                iterInputNumbersForCalc = curNumbersForCals.listIterator();
                while (iterInputNumbersForCalc.hasNext()) {
                    curDates = iterInputNumbersForCalc.next();

                    // Учёт равнозначности операций деления и умножения
                    if (action == ACTIONS.ACT_MULTY)
                    {
                        if ((curDates.getAction() == ACTIONS.ACT_MULTY) &&
                                (curNumbersForCals.size() > 1)) {
                            prevDates.setValue(doBaseActions(prevDates.getValue() *
                                    prevDates.getSign(), curDates.getValue() *
                                    curDates.getSign(), curDates.getAction()));
                            prevDates.setSign(1);
                            iterInputNumbersForCalc.remove();
                        } else if ((curDates.getAction() == ACTIONS.ACT_DIV) &&
                                (curNumbersForCals.size() > 1)) {
                            prevDates.setValue(doBaseActions(prevDates.getValue() *
                                    prevDates.getSign(), curDates.getValue() *
                                    curDates.getSign(), curDates.getAction()));
                            prevDates.setSign(1);
                            iterInputNumbersForCalc.remove();
                        } else {
                            prevDates = curDates;
                        }
                        // Выполнение всех остальных операций (не деления и не умножения)
                    } else if ((curDates.getAction() == action) && (curNumbersForCals.size() > 1))
                    {
                        prevDates.setValue(doBaseActions(prevDates.getValue() *
                                prevDates.getSign(), curDates.getValue() *
                                curDates.getSign(), curDates.getAction()));
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
                    errorCode = ERRORS_IN_STRING.ZERO_DIVIDE;
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
                    errorCode = ERRORS_IN_STRING.ZERO_DIVIDE;
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

    // Отслеживание нажатия на кнопку с запятой
    // (переключение между режимами работы с вещественными и целыми числами)
    public void setCurZapitay() {
        pressedZapitay = !pressedZapitay; // Отслеживание нажатия на кнопку "Zapitay"
        if (inputNumbers.get(curNumber).getTurnOffZapitay()) {
            inputNumbers.get(curNumber).setTurnOffZapitay(false);
            if (inputNumbers.get(curNumber).getNumberZapitay() < 0) {
                inputNumbers.get(curNumber).setNumberZapitay(0);
            }
        } else {
            inputNumbers.get(curNumber).setTurnOffZapitay(true);
        }
    }

    // Установка для функции, а также новой скобки, поскольку функции без скобок не бывает
    // Данный метод не только устанавливает новую функцию, но и открывает скобку
    public String setNewFunction(FUNCTIONS typeFuncInBracket)
    {
        if (inputNumbers.get(curNumber).getIsBracket()) {
            curBracketLevel++;
            add(true, false, typeFuncInBracket, 1, 0d, false,
                    ACTIONS.ACT_PLUS, false);
            curNumber++;
        } else {
            if ((inputNumbers.get(curNumber).getTypeFuncInBracket() == FUNCTIONS.FUNC_NO) &&
                    (!inputNumbers.get(curNumber).getIsValue())) {
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
            add(true, true, FUNCTIONS.FUNC_NO, 1, 0d, false,
                    ACTIONS.ACT_PLUS, false);
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
            if ((prevDates.getIsValue()) || ((prevDates.getIsBracket()) &&
                    (!prevDates.getIsClose())) || ((prevDates.getIsBracket()) &&
                    (prevDates.getIsClose()))) {
                isPrevDatesComplited = true;
            }
        } else {
            isPrevDatesComplited = true;
        }

        if ((!inputNumbers.get(curNumber).getIsValue()) &&
                (!inputNumbers.get(curNumber).getIsBracket()) &&
                (!inputNumbers.get(curNumber).getIsClose()) && (action != ACTIONS.ACT_PERS_MULTY)) {
            if (inputNumbers.size() > 1) {
                if ((inputNumbers.get(curNumber).getIsBracket()) &&
                        (!inputNumbers.get(curNumber).getIsClose())) {
                    // Вывести сообщение о том, что процент нельзя применять к открытой скобке,
                    // а нужно применять только к простым арифметическим операциям *, /, +, -
                    errorMessages.sendErrorInputting(PERCENT_ON_OPEN_BRACKET);
                } else {
                    inputNumbers.get(curNumber).setAction(action);
                }
            } else {
                // Вывести сообщение о том, что нужно сначала ввести число
                errorMessages.sendErrorInputting(INPUT_NUMBER_FIRST);
            }
        } else {
            if ((action == ACTIONS.ACT_PERS_MULTY) && (inputNumbers.get(curNumber).getIsValue())) {
                if ((inputNumbers.get(curNumber).getIsBracket()) &&
                        (!inputNumbers.get(curNumber).getIsClose())) {
                    // Вывести сообщение о том, что процент нельзя применять к открытой скобке,
                    // а нужно применять только к простым арифметическим операциям *, /, +, -
                    errorMessages.sendErrorInputting(PERCENT_ON_OPEN_BRACKET);
                } else if (curNumber == 0) {
                    // Вывести сообщение о том, что для применения процента нужно ввести два числа
                    // и любую следующую арифметическую операцию между ними: *, /, +, -
                    errorMessages.sendErrorInputting(PERCENT_NEEDS_TWO_NUMBERS);
                } else if (((inputNumbers.get(curNumber - 1).getIsBracket()) &&
                        (!inputNumbers.get(curNumber - 1).getIsClose())) ||
                        !inputNumbers.get(curNumber).getIsValue()) {
                    // Вывести сообщение о том, что для применения процента нужно ввести два числа
                    // и любую следующую арифметическую операцию между ними: *, /, +, -
                    errorMessages.sendErrorInputting(PERCENT_NEEDS_TWO_NUMBERS);
                } else {
                    if ((inputNumbers.get(curNumber).getIsBracket()) &&
                            (inputNumbers.get(curNumber).getIsClose())) {
                        // Определение позиции (positionBracketBegin)
                        Dates prevDates;
                        int counter = 0;
                        iterInputNumbersForCalc = inputNumbers.listIterator(curNumber);
                        while (iterInputNumbersForCalc.hasPrevious()) {
                            prevDates = iterInputNumbersForCalc.previous();
                            counter++;
                            if ((prevDates.getBracketLevel() == curBracketLevel + 1) &&
                                    (prevDates.getIsBracket()) && (!prevDates.getIsClose())) {
                                positionBracketBegin -= counter;
                                break;
                            }
                        }
                    }
                    if (inputNumbers.get(positionBracketBegin).getAction() == ACTIONS.ACT_MULTY) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_MULTY);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_MULTY);
                    } else if (inputNumbers.get(positionBracketBegin).getAction() ==
                            ACTIONS.ACT_DIV) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_DIV);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_DIV);
                    } else if (inputNumbers.get(positionBracketBegin).getAction() ==
                            ACTIONS.ACT_PLUS) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_PLUS);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_PLUS);
                    } else if (inputNumbers.get(positionBracketBegin).getAction() ==
                            ACTIONS.ACT_MINUS) {
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PERS_MINUS);
                        inputNumbers.get(positionBracketBegin).setAction(ACTIONS.ACT_PERS_MINUS);
                    } else {
                        // Вывести сообщение о том, что процент нужно применять только
                        // к простым арифметическим операциям *, /, +, -
                        inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PLUS);
                        inputNumbers.get(curNumber).setIsPercent(false);
                    }
                }
            } else {
                if ((isPrevDatesComplited) && (action != ACTIONS.ACT_PERS_MULTY)) {
                    add(false, false, FUNCTIONS.FUNC_NO, 1, 0d,
                            false, action, false);
                    curNumber++;
                }
            }
        }
    }

    // Сменить знак в текущем элементе
    public void changeSign() {
        if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_PLUS) && (curNumber > 0) &&
                (((inputNumbers.get(curNumber - 1).getIsBracket()) &&
                        (inputNumbers.get(curNumber - 1).getIsClose())) ||
                        ((!inputNumbers.get(curNumber - 1).getIsBracket()) &&
                                (!inputNumbers.get(curNumber - 1).getIsClose())))) {
            inputNumbers.get(curNumber).setAction(ACTIONS.ACT_MINUS);
        } else if ((inputNumbers.get(curNumber).getAction() == ACTIONS.ACT_MINUS) &&
                (curNumber > 0) && (((inputNumbers.get(curNumber - 1).getIsBracket()) &&
                (inputNumbers.get(curNumber - 1).getIsClose())) ||
                ((!inputNumbers.get(curNumber - 1).getIsBracket()) &&
                        (!inputNumbers.get(curNumber - 1).getIsClose())))) {
            inputNumbers.get(curNumber).setAction(ACTIONS.ACT_PLUS);
        } else {
            inputNumbers.get(curNumber).setSign(inputNumbers.get(curNumber).getSign() * (-1));
        }
    }

    // Основной метод для вывода информации в историю введённых чисел, действий и функций
    public String createOutput() {
        StringBuilder outputString = new StringBuilder();
        Dates curDates;
        Dates prevDates = null;

        iterInputNumbersForCalc = inputNumbers.listIterator();

        while (iterInputNumbersForCalc.hasNext()) {
            curDates = iterInputNumbersForCalc.next();
            outputString.append(outputStringActionAndFunction(prevDates, curDates));
            prevDates = curDates;
        }

        return outputString.toString();
    }

    // Метод для отображения функции
    private String outputStringFunctionOpen(Dates curDates) {
        String stringFunction = "";
        if ((curDates.getIsBracket()) && (!curDates.getIsClose())) {
            if (curDates.getTypeFuncInBracket() == FUNCTIONS.FUNC_SQRT) {
                stringFunction = "SQRT(";
//          } else if (curDates.getTypeFuncInBracket() == FUNCTIONS.) {
                // Сюда можно добавить другие функции для их отображения
                // TODO
                // stringFunction = "(";
            } else {
                stringFunction = "(";
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

        boolean isPrevBraketOpen = (prevDates != null) && (prevDates.getIsBracket())
                && (!prevDates.getIsClose());
        boolean isFirst = (prevDates == null);
        String stringAction = "";
        String valueString = "";
        if (isValue) {
            valueString = (curDates.getSign() < 0 ? "-" : "") +
                    (curDates.getIntegerPartValue().length() > 0 ?
                            curDates.getIntegerPartValue() : "0");
            if ((!turnOffZapitay) || (curDates.getRealPartValue().length() > 0)) {
                valueString += ".";
            }
            valueString = valueString + (curDates.getRealPartValue().length() > 0 ?
                    curDates.getRealPartValue() : "");
        }

        if (!isFirst) {
            String notZeroOrZero = value >= 0 ? String.format("%s", valueString) :
                    ("(" + String.format("%s", valueString) + ")");
            switch (action) {
                case ACT_STEP:
                    if (isValue) {
                        stringAction = "^" + outputStringFunctionOpen(curDates) +
                                String.format("%s", valueString) + (isClose ? ")" : "");
                    } else {
                        stringAction = "^" + outputStringFunctionOpen(curDates);
                    }
                    break;
                case ACT_PERS_MULTY:
                    if ((isBracket) && (!isClose)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if (isBracket) {
                        stringAction = ")%";
                    } else {
                        stringAction = "*" + outputStringFunctionOpen(curDates) +
                            notZeroOrZero + "%" + (isClose ? ")" : "");
                    }
                    break;
                case ACT_PERS_DIV:
                    if ((isBracket) && (!isClose)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if (isBracket) {
                        stringAction = ")%";
                    } else {
                        stringAction = "/" + outputStringFunctionOpen(curDates) +
                            notZeroOrZero + "%" + (isClose ? ")" : "");
                    }
                    break;
                case ACT_PERS_PLUS:
                    if ((isBracket) && (!isClose)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if (isBracket) {
                        stringAction = ")%";
                    } else {
                        stringAction = (!isPrevBraketOpen ? "+" : "") +
                            outputStringFunctionOpen(curDates) + notZeroOrZero + "%" +
                                (isClose ? ")" : "");
                    }
                    break;
                case ACT_PERS_MINUS:
                    if ((isBracket) && (!isClose)) {
                        stringAction = outputStringFunctionOpen(curDates);
                    } else if (isBracket) {
                        stringAction = ")%";
                    } else {
                        stringAction = "-" + outputStringFunctionOpen(curDates) +
                            notZeroOrZero + "%" + (isClose ? ")" : "");
                    }
                    break;
                case ACT_MULTY:
                    if (isValue) {
                        stringAction = "*" + outputStringFunctionOpen(curDates) +
                            notZeroOrZero + (isClose ? ")" : "");
                    } else {
                        stringAction = "*" + outputStringFunctionOpen(curDates) +
                            (isClose ? ")" : "");
                    }
                    break;
                case ACT_DIV:
                    if (isValue) {
                        stringAction = "/" + outputStringFunctionOpen(curDates) +
                            notZeroOrZero + (isClose ? ")" : "");
                    } else {
                        stringAction = "/" + outputStringFunctionOpen(curDates) +
                            (isClose ? ")" : "");
                    }
                    break;
                case ACT_PLUS:
                    if (isValue) {
                        stringAction = (!isPrevBraketOpen ? "+" : "") +
                            outputStringFunctionOpen(curDates) + notZeroOrZero +
                                (isClose ? ")" : "");
                    } else {
                        if (!curDates.getIsClose()) {
                            stringAction = (!isPrevBraketOpen ? "+" : "") +
                                    outputStringFunctionOpen(curDates);
                        } else {
                            stringAction = ")";
                        }
                    }
                    break;
                case ACT_MINUS:
                    if (isValue) {
                        stringAction = (!isPrevBraketOpen ? "-" : "") +
                            outputStringFunctionOpen(curDates) + notZeroOrZero +
                                (isClose ? ")" : "");
                    } else {
                        if (!curDates.getIsClose()) {
                            stringAction = (!isPrevBraketOpen ? "-" : "") +
                                    outputStringFunctionOpen(curDates);
                        } else {
                            stringAction = ")";
                        }
                    }
                    break;
            }
        } else {
            if (action == ACTIONS.ACT_PLUS) {
                if (isValue) {
                    stringAction = outputStringFunctionOpen(curDates) +
                        String.format("%s", valueString);
                } else {
                    stringAction = outputStringFunctionOpen(curDates);
                }
            }
        }
        return stringAction;
    }

    public boolean getPressedZapitay() {
        return pressedZapitay;
    }

    public String getFinalResult() {
        String finalResultString = "";
        if (errorCode == ERRORS_IN_STRING.NO) {
            finalResultString = numberFormatOutput(finalResult, maxNumberSymbolsInOutputTextField);
        }
        return finalResultString;
    }

    public static String numberFormatOutput(double number, int numberSymbols) {
        DecimalFormat df;
        double comparedNumber =
            (numberSymbols >= MAX_NUMBER_SYMBOLS_IN_OUTPUT_TEXT_FIELD ? 1d : 0.1d);
        if (number > 0) {
            for (int i = 0; i < numberSymbols; i++) {
                comparedNumber *= 10d;
                if (number < comparedNumber) {
                    df = new DecimalFormat(createFormat(numberSymbols, i));
                    return (df.format(number).length() > numberSymbols ?
                            String.format(Locale.getDefault(), "%e", number) :
                            df.format(number));
                }
            }
        } else {
            for (int i = 0; i < numberSymbols - 1; i++) {
                comparedNumber *= 10d;
                if (Math.abs(number) < comparedNumber) {
                    df = new DecimalFormat(createFormat(numberSymbols, i));
                    return (df.format(number).length() > numberSymbols ?
                            String.format(Locale.getDefault(), "%e", number) :
                            df.format(number));
                }
            }
        }
        if (number > 0) {
            if (numberSymbols == MAX_NUMBER_SYMBOLS_IN_OUTPUT_TEXT_FIELD)
                return String.format(Locale.getDefault(),
                    (number < 10E99d ? "%.6e" : "%.5e"), number);
            else
                return String.format(Locale.getDefault(),
                        (number < 10E99d ? "%.3e" : "%.2e"), number);
        }
        else {
            if (numberSymbols == MAX_NUMBER_SYMBOLS_IN_OUTPUT_TEXT_FIELD)
                return String.format(Locale.getDefault(),
                    (Math.abs(number) < 10E99d ? "%.6e" : "%.5e"), number);
            else
                return String.format(Locale.getDefault(),
                        (Math.abs(number) < 10E99d ? "%.3e" : "%.2e"), number);
        }
    }

    private static String createFormat(int numberSymbols, int i) {
        StringBuilder format = new StringBuilder();
        for (int j = 0; j <= i; j++) {
            format.append("#");
        }
        if (i < numberSymbols - 2) {
            format.append(".");
            for (int j = 0; j < numberSymbols - 2 - i; j++) {
                format.append("#");
            }
        }
        return format.toString();
    }

    public ERRORS_IN_STRING getErrorCode() {
        return errorCode;
    }

    public void clearErrorCode() {
        errorCode = ERRORS_IN_STRING.NO;
    }
}