$(document).ready(
    function() {
        var today = moment(new Date()).format('DD/MM/YYYY')

        $("#purchaseDate").datepicker({
            yearRange: "2002:2012",
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
        });
        $("#purchaseDate").val(today);

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
                updateAllTotalAmount();
                $(this).slideDown();
            },
            hide: function(deleteElement) {
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


        $("#purchaseAddForm").validate({
            rules: {
                "purchaseDate": {
                    required: true,
                    regex:/^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/
                },
                "shopVo.id":"required"
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

        // add validation for allready exis one repeater item product
        addProductValidation(0);
    });

function addProductValidation(index){
	$("select[name='purchaseItemVos["+index+"].productVo.id']").rules('add',idValidation);
	$("input[name='purchaseItemVos["+index+"].price']").rules('add',priceValidation);
	$("input[name='purchaseItemVos["+index+"].qty']").rules('add',qtyValidation);
}
function removeProductValidation(index){
	$("select[name='purchaseItemVos["+index+"].productVo.id']").rules('remove');
	$("input[name='purchaseItemVos["+index+"].price']").rules('remove');
	$("input[name='purchaseItemVos["+index+"].qty']").rules('remove');
}

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
    grantTotalAmount = expenseAmount + productAmount;
    $("#totalAmount").val(Number(grantTotalAmount).toFixed(2)).trigger("change");

}