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
                <div class="col-sm-10">
                   <div class="form-group">
                        <label for="id-center">Center</label>
                        <form:select class="form-control" id="center" path="center">
                            <option disabled selected hidden>Choose a center</option>
                            <form:options items="${centers}" itemLabel="address" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label for="id-specialty">Specialty</label>
                        <form:select class="form-control" id="specialty" path="specialty">
                            <option disabled selected hidden>Choose a specialty</option>
                            <form:options items="${specialties}" itemLabel="name" itemValue="id" />
                        </form:select>
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
