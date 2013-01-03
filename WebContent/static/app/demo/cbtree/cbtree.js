require([ "dojo/_base/connect", "dojo/data/ItemFileWriteStore",
		"dojo/domReady", "cbtree/Tree","dojo/store/JsonRest",
		"cbtree/models/ForestStoreModel" ],
function(connect, ItemFileWriteStore, domReady, Tree,JsonRest, ForestStoreModel) {
	function checkBoxClicked(item, nodeWidget, evt) {
//		var len = store.each
		console.log(store);
//		alert("The new state for " + this.getLabel(item) + " is: "
//				+ nodeWidget.get("checked"));
	}
	var store = new ItemFileWriteStore({
		url : contextPath+"/demo/treejson.do"
	});
//	var store = new JsonRest({
//		idProperty:'name',
//	    target: contextPath+"/demo/treejson.do",
//	    sortParam: "sortBy"
//	});
	
	var model = new ForestStoreModel({
		store : store,
		query : {
			type : 'parent'
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