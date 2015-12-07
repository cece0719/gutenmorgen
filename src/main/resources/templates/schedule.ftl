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
            <th>시간 정규표현식<br>(yyyy-MM-dd HH:mm:ss EEE , Locale : ko_KR)</th>
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
            <td>
                <button onclick="$schedule.showSavePopup('${schedule.scheduleKey?html}','${schedule.name?html}', '${schedule.job.jobKey?html}', '${schedule.timeRegex?html}')">
                    수정
                </button>
            </td>
            <td>
                <button onclick="$schedule.delete('${schedule.scheduleKey?html}')">삭제</button>
            </td>
        </tr>
            <#assign i=i+1>
        </#list>
        </tbody>
    </table>
    <div class="button">
        <button onclick="$schedule.showSavePopup();">신규등록</button>
    </div>
</div>
<form id="schedule_save" style="display: none;">
    <input name="scheduleKey" type="hidden">
    <div class="dimmed">
        <div class="popup">
            <table>
                <tbody>
                <tr>
                    <th colspan="2" class="title">SCHEDULE
                        <button type="button" class="cancel" onclick="$schedule.hideSavePopup();">X</button>
                    </th>
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
                    <th>시간 정규표현식<br>(yyyy-MM-dd HH:mm:ss EEE , Locale : ko_KR)</th>
                    <td><input name="timeRegex" type="text"></td>
                </tr>
                </tbody>
            </table>
            <div class="button">
                <button type="button" onclick="$schedule.save()">저장</button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $schedule = {
        delete: function (scheduleKey) {
            $.ajax({
                data : {"scheduleKey" : scheduleKey},
                url : '/schedule/delete.json',
                success : function() {
                    location.reload();
                },
                error : function(errMsg) {
                    alert(errMsg);
                }
            });
        },

        showSavePopup: function (scheduleKey, name, jobKey, timeRegex) {
            $('#schedule_save')[0].scheduleKey.value = scheduleKey?scheduleKey:"";
            $('#schedule_save')[0].name.value = name?name:"";
            $('#schedule_save')[0].jobKey.value = jobKey?jobKey:$('#schedule_save')[0].jobKey.value;
            $('#schedule_save')[0].timeRegex.value = timeRegex?timeRegex:"";
            $('#schedule_save').show();
        },
        hideSavePopup: function () {
            $('#schedule_save').hide();
        },
        save: function () {
            if ($('#schedule_save')[0].name.value === "") {
                alert('이름을 입력하세요');
                return;
            }
            if ($('#schedule_save')[0].timeRegex.value === "") {
                alert('시간 정규표현식을 입력하세요');
                return;
            }
            $.ajax({
                data : $('#schedule_save').serialize(),
                url : '/schedule/save.json',
                success : function() {
                    location.reload();
                },
                error : function(errMsg) {
                    alert(errMsg);
                }
            });
        }
    }
</script>