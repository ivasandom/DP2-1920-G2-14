
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "date must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startTime", "start time must no be empty");
		ValidationUtils.rejectIfEmpty(errors, "center", "center must no be empty");
		ValidationUtils.rejectIfEmpty(errors, "specialty", "specialty must no be empty");
		ValidationUtils.rejectIfEmpty(errors, "professional", "professional must no be empty");

		Appointment appointment = (Appointment) obj;
		
		if (!errors.hasFieldErrors("startTime")) {
			if (appointment.getStartTime().getMinute() % 15 != 0) {
				// Appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times.
				errors.rejectValue("startTime", "appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times");
			}
		
			if (appointment.getStartTime() == null || appointment.getStartTime().getHour() < 8 || appointment.getStartTime().getHour() > 19) {
				// Our clinics are open from 8 a.m to 8 p.m.
				errors.rejectValue("startTime", "our clinics are open from 8 a.m to 8 p.m");
			}
		}
		
		
		if (!errors.hasFieldErrors("date")) {
			if (appointment.getDate() == null || appointment.getDate().isBefore(LocalDate.now())) {
				// Past dates are invalid
				errors.rejectValue("date", "the date must be in future");
			}
		}
		
		if (!errors.hasFieldErrors("professional") && !errors.hasFieldErrors("center")) {
			if (appointment.getProfessional().getCenter() != appointment.getCenter()) {
				// Appointment center must be equal to professional center
				errors.rejectValue("center", "appointment center must be equal to professional center");
			}
		}
		
		if (!errors.hasFieldErrors("professional") && !errors.hasFieldErrors("specialty")) {
			if (appointment.getProfessional().getSpecialty() != appointment.getSpecialty()) {
				// Appointment specialty must be equal to professional specialty
				errors.rejectValue("center", "appointment specialty must be equal to professional specialty");
			}
		}
			
//		if (appointmentService.existsAppointment(center, specialty, professional, date, startTime)) {
//			// It cannot exist 2 appointment with the same values
//			errors.rejectValue("date", "Appointment already taken.");
//		}
		
		
	}

}
