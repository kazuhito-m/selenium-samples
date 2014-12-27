import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

/**
 * 環境差異を吸収する役目のクラス。
 *
 * 現在は「独自のネットワークで動く設定へと整える」仕事のみをこなしている。
 *
 * @author kazuhito_m
 */
class EnvironmentDifferentAbsorber {

	/** HTTPのプロキシーサーバホスト名 */
	static final String HTTP_PROXY_HOST = "exsample.com"

	/** HTTPのプロキシーサーバホスト名 */
	static final Integer HTTP_PROXY_PORT = 8080

	static final String REMOTE_SELENIUM_SERVER_URL = "http://localhost:64444/wd/hub/"

	/**
	 * テスト開始時にHTTPプロキシ設定を環境変数に仕込む。
	 */
	public static void initSystemProperties() {
		// 環境依存の問題解決。開発機からなら、プロキシを設定する。
		if (isDeveloperNetwork()) {
			System.setProperty("https.proxyHost", HTTP_PROXY_HOST)
			System.setProperty("https.proxyPort", HTTP_PROXY_PORT.toString())
			System.setProperty("http.nonProxyHosts", "localhost")
		}
	}

	/**
	 * その環境にて挙動する「SeleniumのWebDriverオブジェクト」を返す。
	 *
	 * @return 環境用WebDriverオブジェクト。
	 * @throws MalformedURLException
	 */
	public static WebDriver createWebDriver() throws MalformedURLException {
		WebDriver driver
		// 環境依存の問題解決。開発機からなら、プロキシを設定する。
		if (isHttpConnectable(REMOTE_SELENIUM_SERVER_URL)) {
	        DesiredCapabilities capability = DesiredCapabilities.firefox()
	        // capability.setVersion("33.0")
            if (isDeveloperNetwork()) {
//	            capability.setCapability(FirefoxDriver.PROFILE, proxySettedProfile())
            }
	            capability.setCapability(FirefoxDriver.PROFILE , new FirefoxProfile())
	        try {
	        	driver = new RemoteWebDriver(new URL(REMOTE_SELENIUM_SERVER_URL), capability)
	        } catch (Exception e) {
	        	e.printStackTrace()
	        	driver = null
	        }
		} else 	if (isDeveloperNetwork()) {
			driver = new FirefoxDriver(proxySettedProfile())
		} else {
			driver = new FirefoxDriver()
		}
		driver
	}

	protected static FirefoxProfile proxySettedProfile() {
		FirefoxProfile profile = new FirefoxProfile()
		profile.setPreference("network.proxy.type", 1)
		profile.setPreference("network.proxy.http", HTTP_PROXY_HOST)
		profile.setPreference("network.proxy.http_port", HTTP_PROXY_PORT)
		profile.setPreference("network.proxy.ssl", HTTP_PROXY_HOST)
		profile.setPreference("network.proxy.ssl_port", HTTP_PROXY_PORT)
		profile
	}

	/**
	 * 「開発用のネットワークか否か」を判定する。
	 *
	 * @return true:開発のネットワーク。
	 */
	public static boolean isDeveloperNetwork() {
		try {
			return InetAddress.getLocalHost().getHostAddress()
					.matches("192\\.168\\.X\\..*")
		} catch (UnknownHostException e) {
			false
		}
	}

    /**
     * 指定されたHTTPアドrスに「接続できるか」を検査し、真偽値で返す。
     * @param httpAddress 検査するHTTPアドレス。
     * @return 結果。True:接続可能。
     */
	protected static boolean isHttpConnectable(String httpAddress) {
		try {
			boolean isConnectAndResponse = false
			URL url = new URL(httpAddress)
			URLConnection uc = url.openConnection()
			uc.setDoOutput(true)// POST可能にする

			uc.setRequestProperty("User-Agent", "@IT java-tips URLConnection")// ヘッダを設定
			uc.setRequestProperty("Accept-Language", "ja")// ヘッダを設定

			InputStream is = uc.getInputStream()// POSTした結果を取得
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is))
			String s
			while ((s = reader.readLine()) != null) {
				isConnectAndResponse = true
			}
			reader.close()
			return isConnectAndResponse
		} catch (Exception e) {
			return false
		}
	}

    /**
     * localhost,127.0.0.1 以外の「自身サーバのアドレス」を取得する。
     * @return localhost,127.0.0.1 以外の「自身サーバのアドレス」文字列。
     */
    static String getLocalAddress() {
        NetworkInterface.networkInterfaces.find{ !it.equals("localhost") && !it.equals("127.0.0.1")}
    }

}
