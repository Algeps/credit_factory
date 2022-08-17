create table if not exists CLIENTS
(
    ID bigint PRIMARY KEY,
    SURNAME VARCHAR(200) NOT NULL,
    FIRST_NAME VARCHAR(200) NOT NULL,
    PATRONYMIC VARCHAR(200) NOT NULL,
    DATE_OF_BIRTH DATE NOT NULL,
    MONTHLY_INCOME DECIMAL
);
create type EMPLOYEE_STATUS as enum ('ADMINISTRATOR', 'CREDIT_INSPECTOR', 'UNDERWRITER');
create table if not exists EMPLOYEES
(
    ID bigint PRIMARY KEY,
    SURNAME VARCHAR(200) NOT NULL,
    FIRST_NAME VARCHAR(200) NOT NULL,
    PATRONYMIC VARCHAR(200) NOT NULL,
    EMPLOYEE_STATUS EMPLOYEE_STATUS
);
create type APPLICATION_STATUS as enum ('DATA_ENTRY', 'PENDING_CONSIDERATION', 'WAITING_FOR_ISSUANCE',
    'WAITING_FOR_REFUSAL_MESSAGE', 'DENIED', 'ISSUED');
create table if not exists APPLICATIONS
(
    ID bigint PRIMARY KEY,
    APPLICATION_STATUS APPLICATION_STATUS NOT NULL,
    TOTAL_SUM DECIMAL NOT NULL,
    LOAN_TERM INTEGER NOT NULL,
    LOAN_ANNUAL_RATE DECIMAL NOT NULL
);