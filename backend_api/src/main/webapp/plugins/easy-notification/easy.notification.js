/*
 * 	Easy Notification - jQuery plugin
 *	written by Alen Grakalic	
 *	http://cssglobe.com
 *
 *	Copyright (c) 2011 Alen Grakalic (http://cssglobe.com)
 *	Dual licensed under the MIT (MIT-LICENSE.txt)
 *	and GPL (GPL-LICENSE.txt) licenses.
 *
 *	Built for jQuery library
 *	http://jquery.com
 *
 */

jQuery.easyNotification = function (options, messageType) {

    var defaults = {	
		id: 'easyNotification',	
		parent: '#notification_messsage',
		prepend: true,		
		sibling: '',
		before: true,
		closeClassName: 'close',
		closeText: 'X',
		delay: 0,
		autoClose: true,
		duration: 5000,
		callback: function(){}
	}; 
    
	if(typeof options == 'string') defaults.text = options;
	var options = jQuery.extend(defaults, options); 
	
	var obj, timeout;
	
	function hide(){
		$(obj).slideUp('fast');
		options.callback();	
		clearTimeout(timeout);
	}
	
	function show(){
		
		$('#'+ options.id).remove();
		clearTimeout(timeout);
		var iconName;
		
		if (messageType == "success") iconName = "true.png";
		else iconName = "false.png";
		
		obj = $('<div id="'+ options.id +'"><div class="img_wrapper"><img src="../icons/'+ iconName +'"></div><div class="text_wrapper">'+ options.text +'</div></div>');
		$('<span class="'+ options.closeClassName +'">'+ options.closeText +'</span>')
			.click(function(){hide();})
			.appendTo(obj);
		if(options.sibling != ''){
			if(options.before) {
				$(obj).hide().insertBefore(options.sibling).fadeIn('fast');
			} else {
				$(obj).hide().insertAfter(options.sibling).fadeIn('fast');
			};	
		} else {
			if(options.prepend) {
				$(obj).hide().prependTo(options.parent).fadeIn('fast');
			} else {
				$(obj).hide().appendTo(options.parent).fadeIn('fast');
			};				
		}
		if (options.autoClose) timeout = setTimeout(hide,options.duration);		
		if (messageType != "success") $(obj).css("border-color", "orange");
		
		var notification_width = 400;
		var window_width = $(window).width();
		if (window_width < 500) {
			notification_width = (((window_width / 100) * 90) / 2);
			$(obj).css("width", notification_width);
			$(".text_wrapper").css("text-align", "center");
			$(".text_wrapper").css("padding", "0");
			$("#easyNotification").css("padding", "0");
			$(".img_wrapper").css("visibility", "hidden");
			$(".close").css("visibility", "hidden");
		}	
		$(obj).css("margin-left", -notification_width / 2);
	};
	
	timeout = setTimeout(show,options.delay);
};