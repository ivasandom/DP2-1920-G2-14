<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="currentPage" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #074b95 !important;">

	    <a class="navbar-brand" href="/">
	    	A C M E 
	    		<sec:authorize access="hasAnyAuthority('admin', 'professional')">
	    			<strong style="
					    margin-left: 1px;
					    letter-spacing: 7px;
					    text-transform: uppercase;
					    font-size: 0.9rem;
					    font-style: oblique;
					    color: gold;
					">Staff</strong>
				</sec:authorize></a>
	
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ${currentPage eq 'professionals' ? 'active' : ''}">
                <a class="nav-link" href="/professionals/find">Find professionals</a>
            </li>
            <sec:authorize access="hasAuthority('professional')">
	            <li class="nav-item ${currentPage eq 'professionals' ? 'active' : ''}">
	                <a class="nav-link" href="/appointments/pro">Consultation mode</a>
	            </li>
            </sec:authorize>
            <sec:authorize access="hasAuthority('admin')">
	            <li class="nav-item ${currentPage eq 'administration' ? 'active' : ''}">
	                <a class="nav-link" href="/admin">Administration</a>
	            </li>
            </sec:authorize>
        </ul>
        <ul class="navbar-nav">
            <sec:authorize access="!isAuthenticated()">
                <li class="nav-item ${currentPage eq 'clients' ? 'active' : ''}">
                    <a class="nav-link btn btn-sm btn-primary mr-2" href="/login">Área clientes</a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <sec:authentication property="name" />
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                    	<sec:authorize access="hasAuthority('client')">
	                    	<a class="dropdown-item" href="/appointments">Mis citas</a>
	                    	<a class="dropdown-item" href="/payments/methods">My payment methods</a>
                    	</sec:authorize>
                        <a class="dropdown-item" href="/logout">Logout</a>
                    </div>
                </li>
            </sec:authorize>
        </ul>
    </div>
</nav>