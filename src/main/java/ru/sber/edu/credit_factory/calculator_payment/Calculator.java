package ru.sber.edu.credit_factory.calculator_payment;

import ru.sber.edu.credit_factory.exception.InvalidArgumentException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    private static final int MONTHS_IN_YEAR = 12;
    private static final int PERCENTS = 100;
    private static final int CALCULATE_SCALE = 10;
    private static final int PRINT_SCALE = 2;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * @param totalSum           Общая сумма кредита
     * @param yearlyInterestRate Годовая процентная ставка
     * @param monthsCount        Количество платежей, месяцев
     * @return Сумма ежемесячного платежа по кредиту
     */
    public static BigDecimal annuityMonthlyPayment(BigDecimal totalSum, BigDecimal yearlyInterestRate, int monthsCount) {
        try {
            if (totalSum.doubleValue() <= 0
                    || yearlyInterestRate.doubleValue() <= 0
                    || monthsCount <= 0
            ) throw new InvalidArgumentException("Аргумент не может быть отрицательный или равен нулю!");
        } catch (InvalidArgumentException e) {
            System.err.println(e.getMessage());
        }

        BigDecimal monthlyInterestRate = yearlyInterestRate.
                divide(BigDecimal.valueOf(MONTHS_IN_YEAR), CALCULATE_SCALE, ROUNDING_MODE).
                divide(BigDecimal.valueOf(PERCENTS), CALCULATE_SCALE, ROUNDING_MODE);

        BigDecimal annuityInterestRate = (monthlyInterestRate.add(BigDecimal.ONE)).pow(monthsCount);

        BigDecimal annuityRatio = (monthlyInterestRate.multiply(annuityInterestRate).
                divide(annuityInterestRate.subtract(BigDecimal.ONE), CALCULATE_SCALE, ROUNDING_MODE));

        BigDecimal monthlyPayment = totalSum.multiply(annuityRatio);

        return monthlyPayment.setScale(PRINT_SCALE, ROUNDING_MODE);
    }
}
