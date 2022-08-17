package ru.sber.edu.credit_factory.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.edu.credit_factory.edit_form.AppCreditEditForm;
import ru.sber.edu.credit_factory.entity.ApplicationCredit;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.enums.UserRole;
import ru.sber.edu.credit_factory.repo.ApplicationCreditRepository;
import ru.sber.edu.credit_factory.repo.UserRepository;
import ru.sber.edu.credit_factory.services.ApplicationCreditService;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ApplicationCreditServiceImpl implements ApplicationCreditService {
    private final UserRepository userRepository;
    private final ApplicationCreditRepository applicationCreditRepository;

    @Autowired
    public ApplicationCreditServiceImpl(UserRepository userRepository, ApplicationCreditRepository applicationCreditRepository) {
        this.userRepository = userRepository;
        this.applicationCreditRepository = applicationCreditRepository;
    }

    @Override
    public void create(ApplicationCredit applicationCredit) {
        applicationCreditRepository.save(applicationCredit);
    }

    @Override
    public ApplicationCredit createAndReturn(ApplicationCredit applicationCredit) {
        return applicationCreditRepository.save(applicationCredit);
    }

    @Override
    public List<ApplicationCredit> allAppsCreditByClientId(Long clientId) {
        return applicationCreditRepository.getAllByClient_Id(clientId);
    }

    @Override
    public void updateStatusApplicationCredit(AppCreditEditForm appCreditEditForm) {
        ApplicationCredit applicationCredit = this.getAppCreditById(appCreditEditForm.getId());
        applicationCredit.setApplicationStatus(appCreditEditForm.getApplicationStatus());
        applicationCreditRepository.save(applicationCredit);
    }

    @Override
    public ApplicationCredit getAppCreditById(Long id) {
        return applicationCreditRepository.getById(id);
    }

    @Override
    public List<ApplicationCredit> getAllAppsCredit() {
        return applicationCreditRepository.findAll().stream()
                .sorted(Comparator.comparing(ApplicationCredit::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationCredit> getAllAppsCreditWithFilter(Predicate<ApplicationCredit> filter) {
        return applicationCreditRepository.findAll().stream()
                .filter(filter)
                .sorted(Comparator.comparing(ApplicationCredit::getId))
                .collect(Collectors.toList());
    }

    @Override
    public UserRole getRole(User user) {
        return userRepository.findByLogin(user.getLogin()).getUserRole();
    }
}
