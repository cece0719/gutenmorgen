<html>
<head>
    <title>Guten Morgen</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/resources/js/ajax.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/gutenmorgen.css">
</head>
<body>
<#include "/buildAndDeploy.ftl">
<div id="header">
    Guten Morgen
    <button id="buildAndDeploy" onclick="$build.execute();">빌드&배포</button>
</div>
<#include "/job.ftl">
<#include "/schedule.ftl">
</body>
</html>