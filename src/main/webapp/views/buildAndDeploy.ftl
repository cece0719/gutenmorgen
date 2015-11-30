<style type="text/css">
	#bad_loading{
		position:fixed;
		width:100%;
		height:100%;
		z-index:9999;
		background-color:rgba(0,0,0,0.15);
	}
	#bad_text{
		text-align:center;
		margin-top:200px;
		font-size:60px;
		witdh:300px;
		height:300px;
		font-weight:bold;
		line-height:300px;
	}
	#bad_loading_circle{
		background:url(/resources/img/loading_circle.png) no-repeat 50% 50%;
		background-size:300px 300px;
		margin:auto;
		width:300px;
		height:300px;
		-webkit-animation:loading 5.0s linear infinite;
	}
	@-webkit-keyframes loading{
		0%{-webkit-transform:rotate(0deg)}
		100%{-webkit-transform:rotate(360deg)}
	}
</style>
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