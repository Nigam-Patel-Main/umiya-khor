$(document).ready(function(){
	
	var isFirstTimeChnageDistrict = 0;
	
	$("#districtId").change(function(){
		
		// increment logic only for edit mode in district change event
		isFirstTimeChnageDistrict +=1;
		
		var content="<option value=''>---Select---</option>";
		var districtId = $(this).val();
		if(districtId){
			$.get("/location/getVillageByDistrictId/"+districtId,function(villages){
				for(village of villages){
					content += "<option value='"+village.id+"'>"+village.name+"</option>";
				}
				$("#villageId").html(content);
				
				
				// set village on edit mode
				if(isFirstTimeChnageDistrict == 1){
					var villageId = $("#hiddenVillageId").val();
					$("#villageId").val(villageId).change();
				}
			});
		}else {
			$("#villageId").html(content);
		}
		
	});
	
	// set village on edit mode
	if($("#customerId").val()){
		$("#districtId").trigger("change");
	}
	
	// validation
	// district form validation
	$('#customerForm').formValidation({
		framework : 'bootstrap',
		live : 'disabled',
		excluded : ":disabled",
		button : {
			selector : "#addCustomerButton",
			disabled : "disabled",
		},
		icon : null,
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : 'The customer name is Required'
					},
					remote : {
						message : 'This customer name is already exist',
						url : "/customer/check/unique/name",
						type : 'POST',
						data : function() {
							return {
								customerId : $("#customerId").val()
							};
						}
					}

				}
			},mobileNumber : {
				validators : {
					notEmpty : {
						message : 'The mobile number  is Required'
					},regexp: {
                        regexp: /^[0]?[789]\d{9}$/,
                        message: 'The mobile number invalid'
					}
				}
			},"districtVo.id" : {
				validators : {
					notEmpty : {
						message : 'The district is Required'
					}
				}
			},"villageVo.id" : {
				validators : {
					notEmpty : {
						message : 'The village is Required'
					}
				}
			},address : {
				validators : {
					notEmpty : {
						message : 'The adddress is Required'
					}
				}
			}
		}
	});
	
	//datatable
	// district data table
	var customerTable = $('#customerTable').DataTable({
		dom : 'rtp',
		order : []
	});
	$('#lengthChnageSelectBox').val(customerTable.page.len());

	$('#inputSearchField').keyup(function() {
		customerTable.search($(this).val()).draw();
	});
	$('#lengthChnageSelectBox').change(function() {
		customerTable.page.len($(this).val()).draw();
	});
});
