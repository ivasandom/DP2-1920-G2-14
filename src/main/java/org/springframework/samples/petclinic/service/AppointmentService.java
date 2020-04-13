
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Client;
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
	private DiagnosisService			diagnosisService;
	
	@Autowired
	private StripeService				stripeService;
	
	@Autowired
	private TransactionService			transactionService;

	@Autowired
	private AppointmentRepository appointmentRepository;


	@Autowired
	public AppointmentService(final AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Appointment> listAppointments() throws DataAccessException {
		return this.appointmentRepository.findAll();
	}

	@Transactional
	public Collection<LocalTime> findAppointmentStartTimesByProfessionalAndDate(final LocalDate date, final Professional professional) {
		return this.appointmentRepository.findAppointmentStartTimesByProfessionalAndDate(date, professional);
	}

	@Transactional
	public Collection<Appointment> findAppointmentByUserId(final int id) {
		return this.appointmentRepository.findAppointmentByClientId(id);
	}

	@Transactional
	public Collection<Appointment> findAppointmentByProfessionalId(final int id) {
		return this.appointmentRepository.findAppointmentByProfessionalId(id);
	}

	@Transactional
	public Collection<String> findAppointmentByTypes() throws DataAccessException {
		return this.appointmentRepository.findAppointmentTypes();
	}

	@Transactional
	public Collection<Appointment> findTodayPendingByProfessionalId(final int id) {
		return this.appointmentRepository.findTodayPendingByProfessionalId(id);
	}
	
	@Transactional
	public Collection<Appointment> findTodayCompletedByProfessionalId(final int id) {
		return this.appointmentRepository.findTodayCompletedByProfessionalId(id);
	}
	
	@Transactional
	public Appointment findAppointmentById(final int id) {
		return this.appointmentRepository.findById(id).get();
	}

	
	@Transactional
	public void saveAppointment(final Appointment appointment) throws DataAccessException {
		appointmentRepository.save(appointment);
		
		if (appointment.getDiagnosis() != null) {
			this.diagnosisService.saveDiagnosis(appointment.getDiagnosis());
		}
		
	}
	
	@Transactional
	public void chargeAppointment(Appointment appointment) throws Exception {
		if (appointment.getReceipt() != null && appointment.getReceipt().getPrice() != null && appointment.getReceipt().getPrice() > .0) {
			Client client = appointment.getClient();
			Collection<PaymentMethod> paymentMethods = client.getPaymentMethods();
			System.out.println("step1");
			if (paymentMethods.size() > 0) {
				System.out.println("step2");
				PaymentMethod primary = paymentMethods.iterator().next();
				PaymentIntent paymentIntent = stripeService.charge(primary.getToken(), appointment.getReceipt().getPrice(), appointment.getClient().getStripeId());
				// Client charged successfully!
				Transaction transaction = new Transaction();
				transaction.setType(TransactionType.CHARGE);
				transaction.setReceipt(appointment.getReceipt());
				transaction.setToken(paymentIntent.getId());
				transaction.setAmount((double) paymentIntent.getAmount() / 100);
				transaction.setStatus(paymentIntent.getStatus());
				transaction.setSuccess(paymentIntent.getStatus().equals("succeeded"));
				transactionService.saveTransaction(transaction);
			}
		}
	}

}
