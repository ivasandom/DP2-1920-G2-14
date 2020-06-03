
package org.springframework.samples.petclinic.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Bill;
import org.springframework.samples.petclinic.projections.BilledPerDay;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BillServiceTests {

	@Autowired
	protected BillService billService;


	@Test
	@Transactional
	public void testCountWithInitialData() {
		Collection<Bill> bills = (Collection<Bill>) this.billService.findAll();
		Assertions.assertEquals(bills.size(), 3);
	}

	@Test
	@Transactional
	public void shouldFindBillById() {
		Optional<Bill> bill = this.billService.findById(1);
		
		Assertions.assertTrue(bill.isPresent());
		Assertions.assertEquals(1800.0, bill.get().getPrice());
	}
	
	@Test
	@Transactional
	public void testGetTotalBilled() {
		Iterator<Bill> bills = this.billService.findAll().iterator();
		Double totalBilled = this.billService.getTotalBilled();
		Double mustBeTotalBilled = 0.0;
		
		while(bills.hasNext()) {
			mustBeTotalBilled += bills.next().getFinalPrice();
		}
		
		Assertions.assertEquals(mustBeTotalBilled, totalBilled);
	}
	
	@Test
	@Transactional
	void testBilledPerDay() {
		List<BilledPerDay> billedPerDay = this.billService.getBilledPerDay().stream().collect(Collectors.toList());

		Assertions.assertEquals(3, billedPerDay.size());
		Assertions.assertEquals(2178.0, billedPerDay.get(0).getAmount());
		Assertions.assertEquals(18150.0, billedPerDay.get(1).getAmount());
		Assertions.assertEquals(84.7, billedPerDay.get(2).getAmount());
		
	}

	@Test
	@Transactional
	public void shouldSaveBill() throws DataAccessException {
		Collection<Bill> bills = (Collection<Bill>) this.billService.findAll();
		int found = bills.size();

		Bill bill = new Bill();
		bill.setCreatedAt(LocalDateTime.now());
		bill.setAppointment(null);
		bill.setPrice(50.0);

		this.billService.saveBill(bill);
		org.assertj.core.api.Assertions.assertThat(bill.getId().longValue()).isNotEqualTo(0);

		bills = (Collection<Bill>) this.billService.findAll();
		org.assertj.core.api.Assertions.assertThat(bills.size()).isEqualTo(found + 1);
	}
	
}
