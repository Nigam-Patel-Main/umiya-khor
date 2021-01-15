<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Update Purchase Order</div>
			<div class="card-body">
				<form method="post" action="/purchase/update" id="purchaseAddForm">
					<div class="form-group has-float-label mt-3">
						<label>Purchase No</label> 
						<input type="text" name="id" value="${purchaseVo.id}" readonly class="form-control" style="font-size: 90%;">
					</div>
					
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
										<c:forEach var="purchaseItem" items="${purchaseVo.purchaseItemVos}">
											<div data-repeater-item class="productItem">
												<div class="d-flex flex-wrap">
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label">
															<label>Product</label> <select
																class="form-control productSelectBox" name="productVo.id">
																<option value="">---Select---</option>
																<c:forEach var="product" items="${products}">
																	<option value="${product.id}" <c:if test="${product.id==purchaseItem.productVo.id}">selected</c:if>>${product.name}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label ">
															<label>Price</label> <input type="text" name="price"
																class="form-control form-control-sm productPrice" value="${purchaseItem.price}">
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label">
															<label>Qty</label> <input type="text" name="qty"
																class="form-control form-control-sm productQty" value="${purchaseItem.qty}">
														</div>
													</div>
													<div class="flex-fill mr-2">
														<div class="form-group has-float-label">
															<label>Amount</label> <input type="text"
																name="totalAmount"
																class="form-control form-control-sm productTotalAmount"
																readonly="readonly" value="${purchaseItem.totalAmount}">
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
												Total Product Amount : <span id="productAmountLable">${purchaseVo.productAmount}
												</span>
											</p>
											<input type="hidden" name="productAmount" 
												id="productAmount" value="${purchaseVo.productAmount}">

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
										<c:if test="${ purchaseVo.expenseItemVos == null || fn:length(purchaseVo.expenseItemVos)== 0}">
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
										<c:forEach var="expenseItem" items="${purchaseVo.expenseItemVos}">
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
												Total Expense Amount : <span id="expenseAmountLable">${purchaseVo.expenseAmount}
												</span>
											</p>
											<input type="hidden" name="expenseAmount" value="0.0"
												id="expenseAmount" value="${purchaseVo.expenseAmount}">

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
							Total Amount : <span id="totalAmountLable">${purchaseVo.totalAmount} </span>
						</p>
						<input type="hidden" name="totalAmount" value="0.0"
							id="totalAmount" value="${purchaseVo.totalAmount}">
					</div>
					<button type="submit" class="btn btn-primary btn-sm px-5"
						id="purchaseAddFormSubmitButton">Update</button>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="/js/page/purchase-update-global-variable.js"></script>
<script type="text/javascript">
	/* set global variable */
	purchaseDate ='${purchaseVo.purchaseDate}';
	purchaseId ='${purchaseVo.id}';
	shopId ='${purchaseVo.shopVo.id}';
	totalExpenseItemCount = '${fn:length(purchaseVo.expenseItemVos)}';
</script>
<script src="/js/page/purchase-validation.js"></script>
<script src="/js/page/purchase-update.js"></script>
<%@  include file="footer.jsp"%>
