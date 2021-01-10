

$(document).ready(function(){
	$.fn.dataTable.ext.classes.sPageButton = ' btn-sm px-0 ';
	$.fn.dataTable.ext.classes.sPageButtonActive = 'active';
	$.validator.addMethod(
	        "regex",
	        function(value, element, regexp) {
	            if (regexp.constructor != RegExp)
	                regexp = new RegExp(regexp);
	            else if (regexp.global)
	                regexp.lastIndex = 0;
	            return this.optional(element) || regexp.test(value);
	        },
	        "Please check your input."
	);
	
});
