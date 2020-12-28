<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Customer Form</div>
			<div class="card-body">
				<form action="/customer/add" method="post">
					<div class="form-group has-float-label mt-3">
						<label>Customer Number</label> <input type="text" name="id"
							class="form-control" id="id">
					</div>
					<div class="form-group has-float-label mt-3">
						<label>Name</label> <input type="text" name="name"
							class="form-control" id="name">
					</div>
					<div class="form-group has-float-label mt-3">
						<label>Mobile Number</label> <input type="text"
							name="mobileNumber" class="form-control" id="mobileNumber">
					</div>

					<div class="form-group has-float-label">
						<label>District</label> <select class="form-control"
							id="districtId" name="districtVo.id">
							<option value="">---Hello---</option>
							<c:forEach var="district" items="${districts}">
								<option value="${district.id}">
									<%-- <c:if test="${material.materialId==orderVo.materialVo.materialId}">selected</c:if>>${material.materialName}</option> --%>
								<option value="${district.id}">${district.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group has-float-label">
						<label>Village</label> <select class="form-control" id="villageId"
							name="villageVo.id">
							<option value="">---Select---</option>
						</select>
					</div>
					<div class="form-group has-float-label">
						<label>Address</label>
						<textarea name="address" class="form-control" rows="2"
							id="address"></textarea>
					</div>
					<button type="submit" class="btn btn-light btn-sm">Add
						Product</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="card">
			<div class="card-header">Customer Table</div>
			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr >
								<th>Customer Number</th>
								<th>Name</th>
								<th>Mobile Number</th>
								<th>District</th>
								<th>Village</th>
								<th>Address</th>
								<th>Created Date</th>
								<th>Created By</th>
								<th>Modified By</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row">1</th>
								<td>Mark</td>
								<td>Otto</td>
								<td>7575046524</td>
								<td>Surendranagar</td>
								<td>Khodu</td>
								<td>@mdo</td>
								<td>@mdo</td>
								<td>@mdo</td>
								<td>

									<div class="dropdown">
										<!--Trigger-->
										<a type="button" id="dropdownMenu2" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false" class="btn btn-default btn-action"><i
											class="fas fa-ellipsis-v  px-1"></i></a>


										<!--Menu-->
										<div class="dropdown-menu dropdown-primary">
											<a class="dropdown-item" href="javascript:void(0)"> <i
												class="fas fa-edit"></i>&nbsp;&nbsp;Update
											</a>
											<a class="dropdown-item" href="javascript:void(0)"> <i
												class="fas fa-trash"></i>&nbsp;&nbsp;Delete
											</a>
										</div>
									</div>

								</td>
							</tr>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="/js/page/customer.js"></script>
<%@  include file="footer.jsp"%>
