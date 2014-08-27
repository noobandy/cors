<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Yell Client</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-cerulean.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<h1>Yell now !!! :)</h1>

				<textarea id="yellBox" class="form-control send-message" rows="3"
					placeholder="yell ..." required></textarea>
				<div id="yellList" class="list-group"></div>
				<!-- <ul class="pager">
					<li class="previous"><a href="#">&larr; Newer</a></li>
					<li class="next"><a href="#">Older &rarr;</a></li>
				</ul> -->
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery.atmosphere.js"></script>

	<script type="text/javascript">
		$(document).ready(
				function() {
					var yellList = $('#yellList');
					var BASE_URL = 'http://localhost:8080/yell/'
					/* 
					var page = 1;
					var pageSize = 10;
					var totalItems = 0;
					var pager = $('.pager');
					var previous = $('.previous');
					var next = $('.next');
					
					//loadYells();
					
					
					function adjustPaginationControl(){
						if(totalItems < pageSize){
							pager.hide();
						}else{
								if(page <= 1){
									previous.addClass('disabled');
								}else{
									previous.removeClass('disabled');
								}
								
								if(totalItems < (page * pageSize)){
									next.addClass('disabled');
								}else{
									next.removeClass('disabled');
								}	
						}
					};
					
					
					function loadYells(){
						$.getJSON('reviews?vendorId='+vendorId+'&page='+page+'&pageSize='+pageSize,function(data){
							reviewList.empty();
							$.each(data.reviews,function(index,review){ 
								reviewList.append(toReviewItem(review));
							});
							initDynamicItemRatings();
							totalItems = data.totalReviews;
							adjustPaginationControl();
						});
					};
					
					next.click(function(event){
						if(!$(this).hasClass('disabled')){
							page = page + 1;
							loadYells();
						}
						event.preventDefault();
					});
					
					previous.click(function(event){
						if(!$(this).hasClass('disabled')){
							page = page - 1;
							loadYells();
						}	
						event.preventDefault();
					});
					 */

					//atmosphere
					var socket = $.atmosphere;

					var request = {
						url : BASE_URL+'yellRoom',
						contentType : "application/json",
						logLevel : 'debug',
						transport : 'websocket',
						fallbackTransport : 'long-polling',
						onOpen : function(response) {

						},
						onTransportFailure : function(errorMsg, request) {
							$.atmosphere.info(errorMsg);
							if (window.EventSource) {
								request.fallbackTransport = "sse";
							}
						},
						onReconnect : function() {
							socket.info("Reconnecting");
						},
						onMessage : function(response) {
							console.log(response);
							var responseBody = response.responseBody;
							try {
								var yell = JSON.parse(responseBody);
								console.log(yell);
								yellList.prepend(toYellItem(yell));
							} catch (e) {
								console.log(e);
							}
						},
						onClose : function(response) {
							console.log('Closing connection');
						},
						onError : function(response) {
							console.log(response);
						}
					};

					var subSocket = socket.subscribe(request);

					$('#yellBox').on("keydown keypress", function(event) {
						if (event.which === 13) {
							var yell = {
									yell : $('#yellBox').val(),
									yelledAt : new Date()
							};
							
							$('#yellBox').val('');
							
							$.ajax({
								url: BASE_URL+'api/yells',
								method: 'POST',
								contentType: 'Application/json',
								cache : false,
								data : JSON.stringify(yell),
								success : function(data){	
									subSocket.push(JSON.stringify(data));
								}
							});
							
							event.preventDefault();
						}
					});

					function toYellItem(yell) {

						var itemText = $('<p></p>').html(yell.yell);

						var itemFooter = $('<footer></footer>').html(new Date(yell.yelledAt));

						var itemBlockquote = $('<blockquote></blockquote>')
								.append(itemText).append(itemFooter);

						var item = $('<div></div>').addClass('list-group-item')
								.append(itemBlockquote);

						return item;
					}
					;

				});
	</script>
</body>
</html>
