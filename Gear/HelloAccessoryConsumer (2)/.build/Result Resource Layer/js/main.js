/*    
 * Copyright (c) 2014 Samsung Electronics Co., Ltd.   
 * All rights reserved.   
 *   
 * Redistribution and use in source and binary forms, with or without   
 * modification, are permitted provided that the following conditions are   
 * met:   
 *   
 *     * Redistributions of source code must retain the above copyright   
 *        notice, this list of conditions and the following disclaimer.  
 *     * Redistributions in binary form must reproduce the above  
 *       copyright notice, this list of conditions and the following disclaimer  
 *       in the documentation and/or other materials provided with the  
 *       distribution.  
 *     * Neither the name of Samsung Electronics Co., Ltd. nor the names of its  
 *       contributors may be used to endorse or promote products derived from  
 *       this software without specific prior written permission.  
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */



var SAAgent = null;
var SASocket = null;
var CHANNELID = 104;
var ProviderAppName = "HelloAccessoryProvider";

// ///////////////////
var canvas;
var context;
var image;
//var data111="45-5000";
var temp;
var angle;
var district;

function createHTML(log_string) {
	var log = document.getElementById('resultBoard');
	log.innerHTML = log.innerHTML + "<br> : " + log_string;
	console.log("createHTML");
}

function onerror(err) {
	console.log("onerror");
	alert("Please! Check Host Device");
	
	//disconnect();
}

var agentCallback = {
	onconnect : function(socket) {
		console.log("onconnect");
		SASocket = socket;
		SASocket.setDataReceiveListener(onreceive); // set receiveListener
		SASocket.sendData(CHANNELID, ""); // send
		SASocket.setSocketStatusListener(function(reason) {
			console.log("Service connection lost, Reason : [" + reason + "]");
			disconnect();
		});
	},
	onerror : onerror
};

var peerAgentFindCallback = {
	onpeeragentfound : function(peerAgent) {
		try {
			console.log("onpeeragentfound");
			if (peerAgent.appName == ProviderAppName) {
				SAAgent.setServiceConnectionListener(agentCallback);
				SAAgent.requestServiceConnection(peerAgent);
			} else {
				alert("Not expected app!! : " + peerAgent.appName);
			}
		} catch (err) {
			console
					.log("exception [" + err.name + "] msg[" + err.message
							+ "]");
		}
	},
	onerror : onerror
}

function onsuccess(agents) {
	try {
		console.log("onsuccess");
		if (agents.length > 0) {
			SAAgent = agents[0];

			SAAgent.setPeerAgentFindListener(peerAgentFindCallback);
			SAAgent.findPeerAgents();
		} else {
			alert("Not found SAAgent!!");
		}
	} catch (err) {
		console.log("exception [" + err.name + "] msg[" + err.message + "]");
	}
}

function connect() {
	console.log("connect");
	if (SASocket) {
		alert('Already connected!');
		return false;
	}
	try {
		webapis.sa.requestSAAgent(onsuccess, onerror);
	} catch (err) {
		console.log("exception [" + err.name + "] msg[" + err.message + "]");
	}
}

function disconnect() {
	try {
		console.log("disconnect");
		if (SASocket != null) {
			SASocket.close();
			SASocket = null;
		}
		tizen.application.getCurrentApplication().exit();
	} catch (err) {
		console.log("exception [" + err.name + "] msg[" + err.message + "]");
		tizen.application.getCurrentApplication().exit();
	}
}

function onreceive(channelId, data) {
	console.log("onreceive");
	console.log("receive " + data)
	data111="45-500";
	temp=data.split(":");
	angle=temp[0];
	district=temp[1];
	//angle=data;
	on_Draw();
}

function fetch() {
	console.log("fetch");
	try {
		SASocket.setDataReceiveListener(onreceive);
		SASocket.sendData(CHANNELID, "");
	} catch (err) {
		console.log("exception [" + err.name + "] msg[" + err.message + "]");
	}
}
// ///////////////////

function on_Load() {
	connect();
	console.log("on_Load");
	this.canvas = document.getElementById("can_DrawArea");
	this.context = canvas.getContext("2d");
	this.angle = 0;
	this.image = new Image();
	this.image.addEventListener("load", this.on_LoadImage, false);
	this.image.src = "ani.png";

	context.save();
	
	// add eventListener for tizenhwkey
	document.addEventListener('tizenhwkey', function(e) {
		if (e.keyName == "back"){
			console.log("back");
			tizen.application.getCurrentApplication().exit();
		}
	});
}
function on_LoadImage() {
	console.log("on_LoadImage");
	on_Draw();
}
function on_Draw() {
	console.log("on_Draw");
	context.save();
	context.clearRect(0, 0, 320, 320);
	context.translate(160, 160);
	context.rotate(angle * Math.PI / 180);
	context.drawImage(image, -160, -160, 320, 320);
	context.font = '25px "Arial"';
	context.fillText(district, -20, 120);
	context.restore();
}
/*
window.onload = function() {
	console.log("12");
	// add eventListener for tizenhwkey
	document.addEventListener('tizenhwkey', function(e) {
		if (e.keyName == "back")
			tizen.application.getCurrentApplication().exit();
	});
	
};*/