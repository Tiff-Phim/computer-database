<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
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
			<a class="navbar-brand" href="DashboardServlet">
				Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${ computerTotal }" />
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="AddComputerServlet">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>

				<!-- Browse attribute computers -->
				<tbody>
					<c:forEach var="computer" items="${computerPage}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${ computer.get().getId() }"></td>
							<td><a href="<c:url value="EditComputerServlet"><c:param name="computerId" 
								value="${ computer.get().getId() }"/></c:url>"><c:out
										value="${computer.get().getName() }" /></a></td>									
							<td><c:out value="${computer.get().getIntroduced() }" /></td>
							<td><c:out value="${computer.get().getDiscontinued() }" /></td>
							<td><c:out value="${computer.get().getCompany().getName() }" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				
				<li><a href="?page=${1}" aria-label="First"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<c:if test="${ pageNumber > 1 }">
					<li><a href="?page=${pageNumber > 1 ? pageNumber - 1 : 1}" aria-label="Previous"> Previous </a></li>
				</c:if>
				
				<c:forEach var="number" items="${listPageNumbers}">
					<li><a href="?page=${number}">${number}</a></li>
				</c:forEach>
				
				<c:if test="${ pageNumber < pageTotal }">
					<li><a href="?page=${pageNumber + 1}" aria-label="Next"> Next </a></li>
				</c:if>
				
				<li><a href="?page=${ pageTotal }" aria-label="Last"> <span
						aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
			<form action="DashboardServlet">
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

</body>
</html>