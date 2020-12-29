$(document).ready(function(){
	
	// validation
	// district form validation
	$('#productForm').formValidation({
		framework : 'bootstrap',
		live : 'disabled',
		excluded : ":disabled",
		button : {
			selector : "#addProductButton",
			disabled : "disabled",
		},
		icon : null,
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : 'The product name is Required'
					},
					remote : {
						message : 'This product name is already exist',
						url : "/product/check/unique/name",
						type : 'POST',
						data : function() {
							return {
								productId : $("#productId").val()
							};
						}
					}

				}
			},price : {
				validators : {
					notEmpty : {
						message : 'The price is Required'
					},
                    regexp: {
                        regexp: /^[0-9]\d*(\.\d+)?$/,
                        message: 'The price can only consist of number'
                    }
				}
			}
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
