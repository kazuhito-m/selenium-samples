import geb.report.ScreenshotAndPageSourceReporter
import org.openqa.selenium.firefox.FirefoxDriver

// デフォルトのドライバが”org.openqa.selenium.htmlunit.HtmlUnitDriver”だが、
// スクリーンショットを取ることが出来ないため、明示的にブラウザ用個別ドライバを設定する。
driver = { new FirefoxDriver() }
reportsDir = "build/test-evidence/geb"
