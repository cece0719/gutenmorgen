<div class="card">
	<table>
		<tbody>
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
			<#list jobList as job>
			<tr>
				<td>${job.jobKey?html}</td>
				<td>${job.name?html}</td>
				<td>${processorMap[job.processor].name?html}</td>
				<td>${job.parameter?html}</td>
				<td><button onclick="$job.execute('${job.jobKey?html}')">실행</button></td>
				<td><button onclick="$job.showUpdatePopup('${job.jobKey?html}', '${job.name?html}', '${job.processor?html}', '${job.parameter?html}')">수정</button></td>
				<td><button onclick="$job.remove('${job.jobKey?html}')">삭제</button></td>
			</tr>
			</#list>
        </tbody>
	</table>
	<div class="button">
		<button onclick="$job.showRegisterPopup();">신규등록</button>
	</div>
</div>
<form id="job_register" action="/job/register" style="display:none;">
    <input name="parameter" type="hidden">
	<div class="dimmed">
		<div class="popup">
			<table>
				<tbody>
					<tr>
						<th colspan="2" class="title">JOB - 신규등록<button type="button" class="cancel" onclick="$job.hideRegisterPopup();">X</button></th>
					</tr>
					<tr>
						<th>이름</th>
						<td><input name="name" type="text"></td>
					</tr>
					<tr>
						<th>프로세서</th>
						<td>
							<select name="processor" onchange="$job.changeRegisterProcessor()">
								<#list processorMap?keys as key>
								<option value="${key?html}">${processorMap[key].name?html}</option>
								</#list>
							</select>
						</td>
					</tr>
                </tbody>
			</table>
			<#list processorMap?keys as key>
				<#if (processorMap[key].parameterInfoList?size > 0) >
				<table processor="${key?html}">
                    <tbody>
						<tr>
							<th colspan="2" class="title">파라미터(${processorMap[key].name?html})</th>
						</tr>
						<#list processorMap[key].parameterInfoList as parameterInfo>
							<tr>
								<th>${parameterInfo['name']?html}</th>
								<td type="${parameterInfo['type']?html}">
									<#if parameterInfo['type']! == 'list'>
                                        <select parameterId="${parameterInfo['id']}" size="3">
										</select>
										<#if parameterInfo['inputType']! == 'select'>
                                            <select>
												<#list parameterInfo['selectList'] as select>
                                                    <option>${select?html}</option>
												</#list>
                                            </select>
										<#elseif parameterInfo['inputType']! == 'text'>
                                            <input type="text">
										</#if>
                                        <button type="button" onclick="$job.addRegisterParameterList('${key?html}', '${parameterInfo['id']}', $(this).prev().val())">추가</button>
                                        <button type="button" onclick="$job.removeSelectedRegisterParameterList('${key?html}', '${parameterInfo['id']}')">선택 삭제</button>
									<#elseif parameterInfo['type']! == 'text'>
										<#if parameterInfo['inputType']! == 'select'>
                                        	<select parameterId="${parameterInfo['id']}" type="single">
												<#list parameterInfo['selectList'] as select>
													<option>${select?html}</option>
												</#list>
											</select>
										<#elseif parameterInfo['inputType']! == 'text'>
                                            <input parameterId="${parameterInfo['id']}" type="text">
										</#if>
									</#if>
								</td>
							</tr>
						</#list>
                    </tbody>
				</table>
				</#if>
			</#list>
			<div class="button">
				<button type="button" onclick="$job.register()">등록</button>
			</div>
		</div>
	</div>
