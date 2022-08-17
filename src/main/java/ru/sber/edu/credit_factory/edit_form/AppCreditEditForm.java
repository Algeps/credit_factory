package ru.sber.edu.credit_factory.edit_form;

import lombok.Data;
import ru.sber.edu.credit_factory.enums.ApplicationStatus;

@Data
public class AppCreditEditForm {
    private Long id;
    private ApplicationStatus applicationStatus;
}
