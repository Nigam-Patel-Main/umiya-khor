$(document).ready(function(){
	
	
	
	
	
	
	
	// validation
	// district form validation
	$('#expenseForm').formValidation({
		framework : 'bootstrap',
		live : 'disabled',
		excluded : ":disabled",
		button : {
			selector : "#addExpenseButton",
			disabled : "disabled",
		},
		icon : null,
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : 'The expense name is Required'
					},
					remote : {
						message : 'This expense name is already exist',
						url : "/expense/check/unique/name",
						type : 'POST',
						data : function() {
							return {
								expenseId : $("#expenseId").val()
							};
						}
					}

				}
			}
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
