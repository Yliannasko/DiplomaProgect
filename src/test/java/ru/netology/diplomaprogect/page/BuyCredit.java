package ru.netology.diplomaprogect.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyCredit {
    private SelenideElement heading = $$("h3").find(exactText("Оплата по карте"));
    private SelenideElement cardNumber = $("[class='input__control']").shouldHave(exactText("Номер карты"));
    private SelenideElement cardMonth = $("[class='input__control']").shouldHave(exactText("Месяц"));
    private SelenideElement cardYear = $("[class='input__control']").shouldHave(exactText("Год"));
    private SelenideElement cardOwner = $("[class='input__control']").shouldHave(exactText("Владелец"));
    private SelenideElement cardCvv = $("[class='input__control']").shouldHave(exactText("CVC/CVV"));
    private SelenideElement approvedStatus = $("[class='notification__content']").shouldHave(exactText("Операция одобрена Банком."));
    private SelenideElement declinedStatus = $("[class='notification__content']").shouldHave(exactText("Ошибка! Банк отказал в проведении операции."));
    private  SelenideElement incorrectFormat = $(byText("Неверный формат"));
    private SelenideElement cardExpirationError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(byText("Истёк срок действия карты"));
    private SelenideElement fieldRequired = $(byText("Поле обязательно для заполнения"));
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public BuyCredit(){
        heading.shouldBe(visible);
    }
}
