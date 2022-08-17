package ru.sber.edu.credit_factory.entity;

import lombok.*;
import ru.sber.edu.credit_factory.enums.UserRole;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "USERS")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "SURNAME", nullable = false)
    private String lastName;
    @Column(name = "PATRONYMIC", nullable = false)
    private String patronymic;
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole = UserRole.CLIENT;

    @Override
    public String toString() {
        return login;
    }

    public String getDisplayUserRole() {
        return userRole.getStringUserRole();
    }
}
