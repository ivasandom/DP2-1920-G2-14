<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:staffLayout currentPage="appointments" pageTitle="Appointments: #${appointment.id}">

  <jsp:attribute name="customScript">
    <script>
      $(document).ready(function () {
        $('.dataTable').DataTable();
      });

    </script>
  </jsp:attribute>
  <jsp:body>
    <div class="row">
      <div class="col-6">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Client details</h3>
          </div>
          <div class="card-body">
            <dl class="dl-horizontal">
              <dt>First name</dt>
              <dd>
                <c:out value="${appointment.client.firstName}" />
              </dd>
              <dt>Last name</dt>
              <dd>
                <c:out value="${appointment.client.lastName}" />
              </dd>
              <dt>Health Insurance</dt>
              <dd>
                <c:out value="${appointment.client.healthInsurance} / ${appointment.client.healthCardNumber}" />
              </dd>
              <dt>Email</dt>
              <dd>
                <c:out value="${appointment.client.email}" />
              </dd>
              <dt>Document</dt>
              <dd>
                <c:out value="${appointment.client.document} (${appointment.client.documentType})" />
              </dd>
              <dt>Birth date</dt>
              <dd>
                <c:out value="${appointment.client.birthDate}" />
              </dd>
            </dl>
            <button type="button" class="btn btn-primary">View client</button>
          </div>
        </div>
      </div>
      <div class="col-6">
        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Professional details</h3>
          </div>
          <div class="card-body">
            <dl class="dl-horizontal">
              <dt>First name</dt>
              <dd>
                <c:out value="${appointment.professional.firstName}" />
              </dd>
              <dt>Last name</dt>
              <dd>
                <c:out value="${appointment.professional.lastName}" />
              </dd>
              <dt>Collegiate number</dt>
              <dd>
                <c:out value="${appointment.professional.collegiateNumber}" />
              </dd>
              <dt>Email</dt>
              <dd>
                <c:out value="${appointment.professional.email}" />
              </dd>
              <dt>Document</dt>
              <dd>
                <c:out value="${appointment.professional.document} (${appointment.professional.documentType})" />
              </dd>
            </dl>
            <button type="button" class="btn btn-primary">View professional</button>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-12">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Transactions</h3>
        </div>
        <div class="card-body">
          <table class="table table-bordered table-striped dataTable">
            <thead>
              <tr>
                <th>#</th>
                <th>Amount</th>
                <th>Date</th>
                <th>Status</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    </div>
  </jsp:body>

</petclinic:staffLayout>
