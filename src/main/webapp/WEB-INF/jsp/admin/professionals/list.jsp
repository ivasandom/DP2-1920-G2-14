<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="professionals" pageTitle="Professionals">

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
            <h3 class="card-title">Professionals</h3>
          </div>
          <div class="card-body">
            <table class="table table-bordered table-striped dataTable">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Document - Type</th>
                  <th>Collegiate number</th>
                  <th>Specialty</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${professionals}" var="professional">
                  <tr>
                    <td>
                      <spring:url value="/admin/professionals/{professionalId}" var="professionalUrl">
                        <spring:param name="professionalId" value="${professional.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(professionalUrl)}">
                        <c:out value="${professional.firstName} ${professional.lastName}" /></a>
                    </td>

                    <td>
                      <c:out value="${professional.email}" />
                    </td>

                    <td>
                      <c:out value="${professional.document} - ${professional.documentType}" />
                    </td>

                    <td>
                      <c:out value="${professional.collegiateNumber}" />
                    </td>
                    
                    <td>
                      <c:out value="${professional.specialty.name}" />
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          <div class="card-footer">
          	<a href="/admin/professionals/create" class="btn btn-primary">+ Create new professional</a>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>

</petclinic:staffLayout>
