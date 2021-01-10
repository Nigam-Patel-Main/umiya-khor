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
	
	$("#shopForm").validate({
	    rules: {
	      	name : {
	      		required: true,
	            remote: {
	                 url: "/shop/check/unique/name",
	                 type: "POST",
	                 data: {
	                	 shopId: function() {
	                     return $("#shopId").val();
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
	    	  remote: " Shop name is all ready exist"
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
