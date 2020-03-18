
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class AppointmentValidator implements org.springframework.validation.Validator {

	//which objects can be validated by this validator
	@Override
	public boolean supports(final Class<?> paramClass) {
		return Appointment.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startTime", "required");
		ValidationUtils.rejectIfEmpty(errors, "center", "required");
		ValidationUtils.rejectIfEmpty(errors, "specialty", "required");
		ValidationUtils.rejectIfEmpty(errors, "professional", "required");

		Appointment appointment = (Appointment) obj;
		
		if (appointment.getStartTime().getMinute() % 15 != 0) {
			// Appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times.
			errors.rejectValue("startTime", "startTimeInvalid");
		}
		
		if (appointment.getStartTime().getHour() < 8 || appointment.getStartTime().getHour() > 20) {
			// Our clinics are open from 8 a.m to 8 p.m.
			errors.rejectValue("startTime", "startTimeOfOutSchedule");
		}
		
		if (appointment.getDate().isBefore(LocalDate.now())) {
			// Past dates are invalid
			errors.rejectValue("date", "pastDate");
		}
		
		if (appointment.getProfessional().getCenter() != appointment.getCenter()) {
			// Appointment center must be equal to professional center
			errors.rejectValue("center", "centerInvalid");
		}
		
		if (appointment.getProfessional().getSpecialty() != appointment.getSpecialty()) {
			// Appointment specialty must be equal to professional specialty
			errors.rejectValue("center", "specialty");
		}
			
		
//		if (appointmentService.existsAppointment(center, specialty, professional, date, startTime)) {
//			// It cannot exist 2 appointment with the same values
//			errors.rejectValue("date", "Appointment already taken.");
//		}
		
		
	}

}
