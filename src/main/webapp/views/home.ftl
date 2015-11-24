<html>
<head>
<title>Guten-Morgen</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-latest.min.js"></script>
<style type="text/css">
body {
	margin: 0;
	background: #e9ecef;
}

#header {
	position: relative;
	width: 100%;
	height: 60px;
	font-size: 40px;
	line-height: 50px;
	font-weight: bold;
	text-align: center;
	color: #fff;
	background-color: #024;
}

#buildAndDeploy {
	position: absolute;
	margin: auto;
	right: 15px;
	bottom: 0px;
	top: 0px;
	width: 130px;
	height: 30px;
	color: #fff;
	background-color: #036;
	font-size: 18px;
	text-align: center;
}

.card {
	margin: 10px;
	padding: 1px; border : solid 1px;
	background-color: #eef;
	border: solid 1px;
}

table {
	width: 100%; border-collapse : collapse;
	font-size: 15px;
	border-collapse: collapse;
}

table th {
	padding: 3px 5px 3px 5px;
	height: 20px;
	text-align: left;
	background-color: #F5F5F5;
	border: 1px solid #C0C0C0;
}

table td {
	padding: 3px 5px 3px 5px;
	height: 20px;
	border: 1px solid #C0C0C0;
	background-color: #fff;
}

tr td th {
	border: solid 1px;
}

th {
	text-align: left;
	background-color: #F5F5F5;
	border: 1px solid #C0C0C0;
}

.title {
	position:relative;
	font-size: 18px;
	background-color: #D3D3D3;
}
button{
	height:30px;
	font-weight:bold;
	width:100%;
}
form{
	margin:0;
}
.button{
	margin-top:3px;
}
.cancel{
	position:absolute;
	margin:auto;
	width:24px;
	height:24px;
	right:3px;
	text-align:center;
	padding:0;
}
input {
	width: 100%;
}
select {
	width: 100%;
}
.popup {
	position: fixed;
	background-color: #fff;
	top: 200px;
	left: 0px;
	right: 0px;
	margin: auto;
	width: 600px;
	border: solid 1px;
	padding: 3px;
}
.dimmed {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background-color: rgba(0, 0, 0, 0.15);
}
</style>
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