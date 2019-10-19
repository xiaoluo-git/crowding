<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<%@include  file="/WEB-INF/jsp/common/css.jsp"%>
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form action="doregister"  method="post" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>
		  <div class="form-group has-success has-feedback"> 
			<input type="text" class="form-control" id="loginacct" name="loginacct" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="userpswd" name="userpswd"  placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="email" name="email" placeholder="请输入邮箱地址" style="margin-top:10px;">
			<span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
		  </div>
		 <!--  <div class="form-group has-success has-feedback">
			<select class="form-control" >
                <option>企业</option>
                <option>个人</option>
            </select>
		  </div> -->
        <div class="checkbox">
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="login">我有账号</a>
          </label>
        </div>
        <a id="register" class="btn btn-lg btn-success btn-block"  > 注册</a>
      </form>
    </div>
    <%@include  file="/WEB-INF/jsp/common/js.jsp"%>
    <script type="text/javascript">
    	$("#register").click(function(){
    		
    		$("form").submit();
    	})
    </script>
  </body>
</html>