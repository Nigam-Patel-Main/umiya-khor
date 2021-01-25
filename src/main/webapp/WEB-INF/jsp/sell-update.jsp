<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Update Sell</div>
			<div class="card-body">
				<form method="post" action="/sell/update" id="sellAddForm">
					<div class="form-group has-float-label mt-3">
						<label>Selling No</label> 
						<input type="text" name="id" value="${sellVo.id}" readonly class="form-control" style="font-size: 90%;">
					</div>
					
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
										<c:forEach var="sellItem" items="${sellVo.sellItemVos}">
											<div data-repeater-item class="productItem">
												<div class="d-flex flex-wrap">
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label">
															<label>Product</label> <select
																class="form-control productSelectBox" name="productVo.id">
																<option value="">---Select---</option>
																<c:forEach var="product" items="${products}">
																	<option value="${product.id}" <c:if test="${product.id==sellItem.productVo.id}">selected</c:if>>${product.name}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label ">
															<label>Price</label> <input type="text" name="price"
																class="form-control form-control-sm productPrice" value="${sellItem.price}">
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label">
															<label>Qty</label> <input type="text" name="qty"
																class="form-control form-control-sm productQty" value="${sellItem.qty}">
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label">
															<label>Amount</label> <input type="text"
																name="totalAmount"
																class="form-control form-control-sm productTotalAmount"
																readonly="readonly" value="${sellItem.totalAmount}">
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
										</c:forEach>
										
									</div>
									<div class="d-flex-inline align-items-center">
										<div class="d-inline-block flex fill">
											<p class="badge badge-light py-2">
												Total Product Amount : <span id="productAmountLable">${sellVo.productAmount}
												</span>
											</p>
											<input type="hidden" name="productAmount" 
												id="productAmount" value="${sellVo.productAmount}">

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
									
										<!-- if expense item not present then set default item  -->
										<c:if test="${ sellVo.expenseItemVos == null || fn:length(sellVo.expenseItemVos)== 0}">
											<div data-repeater-item class="expenseItem" >
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
										</c:if>
										<!-- set existing expense item -->
										<c:forEach var="expenseItem" items="${sellVo.expenseItemVos}">
											<div data-repeater-item class="expenseItem">
												<div class="d-flex flex-wrap">
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label">
															<label>Expense Category</label> <select
																class="form-control" name="expenseCategoryVo.id">
																<option value="">---Select---</option>
																<c:forEach var="expenseCategory"
																	items="${expenseCategories}">
																	<option value="${expenseCategory.id }" <c:if test="${expenseCategory.id==expenseItem.expenseCategoryVo.id}">selected</c:if>>${expenseCategory.name}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label ">
															<label>Description</label>
															<textarea rows="2" cols="2" name="description"
																class="form-control form-control-sm">${expenseItem.description}</textarea>
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label ">
															<label>Price</label> <input type="text" name="price"
																class="form-control form-control-sm expensePrice" value="${expenseItem.price}" >
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
										</c:forEach>
									</div>

									<div class="d-flex-inline align-items-center">
										<div class="d-inline-block flex fill">
											<p class="badge badge-light py-2">
												Total Expense Amount : <span id="expenseAmountLable">${sellVo.expenseAmount}
												</span>
											</p>
											<input type="hidden" name="expenseAmount" value="0.0"
												id="expenseAmount" value="${sellVo.expenseAmount}">

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
							Total Amount : <span id="totalAmountLable">${sellVo.totalAmount} </span>
						</p>
						<input type="hidden" name="totalAmount" value="0.0"
							id="totalAmount" value="${sellVo.totalAmount}">
					</div>
					<button type="submit" class="btn btn-primary btn-sm px-5"
						id="sellAddFormSubmitButton">Update</button>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="/js/page/sell-update-global-variable.js"></script>
<script type="text/javascript">
	/* set global variable */
	sellDate ='${sellVo.sellDate}';
	sellId ='${sellVo.id}';
	customerId ='${sellVo.customerVo.id}';
	totalExpenseItemCount = '${fn:length(sellVo.expenseItemVos)}';
</script>
<script src="/js/page/sell-validation.js"></script>
<script src="/js/page/sell-update.js"></script>
<%@  include file="footer.jsp"%>
