import static org.junit.Assert.*

import org.junit.Test
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.io.*

public class ScreenShotOutputFileTest {

    @Test
    public void YahooTest() {

        WebDriver driver = new FirefoxDriver()

        // Access to Yahoo

        driver.get("http://yahoo.co.jp/")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File("out/evidences/screens/Y1.png"))
        } catch (IOException e1) {
            fail("file error 1")
        }

        // Click 'economy' link

        WebElement e = driver.findElement(By.id("economy"))
        e.click()

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File("out/evidences/screens/Y2.png"))
        } catch (IOException e2) {
            fail("file error 2")
        }

        // Search SKYSEA Client View

        e = driver.findElement(By.id("srchtxt"))
        e.sendKeys("SKYSEA Client View")
        e.submit()

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File("out/evidences/screens/Y3.png"))
        } catch (IOException e3) {
            fail("file error 3")
        }

        try {
            e = driver.findElement(By.linkText("Sky株式会社の SKYSEA Client View"))
        } catch (NoSuchElementException ne) {
            fail("No Link for SKYSEA")
            driver.close()
        }
        e.click()

        // Now we see SKYSEA Client View's site

        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File("out/evidences/screens/Y4.png"))
        } catch (IOException e4) {
            fail("file error 4")
        }

        // Done.

        driver.close()
    }

    @Test
    public void GoogleTest() {
        WebDriver driver = new FirefoxDriver()

        // Access to Google

        driver.get("http://www.google.co.jp/")
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File("out/evidences/screens/G1.png"))
        } catch (IOException e1) {
            fail("file error G1")
        }

        driver.close()
    }

    @Test
    public void SeleniumTest() {

        WebDriver driver = new FirefoxDriver()

        driver.get("http://docs.seleniumhq.org")

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        try {
            FileHandler.copy(scrFile, new File("out/evidences/screens/S1.png"))
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

        driver.close()

    }
}
 