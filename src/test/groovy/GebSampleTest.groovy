import geb.Browser
import geb.Page
import geb.navigator.NonEmptyNavigator
import org.junit.*
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.htmlunit.HtmlUnitWebElement
import org.openqa.selenium.io.FileHandler
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

public class GebSampleTest {


    static final File EVIDENCE_DIR = new File("build/test-evidence")

    WebDriver driver

    @BeforeClass
    static void setUp() {
        if (EVIDENCE_DIR.exists()) {
            EVIDENCE_DIR.deleteDir()
        }
        EVIDENCE_DIR.mkdirs()
    }

    @Before
    void initMethod() {
        clearDriver()
        driver = new FirefoxDriver()
    }

    @After
    void endMethod() {
        clearDriver()
    }

    void clearDriver() {
        if (driver != null) {
            driver.close()
            driver = null
        }
    }


    @Test
    public void basicTest() {

        Browser.drive {
            go 'http://www.htmlhifive.com/conts/web/view/Main/WebHome'

            // 1ページ目、”⇛こちら”ってリンクを探しクリック。
            assert title == "hifive - HTML5とスマートフォンのための開発プラットフォーム - hifive"
            def link = $('a').allElements().find() { it.text == "⇒こちら" }
            link.click()

            // 2ページ目、在る要素のテキストを確認
            assert title == 'チュートリアル - hifive'
            // action & assertion
            assert $("#document-title").text() == "チュートリアル"
        }

    }

    @Ignore
    @Test
    void yahooTest() {

        driver.get("http://yahoo.co.jp/")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR, "Y1.png"))
        } catch (IOException e1) {
            fail("file error 1")
        }

        // Click 'economy' link
        WebElement e = driver.findElement(By.id("economy"))
        e.click()

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR, "Y2.png"))
        } catch (IOException e2) {
            fail("file error 2")
        }

        // Search SKYSEA Client View
        e = driver.findElement(By.id("srchtxt"))
        e.sendKeys("SKYSEA Client View")
        e.submit()

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR, "Y3.png"))
        } catch (IOException e3) {
            fail("file error 3")
        }

        try {
            e = driver.findElement(By.linkText("Sky株式会社の SKYSEA Client View"))
        } catch (NoSuchElementException ne) {
            fail("No Link for SKYSEA")
        }
        e.click()

        // Now we see SKYSEA Client View's site

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR, "Y4.png"))
        } catch (IOException e4) {
            fail("file error 4")
        }

    }

    @Ignore
    @Test
    public void googleTest() {
        driver.get("http://www.google.co.jp/")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR, "G1.png"))
        } catch (IOException e1) {
            fail("file error G1")
        }
    }

    @Ignore
    @Test
    public void seleniumTest() {
        driver.get("http://docs.seleniumhq.org")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR, "S1.png"))
        } catch (IOException e1) {
            fail("file error S1")
        }

        WebElement e = null
        try {
            e = driver.findElement(By.id("menu_projects"))
        } catch (NoSuchElementException ne) {
            fail("No Link for SKYSEA")
        }
        e.click()
    }
}
 