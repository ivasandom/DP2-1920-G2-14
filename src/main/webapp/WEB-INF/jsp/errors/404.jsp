<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="error">

    <spring:url value="/resources/images/acme.jpg" var="petsImage"/>
    <img src="${petsImage}"/>

    <h1>Error 404. Not found.</h1>

    <p>Resource not found</p>

</petclinic:layout>
