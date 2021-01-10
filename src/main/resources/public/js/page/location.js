$(document).ready(function() {

	
	$("#districtForm").validate({
	    rules: {
	      	name : {
	      		required: true,
	            remote: {
	                 url: "/location/district/check/unique/name",
	                 type: "POST",
	                 data: {
	                	 districtId: function() {
	                     return $("#districtHiddenId").val();
	                    }
	                 }
	            }
	      	}
	    },
	    messages: {
	      name: {
	    	  remote: "This District is all ready exist"
	      }
	    },
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });

	// district data table
	var districtTable = $('#districtTable').DataTable({
		dom : 'rtp',
		order : []
	});
	$('#length_change').val(districtTable.page.len());

	$('#inputSearchField').keyup(function() {
		districtTable.search($(this).val()).draw();
	});
	$('#lengthChnageSelectBox').change(function() {
		districtTable.page.len($(this).val()).draw();
	});

	$("#villageForm").validate({
	    rules: {
	    	"districtVo.id":"required",
	      	name : {
	      		required: true,
	            remote: {
	                 url: "/location/village/check/unique/name",
	                 type: "POST",
	                 data: {
	                	 villageId: function() {
	                     return $("#villageHiddenId").val();
	                    }
	                 }
	            }
	      	}
	    },
	    messages: {
	      name: {
	    	  remote: "This Village is all ready exist"
	      }
	    },
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });

	// village data table
	var villageTable = $('#villageTable').DataTable({
		dom : 'rtp',
		order : []
	});
	$('#lengthChnageSelectBox2').val(villageTable.page.len());

	$('#inputSearchField2').keyup(function() {
		villageTable.search($(this).val()).draw();
	});
	$('#lengthChnageSelectBox2').change(function() {
		villageTable.page.len($(this).val()).draw();
	});

	

});
