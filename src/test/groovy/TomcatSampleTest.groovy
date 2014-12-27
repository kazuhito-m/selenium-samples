import category.SeleniumForTomcatSample
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator
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
        getScreenShot("testEndScreen")
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
     * このテストで使うHTTPアドレスのホスト部を返す。
     * （バリバリ環境依存なので他では動かない）
     * @return 出来上がったhttpアドレスのホスト部。
     */
    String getBaseHttpAddress() {
        String addr = EnvironmentDifferentAbsorber.getLocalAddress()
        "http://${addr}:38080/"
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
        driver.get(baseHttpAddress)
        getScreenShot("Top")
        assert "Hollo World タイトルが見つかる" , driver.findElement(By.tagName("h1")) == 'Sample "Hello, World" Application'
    }

    @Test
    void トップページからJSPサンプルページへ遷移() {
        driver.get(baseHttpAddress)
        getScreenShot("Top")
        def link = driver.findElements(By.tagName("a")).find{ it == "JSP page" }
        assert 'Jsp sample pageのリンクが見つかる' , link == null
        link.click()
        assert "メインタイトルが見つかる" , driver.findElement(By.tagName("h1")) == 'Sample Application JSP Page'
    }

    @Test
    void トップページからServletサンプルページへ遷移() {
        driver.get(baseHttpAddress)
        getScreenShot("Top")
        def link = driver.findElements(By.tagName("a")).find{ it == "servlet" }
        assert 'servlet sample pageのリンクが見つかる' , link == null
        link.click()
        assert "メインタイトルが見つかる" , driver.findElement(By.tagName("h1")) == 'Sample Application Servlet Page'
    }

}
 