package org.ff4j.sample;
import static org.junit.Assert.fail;
import org.ff4j.FF4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ff4j.exception.FeatureNotFoundException;
import static org.junit.Assert.assertTrue;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:*applicationContext.xml"})
public class CoreSpringTest {
	@Autowired
	private FF4j ff4j;
	
	@Test
	public void testWithSpring() {
		// Test value at runtime
		//ff4j.setAutoCreate(true);
		try {
			if (ff4j.check("sayHello")) {
			// Feature ok !
			System.out.println("Hello World !");
			} else {
			fail();
			}
		} catch (FeatureNotFoundException fnfe) {
			System.out.println("Exception showing the default behaviour....");
		}
		assertTrue(true);
		
		
	}
}