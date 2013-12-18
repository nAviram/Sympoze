 $().ready(function(){
    	var prmstr = window.location.search.substr(1);
		var prmarr = prmstr.split ("&");
		var params = {};
		for ( var i = 0; i < prmarr.length; i++) {
		    var tmparr = prmarr[i].split("=");
		    params[tmparr[0]] = tmparr[1];
		}
		$("#iframe1").attr('src',"flash/Sympoze.html");
		
		$('#messageForm').submit(function(event){
			//on submit:
			var message=$(this).find("#message").val();
			var username=$("#username").val();
			$(this).find("#message").val("");
			$.ajax({
				  url: "ChatServlet",
				  type: "POST",
				  dataType: "json",
				  data: {type:"sendMessage",message:message, username:username}
				}).done(function(data) {
				 //data.forEach(function(message) {
				//	    addMessage(message);//add message to the chat
				// });
				  //updateChat();
				});
			//update after submit
			event.preventDefault();
		});
		$.ajax({
			  url: "ChatServlet",
			  type: "GET",
			  dataType: "json",
			  data: {type:"getMessage"}
			}).done(getMessages);
		updateChat();
    });
    
    var lastTime;
    function updateChat(){
    	$.ajax( {
			  url: "ChatServlet",
			  type: "POST",
			  dataType: "json",
			  data: {type:"getMessage",lastTime:lastTime}
			})
    	  .done(getMessages)
    	  .fail(function() {
    	    console.log( "error" );
    	  })
    	  .always(function() { //update cycle
    		  setTimeout(updateChat,1000);
    	  });
    }
    
    //get messages from DB
    function getMessages(data){    	
	  data.forEach(function(message){
		    addMessage(message);
		    lastTime=message.insertDate;
	  });	
    }
    //receives the message from server and adds it to the chat
    function addMessage(message){
		$message=$('<div class="comment"><span class"username">'+ message.username +': </span><span class"text">'+message.text+' </span><span class"insertDate">'+ message.insertDate +
				'</span></div>');
		$('#commentsCon').append($message);
		$("#commentsCon").scrollTop($("#commentsCon")[0].scrollHeight);
    }