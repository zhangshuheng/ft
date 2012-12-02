require(["dgrid/Grid", "dgrid/extensions/Pagination", "dgrid/Selection",
					"dojo/_base/lang", "dojo/_base/declare",
					"dojo/dom-construct", "dojo/dom-form", "dgrid/test/data/base",
					"dojo/data/ItemFileReadStore",
					"dojo/store/Observable",
					"dijit/Tooltip",
					"dojo/domReady!"],
	function(Grid, Pagination, Selection,
			lang, declare, domConstruct, domForm
			, testStore,ItemFileReadStore,Observable,
			Tooltip
			){
		
		var CustomGrid = declare([Grid, Selection, Pagination]);
		
		var getColumns = function(){
			return {
				col1: 'Column 1',
				col2: {label: 'Column2', sortable: false},
				col3: 'Column 3',
				col4: 'Column 4',
				col5: 'Column 5'
			};
		};
		
		var jsonStore = new ItemFileReadStore({url:contextPath+"/module1/jsonlst.do?_t="+Math.random()}); 
		
		var grid = new CustomGrid({
			store: jsonStore,
			query:{a:1,b:2,c:2},
			columns: getColumns(),
			pagingLinks: false,
			pagingTextBox: true,
			firstLastArrows: true,
			pageSizeOptions: [10, 15, 25]
		}, "gridDiv");
		jsonStore.fetch({
			
		});
		grid.on(".dgrid-cell:mouseover", function(evt){
		    var cell = grid.cell(evt);
		    Tooltip.show(cell,cell.element);
//		    console.dir(cell);
		    // cell.element == the element with the dgrid-cell class
		    // cell.column == the column definition object for the column the cell is within
		    // cell.row == the same object obtained from grid.row(evt)
		});
		
});
