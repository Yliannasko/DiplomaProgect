package ru.netology.diplomaprogect.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.checkerframework.checker.units.qual.C;

import java.util.Locale;

public class DataHelper {
    public static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

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

    public static String getMonthNumber1symbol() {
        return faker.numerify("#");
    }

    public static String getMonthNumber() {
        return String.format("%02d", faker.number().numberBetween(0, 13));
    }

    public static String getMonthNumberMoreThan12() {
        return String.format("%02d", faker.number().numberBetween(13, 99));
    }

    public static String getMonthNumber00() {
        return "00";
    }

    public static String getYearNumber1symbol() {
        return faker.numerify("#");
    }

    public static String getYear() {
        return String.format("%02d", faker.number().numberBetween(23, 30));
    }

    public static String getYearNumberLessThanThisYear() {
        return String.format("%02d", faker.number().numberBetween(00, 23));
    }

    public static String getYearNumber00() {
        return "00";
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

    public static String getCVV() {
        return faker.numerify("###");
    }

    public static String getCVVFromOneDigit() {
        return faker.numerify("#");
    }

    public static String getCVVFromTwoDigit() {
        return faker.numerify("##");
    }
    public static String getCVVWith000() {
        return "000";
    }

}
