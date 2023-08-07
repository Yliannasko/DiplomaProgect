package ru.netology.diplomaprogect.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    public static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static CardNumber getApprovedCard() {
        return new CardNumber ("4444444444444441");
    }

    public static CardNumber getDeclinedCard() {
        return new CardNumber("4444444444444442");
    }

    public static String generateRandomMonth() {
        return faker.numerify("##");
    }
    public static String generateRandomYear() {
        return faker.numerify("##");
    }
    public static String generateRandomCardOwner() {
        return faker.name().username();
    }
    public static String generateRandomCvv() {
        return faker.numerify("###");
    }
    @Value
    public static class CardNumber {
        String number;
    }
    @Value
    public static class CardInfo {
        String month;
        String year;
        String cardOwner;
        String cvv;

    }
}