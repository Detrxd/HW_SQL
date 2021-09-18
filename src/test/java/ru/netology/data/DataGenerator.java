package ru.netology.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    public static String getRandomLogin() {
        String login = faker.cat().name();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.bothify("#??#?#");
        return password;
    }

    public static String getRandomAuthCode() {
        String authCode = faker.numerify("######");
        return authCode;
    }

    public static String getRandomUserId() {
        String userId = faker.numerify("######");
        return userId;
    }

}
