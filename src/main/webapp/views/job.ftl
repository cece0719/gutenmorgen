<div class="card">
	<table>
		<tr>
			<th colspan="7" class="title">JOB</th>
		</tr>
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>프로세서</th>
			<th>파라미터</th>
			<th>실행</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
		<#assign i=0> <#list jobMap?keys as key>
		<tr>
			<td>${i+1}</td>
			<td>${key?html}</td>
			<td>${jobMap[key].processor?html}</td>
			<td>${	jobMap[key].parameter?html}</td>
			<td><button onclick="$job.execute('${key?html}')">실행</button></td>
			<td><button onclick="$job.showUpdatePopup('${key?html}', '${jobMap[key].processor?html}', '${jobMap[key].parameter?html}')">수정</button></td>
			<td><button onclick="$job.remove('${key?html}')">삭제</button></td>
		</tr>
		<#assign i=i+1> </#list>
	</table>
	<div class="button">
		<button onclick="$job.showRegisterPopup();">신규등록</button>
	</div>
</div>
<form id="job_register" action="/job/register" style="display:none;">
	<div class="dimmed">
		<div class="popup">
			<table>
				<tr>
					<th colspan="2" class="title">JOB - 신규등록<button type="button" class="cancel" onclick="$job.hideRegisterPopup();">X</button></th>
				</tr>
				<tr>
					<th>이름</th>
					<td><input name="name" type="text"></td>
				</tr>
				<tr>
					<th>프로세서</th>
					<td><select name="processor"> <#list
							processorMap?keys as key>
							<option value="${key?html}">${key?html}</option> </#list>
					</select></td>
				</tr>
				<tr>
					<th>파라미터</th>
					<td><input name="parameter" type="text"></td>
				</tr>
			</table>
			<div class="button">
				<button type="button" onclick="$job.register()">등록</button>
			</div>
		</div>
	</div>
</form>
<form id="job_update" action="/job/update" style="display: none;">
	<input name="name" type="hidden">
	<div class="dimmed">
		<div class="popup">
			<table>
				<tr>
					<th colspan="2" class="title">JOB - 수정<button type="button" class="cancel" onclick="$job.hideUpdatePopup();">X</button></th>
				</tr>
				<tr>
					<th>이름</th>
					<td><input name="newName" type="text"></td>
				</tr>
				<tr>
					<th>프로세서</th>
					<td>
						<select name="processor">
							<#list processorMap?keys as key>
							<option value="${key?html}">${key?html}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>파라미터</th>
					<td><input name="parameter" type="text"></td>
				</tr>
			</table>
			<div class="button">
				<button type="button" onclick="$job.update()">수정</button>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	$job = {
		showRegisterPopup : function() {
			$('#job_register')[0].reset();
			$('#job_register').show();
		},
		hideRegisterPopup : function() {
			$('#job_register').hide();
		},
		register : function() {
			if ($('#job_register')[0].name.value==='') {
				alert('이름을 입력하세요');
				return;
			}
			$('#job_register').submit();
		},
		
		remove : function(name) {
			location.href = "/job/remove?name=" + name;
		},
		execute : function(name) {
			location.href = "/job/execute?name=" + name;
		},
		
		showUpdatePopup : function(name, processor, parameter) {
			$('#job_update')[0].name.value = name;
			$('#job_update')[0].newName.value = name;
			$('#job_update')[0].processor.value = processor;
			$('#job_update')[0].parameter.value = parameter;
			$('#job_update').show();
		},
		hideUpdatePopup : function() {
			$('#job_update').hide();
		},
		update : function() {
			if ($('#job_update')[0].newName.value==="") {
				alert('이름을 입력하세요');
				return;
			}
			$('#job_update')[0].submit();
		}
	}
</script>