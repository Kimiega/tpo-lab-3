package chrome

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.firefox.FirefoxDriver as ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.Duration

class ChromeRegistrationTest {
    private final val BASE_URL: String = "https://lamoda.ru/"

    private lateinit var driver: RemoteWebDriver

    @BeforeEach
    fun setUp() {
        driver = ChromeDriver().apply {
                this.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(5))
        }
    }


    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun `registration test`() {
        driver.get(BASE_URL)
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("//a[text() = 'Зарегистрироваться']")).click()
        driver.findElement(By.xpath("//input[@name='Имя']")).sendKeys("Дмитрий")
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("9199191199")
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("43243243DAc(")
        driver.findElement(By.xpath("//input[@name='password_confirmation']")).sendKeys("43243243DAc(")
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[3]")).sendKeys("dmitry@belyakov.ru")
        driver.findElement(By.xpath("//button[text()='Зарегистрироваться']")).let {
            Assertions.assertTrue(it.isEnabled)
            it.click()
        }
    }


    @Test
    fun `registration test without phone`() {
        driver.get(BASE_URL)
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("//a[text() = 'Зарегистрироваться']")).click()
        driver.findElement(By.xpath("//input[@name='Имя']")).sendKeys("Дмитрий")
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("43243243DAc(")
        driver.findElement(By.xpath("//input[@name='password_confirmation']")).sendKeys("43243243DAc(")
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[3]")).sendKeys("dmitry@belyakov.ru")
        driver.findElement(By.xpath("//button[text()='Зарегистрироваться']")).let {
            Assertions.assertFalse(it.isEnabled)
            Assertions.assertThrows(ElementClickInterceptedException::class.java){
                it.click()
            }
        }
    }
}