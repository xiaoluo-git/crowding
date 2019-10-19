<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta charset="UTF-8">
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

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/common/navbar.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">
			<jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="conditionInput" class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button  type="button" class="btn btn-warning">
								<i id="likeSelect" class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<security:authorize access="hasRole('PM - 项目经理')">
							<button id="addBtn" type="button" class="btn btn-primary"
								style="float: right;">
								<i class="glyphicon glyphicon-plus"></i> 新增
							</button>
						</security:authorize>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination">

											</ul>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 添加模态框 -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加角色</h4>
				</div>
				<div  class="addModal-body">
					<input type="text" class="form-control" id="addRole" placeholder=" 请输入角色名称">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveBtn" type="button" class="btn btn-primary">添加</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改模态框 -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"> 修改角色</h4>
				</div>
				<div  class="addModal-body">
					<input type="text" class="form-control" id="roleName" name="roleName" placeholder=" 请输入角色名称">
					<input type="hidden"  id="roleId" name="roleId">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="updateBtn" type="button" class="btn btn-primary">修改</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 分派模态框 -->
	<div class="modal fade" id="assignModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">分派权限</h4>
				</div>
				<div  class="addModal-body">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="assignBtn" type="button" class="btn btn-primary">分派</button>
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

			loadData(1);
		});

		//===============加载数据===============================
		var initParam = {
			pageNum : 1,
			pageSize : 3
		}
		function loadData(pageNum) {
			initParam.pageNum = pageNum;
			$("tbody").empty();
			$
					.ajax({
						type : 'GET',
						url : 'role/loadData',
						data : initParam,
						beforeSend : function() {
							index = layer.load(0, {
								time : 1000 * 10
							});
							return true;
						},
						success : function(result) {
							layer.close(index);
							console.log(result);
							//装配数据
							
							renderData(result);
							
							//显示分页条
							showPage(result);
						}
					});
		}
		
		function renderData(result){
			var roles = result.list;
			$.each(
					roles,
					function(index, role) {
					var tr = $('<tr></tr>');
					tr.append($('<td>'+ (index + 1)+ '</td>'))
					  .append($('<td><input type="checkbox"></td>'))
					  .append($('<td>'+ role.name+ '</td>'));
					var td = $('<td></td>');
					td.append($('<button type="button" roleId="'+ role.id +'" class="assignClass btn btn-success btn-xs" "><i class=" glyphicon glyphicon-check"></i></button> '))
					  .append($('<span>&nbsp;</span><button roleId="'+ role.id +'" type="button" class="updateClass btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>'))
					  .append($('<span>&nbsp;</span><button roleId="'+ role.id +'" type="button" class="deleteClass btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>'));
					tr.append(td);
					$("tbody").append(tr);
					})
		}
		
		/* <li class="disabled"><a href="#">上一页</a></li>
		<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
		<li><a href="#">2</a></li>
		<li><a href="#">3</a></li>
		<li><a href="#">4</a></li>
		<li><a href="#">5</a></li>
		<li><a href="#">下一页</a></li> */
		function showPage(result) {
			$(".pagination").empty();
			if (result.isFirstPage) {
				$(".pagination").append(
						$('<li class="disabled"><a >上一页</a></li>'))
			} else {
				$(".pagination").append(
						$('<li ><a onclick="loadData(' + result.prePage
								+ ')">上一页</a></li>'))
			}
			var navs = result.navigatepageNums;
			$.each(navs,function(index, nav) {
					if (result.pageNum == nav) {
						
						$(".pagination").append($('<li class="active"><a onclick="loadData('+ nav+ ')">'+ nav + ' <span class="sr-only">(current)</span></a></li>'))
					
						} else {
									$(".pagination").append($('<li><a onclick="loadData('+ nav + ')">'+ nav + '</a></li>'))
						  		 }

					})

			if (result.isLastPage) {
				$(".pagination").append(
						$('<li class="disabled"><a >下一页</a></li>'))
			} else {
				$(".pagination").append(
						$('<li ><a onclick="loadData(' + result.nextPage
								+ ')">下一页</a></li>'))
			}

		}

		//===================新增数据==================
		$("#addBtn").click(function() {
			$('#addModal').modal({
				show:true,
				backdrop:'static',
				keyboard:false
			})
		})
		
		$("#saveBtn").click(function(){
			var name = $("#addRole").val();
			if(name==null || $.trim(name) == ""){
				layer.msg('请输入内容',{time:1000})
			}else{
				
				$.post("role/add",{name:name},function(result){
					if("ok" == result){
						layer.msg('添加成功！',{time:1000},function(){
							$("#addRole").val("");
							$("#addModal").modal('hide');
							loadData(1);
						})
					}else if(403 == result){
						layer.msg("您没有此权限",{time:1000,icon:5,anim:6},function(){
							$("#addRole").val("");
							$("#addModal").modal('hide');
						})
					}else{
						layer.msg('添加失败！',{time:1000})
					}
				})
			}
		});
		
		//===================修改信息==============
		$("tbody").on('click',".updateClass",function(){
			var id = $(this).attr("roleId"); 
			
			$.get("role/toUpdate",{id:id},function(result){
				console.log(result);
				$("#updateModal").modal('show');
				$("#roleId").val(result.id);
				$("#roleName").val(result.name);
			})
		})
		
		$("#updateBtn").click(function(){
			var id = $("#roleId").val();
			var name= $("#roleName").val();
			$.post("role/doUpdate",{id:id,name:name},function(msg){
				
				if("ok" == msg){
					layer.msg("更新成功！",{time:1000,icon:6});
					$("#updateModal").modal('hide');
					loadData(1);
					
				}else{
					layer.msg("更新失败！",{time:1000,icon:5});
				}
				
			})
		})
		
		//=========删除===============
		$("tbody").on("click",".deleteClass",function(){
			var id = $(this).attr("roleId");
			layer.confirm("确定要删除吗？",function(index){
				$.post("role/delete",{id:id},function(msg1){
					if("ok" == msg1){
						
						layer.msg("删除成功",{time:1000});
						loadData(1);
					}else{
						layer.msg("删除失败",{time:1000});
					}
					
				})
				layer.close(index);
			},function(index){
				layer.close(index);
			})
		})
		//===================模糊查询============
		$("form button").click(function(){
			var condition = $("#conditionInput").val();
			$.ajax({
				
				data:{condition:condition},
				url:'role/loadData',
				success:function(result){
					
					$("tbody").empty();
					$(".pagination").empty();
					renderData(result);
					
					showPage(result);
				}
				
			})
		})
		//===================授权======================
			var roleId = "";
			$("tbody").on("click",".assignClass",function(){
				roleId = $(this).attr("roleId");
				$("#assignModal").modal({
					show:true,
					keyboard:false,
					backdrop:"static"
				})
				loadTree();
			})
		
		
		function loadTree(){
		var setting = {
				data: {
					simpleData: {enable: true,pIdKey: "pid"},
					key:{name:"title"}
				},
				check: {
					enable: true
				},
				view:{
					addDiyDom: function(treeId, treeNode){
						var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
						if ( treeNode.icon ) {
							//icoObj.removeClass("#"+treeNode.tId+"_ico").addClass( treeNode.icon);
							$("#"+treeNode.tId+"_ico").removeClass();//.addClass();
							$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>");
							}
						}
					}
			}
		$.get("premission/loadTree",function(result){
				$.fn.zTree.init($("#treeDemo"), setting, result);
				$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
				$.get("role/listPermissionIdByRoleId",{roleId:roleId},function(result){
					$.each(result,function(){
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var node = treeObj.getNodeByParam("id", this, null);
						treeObj.checkNode(node, true, false);
					})
				})
			})
		}
			
		$("#assignBtn").click(function(){
			//获取选中的数据
			var json = {roleId:roleId,}
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getCheckedNodes(true);
			
			$.each(nodes,function(i,e){
				
				json['ids['+i+']'] = e.id;
			})
			
			$.ajax({
				type:'POST',
				url:'role/assignPermissionToRole',
				data:json,
				success:function(msg){
					if("ok" == msg){
						layer.msg("分配成功",{time:1000,icon:6,anim:5},function(){
							$("#assignModal").modal('hide');
						})
					}else{
						layer.msg("分配失败",{time:1000,icon:5,anim:5},function(){
							$("#assignModal").modal('hide');
						})
					}
				}
			});
			
		})
		
		
	</script>
</body>
</html>
