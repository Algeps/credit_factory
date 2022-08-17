package ru.sber.edu.credit_factory.enums;

public enum ApplicationStatus {
    DATA_ENTRY("Ввод данных"),
    PENDING_CONSIDERATION("Ожидание Рассмотрения"),
    WAITING_FOR_ISSUANCE("Ожидание Выдачи"),
    WAITING_FOR_REFUSAL_MESSAGE("Ожидание сообщения об Отказе"),
    DENIED("Отказано"),
    ISSUED("Выдано");

    private final String status;
    ApplicationStatus(String status) {
        this.status = status;
    }

    public String getStringStatus() {
        return status;
    }
}
