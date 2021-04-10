<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
			<a class="navbar-brand" href="dashboard">
				Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form:form id="addComputerForm" action="addComputer" method="POST" modelAttribute="computer">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> 
								<form:input type="text" class="form-control" id="name" path="name" placeholder="Computer name"/>
								<form:errors path="name" cssClass="error" />
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label>
								<form:input type="date" class="form-control" id="introduced" path="introduced" placeholder="Introduced date"/>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label>
								<form:input type="date" class="form-control" id="discontinued" path="discontinued" placeholder="Discontinued date"/>
								<form:errors path="discontinued" cssClass="error"/>									
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<form:select class="form-control" id="companyId" path="companyId">
									<form:option selected="true" value="0" label="Please select a company"/>
									<form:options items="${companyList}" itemValue="id" itemLabel="name"/>
								</form:select>
								<form:errors path="companyId" cssClass="error"/>	
							</div>
						</fieldset>
						
						<div class="alert alert-danger page-alert" id="alert-message" style="display: none;">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
							<strong>Too bad!</strong> Change a few things up and try submitting again. The introduced date must be before the discontinued date!
						</div>
						
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/computerValidator.js"></script>
	
</body>
</html>
