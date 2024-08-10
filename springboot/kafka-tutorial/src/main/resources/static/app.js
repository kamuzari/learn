let roomId;

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.trace('Connected: ' + frame);
    stompClient.subscribe(`/topic/${roomId}`, (greeting) => {
        let message = JSON.parse(greeting.body);
        showGreeting(message);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    roomId = $("#roomId").val();
    if (!roomId) {
        alert("채팅 ID를 입력해주세요!");
        return;
    }

    stompClient.connectHeaders = {
        'roomId': roomId
    };
    stompClient.activate();
}

function disconnect() {
    if (roomId) {
        stompClient.publish({
            destination: "/app/leave",
            headers: {
                'roomId': roomId
            },
            body: JSON.stringify({'name': $("#name").val()})  // Optionally include additional data
        });
    }
    stompClient.deactivate();
    setConnected(false);
    console.trace("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        headers: {
            'roomId': roomId
        },
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message.name + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});
