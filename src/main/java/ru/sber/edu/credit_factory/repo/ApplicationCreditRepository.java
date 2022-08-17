package ru.sber.edu.credit_factory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.credit_factory.entity.ApplicationCredit;

import java.util.List;

@Repository
public interface ApplicationCreditRepository extends JpaRepository<ApplicationCredit, Long> {
    List<ApplicationCredit> getAllByClient_Id(long id);
}
