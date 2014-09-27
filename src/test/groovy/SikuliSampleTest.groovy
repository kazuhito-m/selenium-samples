import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test

class SikuliSampleTest {

    static SikuliSample sut

    @BeforeClass
    static void setUp() {
        sut = new SikuliSample()
    }

    @Ignore
	@Test
	void execSikuliTest() {
        sut.execSikuli()
	}

    @Ignore
    @Test
    void watchEvo2014OnNiconicoTest() {
        sut.watcgNicoNama("http://live.nicovideo.jp/watch/lv185030752")
    }
}
