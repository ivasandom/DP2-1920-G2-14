<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout currentPage="payments">

      <div class="container">
          <h2 class="mt-5">My cards</h2>
          <a href="/payments/new-card" class="btn btn-outline-primary">+ Add card</a>
          <hr />
			<table class="table table-striped">
				<thead>
					<tr>
						<th>#</th>
						<th>Token</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paymentMethods}" var="creditCard">
						<tr>
							<td>#</td>
							<td><c:out value="${creditCard.token}"/></td>
							<td><c:out value="${creditCard.brand}"/> **** **** **** <c:out value="${creditCard.last4}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

       </div>
 
</petclinic:layout>
