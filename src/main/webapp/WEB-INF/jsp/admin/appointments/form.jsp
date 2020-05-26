<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="appointments" pageTitle="Edit appointment: #${appointment.id}">
<jsp:body>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Edit</h3>
          </div>
          <div class="card-body">
            <form:form modelAttribute="appointment">
            <h5>Details</h5>
              <div class="form-group ">
             	 <div class="row">
                  <div class="col-md-6">
                   <petclinic:inputField label="Date" name="date" />
                  </div>
                  <div class="col-md-6">
                    <petclinic:inputField label="Start time" name="startTime" />
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                   <petclinic:selectField label="Client" name="client"
                      names="${clients}" itemLabel="user.username" />
                  </div>
                  <div class="col-md-6">
                    <petclinic:selectField label="Professional" name="professional"
                      names="${professionals}" itemLabel="user.username" />
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                   <petclinic:selectField label="Center" name="center"
                      names="${centers}" itemLabel="address" />
                  </div>
                  <div class="col-md-6">
                    <petclinic:selectField label="Specialty" name="specialty"
                      names="${specialties}" itemLabel="name" />
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <petclinic:inputField label="Reason" name="reason" />
                  </div>
                </div>

               	<petclinic:enumField label="Status" name="status"
                      names="${statusChoices}" />


              </div>

              <button class="btn btn-secondary container-fluid" type="submit">Edit</button>

            </form:form>
          </div>
        </div>
      </div>
    </div>

  </jsp:body>


</petclinic:staffLayout>
