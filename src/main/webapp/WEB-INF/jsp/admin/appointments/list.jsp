<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="appointments" pageTitle="Appointments">

  <jsp:attribute name="customScript">
    <script>
      $(document).ready(function () {
        $('.dataTable').DataTable();
      });

    </script>
  </jsp:attribute>
  <jsp:body>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Appointments</h3>
          </div>
          <div class="card-body">
            <table class="table table-bordered table-striped dataTable">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Client</th>
                  <th>Professional</th>
                  <th>Date</th>
                  <th>Reason</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${appointments}" var="appointment">
                  <tr>
                    <td>
                      <spring:url value="/admin/appointments/{appointmentId}" var="appointmentUrl">
                        <spring:param name="appointmentId" value="${appointment.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(appointmentUrl)}">
                        <c:out value="${appointment.id}" /></a>
                    </td>
                    <td>
                      <spring:url value="/admin/clients/{clientId}" var="clientUrl">
                        <spring:param name="clientId" value="${appointment.client.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(clientUrl)}">
                        <c:out value="${appointment.client.firstName} ${appointment.client.lastName}" /></a>
                    </td>

                    <td>
                       <spring:url value="/admin/professionals/{professionalId}" var="professionalUrl">
                        <spring:param name="professionalId" value="${appointment.professional.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(professionalUrl)}">
                        <c:out value="${appointment.professional.firstName} ${appointment.professional.lastName}" /></a>
                    </td>

                    <td>
                      <c:out value="${appointment.date}" />
                    </td>

                    <td>
                      <c:out value="${appointment.reason}" />
                    </td>

                    <td>
                      <c:out value="${appointment.status}" />
                    </td>
                    
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>

</petclinic:staffLayout>
