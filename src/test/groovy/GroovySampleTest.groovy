import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*
import org.junit.Test

class GroovySampleTest {

	@Test
	void test() {
		GroovySample sample = new GroovySample()
		assert sample.message == "GroovySample!"
	}
}
