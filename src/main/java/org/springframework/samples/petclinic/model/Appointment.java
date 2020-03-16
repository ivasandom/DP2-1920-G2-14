/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity {

	/**
	 * Holds value of property date.
	 */
	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate		Date;

	@Column(name = "start_time")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime		startTime;

	@Column(name = "end_time")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime		endTime;

	/**
	 * Holds value of property reason.
	 */
	@NotEmpty
	@Column(name = "reason")
	private String			reason;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private AppointmentType	type;
	
	@ManyToOne
	@JoinColumn(name = "specialty")
	private Specialty specialty;

	//Relations


	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client			client;

	@ManyToOne
	@JoinColumn(name = "professional_id")
	private Professional	professional;

	@ManyToOne
	@JoinColumn(name = "center_id")
	private Center			center;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "diagnosis_id")
	private Diagnosis		diagnosis;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receipt_id")
	private Receipt			receipt;

}
