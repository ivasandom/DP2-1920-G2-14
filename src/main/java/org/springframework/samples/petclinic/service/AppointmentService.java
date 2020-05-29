
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.model.PaymentIntent;

@Service
public class AppointmentService {

	@Autowired
	private DiagnosisService		diagnosisService;

	@Autowired
	private StripeService			stripeService;

	@Autowired
	private TransactionService		transactionService;

	@Autowired
	private AppointmentRepository	appointmentRepository;
	

	public int appointmentCount() {
		return (int) this.appointmentRepository.count();
	}
	
	@Autowired
	public AppointmentService(final AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	public Iterable<Appointment> listAppointments() throws DataAccessException {
		return this.appointmentRepository.findAll();
	}

	public Collection<LocalTime> findAppointmentStartTimesByProfessionalAndDate(final LocalDate date, final Professional professional) {
		return this.appointmentRepository.findAppointmentStartTimesByProfessionalAndDate(date, professional);
	}

	public Collection<Appointment> findAppointmentByUserId(final int id) {
		return this.appointmentRepository.findAppointmentByClientId(id);
	}

	public Collection<Appointment> findAppointmentByProfessionalId(final int id) {
		return this.appointmentRepository.findAppointmentByProfessionalId(id);
	}

	public Collection<Appointment> findTodayPendingByProfessionalId(final int id) {
		return this.appointmentRepository.findTodayPendingByProfessionalId(id);
	}

	public Collection<Appointment> findTodayCompletedByProfessionalId(final int id) {
		return this.appointmentRepository.findTodayCompletedByProfessionalId(id);
	}

	public Optional<Appointment> findAppointmentById(final int id) {
		return this.appointmentRepository.findById(id);
	}

	public Collection<Medicine> findMedicines(final int id) {
		return this.appointmentRepository.findMedicines(id);
	}

	public Collection<Desease> findDeseases(final int id) {
		return this.appointmentRepository.findDeseases(id);
	}

	@Transactional
	public void saveAppointment(final Appointment appointment) throws DataAccessException {
		this.appointmentRepository.save(appointment);

		if (appointment.getDiagnosis() != null) {
			this.diagnosisService.saveDiagnosis(appointment.getDiagnosis());
		}

	}

	@Transactional
	public void chargeAppointment(final Appointment appointment) throws Exception {
		if (appointment.getBill() != null && appointment.getBill().getPrice() != null && appointment.getBill().getPrice() > .0) {
			Client client = appointment.getClient();
			Collection<PaymentMethod> paymentMethods = client.getPaymentMethods();
			
			if (paymentMethods.size() > 0) {
				PaymentMethod primary = paymentMethods.iterator().next();
				PaymentIntent paymentIntent = this.stripeService.charge(primary.getToken(), appointment.getBill().getFinalPrice(), appointment.getClient().getStripeId());
				// Client charged successfully!
				Transaction transaction = new Transaction();
				transaction.setType(TransactionType.CHARGE);
				transaction.setBill(appointment.getBill());
				transaction.setToken(paymentIntent.getId());
				transaction.setAmount((double) paymentIntent.getAmount() / 100);
				transaction.setStatus(paymentIntent.getStatus());
				transaction.setSuccess(paymentIntent.getStatus().equals("succeeded"));
				transaction.setBill(appointment.getBill());
				this.transactionService.saveTransaction(transaction);
			}
		}
	}

	@Transactional
	public void delete(final Appointment appointment) throws Exception {

		if (appointment.getDate().isBefore(LocalDate.now())) {
			throw new Exception("You cannot delete a passed appointment");
		} else if (appointment.getStartTime().isBefore(LocalTime.now()) && appointment.getDate().isEqual(LocalDate.now())) {
			throw new Exception("You cannot delete an appointment that was today");
		}
		this.appointmentRepository.delete(appointment);
	}
	
	public Long getNumberOfPendingAppointments() throws DataAccessException {
		return this.appointmentRepository.getNumberOfPendingAppointmentsByStatus();		
	}
	
	public Long getNumberOfAbsentAppointments() throws DataAccessException {
		return this.appointmentRepository.getNumberOfAbsentAppointmentsByStatus();		
	}
	
	public Long getNumberOfCompletedAppointments() throws DataAccessException {
		return this.appointmentRepository.getNumberOfCompletedAppointmentsByStatus();		
	}
	
	
}
