<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="bills" pageTitle="Bill: #${bill.id}">

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
              <dt>Name</dt>
              <dd>
                <c:out value="${bill.name}" />
              </dd>
              <dt>Document</dt>
              <dd>
               <c:out value="${bill.document} - ${bill.documentType}" />
              </dd>
              <dt>Price</dt>
              <dd>
                <c:out value="${bill.price}" />
              </dd>
              <dt>IVA</dt>
              <dd>
                <c:out value="${bill.iva}" />
              </dd>
              <dt>Final price</dt>
              <dd>
                <c:out value="${bill.finalPrice}" />
              </dd>
              <dt>Total paid</dt>
              <dd>
                <c:out value="$ ${bill.totalPaid}" />
              </dd>
              <dt>Status</dt>
              <dd>
                <c:if test="${bill.status eq 'PAID'}" >
		        	<span class="badge badge-success"><c:out value="${bill.status.displayName}" /></span>
		        </c:if>
		        <c:if test="${bill.status eq 'REFUNDED'}" >
		           	<span class="badge badge-danger"><c:out value="${bill.status.displayName}" /></span>
		        </c:if>
		        <c:if test="${bill.status ne 'REFUNDED' and bill.status ne 'PAID'}" >
		         	<span class="badge badge-warning"><c:out value="${bill.status.displayName}" /></span>
		        </c:if>
              </dd>
            </dl>
            
          </div>
          <div class="card-footer">
          <spring:url value="/admin/bills/{billId}/charge" var="chargeUrl">
              <spring:param name="billId" value="${bill.id}" />
            </spring:url>
            <a href="${fn:escapeXml(chargeUrl)}" class="btn btn-primary text-white">
              Charge bill
            </a>
          </div>
        </div>
      </div>
      
    </div>
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
                  	<th>Date time</th>
                    <th>Amount</th>
                    <th>Source</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${bill.transactions}" var="transaction">
                    <tr>
                      <td>
                      	<c:out value="${transaction.createdAt}" />
                      </td>
                      <td>
                        <c:out value="${transaction.amount} $" />
                      </td>
                      <td>
                        <c:out value="${transaction.source}" />
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
                      <td>
                      	<c:if test="${not transaction.refunded and transaction.success and transaction.type eq 'CHARGE'}">
                      	<form:form action="/admin/bills/${bill.id}/refund/${transaction.id}/" method="post">
                      		<button type="submit" class="btn btn-danger btn-sm">Refund</button>
                      	</form:form>
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

  </jsp:body>

</petclinic:staffLayout>
