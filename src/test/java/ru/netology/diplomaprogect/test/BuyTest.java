package ru.netology.diplomaprogect.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.diplomaprogect.data.DataHelper;
import ru.netology.diplomaprogect.data.SQLHelper;
import ru.netology.diplomaprogect.page.Buy;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diplomaprogect.data.SQLHelper.cleanDatabase;
import static ru.netology.diplomaprogect.data.SQLHelper.getOrderCount;

public class BuyTest {
    Buy buy = new Buy();

    @BeforeEach
    void setup() {
        open("http://localhost:8080");

    }
    @BeforeEach
    public void cleanDataBase() {
        SQLHelper.cleanDatabase();
    }

    @BeforeAll
    static void setAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should purchase must be successful with card number from the database")
    public void shouldSuccessfulPurchase() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.approvedStatus();
        assertEquals ("APPROVED",SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Should purchase must be unsuccessful with card from the database")
    public void shouldUnsuccessfulPurchase(){
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getDeclinedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.declinedStatus();
        assertEquals ("DECLINED",SQLHelper.getPaymentStatus());
    }
 //   @Test
//    @DisplayName("Payment for the tour if the card number is missing")
//    public void testCardNumberEmpty(){
//        buy.buyDebitCard();
//        buy.setCardMonth(DataHelper.getMonthNumber());
//        buy.setCardYear(DataHelper.getYear());
//        buy.setCardholder(DataHelper.getNameCardholder());
//        buy.setCardCvv(DataHelper.getCVV());
//        buy.clickContinueButton();
//        buy.incorrectFormat();
//        assertEquals(0,getOrderCount());
//    }

}