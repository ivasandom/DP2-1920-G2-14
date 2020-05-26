<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="names" required="true" rtexprvalue="true" type="java.util.List"
              description="Names in the list" %>
<%@ attribute name="size" required="false" rtexprvalue="true"
              description="Size of Select" %>
<%@ attribute name="itemLabel" required="false" rtexprvalue="true"
              description="Item label" %>
<%@ attribute name="itemValue" required="false" rtexprvalue="true"
              description="Item value" %>

<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <div class="${cssGroup}">
        <label for="id-${label}">${label}</label>

            <form:select class="form-control ${status.error ? 'is-invalid' : ''} ${valid ? 'is-valid' : ''}" path="${name}">
            	<c:if test="${not empty itemLabel}">
            		<form:options items="${names}" itemLabel="${itemLabel}" itemValue="${empty itemValue ? 'id' : itemValue}"/>
            	</c:if>
            	<c:if test="${empty itemLabel}">
            		<form:options items="${names}" />
            	</c:if>
            </form:select>
            <c:if test="${status.error}">
                <div class="invalid-feedback">
                	${status.errorMessage}
                </div>
            </c:if>
    </div>
</spring:bind>
