import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    public String visitDate(int longDate) {
        LocalDate today = LocalDate.now();
        LocalDate method = today.plusDays(longDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return method.format(formatter);
    }

    @Test
    public void deliveryTest() {

        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        String planData = visitDate(3);
        $(".calendar-input input").setValue(planData);
        $("[data-test-id='name'] input").setValue("Николай Комягин");
        $("[data-test-id='phone'] input").setValue("+79995553535");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//div//*[@class='notification__content']").should(Condition.text("Встреча успешно забронирована на " + planData), Duration.ofSeconds(13));
        $x("//div//*[@class='notification__content']").shouldBe(Condition.visible);
    }
}
