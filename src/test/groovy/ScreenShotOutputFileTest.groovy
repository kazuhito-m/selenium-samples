import org.junit.*
import org.junit.Test
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.io.*

public class ScreenShotOutputFileTest {


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
    void yahooTest() {

        driver.get("http://yahoo.co.jp/")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR , "Y1.png"))
        } catch (IOException e1) {
            fail("file error 1")
        }

        // Click 'economy' link
        WebElement e = driver.findElement(By.id("economy"))
        e.click()

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR , "Y2.png"))
        } catch (IOException e2) {
            fail("file error 2")
        }

        // Search SKYSEA Client View
        e = driver.findElement(By.id("srchtxt"))
        e.sendKeys("SKYSEA Client View")
        e.submit()

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR , "Y3.png"))
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
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR , "Y4.png"))
        } catch (IOException e4) {
            fail("file error 4")
        }

    }

    @Test
    public void googleTest() {
        driver.get("http://www.google.co.jp/")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR , "G1.png"))
        } catch (IOException e1) {
            fail("file error G1")
        }
    }

    @Test
    public void seleniumTest() {
        driver.get("http://docs.seleniumhq.org")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File(EVIDENCE_DIR , "S1.png"))
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
 