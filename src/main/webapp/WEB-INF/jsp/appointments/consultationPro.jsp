<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout currentPage="appointments">
	<div class="container">
    <h2 class="my-5">Consultation </h2>
    

    <table class="table table-striped">
	        <tr>
	            <th>Name</th>
	            <td><b><c:out value="${appointment.client.firstName} ${appointment.client.lastName}"/></b></td>
	        </tr>
	        <tr>
	            <th>Date</th>
	            <td><c:out value="${appointment.date}"/></td>
	        </tr>
	        <tr>
	            <th>Start Time</th>
	            <td><c:out value="${appointment.startTime}"/></td>
	        </tr>
	        <tr>
	            <th>Type</th>
	            <td><c:out value="${appointment.type.name}"/></td>
	        </tr>
	        <tr>
	            <th>Reason</th>
	            <td><c:out value="${appointment.reason}"/></td>
	        </tr>
	        <tr>
	        	<th>Type</th>
                <td><c:out value="${appointment.type}"/></td>
            </tr>
            <tr>
	        	<td><petclinic:selectField name="medicines" label="Medicine " names="${medicines}" size="${fn:length(medicines)}"/></td>
            </tr>
	    </table>
    </div>
    <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-primary container-fluid" type="submit">Update</button>
             </div>
              </div>
</petclinic:layout>
