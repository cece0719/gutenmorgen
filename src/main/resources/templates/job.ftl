<#-- @ftlvariable name="jobList" type="java.util.List<com.woowol.gutenmorgen.model.Job>" -->
<#include "common/common.ftl">
<@layout.main subtitle='JOB'>
    <#list jobList as job>
    <div class="fold">
        <div class="fold_title">
            <label for="job_fold_${job.jobKey}">${job.name?html}</label>
        </div>
        <input id="job_fold_${job.jobKey}" type="checkbox" style="display:none;">
        <div class="fold_content">
            <div class="card">
                <div class="card_title">프로세서</div>
                <div class="card_content">${job.processor}</div>
            </div>
            <div class="card">
                <div class="card_title">파라미터</div>
                <div class="card_content">${job.parameter}</div>
            </div>
        </div>
    </div>
    </#list>
</@layout.main>