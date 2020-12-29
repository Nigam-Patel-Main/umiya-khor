<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row mb-4">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">District Form</div>
			<div class="card-body">
				<form
					<c:if test="${districtVo.id == 0}">action="/location/district/add"</c:if>
					<c:if test="${districtVo.id != 0}">action="/location/district/update"</c:if>
					method="post" id="districtForm">
					<input type="hidden" name="id" value="${districtVo.id}"
						id="districtHiddenId">
					<div class="form-group has-float-label mt-3">
						<label>District Name</label> <input type="text" name="name"
							class="form-control" id="districtName" value="${districtVo.name}">
					</div>
					<button type="submit" class="btn btn-light btn-sm"
						id="addDistrictButton">
						<c:if test="${districtVo.id == 0}">Add District</c:if>
						<c:if test="${districtVo.id != 0}">Update District</c:if>
					</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="card">
			<div class="card-header">District Table</div>
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
					<table class="table table-bordered" id="districtTable">
						<thead>
							<tr>
								<th scope="col">Name</th>
								<th scope="col">Created Date</th>
								<th scope="col">Created By</th>
								<th scope="col">Modified Date</th>
								<th scope="col">Modified By</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="district" items="${districts}">
								<tr>
									<td>${district.name }</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${district.createdDate}" /></td>
									<td>${district.createdBy}</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${district.updatedDate}" /></td>
									<td>${district.updatedBy}</td>
									<td>

										<div class="dropdown">
											<!--Trigger-->
											<a type="button" id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"
												class="btn btn-default btn-action"><em
												class="fas fa-ellipsis-v  "></em></a>


											<!--Menu-->
											<div class="dropdown-menu dropdown-primary">
												<a class="dropdown-item"
													href="/location/district/update/${district.id}"> <em
													class="fas fa-edit"></em>&nbsp;&nbsp;Update
												</a> <a class="dropdown-item deleteLink"
													href="/location/district/delete/${district.id}"> <em
													class="fas fa-trash "></em>&nbsp;&nbsp;Delete
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
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Village Form</div>
			<div class="card-body">
				<form
					<c:if test="${villageVo.id == 0}">action="/location/village/add"</c:if>
					<c:if test="${villageVo.id != 0}">action="/location/village/update"</c:if>
					method="post" id="villageForm">
					<input type="hidden" name="id" value="${villageVo.id}"
						id="villageHiddenId">
					<div class="form-group has-float-label">
						<label>District</label> <select class="form-control"
							id="selectDistrictId" name="districtVo.id">
							<option value="">---Select---</option>
							<c:forEach var="district" items="${districts}">
								<option value="${district.id}"
									<c:if
										test="${district.id==villageVo.districtVo.id}">selected</c:if>>${district.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group has-float-label mt-3">
						<label>Village Name</label> <input type="text" name="name"
							class="form-control" id="villageName" value="${villageVo.name}">
					</div>
					<button type="submit" class="btn btn-light btn-sm"
						id="addVillageButton">
						<c:if test="${villageVo.id == 0}">Add Village</c:if>
						<c:if test="${villageVo.id != 0}">Update District</c:if>
					</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="card">
			<div class="card-header">Village Table</div>
			<div class="card-body">
				<div class="d-flex flex-row-reverse">
					<div class="p-2 ">
						<div class="">
							<div class="input-group input-group-sm	">
								<input type="text" class="form-control" placeholder="Search..."
									id="inputSearchField2">
								<div class="input-group-append">
									<span class="input-group-text"><i class="fas fa-search"></i></span>
								</div>
							</div>
						</div>
					</div>
					<div class="p-2 ">
						<div class="mr-3">
							<select class="form-control form-control-sm ml-3"
								id="lengthChnageSelectBox2">
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
					<table class="table table-bordered" id="villageTable">
						<thead>
							<tr>
								<th scope="col">Village</th>
								<th scope="col">District</th>
								<th scope="col">Created Date</th>
								<th scope="col">Created By</th>
								<th scope="col">Modified Date</th>
								<th scope="col">Modified By</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="village" items="${villages}">
								<tr>
									<td>${village.name }</td>
									<td>${village.districtVo.name }</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${village.createdDate}" /></td>
									<td>${village.createdBy}</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${village.updatedDate}" /></td>
									<td>${village.updatedBy}</td>
									<td>

										<div class="dropdown">
											<!--Trigger-->
											<a type="button" id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"
												class="btn btn-default btn-action"><em
												class="fas fa-ellipsis-v  "></em></a>


											<!--Menu-->
											<div class="dropdown-menu dropdown-primary">
												<a class="dropdown-item"
													href="/location/village/update/${village.id}"> <em
													class="fas fa-edit"></em>&nbsp;&nbsp;Update
												</a> <a class="dropdown-item deleteLink"
													href="/location/village/delete/${village.id}"> <em
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

<script src="/js/page/location.js"></script>
<%@  include file="footer.jsp"%>
