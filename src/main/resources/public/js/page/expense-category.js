$(document).ready(function(){
	$("#expenseCategoryForm").validate({
	    rules: {
	      	name : {
	      		required: true,
	            remote: {
	                 url: "/expenseCategory/check/unique/name",
	                 type: "POST",
	                 data: {
	                	 expenseCategoryId: function() {
	                     return $("#expenseCategoryId").val();
	                    }
	                 }
	            }
	      	}
	    },
	    messages: {
	      name: {
	    	  remote: "This expense category name is all ready exist"
	      }
	    },
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	
	//datatable
	// district data table
	var expenseTable = $('#expenseTable').DataTable({
		dom : 'rtp',
		order : []
	});
	$('#lengthChnageSelectBox').val(expenseTable.page.len());

	$('#inputSearchField').keyup(function() {
		expenseTable.search($(this).val()).draw();
	});
	$('#lengthChnageSelectBox').change(function() {
		expenseTable.page.len($(this).val()).draw();
	});
});
