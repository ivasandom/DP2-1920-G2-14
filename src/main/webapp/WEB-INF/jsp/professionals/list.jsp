<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="professionals">
	<div class="container">
    <h2 class="mx-3 my-5">Find Professionals</h2>

    
    <form:form modelAttribute="professional" action="/professionals" method="get" class="form-horizontal"
               id="search-owner-form">
        <div class="form-group">
            <div class="control-group" id="lastName">
                <label class="col-sm-2 control-label">Specialty</label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="specialty" size="30" maxlength="80"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Find Professionals</button>
            </div>
        </div>

    </form:form>

    <br/> 

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">First name</th>
            <th style="width: 200px;">Last name</th>
            <th>Specialty</th>
            <th style="width: 120px">Email</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="professional">
            <tr>
                <td>    
                    <c:out value="${professional.firstName}"/>
                </td>
                <td>
                   	<c:out value="${professional.lastName}"/>
                </td>
                <td>
                    <span class="badge badge-secondary"><c:out value="${professional.specialty}"/></span>
                </td>
                <td>
                    <c:out value="${professional.email}"/>
                </td>                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</petclinic:layout>
