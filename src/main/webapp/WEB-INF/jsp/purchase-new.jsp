<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Purchase Form</div>
			<div class="card-body">
				<form method="post" action="/purchase/add" id="purchaseAddForm">
					<div class="form-group has-float-label mt-3">
						<label>Purchase Date</label> <input type="text"
							name="purchaseDate" class="form-control" id="purchaseDate"
							autocomplete="off" style="font-size: 90%;">
					</div>
					<div class="form-group has-float-label">
						<label>Shop</label> <select class="form-control" id="shopId"
							name="shopVo.id">
							<option value="">---Select---</option>
							<c:forEach var="shop" items="${shops}">
								<option value="${shop.id }">${shop.name}</option>
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
									<div data-repeater-list="purchaseItemVos">
										<div data-repeater-item class="productItem">
											<div class="d-flex flex-wrap align-content-start">
												<div class="flex fill mr-2">
													<div class="form-group has-float-label">
														<label>Product</label> <select class="form-control"
															name="productVo.id">
															<option value="">---Select---</option>
															<c:forEach var="product" items="${products}">
																<option value="${product.id }">${product.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="flex fill mr-2">
													<div class="form-group has-float-label ">
														<label>Price</label> <input type="text" name="price"
															class="form-control form-control-sm productPrice">
													</div>
												</div>
												<div class="flex fill mr-2">
													<div class="form-group has-float-label">
														<label>Qty</label> <input type="text" name="qty"
															class="form-control form-control-sm productQty">
													</div>
												</div>
												<div class="flex fill mr-2">
													<div class="form-group has-float-label">
														<label>Amount</label> <input type="text"
															name="totalAmount" class="form-control form-control-sm productTotalAmount" readonly="readonly">
													</div>
												</div>
												<div class="flex fill mr-2">
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
												Total Product Amount : <span id="productAmountLable">0.0 </span>
											</p>
											<input type="hidden" name="productAmount" value="0.0" id="productAmount">

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
											<div class="d-flex-inline align-items-center">
												<div class="d-inline-block flex fill">
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
												<div class="d-inline-block flex fill">
													<div class="form-group has-float-label ">
														<label>Description</label> <input type="text"
															name="discription" class="form-control form-control-sm">
													</div>
												</div>
												<div class="d-inline-block flex fill">
													<div class="form-group has-float-label ">
														<label>Price</label> <input type="text" name="price"
															class="form-control form-control-sm expensePrice">
													</div>
												</div>
												<div class="d-inline-block flex fill">
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
												Total Expense Amount : <span id="expenseAmountLable">0.0 </span>
											</p>
											<input type="hidden" name="expenseAmount" value="0.0" id="expenseAmount">

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
						<input type="hidden" name="totalAmount" value="0.0" id="totalAmount">
					</div>
					<button type="submit" class="btn btn-primary btn-sm px-5" id="purchaseAddFormSubmitButton">
						Submit</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="card">
			<div class="card-header">Purchase Table</div>
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
					<table class="table table-bordered" id="expenseTable">
						<thead>
							<tr>
								<th scope="col">Expense No.</th>
								<th scope="col">Name</th>
								<th scope="col">Created Date</th>
								<th scope="col">Created By</th>
								<th scope="col">Modified Date</th>
								<th scope="col">Modified By</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="/js/page/purchase-new-validation.js"></script>
<script src="/js/page/purchase-new.js"></script>
<%@  include file="footer.jsp"%>
