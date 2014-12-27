import geb.Browser
import geb.navigator.NonEmptyNavigator
import org.junit.*
import org.junit.rules.TestName
import org.openqa.selenium.Keys

/**
 * gebで書いたブラウザテストのサンプル。
 * SeleniumSampleTest,ScreenShotOutputFileと同内容をgebにて書きなおしたもの。
 */
class GebSampleTest {

    @Rule
    public TestName names = new TestName()

    @BeforeClass
    static void setUp() {
        // レポートディレクトリのクリア
        Browser.drive { cleanReportGroupDir() }
    }

    @Test
    public void basicTest() {

        Browser.drive {

            // レポートグループはクラス＋メソッド名に。
            reportGroup this.class.name + "." + names.methodName

            go 'http://www.htmlhifive.com/conts/web/view/Main/WebHome'

            // 1ページ目、”⇛こちら”ってリンクを探しクリック。
            assert title == "hifive - HTML5企業Webシステムのための開発プラットフォーム - hifive"
            def link = $('a').allElements().find() { it.text == "⇒こちら" }
            link.click()

            // 2ページ目、在る要素のテキストを確認
            assert title == 'チュートリアル - hifive'
            // action & assertion
            assert $("#document-title").text() == "チュートリアル"

            report "basicTest01"
            // println "ドライバクラス名 : " +  config.driver.class.canonicalName

        }

    }

    @Test
    void yahooTest() {

        Browser.drive {
            // レポートグループはクラス＋メソッド名に。
            reportGroup this.class.name + "." + names.methodName

            // ページ移動
            go "http://yahoo.co.jp/"
            report "Y1"     // evideince記録

            // Click 'economy' link
            $("#economy").click()
            report "Y2"     // evideince記録

            def queryInput = $("#srchtxt")
            assert queryInput != null
            queryInput << "SKYSEA Client View" + Keys.ENTER
            // evideince記録
            report "Y3"

            // <a>アンカーを検索すると、長くかかるため、<div>タグで絞ってからその下を検索。
            // seleniumのfindElement(By.linkText())相当のものがあれば早いのだが…。
            def link = $("#WS2m").find("a").allElements().find { it.text == "Sky株式会社の SKYSEA Client View" }
            assert link != null, "No Link for SKYSEA"
            link.click()

            // Now we see SKYSEA Client View's site
            report "Y4" // evideince記録
        }

    }

    @Test
    void googleTest() {

        Browser.drive {
            // レポートグループはクラス＋メソッド名に。
            reportGroup this.class.name + "." + names.methodName
            // ページ移動
            go "http://www.google.co.jp/"
            report "G1"     // evideince記録
            assert title == "Google"
        }

    }

    @Test
    void seleniumTest() {

        Browser.drive {
            // レポートグループはクラス＋メソッド名に。
            reportGroup this.class.name + "." + names.methodName
            // ページ移動
            go "http://docs.seleniumhq.org"
            report "S1"     // evideince記録
            assert title == "Selenium - Web Browser Automation"
            // メニューリンク抽出
            def menu = $("#menu_projects")
            assert menu as NonEmptyNavigator, "No Link for SKYSEA"
            // ページ移動
            menu.click()
            report "S2"
        }

    }
}
 