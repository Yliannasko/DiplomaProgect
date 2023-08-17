package ru.netology.diplomaprogect.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.diplomaprogect.data.DataHelper;
import ru.netology.diplomaprogect.data.SQLHelper;
import ru.netology.diplomaprogect.page.Buy;
import ru.netology.diplomaprogect.page.BuyCredit;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diplomaprogect.data.SQLHelper.cleanDatabase;
import static ru.netology.diplomaprogect.data.SQLHelper.getOrderCount;

public class BuyCreditTest {
    BuyCredit buyCredit = new BuyCredit();

    @BeforeEach
    void setup() {
        open("http://localhost:8080");

    }
    @BeforeAll
    static void setAll() {
    SelenideLogger.addListener("allure", new AllureSelenide());
}

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    public void cleanDataBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Should purchase must be successful with card number from the database")
    public void shouldSuccessfulPurchase() {
       // var buyCredit = open("http://localhost:8080", BuyCredit.class);
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.approvedStatus();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Should purchase must be unsuccessful with card from the database")
    public void shouldUnsuccessfulPurchase() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getDeclinedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.declinedStatus();
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Payment for the tour if the card number is missing")
    public void testCardNumberEmpty() {
       // var buyCredit = open("http://localhost:8080", BuyCredit.class);
        buyCredit.buyCreditCard();
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the card number less than 16 digits")
    public void testCardNumberLess16Digits() {
        buyCredit.buyCreditCard();
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardNumber(DataHelper.getCardNumber15Symbols());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour new card number")
    public void testNewCardNumber() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getNewCardNumber());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.declinedStatus();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month is missing ")
    public void testMonthEmpty() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month from 1 digit ")
    public void testMonth1Digit() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber1symbol());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month value greater than 12")
    public void testMonthMore12() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumberMoreThan12());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month from 00")
    public void testMonth00() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber00());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year from is missing")
    public void testYearEmpty() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year from 1 digit")
    public void testYear1Digit() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYearNumber1symbol());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year less than the current one")
    public void testYearExpired() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYearNumberLessThanThisYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.cardExpired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year 00")
    public void testYear00() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYearNumber00());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.cardExpired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder empty")
    public void testCardholderEmpty() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.fieldRequired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder from one word")
    public void testCardholderOneWord() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholderOneWord());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder from the cyrillic alphabet")
    public void testCardholderCyrillic() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholderWithCyrillicAlphabet());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder with digits")
    public void testCardholderWithDigits() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholderWithNumbers());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder with invalid symbol")
    public void testCardholderWithInvalidSymbol() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholderWithInvalidSymbol());
        buyCredit.setCardCvv(DataHelper.getCVV());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cvv empty")
    public void testCvvEmpty() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.clickContinueButton();
        buyCredit.fieldRequired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cvv from 1 digit")
    public void testCvv1Digit() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVVFromOneDigit());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cvv from 2 digits")
    public void testCvv2Digits() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.getCVVFromTwoDigit());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }
}
