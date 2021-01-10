<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Expense Category Form</div>
			<div class="card-body">
				<form
					<c:if test="${expenseCategoryVo.id == 0}">action="/expenseCategory/add"</c:if>
					<c:if test="${expenseCategoryVo.id != 0}">action="/expenseCategory/update"</c:if>
					method="post" id="expenseCategoryForm">

					<input type="hidden" name="id" value="${expenseCategoryVo.id }"
						id="expenseCategoryId">
					<div class="form-group has-float-label mt-3">
						<label> Expense Category Name</label> <input type="text" name="name"
							class="form-control" id="name" value="${expenseCategoryVo.name}">
					</div>
					<button type="submit" class="btn btn-light btn-sm" id="addExpenseCategoryButton">
						<c:if test="${expenseCategoryVo.id == 0}">Add Category</c:if>
						<c:if test="${expenseCategoryVo.id != 0}">Update Category</c:if>
					</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="card">
			<div class="card-header">Expense Category Table</div>
			<div class="card-body">
				<div class="d-flex flex-row-reverse">
					<div class="p-2 ">
						<div class="">
							<div class="input-group input-group-sm	">
								<input type="text" class="form-control" placeholder="Search..."
									id="inputSearchField">
								<div class="input-group-append">
									<span class="input-group-text"><i class="fas fa-search"></i></span>
								</div>
							</div>
						</div>
					</div>
					<div class="p-2 ">
						<div class="mr-3">
							<select class="form-control form-control-sm ml-3"
								id="lengthChnageSelectBox">
								<option>10</option>
								<option>20</option>
								<option>30</option>
								<option>40</option>
								<option>100</option>
								<option>200</option>
								<option>500</option>
							</select>
						</div>

					</div>


				</div>
				<div class="table-responsive">
					<table class="table table-bordered" id="expenseCategoryTable">
						<thead>
							<tr>
								<th scope="col">Expense Category No.</th>
								<th scope="col">Name</th>
								<th scope="col">Created Date</th>
								<th scope="col">Created By</th>
								<th scope="col">Modified Date</th>
								<th scope="col">Modified By</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="expenseCategory" items="${expenseCategorys}">
								<tr>
									<th scope="row">${expenseCategory.id }</th>
									<td>${expenseCategory.name }</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${expenseCategory.createdDate}" /></td>
									<td>${expenseCategory.createdBy}</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${expenseCategory.updatedDate}" /></td>
									<td>${expenseCategory.updatedBy}</td>
									<td>

										<div class="dropdown">
											<!--Trigger-->
											<a type="button" id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"
												class="btn btn-default btn-action"><em
												class="fas fa-ellipsis-v "></em></a>


											<!--Menu-->
											<div class="dropdown-menu dropdown-primary">
												<a class="dropdown-item"
													href="/expenseCategory/update/${expenseCategory.id }"> <em
													class="fas fa-edit"></em>&nbsp;&nbsp;Update
												</a> <a class="dropdown-item deleteLink"
													href="/expenseCategory/delete/${expenseCategory.id }"> <em
													class="fas fa-trash"></em>&nbsp;&nbsp;Delete
												</a>
											</div>
										</div>

									</td>
								</tr>
							</c:forEach>


						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="/js/page/expense-category.js"></script>
<%@  include file="footer.jsp"%>
