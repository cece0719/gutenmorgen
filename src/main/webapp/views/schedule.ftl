<div class="card">
	<table>
		<tbody>
			<tr>
				<th colspan="7" class="title">SCHEDULE</th>
			</tr>
			<tr>
				<th>번호</th>
				<th>이름</th>
				<th>JOB</th>
				<th>time regex(yyyy-MM-dd HH:mm:ss EEE , Locale : ko_KR)</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
			<#assign i=0>
			<#list scheduleList as schedule>
			<tr>
				<td>${schedule.scheduleKey?html}</td>
				<td>${schedule.name?html}</td>
				<td>${schedule.job.name?html}</td>
				<td>${schedule.timeRegex?html}</td>
				<td><button onclick="$schedule.showUpdatePopup('${schedule.scheduleKey?html}','${schedule.name?html}', '${schedule.job.jobKey?html}', '${schedule.timeRegex?html}')">수정</button></td>
				<td><button onclick="$schedule.remove('${schedule.scheduleKey?html}')">삭제</button></td>
			</tr>
			<#assign i=i+1>
			</#list>
        </tbody>
	</table>
	<div class="button">
		<button onclick="$schedule.showRegisterPopup();">신규등록</button>
	</div>
</div>
<form id="schedule_register" action="/schedule/register" style="display:none;">
	<div class="dimmed">
		<div class="popup">
			<table>
				<tbody>
					<tr>
						<th colspan="2" class="title">SCHEDULE - 신규등록<button type="button" class="cancel" onclick="$schedule.hideRegisterPopup();">X</button></th>
					</tr>
					<tr>
						<th>이름</th>
						<td><input name="name" type="text"></td>
					</tr>
					<tr>
						<th>JOB</th>
						<td>
							<select name="jobKey">
								<#list jobList as job>
								<option value="${job.jobKey?html}">${job.name?html}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<th>time regex(yyyy-MM-dd HH:mm:ss EEE , Locale : ko_KR)</th>
						<td><input name="timeRegex" type="text"></td>
					</tr>
                </tbody>
			</table>
			<div class="button">
				<button type="button" onclick="$schedule.register()">등록</button>
			</div>
		</div>
	</div>
</form>
<form id="schedule_update" action="/schedule/update" style="display: none;">
	<input name="scheduleKey" type="hidden">
	<div class="dimmed">
		<div class="popup">
			<table>
				<tbody>
					<tr>
						<th colspan="2" class="title">SCHEDULE - 수정<button type="button" class="cancel" onclick="$schedule.hideUpdatePopup();">X</button></th>
					</tr>
					<tr>
						<th>이름</th>
						<td><input name="name" type="text"></td>
					</tr>
					<tr>
						<th>JOB</th>
						<td>
							<select name="jobKey">
								<#list jobList as job>
								<option value="${job.jobKey?html}">${job.name?html}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<th>time regex(yyyy-MM-dd HH:mm:ss EEE , Locale : ko_KR)</th>
						<td><input name="timeRegex" type="text"></td>
					</tr>
                </tbody>
			</table>
			<div class="button">
				<button type="button" onclick="$schedule.update()">수정</button>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	$schedule = {
		showRegisterPopup : function() {
			$('#schedule_register')[0].reset();
			$('#schedule_register').show();
		},
		hideRegisterPopup : function() {
			$('#schedule_register').hide();
		},
		register : function() {
			if ($('#schedule_register')[0].name.value==='') {
				alert('이름을 입력하세요');
				return;
			}
			$('#schedule_register').submit();
		},
		
		remove : function(scheduleKey) {
			location.href = "/schedule/remove/" + scheduleKey;
		},
		
		showUpdatePopup : function(scheduleKey, name, jobKey, timeRegex) {
			$('#schedule_update')[0].scheduleKey.value = scheduleKey;
			$('#schedule_update')[0].name.value = name;
			$('#schedule_update')[0].jobKey.value = jobKey;
			$('#schedule_update')[0].timeRegex.value = timeRegex;
			$('#schedule_update').show();
		},
		hideUpdatePopup : function() {
			$('#schedule_update').hide();
		},
		update : function() {
			if ($('#schedule_update')[0].name.value==="") {
				alert('이름을 입력하세요');
				return;
			}
			$('#schedule_update')[0].submit();
		}
	}
</script>