<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Customer Form</div>
			<div class="card-body">
				<form
					<c:if test="${customerVo.id == 0}">action="/customer/add"</c:if>
					<c:if test="${customerVo.id != 0}">action="/customer/update"</c:if>
					method="post" id="customerForm">

					<input type="hidden" name="id" value="${customerVo.id }"
						id="customerId">
					<div class="form-group has-float-label mt-3">
						<label>Name</label> <input type="text" name="name"
							class="form-control" id="name" value="${customerVo.name}">
					</div>
					<div class="form-group has-float-label mt-3">
						<label>Mobile Number</label> <input type="text"
							name="mobileNumber" class="form-control" id="mobileNumber"
							value="${customerVo.mobileNumber}">
					</div>

					<div class="form-group has-float-label">
						<label>District</label> <select class="form-control"
							id="districtId" name="districtVo.id">
							<option value="">---Select---</option>
							<c:forEach var="district" items="${districts}">
								<option value="${district.id}"
									<c:if test="${district.id==customerVo.districtVo.id}">selected</c:if>>${district.name}</option>
							</c:forEach>
						</select>
					</div>
					<input type="hidden" name="hiddenVillageId" id="hiddenVillageId"
						value="${customerVo.villageVo.id}">
					<div class="form-group has-float-label">
						<label>Village</label> <select class="form-control" id="villageId"
							name="villageVo.id">
							<option value="">---Select---</option>
						</select>
					</div>
					<div class="form-group has-float-label">
						<label>Address</label>
						<textarea name="address" class="form-control" rows="2"
							id="address">${customerVo.address}</textarea>
					</div>
					<button type="submit" class="btn btn-light btn-sm" id="addCustomerButton">
						<c:if test="${customerVo.id == 0}">Add Customer</c:if>
						<c:if test="${customerVo.id != 0}">Update Customer</c:if>
					</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="card">
			<div class="card-header">Customer Table</div>
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
					<table class="table table-bordered" id="customerTable">
						<thead>
							<tr>
								<th scope="col">Customer No.</th>
								<th scope="col">Name</th>
								<th scope="col">Mobile Number</th>
								<th scope="col">District</th>
								<th scope="col">Village</th>
								<th scope="col">Address</th>
								<th scope="col">Created Date</th>
								<th scope="col">Created By</th>
								<th scope="col">Modified Date</th>
								<th scope="col">Modified By</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customer" items="${customers}">
								<tr>
									<th scope="row">${customer.id }</th>
									<td>${customer.name }</td>
									<td>${customer.mobileNumber }</td>
									<td>${customer.districtVo.name }</td>
									<td>${customer.villageVo.name }</td>
									<td>${customer.address }</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${customer.createdDate}" /></td>
									<td>${customer.createdBy}</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${customer.updatedDate}" /></td>
									<td>${customer.updatedBy}</td>
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
													href="/customer/update/${customer.id }"> <em
													class="fas fa-edit"></em>&nbsp;&nbsp;Update
												</a> <a class="dropdown-item deleteLink"
													href="/customer/delete/${customer.id }"> <em
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

<script src="/js/page/customer.js"></script>
<%@  include file="footer.jsp"%>
