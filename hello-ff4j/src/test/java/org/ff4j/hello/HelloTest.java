package org.ff4j.hello;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
 
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.exception.FeatureNotFoundException;
import org.junit.Test;
import java.util.Map;
 
public class HelloTest {

	@Test
	public void createFeatureDynamically() {
		// Initialize with empty store
		FF4j ff4j = new FF4j();
		// Dynamically register new features
		ff4j.create("f1").enable("f1");
		// Testing
		assertTrue(ff4j.exist("f1"));
		assertTrue(ff4j.check("f1"));
		
		ff4j.create("Bascule Centrales").enable("Bascule Centrales");
		assertTrue(ff4j.exist("Bascule Centrales"));
		assertTrue(ff4j.check("Bascule Centrales"));
	}
    
    @Test
    public void my_first_test() {
        // Given: file exist
        assertNotNull(getClass().getClassLoader().getResourceAsStream("ff4j.xml"));
        // When: init
        FF4j ff4j = new FF4j("ff4j.xml");
        // Then
        assertEquals(5, ff4j.getFeatures().size());
        assertTrue(ff4j.exist("hello"));
        assertTrue(ff4j.check("hello"));
        
        // Usage
        if (ff4j.check("hello")) {
            // hello is toggle on
            System.out.println("Hello World !!");
        }
        
        // When: Toggle Off
        ff4j.disable("hello");
        // Then: expected to be off
        assertFalse(ff4j.check("hello"));
    }
    
    @Test
    public void how_does_AutoCreate_Works() {
        // Given: feature not exist
        FF4j ff4j = new FF4j("ff4j.xml");
        assertFalse(ff4j.exist("does_not_exist"));
        
        // When: use it
        try {
           if (ff4j.check("does_not_exist")) fail();
        } catch (FeatureNotFoundException fnfe) {
            // Then: exception
            System.out.println("By default, feature not found throw exception");
        }
        
        // When: using autocreate
        ff4j.setAutocreate(true);
        // Then: no more exceptions
        if (!ff4j.check("does_not_exist")) {
            System.out.println("Auto created and set as false");
        }
    }
    
    @Test
    public void how_groups_works() {
        // Given: 2 features 'off' within existing group
        FF4j ff4j = new FF4j("ff4j.xml");
        assertTrue(ff4j.exist("userStory3_1"));
        assertTrue(ff4j.exist("userStory3_2"));
        assertTrue(ff4j.getStore().readAllGroups().contains("sprint_3"));
        assertEquals("sprint_3", ff4j.getFeature("userStory3_1").getGroup());
        assertEquals("sprint_3", ff4j.getFeature("userStory3_2").getGroup());
        assertFalse(ff4j.check("userStory3_1"));
        assertFalse(ff4j.check("userStory3_2"));
        
        // When: toggle group on
        ff4j.getStore().enableGroup("sprint_3");
        
        // Then: all features on
        assertTrue(ff4j.check("userStory3_1"));
        assertTrue(ff4j.check("userStory3_2"));
    }
	
	@Test
	public void workWithGroupTest() {
		// Given
		FF4j ff4j = new FF4j("ff4j-groups.xml");
		assertTrue(ff4j.exist("featA1"));
		// When
		ff4j.getStore().addToGroup("featA1", "new-group");
		// Then
		assertTrue(ff4j.getStore().existGroup("new-group"));
		assertTrue(ff4j.getStore().readAllGroups().contains("new-group"));
		Map<String, Feature> myGroup = ff4j.getStore().readGroup("new-group");
		assertTrue(myGroup.containsKey("featA1"));
		Map<String, Feature> myGroup2 = ff4j.getStore().readGroup("release-2.3");
		assertNotNull(myGroup2);
		assertTrue(myGroup2.containsKey("users-story11"));
		// A feature can be in a single group
		// Here changing => deleting the last element of a group => deleting the group
		ff4j.getStore().addToGroup("featA1", "group2");
		assertFalse(ff4j.getStore().existGroup("new-group"));
	}
		
	
	
}