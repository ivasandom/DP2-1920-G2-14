<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<petclinic:layout currentPage="professionals">
	<div class="container">
    <h2 class="my-5">Professionals</h2>

    <table id="prosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Specialty</th>
            <th>Center</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${professionals}" var="professional">
            <tr>
                <td>
                    <c:out value="${professional.firstName} ${professional.lastName}"/>
                </td>
                
                <td>
                    <c:out value="${professional.email}"/>
                </td>
                
                <td>
                    <c:out value="${professional.specialty}"/>
                </td>
                
                <td>
                    <c:out value="${professional.center.address}"/>
                </td>     

                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</petclinic:layout>