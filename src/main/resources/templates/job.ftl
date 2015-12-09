<#-- @ftlvariable name="objectMapper" type="com.fasterxml.jackson.databind.ObjectMapper" -->
<#-- @ftlvariable name="processorMap" type="java.util.Map<String, com.woowol.gutenmorgen.processor.Processor>" -->
<#-- @ftlvariable name="jobList" type="java.util.List<com.woowol.gutenmorgen.model.Job>" -->
<#include "common/common.ftl">
<@layout.main subtitle='JOB'>
    <#list jobList as job>
    <div class="fold">
        <div class="fold_title">
            <a href="javascript:;" onclick="$(this).parent().next().slideToggle();">${job.name?html}</a>
        </div>
        <div class="fold_content" style="display: none;">
            <div class="card">
                <div class="card_title">프로세서</div>
                <div class="card_content">${processorMap[job.processor].name?html}</div>
            </div>
            <#if job.parameter! != ''>
                <div class="card">
                    <div class="card_title">파라미터</div>
                    <div class="card_content">
                        <pre>${objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readValue(job.parameter, object))?html}</pre>
                    </div>
                </div>
            </#if>
            <button type="button" class="card" onclick="$job.execute('${job.jobKey?html}')">실행</button>
            <button type="button" class="card" onclick="$job.delete('${job.jobKey?html}')">삭제</button>
        </div>
    </div>
    </#list>
<script type="text/javascript">
    $job = {
        delete: function (jobKey) {
            $.ajax({
                data: {"jobKey": jobKey},
                url: '/job/delete.json',
                success: function () {
                    location.reload();
                },
                error: function (errMsg) {
                    alert(errMsg);
                }
            });
        },
        execute: function (jobKey) {
            $.ajax({
                data: {"jobKey": jobKey},
                url: '/job/execute.json',
                success: function () {
                    location.reload();
                },
                error: function (errMsg) {
                    alert(errMsg);
                }
            });
        },

        showSavePopup: function (jobKey, name, processor, parameter) {
            $('#job_save')[0].jobKey.value = jobKey ? jobKey : "";
            $('#job_save')[0].name.value = name ? name : "";
            $('#job_save')[0].processor.value = processor ? processor : $('#job_save')[0].processor.value;
            $('#job_save')[0].parameter.value = parameter ? parameter : "";
            $('#job_save>div>div>table[processor]>tbody>tr>td>select[parameterId]').html('');
            $job.changeSaveProcessor();
            $job.setSaveInput(processor, $('#job_save')[0].parameter.value);
            $('#job_save').show();
        },
        hideSavePopup: function () {
            $('#job_save').hide();
        },
        save: function () {
            if ($('#job_save')[0].name.value === "") {
                alert('이름을 입력하세요');
                return;
            }
            $job.setSaveParameter($('#job_save')[0].processor.value);
            $.ajax({
                data: $('#job_save').serialize(),
                url: '/job/save.json',
                success: function () {
                    location.reload();
                },
                error: function (errMsg) {
                    alert(errMsg);
                }
            });
        },
        changeSaveProcessor: function () {
            $('#job_save>div>div>table[processor]').hide();
            $('#job_save>div>div>table[processor=' + $('#job_save')[0].processor.value + ']').show();
        },
        addSaveParameterList: function (processorKey, parameterId, value) {
            if (!value) {
                alert('값을 입력해 주세요');
                return;
            }
            $('#job_save>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId=' + parameterId + ']').append('<option>' + value + '</option>');
            $('#job_save>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId=' + parameterId + ']').next().val('');
        },
        removeSelectedSaveParameterList: function (processorKey, parameterId) {
            $('#job_save>div>div>table[processor=' + processorKey + ']>tbody>tr>td>select[parameterId=' + parameterId + ']').find('option:selected').remove();
        },
        setSaveInput: function (processorKey, parameter) {
            if (!parameter) {
                return;
            }
            var parameterObject = jQuery.parseJSON(parameter);
            for (var key in parameterObject) {
                if (parameterObject.hasOwnProperty(key)) {
                    if (parameterObject[key] instanceof Array) {
                        parameterObject[key].forEach(function (value) {
                            $('#job_save>div>div>table[processor=' + processorKey + ']>tbody>tr>td>[parameterId=' + key + ']').append('<option>' + value + '</option>');
                        });
                    } else {
                        $('#job_save>div>div>table[processor=' + processorKey + ']>tbody>tr>td>[parameterId=' + key + ']').val(parameterObject[key]);
                    }
                }
            }
        },
        setSaveParameter: function (processorKey) {
            var object = {};
            $('#job_save>div>div>table[processor=' + processorKey + ']>tbody>tr>td>[parameterId]').each(function () {
                var parameterId = $(this).attr('parameterId');
                if (this.tagName === 'SELECT') {
                    if ($(this).attr('type') === 'single') {
                        object[parameterId] = $(this).val();
                    } else {
                        object[parameterId] = [];
                        $(this).children().each(function () {
                            object[parameterId].push($(this).html());
                        })
                    }
                } else if (this.tagName === 'INPUT') {
                    object[parameterId] = $(this).val();
                }
            });
            $('#job_save')[0].parameter.value = JSON.stringify(object);
        }
    }
</script>
</@layout.main>