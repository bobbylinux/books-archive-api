package online.bobbylinux.bobbybook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DemoUtilsTest {
	
	@Test
	void testEqualsAndNotEquals() {
		//setup
		DemoUtils demoUtils = new DemoUtils();
		
		int expected = 6;

		//execute
		int actual = demoUtils.add(2, 4);
		
		//assert
		Assertions.assertEquals(expected, actual, "2 + 4 must be 6");
		
	}
	
}
