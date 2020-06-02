package org.springframework.samples.petclinic.projections;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ListAppointmentsClient {

	LocalDate getDate();
	LocalTime getStartTime();
	Integer getId();
	
}
