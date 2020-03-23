<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:staffLayout currentPage="dashboard">
    <div class="container">
        <h1 class="mt-5">Consultation <span class="badge badge-info">08:00</span> </h1>
        <hr>
        <h3 class="my-3 mt-5">Appointment Information</h3>
        <div class="row">
            <div class="col-md-8">
                <table class="table table-striped">
                    <tr>
                        <th>Name</th>
                        <td>
                            <b>
                                <c:out value="${appointment.client.fullName}" /></b>
                        </td>
                    </tr>
                    <tr>
                        <th>Date</th>
                        <td>
                            <c:out value="${appointment.date}" />
                        </td>
                    </tr>
                    <tr>
                        <th>Start Time</th>
                        <td>
                            <c:out value="${appointment.startTime}" />
                        </td>
                    </tr>
                    <tr>
                        <th>Type</th>
                        <td>
                            <c:out value="${appointment.type.name}" />
                        </td>
                    </tr>
                    <tr>
                        <th>Reason</th>
                        <td>
                            <c:out value="${appointment.reason}" />
                        </td>
                    </tr>
                </table>
            </div>
			  <div class="col-md-3" style="background-image:url;">
                <img src="https://a.wattpad.com/cover/194784944-256-k712290.jpg" width="160px"
                    style="border-radius:5px;">
            </div>
        </div>
        <hr class="my-5">
        <div class="row">
            <div class="col-md-6">
                <h3 class="mb-3">Diagnosis</h3>
                <petclinic:selectField name="medicines" label="Deseases " names="${deseases}"
                    size="${fn:length(deseases)}" />
						<button class="btn btn-sm btn-primary">View clinical history </button>
            </div>
            <div class="col-md-6">
                <h3 class="mb-3">Drugs</h3>
                <petclinic:selectField name="medicines" label="Medicine " names="${medicines}"
                    size="${fn:length(medicines)}" />
            </div>
        </div>
		<hr class="my-5">
		<h3 class="mb-3">Bill</h3>
			....
    </div>

    <diV
        style="position:fixed;bottom:0; background:white;border-top:2px solid #ddd;width:calc(100% - 240px);padding:20px;">
		<div class="text-right">
        <button class="btn btn-outline-danger" type="submit">MARK ABSENT</button>
        <button class="btn btn-primary" type="submit">SAVE & NEXT CONSULTATION</button>
		</div>
	</div>
</petclinic:staffLayout>
