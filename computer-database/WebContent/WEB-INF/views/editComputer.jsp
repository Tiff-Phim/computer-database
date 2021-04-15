<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
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
            <a class="navbar-brand" href="dashboard"><fmt:message key="label.home"/></a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.getId() }
                    </div>
                    <h1><fmt:message key="label.editComputer"/></h1>

                    <form:form id="editComputer" action="editComputer" method="POST" modelAttribute="computerToEdit">
                        <form:input type="hidden" value="${computer.getId() }" id="id" path="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><fmt:message key="label.computerName"/></label>
                                <form:input type="text" class="form-control" id="computerName" path="name" placeholder="${computer.getName()}" required=""/>
                                <form:errors path="name" cssClass="error" />
                            </div>
                            <div class="form-group">
                                <label for="introduced"><fmt:message key="label.introducedDate"/></label>
                                <form:input type="date" class="form-control" id="introduced" path="introduced" placeholder="${ empty computer.getIntroduced() ?  'Introduced date' : computer.getIntroduced()}"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><fmt:message key="label.discontinuedDate"/></label>
                                <form:input type="date" class="form-control" id="discontinued" path="discontinued" placeholder="${ empty computer.getDiscontinued() ?  'Discontinued date' : computer.getDiscontinued()}"/>
                                <form:errors path="discontinued" cssClass="error"/>	
                            </div> 
                            <div class="form-group">
								<label for="companyId"><fmt:message key="label.companyName"/></label>
								<fmt:message key="computer.company.invalid" var="pleaseSelect"/>
								<form:select class="form-control" id="companyId" path="companyId">
									<form:option selected="true" value="0" label="${pleaseSelect }"/>
									<form:options items="${companyList}" itemValue="id" itemLabel="name"/>
								</form:select>
								<form:errors path="companyId" cssClass="error"/>
							</div>  
                        </fieldset>
                        
                        <div class="alert alert-danger page-alert" id="alert-message" style="display: none;">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
							<fmt:message key="computer.discontinued.invalid"/>
						</div>                        
                        
                        <div class="actions pull-right">
                            <input type="submit" value="<fmt:message key="label.edit"/>" class="btn btn-primary">
                            <fmt:message key="label.or"/>
                            <a href="dashboard" class="btn btn-default"><fmt:message key="label.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <!-- <script src="js/computerValidator.js"></script> -->
    
</body>
</html>