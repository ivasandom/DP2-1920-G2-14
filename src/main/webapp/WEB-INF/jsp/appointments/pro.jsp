<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout currentPage="appointments">
	<div class="container">
    <h2 class="my-5">My appointments </h2>
    

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th>Last Name</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>Reason</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="appointment">
            <tr>
               <td>${appointment.client.lastName}</td>
               <td>${appointment.date}</td>
               <td>${appointment.startTime}</td>
               <td>${appointment.reason}</td>
              <td>
                    <spring:url value="/appointments/{appointmentId}" var="appointmentUrl">
                        <spring:param name="appointmentId" value="${appointment.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(appointmentUrl)}"><c:out value="Go to consultation"/></a>
                </td>             
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</petclinic:layout>
