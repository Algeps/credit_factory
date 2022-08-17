package ru.sber.edu.credit_factory.enums;

/**
 *  Роли.
 */
public enum UserRole {
    ADMIN("Администратор"),
    CREDIT_INSPECTOR("Кредитный инспектор "),
    UNDERWRITER("Андерайдер"),
    CLIENT("Клиент"),
    BANNED("Заблокирован");

    private final String status;
    UserRole(String status) {
        this.status = status;
    }

    public String getStringUserRole() {
        return status;
    }
}
