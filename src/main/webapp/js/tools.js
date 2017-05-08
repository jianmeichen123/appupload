
var UA = window.navigator.userAgent;
var tools = {
		isQQBrowser : /(?:MQQBrowser | QQ)/.test(UA),
		isWindowsPhone : /(?:Windows Phone)/.test(UA),
		isSymbian : /(?:SymbianOS)/.test(UA) || $.isWindowsPhone,
		isAndroid : /(?:Android)/.test(UA) || /(?:Adr)/.test(UA),
		isIOS: /\(i[^;]+;( U;)? CPU.+Mac OS X/.test(UA),
		isFireFox : /(?:Firefox)/.test(UA),
		isSafari : /(?:Safari)/.test(UA) && !(/(?:Chrome|CriOS)/.test(UA)),
		isEdge : /(?:Edge)/.test(UA),
		isChrome : /(?:Chrome|CriOS)/.test(UA) && !$.isSafari && !$.isEdge,
		isIpad : /(?:iPad|PlayBook)/.test(UA),
		isTablet : /(?:iPad|PlayBook)/.test(UA)||($.isFireFox && /(?:Tablet)/.test(UA)),
		isPhone : /(?:iPhone)/.test(UA) && !$.isTablet,
		isOpen: /(?:Opera Mini)/.test(UA),
		isUC : /(?:UCWEB|UCBrowser)/.test(UA),
		isPc : !$.isPhone && !$.isAndroid && !$.isSymbian,
		isWeiXin : /(?:MicroMessenger)/.test(UA)
		//ua.match(/MicroMessenger/i) == 'micromessenger';
};

		


	
	

	


