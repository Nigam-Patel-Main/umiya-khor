$(document).ready(
    function() {
    	
    	/* datatable initialization */

    	var sellTable = $('#sellTable').DataTable({
    		dom : 'rtp',
    		order : []
    	});
    	$('#length_change').val(sellTable.page.len());

    	$('#inputSearchField').keyup(function() {
    		sellTable.search($(this).val()).draw();
    	});
    	$('#lengthChnageSelectBox').change(function() {
    		sellTable.page.len($(this).val()).draw();
    	});
    	
    	/*date picker for sell date*/
    	var today = moment(new Date()).format('DD/MM/YYYY')
        $("#sellDate").datepicker({
            yearRange: "2002:2012",
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
        });
        $("#sellDate").val(today);

        // accordian plus and minus button
        // Add minus icon for collapse element which is open by default
        $(".collapse.show").each(
            function() {
                $(this).prev(".card-header").find(".fa").addClass(
                    "fa-trash").removeClass("fa-plus");
            });

        // Toggle plus minus icon on show hide of collapse element
        $(".collapse").on(
            'show.bs.collapse',
            function() {
                $(this).prev(".card-header").find(".fa").removeClass(
                    "fa-plus").addClass("fa-trash");
            }).on(
            'hide.bs.collapse',
            function() {
            	removeAllExpenseRepeater();
                $(this).prev(".card-header").find(".fa").removeClass(
                    "fa-trash").addClass("fa-plus");
            });

        // product repeater
        $('.product-repeater').repeater({
            initEmpty: false,
            show: function() {
                addProductValidation($(this).index());
            	updateAllTotalAmount();
                $(this).slideDown();
            },
            hide: function(deleteElement) {
            	removeProductValidation($(this).index());
            	$(this).slideUp(deleteElement);
                setTimeout(function() {
                    updateAllTotalAmount();
                }, 700)
            },
            isFirstItemUndeletable: true
        })

        // expense repeater
        $('.expense-repeater').repeater({
            initEmpty: true,
            show: function() {
            	addExpenseValidation($(this).index());
                updateAllTotalAmount();
                $(this).slideDown();
            },
            hide: function(deleteElement) {
            	removeExpenseValidation($(this).index())
                $(this).slideUp(deleteElement);
                setTimeout(function() {
                    updateAllTotalAmount();
                }, 700)
            },
            isFirstItemUndeletable: true
        })

        // set total product amount label
        $("#productAmount").change(function() {
            $("#productAmountLable").html($(this).val());
        });

        // set total amount expense label
        $("#expenseAmount").change(function() {
            $("#expenseAmountLable").html($(this).val());
        });

        // set Grand total amount label
        $("#totalAmount").change(function() {
            $("#totalAmountLable").html($(this).val());
        });

        $(document).on("change", ".productQty,.productPrice,.expensePrice", function() {
            updateAllTotalAmount();
        });

        // update all total amount to 0.00 on page load 
        updateAllTotalAmount();


        $("#sellAddForm").validate({
            rules: {
                "sellDate": {
                    required: true,
                    regex:/^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/
                },
                "customerVo.id":"required"
            },
            messages: {
                sellDate: {
                    regex: "date is invalid"
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });

        /*add validation for allready exis one product repeater item*/ 
        addProductValidation(0);
        
        /*get price of product on change of select box*/
        $(document).on("change",".productSelectBox",function(){
        	var productId = $(this).val();
        	if(productId){
        		var that =this;
        		$.get("/product/price/"+productId,function(price){
        			$(that).closest("div[data-repeater-item]").find(".productPrice").val(price);
        		})
        	}else{
        		$(this).closest("div[data-repeater-item]").find(".productPrice").val(0.00);
    		}
        });
        
    });

/*view all sell order detail*/
function onView(sellId){
	
	if(sellId){
		$.get("/sell/"+sellId,function(order){
			$("#POMOrderNumber").html(order.id);
			$("#POMOrderDate").html(moment(order.sellDate).format("DD/MM/YYYY"));
			$("#POMCustomerName").html(order.customerVo.name);
			
			/*prepare product body for view in table */
			var productTableBody="";
			if(order.sellItemVos){
				for(var sellItem of order.sellItemVos) {
					productTableBody+="<tr>";
					productTableBody+="<td>"+sellItem?.productVo.name+"</td>";
					productTableBody+="<td>"+sellItem?.qty+"</td>";
					productTableBody+="<td>"+sellItem?.price+"</td>";
					productTableBody+="<td>"+sellItem?.totalAmount+"</td>";
					productTableBody+="</tr>";
				}
			}
			$("#POMProducts").html(productTableBody);
			
			/*prepare expense body for view in table */
			var expenseTableBody="";
			if(order.sellItemVos){
				for(var expenseItem of order.expenseItemVos) {
					expenseTableBody+="<tr>";
					expenseTableBody+="<td>"+expenseItem?.expenseCategoryVo.name+"</td>";
					expenseTableBody+="<td>"+expenseItem?.description+"</td>";
					expenseTableBody+="<td>"+expenseItem?.price+"</td>";
					expenseTableBody+="</tr>";
				}
			}
			if(!expenseTableBody){
				expenseTableBody+="<tr class='text-center'> <td colspan='3'>Expenses Not Available</td></tr>";
			}
			$("#POMExpenses").html(expenseTableBody);
			
			/* set final amount */
			$("#POMProductAmount").html(order.productAmount);
			$("#POMExpenseAmount").html(order.expenseAmount);
			$("#POMTotalAmount").html(order.totalAmount);
			
			/*set log for this order*/
			$("#POMCreatedDate").html(moment(order.createdDate).format("DD/MM/YYYY hh:mm"));
			$("#POMCreatedBy").html(order.createdBy);
			$("#POMUpdatedDate").html(moment(order.updatedDate).format("DD/MM/YYYY hh:mm"));
			$("#POMUpdatedBy").html(order.updatedBy);
			
			
			$("#sellOrderModal").modal("show");	
		})
	}
	
}
/*add product repeater validation dynamically*/
function addProductValidation(index){
	$("select[name='sellItemVos["+index+"].productVo.id']").rules('add',idValidation);
	$("input[name='sellItemVos["+index+"].price']").rules('add',priceValidation);
	$("input[name='sellItemVos["+index+"].qty']").rules('add',qtyValidation);
}
/*remove product repeater validation dynamically*/
function removeProductValidation(index){
	$("select[name='sellItemVos["+index+"].productVo.id']").rules('remove');
	$("input[name='sellItemVos["+index+"].price']").rules('remove');
	$("input[name='sellItemVos["+index+"].qty']").rules('remove');
}

/*add expense repeater validation dynamically*/
function addExpenseValidation(index){
	$("select[name='expenseItemVos["+index+"].expenseCategoryVo.id']").rules('add',idValidation);
	$("input[name='expenseItemVos["+index+"].description']").rules('add',descriptionValidation);
	$("input[name='expenseItemVos["+index+"].price']").rules('add',priceValidation);
}

/*remove expense repeater validation dynamically*/
function removeExpenseValidation(index){
	$("select[name='expenseItemVos["+index+"].expenseCategoryVo.id']").rules('remove');
	$("input[name='expenseItemVos["+index+"].description']").rules('remove');
	$("input[name='expenseItemVos["+index+"].price']").rules('remove');
}

/*cleare all expense repeater on hide button*/
function removeAllExpenseRepeater(){
	$(".expenseItem").each(function(index) {
        $(this).find("input[data-repeater-delete]").trigger("click");
    });
}

/* update all product,expense and frand total function*/
function updateAllTotalAmount() {
    var productAmount = 0.0;
    var expenseAmount = 0.0;
    var grantTotalAmount = 0.0;

    // calculate total product price
    $(".productItem").each(function(index) {
        var productPrice = $(this).find(".productPrice").val();
        var qty = $(this).find(".productQty").val();
        if (isNaN(qty)) {
            qty = 0.0;
        }
        if (isNaN(productPrice)) {
            productPrice = 0.0;
        }
        var productTotalAmount = qty * productPrice;
        $(this).find(".productTotalAmount").val(productTotalAmount.toFixed(2));

        productAmount += productTotalAmount;

    });

    // update total product amount
    $("#productAmount").val(productAmount.toFixed(2)).trigger("change");

    // calculate total expense
    $(".expenseItem").each(function(index) {
        var expensePrice = $(this).find(".expensePrice").val();
        if (isNaN(expensePrice)) {
            expensePrice = 0.0;
        }
        expenseAmount += expensePrice;
    });

    // update total expense amount
    $("#expenseAmount").val(Number(expenseAmount).toFixed(2)).trigger("change");


    // update grand total amount
    grantTotalAmount = Number(expenseAmount) + Number(productAmount);
    $("#totalAmount").val(Number(grantTotalAmount).toFixed(2)).trigger("change");

}