package ru.netology.web.page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class ErrorMessage {
    public static void errorMassageBalance (){
        $(withText("Недостаточно средств для проведения операции")).shouldBe(Condition.visible);
    }
}