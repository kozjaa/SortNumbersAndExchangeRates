$( document ).ready(function() {

    $("#customerForm").submit(function(event) {
		event.preventDefault();
		ajaxPost();
	});

    function ajaxPost(){

    	var numbers = {
    		numbers : $("input[name='numbers']")
                                    .map(function(){return $(this).val();}).get(),
    		order :  $("#order").val()
    	}

    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/numbers/sort-command",
			data : JSON.stringify(numbers),
			dataType : 'json',
			success : function(result) {
					$("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
												"Sorted Numbers = " + result);
				console.log(result);
			},
			error : function(e) {
				alert("Incorrect data!")
				console.log("ERROR: ", e);
			}
		});
    }
})
