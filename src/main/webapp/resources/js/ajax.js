$ajax = {
	post : function(object) {
		var originSuccess = object.success;
		var originError = object.error;
		object.success = function(data) {
			if (data.retCode === '0' && typeof originSuccess === 'function') {
				originSuccess(data);
			} else {
				$ajax.error('[' + data.retCode + ']' + data.retMsg, originError);
			}
		};
		object.error = function(request, status, error) {
			$ajax.error("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error, originError);
		};
		return $.ajax(object);
	},
	
	error : function(msg, error) {
		alert(msg);
		if (typeof error === 'function') {
			error();
		}
	}
}