package ru.sber.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//todo BACKEND
// *при заполнеии заявки, нажимается кнопка "посчитать ежемесячный платёж" и перебрасывет на следующию страницу с
//  таблицей платежей (дата и сумма) и внизу место для галочки "я принимаю солгашение"(ссылка на соглашение) и
//  кнопка "отправить заявку на рассмотрение"
// *Проверка введённых данных пользователем на серверной части при оформлении заявки
// *блокировка заявки во время работы с ней

//todo FRONTEND
// *подключить всем страницам main.css
// *сделать красивое отображение страниц
// *изменить страницу для взятие кредита(добавить ползунок для изменения суммы и количества месяце)


@SpringBootApplication
public class CreditFactoryApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(CreditFactoryApplication.class, args);
	}
}
