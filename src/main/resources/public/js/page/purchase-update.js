$(document).ready(
    function() {
    	/*product form validation*/
    	$("#purchaseAddForm").validate({
            rules: {
                "purchaseDate": {
                    required: true,
                    regex: /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/
                },
                "shopVo.id": "required"
            },
            messages: {
                purchaseDate: {
                    regex: "date is invalid"
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    	
        $("#purchaseDate").datepicker({
            yearRange: "2002:2012",
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
        });

        /*set purchase date in edit mode*/
        var formateEditDate = moment(purchaseDate).format('DD/MM/YYYY')
        $("#purchaseDate").val(formateEditDate);

        /*select shop*/
        $("#shopId").val(shopId)

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
            initEmpty: false,
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
            isFirstItemUndeletable: false
        })
        
        /*open expense repeater box if expense exist*/
        if (totalExpenseItemCount > 0) {
            $("a[href='#collapseTwo']").trigger("click");
        }else {
        	/*remove expense item if there is no any expense item present in database*/
        	removeAllExpenseRepeater()
    	}

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

        
        
        
        /*add validation for allready exis one product item*/
        $(".productItem").each(function(index) {
        	addProductValidation(index);
        });
        
        /*add validation for allready exis expense item*/
        $(".expenseItem").each(function(index) {
        	addExpenseValidation(index);
        });
        
        

        /*get price of product on change of select box*/
        $(".productSelectBox").change(function() {
            var productId = $(this).val();
            if (productId) {
                var that = this;
                $.get("/product/price/" + productId, function(price) {
                    $(that).closest("div[data-repeater-item]").find(".productPrice").val(price);
                })
            } else {
                $(this).closest("div[data-repeater-item]").find(".productPrice").val(0.00);
            }
        });
        
        /* set product,expense and grant total amount */
        updateAllTotalAmount();
    });

/*view all purchase order detail*/
function onView(purchaseId) {

    if (purchaseId) {
        $.get("/purchase/" + purchaseId, function(order) {
            $("#POMOrderNumber").html(order.id);
            $("#POMOrderDate").html(moment(order.purchaseDate).format("DD/MM/YYYY"));
            $("#POMShopName").html(order.shopVo.name);

            /*prepare product body for view in table */
            var productTableBody = "";
            if (order.purchaseItemVos) {
                for (var purchaseItem of order.purchaseItemVos) {
                    productTableBody += "<tr>";
                    productTableBody += "<td>" + purchaseItem?.productVo.name + "</td>";
                    productTableBody += "<td>" + purchaseItem?.qty + "</td>";
                    productTableBody += "<td>" + purchaseItem?.price + "</td>";
                    productTableBody += "<td>" + purchaseItem?.totalAmount + "</td>";
                    productTableBody += "</tr>";
                }
            }
            $("#POMProducts").html(productTableBody);

            /*prepare expense body for view in table */
            var expenseTableBody = "";
            if (order.purchaseItemVos) {
                for (var expenseItem of order.expenseItemVos) {
                    expenseTableBody += "<tr>";
                    expenseTableBody += "<td>" + expenseItem?.expenseCategoryVo.name + "</td>";
                    expenseTableBody += "<td>" + expenseItem?.description + "</td>";
                    expenseTableBody += "<td>" + expenseItem?.price + "</td>";
                    expenseTableBody += "</tr>";
                }
            }
            if (!expenseTableBody) {
                expenseTableBody += "<tr class='text-center'> <td colspan='3'>Expenses Not Available</td></tr>";
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


            $("#purchaseOrderModal").modal("show");
        })
    }

}
/*add product repeater validation dynamically*/
function addProductValidation(index) {
    $("select[name='purchaseItemVos[" + index + "].productVo.id']").rules('add', idValidation);
    $("input[name='purchaseItemVos[" + index + "].price']").rules('add', priceValidation);
    $("input[name='purchaseItemVos[" + index + "].qty']").rules('add', qtyValidation);
}
/*remove product repeater validation dynamically*/
function removeProductValidation(index) {
    $("select[name='purchaseItemVos[" + index + "].productVo.id']").rules('remove');
    $("input[name='purchaseItemVos[" + index + "].price']").rules('remove');
    $("input[name='purchaseItemVos[" + index + "].qty']").rules('remove');
}

/*add expense repeater validation dynamically*/
function addExpenseValidation(index) {
    $("select[name='expenseItemVos[" + index + "].expenseCategoryVo.id']").rules('add', idValidation);
    $("input[name='expenseItemVos[" + index + "].discription']").rules('add', descriptionValidation);
    $("input[name='expenseItemVos[" + index + "].price']").rules('add', priceValidation);
}

/*remove expense repeater validation dynamically*/
function removeExpenseValidation(index) {
	$("select[name='purchaseItemVos[" + index + "].expenseCategoryVo.id']").rules('remove');
    $("input[name='purchaseItemVos[" + index + "].discription']").rules('remove');
    $("input[name='purchaseItemVos[" + index + "].price']").rules('remove');
}

/*cleare all expense repeater on hide button*/
function removeAllExpenseRepeater() {
	$(".expenseItem").each(function(index) {
		$(this).find("input[data-repeater-delete]").trigger("click");
    });
    updateAllTotalAmount();
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