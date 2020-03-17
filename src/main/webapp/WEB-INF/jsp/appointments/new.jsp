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
	    var startDate;
	    var endDate;
	
	    var fechasOcupadas = [
	       
	    ];
	
	    $("#dias-siguientes").on("click", function(){
	        generaTablaHorario(endDate);
	    });
	
	    $("#dias-anteriores").on("click", function(){
	        window.endDate.setDate(endDate.getDate()-14);
	        generaTablaHorario(window.endDate);
	    });
	
	    function dateToStringFormat(dt){
	        var dd = dt.getDate();
	        var mm = dt.getMonth() + 1;
	        var yy = dt.getFullYear();
	
	        var hh = dt.getHours();
	
	        dd = (dd < 10) ? "0" + dd : dd;
	        mm = (mm < 10) ? "0" + mm : mm;
	        hh = (hh < 10) ? "0" + hh : hh;
	
	        return dd + "-" + mm + "-" + yy + "T" + hh + ":00:00.000Z";
	
	    }
	
	    $("body").on("click", ".hora-cita", function () {
	        $(".hora-cita").each(function () {
	            var trd = $(this);
	
	            if (trd.hasClass("selected")) {
	                trd.html(trd.data("hora"));
	                trd.removeClass("selected");
	            }
	        });
	
	        $(this).html("Seleccionado");
	        $(this).addClass("selected");
	
	        // https://stackoverflow.com/questions/476105/how-can-i-convert-string-to-datetime-with-format-specification-in-javascript
	        var dateString = $(this).data("datetime");
	        var reggie = /(\d{2})\/(\d{2})\/(\d{2}) (\d{2}):(\d{2}):(\d{2}),(\d{6})/;
	        var dateArray = reggie.exec(dateString); 
	
	        var dateObject = new Date(
	            (+dateArray[3]+2000),
	            (+dateArray[2])-1, // Careful, month starts at 0!
	            (+dateArray[1]),
	            (+dateArray[4]),
	            (+dateArray[5]),
	            (+dateArray[6]),
	            (+dateArray[7])
	        );
	        
	        $("#hora-inicio").val(dateToStringFormat(dateObject));
	        dateObject.setHours(dateObject.getHours()+1);
	        $("#hora-fin").val(dateToStringFormat(dateObject));
	
	    })
	
	    function getDaysOfWeek(from, dayOfWeek) {
	        // Devuelve los próximos 5 días quitando sábados y domingos
	        var days = [];
	
	        i = 0;
	        while (days.length < 5) {
	            
	            from.setDate(from.getDate() + 1);
	            if (from.getDay() != 0 && from.getDay() != 6){
	                days.push(new Date(from));
	            }
	            i = i + 1;
	        }
	
	        return days;
	
	    }
	
	    function dateToString(dt){
	        var dd = dt.getDate();
	        var mm = dt.getMonth() + 1;
	        var yy = dt.getFullYear().toString().substr(-2);
	
	        dd = (dd < 10) ? "0" + dd : dd;
	        mm = (mm < 10) ? "0" + mm : mm;
	
	        return dd + "/" + mm + "/" + yy;
	    }
	
	    function getDayWithName(date) {
	        days = {
	            0: "Domingo",
	            1: "Lunes",
	            2: "Martes",
	            3: "Miércoles",
	            4: "Jueves",
	            5: "Viernes",
	            6: "Sábado"
	        }
	
	        return days[date.getDay()] + ", " + date.toLocaleDateString("es-ES")
	    }
	
	    function generaTablaHorario(dt) {
	        var dayOfWeek = dt.getDay();
	
	        var daysOfWeek = getDaysOfWeek(dt, dayOfWeek);
	        window.startDate = daysOfWeek[0];
	        window.endDate = daysOfWeek[4];
	
	        $("#row-dates").empty();
	        $("#row-horas").empty();
	
	        for (var i in daysOfWeek) {
	            $("#row-dates").append("<th style='width:20%;'>" + getDayWithName(daysOfWeek[i]) + "</th>");
	        }
	
	        var hora = 9;
	        while (hora < 20) {
	
	            if (hora != 15 && hora != 16){
	
	                var horaAux = (hora < 10) ? "0" + hora : hora;
	
	                $("#row-horas").append("<tr>" +
	                    "<td class='hora-cita' data-datetime='" + dateToString(daysOfWeek[0]) + " " + horaAux + ":00:00,000000" + "' data-hora='" + horaAux + ":00'>" + horaAux + ":00</td>" +
	                    "<td class='hora-cita' data-datetime='" + dateToString(daysOfWeek[1]) + " " + horaAux + ":00:00,000000" + "' data-hora='" + horaAux + ":00'>" + horaAux + ":00</td>" +
	                    "<td class='hora-cita' data-datetime='" + dateToString(daysOfWeek[2]) + " " + horaAux + ":00:00,000000" + "' data-hora='" + horaAux + ":00'>" + horaAux + ":00</td>" +
	                    "<td class='hora-cita' data-datetime='" + dateToString(daysOfWeek[3]) + " " + horaAux + ":00:00,000000" + "' data-hora='" + horaAux + ":00'>" + horaAux + ":00</td>" +
	                    "<td class='hora-cita' data-datetime='" + dateToString(daysOfWeek[4]) + " " + horaAux + ":00:00,000000" + "' data-hora='" + horaAux + ":00'>" + horaAux + ":00</td>" +
	                    "</tr>");
	            } else {
	                $("#row-horas").append("<tr>" +
	                    "<td style='background-color: white;height:49px;cursor:not-allowed;'><i>Cerrado</i></td>" +
	                    "<td style='background-color: white;height:49px;cursor:not-allowed;'><i>Cerrado</i></td>" +
	                    "<td style='background-color: white;height:49px;cursor:not-allowed;'><i>Cerrado</i></td>" +
	                    "<td style='background-color: white;height:49px;cursor:not-allowed;'><i>Cerrado</i></td>" +
	                    "<td style='background-color: white;height:49px;cursor:not-allowed;'><i>Cerrado</i></td>" +
	                    "</tr>");
	            }
	            hora = hora + 1;
	        }
	
	        $(".hora-cita").each(function(){
	
	            if (fechasOcupadas.indexOf($(this).data("datetime")) > -1){
	                $(this).addClass("reservado");
	                $(this).removeClass("hora-cita");
	                $(this).html("Ocupado");
	            }
	        })
	
	        return daysOfWeek;
	    }
	
	    var startDate = new Date();
	    startDate.setDate(startDate.getDate()-1);
	
	    generaTablaHorario(startDate);
	
	    </script>
    </jsp:attribute>
    
    	<jsp:body>
			<div class="container">
			    <h2 class="my-5">New appointment</h2>
			        <form:form modelAttribute="appointment" method="post" action="/appointments/new">
				        <div class="form-group has-feedback">	           
				           <div class="form-group">
						        <label for="id-center">Center</label>
						        <form:select class="form-control" id="center" path="${center}">
						        	<form:options items="${centers}" itemLabel="address" itemValue="id" />
						        </form:select> 
						   </div>
						   <div class="form-group">
						        <label for="id-specialty">Specialty</label>
						        <form:select class="form-control" id="specialty" path="${specialty}">
						        	<form:options items="${specialties}" itemLabel="name" itemValue="id" />
						        </form:select> 
						   </div>
						   <div class="form-group">
						        <label for="id-professional">Professionals</label>
						        <form:select class="form-control" id="professional" path="${professional}">
						        	<form:options items="${professionals}" itemLabel="fullName" itemValue="id" />
						        </form:select> 
						   </div>
						   
						   <h3>Selecciona fecha</h3>
				            <p>Selecciona el día y la hora. <code>*Solo html y js implementado</code></p>
				
				            
				            <div class="btn-group mb-3">
				                <button type="button" class="btn btn-outline-secondary" id="dias-anteriores">Anterior</button>
				                <button type="button" class="btn btn-outline-secondary" id="dias-siguientes">Siguiente</button>
				            </div>
				
				            <table class="table tabla-horarios">
				                <thead>
				                    <tr id="row-dates">
				                    </tr>
				                </thead>
				                <tbody>
				                <tbody id="row-horas">
				
				                </tbody>
				                </tbody>
				            </table>
				
				            <input type="hidden" name="hora_inicio" id="hora-inicio">
				            <input type="hidden" name="hora_fin" id="hora-fin">
				        </div>
				        <button type="submit" class="btn btn-primary">Create appointment</button>
				    </form:form>
		    </div>
    </jsp:body>
    

</petclinic:layout>
