<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  include file="header.jsp"%>
<div
	style="background-color: #f2f2f2; height: 100vh !important; overflow-y: auto">
	<div class="row my-auto">
		<div class="col-lg-4 col-sm-8 col-xl-4 ml-auto mr-auto">
			<div class="card mt-4 mx-2"
				style="background: #fff; border-radius: 10px; -webkit-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);">
				<div class="card-body  text-center">
					<div>
						<span
							style="display: block; font-family: Poppins; font-size: 27px; color: #333333; line-height: 1.2;">Welcome
							to</span>
					</div>
					<div class="mt-2">
						<h5 style="display: block; font-family: Poppins;  color: #333333; line-height: 1.2;">Umiya Khor</h5>

					</div>
					<div class="mt-3">
						<form method="post">
							<c:if test="${param.error == true}">
								<div class="alert alert-danger p-1" role="alert">Username
									and Password Incorrect !!!</div>
							</c:if>
							<c:if test="${param.logout==''}">
								<div class="alert alert-success p-1" role="alert">You are
									logged out.</div>
							</c:if>
							<div class="form-group">
								<input type="text" class="form-control" name="username"
									placeholder="Username">
							</div>
							<div class="form-group">
								<input type="password" class="form-control" name="password"
									placeholder="Password">
							</div>
							<input type="submit" class="btn btn-success btn-block"
								style="border-radius: 15px;"" value="Login">
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>




<%@  include file="footer.jsp"%>