<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  include file="header.jsp"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light ">
	<a class="navbar-brand" href="#"><b>UMIYA KHOR</b></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarText" aria-controls="navbarText"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link" href="#">Selling

			</a></li>
			<li class="nav-item"><a class="nav-link" href="/purchase">Purchase</a></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="http://example.com"
				data-toggle="dropdown">Master</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="/customer">Customer</a> <a
						class="dropdown-item" href="/product">Product</a> <a
						class="dropdown-item" href="/shop">Shop</a><a class="dropdown-item"
						href="/location">Location</a><a class="dropdown-item" href="/expenseCategory">Expense</a>
				</div></li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li class="nav-item"><a class="nav-link" href="/logout">Logout</a>
		</ul>

	</div>
</nav>
<div class="container-fluid mt-4 mb-4">
	

