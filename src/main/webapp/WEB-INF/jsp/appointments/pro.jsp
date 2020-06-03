<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layoutPro currentPage="appointments">

    <div class="container">
        <h2 class="my-5">Consultation Mode</h2>

        <h5 class="mb-3">Next</h5>
        <table id="ownersTable" class="table table-striped">
            <thead>
                <tr>
                    <th width="20%">Client</th>
                    <th width="20%">Start Time</th>
                    <th width="20%">Type</th>
                    <th width="10%">Status</th>
                    <th width="30%"></th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not empty nextAppointment}">
	                <tr>
	                    <td>${nextAppointment.client.fullName}</td>
	                    <td>${nextAppointment.startTime}</td>
	                    <td>${nextAppointment.type}</td>
	                    <td>
	                        <span class="badge badge-${nextAppointment.status eq 'PENDING' ? 'info' : 'danger'}">${nextAppointment.status}</span></td>
	                    <td>
	                        <spring:url value="/appointments/{appointmentId}/consultation" var="appointmentUrl">
	                            <spring:param name="appointmentId" value="${nextAppointment.id}" />
	                        </spring:url>
	                        <spring:url value="/appointments/{appointmentId}/absent" var="appointmentAbsentUrl">
	                            <spring:param name="appointmentId" value="${nextAppointment.id}" />
	                        </spring:url>
	                        <a class="btn btn-success btn-sm" href="${fn:escapeXml(appointmentUrl)}">
	                            <c:out value="> Start consultation" /></a>
	                        <form:form action="${appointmentAbsentUrl}" method="post" class="d-inline">
	                            <button type="submit" class="btn btn-outline-danger btn-sm">
	                                <c:out value="Mark absent" /></button>
	                        </form:form>
	                    </td>
	                </tr>
                </c:if>
                <c:if test="${empty nextAppointment}">
                	<tr style="text-align:center;">
                		<td colspan="5">No hay citas</td>
                	</tr>
                </c:if>
            </tbody>
        </table>

        <h5 class="mt-5">Today pending</h5>
        <table id="ownersTable" class="table table-striped">
            <thead>
                <tr>
                    <th width="20%">Client</th>
                    <th width="20%">Start Time</th>
                    <th width="20%">Type</th>
                    <th width="10%">Status</th>
                    <th width="30%"></th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not pendingAppointments.hasNext()}">
                	<tr style="text-align:center;">
                		<td colspan="5">No hay citas</td>
                	</tr>
                </c:if>
            	<c:if test="${pendingAppointments.hasNext()}">
	                <c:forEach items="${pendingAppointments}" var="appointment">
	                    <tr>
	                        <td>${appointment.client.fullName}</td>
	                        <td>${appointment.startTime}</td>
	                        <td>${appointment.type}</td>
	                        <td>
	                            <span class="badge badge-${appointment.status eq 'PENDING' ? 'info' : 'danger'}">${appointment.status}</span></td>
	
	                        <td>
	                            <spring:url value="/appointments/{appointmentId}/consultation" var="appointmentUrl">
	                                <spring:param name="appointmentId" value="${appointment.id}" />
	                            </spring:url>
	                            <spring:url value="/appointments/{appointmentId}/absent" var="appointmentAbsentUrl">
	                                <spring:param name="appointmentId" value="${appointment.id}" />
	                            </spring:url>
	                            <a class="btn btn-success btn-sm" href="${fn:escapeXml(appointmentUrl)}">
	                                <c:out value="> Start consultation" /></a>
	                            <form:form action="${appointmentAbsentUrl}" method="post"  class="d-inline">
	                                <button type="submit" class="btn btn-outline-danger btn-sm">
	                                    <c:out value="Mark absent" /></button>
	                            </form:form>
	
	                        </td>
	                    </tr>
	                </c:forEach>
                </c:if>
            </tbody>
        </table>

        <h5 class="mt-5">Today completed consultations</h5>
        <table id="ownersTable" class="table table-striped">
            <thead>
                <tr>
                    <th width="20%">Client</th>
                    <th width="20%">Start Time</th>
                    <th width="20%">Type</th>
                    <th width="10%">Status</th>
                    <th width="30%"></th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not empty completedAppointments}">
	                <c:forEach items="${completedAppointments}" var="appointment">
	                    <tr>
	                        <td>${appointment.client.fullName}</td>
	                        <td>${appointment.startTime}</td>
	                        <td>${appointment.type}</td>
	                        <td>
	                            <span class="badge badge-success">${appointment.status}</span></td>
	
	                        <td>
	                           
	                        </td>
	                    </tr>
	                </c:forEach>
                </c:if>
                <c:if test="${empty completedAppointments}">
                	<tr style="text-align:center;">
                		<td colspan="5">No hay citas</td>
                	</tr>
                </c:if>
            </tbody>
        </table>


    </div>
</petclinic:layoutPro>
