<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="appointments">

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
		        </div>
		        <button type="submit" class="btn btn-primary">Create appointment</button>
		    </form:form>
	    
    </div>

</petclinic:layout>