</form>
<form id="job_update" action="/job/update" style="display: none;">
	<input name="jobKey" type="hidden">
    <input name="parameter" type="hidden">
	<div class="dimmed">
		<div class="popup">
			<table>
				<tbody>
					<tr>
						<th colspan="2" class="title">JOB - 수정<button type="button" class="cancel" onclick="$job.hideUpdatePopup();">X</button></th>
					</tr>
					<tr>
						<th>이름</th>
						<td><input name="name" type="text"></td>
					</tr>
					<tr>
						<th>프로세서</th>
						<td>
							<select name="processor">
								<#list processorMap?keys as key>
								<option value="${key?html}">${processorMap[key].name?html}</option>
								</#list>
							</select>
						</td>
					</tr>
                </tbody>
			</table>
			<#list processorMap?keys as key>
				<#if (processorMap[key].parameterInfoList?size > 0) >
					<table processor="${key?html}">
						<tbody>
						<tr>
							<th colspan="2" class="title">파라미터(${processorMap[key].name?html})</th>
						</tr>
							<#list processorMap[key].parameterInfoList as parameterInfo>
							<tr>
								<th>${parameterInfo['name']?html}</th>
								<td type="${parameterInfo['type']?html}">
									<#if parameterInfo['type']! == 'list'>
										<select parameterId="${parameterInfo['id']}" size="3">
										</select>
										<#if parameterInfo['inputType']! == 'select'>
											<select>
												<#list parameterInfo['selectList'] as select>
													<option>${select?html}</option>
												</#list>
											</select>
										<#elseif parameterInfo['inputType']! == 'text'>
											<input type="text">
										</#if>
										<button type="button" onclick="$job.addUpdateParameterList('${key?html}', '${parameterInfo['id']}', $(this).prev().val())">추가</button>
										<button type="button" onclick="$job.removeSelectedUpdateParameterList('${key?html}', '${parameterInfo['id']}')">선택 삭제</button>
									<#elseif parameterInfo['type']! == 'text'>
										<#if parameterInfo['inputType']! == 'select'>
											<select parameterId="${parameterInfo['id']}" type="single">
												<#list parameterInfo['selectList'] as select>
													<option>${select?html}</option>
												</#list>
											</select>
										<#elseif parameterInfo['inputType']! == 'text'>
											<input parameterId="${parameterInfo['id']}" type="text">
										</#if>
									</#if>
								</td>
							</tr>
							</#list>
						</tbody>
					</table>
				</#if>
			</#list>
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
            $job.changeRegisterProcessor();
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
            $job.setRegisterParameter($('#job_register')[0].processor.value);
			$('#job_register').submit();
		},
        changeRegisterProcessor : function() {
			$('#job_register>div>div>table[processor]').hide();
            $('#job_register>div>div>table[processor='+$('#job_register')[0].processor.value+']').show();
		},
        addRegisterParameterList : function(processorKey, parameterId, value) {
			if (!value) {
				alert('값을 입력해 주세요');
				return;
			}
            $('#job_register>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId='+parameterId+']').append('<option>'+value+'</option>');
            $('#job_register>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId='+parameterId+']').next().val('');
		},
        removeSelectedRegisterParameterList : function(processorKey, parameterId) {
            $('#job_register>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId='+parameterId+']').find('option:selected').remove();
		},
		setRegisterParameter : function(processorKey) {
			var object = {};
            $('#job_register>div>div>table[processor='+processorKey+']>tbody>tr>td>[parameterId]').each(function() {
				var parameterId = $(this).attr('parameterId');
				if(this.tagName === 'SELECT') {
					if ($(this).attr('type') === 'single') {
                        object[parameterId]=$(this).val();
					} else {
                        object[parameterId]=[];
                        $(this).children().each(function(){
                            object[parameterId].push($(this).html());
                        })
					}
				} else if (this.tagName === 'INPUT') {
                    object[parameterId]=$(this).val();
				}
			});
            $('#job_register')[0].parameter.value = JSON.stringify(object);
		},
		remove : function(jobKey) {
			location.href = "/job/remove/" + jobKey;
		},
		execute : function(jobKey) {
			location.href = "/job/execute/" + jobKey;
		},
		
		showUpdatePopup : function(jobKey, name, processor, parameter) {
			$('#job_update')[0].jobKey.value = jobKey;
			$('#job_update')[0].name.value = name;
			$('#job_update')[0].processor.value = processor;
			$('#job_update')[0].parameter.value = parameter;
            $job.changeUpdateProcessor();
			$job.setUpdateInput(processor, parameter);
			$('#job_update').show();
		},
		hideUpdatePopup : function() {
			$('#job_update').hide();
		},
		update : function() {
			if ($('#job_update')[0].name.value==="") {
				alert('이름을 입력하세요');
				return;
			}
            $job.setUpdateParameter($('#job_update')[0].processor.value);
			$('#job_update')[0].submit();
		},
        changeUpdateProcessor : function() {
            $('#job_update>div>div>table[processor]').hide();
            $('#job_update>div>div>table[processor='+$('#job_update')[0].processor.value+']').show();
        },
        addUpdateParameterList : function(processorKey, parameterId, value) {
            if (!value) {
                alert('값을 입력해 주세요');
                return;
            }
            $('#job_update>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId='+parameterId+']').append('<option>'+value+'</option>');
            $('#job_update>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId='+parameterId+']').next().val('');
        },
        removeSelectedUpdateParameterList : function(processorKey, parameterId) {
            $('#job_update>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId='+parameterId+']').find('option:selected').remove();
        },
		setUpdateInput : function(processorKey, parameter) {
			var parameterObject = jQuery.parseJSON(parameter);
            for (var key in parameterObject) {
                if (parameterObject.hasOwnProperty(key)) {
					if (parameterObject[key] instanceof Array) {
						parameterObject[key].forEach(function(value) {
                            $('#job_update>div>div>table[processor='+processorKey+']>tbody>tr>td>[parameterId='+key+']').append('<option>'+value+'</option>');
						});
					} else {
                        $('#job_update>div>div>table[processor='+processorKey+']>tbody>tr>td>[parameterId='+key+']').val(parameterObject[key]);
					}
                }
            }
		},
        setUpdateParameter : function(processorKey) {
            var object = {};
            $('#job_update>div>div>table[processor='+processorKey+']>tbody>tr>td>[parameterId]').each(function() {
                var parameterId = $(this).attr('parameterId');
                if(this.tagName === 'SELECT') {
                    if ($(this).attr('type') === 'single') {
                        object[parameterId]=$(this).val();
                    } else {
                        object[parameterId]=[];
                        $(this).children().each(function(){
                            object[parameterId].push($(this).html());
                        })
                    }
                } else if (this.tagName === 'INPUT') {
                    object[parameterId]=$(this).val();
                }
            });
            $('#job_update')[0].parameter.value = JSON.stringify(object);
        }
	}
</script>