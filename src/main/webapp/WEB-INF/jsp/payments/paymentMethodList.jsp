<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout currentPage="payments">

      <div class="container">
          <h2 class="mt-5">My payment methods</h2>
          <a href="/payments/new-method" class="btn btn-outline-primary">+ Add method</a>
          <hr />
			<table class="table table-striped">
				<thead>
					<tr>
						<th>#</th>
						<th>Token</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paymentMethods}" var="paymentMethod">
						<tr>
							<td>#</td>
							<td><c:out value="${paymentMethod.token}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

       </div>
 
</petclinic:layout>
