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
	$("#customerForm").validate({
	    rules: {
	      	name : {
	      		required: true,
	            remote: {
	                 url: "/customer/check/unique/name",
	                 type: "POST",
	                 data: {
	                	 customerId: function() {
	                     return $("#customerId").val();
	                    }
	                 }
	            }
	      	},
	      	mobileNumber:{
	      		required:true,
	      		regex :/^[0]?[789]\d{9}$/
	      	},
	      	"districtVo.id":"required",
	      	"villageVo.id":"required",
	      	"address":"required"
	      	
	      	
	    },
	    messages: {
	      name: {
	    	  remote: " Name is all ready exist"
	      }, mobileNumber: {
	    	  regex : "Mobile Number is invalid"
	      }
	    },
	    submitHandler: function(form) {
	      form.submit();
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
