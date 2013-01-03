 require(["dijit/form/TextBox",
  "dijit/Dialog", "dijit/form/Button",
		"dojo/domReady!" ], function(TextBox, Dialog, Button) {
	var myDialog = new Dialog({
		title : "Login Form",
		draggable : false,
		style : "width: 300px"
	}, "div-body");
	myDialog.show();
	var userNameTextbox = new TextBox({
		placeHolder : "Enter username here.",
		name : "username",
		style : "width:200px"
	}, "userNameText");
	userNameTextbox.startup();
	var passwordTextbox = new TextBox({
		placeHolder : "Enter password here.",
		name : "password",
		type : "password",
		style : "width:200px"
	}, "passwordTextbox");
	passwordTextbox.startup();
});