import category.SeleniumForTomcatSample
import org.junit.*
import org.junit.rules.TestName
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.io.FileHandler
import org.junit.experimental.categories.Category


/**
 * Groovy + JUnitで書いたブラウザテストのサンプル。
 * Tomcatサイトにある「sample.war」をデプロイした状態で動かすことを期待している。
 */
@Category(SeleniumForTomcatSample.class)
class TomcatSampleTest {

    static final File EVIDENCE_DIR = new File("build/test-evidence/selenium")

    @Rule
    public TestName names = new TestName()

    WebDriver driver

    File methodEvidenceDir = null

    @BeforeClass
    static void setUp() {
        if (EVIDENCE_DIR.exists()) {
            EVIDENCE_DIR.deleteDir()
        }
        EVIDENCE_DIR.mkdirs()
    }

    @Before
    void initMethod() {
        // メソッド名のディレクトリ作成
        methodEvidenceDir = new File(EVIDENCE_DIR , this.class.name + "." + names.methodName)
        methodEvidenceDir.mkdirs()
        clearDriver()
        driver = EnvironmentDifferentAbsorber.createWebDriver()
    }

    @After
    void endMethod() {
        getScreenShot("テスト終了時")
        methodEvidenceDir = null
        clearDriver()
    }

    void clearDriver() {
        if (driver != null) {
            driver.close()
            driver = null
        }
    }

    /**
     * スクリーンショットを取得する。
     * @param key
     */
    void getScreenShot(String sheen) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        File destFile = new File(methodEvidenceDir, sheen + ".png")
        try {
            FileHandler.copy(scrFile, destFile)
        } catch (IOException e1) {
            fail("file error : " + destFile.path)
        }
    }

    @Test
    void トップページまで到達できる() {
        println(NetworkInterface.getNetworkInterfaces())
    }
}
 