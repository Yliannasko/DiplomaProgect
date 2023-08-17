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
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should purchase must be unsuccessful with card from the database")
    public void shouldUnsuccessfulPurchase() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getDeclinedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.declinedStatus();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Payment for the tour if the card number is missing")
    public void testCardNumberEmpty() {
        buy.buyDebitCard();
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the card number less than 16 digits")
    public void testCardNumberLess16Digits() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getCardNumber15Symbols());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour new card number")
    public void testNewCardNumber() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getNewCardNumber());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.declinedStatus();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month is missing ")
    public void testMonthEmpty() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month from 1 digit ")
    public void testMonth1Digit() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber1symbol());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month value greater than 12")
    public void testMonthMore12() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumberMoreThan12());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month from 00")
    public void testMonth00() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber00());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year from is missing")
    public void testYearEmpty() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year from 1 digit")
    public void testYear1Digit() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYearNumber1symbol());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year less than the current one")
    public void testYearExpired() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYearNumberLessThanThisYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.cardExpired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the year 00")
    public void testYear00() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYearNumber00());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.cardExpired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder empty")
    public void testCardholderEmpty() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.fieldRequired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder from one word")
    public void testCardholderOneWord() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholderOneWord());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder from the cyrillic alphabet")
    public void testCardholderCyrillic() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholderWithCyrillicAlphabet());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder with digits")
    public void testCardholderWithDigits() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholderWithNumbers());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cardholder with invalid symbol")
    public void testCardholderWithInvalidSymbol() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholderWithInvalidSymbol());
        buy.setCardCvv(DataHelper.getCVV());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cvv empty")
    public void testCvvEmpty() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.clickContinueButton();
        buy.fieldRequired();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cvv from 1 digit")
    public void testCvv1Digit() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVVFromOneDigit());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cvv from 2 digits")
    public void testCvv2Digits() {
        buy.buyDebitCard();
        buy.setCardNumber(DataHelper.getApprovedCard());
        buy.setCardMonth(DataHelper.getMonthNumber());
        buy.setCardYear(DataHelper.getYear());
        buy.setCardholder(DataHelper.getNameCardholder());
        buy.setCardCvv(DataHelper.getCVVFromTwoDigit());
        buy.clickContinueButton();
        buy.incorrectFormat();
        assertEquals(0, getOrderCount());
    }
}