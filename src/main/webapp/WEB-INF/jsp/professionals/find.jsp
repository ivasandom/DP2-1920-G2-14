<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout currentPage="professionals">
	<div class="container">
    <h2 class="mx-3 my-5">Find Professionals</h2>

    
    <form:form modelAttribute="professional" action="/professionals" method="get" class="form-horizontal"
               id="search-owner-form">
                <div class="col-sm-10">
                   <div class="form-group">
                        <label for="id-center">Center</label>
                        <form:select class="form-control" id="center" path="center.id">
                            <option disabled selected hidden>Choose a center</option>
                            <form:options items="${centers}" itemLabel="address" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label for="id-specialty">Specialty</label>
                        <form:select class="form-control" id="specialty" path="specialty.id">
                            <option disabled selected hidden>Choose a specialty</option>
                            <form:options items="${specialties}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                     <span class="help-inline" style="color: red;"><form:errors path="*"/></span>
                </div>
               

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Find Professionals</button>
            </div>
        </div>

    </form:form>

    <br/> 
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-primary" href='<spring:url value="/owners/new" htmlEscape="true"/>'>Add Owner</a>
	</sec:authorize>
	</div>
	
</petclinic:layout>
