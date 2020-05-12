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


	    <h2 class="my-5">Diagnosis</h2>
	    
	    <table class="table table-striped">
	        <c:forEach var="medicine" items="${medicines}">
				<tr>
	                <td valign="top">
	                    <dl>
	                        <dt>Medicine</dt>
	                        <dd><c:out value="${medicine.name}"/>,	Price: <c:out value="${medicine.price}"/></dd>
	                    </dl>
	                </td>
	        	</tr>
			</c:forEach>
		</table>
	    
	    <h2 class="my-5">Deseases</h2>
		 <table class="table table-striped">
	        <c:forEach var="desease" items="${deseases}">
				<tr>
	                <td valign="top">
	                	<dl>
	                        <dd><c:out value="${desease.name}"/></dd>

	                    </dl>
	                </td>
	        	</tr>
			</c:forEach>
		</table>
	    </div>

</petclinic:layout>