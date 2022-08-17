package ru.sber.edu.credit_factory.security;

import java.util.function.Predicate;

public class RequestVerificationService {
    /**
     * Проверка запроса на доступ.
     */
    public static String requestVerification(CF_UserPrincipal principal,
                                             Predicate<ru.sber.edu.credit_factory.entity.User> isRole) {
        if (principal == null || !isRole.test(principal.getUser())) return "error";
        return "";
    }
}
