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
    void watcgNicoNamaTest() {
        sut.watcgNicoNama("http://live.nicovideo.jp/watch/lv185028521")
    }

    @Test
    void dummyTest() {
        println("このテストは「テストがゼロならエラーとなる」ということから、他をIgnoreにした時にも問題無いように置いてあるテストである。")
    }
}
