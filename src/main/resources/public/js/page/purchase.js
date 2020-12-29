$(document).ready(function(){
	var today = moment(new Date()).format('DD-MM-YYYY')

	$("#purchaseDate").datepicker({
		yearRange : "2002:2012",
		dateFormat : 'dd-mm-yy',
		changeMonth : true,
		changeYear : true,
	});
	$("#purchaseDate").val(today);
	
	
	// accordian plus and minus button
	// Add minus icon for collapse element which is open by default
    $(".collapse.show").each(function(){
    	$(this).prev(".card-header").find(".fa").addClass("fa-minus").removeClass("fa-plus");
    });
    
    // Toggle plus minus icon on show hide of collapse element
    $(".collapse").on('show.bs.collapse', function(){
    	$(this).prev(".card-header").find(".fa").removeClass("fa-plus").addClass("fa-minus");
    }).on('hide.bs.collapse', function(){
    	$(this).prev(".card-header").find(".fa").removeClass("fa-minus").addClass("fa-plus");
    });
});
