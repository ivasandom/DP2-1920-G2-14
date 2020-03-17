<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="owners">
	<div class="container">
    <h2 class="my-5">My appointments <a class="btn btn-sm btn-primary" href="/appointments/new">+ New appointment</a></h2>
    

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th>Date</th>
            <th>Center</th>
            <th>Professional</th>
            <th>Specialty</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="appointment">
            <tr>
               <td>${appointment.startTime}</td>
               <td>${appointment.center.address}</td>
               <td>${appointment.professional.fullName}</td>
               <td>${appointment.specialty.name}</td>
               <td><a href="#">View more</a></td>             
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</petclinic:layout>