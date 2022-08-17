package ru.sber.edu.credit_factory.calculator_payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Один месячный платёж.
 */
@AllArgsConstructor
@Getter
public final class MonthlyPayment {
    private LocalDate data;
    private BigDecimal sum;

    @Override
    public String toString() {
        return "Дата: " + data.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                + ", Сумма: " + sum.toString() + " рублей";
    }
}
