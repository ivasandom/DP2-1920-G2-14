<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="professionals"
  pageTitle="Professionals: ${professional.firstName} ${professional.lastName}">

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
                <c:out value="${professional.firstName}" />
              </dd>
              <dt>Last name</dt>
              <dd>
                <c:out value="${professional.lastName}" />
              </dd>
              <dt>Collegiate number</dt>
              <dd>
                <c:out value="${professional.collegiateNumber}" />
              </dd>
              <dt>Email</dt>
              <dd>
                <c:out value="${professional.email}" />
              </dd>
              <dt>Document</dt>
              <dd>
                <c:out value="${professional.document} (${professional.documentType})" />
              </dd>
            </dl>
            <spring:url value="/admin/professionals/{professionalId}/edit" var="editUrl">
              <spring:param name="professionalId" value="${professional.id}" />
            </spring:url>
            <a href="${fn:escapeXml(editUrl)}" class="btn btn-primary text-white">
              Edit professional
            </a>
            <form:form action="/admin/professionals/${professional.id}/delete/" method="post">
            	<button type="submit" class="btn btn-danger">Delete professional</button>
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
                  <th>Client</th>
                  <th>Date</th>
                  <th>Reason</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${professional.appointments}" var="appointment">
                  <tr>
                    <td>
                      <spring:url value="/admin/clients/{clientId}" var="clientUrl">
                        <spring:param name="clientId" value="${appointment.client.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(clientUrl)}">
                        <c:out value="${appointment.client.firstName} ${appointment.client.lastName}" /></a>
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
