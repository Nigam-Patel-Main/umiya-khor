$(document).ready(function() {

	// district form validation
	$('#districtForm').formValidation({
		framework : 'bootstrap',
		live : 'disabled',
		excluded : ":disabled",
		button : {
			selector : "#addDistrictButton",
			disabled : "disabled",
		},
		icon : null,
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : 'The district is Required'
					},
					remote : {
						message : 'This district is already exist',
						url : "/location/district/check/unique/name",
						type : 'POST',
						data : function() {
							return {
								districtId : $("#districtHiddenId").val()
							};
						}
					}

				}
			}
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

	// village form validation
	$('#villageForm').formValidation({
		framework : 'bootstrap',
		live : 'disabled',
		excluded : ":disabled",
		button : {
			selector : "#addVillageButton",
			disabled : "disabled",
		},
		icon : null,
		fields : {
			"districtVo.id" : {
				validators : {
					notEmpty : {
						message : 'The district is Required'
					}
				}
			},
			name : {
				validators : {
					notEmpty : {
						message : 'The village is Required'
					},
					remote : {
						message : 'This village is already exist',
						url : "/location/village/check/unique/name",
						type : 'POST',
						data : function() {
							return {
								villageId : $("#villageHiddenId").val()
							};
						}
					}

				}
			}
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
