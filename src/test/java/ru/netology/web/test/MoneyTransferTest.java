package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.ErrorMessage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        val loginPage = open("http://localhost:9999", LoginPageV1.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstBillBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillBeforeTransfer = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.secondBill();
        int amount = 3000;
        moneyTransfer.transferMoney(amount, DataHelper.CardNumber.getCardFirst());
        val balanceFirstBillAfterTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillAfterTransfer = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstBillBeforeTransfer - amount), balanceFirstBillAfterTransfer);
        assertEquals((balanceSecondBillBeforeTransfer + amount), balanceSecondBillAfterTransfer);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCardNoValid() {
        val loginPage = open("http://localhost:9999", LoginPageV1.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstBillBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillBeforeTransfer = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.firstBill();
        int amount = 35000;
        moneyTransfer.transferMoney(amount, DataHelper.CardNumber.getCardSecond());
        ErrorMessage.errorMassageBalance();
    }
    @Test
    void shouldTransferMoneyFromSecondCardToFirstCardMaxAmount() {
        val loginPage = open("http://localhost:9999", LoginPageV1.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstBillBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillBeforeTransfer = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.firstBill();
        int amount = 10000;
        moneyTransfer.transferMoney(amount, DataHelper.CardNumber.getCardSecond());
        val balanceSecondBillAfterTransfer = dashboardPage.getSecondCardBalance();
        val balanceFirstBillAfterTransfer = dashboardPage.getFirstCardBalance();
        assertEquals((balanceSecondBillBeforeTransfer - amount), balanceSecondBillAfterTransfer);
        assertEquals((balanceFirstBillBeforeTransfer + amount), balanceFirstBillAfterTransfer);
    }
}
