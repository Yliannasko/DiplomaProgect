package ru.netology.diplomaprogect.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import ru.netology.diplomaprogect.data.DataHelper;
import ru.netology.diplomaprogect.data.SQLHelper;

import ru.netology.diplomaprogect.page.BuyCredit;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diplomaprogect.data.SQLHelper.getOrderCount;

public class BuyCreditTest {
    public static String url = System.getProperty("sut.url");
    BuyCredit buyCredit = new BuyCredit();

    @BeforeEach
    public void openPage() {
        open(url);
    }

    @BeforeAll
    static void setAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    public void cleanDataBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Should purchase must be successful with card number from the database")
    public void shouldSuccessfulPurchase() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
        buyCredit.clickContinueButton();
        buyCredit.declinedStatus();
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Payment for the tour if the card number is missing")
    public void testCardNumberEmpty() {
        buyCredit.buyCreditCard();
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month from 1 digit ")
    public void testMonth1Digit() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.get1Digit());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
        buyCredit.clickContinueButton();
        buyCredit.cardExpirationError();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month from 00 for Approved card")
    public void testMonth00forApprovedCard() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.get00());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get3Digits());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the month from 00 for Declined card")
    public void testMonth00forDeclinedCard() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getDeclinedCard());
        buyCredit.setCardMonth(DataHelper.get00());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardYear(DataHelper.get1Digit());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardYear(DataHelper.get00());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.setCardCvv(DataHelper.get3Digits());
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
        buyCredit.incorrectFormat();
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
        buyCredit.setCardCvv(DataHelper.get1Digit());
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
        buyCredit.setCardCvv(DataHelper.get2Digits());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("Payment for the tour if the cvv from 000")
    public void testCvv000() {
        buyCredit.buyCreditCard();
        buyCredit.setCardNumber(DataHelper.getApprovedCard());
        buyCredit.setCardMonth(DataHelper.getMonthNumber());
        buyCredit.setCardYear(DataHelper.getYear());
        buyCredit.setCardholder(DataHelper.getNameCardholder());
        buyCredit.setCardCvv(DataHelper.get000());
        buyCredit.clickContinueButton();
        buyCredit.incorrectFormat();
        assertEquals(0, getOrderCount());
    }
}
