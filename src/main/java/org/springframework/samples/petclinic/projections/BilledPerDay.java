package org.springframework.samples.petclinic.projections;

import java.util.Date;

public interface BilledPerDay {

	Double getAmount();
	Date getDate();
	
}
