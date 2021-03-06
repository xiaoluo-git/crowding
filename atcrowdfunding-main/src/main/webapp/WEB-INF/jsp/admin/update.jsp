<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="GB18030">
<head>
<meta charset="GB18030">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<%@include file="/WEB-INF/jsp/common/css.jsp"%>
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/common/navbar.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">
			<jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">数据列表</a></li>
					<li class="active">修改</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-heading">
						表单数据
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<form role="form" action="admin/doUpdate" method="post">
							<input type="hidden"  name="id" value="${tAdmin.id }" >
							<input type="hidden"  name="pageNum" value="${param.pageNum }" >
							
							<div class="form-group">
								<label for="exampleInputPassword1">登陆账号</label> <input
									type="text" class="form-control" id="loginacct"
									name="loginacct" value="${tAdmin.loginacct }" placeholder="请输入登陆账号">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">用户名称</label> <input
									type="text" class="form-control" id="username"
								name="username" value="${tAdmin.username }" 	placeholder="请输入用户名称">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">邮箱地址</label> <input type="email"
									class="form-control" id="email" name="email" value="${tAdmin.email }" 
									placeholder="请输入邮箱地址">
								<p class="help-block label label-warning">请输入合法的邮箱地址, 格式为：
									xxxx@xxxx.com</p>
							</div>
							<button id="updatebtn" type="button" class="btn btn-success">
								<i class="glyphicon glyphicon-plus"></i> 更新
							</button>
							<button type="button" class="btn btn-danger">
								<i class="glyphicon glyphicon-refresh"></i> 重置
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="/WEB-INF/jsp/common/js.jsp"%>
	<script type="text/javascript">
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});
			$("#updatebtn").click(function() {

				$("form").submit();
			})

		});
	</script>
</body>
</html>
