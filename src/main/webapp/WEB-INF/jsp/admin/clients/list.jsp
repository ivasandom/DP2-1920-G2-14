<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="clients" pageTitle="Clients">

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
            <h3 class="card-title">Clients</h3>
          </div>
          <div class="card-body">
            <table class="table table-bordered table-striped dataTable">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Document - Type</th>
                  <th>Birthdate</th>
                  <th>Health Insurance</th>
                  <th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${clients}" var="client">
                  <tr>
                    <td>
                      <spring:url value="/admin/clients/{clientId}" var="clientUrl">
                        <spring:param name="clientId" value="${client.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(clientUrl)}">
                        <c:out value="${client.firstName} ${client.lastName}" /></a>
                    </td>

                    <td>
                      <c:out value="${client.email}" />
                    </td>

                    <td>
                      <c:out value="${client.document} - ${client.documentType}" />
                    </td>

                    <td>
                      <c:out value="${client.birthDate}" />
                    </td>

                    <td>
                      <c:out value="${client.healthInsurance} ${client.healthCardNumber}" />
                    </td>
                    
                    
                    <td>
                      <spring:url value="/admin/clients/{clientId}" var="clientUrl">
                      	<spring:param name="clientId" value="${client.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(clientUrl)}" class="btn btn-secondary btn-sm">
                        View details
                      </a>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          <div class="card-footer">
          	<a href="/admin/clients/create" class="btn btn-primary">+ Create new client</a>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>

</petclinic:staffLayout>
