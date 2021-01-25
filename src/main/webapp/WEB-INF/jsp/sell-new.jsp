<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Selling</div>
			<div class="card-body">
				<form method="post" action="/sell/add" id="sellAddForm">
					<div class="form-group has-float-label mt-3">
						<label>Sell Date</label> <input type="text"
							name="sellDate" class="form-control" id="sellDate"
							autocomplete="off" style="font-size: 90%;">
					</div>
					<div class="form-group has-float-label">
						<label>Customer</label> <select class="form-control" id="customerId"
							name="customerVo.id">
							<option value="">---Select---</option>
							<c:forEach var="customer" items="${customers}">
								<option value="${customer.id }">${customer.name}</option>
							</c:forEach>
						</select>
					</div>
					<div id="accordion" class="mb-4">
						<div class="card mb-3">
							<div class="card-header py-2">
								<div class="d-flex align-items-center">
									<h7 class="mr-auto">Products</h7>
									<a class="card-link" data-toggle="collapse" href="#collapseOne">

									</a>
								</div>
							</div>
							<div id="collapseOne" class="collapse show">
								<div class="card-body product-repeater">
									<div data-repeater-list="sellItemVos">
										<div data-repeater-item class="productItem">
											<div class="d-flex flex-wrap">
												<div class="flex-fill mr-2">
													<div class="form-group has-float-label">
														<label>Product</label> <select
															class="form-control productSelectBox" name="productVo.id">
															<option value="">---Select---</option>
															<c:forEach var="product" items="${products}">
																<option value="${product.id }">${product.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="flex-fill mr-2">
													<div class="form-group has-float-label ">
														<label>Price</label> <input type="text" name="price"
															class="form-control form-control-sm productPrice">
													</div>
												</div>
												<div class="flex-fill mr-2">
													<div class="form-group has-float-label">
														<label>Qty</label> <input type="text" name="qty"
															class="form-control form-control-sm productQty">
													</div>
												</div>
												<div class="flex-fill mr-2">
													<div class="form-group has-float-label">
														<label>Amount</label> <input type="text"
															name="totalAmount"
															class="form-control form-control-sm productTotalAmount"
															readonly="readonly">
													</div>
												</div>
												<div class="flex-fill mr-2">
													<div class="ml-2" style="margin-bottom: 1rem">
														<input data-repeater-delete type="button"
															class="btn btn-light btn-sm mb-1" value="Delete" />
													</div>

												</div>
											</div>
											<hr>


										</div>
									</div>
									<div class="d-flex-inline align-items-center">
										<div class="d-inline-block flex fill">
											<p class="badge badge-light py-2">
												Total Product Amount : <span id="productAmountLable">0.0
												</span>
											</p>
											<input type="hidden" name="productAmount" value="0.0"
												id="productAmount">

										</div>
										<div class="d-inline-block flex fill mb-2">

											<input data-repeater-create type="button"
												class="btn btn-light btn-sm" value="Add Product" />
										</div>
									</div>

								</div>
							</div>
						</div>

						<div class="card mb-3">
							<div class="card-header py-2">
								<div class="d-flex align-items-center">
									<h7 class="mr-auto">Expense</h7>
									<a class="collapsed card-link" data-toggle="collapse"
										href="#collapseTwo"><i class="fa fa-plus"></i> </a>
								</div>
							</div>
							<div id="collapseTwo" class="collapse">
								<div class="card-body expense-repeater">
									<div data-repeater-list="expenseItemVos">
										<div data-repeater-item class="expenseItem">
											<div class="d-flex flex-wrap">
												<div class="flex-fill mr-2">
													<div class="form-group has-float-label">
														<label>Expense Category</label> <select
															class="form-control" name="expenseCategoryVo.id">
															<option value="">---Select---</option>
															<c:forEach var="expenseCategory"
																items="${expenseCategories}">
																<option value="${expenseCategory.id }">${expenseCategory.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="flex-fill mr-2">
													<div class="form-group has-float-label ">
														<label>Description</label>
														<textarea rows="2" cols="1" name="description"
															class="form-control form-control-sm"></textarea>
													</div>
												</div>
												<div class="flex-fill mr-2">
													<div class="form-group has-float-label ">
														<label>Price</label> <input type="text" name="price"
															class="form-control form-control-sm expensePrice">
													</div>
												</div>
												<div class="flex-fill">
													<div class="" style="margin-bottom: 1rem">
														<input data-repeater-delete type="button"
															class="btn btn-light btn-sm mb-1" value="Delete" />
													</div>

												</div>
											</div>
											<hr>


										</div>
									</div>

									<div class="d-flex-inline align-items-center">
										<div class="d-inline-block flex fill">
											<p class="badge badge-light py-2">
												Total Expense Amount : <span id="expenseAmountLable">0.0
												</span>
											</p>
											<input type="hidden" name="expenseAmount" value="0.0"
												id="expenseAmount">

										</div>
										<div class="d-inline-block flex fill mb-2">
											<input data-repeater-create type="button"
												class="btn btn-light btn-sm" value="Add Expense" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div>
						<p class="badge badge-light py-2">
							Total Amount : <span id="totalAmountLable">0.0 </span>
						</p>
						<input type="hidden" name="totalAmount" value="0.0"
							id="totalAmount">
					</div>
					<button type="submit" class="btn btn-primary btn-sm px-5"
						id="sellAddFormSubmitButton">Submit</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="card">
			<div class="card-header">Sell Table</div>
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
					<table class="table table-bordered" id="sellTable">
						<thead>
							<tr>
								<th scope="col">Order No</th>
								<th scope="col">Customer Name</th>
								<th scope="col">Order Date</th>
								<th scope="col">Product Amount</th>
								<th scope="col">Expense Amount</th>
								<th scope="col">Total Amount</th>
								<!-- <th scope="col">Created By</th>
								<th scope="col">Modified Date</th>
								<th scope="col">Modified By</th> -->
								<th>Created Date</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="sellsOrder" items="${sellsOrders}">
								<tr>
									<td>${sellsOrder.id}</td>
									<td>${sellsOrder.customerVo.name}</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy"
											value="${sellsOrder.sellDate}" /></td>
									<td>${sellsOrder.productAmount}</td>
									<td>${sellsOrder.expenseAmount}</td>
									<td>${sellsOrder.totalAmount}</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy h:m a"
											value="${sellsOrder.createdDate}" /></td>
									<td>
										<div class="dropdown">
											<!--Trigger-->
											<a type="button" id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"
												class="btn btn-default btn-action"><em
												class="fas fa-ellipsis-v  "></em></a>


											<!--Menu-->
											<div class="dropdown-menu dropdown-primary">
												<a class="dropdown-item" href="javascript:void(0)"
													onclick="onView(${sellsOrder.id})"> <em
													class="fas fa-eye "></em>&nbsp;&nbsp;View
												</a> <a class="dropdown-item"
													href="/sell/update/${sellsOrder.id}"> <em
													class="fas fa-edit"></em>&nbsp;&nbsp;Update
												</a> <a class="dropdown-item deleteLink"
													href="/sell/delete/${sellsOrder.id}"> <em
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

<!-- modal for view sell order -->
<div class="modal" tabindex="-1" role="dialog" id="sellOrderModal">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Sell Order</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<th scope="row">Order No</th>
								<td id="POMOrderNumber"></td>
							</tr>
							<tr>
								<th scope="row">Order Date</th>
								<td id="POMOrderDate"></td>
							</tr>
							<tr>
								<th scope="row">Customer Name</th>
								<td id="POMCustomerName"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<h7>Product Detail</h7><hr>
				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th scope="row">Product Name</th>
								<th scope="row">Qty</th>
								<th scope="row">Price</th>
								<th scope="row">Total Price</th>
							</tr>
						</thead>
						<tbody id="POMProducts">
								
						</tbody>
					</table>
				</div>
				<h7>Expense Detail</h7><hr>
				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th scope="row">Expense Type</th>
								<th scope="row">Description</th>
								<th scope="row">Amount</th>
							</tr>
						</thead>
						<tbody id="POMExpenses">
								
						</tbody>
					</table>
				</div>
				<h7>Order Summary</h7><hr>
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<th scope="row">Product Amount</th>
								<td id="POMProductAmount"></td>
							</tr>
							<tr>
								<th scope="row">Expense Amount</th>
								<td id="POMExpenseAmount"></td>
							</tr>
							<tr>
								<th scope="row">Total Amount</th>
								<td id="POMTotalAmount"></td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<h7>Order Log</h7><hr>
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<th scope="row">Created Date</th>
								<td id="POMCreatedDate"></td>
							</tr>
							<tr>
								<th scope="row">Created By</th>
								<td id="POMCreatedBy"></td>
							</tr>
							<tr>
								<th scope="row">Updated Date</th>
								<td id="POMUpdatedDate"></td>
							</tr>
							<tr>
								<th scope="row">Updated By</th>
								<td id="POMUpdatedBy"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="/js/page/sell-validation.js"></script>
<script src="/js/page/sell-new.js"></script>
<%@  include file="footer.jsp"%>
