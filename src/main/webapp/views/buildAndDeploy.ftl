<div id="bad_loading" style="display:none;">
	<div id="bad_loading_circle"><div id="bad_text">빌드중</div></div>
</div>
<script type="text/javascript">
	$build={
		execute : function() {
			$ajax.post({
				url : '/buildAndDeploy/go.json',
				success : function(data) {
					$('#bad_loading').show();
					var deploy = false;
					var statusCheck = function() {
						$ajax.post({
							url : '/buildAndDeploy/status.json',
							timeout : 1000,
							success : function(data) {
								if (data.rtnMsg==='run') {
									$('#bad_text').html('완료');
									location.reload();
								}
							},
							error : function() {
								if (!deploy) {
									deploy = true;
									$('#bad_text').html('배포중');
								}
							},
							complete : function(data) {
								setTimeout(statusCheck, 1000);
							}
						});
					}
					statusCheck();
				},
				error : function(errMsg) {
					alert(errMsg);
				}
			});
		} 
	};
</script>