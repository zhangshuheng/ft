require(["dgrid/Grid", "dgrid/extensions/Pagination", "dgrid/Selection",
					"dojo/_base/lang", "dojo/_base/declare",
					"dojo/dom-construct", "dojo/dom-form",
					"dojo/store/JsonRest",
					"dgrid/TouchScroll",
					"dojo/_base/Deferred",
					"dijit/form/Button",
					"dgrid/editor",
					"dojo/domReady!"],
	function(Grid, Pagination, Selection,
			lang, declare, domConstruct, domForm,JsonRest,TouchScroll
			,Deferred,Button,Editor
	){
	
	   var getQueryParam = function(){
		   return {a:1,b:2,c:3};
	   }
		var restStore = new JsonRest({
			idProperty:'userid',
		    target: contextPath+"/demo/listuser.do?id=",
		    sortParam: "sortBy"
		});
	   
		window.deleteUserBYId=function(userid){
			if(!confirm("删除用户"))return ;
			restStore.remove(userid).then(function(results){
				grid.set("query", getQueryParam());
			});
			return;
//			var xhrArgs = {
//		    	      url: contextPath+"/demo/deluser.do?userid="+userid,
//		    	      handleAs: "text",
//		    	      load: function(data){
//		    	  		grid.set("query", getQueryParam());
//		    	      },
//		    	      error: function(error){
//		    	        // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
//		    	        // docs server.
//		    	        dojo.byId("response").innerHTML = error;
//		    	      }
//		    	    };
//			
//			 var deferred = dojo.xhrGet(xhrArgs);
		};
		
		window.SaveChange = function(userid){
			console.log(grid.dirty)
//			restStore.put(object, options)
		}
		
		
		if (navigator.geolocation) {
		   //一次性获取位置的请求
		    navigator.geolocation.getCurrentPosition(function(pos){
		    	console.log(pos);
		    });
		}
		//如果是iPAD或者手机设备，则添加TouchScroll
//		var CustomGrid = declare([Grid, Selection, Pagination,TouchScroll]);
		var CustomGrid = declare([Grid, Selection, Pagination,Editor]);
		
		var getColumns = function(){
			return {
				userid: {label:'userid'},
				name: {label: 'name', sortable: false,formatter: function(v,pa2){
		              return '<a href="http://www.baidu.com">--'+v+'--</a>';
		        }},
				account: Editor({name: "account"}, "text", "dblclick"),//,autoSave:true
				enable: {label: 'ops',get:function(item){
					return item;
				},formatter:function(item){
					var renderStr = "<input type='button' value='Edit'  onclick='dojo.publish(\"usereditbtnclick\", [ "+item.userid+" ]);' />";
					renderStr=renderStr+"  <input type='button' value='Delete' onclick='deleteUserBYId("+item.userid+")' />";
					renderStr=renderStr+"  <input type='button' value='Save' onclick='SaveChange("+item.userid+")' />";
					return renderStr;
				}}
			};
		};
		
		var grid = new CustomGrid({
			store:restStore,
			query:getQueryParam(),
			selectionMode:"single",
			columns: getColumns(),
			pagingLinks: true,
			pagingTextBox: true,
			firstLastArrows: true,
			pageSize:20,
//			pageSizeOptions: [10,20]
		}, "userDiv");
		
		var refreshuser = function(){
			grid.set("query", getQueryParam());
		};
	     var refreshBtn = new Button({
	      	label: "Refresh",
	      	onClick: refreshuser
	      }, "refreshlist");
});
