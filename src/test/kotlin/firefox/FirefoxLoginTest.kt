package firefox

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.Duration

class FirefoxLoginTest {
    private final val BASE_URL: String = "https://lamoda.ru/"

    private lateinit var driver: RemoteWebDriver

    @BeforeEach
    fun setUp(){
        driver = FirefoxOptions().let { options ->
            FirefoxDriver(options).apply {
                this.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(5))
            }
        }
    }

    @AfterEach
    fun tearDown(){
        driver.quit()
    }

    @Test
    fun `login test`(){
        driver.get(BASE_URL)
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[2]")).sendKeys("dabvre376@mail.ru")
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("qwert678L")
        Thread.sleep(500)
        val action1 = Actions(driver)
        val enter = driver.findElement(By.xpath("(//button[text()='Войти'])[2]"))
        action1.moveToElement(enter)
        action1.click()
        action1.perform()

        try {
            while (true) {
                Thread.sleep(500)
                driver.findElement(By.xpath("//input[@id='recaptcha-token']"))
            }
        } catch (_: NoSuchElementException) {}
        Thread.sleep(500)
        val action2 = Actions(driver)
        val profile = driver.findElement(By.xpath("//span[text()='Профиль']"))
        action2.moveToElement(profile)
        action2.perform()
        Thread.sleep(500)
        driver.findElement(By.xpath("//span[text()='Дмитрий Беляков']"))
    }

    @Test
    fun `login test wrong password`(){
        driver.get(BASE_URL)
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        Thread.sleep(200)
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[2]")).sendKeys("dabvre376@mail.ru")
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("qwert678")
        Thread.sleep(200)
        val action1 = Actions(driver)
        val enter = driver.findElement(By.xpath("(//button[text()='Войти'])[2]"))
        action1.moveToElement(enter)
        action1.click()
        action1.perform()
        try {
            while (true) {
                Thread.sleep(500)
                driver.findElement(By.xpath("//input[@id='recaptcha-token']"))
            }
        } catch (_: NoSuchElementException) {}
        Thread.sleep(500)
        driver.findElement(By.xpath("//div[text()='Неверный логин или пароль.']"))
    }


    @Test
    fun `login test without password`(){
        driver.get(BASE_URL)
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[2]")).sendKeys("dabvre376@mail.ru")
        driver.findElement(By.xpath("(//button[text()='Войти'])[2]")).let {
            Assertions.assertFalse(it.isEnabled)
            Assertions.assertThrows(ElementClickInterceptedException::class.java){
                it.click()
            }
        }
    }
}