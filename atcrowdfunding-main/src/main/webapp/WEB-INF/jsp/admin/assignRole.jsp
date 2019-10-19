<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="GB18030">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<%@include  file="/WEB-INF/jsp/common/css.jsp"%>
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
					<li class="active">分配角色</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-body">
						<form role="form" class="form-inline">
							<div class="form-group">
								<label for="exampleInputPassword1">未分配角色列表</label><br> <select
									id="leftRoleList" class="form-control" multiple size="10"
									style="width: 250px; overflow-y: auto;">
									<c:forEach items="${unAssignList }" var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
									
								</select>
							</div>
							<div class="form-group">
								<ul>
									<li id="leftToRight" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
									<br>
									<li id="rightToLeft" class="btn btn-default glyphicon glyphicon-chevron-left"
										style="margin-top: 20px;"></li>
								</ul>
							</div>
							<div class="form-group" style="margin-left: 40px;">
								<label for="exampleInputPassword1">已分配角色列表</label><br> <select
									id="rightRoleList" class="form-control" multiple size="10"
									style="width: 250px; overflow-y: auto;">
									<c:forEach items="${assignList }" var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
									
								</select>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@include  file="/WEB-INF/jsp/common/js.jsp"%>
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
			
			
		});
		
		$("#leftToRight").click(function(){
			var roles = $("#leftRoleList option:selected");
			var cloneRole = roles.clone();
			var roleIds = "";
			$.each(cloneRole,function(i,role){
				roleIds += "&id=" + $(this).val();
			})
			roleIds += "&adminId=" + ${param.id};
			$.ajax({
				type:'post',
				url:'admin/assignRoleToAdmin',
				data:roleIds,
				success:function(msg){
					if("ok" == msg){
						layer.msg("添加成功",{time:1000,icon:6,anim:6},function(){
							$("#rightRoleList").append($(cloneRole));
							roles.remove();
						});
					}else{
						layer.msg("添加失败",{time:1000,icon:5,anim:6});
					}
					
				}
				
			})
		})
		
		$("#rightToLeft").click(function(){
			var roles = $("#rightRoleList option:selected");
			var cloneRole = roles.clone();
			var roleIds = "";
			$.each(cloneRole,function(i,role){
				if(i==0){
					roleIds +=$(this).val() ;
				}else{
					
				roleIds +="," + $(this).val();
				}
			})
			var adminId = ${param.id};
			$.ajax({
				type:'post',
				url:'admin/deleteRoleForAdmin',
				data:{roleIds:roleIds,adminId:adminId},
				success:function(msg){
					if("ok" == msg){
						layer.msg("删除成功",{time:500,icon:6,anim:6},function(){
							$("#leftRoleList").append($(cloneRole));
							roles.remove();
						});
					}else{
						layer.msg("删除失败",{time:1000,icon:5,anim:6});
					}
					
				}
				
			})
		})
	</script>
</body>
</html>
