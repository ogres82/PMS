




var useRemember="${configure.getString('bdf2.useRememberMeForLogin')}";
window.checkForm=function(){
	var errorInfo=$("#errorInfo");
	var username=$("#username_").val();
	if(username==""){
		errorInfo.html("用户名不能为空!");
		$("#username_").focus();
		return false;
	}
	var password=$("#password_").val();
	if(password==""){
		errorInfo.html("密码不能为空!");
		$("#password_").focus();
		return false;
	}	
	
}

