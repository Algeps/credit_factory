package ru.sber.edu.credit_factory.services;

import ru.sber.edu.credit_factory.edit_form.AppCreditEditForm;
import ru.sber.edu.credit_factory.entity.ApplicationCredit;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.enums.UserRole;

import java.util.List;
import java.util.function.Predicate;

public interface ApplicationCreditService {
    /**
     * Создание новой заявки.
     */
    void create(ApplicationCredit applicationCredit);

    /*Создание заявки. Возвращает только что записанную заявку.*/
    ApplicationCredit createAndReturn(ApplicationCredit applicationCredit);

    /**
     * Изменение статуса заявки.
     */
    void updateStatusApplicationCredit(AppCreditEditForm appCreditEditForm);

    /**
     * Поиск заявки по id.
     */
    ApplicationCredit getAppCreditById(Long id);

    /**
     * Найти все заявки.
     */
    List<ApplicationCredit> getAllAppsCredit();

    /**
     * Получить спискок отфильтрованных заявок.
     */
    List<ApplicationCredit> getAllAppsCreditWithFilter(Predicate<ApplicationCredit> filter);

    /**
     * Найти все заявки одного пользователя по его id.
     */
    List<ApplicationCredit> allAppsCreditByClientId(Long clientId);

    /**
     * Получения статуса пользователя.
     */
    UserRole getRole(User user);
}
