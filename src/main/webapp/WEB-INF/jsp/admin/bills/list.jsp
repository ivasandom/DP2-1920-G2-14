<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="bills" pageTitle="Bills">

  <jsp:attribute name="customScript">
    <script>
      $(document).ready(function () {
        $('.dataTable').DataTable(
        	{"order": [[ 0, "desc" ]]});
      });

    </script>
  </jsp:attribute>
  <jsp:body>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Bills</h3>
          </div>
          <div class="card-body">
            <table class="table table-bordered table-striped dataTable">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Document</th>
                  <th>Price</th>
                  <th>IVA</th>
                  <th>Final price</th>
                  <th>Status</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${bills}" var="bill">
                  <tr>

                    <td>
                      <c:out value="${bill.name}" />
                    </td>
                    
                    <td>
                      <c:out value="${bill.document} ${bill.documentType.displayName}" />
                    </td>

                    <td>
                      <c:out value="${bill.price}" /> $
                    </td>

                    <td>
                      <c:out value="${bill.iva}" />
                    </td>
                    
                    <td>
                      <c:out value="${bill.finalPrice}" /> $
                    </td>
                    
                    <td>
                      <c:if test="${bill.status eq 'PAID'}" >
		              	<span class="badge badge-success"><c:out value="${bill.status.displayName}" /></span>
		              </c:if>
		              <c:if test="${bill.status eq 'REFUNDED'}" >
		              	<span class="badge badge-danger"><c:out value="${bill.status.displayName}" /></span>
		              </c:if>
		              <c:if test="${bill.status ne 'REFUNDED' and bill.status ne 'PAID'}" >
		              	<span class="badge badge-warning"><c:out value="${bill.status.displayName}" /></span>
		              </c:if>
                    </td>
                    
                    <td>
                      <spring:url value="/admin/bills/{billId}" var="billUrl">
                        <spring:param name="billId" value="${bill.id}" />
                      </spring:url>
                      <a href="${fn:escapeXml(billUrl)}" class="btn btn-secondary btn-sm">
                        View details
                      </a>
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
