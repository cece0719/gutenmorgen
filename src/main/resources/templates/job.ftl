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
        </div>
    </div>
    </#list>
</@layout.main>