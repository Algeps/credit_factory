package ru.sber.edu.credit_factory.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.edu.credit_factory.calculator_payment.Calculator;
import ru.sber.edu.credit_factory.calculator_payment.MonthlyPaymentList;
import ru.sber.edu.credit_factory.entity.ApplicationCredit;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.enums.UserRole;
import ru.sber.edu.credit_factory.security.CF_UserPrincipal;
import ru.sber.edu.credit_factory.security.RequestVerificationService;
import ru.sber.edu.credit_factory.services.ApplicationCreditService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Slf4j
@Controller
@Secured("ROLE_CLIENT")
@RequestMapping(value = "/client")
public class ClientController {
    private final ApplicationCreditService applicationCreditService;

    public ClientController(ApplicationCreditService applicationCreditService) {
        this.applicationCreditService = applicationCreditService;
    }

    /**
     * Начальная страница пользователя.
     */
    @GetMapping()
    public String index(Model model, @AuthenticationPrincipal CF_UserPrincipal principal,
                          HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        model.addAttribute("user", principal.getUser());
        return "/client/client_profile";
    }

    /**
     * Возвращает страницу для создания заявки.
     */
    @GetMapping("/app_credit/new")
    public String createApp(Model model, @AuthenticationPrincipal CF_UserPrincipal principal,
                            HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        model.addAttribute("current", principal.getUser());
        model.addAttribute("app_credit", new ApplicationCredit());
        return "/client/add_application";
    }

    /**
     * Создание и запись заявки в БД.
     */
    @PostMapping("/app_credit/new")
    public String createApp(Model model, @ModelAttribute ApplicationCredit applicationCredit,
                            @AuthenticationPrincipal CF_UserPrincipal principal, HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        applicationCredit.setClient(principal.getUser());
        //applicationCreditService.create(applicationCredit);
        return "redirect:/client/app_credit/new/calc";
    }

    /**
     * Возвращает страницу, на которой отображается ежемесячный платёж и подтверждения оформления.
     */
    @GetMapping("/app_credit/new/calc")
    public String calculateMonthlyPayment(Model model, @ModelAttribute ApplicationCredit applicationCredit,
                                          @AuthenticationPrincipal CF_UserPrincipal principal,
                                          HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        model.addAttribute("current", principal.getUser());
        model.addAttribute("monthlyPaymentList", MonthlyPaymentList
                .getMonthlyPaymentList(applicationCredit.getTotalSumLoan(), applicationCredit.getLoanAnnualRate(),
                        applicationCredit.getLoanTerm(), LocalDate.now()));
        return "/client/final_conditions_app_credit";
    }

    @PostMapping("/app_credit/new/agree")
    public String agree(Model model, @ModelAttribute("app_credit") ApplicationCredit applicationCredit,
                                          @AuthenticationPrincipal CF_UserPrincipal principal,
                                          HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        model.addAttribute("current", principal.getUser());

        return "redirect:/client";
    }

    /**
     * Возаращает страницу со всеми заявками от пользователя.
     */
    @GetMapping("/apps_credit")
    public String ticketNew(Model model, @AuthenticationPrincipal CF_UserPrincipal principal,
                            HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        final User current = principal.getUser();
        model.addAttribute("current", current);
        model.addAttribute("apps_credit",
                applicationCreditService.allAppsCreditByClientId(current.getId()));
        return "/client/client_list_apps_credit";
    }

    /**
     * Запрашивает роль из БД.
     */
    private boolean isRole(User user) {
        return applicationCreditService.getRole(user).equals(UserRole.CLIENT);
    }
}
