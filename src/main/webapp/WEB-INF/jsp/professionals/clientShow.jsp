<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	<petclinic:layout currentPage="clients" pageTitle="Client: ${client.firstName} ${client.lastName}">
	
	<div class="container">
	    <h2 class="my-5">Client Information</h2>
	
	
	    <table class="table table-striped">
	   
	        <tr>	        	        
	            <th>Name</th>
	            <td><c:out value="${client.firstName} ${client.lastName}"/></td>
	        </tr>
	        
	        <tr>
	            <th>Email</th>
	            <td><c:out value="${client.email}"/></td>	            
	        </tr>
	        
	        <tr>
	            <th>Birthdate</th>
	            <td><c:out value="${client.birthDate}"/></td>
	        </tr>
	        
	        <tr>
	        	<th>RegistrationDate</th>
	            <td><c:out value="${client.registrationDate}"/></td>
	        </tr>
	        
	       	<tr>
	            <th>Document - Type</th>
	            <td><c:out value="${client.document} - ${client.documentType}"/></td>
	        </tr>
	        
	        <tr>
	            <th>Health Insurance</th>
	            <td><c:out value="${client.healthInsurance}"/></td>
	        </tr>
	        
	        <tr>
	            <th>Health Card Number</th>
	            <td><c:out value="${client.healthCardNumber}"/></td>
	        </tr>


	    </table>
	
<%-- 	    <spring:url value="{ownerId}/edit" var="editUrl"> --%>
<%-- 	        <spring:param name="ownerId" value="${owner.id}"/> --%>
<%-- 	    </spring:url> --%>
<%-- 	    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Owner</a> --%>
	
<%-- 	    <spring:url value="{ownerId}/pets/new" var="addUrl"> --%>
<%-- 	        <spring:param name="ownerId" value="${owner.id}"/> --%>
<%-- 	    </spring:url> --%>
<%-- 	    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Pet</a> --%>
	
<!-- 	    <br/> -->
<!-- 	    <br/> -->
<!-- 	    <br/> -->
	    <h2>Diagnosis</h2>
	    
	    <h2>Desease</h2>
	
<!-- 	    <table class="table table-striped"> -->
<%-- 	        <c:forEach var="pet" items="${owner.pets}"> --%>
	
<!-- 	            <tr> -->
<!-- 	                <td valign="top"> -->
<!-- 	                    <dl class="dl-horizontal"> -->
<!-- 	                        <dt>Name</dt> -->
<%-- 	                        <dd><c:out value="${pet.name}"/></dd> --%>
<!-- 	                        <dt>Birth Date</dt> -->
<%-- 	                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd> --%>
<!-- 	                        <dt>Type</dt> -->
<%-- 	                        <dd><c:out value="${pet.type.name}"/></dd> --%>
<!-- 	                    </dl> -->
<!-- 	                </td> -->
<!-- 	                <td valign="top"> -->
<!-- 	                    <table class="table-condensed"> -->
<!-- 	                        <thead> -->
<!-- 	                        <tr> -->
<!-- 	                            <th>Visit Date</th> -->
<!-- 	                            <th>Description</th> -->
<!-- 	                        </tr> -->
<!-- 	                        </thead> -->
<%-- 	                        <c:forEach var="visit" items="${pet.visits}"> --%>
<!-- 	                            <tr> -->
<%-- 	                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td> --%>
<%-- 	                                <td><c:out value="${visit.description}"/></td> --%>
<!-- 	                            </tr> -->
<%-- 	                        </c:forEach> --%>
<!-- 	                        <tr> -->
<!-- 	                            <td> -->
<%-- 	                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl"> --%>
<%-- 	                                    <spring:param name="ownerId" value="${owner.id}"/> --%>
<%-- 	                                    <spring:param name="petId" value="${pet.id}"/> --%>
<%-- 	                                </spring:url> --%>
<%-- 	                                <a href="${fn:escapeXml(petUrl)}">Edit Pet</a> --%>
<!-- 	                            </td> -->
<!-- 	                            <td> -->
<%-- 	                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl"> --%>
<%-- 	                                    <spring:param name="ownerId" value="${owner.id}"/> --%>
<%-- 	                                    <spring:param name="petId" value="${pet.id}"/> --%>
<%-- 	                                </spring:url> --%>
<%-- 	                                <a href="${fn:escapeXml(visitUrl)}">Add Visit</a> --%>
<!-- 	                            </td> -->
<!-- 	                        </tr> -->
<!-- 	                    </table> -->
<!-- 	                </td> -->
<!-- 	            </tr> -->
	
<%-- 	        </c:forEach> --%>
<!-- 	    </table> -->
	    </div>

</petclinic:layout>