import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OrderFormTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() { System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");}

    @BeforeEach
    void setUp() { driver = new ChromeDriver(); }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestForm() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Гаврилов Арсений");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();

    }
}