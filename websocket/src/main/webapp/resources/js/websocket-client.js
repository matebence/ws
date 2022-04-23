var serviceLocation = "ws://localhost:8080/websocket/chat";
var room = '';
var user = '';

var $chatWindow;
var $nickName;
var $message;
var wsocket;
var $alert;


$(document).ready(function () {
    $nickName = $('#nickname');
    $message = $('#message');
    $chatWindow = $('#response');
    $alert = $('#alert');

    $('.chat-wrapper').hide();

    $('#enterRoom').click(function (evt) {
        evt.preventDefault();

        room = $('#chatroom option:selected').val();
        user = $('#nickname').val();

        connectToChatServer();

        $('.chat-wrapper h2').html("Welcome " + user + "! <br/>You are in the " + room + " chat room.");
        $('.chat-signin').hide();
        $('.chat-wrapper').show();
        $message.focus();
    });

    $('#do-chat').submit(function (evt) {
        evt.preventDefault();
        sendMessage()
    });

    $('#leave-room').click(function () {
        leaveRoom();
    });
});

function onMessageReceived(evt) {
    var msg = JSON.parse(evt.data);

    var $messageLine = constructHTMLSnippet(msg.sender, msg.content, msg.received);
    $chatWindow.append($messageLine);
}

function sendMessage() {
    var msg = '{"content":"' + $message.val() + '", "sender":"' + user + '", "received":"' + '"}';
    wsocket.send(msg);

    $message.val('').focus();
}

function connectToChatServer() {
    wsocket = new WebSocket(constructURI(serviceLocation, encodeURI(room), user));

    wsocket.onerror = onConnectionError;
    wsocket.onmessage = onMessageReceived;
}

function onConnectionError(evt) {
    $alert.append($(evt));
}

function leaveRoom() {
    wsocket.close();
    $chatWindow.empty();
    $('.chat-wrapper').hide();
    $('.chat-signin').show();
}

function constructHTMLSnippet(nickName, content, received) {
    return $('<tr><td class="received">' + received.substring(0, 8)
        + '</td><td class="user">' + nickName
        + '</td><td class="message">' + content
        + '</td></tr>');
}

function constructURI(serviceLocation, room, user) {
    return serviceLocation + "/" + room + "/" + user;
}
