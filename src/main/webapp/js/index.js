window.onload = function() {
	toggleInputType();
	$('[name="inputType"]').click(function() {
		toggleInputType();
	});
}

function toggleInputType() {
	var inputType = $('[name="inputType"]:checked').val();
	if (inputType === 'trackingNumbers') {
		$('#tracking-numbers-row').css('display', '');
		$('#file-row').css('display', 'none');
	}
	if (inputType === 'csv') {
		$('#tracking-numbers-row').css('display', 'none');
		$('#file-row').css('display', '');
	}
}

function track() {
	var service = $('#service').val();
	var inputType = $('[name="inputType"]:checked').val();
	if (inputType === 'trackingNumbers') {
		var url = '/api/tracking/' + service;
		var data = new FormData();
		data.append('email', $('#email').val());
		data.append('trackingNumbers', $('#tracking-numbers').val().split('\n'));
		post(url, data);
	}
	if (inputType === 'csv') {
		var url = '/api/tracking/' + service + '/csv';
		var data = new FormData();
		data.append('email', $('#email').val());
		data.append('file', $('#file')[0].files[0]);
		post(url, data);
	}
}

function post(url, data) {
	$.ajax({
		method: 'POST',
		url: url,
        data: data,
		processData: false,
        contentType: false,
        success: function() {
        	clearMessage();
        	appendMessage('Tracking submitted. You will receive an email when complete.');
        	clearInputs();
        },
        error: function(error) {
        	clearMessage();
        	clearInputs();
        	if (error.status === 400) {
        		for (const message of error.responseJSON) {
        			appendMessage(message);
        		}
        		return;
        	}
        	if (error.status === 500) {
				appendMessage(error.responseJSON.message);
        		return;
        	}
        	if (error.status === 404) {
        		appendMessage('404 - Not found');
        		return;
        	}
        	appendMessage('Error: ' + error.status);
        }
	});
}

function appendMessage(message) {
	$('#message').append(message + '<br/>');
	$('#message').css('display', 'block').delay(5000).fadeOut('slow');
}

function clearMessage() {
	$('#message').text('');
}

function clearInputs() {
	$('#file').val('');
	$('#tracking-numbers').val('');
}
