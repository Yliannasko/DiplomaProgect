package ru.netology.diplomaprogect.data;

import com.github.javafaker.Faker;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;


public class DataHelper {
    public static Faker faker = new Faker(new Locale("en"));

    public static String getApprovedCard() {
        return "4444444444444441";
    }

    public static String getDeclinedCard() {
        return "4444444444444442";
    }

    public static String getNewCardNumber() {
        return faker.numerify("#### #### #### ####");
    }

    public static String getCardNumber15Symbols() {
        return "444444444444444";
    }

    public static String get1Digit() {
        return faker.numerify("#");
    }

    public static String get2Digits() {
        return faker.numerify("##");
    }

    public static String get3Digits() {
        return faker.numerify("###");
    }

    public static String get00() {
        return "00";
    }

    public static String get000() {
        return "000";
    }

    public static String getMonthNumber() {
        LocalDate currentData = LocalDate.now();
        return currentData.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthNumberMoreThan12() {
        int currentMonth = Integer.parseInt(getMonthNumber());
        int moreMonth = currentMonth + 12;
        return String.format("%02d", moreMonth % 100);
    }

    public static String getYear() {
        LocalDate currentData = LocalDate.now();
        LocalDate currentYear = currentData.plusYears(5);
        return currentYear.format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String getYearNumberLessThanThisYear() {
        LocalDate currentData = LocalDate.now();
        LocalDate currentYear = currentData.minusYears(1);
        return currentYear.format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String getNameCardholder() {
        return faker.name().firstName() + faker.name().lastName();
    }

    public static String getNameCardholderOneWord() {
        return faker.name().firstName();
    }

    public static String getNameCardholderWithCyrillicAlphabet() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + faker.name().lastName();
    }

    public static String getNameCardholderWithNumbers() {
        return faker.name().firstName() + faker.numerify("#####");
    }

    public static String getNameCardholderWithInvalidSymbol() {
        return faker.name().firstName() + "!@_+*";
    }

}
