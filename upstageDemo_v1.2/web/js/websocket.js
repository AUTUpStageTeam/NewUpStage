var socket = new WebSocket("ws://localhost:8080/websocketExample_1/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);
    if (device.action === "add") {
        printDeviceElement(device);
    }
}

function addDevice(description) {
    var DeviceAction = {
        action: "add",
        description: description
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceElement(device) {
    var content = document.getElementById("content");
    var deviceDiv = document.createElement("div");
    content.appendChild(deviceDiv);

    var deviceDescription = document.createElement("span");
    deviceDescription.innerHTML = "<b>User:</b> " + device.description;
    deviceDiv.appendChild(deviceDescription);

}

function formSubmit() {
    var form = document.getElementById("chatBox");
    var description = form.elements["device_description"].value;
    document.getElementById("chatBox").reset();
    addDevice(description);
}

