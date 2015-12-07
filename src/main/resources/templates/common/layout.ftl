<#macro default title='Guten Morgen'>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title?html}</title>
    <link rel="stylesheet" type="text/css" href="/css/gutenmorgen.css">
    <script type="text/javascript" src="/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="/js/ajax.js"></script>
</head>
<body>
    <#nested/>
</body>
</html>
</#macro>