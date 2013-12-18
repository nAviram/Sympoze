<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Sympoze</title>
	<link rel="stylesheet" type="text/css" href="css/main.css">
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="Main.js"></script>

</head>

<body>
	<header class="mainHeader"> 
		<span class="logo">Sympoze</span>
		<span class="userName"><input name="username" id="username"></span>
	</header>
	<div class="container cf">
		<h2 class="title"> iOS7.the future or a disater? </h2>
		 <section class="video">
		 	<div name="flashPart" class="flashPart">
			 	<iframe id="iframe1"></iframe>
			 	<!--
			 	<div class="person p1"><img src="img/user-avatar1.PNG"></div>
			 	<div class="vs"><CENTER> vs- </CENTER></div>
				<div class="person p2"><img src="img/user-avatar2.PNG"></div>
				//-->
			</div>
			<div class="contacts">
				<span><img src="img/1.PNG"></span>
				<span><img src="img/2.PNG"></span>
				<span><img src="img/3.PNG"></span>
				<span><img src="img/4.PNG"></span>
				<span><img src="img/5.PNG"></span>
				<span><img src="img/6.PNG"></span>
				<span><img src="img/7.PNG"></span>
				<span><img src="img/8.PNG"></span>
			</div>
		</section>
		<section class="chat">
			<h2>Comments <span class="blue"></span></h2>
			<div class="commentsCon" id="commentsCon"></div>
			<form name="messageForm" id="messageForm">
				<br />
				<input name="message" id="message">
				<input type="submit" value="Send"/>
			</form>
		</section>
	</div>
	
</body>		

</html>