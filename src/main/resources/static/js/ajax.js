$ajax = {
	post : function(param) {
		var originSuccess = param.success;
		var originError = param.error;
		param.success = function(data) {
			if (data.rtnCode === '0' && typeof originSuccess === 'function') {
				originSuccess(data);
			} else if (data.rtnCode !== '0' && typeof originError === 'function') {
				originError('[' + data.rtnCode + ']' + data.rtnMsg);
			}
		};
		param.error = function(request, status, error) {
			if (typeof originError === 'function') {
				originError("알수없는 오류 code:["+request.status+"]"+"message:["+request.responseText+"]error:["+error+"]");
			}
		};
		if (typeof param.cache !== 'boolean') {
			param.cache=false;
		}
		return $.ajax(param);
	}
}