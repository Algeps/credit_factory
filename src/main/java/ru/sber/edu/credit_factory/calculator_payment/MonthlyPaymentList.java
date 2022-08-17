package ru.sber.edu.credit_factory.calculator_payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Служебный класс для расчёта ежемесячного платежа.
 * Паттерн - класс утилита.
 */
public final class MonthlyPaymentList {
    private MonthlyPaymentList() {}
    /**
     * Возвращает список, где каждый элемент - это дата платежа и сумма кредита
     *
     * @param totalSum    общая сумма кредита.
     * @param monthsCount срок кредита.
     * @return список вида: "Дата платежа, Сумма Платежа", по числу месяцев кредита.
     */
    public static List<MonthlyPayment> getMonthlyPaymentList
    (BigDecimal totalSum, BigDecimal annualInterestRate, int monthsCount, LocalDate loanDate) {
        Collection<MonthlyPayment> monthlyPaymentList = new LinkedHashSet<>(monthsCount);
        BigDecimal monthlyPayment = getMonthlyPayment(totalSum, annualInterestRate, monthsCount);

        for (int i = 0; i < monthsCount; ++i) {
            loanDate = loanDate.plusMonths(1);
            monthlyPaymentList.add(new MonthlyPayment(loanDate, monthlyPayment));
        }

        return new ArrayList<>(monthlyPaymentList);
    }

    private static BigDecimal getMonthlyPayment(BigDecimal totalSum, BigDecimal annualInterestRate, int monthsCount) {
        return Calculator.annuityMonthlyPayment(totalSum, annualInterestRate, monthsCount);
    }
}