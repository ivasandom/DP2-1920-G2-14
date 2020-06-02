<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="clients" pageTitle="Edit client: ${client.firstName} ${client.lastName}">
  <jsp:body>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Edit</h3>
          </div>
          <div class="card-body">
            <form:form modelAttribute="client" class="form-horizontal" id="add-owner-form">
            <h5>Details</h5>
              <div class="form-group ">
                <div class="row">
                  <div class="col-md-6">
                    <petclinic:inputField label="First name" name="firstName" />
                  </div>
                  <div class="col-md-6">
                    <petclinic:inputField label="Last name" name="lastName" />
                  </div>
                </div>
                <petclinic:inputField label="Birth date" name="birthDate" />
                <div class="row">
                  <div class="col-md-6">
                    <petclinic:inputField label="Document" name="document" />
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
                    <petclinic:inputField label="Health Card Number" name="healthCardNumber" />
                  </div>
                </div>
                <h5>Login</h5>
                <petclinic:inputField label="Email" name="email" type="email" />
                <petclinic:inputField label="Username" name="user.username" />
                <petclinic:inputField type="password" label="Password" name="user.password" />


              </div>

              <button class="btn btn-secondary container-fluid" type="submit">Edit</button>

            </form:form>
          </div>
        </div>
      </div>
    </div>

  </jsp:body>

</petclinic:staffLayout>
