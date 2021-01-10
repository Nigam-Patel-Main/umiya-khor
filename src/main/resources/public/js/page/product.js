$(document).ready(function(){
	
	$("#productForm").validate({
	    rules: {
	      	name : {
	      		required: true,
	            remote: {
	                 url: "/product/check/unique/name",
	                 type: "POST",
	                 data: {
	                	 productId: function() {
	                     return $("#productId").val();
	                    }
	                 }
	            }
	      	},
	      	price:{
	      		required:true,
	      		regex :/^[0-9]\d*(\.\d+)?$/
	      	}
	    },
	    messages: {
	      name: {
	    	  remote: "Product Name is all ready exist"
	      }, price: {
	    	  regex : "Price is invalid"
	      }
	    },
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	
	//datatable
	// district data table
	var productTable = $('#productTable').DataTable({
		dom : 'rtp',
		order : []
	});
	$('#lengthChnageSelectBox').val(productTable.page.len());

	$('#inputSearchField').keyup(function() {
		productTable.search($(this).val()).draw();
	});
	$('#lengthChnageSelectBox').change(function() {
		productTable.page.len($(this).val()).draw();
	});
});
