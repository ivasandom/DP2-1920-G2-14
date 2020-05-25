<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="clients" pageTitle="Clients: ${client.firstName} ${client.lastName}">

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
            <h3 class="card-title">Details</h3>
          </div>
          <div class="card-body">
            <dl class="dl-horizontal">
              <dt>First name</dt>
              <dd>
                <c:out value="${client.firstName}" />
              </dd>
              <dt>Last name</dt>
              <dd>
                <c:out value="${client.lastName}" />
              </dd>
              <dt>Health Insurance</dt>
              <dd>
                <c:out value="${client.healthInsurance.displayName} / ${client.healthCardNumber}" />
              </dd>
              <dt>Email</dt>
              <dd>
                <c:out value="${client.email}" />
              </dd>
              <dt>Document</dt>
              <dd>
                <c:out value="${client.document} (${client.documentType})" />
              </dd>
              <dt>Birth date</dt>
              <dd>
                <c:out value="${client.birthDate}" />
              </dd>
            </dl>
            <spring:url value="/admin/clients/{clientId}/edit" var="editUrl">
              <spring:param name="clientId" value="${client.id}" />
            </spring:url>
            <a href="${fn:escapeXml(editUrl)}" class="btn btn-primary text-white">
              Edit client
            </a>
            <form:form action="/admin/clients/${client.id}/delete/" method="post">
            	<button type="submit" class="btn btn-danger">Delete client</button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-8">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Appointments</h3>
          </div>
          <div class="card-body">
            <table class="table table-bordered table-striped dataTable">
              <thead>
                <tr>
                  <th>Professional</th>
                  <th>Date</th>
                  <th>Reason</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${client.appointments}" var="appointment">
                  <tr>
                    <td>
                      <spring:url value="/admin/professionals/{professionalId}" var="professionalUrl">
                        <spring:param name="professionalId" value="${appointment.professional.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(professionalUrl)}">
                        <c:out value="${appointment.professional.firstName} ${appointment.professional.lastName}" />
                      </a>
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
      <div class="col-4">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Transactions</h3>
          </div>
          <div class="card-body">
            <table class="table table-bordered table-striped dataTable">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Amount</th>
                  <th>Date</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>

</petclinic:staffLayout>
