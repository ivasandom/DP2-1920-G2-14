<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:staffLayout currentPage="dashboard">
    <jsp:attribute name="customScript">
        <script>
            // https://stackoverflow.com/questions/24021276/select2-search-options/47697371#47697371
            $('.select-multiple-search').select2({
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
        <div class="container" style="padding-bottom:80px;">
            <h1 class="mt-5">Consultation <span class="badge badge-secondary">
                    <c:out value="${ appointment.startTime }" /></span> </h1>
            <hr>
            <h3 class="mt-5 mb-4">Appointment Information</h3>
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
                    <img src="https://a.wattpad.com/cover/194784944-256-k712290.jpg" height="246px">
                </div>
            </div>
            <hr class="my-5">
            <h3>Diagnosis</h3>
            <button class="btn btn-sm btn-primary mb-3" disabled>View clinical history </button>
            <div class="form-group">
                <label>Description</label>
                <textarea class="form-control"></textarea>
				<small class="form-text text-muted">Add a description of client symptoms and the reason of selected deseases and medicines</small>
            </div>
            <div class="form-group">
                <label>Deseases</label>
                <select class="form-control select-multiple-search" name="deseases" multiple>
                    <c:forEach items="${deseases}" var="desease">
                        <option value="${desease.id}">${desease.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Medicines</label>
                <select class="form-control select-multiple-search" name="medicines" multiple>
                    <c:forEach items="${medicines}" var="medicine">
                        <option value="${medicine.id}">${medicine.name}</option>
                    </c:forEach>
                </select>
            </div>
            <hr class="my-5">
            <h3 class="mb-3">Bill</h3>
            <div class="form-group">
				<label>Amount</label>
				<input class="form-control" type="number" placeholder="100.00">
				<small class="form-text text-muted">Añada el coste de la consulta si procede.</small>
			</div>
        </div>

        <div
            style="position:fixed;bottom:0; background:white;border-top:2px solid #ddd;width:calc(100% - 240px);padding:20px;">
            <div class="text-right">
                <button class="btn btn-outline-danger" type="submit">MARK ABSENT</button>
                <button class="btn btn-primary" type="submit">SAVE & NEXT CONSULTATION</button>
            </div>
        </div>
    </jsp:body>
</petclinic:staffLayout>
