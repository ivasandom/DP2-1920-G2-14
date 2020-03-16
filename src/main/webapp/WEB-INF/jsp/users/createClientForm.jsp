<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="clients">
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
        	<div class="row">
        		<div class="col-md-6">
        			<petclinic:inputField label="Document" name="document"/>
        		</div>
        		<div class="col-md-6">
        			<petclinic:selectField label="Document Type" name="documentType" size="3" names="${documentTypes}"/>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-md-6">
        			<petclinic:selectField label="Health Insurance" name="healthInsurance" size="11" names="${healthInsurances}"/>
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
</petclinic:layout>
