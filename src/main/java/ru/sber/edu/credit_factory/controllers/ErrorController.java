package ru.sber.edu.credit_factory.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.edu.credit_factory.security.CF_UserPrincipal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, @AuthenticationPrincipal CF_UserPrincipal principal) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String req = request.getMethod() + " '" + request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI) + "'" +
                " from user_id: " + principal.getUser().getId();
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                log.error(req + " - access denied - !");
                return "error/access_denied";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                log.error(req + " - page not found - !");
                return "error/not_found";
            }
        }
        return "error/error";
    }
}
