var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocket_entry');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/device/dist_sys_data', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/hello", {}, JSON.stringify({'message': $("#message").val()}));
}

function showGreeting(data) {
    $("#greetings").html("");
    $("#greetings").append("<tr><td>CPU:</td><td>" + data.cpu + "</td></tr>");
    $("#greetings").append("<tr><td>RAM:</td><td>" + data.ram + "</td></tr>");
    $("#greetings").append("<tr><td>BW:</td><td>" + data.bw + "</td></tr>");
    $("#greetings").append("<tr><td>消息:</td><td>" + data.helloMessage + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});