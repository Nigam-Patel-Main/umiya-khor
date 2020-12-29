<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  include file="navbar.jsp"%>
<div class="row">
	<div class="col-lg-6">
		<div class="card mb-4">
			<div class="card-header">Purchase Form</div>
			<div class="card-body">
				<form method="post">
					<div class="form-group has-float-label mt-3">
						<label>Purchase Date</label> <input type="text"
							name="orderDateString" class="form-control" id="purchaseDate"
							autocomplete="off" style="font-size: 90%;">
					</div>
					<div class="form-group has-float-label">
						<label>Shop</label> <select class="form-control" id="shopId">
							<option value="">---Select---</option>
						</select>
					</div>
					<div id="accordion" class="mb-4">
						<div class="card mb-3">
							<div class="card-header py-2">
								<div class="d-flex align-items-center">
									<h7 class="mr-auto">Products</h7>
									<a class="card-link" data-toggle="collapse" href="#collapseOne">
										<i class="fa fa-plus"></i>
									</a>
								</div>

							</div>
							<div id="collapseOne" class="collapse show">
								<div class="card-body">Lorem ipsum..</div>
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
								<div class="card-body">Lorem ipsum..</div>
							</div>
						</div>

						<div class="card mb-3">
							<div class="card-header py-2">
								<div class="d-flex align-items-center">
									<h7 class="mr-auto">Payment</h7>
									<a class="collapsed card-link" data-toggle="collapse"
									href="#collapseThree"><i class="fa fa-plus"></i> </a>
								</div>
								
							</div>
							<div id="collapseThree" class="collapse">
								<div class="card-body">Lorem ipsum..</div>
							</div>
						</div>

					</div>
					<button type="submit" class="btn btn-light btn-sm">
						Purchase</button>
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

<script src="/js/page/purchase.js"></script>
<%@  include file="footer.jsp"%>
