<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="appointments">

    <jsp:attribute name="customScript">
        <script>
            var data = {};
            var professionalSelect = $("#professional");
            var professionalGroup = $(".professionalGroup");
			var appointmentDateGroup = $("#appointment-date-group");

            function updateField(event) {
                data[event.target.name + "Id"] = event.target.value;
                if (data.centerId && data.specialtyId && event.target.name != "professional") {
					data.professionalId = undefined;
					appointmentDateGroup.hide();

                    $.ajax({
                        url: "/professionals/filter",
                        data: data,
                        success: function (data) {
                            if (data.length > 0) {
                                professionalSelect.empty();
                                var option = new Option("option text", "");
                                option.hidden = true;
								option.disabled = true;
								option.selected = true;
                                $(option).html("Choose professional");
                                professionalSelect.append(option);
                                for (var i in data) {
                                    option = new Option("option text", data[i].id);
                                    $(option).html(data[i].fullName);
                                    professionalSelect.append(option);
                                }
                                professionalGroup.show();
                                professionalSelect.show();
                            } else {
                                professionalGroup.hide();
                                professionalSelect.hide();
                                alert("No hay profesionales con esos parametros");
                            }
                        },
                    });
                }

				if (data.professionalId) {
					appointmentDateGroup.show();
					// Get date
				}
            }

            $("#center").on('change', updateField);
            $("#specialty").on('change', updateField);
			$("#professional").on('change', updateField);
            var today = new Date(); 
			$( "#datepicker" ).datepicker({
				minDate: today
			});

        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <h2 class="my-5">New appointment</h2>
            <form:form modelAttribute="appointment" method="post" action="/appointments/new">
                <div class="form-group has-feedback">
                    <h4 class="mt-5">Type of appointment</h4>
                    <p>Choose the center, specialty and the professional you want.</p>
                    <div class="form-group">
                        <label for="id-center">Center</label>
                        <form:select class="form-control" id="center" path="center">
                            <option disabled selected hidden>Choose a center</option>
                            <form:options items="${centers}" itemLabel="address" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label for="id-specialty">Specialty</label>
                        <form:select class="form-control" id="specialty" path="specialty">
                            <option disabled selected hidden>Choose a specialty</option>
                            <form:options items="${specialties}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group professionalGroup" style="display: none;">
                        <label for="id-professional">Professional</label>
                        <form:select class="form-control" id="professional" path="professional" style="display: none;">

                        </form:select>
                    </div>

                    <div id="appointment-date-group" style="display: none;">
                        <h4 class="mt-5">Choose date:</h4>
                        <p>Choose the date of your appointment.</p>
                        <div class="form-group">
                            <label for="">Date</label>
                            <input type="text" class="form-control" id="datepicker" placeholder="Choose date">
                        </div>
                        <div class="form-group">
                            <label for="">Time</label>
							<code>TODO: get available times by selected professional and selected date</code>
                            <select class="form-control">
								<option disabled selected>Choose time</option>
								<option disabled>09:00</option>
								<option>> 09:15</option>
								<option>> 09:30</option>
								<option disabled>09:45</option>
								<option disabled>10:00</option>
								<option>> 10:15</option>
								<option>> 10:30</option>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">+ Create appointment</button>
            </form:form>
        </div>
    </jsp:body>



</petclinic:layout>
