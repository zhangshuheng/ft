require([ "dijit/form/Textarea", "dijit/form/Button", "dojo/request",
		"dojo/dom", "dojo/request/xhr", "dijit/Dialog", "dijit/form/TextBox",
		"dijit/form/ValidationTextBox", "dijit/registry", "dijit/form/Form",
		"dojo/store/Memory", "dijit/form/FilteringSelect", "dojo/_base/window",
		"dijit/popup", "dojo/_base/fx", "dojo/domReady!" ], function(Textarea,
		Button, request, dom, xhr, Dialog, TextBox, ValidationTextBox,
		registry, Form, Memory, FilteringSelect, win, popup, fx) {
	var addUserDialog = registry.byId("adduserdlg");
	var showadduser = function() {
		if (!addUserDialog) {
			addUserDialog = new Dialog({
				title : "Add User",
				draggable : false,
				style : "width: 350px"
			}, "adduserdlg");

			var userNameTextbox = new ValidationTextBox({
				placeHolder : "Enter username here.",
				required : true,
				invalidMessage : 'cannot be empty',
				name : "name",
			}, "userNameText");

			var accountTextbox = new TextBox({
				placeHolder : "Enter username here.",
				name : "account",
			}, "accountText");
			var passwordTextbox = new TextBox({
				placeHolder : "Enter password here.",
				name : "password",
				type : "password",
			}, "password");
			var sexStore = new Memory({
				data : [ {
					name : "Male",
					id : "M"
				}, {
					name : "Female",
					id : "F"
				} ]
			});
			var filteringSelect = new FilteringSelect({
				name : "sex",
				store : sexStore,
				searchAttr : "name"
			}, "sexSelect");

			var saveBtn = new Button({
				label : "Save User",
			}, "saveuserbtn");
			saveBtn.startup();
			dojo.connect(saveBtn, "onClick", function(event) {
				var form = registry.byId("adduserform");
				if (!form.isValid()) {
					new Dialog({
						title : "Tip",
						draggable : false,
						content : 'form invalid',
						style : "width: 350px"
					}).show();
					return false;
				}
				// Stop the submit event since we want to control form
				// submission.
				dojo.stopEvent(event);

				// The parameters to pass to xhrPost, the form, how to handle
				// it, and the callbacks.
				// Note that there isn't a url passed. xhrPost will extract the
				// url to call from the form's
				// 'action' attribute. You could also leave off the action
				// attribute and set the url of the xhrPost object
				// either should work.
				var xhrArgs = {
					url : contextPath + "/demo/saveuser.do",
					form : "adduserform",
					handleAs : "text",
					load : function(data) {
						addUserDialog.hide();
						var refreshBtn = registry.byId("refreshlist");
						refreshBtn.emit('Click');
					},
					error : function(error) {
						// We'll 404 in the demo, but that's okay. We don't have
						// a 'postIt' service on the
						// docs server.
						new Dialog({
							title : "Tip",
							draggable : false,
							content : error.message,
							style : "width: 350px"
						}).show();
					}
				};

				var deferred = dojo.xhrPost(xhrArgs);
			});
		};
		var form = registry.byId("adduserform");
		form.reset();
		addUserDialog.show();
	};

	var adduserBtn = new Button({
		label : "Add User",
		onClick : showadduser
	}, "adduserbtn");

	dojo.subscribe("usereditbtnclick", null, function(userid) {
		var xhrArgs = {
			url : contextPath + "/demo/loaduser.do?userid=" + userid,
			form : "adduserform",
			handleAs : "json",
			load : function(data) {
				showadduser();
				var form = registry.byId("adduserform");
				form.reset();
				form.setValues(data);
				var pwdfield = registry.byId("password");
				pwdfield.set('disabled', true);
			},
			error : function(error) {
				// We'll 404 in the demo, but that's okay. We don't have a
				// 'postIt' service on the
				// docs server.
				dojo.byId("response").innerHTML = "error:" + error;
			}
		};
		var deferred = dojo.xhrPost(xhrArgs);
	});
});