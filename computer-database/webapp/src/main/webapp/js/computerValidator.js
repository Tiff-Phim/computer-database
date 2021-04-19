$(document).submit(function(event) {
	var introduced = $("#introduced").val();
	var discontinued = $("#discontinued").val();
	if (introduced.length > 1) {
		if (discontinued.length > 1) {
			if (new Date(introduced) > new Date(discontinued)) {
				event.preventDefault();
				$("#alert-message").show();
			}
		}
	}
});