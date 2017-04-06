function checkform(){
    var username =$("#username").val();
    var password =$("#password").val();
    
    if(username==""){
		$("#username").focus();
        layer.tips('请输入用户名', '#username');
        return false;
    }
    if(password==""){
		$("#password").focus();
        layer.tips('请输入密码', '#password');
        return false;
    }
    $("#fm").submit();
 }
 $(document).keyup(function(event){
	  if(event.keyCode ==13){
		  checkform();
	  }
	});
 
 /*$(function(){
		var source=$(".ritmin").attr("source");
		var li =$(".lft li a[target='"+source+"']").parent().addClass("on");
	});*/
