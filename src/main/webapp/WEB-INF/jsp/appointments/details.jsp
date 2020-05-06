<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout currentPage="appointments">
    <jsp:attribute name="customScript">
        <script>
            // https://stackoverflow.com/questions/24021276/select2-search-options/47697371#47697371
            $('.select-multiple-search').select2({
            	width: "100%",
                matcher: function (params, data) {
                    // If there are no search terms, return all of the data
                    if ($.trim(params.term) === '') {
                        return data;
                    }

                    // Do not display the item if there is no 'text' property
                    if (typeof data.text === 'undefined') {
                        return null;
                    }

                    // `params.term` is the user's search term
                    // `data.id` should be checked against
                    // `data.text` should be checked against
                    var q = params.term.toLowerCase();
                    if (data.text.toLowerCase().indexOf(q) > -1 || data.id.toLowerCase().indexOf(q) > -1) {
                        return $.extend({}, data, true);
                    }

                    // Return `null` if the term should not be displayed
                    return null;
                }
            });

        </script>
    </jsp:attribute>
    <jsp:body>

        <form:form modelAttribute="appointment" action="" method="post">
            <div class="container" style="padding-bottom:80px;">
                <h1 class="mt-5">Consultation <small> <span class="badge badge-secondary">
                            <c:out value="${ appointment.startTime }" /></span></small> </h1>
                <hr />
                <div class="row form-group">
                    <div class="list-group list-group-horizontal" role="tablist" style="width:100%;">
                        <a class="list-group-item list-group-item-action active" data-toggle="list"
                            href="#list-information" role="tab" aria-controls="information">
                            <div class="justify-content-between">
                                <h5 class="mb-1 text-center">1. Information</h5>
                            </div>
                            <p class="mb-1 text-center">Patient and appointment</p>
                        </a>
                        <a class="list-group-item list-group-item-action" data-toggle="list" href="#list-diagnosis"
                            role="tab" aria-controls="diagnosis">
                            <div class="justify-content-between">
                                <h5 class="mb-1 text-center">2. Diagnosis</h5>
                            </div>
                            <p class="mb-1 text-center">Make a diagnosis</p>

                        </a>
                        <a class="list-group-item list-group-item-action" data-toggle="list" href="#list-billing"
                            role="tab" aria-controls="billing">
                            <div class="justify-content-between">
                                <h5 class="mb-1 text-center">3. Billing</h5>
                            </div>
                            <p class="mb-1 text-center">Charge patient</p>
                        </a>
                        <a class="list-group-item list-group-item-action" data-toggle="list" href="#list-summary"
                            role="tab" aria-controls="summary">
                            <div class="justify-content-between">
                                <h5 class="mb-1 text-center">4. Summary</h5>
                            </div>
                            <p class="mb-1 text-center">Ready, go!</p>
                        </a>
                    </div>
                </div>

                <hr />

                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="list-information" role="tabpanel"
                        aria-labelledby="list-information-list">
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
                                <button class="btn btn-sm btn-primary mb-3" disabled>View clinical history </button>
                            </div>
                            <div class="col-md-3" style="background-image:url;">
                                <img src="https://a.wattpad.com/cover/194784944-256-k712290.jpg" height="246px">
                            </div>

                        </div>
                    </div>


                        <div class="tab-pane fade" id="list-diagnosis" role="tabpanel"
                            aria-labelledby="list-diagnosis-list">


                            <div class="form-group">
                                <label>Description</label>
                                <form:textarea class="form-control" path="diagnosis.description"></form:textarea>
                                <small class="form-text text-muted">Add a description of client symptoms and the
                                    reason of selected
                                    deseases and medicines</small>
                                     <c:if test="${status.error}">
                                    <div class="invalid-feedback">
                                        ${status.errorMessage}
                                    </div>
                                </c:if>
                            </div>
<!--                             	    <h2 class="my-5">Diagnosis</h2> -->
	    
<!-- 	    <table class="table table-striped"> -->
<%-- 	        <c:forEach var="medicine" items="${medicines}"> --%>
<!-- 				<tr> -->
<!-- 	                <td valign="top"> -->
<!-- 	                    <dl> -->
<!-- 	                        <dt>Medicine</dt> -->
<%-- 	                        <dd><c:out value="${medicine.name}"/>,	Price: <c:out value="${medicine.price}"/></dd> --%>
<!-- 	                    </dl> -->
<!-- 	                </td> -->
<!-- 	        	</tr> -->
<%-- 			</c:forEach> --%>
<!-- 		</table> -->
	    
<!-- 	    <h2 class="my-5">Deseases</h2> -->
<!-- 		 <table class="table table-striped"> -->
<%-- 	        <c:forEach var="desease" items="${deseases}"> --%>
<!-- 				<tr> -->
<!-- 	                <td valign="top"> -->
<%-- 	                        <dd><c:out value="${desease.name}"/></dd> --%>

<!-- 	                    </dl> -->
<!-- 	                </td> -->
<!-- 	        	</tr> -->
<%-- 			</c:forEach> --%>
<!-- 		</table> -->
                            <div class="form-group">
                                <label>Deseases</label>
                                <form:select class="form-control select-multiple-search" path="diagnosis.deseases"
                                    multiple="true">
                                    <form:options items="${deseaseList}" itemLabel="name" itemValue="id" />
                                </form:select>
                                <c:if test="${status.error}">
                                    <div class="invalid-feedback">
                                        ${status.errorMessage}
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Medicines</label>
                                <form:select class="form-control select-multiple-search ${status.error ? 'is-invalid' : ''} ${valid ? 'is-valid' : ''}" path="diagnosis.medicines"
                                    multiple="true">
                                    <form:options items="${medicineList}" itemLabel="name" itemValue="id" />
                                </form:select>
                                <c:if test="${status.error}">
                                    <div class="invalid-feedback">
                                        <small class="form-text text-muted">${status.errorMessage}</small>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="list-billing" role="tabpanel"
                            aria-labelledby="list-billing-list">

                            <div class="form-group">
                                <label>Amount to charge</label>
                                <form:input class="form-control ${status.error ? 'is-invalid' : ''} ${valid ? 'is-valid' : ''}" type="number" placeholder="0.00 EUR" path="receipt.price"/>
                                <small class="form-text text-muted">Add the cost of the consultation.</small>
                                <c:if test="${status.error}">
                                    <div class="invalid-feedback">
                                        ${status.errorMessage}
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Payment method</label>
                                <form:select
                                class="form-control ${status.error ? 'is-invalid' : ''} ${valid ? 'is-valid' : ''}"
                                id="paymentMethod" path="client.paymentMethods">
                                <form:options items="${appointment.client.paymentMethods}" itemLabel="brand" itemValue="id" />
                            </form:select>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="list-summary" role="tabpanel"
                            aria-labelledby="list-summary-list">
                            <p>Summary</p>
                        </div>
                  
                </div>

                     <span class="help-inline" style="color: red;"><form:errors path="*"/></span>


            </div>
            <div
                style="position:fixed;bottom:0; background:white;border-top:2px solid #ddd;width:calc(100% - 240px);padding:20px;">
                <div class="text-right">
                    <a href="/appointments" class="btn btn-outline-secondary">BACK</a>
                <spring:url value="/appointments/delete/{appointmentId}" var="appointmentDeleteUrl">
    				<spring:param name="appointmentId" value="${appointment.id}"/>
    			</spring:url> 
    			<a href="${fn:escapeXml(appointmentDeleteUrl)}" class="btn btn-default">Cancel</a>
    			
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>