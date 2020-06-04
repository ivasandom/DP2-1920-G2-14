
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class AppointmentValidator implements org.springframework.validation.Validator {

	private static final String PROFESSIONAL = "professional";

	private static final String SPECIALTY = "specialty";

	private static final String CENTER = "center";
	
	private static final String STARTTIME = "startTime";


	//which objects can be validated by this validator
	@Override
	public boolean supports(final Class<?> paramClass) {
		return Appointment.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "date must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reason", "reason must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, STARTTIME, "start time must no be empty");
		ValidationUtils.rejectIfEmpty(errors, "client", "client must no be empty");
		ValidationUtils.rejectIfEmpty(errors, CENTER, "center must no be empty");
		ValidationUtils.rejectIfEmpty(errors, SPECIALTY, "specialty must no be empty");
		ValidationUtils.rejectIfEmpty(errors, PROFESSIONAL, "professional must no be empty");
		
		Appointment appointment = (Appointment) obj;

		if (!errors.hasFieldErrors(STARTTIME)) {
			if (appointment.getStartTime().getMinute() % 15 != 0) {
				// Appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times.
				errors.rejectValue(STARTTIME, "appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times");
			}

			if (appointment.getStartTime() == null || appointment.getStartTime().getHour() < 8 || appointment.getStartTime().getHour() > 19) {
				// Our clinics are open from 8 a.m to 8 p.m.
				errors.rejectValue(STARTTIME, "our clinics are open from 8 a.m to 8 p.m");
			}
		}

		if (!errors.hasFieldErrors("date")) {
			if (appointment.getDate() == null || appointment.getDate().isBefore(LocalDate.now())) {
				// Past dates are invalid
				errors.rejectValue("date", "the date must be in future");
			}
		}

		if (!errors.hasFieldErrors(PROFESSIONAL) && !errors.hasFieldErrors(CENTER)) {
			if (!appointment.getProfessional().getCenter().equals(appointment.getCenter())) {
				// Appointment center must be equal to professional center
				errors.rejectValue(CENTER, "appointment center must be equal to professional center");
			}
		}

		if (!errors.hasFieldErrors(PROFESSIONAL) && !errors.hasFieldErrors(SPECIALTY)) {
			if (!appointment.getProfessional().getSpecialty().getName().equals(appointment.getSpecialty().getName())) {
				// Appointment specialty must be equal to professional specialty
				errors.rejectValue(SPECIALTY, "appointment specialty must be equal to professional specialty");
			}
		}

	}

}
