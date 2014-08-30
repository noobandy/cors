(function() {

	var remoteServer = 'http://anandm:8080/yell/';

	var page = 1;
	var pageSize = 10;
	var totalItems = 0;
	var newItems = [];

	var itemListElement = $('<ul></ul>').addClass('list-group');
	var newItemsElement = $('<a href="javascript:;"></a>');
	var moreItemsElement = $('<a href="javscript:;">More items</a>');

	var heading = $('<h1></h1>').html('Yell now !!! :)');

	var yellBox = $('<textarea></textarea>').
	attr({ 'rows':'3','placeholder':'yell ...','required':'required' }).
	addClass('form-control');
	var container = $('#yell-container');

	//load first page

	loadData(page,pageSize);


	container.append(heading);

	container.append(yellBox);

	container.append(newItemsElement);

	container.append(itemListElement);

	container.append(moreItemsElement);

	//atmosphere
	var socket = $.atmosphere;

	var request = {
			url : remoteServer + 'yellRoom',
			contentType : "application/json",
			logLevel : 'debug',
			transport : 'websocket',
			trackMessageLength : true,
			shared : true,
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

				var responseBody = response.responseBody;

				try {
					var yell = JSON.parse(responseBody);
					newItems.push(yell);
					updateDOMCOntrols();
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

	var subsocket = socket.subscribe(request);

	yellBox.on('keydown keypress',function(event){

		if (event.which === 13) {
			event.preventDefault();
			var yell = {
					yell : yellBox.val()
			};
			//add yell
			addItem(yell);
		}

	});


	moreItemsElement.click(function(event){

		event.preventDefault();
		page = page + 1;
		loadData(page,pageSize);
	});

	newItemsElement.click(function(event){

		event.preventDefault();

		var items = newItems.splice(0,newItems.length);

		$.each(items, function(index, yell) {
			itemListElement.prepend(toYellItem(yell));
		});

		updateDOMCOntrols();
	});


	function loadData(page,pageSize){
		var rpc = new easyXDM.Rpc({
			remote : remoteServer+'resources/easyXDM/cors/index.html'
		},{
			remote : {
				request : {

				}
			}
		});

		rpc.request({
			url : remoteServer+'api/yells?page='+page+'&pageSize='+pageSize,
			method : 'GET'
		},function(response){
			var data = JSON.parse(response.data);

			totalItems = data.count;

			$.each(data.items, function(index, yell) {
				itemListElement.append(toYellItem(yell));
			});

			updateDOMCOntrols();

		},function(error){
			console.log(error);
		});
	}

	function addItem(yell){
		var rpc = new easyXDM.Rpc({
			remote : remoteServer+'resources/easyXDM/cors/index.html'
		},{
			remote : {
				request : {

				}
			}
		});

		rpc.request({
			url : remoteServer+'api/yells',
			method : 'POST',
			headers : {
				'Content-Type' : 'Application/json'
			},
			data : JSON.stringify(yell)
		},function(response){
			yellBox.val('');
			subsocket.push(response.data);
		},function(error){
			console.log(error);
		});
	}

	function toYellItem(yell) {

		var itemText = $('<p></p>').html(yell.yell);

		var itemFooter = $('<footer></footer>').html(
				new Date(yell.yelledAt));

		var itemBlockquote = $('<blockquote></blockquote>')
		.append(itemText).append(itemFooter);

		var item = $('<div></div>').addClass('list-group-item')
		.append(itemBlockquote);

		return item;
	};

	function updateDOMCOntrols(){

		if(newItems.length > 0){
			newItemsElement.html(newItems.length+' new items');
			newItemsElement.show();
		}else{
			newItemsElement.hide(0);
		}


		if(totalItems <= (page * pageSize)){
			moreItemsElement.hide();
		}
	}


})(); // We call our anonymous function immediately