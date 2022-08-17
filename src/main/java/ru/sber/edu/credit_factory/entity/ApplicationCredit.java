package ru.sber.edu.credit_factory.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.sber.edu.credit_factory.enums.ApplicationStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "APPLICATIONS_CREDIT")
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "APPLICATION_STATUS", nullable = false)
    private ApplicationStatus applicationStatus = ApplicationStatus.DATA_ENTRY;
    @Column(name = "TOTAL_SUM", nullable = false)
    private BigDecimal totalSumLoan;
    @Column(name = "LOAN_TERM", nullable = false)
    private Integer loanTerm;
    @Column(name = "LOAN_ANNUAL_RATE", nullable = false)
    private BigDecimal loanAnnualRate;
    @Column(name = "PURPOSE_OF_LOAN", nullable = false)
    private String purposeOfLoan;
    @Column(name = "MONTHLY_INCOME", nullable = false)
    private BigInteger monthlyIncome;
    @Column(name = "DATETIME_CREATION")
    private LocalDateTime creationAt = LocalDateTime.now();

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(foreignKey = @ForeignKey(name = "client_id"))
    @ToString.Exclude
    private User client;

    public String getDisplayStatus() {
        return applicationStatus.getStringStatus();
    }
}
