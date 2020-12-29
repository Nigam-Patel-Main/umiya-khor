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
	if($("#shopId").val()){
		$("#districtId").trigger("change");
	}
	
	// validation
	// district form validation
	$('#shopForm').formValidation({
		framework : 'bootstrap',
		live : 'disabled',
		excluded : ":disabled",
		button : {
			selector : "#addShopButton",
			disabled : "disabled",
		},
		icon : null,
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : 'The shop name is Required'
					},
					remote : {
						message : 'This shop name is already exist',
						url : "/shop/check/unique/name",
						type : 'POST',
						data : function() {
							return {
								shopId : $("#shopId").val()
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
	var shopTable = $('#shopTable').DataTable({
		dom : 'rtp',
		order : []
	});
	$('#lengthChnageSelectBox').val(shopTable.page.len());

	$('#inputSearchField').keyup(function() {
		shopTable.search($(this).val()).draw();
	});
	$('#lengthChnageSelectBox').change(function() {
		shopTable.page.len($(this).val()).draw();
	});
});
