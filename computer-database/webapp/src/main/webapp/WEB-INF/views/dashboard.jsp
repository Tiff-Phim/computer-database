<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="label.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
			<h1 id="homeTitle">
				<c:out value="${ computerTotal }" />
				<fmt:message key="label.computersFound"/>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<fmt:message key="label.searchName"/>" /> 
						<input type="submit" id="searchsubmit" value="<fmt:message key="label.filterName"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><fmt:message key="label.addComputer"/></a> 
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message key="label.edit"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="dashboard" method="POST">
			<input type="hidden" name="selection" value="">
		</form>
		
		<form id="sortForm" action="dashboard" method="POST">
			<input type="hidden" name="filter" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input type="checkbox" id="selectall" /> 
							<span style="vertical-align: top;"> - <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i> </a>
							</span>
						</th>						
						<th><fmt:message key="label.computerName"/><a href="#" id="orderByComputerName" onclick="$.fn.orderBy('COMPUTER_NAME');">
						<i class="fa fa-fw fa-sort pull-right fa-clickable"></i></a></th>
						<th><fmt:message key="label.introducedDate"/><a href="#" id="orderByIntroducedDate" onclick="$.fn.orderBy('COMPUTER_INTRODUCED');">
						<i class="fa fa-fw fa-sort pull-right"></i></a></th>
						<!-- Table header for Discontinued Date -->
						<th><fmt:message key="label.discontinuedDate"/><a href="#" id="orderByDiscontinuedDate" onclick="$.fn.orderBy('COMPUTER_DISCONTINUED');">
						<i class="fa fa-fw fa-sort pull-right"></i></a></th>
						<!-- Table header for Company -->
						<th><fmt:message key="label.companyName"/><a href="#" id="orderByCompanyrName" onclick="$.fn.orderBy('COMPANY_NAME');">
						<i class="fa fa-fw fa-sort pull-right"></i></a></th>

					</tr>
				</thead>

				<!-- Browse attribute computers -->
				<tbody>
					<c:forEach var="computer" items="${computerPage}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${ computer.getId() }"></td>
							<td><a href="<c:url value="editComputer"><c:param name="computerId" 
								value="${ computer.getId() }"/></c:url>"><c:out
										value="${computer.getName() }" /></a></td>									
							<td><c:out value="${computer.getIntroduced() }" /></td>
							<td><c:out value="${computer.getDiscontinued() }" /></td>
							<td><c:out value="${computer.getCompanyName() }" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">	
		<div class="container text-center">			
			<div class="btn-group btn-group-sm pull-left" role="group" id="language">
               <a class="dropdown-item" href="?lang=en"><fmt:message key="label.lang.en"/></a> 
               <a class="dropdown-item" href="?lang=fr"><fmt:message key="label.lang.fr"/></a>
            </div>

			<ul class="pagination">
				
				<li><a href="?page=${1}" aria-label="First"> <span aria-hidden="true">&laquo;</span></a></li>
				<c:if test="${ pageNumber > 1 }">
					<li><a href="?page=${pageNumber > 1 ? pageNumber - 1 : 1}" aria-label="Previous"> <fmt:message key="label.previous"/></a></li>
				</c:if>
				
				<c:forEach var="number" items="${listPageNumbers}">
					<li><a href="?page=${number}">${number}</a></li>
				</c:forEach>
				
				<c:if test="${ pageNumber < pageTotal }">
					<li><a href="?page=${pageNumber + 1}" aria-label="Next"> <fmt:message key="label.next"/> </a></li>
				</c:if>
				
				<li><a href="?page=${ pageTotal }" aria-label="Last"> <span
						aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
			<form action="dashboard"  method="POST">
				<button name="pageSize" type="submit" class="btn btn-default" value="10">10</button>
				<button name="pageSize" type="submit" class="btn btn-default" value="50">50</button>
				<button name="pageSize" type="submit" class="btn btn-default" value="100">100</button>
			</form>
			</div>

		</div>
	</footer>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
	
	<script type="text/javascript">
		var messages = new Array();
		messages["view"] = "<fmt:message key="label.view" />";
		messages["edit"] = "<fmt:message key="label.edit" />";
	</script>

</body>
</html>