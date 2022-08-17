package ru.sber.edu.credit_factory.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.edu.credit_factory.edit_form.AppCreditEditForm;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.enums.ApplicationStatus;
import ru.sber.edu.credit_factory.enums.UserRole;
import ru.sber.edu.credit_factory.security.CF_UserPrincipal;
import ru.sber.edu.credit_factory.security.RequestVerificationService;
import ru.sber.edu.credit_factory.services.ApplicationCreditService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Controller
@Secured("ROLE_CREDIT_INSPECTOR")
@RequestMapping(value = "/credit_inspector")
public class CreditInspectorController {
    private final ApplicationCreditService applicationCreditService;

    @Autowired
    public CreditInspectorController(ApplicationCreditService applicationCreditService) {
        this.applicationCreditService = applicationCreditService;
    }

    /**
     * Начальная страница для кредитного инспектора.
     */
    @GetMapping()
    public String index(Model model, @AuthenticationPrincipal CF_UserPrincipal principal, HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        model.addAttribute("current", principal.getUser());
        model.addAttribute("appsCredit", applicationCreditService.getAllAppsCredit());
        return "/credit_inspector/credit_inspector_profile";
    }

    /**
     * Отображает необходимые заявки.
     */
    @GetMapping("/list_apps_credit")
    public String working(Model model, @AuthenticationPrincipal CF_UserPrincipal principal,
                          HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        model.addAttribute("current", principal.getUser());
        model.addAttribute("apps_credit", applicationCreditService
                .getAllAppsCreditWithFilter(e -> !e.getApplicationStatus().equals(ApplicationStatus.DATA_ENTRY)));
        return "/apps_credit/list_apps_credit";
    }

    /**
     * Работа с заявкой - страница.
     */
    @GetMapping("/edit_app_credit/{id}")
    public String ticketWork(Model model,
                             @AuthenticationPrincipal CF_UserPrincipal principal,
                             @PathVariable("id") Long id, HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        model.addAttribute("current", principal.getUser());
        model.addAttribute("app_credit", applicationCreditService.getAppCreditById(id));
        model.addAttribute("app_credit_edit_form", new AppCreditEditForm());
        model.addAttribute("appStatusList", Arrays.stream(ApplicationStatus.values())
                .filter(e -> !e.getStringStatus().equals(ApplicationStatus.DATA_ENTRY.getStringStatus()))
                .collect(Collectors.toList()));
        return "/apps_credit/edit_app_credit";
    }

    /**
     * Пост запрос для измениня статуса заявки.
     */
    @PostMapping("/app_credit/{id}")
    public String workingAppCredit(@ModelAttribute("app_credit_edit_form") AppCreditEditForm appCreditEditForm,
                                   @AuthenticationPrincipal CF_UserPrincipal principal, HttpServletRequest request) {
        String result = RequestVerificationService.requestVerification(principal, this::isRole);
        if(!result.equals("")) return result;
        log.info(request.getMethod() + " '" + request.getServletPath() + "'");

        applicationCreditService.updateStatusApplicationCredit(appCreditEditForm);
        return "redirect:/credit_inspector/list_apps_credit";
    }

    /**
     * Метод запроса соответствия роли, из БД.
     */
    private boolean isRole(User user) {
        return applicationCreditService.getRole(user).equals(UserRole.CREDIT_INSPECTOR);
    }
}
