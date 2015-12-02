<#macro default title='Guten Morgen'>
<html>
<head>
    <title>${title?html}</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/gutenmorgen.css">
    <script type="text/javascript" src="/resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="/resources/js/ajax.js"></script>
</head>
<body>
    <#nested/>
</body>
</html>
</#macro>