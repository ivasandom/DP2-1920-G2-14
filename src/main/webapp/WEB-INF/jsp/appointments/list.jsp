<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="appointments">
	<div class="container">
    <h2 class="my-5">My appointments <a class="btn btn-sm btn-primary" href="/appointments/new">+ New appointment</a></h2>
    

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th>Date</th>
            <th>Time</th>
            <th>Center</th>
            <th>Professional</th>
            <th>Specialty</th>
            <th>Type</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="appointment">
            <tr>
               <td>${appointment.date}</td>
               <td>${appointment.startTime}</td>
               <td>${appointment.center.address}</td>
               <td>${appointment.professional.fullName}</td>
               <td>${appointment.specialty.name}</td>
               <td>${appointment.type.name}</td>
               <td>
                <spring:url value="/appointments/{appointmentId}/details" var="appointmentUrl">
                      <spring:param name="appointmentId" value="${appointment.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(appointmentUrl)}">View more</a>
               	</td>
               	<td>
               	<spring:url value="/appointments/delete/{appointmentId}" var="appointmentDeleteUrl">
    				<spring:param name="appointmentId" value="${appointment.id}"/>
    			</spring:url> 
    			<a href="${fn:escapeXml(appointmentDeleteUrl)}">Cancel</a> 
    			</td>            
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</petclinic:layout>
