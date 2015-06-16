package org.ff4j.hello.aop;

import org.ff4j.FF4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*applicationContext-test-aop.xml")
public class FeatureFlippingThoughAopTest {
	
	@Autowired
	private FF4j ff4j;
	
	@Autowired
	@Qualifier("greeting.english")
	private GreetingService greeting;
	
	@Test
	public void testAOP() {
		System.out.println("Testing AOP...");
		Assert.assertTrue(greeting.sayHello("CLU").startsWith("Hello"));
		Assert.assertTrue(!ff4j.check("language-french"));
		ff4j.enable("language-french");
		Assert.assertTrue(ff4j.check("language-french"));
		//ff4j.enable("greeting.french");
		System.out.println(greeting.sayHello("CLU"));
		//Assert.assertTrue(greeting.sayHello("CLU").startsWith("Bonjour"));
	}
}