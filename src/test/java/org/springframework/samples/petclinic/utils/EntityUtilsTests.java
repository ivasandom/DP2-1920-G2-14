package org.springframework.samples.petclinic.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.util.EntityUtils;

public class EntityUtilsTests {

	private CenterService centerService;

	private Professional professional;

	private Center center;

	@BeforeEach
	void setup() throws Exception {
		Center c = new Center();
		c.setId(1);

		this.professional = new Professional();
		this.professional.setCenter(c);

		this.center = new Center();
		center.setId(1);
		center.setAddress("Sevilla");

		this.centerService = Mockito.mock(CenterService.class);
		Mockito.when(this.centerService.findCenterById(1)).thenReturn(Optional.of(this.center));
		Mockito.when(this.centerService.findCenterById(2)).thenReturn(Optional.empty());

	}

	@Test
	void testSetRelationshipAttributeSuccess() throws Exception {
		EntityUtils.setRelationshipAttribute(this.professional, Center.class, this.centerService, "findCenterById");

		Assertions.assertEquals(this.center, this.professional.getCenter());
	}

	@Test
	void testSetRelationshipAttributeNotFound() throws Exception {
		this.professional.getCenter().setId(2);

		Assertions.assertThrows(InvocationTargetException.class, () -> EntityUtils
				.setRelationshipAttribute(this.professional, Center.class, this.centerService, "findCenterById"));
	}
	
	@Test
	void testSetRelationshipAttributeIdNull() throws Exception {
		this.professional.getCenter().setId(null);
		EntityUtils.setRelationshipAttribute(this.professional, Center.class, this.centerService, "findCenterById");
		
		Assertions.assertEquals(null, this.professional.getCenter());
	}
	
	@Test
	void testSetRelationshipAttributeNull() throws Exception {
		this.professional.setCenter(null);;
		EntityUtils.setRelationshipAttribute(this.professional, Center.class, this.centerService, "findCenterById");
		
		Assertions.assertEquals(null, this.professional.getCenter());
	}

}