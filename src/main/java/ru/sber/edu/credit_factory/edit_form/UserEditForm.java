package ru.sber.edu.credit_factory.edit_form;

import lombok.Data;
import ru.sber.edu.credit_factory.enums.UserRole;

@Data
public class UserEditForm {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String patronymic;
    private UserRole userRole;
    private Boolean changePassword;
    private String newPassword;
}
