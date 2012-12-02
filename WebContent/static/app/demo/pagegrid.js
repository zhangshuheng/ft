require(["dgrid/Grid", "dgrid/extensions/Pagination", "dgrid/Selection",
					"dojo/_base/lang", "dojo/_base/declare",
					"dojo/dom-construct", "dojo/dom-form",
					"dojo/store/JsonRest",
					"dgrid/TouchScroll",
					"dojo/_base/Deferred",
					"dojo/domReady!"],
	function(Grid, Pagination, Selection,
			lang, declare, domConstruct, domForm,JsonRest,TouchScroll
			,Deferred
			){
	
//		if (params === undefined || params === null) {
		   var params = {
		        //start: 0,
		        //limit: 0,
		        act: 'get-data',
		        a:1,b:2,c:3,d:4,c:"&#^&*&?d=d"
		    }
//		}
		
		var restStore = new JsonRest({
		    target: contextPath+"/module1/jsonlst.do"
		});
		
		var CustomGrid = declare([Grid, Selection, Pagination,TouchScroll]);
		
		var getColumns = function(){
			return {
				col1: {label:'Column 1',width:'1000px'},
				col2: {label: 'Column2', sortable: false},
				col3: 'Column 3',
				col4: 'Column 4',
				col5: 'Column 5'
			};
		};
		var grid = new CustomGrid({
			store: restStore,
			query:params,
//			subRows: [
//			          [
//			              { field: "col1", label: "ID" },
//			              { field: "col2", label: "Name" }
//			          ],
//			          [
//			              { field: "col3", label: "Description", colSpan: 2 }
//			          ]
//			      ],
			columns: getColumns(),
			pagingLinks: true,
			pagingTextBox: true,
			firstLastArrows: true,
			pageSize:20,
			pageSizeOptions: [10,20]
		}, "gridDiv");
//		grid.set("query", params);
});
