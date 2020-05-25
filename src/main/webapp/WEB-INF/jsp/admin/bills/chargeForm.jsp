<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="bills" pageTitle="Charge bill: #${bill.id}">
	
	<jsp:body>
	    <div class="row">
	      <div class="col-12">
	        <div class="card">
	          <div class="card-header">
	            <h3 class="card-title">Charge bill</h3>
	          </div>
	          <div class="card-body">
	            <form:form modelAttribute="transaction">
	              <div class="form-group ">
	                   <petclinic:inputField label="Amount" name="amount" />
	                   <petclinic:selectField label="Payment method" name="paymentMethod.token" names="${paymentMethods}" itemLabel="displayName" itemValue="token" />
	              </div>
	
	              <button class="btn btn-secondary container-fluid" type="submit">Mark as paid</button>
	
	            </form:form>
	          </div>
	        </div>
	      </div>
	    </div>
  	</jsp:body>


</petclinic:staffLayout>
