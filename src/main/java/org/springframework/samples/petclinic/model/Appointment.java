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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {
	
	@Column(name = "date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate		date;

	@Column(name = "start_time")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime		startTime;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private AppointmentType	type;
	
	
	// Relations
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client			client;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "professional_id")
	private Professional	professional;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "specialty_id")
	private Specialty specialty;
	
	@NotNull
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
