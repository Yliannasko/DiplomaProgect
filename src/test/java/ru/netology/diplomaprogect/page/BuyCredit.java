package ru.netology.diplomaprogect.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.diplomaprogect.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class BuyCredit {
    private SelenideElement heading = $$("h2").find(exactText("Путешествие дня"));
    private SelenideElement buyCreditButton = $$(".button__text").find(exactText("Купить в кредит"));
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$("[class='input__control']");
    private SelenideElement cardMonth = $(byText("Месяц")).parent().$("[class='input__control']");
    private SelenideElement cardYear = $(byText("Год")).parent().$("[class='input__control']");
    private SelenideElement cardOwner = $(byText("Владелец")).parent().$("[class='input__control']");
    private SelenideElement cardCvv = $(byText("CVC/CVV")).parent().$("[class='input__control']");
    private SelenideElement approvedStatus = $(byText("Операция одобрена Банком.")).parent().$("[class='notification__content']");
    private SelenideElement declinedStatus = $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class='notification__content']");
    private SelenideElement incorrectFormat = $(byText("Неверный формат"));
    private SelenideElement cardExpirationError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(byText("Истёк срок действия карты"));
    private SelenideElement fieldRequired = $(byText("Поле обязательно для заполнения"));
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public void buyCreditCard() {
        open("http://localhost:8080");
        heading.shouldBe(visible);
        buyCreditButton.click();
    }

    public void setCardNumber(String number) {
        cardNumber.setValue(number);
    }

    public void setCardMonth(String month) {
        cardMonth.setValue(month);
    }

    public void setCardYear(String year) {
        cardYear.setValue(year);
    }

    public void setCardholder(String user) {
        cardOwner.setValue(user);
    }

    public void setCardCvv(String cvv) {
        cardCvv.setValue(cvv);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    public void approvedStatus() {
        approvedStatus.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void declinedStatus() {
        declinedStatus.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void incorrectFormat() {
        incorrectFormat.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void cardExpirationError() {
        cardExpirationError.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void cardExpired() {
        cardExpired.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void fieldRequired() {
        fieldRequired.shouldBe(visible, Duration.ofSeconds(10));
    }
}