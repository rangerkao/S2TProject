

alert("加載require成功!");


//載入'jquery', 'underscore', 'backbone'三個modual，預設路徑與Main相同
//require(['jquery', 'underscore', 'backbone'], function ($, _, Backbone){
//　　　　// some code here
//});


//自定義modual
//上面的代碼給出了三個模塊的文件名，路徑默認與main.js在同一個目錄（js子目錄）
//如果這些模塊在其他目錄，比如js/lib目錄，則有兩種寫法。一種是逐一指定路徑 "lib/jquery.min.js",
//另一種則是直接改變基目錄（baseUrl）。baseUrl: "js/lib",
//如果某個模塊在另一台主機上，也可以直接指定它的網址，比如："jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min"
/*require.config({
	//baseUrl: "js/CRM",
	paths: {
		"jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"
	}
});

require(['jquery'], function($) {
   // alert($().jquery);
});*/

require(["angular","angular-route","common","cookies"],function(angular,angularRoute,common,cookies){
	  //原本的程式碼
});