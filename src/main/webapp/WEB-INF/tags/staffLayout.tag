<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<%@ attribute name="currentPage" required="true" %>
<%@ attribute name="pageTitle" required="false" %>
<%@ attribute name="customScript" required="false" fragment="true"%>




<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="x-ua-compatible" content="ie=edge">

  <petclinic:head pageTitle="${pageTitle}" />

  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"
    integrity="sha256-h20CPZ0QyXlBuAw7A+KluUYx/3pK+c7lYEpqLTlxjYQ=" crossorigin="anonymous" />
  <!-- Theme style -->
  <spring:url value="/resources/adminlte/adminlte.min.css" var="adminlteCss" />
  <link href="${adminlteCss}" rel="stylesheet" />
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
  <!-- Datatable -->
  <spring:url value="/resources/adminlte/dataTables.bootstrap4.min.css" var="dataTablesBs4CSS" />
  <link href="${dataTablesBs4CSS}" rel="stylesheet" />


  <style>
    [class*='col-']>.card {
      height: calc(100% - 1rem)
    }

  </style>
</head>

<body class="hold-transition sidebar-mini">
  <div class="wrapper">

    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
      <!-- Left navbar links -->
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
        </li>
      </ul>
    </nav>
    <!-- /.navbar -->

    <!-- Main Sidebar Container -->
    <aside class="main-sidebar sidebar-dark-primary elevation-4">
      <!-- Brand Logo -->
      <a href="/admin" class="brand-link" style="color:#fff;text-align center">

        <span class="brand-text font-weight-light">	    
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
				</sec:authorize></span>
      </a>

      <!-- Sidebar -->
      <div class="sidebar">
        <!-- Sidebar user panel (optional)        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
          <div class="image">
            <img
              src="https://w7.pngwing.com/pngs/682/576/png-transparent-willyrex-youtuber-spain-wigetta-en-las-dinolimpiadas-youtube-thumbnail.png"
              class="img-circle elevation-2" alt="User Image">
          </div>
          <div class="info">
            <a href="#" class="d-block">Admin name</a>
          </div>
        </div> -->
        

        <!-- Sidebar Menu -->
        <nav class="mt-2">
          <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
            <li class="nav-item ">
              <a href="/admin" class="nav-link">
                <i class="nav-icon fas fa-th"></i>
                <p>
                  Dashboard
                  <span class="right badge badge-danger">New</span>
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="/admin/clients" class="nav-link">
                <i class="nav-icon fas fa-users"></i>
                <p>
                  Clients
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="/admin/professionals" class="nav-link">
                <i class="nav-icon fas fa-user-nurse"></i>
                <p>
                  Professionals
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="/admin/appointments" class="nav-link">
                <i class="nav-icon fas fa-syringe"></i>
                <p>
                  Appointments
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="/admin/bills" class="nav-link">
                <i class="nav-icon fas fa-file-invoice"></i>
                <p>
                  Bills
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="/" class="nav-link">
                <i class="nav-icon fas fa-home"></i>
                <p>
                  Exit
                </p>
              </a>
            </li>
          </ul>
        </nav>
        <!-- /.sidebar-menu -->
      </div>
      <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <div class="content-header">
        <div class="container-fluid">
          <div class="row mb-2">
            <div class="col-sm-6">
              <h1 class="m-0 text-dark">${pageTitle}</h1>
            </div><!-- /.col -->
            <div class="col-sm-6">
              <ol class="breadcrumb float-sm-right">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item active">Starter Page</li>
              </ol>
            </div><!-- /.col -->
          </div><!-- /.row -->
        </div><!-- /.container-fluid -->
      </div>
      <!-- /.content-header -->

      <!-- Main content -->
      <div class="content">
        <div class="container-fluid">
          <jsp:doBody />
        </div><!-- /.container-fluid -->
      </div>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
      <!-- Control sidebar content goes here -->
      <div class="p-3">
        <h5>Title</h5>
        <p>Sidebar content</p>
      </div>
    </aside>
    <!-- /.control-sidebar -->

    <!-- Main Footer -->
    <footer class="main-footer">
      <!-- To the right -->
      <div class="float-right d-none d-sm-inline">
        Anything you want
      </div>
      <!-- Default to the left -->
      <strong>Copyright &copy; 2014-2019 <a href="https://adminlte.io">AdminLTE.io</a>.</strong> All rights reserved.
    </footer>
  </div>
  <!-- ./wrapper -->

  <!-- REQUIRED SCRIPTS -->

  <!-- jQuery -->
  <spring:url value="/resources/adminlte/jquery.min.js" var="jqueryJS" />
  <script src="${jqueryJS}"></script>
  <!-- Bootstrap 4 -->
  <spring:url value="/resources/adminlte/bootstrap.bundle.min.js" var="bootstrapJS" />
  <script src="${bootstrapJS}"></script>
  <!-- AdminLTE App -->
  <spring:url value="/resources/adminlte/adminlte.min.js" var="adminlteJS" />
  <script src="${adminlteJS}"></script>
  <!-- jQuery Datatable -->
  <spring:url value="/resources/adminlte/jquery.dataTables.min.js" var="datatablesJS" />
  <script src="${datatablesJS}"></script>

  <jsp:invoke fragment="customScript" />

</body>

</html>
