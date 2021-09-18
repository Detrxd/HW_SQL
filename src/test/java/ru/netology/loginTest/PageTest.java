package ru.netology.loginTest;

import ru.netology.data.DataGenerator;
import ru.netology.data.DataHelper;
import ru.netology.database.DataManager;
import org.junit.jupiter.api.*;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PageTest {
    private void successAuthorization() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getAuthInfo2();
        LoginPage loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @BeforeEach
    void browserSetup() {
        open("http:/localhost:9999");
    }

    @AfterAll
    static void cleanBase() {
        DataManager.clearDatabase();
    }

    @Test
    void shouldFallDownRegistration() {
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin(DataGenerator.getRandomLogin(), DataHelper.getAuthInfo().getPassword());
    }

    @Test
    void shouldLoginActiveUser() {
        successAuthorization();
    }

    @Test
    void shouldFallDownByWrongPass() {
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin(DataHelper.getAuthInfo().getLogin(), DataGenerator.getRandomPassword());
    }

    @Test
    void shouldFallDownForManyWrongPass() {
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin(DataHelper.getAuthInfo().getLogin(), DataGenerator.getRandomPassword());
        loginPage.invalidLogin(DataHelper.getAuthInfo().getLogin(), DataGenerator.getRandomPassword());
        loginPage.invalidLogin(DataHelper.getAuthInfo().getLogin(), DataGenerator.getRandomPassword());
        var actual = DataManager.getUserStatusByLogin(DataHelper.getAuthInfo().getLogin());
        assertNotEquals("active", actual);
    }

    @Test
    void shouldFallDownForFailedAuthCode() {
        var wrongAuth = DataHelper.getAuthInfo();
        LoginPage loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(wrongAuth.getLogin(), wrongAuth.getPassword());
    }

    @Test
    void shouldSendEmptyField() {
        var authInfo = DataHelper.getAuthInfo();
        LoginPage loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        verifyPage.sendEmptyField();
    }

    @Test
    void shouldFallDownForManyWrongAuthCode() {
        var authInfo = DataHelper.getAuthInfo2();
        LoginPage loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        verifyPage.invalidVerify(DataGenerator.getRandomAuthCode());
        verifyPage.invalidVerify(DataGenerator.getRandomAuthCode());
        verifyPage.getBlockMessage(DataGenerator.getRandomAuthCode());
        assertNotEquals("active", DataManager.getUserStatusByLogin(DataHelper.getAuthInfo().getLogin()));
    }
}
