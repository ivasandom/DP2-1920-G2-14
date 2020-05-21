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
                <c:out value="${appointment.status}" />
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
            <button type="button" class="btn btn-danger">Delete appointment</button>
          </div>
        </div>
      </div>
      <div class="col-4">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Receipt</h3>
          </div>
          <div class="card-body">
            <c:if test="${not empty appointment.receipt}">
              <dl class="dl-horizontal">
                <dt>Price</dt>
                <dd>
                  $
                  <c:out value="${appointment.receipt.price}" />
                </dd>
                <dt>Status</dt>
                <dd>
                  <c:out value="${appointment.receipt.status}" />
                </dd>
                <dt>Created at</dt>
                <dd>
                  <c:out value="${appointment.receipt.status}" />
                </dd>
              </dl>

            </c:if>
            <c:if test="${empty appointment.receipt}">
              No receipt. A receipt will be created after appointment has been completed.
            </c:if>
          </div>
          <div class="card-footer">
          <button class="btn btn-secondary">Download .pdf</button>
          </div>
        </div>
      </div>
    </div>
    <c:if test="${not empty appointment.receipt}">
      <div class="row equal">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">Transactions</h3>
            </div>
            <div class="card-body">
              <table class="table table-bordered table-striped dataTable">
                <thead>
                  <tr>
                    <th>Amount</th>
                    <th>Token</th>
                    <th>Type</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${appointment.receipt.transactions}" var="transaction">
                    <tr>
                      <td>
                        <c:out value="${transaction.amount} $" />
                      </td>
                      <td>
                        <c:out value="${transaction.token}" />
                      </td>
                      <td>
                        <c:if test="${transaction.type eq 'CHARGE'}" >
                        <span class="badge badge-success"><c:out value="${transaction.type}" /></span>
                        </c:if>
                        <c:if test="${transaction.type eq 'REFUND'}" >
                        <span class="badge badge-danger"><c:out value="${transaction.type}" /></span>
                        </c:if>
                      </td>
                      <td>
                        <c:if test="${transaction.success}" >
                        <span class="badge badge-success text-uppercase"><c:out value="${transaction.status}" /></span>
                        </c:if>
                        <c:if test="${not transaction.success}" >
                        <span class="badge badge-danger text-uppercase"><c:out value="${transaction.status}" /></span>
                        </c:if>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </c:if>
  </jsp:body>

</petclinic:staffLayout>
