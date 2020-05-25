<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout currentPage="home">
    <div class="jumbotron landing">
		<div class="container">
			<sec:authorize access="!isAuthenticated()">
				<h1 class="display-4" style="font-weight: normal;">Clínicas <strong><em>Acme</em></strong></h1>
				<p class="lead">El mejor software para clínicas de salud</p>
				<hr class="my-4" style="height:2px;background-color:rgba(255,255,255,0.1);">
				<p>Citación online, facturas y mucho más.</p>
				<a class="btn btn-primary btn-lg" href="/appointments/new" role="button">> Citación online</a>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<h1 class="display-4" style="font-weight: normal;">Bienvenido/a, <strong><em><sec:authentication property="name" /></em></strong></h1>
				<p class="lead">¡Gracias por estar de vuelta!</p>
				<hr class="my-4" style="height:2px;background-color:rgba(255,255,255,0.1);">
				<p>Citación online, facturas y mucho más.</p>
				 <sec:authorize access="!hasAuthority('professional')">
		           <a class="btn btn-primary btn-lg" href="/appointments/new" role="button">> Citación online</a>
	            </sec:authorize>
	            <sec:authorize access="hasAuthority('professional')">
		           <a class="btn btn-primary btn-lg" href="/appointments/pro" role="button">> Acceder a modo consulta</a>
	            </sec:authorize>
	            <sec:authorize access="!hasAuthority('admin')">
		           <a class="btn btn-primary btn-lg" href="/admin" role="button">> Panel de administración</a>
	            </sec:authorize>
			</sec:authorize>
		</div>
    </div>
    <div class="container">
        <h2>¿Por qué clinicas Acme?</h2>
        <p>
            Porque nadie cuida de ti como nosotros.
        </p>
    </div>

</petclinic:layout>
