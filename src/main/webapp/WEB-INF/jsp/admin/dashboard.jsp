<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<petclinic:staffLayout currentPage="dashboard" pageTitle="Dashboard">
	
	<jsp:attribute name="customScript">		
	   	
	   	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	    
	    <script>
	    	
		    var ctx1 = document.getElementById('billedPerDay').getContext('2d');
		    var chart = new Chart(ctx1, {
		        type: 'bar',
		        data: {
		            labels: [
		            	<c:forEach items="${billedPerDay}" var="day">
                			'<c:out value="${day[1]}" />',
                		</c:forEach>
		            ],
		            datasets: [{
		                label: 'Billed per day',
		                backgroundColor: '#17a2b8',
		                borderColor: '#17a2b8',
		                data: [
		                	<c:forEach items="${billedPerDay}" var="day">
                				<c:out value="${day[0]}" />,
                			</c:forEach>
		                ]
		            }]
		        },
		        options: {}
		    });
	    
		    var ctx2 = document.getElementById('appointmentsDonut').getContext('2d');
		    var chart = new Chart(ctx2, {
		        type: 'pie',
		        data: {
		            labels: ['Pending', 'Completed', 'Absent'],
		            datasets: [{
		                label: 'My First dataset',
		                backgroundColor: ['#dc3545', '#28a745', '#ffc107'],
		                borderColor: ['#dc3545', '#28a745', '#ffc107'],
		                data: [
		        			<c:out value='${numPendingAppointments}' />,
		        			<c:out value='${numCompletedAppointments}' />,
		        			<c:out value='${numAbsentAppointments}' />,
		        			
		        		]
		            }]
		        },
		        options: {}
		    });
		    
		   
	      
	      
	
	    </script>
	  </jsp:attribute>
	<jsp:body>
  
    <div class="row">
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-info">
              <div class="inner">
                <h3><c:out value="${numClients}" /></h3>

                <p>Clients</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
              <a href="/admin/clients/" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-success">
              <div class="inner">
                <h3><c:out value="${numProfessionals}" /></h3>

                <p>Professionals</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
              <a href="/admin/professionals/" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-danger">
              <div class="inner">
                <h3><c:out value="${numAppointments}" /></h3>

                <p>Appointments</p>
              </div>
              <div class="icon">
                <i class="ion ion-pie-graph"></i>
              </div>
              <a href="/admin/appointments" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
           <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-warning">
              <div class="inner">
                <h3>$ <c:out value="${totalBilled > 0 ? totalBilled : '0.00'}" /></h3>

                <p>Total billed</p>
              </div>
              <div class="icon">
                <i class="ion ion-person-add"></i>
              </div>
              <a href="/admin/bills" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
        </div>
        <!-- /.row -->
        <div class="row">
        	<div class="col-md-8">
            	  <!-- PIE CHART -->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Bills per day</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i>
                  </button>
        
                </div>
              </div>
              <div class="card-body">
              	<canvas id="billedPerDay"></canvas>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          	</div>
        	<div class="col-md-4">
            	  <!-- PIE CHART -->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Appointments by status</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i>
                  </button>
        
                </div>
              </div>
              <div class="card-body">
              	<canvas id="appointmentsDonut"></canvas>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          	</div>
  		</div>
       </jsp:body>
       
</petclinic:staffLayout>
