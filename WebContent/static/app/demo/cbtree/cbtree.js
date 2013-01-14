require([ "dojo/_base/connect", "dojo/data/ItemFileWriteStore",
		"dojo/domReady", "cbtree/Tree", "dojo/store/JsonRest",
		"cbtree/models/ForestStoreModel" ], function(connect,
		ItemFileWriteStore, domReady, Tree, JsonRest, ForestStoreModel) {
	function checkBoxClicked(item, nodeWidget, evt) {
		// Define a callback that fires when all the items are returned.
		var gotList = function(items, request) {
			dojo.forEach(items, function(item) {
				if (item.checked[0]) {
					console.log(item);
				}
			});
		}
		var gotError = function(error, request) {
			alert("The request to the store failed. " + error);
		}
		// Invoke the search
		store.fetch({
			query : {
				checked : true
			},
			onComplete : gotList,
			onError : gotError
		});
	}
	var store = new ItemFileWriteStore({
		url : contextPath + "/demo/treejson.do"
	});

	var model = new ForestStoreModel({
		store : store,
		query : {
			type : 2
		},
		rootLabel : 'The Family'
	});
	var tree = new Tree({
		model : model,
		id : "MenuTree",
		branchIcons : true,
		branchReadOnly : false,
		checkBoxes : true,
		nodeIcons : true
	});

	connect.connect(tree, "onCheckBoxClick", model, checkBoxClicked);
	domReady(function() {
		tree.placeAt('CheckboxTree');
	});
});