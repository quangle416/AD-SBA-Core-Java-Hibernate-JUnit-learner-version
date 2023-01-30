package sba.sms.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import sba.sms.models.Course;
import sba.sms.utils.CommandLine;
import static org.assertj.core.api.Assertions.*;

// used ctrl 1 shortcut to create test
class CourseServiceTest {

	static Course c = null;
	static CourseService cSer = null;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		CommandLine.addData();
		
		c = new Course("JPA", "Jafer Alhaboubi");
	
	}
	
	@Test
	void testCreateCourse() {
		assertThat(c.getName().isBlank());
	}

	@Test
	void testGetCourseById() {
//		Junit test syntax
//		c = new Course();
//		c.setId(1);
//		c.setName("JPA");
//		c.setInstructor("Jafer Alhaboubi");
//		
//		assertEquals(c.getId(),cSer.getCourseById(1));
//		assertJ text syntax
		assertThat(c.getId()).isEqualTo(cSer.getCourseById(3).getName());
		
	}

//	@Test
//	void testGetAllCourses() {
//		fail("Not yet implemented");
//	}

}
