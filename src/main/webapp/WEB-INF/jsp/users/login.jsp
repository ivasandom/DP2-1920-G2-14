<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout currentPage="clients">
	<div class="jumbotron landing">
		<div class="container">
			<h1 class="display-4" style="font-weight: normal;">Área clientes</h1>
		</div>
	</div>
    <div class="container">
   		<div class="jumbotron">
		<div class="container">
			<div class="row" style="text-align:center;">
				<div class="col-md-6" style="border-right:1px solid #ddd;">
					
					
					<form:form action="/signin" method="post" style="text-align:left;max-width:400px;">
						<h2>
							¿Ya tienes cuenta?
						</h2>
						<p>Entra a tu cuenta para acceder a todas <br>las ventajas de <em>Acme</em></p>
	                    <c:if test="${not empty error}">
	                    	<div class="alert alert-danger">
	                           ${error}
	                      	</div>
	                    </c:if>
	                    <c:if test="${not empty logout}">
	                    	<div class="alert alert-success">
	                           ${logout}
	                      	</div>
	                    </c:if>
						<div class="form-group">
							<label for="username">Username</label>
							<input type="text" class="form-control" id="username" name="username">
						</div>
						<div class="form-group">
							<label for="password">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password">
						</div>
						<button type="submit" class="btn btn-primary">Log in</button>
	
	                       
	
	                        
	                </form:form>
					
					
					
				</div>
				<div class="col-md-6">
					<h2>
						¿No tienes cuenta?
					</h2>
					<p>Crea una cuenta para acceder a todas <br>las ventajas de <em>Acme</em></p>
					<a href="/clients/new" class="btn btn-primary">Regístrate</a>
				</div>
			</div>
		</div>
    </div>
    </div>
</petclinic:layout>
