import org.junit.*
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.*

class SeleniumSampleTest {


    @BeforeClass
    static void setup() {
        // System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome")	// for Linux Chrome
    }

    @Test
    public void test1() {

        // WebDriver driver = new ChromeDriver()
        FirefoxDriver driver = new FirefoxDriver()

        driver.get("http://www.htmlhifive.com/conts/web/view/Main/WebHome")
        WebElement elTutorial = driver.findElement(By.linkText("⇒こちら"))
        elTutorial.click();

        WebDriverWait wait = new WebDriverWait(driver, 10)
        WebElement elTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("document-title")))

        // action & assertion
        assert elTitle.getText() == "チュートリアル"

        driver.quit();

    }
}