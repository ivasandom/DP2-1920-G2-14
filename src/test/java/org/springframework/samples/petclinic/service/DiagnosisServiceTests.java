package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Diagnosis;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DiagnosisServiceTests {

	@Autowired
	protected DiagnosisService diagnosisService;

	@ParameterizedTest
	@CsvSource({"2020-02-03, diagnosis description"})
	@Transactional
	public void shouldSaveDiagnosis(final LocalDate date, final String description) {
		Collection<Diagnosis> diagnosisCollection = (Collection<Diagnosis>) this.diagnosisService.findAll();
		int found = diagnosisCollection.size();

		Diagnosis diagnosis = new Diagnosis();
		diagnosis.setDate(date);
		diagnosis.setDescription(description);

		Set<Medicine> medicines = Collections.emptySet();
		diagnosis.setMedicines(medicines);

		Set<Desease> deseases = Collections.emptySet();
		diagnosis.setDeseases(deseases);

		this.diagnosisService.saveDiagnosis(diagnosis);

		Assertions.assertThat(diagnosis.getId().longValue()).isNotEqualTo(0);

		diagnosisCollection = (Collection<Diagnosis>) this.diagnosisService.findAll();
		Assertions.assertThat(diagnosisCollection.size()).isEqualTo(found + 1);
	}
}
