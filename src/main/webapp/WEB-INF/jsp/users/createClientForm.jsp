<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="clients">

	<jsp:attribute name="customScript">
		<script>
			$("#birthDate").datepicker({
	            minDate: today,
	            dateFormat: 'dd/mm/yy'
	        });
		</script>
	</jsp:attribute>
    
    <jsp:body>
	    <div class="container">
	    <h2 class="my-5">
	        Sign up
	    </h2>
	    <form:form modelAttribute="client" class="form-horizontal" id="add-owner-form">
	        <div class="form-group has-feedback">
	
	        	<div class="row">
	        		<div class="col-md-6">
	        			<petclinic:inputField label="First name" name="firstName"/>
	        		</div>
	        		<div class="col-md-6">
	        			<petclinic:inputField label="Last name" name="lastName"/>
	        		</div>
	        	</div>
	        	 <petclinic:inputField label="Birth date" name="birthDate" type="date"/>
	        	<div class="row">
	        		<div class="col-md-6">
	        			<petclinic:inputField label="Document" name="document"/>
	        		</div>
	        		<div class="col-md-6">
	        			<petclinic:enumField label="Document Type" name="documentType"
	                      names="${documentTypes}" />
	                </div>
	        	</div>
	        	<div class="row">
	        		<div class="col-md-6">
	        			<petclinic:enumField label="Health Insurance" name="healthInsurance"
	                      names="${healthInsurances}" />
	        		</div>
	        		<div class="col-md-6">
	        			<petclinic:inputField label="Health Card Number" name="healthCardNumber"/>
	        		</div>
	        	</div>  
	            <petclinic:inputField label="Email" name="email" type="email"/>
	            <petclinic:inputField label="Username" name="user.username"/>
	            <petclinic:inputField label="Password" name="user.password" type="password"/>
	
	            
	        </div>
	  
	        <button class="btn btn-primary container-fluid" type="submit">Sign up</button>
	            
	    </form:form>
	    </div>
    </jsp:body>
</petclinic:layout>
