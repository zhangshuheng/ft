//<!-- Configure Dojo first -->
//http://dojotoolkit.org/documentation/tutorials/1.7/dojo_config/
var dojoConfig = (function(){
//	var base = location.href.split("/");
//	base.pop();
//	base = base.join("/");
//	console.log(base);
	return {
		async: true,
		parseOnLoad: true, isDebug: false,
		ioPublish:true,
		packages: [{
			name: "app",
			location: contextPath+"/static/app"
		},{
			name: "dgrid",
			location: contextPath+"/static/js/dgrid/dgrid"
		},{
			name: "put-selector",
			location: contextPath+"/static/js/dgrid/put-selector"
		},{
			name: "xstyle",
			location: contextPath+"/static/js/dgrid/xstyle"
		}],
		flickrRequest: {
			apikey: "8c6803164dbc395fb7131c9d54843627",
			sort:[{
				attribute: "datetaken",
				descending: true
			}]
		}
	};
})();