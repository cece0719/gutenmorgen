(function () {
    $.ajaxSetup({
        cache: false,
        type: "POST"
    });

    var originalAjax = $.ajax;

    $.ajax = function (param) {
        var originSuccess = param.success;
        var orgError = param.error;
        param.success = function (data) {
            if (data['rtnCode'] === '0' && typeof originSuccess === 'function') {
                originSuccess(data);
            } else if (data['rtnCode'] !== '0' && typeof orgError === 'function') {
                orgError('[' + data['rtnCode'] + ']' + data['rtnMsg']);
            }
        };
        param.error = function (request, status, error) {
            if (typeof orgError === 'function') {
                orgError("알수없는 오류 code:[" + request.status + "]" + "message:[" + request.responseText + "]error:[" + error + "]");
            }
        };
        return originalAjax(param);
    }
}());