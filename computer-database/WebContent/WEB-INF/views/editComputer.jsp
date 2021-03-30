<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="DashboardServlet"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.getComputerId() }
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="EditComputerServlet" method="POST">
                        <input type="hidden" value="${computer.getComputerId() }" id="id" name="computerId"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="${computer.getComputerName()}">
                                <span class="error"><c:out value="${errors['computerName']}" /></span>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="${ empty computer.getIntroduced() ?  'Introduced date' : computer.getIntroduced()}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="${ empty computer.getDiscontinued() ?  'Discontinued date' : computer.getDiscontinued()}">
                                <span class="error"><c:out value="${errors['discontinued']}" /></span>
                            </div>                            
                            <div class="form-group">
								<label for="companyId">Company</label> 
								<select class="form-control" id="companyId" name="companyId">
									<option value="0" disabled selected><c:out value="Please select a company"/></option>
									<c:forEach var="company" items="${companyList}">
										<option value="${ company.getId()}"><c:out
												value="${ company.getName() }" /></option>
									</c:forEach>
								</select>
								<span class="error"><c:out value="${errors['companyId']}" /></span>
							</div>      
                        </fieldset>
                        
                        <div class="alert alert-danger page-alert" id="alert-message" style="display: none;">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
							<strong>Too bad!</strong> Change a few things up and try submitting again. The introduced date must be before the discontinued date!
						</div>                        
                        
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="DashboardServlet" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
</body>
</html>