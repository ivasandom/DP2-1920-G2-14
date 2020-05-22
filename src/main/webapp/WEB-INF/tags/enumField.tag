<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="names" required="true" rtexprvalue="true" type="java.lang.Object[]"
              description="Names in the list" %>
              
<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <div class="${cssGroup}">
        <label for="id-${label}">${label}</label>

            <form:select class="form-control ${status.error ? 'is-invalid' : ''} ${valid ? 'is-valid' : ''}" path="${name}">
            	<form:options items="${names}" itemLabel="displayName"/>	
            </form:select>
            <c:if test="${status.error}">
                <div class="invalid-feedback">
                	${status.errorMessage}
                </div>
            </c:if>
    </div>
</spring:bind>
