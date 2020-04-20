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
    <h2 class="my-5">Clients</h2>

    <table id="clientsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Email</th>
            <th>Document</th>
            <th style="width: 120px;">Birthdate</th>
            <th>Health Insurance</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clients}" var="client">
            <tr>
                <td>
                    <spring:url value="/professionals/{clientId}" var="clientUrl">
                        <spring:param name="clientId" value="${client.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clientUrl)}"><c:out value="${client.firstName} ${client.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${client.email}"/>
                </td>
                <td>
                    <c:out value="${client.documentType} ${client.document}"/>
                </td>
                <td>
                    <c:out value="${client.birthDate}"/>
                </td>
                <td>
                    <c:out value="${client.healthInsurance} ${client.healthCardNumber}"/>
                </td>     
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</petclinic:layout>