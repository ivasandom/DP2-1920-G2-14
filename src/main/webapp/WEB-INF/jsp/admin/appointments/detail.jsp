<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="appointments" pageTitle="Appointments: #${appointment.id}">

  <jsp:attribute name="customScript">
    <script>
      $(document).ready(function () {
        $('.dataTable').DataTable();
      });

    </script>
  </jsp:attribute>
  <jsp:body>
    <div class="row">
      <div class="col-8">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Details</h3>
          </div>
          <div class="card-body">
            <dl class="dl-horizontal">
              <dt>Client</dt>
              <dd>
                <spring:url value="/admin/clients/{clientId}" var="clientUrl">
                  <spring:param name="clientId" value="${appointment.client.id}" />
                </spring:url>
                <a href="${fn:escapeXml(clientUrl)}">
                  <c:out value="${appointment.client.firstName} ${appointment.client.lastName}" />
                </a>
              </dd>
              <dt>Professional</dt>
              <dd>
                <spring:url value="/admin/professionals/{professionalId}" var="professionalUrl">
                  <spring:param name="professionalId" value="${appointment.professional.id}" />
                </spring:url>
                <a href="${fn:escapeXml(professionalUrl)}">
                  <c:out value="${appointment.professional.firstName} ${appointment.professional.lastName}" />
                </a>
              </dd>
              <dt>Date time</dt>
              <dd>
                <c:out value="${appointment.date} at ${appointment.startTime}" />
              </dd>
              <dt>Reason</dt>
              <dd>
                <c:out value="${appointment.reason}" />
              </dd>
              <dt>Center</dt>
              <dd>
                <c:out value="${appointment.center.address}" />
              </dd>
              <dt>Status</dt>
              <dd>
              	<c:if test="${appointment.status eq 'COMPLETED'}" >
                	<span class="badge badge-success"><c:out value="${appointment.status.displayName}" /></span>
	            </c:if>
                <c:if test="${appointment.status eq 'ABSENT'}" >
                	<span class="badge badge-danger"><c:out value="${appointment.status.displayName}" /></span>
	            </c:if>
	            <c:if test="${appointment.status eq 'PENDING'}" >
                	<span class="badge badge-warning"><c:out value="${appointment.status.displayName}" /></span>
	            </c:if>
              </dd>
            </dl>
            
          </div>
          <div class="card-footer">
          <spring:url value="/admin/appointments/{appointmentId}/edit" var="editUrl">
              <spring:param name="appointmentId" value="${appointment.id}" />
            </spring:url>
            <a href="${fn:escapeXml(editUrl)}" class="btn btn-primary text-white">
              Edit appointment
            </a>
            <c:if test="${appointment.status ne 'COMPLETED'}">
            	<form:form action="/admin/appointments/${appointment.id}/delete">
            		<button type="submit" class="btn btn-danger">Delete appointment</button>
            	</form:form>
            </c:if>
            
          </div>
        </div>
      </div>
      <div class="col-4">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Bill</h3>
          </div>
          <div class="card-body">
            <c:if test="${not empty appointment.bill}">
              <dl class="dl-horizontal">
                <dt>Price</dt>
                <dd>
                  $
                  <c:out value="${appointment.bill.price}" />
                </dd>
                <dt>IVA</dt>
                <dd>
                  <c:out value="${appointment.bill.iva}" />
                </dd>
                <dt>Final price</dt>
                <dd>
                  $
                  <c:out value="${appointment.bill.finalPrice}" />
                </dd>
                <dt>Status</dt>
                <dd>
                  <c:if test="${appointment.bill.status eq 'PAID'}" >
                	<span class="badge badge-success"><c:out value="${appointment.bill.status.displayName}" /></span>
	              </c:if>
	              <c:if test="${appointment.bill.status eq 'REFUNDED'}" >
	              	<span class="badge badge-danger"><c:out value="${appointment.bill.status.displayName}" /></span>
	              </c:if>
	              <c:if test="${appointment.bill.status ne 'REFUNDED' and appointment.bill.status ne 'PAID'}" >
	              	<span class="badge badge-warning"><c:out value="${appointment.bill.status.displayName}" /></span>
	              </c:if>
                </dd>
                <dt>Created at</dt>
                <dd>
                  <c:out value="${appointment.bill.createdAt}" />
                </dd>
              </dl>

            </c:if>
            <c:if test="${empty appointment.bill}">
              No bill. A bill will be created after appointment has been completed.
            </c:if>
          </div>
          <div class="card-footer">
          	<spring:url value="/admin/bills/{billId}" var="billUrl">
              <spring:param name="billId" value="${appointment.bill.id}" />
            </spring:url>
            <a href="${fn:escapeXml(billUrl)}" class="btn btn-secondary text-white">
              View bill
            </a>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>

</petclinic:staffLayout>
