<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
//添加模态框	
<div id="addModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">添加菜单</h4>
      </div>
      <div class="modal-body">
      	<input type="hidden" name="pid">
       	<div class="form-group">
            <label for="recipient-name" class="control-label">name:</label>
            <input type="text" class="form-control" name="name">
         </div>
       	<div class="form-group">
            <label for="recipient-name" class="control-label">icon:</label>
            <input type="text" class="form-control" name="icon">
         </div>
       	<div class="form-group">
            <label for="recipient-name" class="control-label">url:</label>
            <input type="text" class="form-control" name="url">
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button id="addBtn" type="button" class="btn btn-primary">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
//修改模态框	
<div id="updateModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">修改菜单</h4>
      </div>
      <div class="modal-body">
      	<input type="hidden" name="id">
       	<div class="form-group">
            <label for="recipient-name" class="control-label">name:</label>
            <input type="text" class="form-control" name="name">
         </div>
       	<div class="form-group">
            <label for="recipient-name" class="control-label">icon:</label>
            <input type="text" class="form-control" name="icon">
         </div>
       	<div class="form-group">
            <label for="recipient-name" class="control-label">url:</label>
            <input type="text" class="form-control" name="url">
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button id="updateBtn" type="button" class="btn btn-primary">更新</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

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
					<ul id="permissionTree" class="ztree"></ul>
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
		
			loadTree()
				
		
		});
		
		function loadTree(){
			//ztree
			var setting = {
					
					data: {
						simpleData: {
							enable: true,
							pIdKey: "pid",
						},
						key:{
							children: "childs",
						}
			
					},
					
					view:{
						addDiyDom: function(treeId, treeNode){
							var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
							if ( treeNode.icon ) {
								//icoObj.removeClass("#"+treeNode.tId+"_ico").addClass( treeNode.icon);
								$("#"+treeNode.tId+"_ico").removeClass();//.addClass();
								$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>")
							}
						},
						addHoverDom: function(treeId, treeNode){  
							var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
							console.log(treeNode.childs);
							aObj.attr("href", "javascript:;");
							if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
							var s = '<span id="btnGroup'+treeNode.tId+'">';
							if ( treeNode.level == 0 ) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addMenu('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							} else if ( treeNode.level == 1 ) {
								 s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateMenu('+ treeNode.id +')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
								if (treeNode.childs.length == 0) {
								 	s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteMenu('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
								}
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addMenu('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>'; 
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addPermission('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-check-square-o rbg "></i></a>'; 
							} else if ( treeNode.level == 2 ) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateMenu('+ treeNode.id +')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteMenu('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addPermission('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-check-square-o rbg "></i></a>'; 
							}
			
							s += '</span>';
							aObj.after(s);
						},
						removeHoverDom: function(treeId, treeNode){
							$("#btnGroup"+treeNode.tId).remove();
						}
					}
				};
			$.get(
				"menu/loadTree",
				function(result){
					
				result.push({ id:0, name:"系统菜单",icon:"glyphicon glyphicon-list"});

				$.fn.zTree.init($("#treeDemo"), setting, result);
				$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
				
				}
			);
			
		}
		
		
		
		//====================添加菜单===================
			
			function addMenu(id){
				$("#addModal input[name='pid']").val(id);
				$("#addModal").modal({
					show:true,
					keyboard:false,
					backdrop:"static",
				})
		
			}
		
			$("#addBtn").click(function(){
				var pid = $("#addModal input[name='pid']").val();
				var name = $("#addModal input[name='name']").val();
				var icon = $("#addModal input[name='icon']").val();
				var url = $("#addModal input[name='url']").val();
				
				$.ajax({
					type:'POST',
					data:{name:name,icon:icon,url:url,pid:pid},
					url:'menu/doAdd',
					success:function(result){
						if("ok" == result){
							layer.msg("添加成功！",{icon:6,time:1000,anim:6});
							$("#addModal").modal('hide');
							$("#addModal input[name='name']").val("");
							$("#addModal input[name='icon']").val("");
							$("#addModal input[name='url']").val("");
							loadTree();
						}else{
							layer.msg("添加失败！",{time:1000,anim:6});
						}
						
					}
				});
			});
		//====================添加结束===================
			
		//====================修改菜单===================
		function updateMenu(id){
			$("#updateModal input[name='id']").val(id);
			$.get("menu/update",{id:id},function(menu){
				$("#updateModal").modal({
					show:true,
					keyboard:false,
					backdrop:"static",
				})
				$("#updateModal input[name='name']").val(menu.name);
				$("#updateModal input[name='icon']").val(menu.icon);
				$("#updateModal input[name='url']").val(menu.url);
			})
		}
		
		$("#updateBtn").click(function(){
			var id = $("#updateModal input[name='id']").val();
			var name = $("#updateModal input[name='name']").val();
			var icon = $("#updateModal input[name='icon']").val();
			var url = $("#updateModal input[name='url']").val();
			
			$.ajax({
				type:'POST',
				data:{name:name,icon:icon,url:url,id:id},
				url:'menu/doUpdate',
				success:function(result){
					if("ok" == result){
						layer.msg("添加成功！",{icon:6,time:1000,anim:6});
						$("#updateModal").modal('hide');
						loadTree();
					}else{
						layer.msg("添加失败！",{time:1000,anim:6});
					}
					
				}
			});
		})
			
		//====================修改结束===================
			
		//====================删除菜单===================
			
			function deleteMenu(id){
			$.post("menu/delete",{id:id},function(result){
				if("ok" == result){
					layer.msg("添加成功！",{icon:6,time:1000,anim:6});
					loadTree();
				}else{
					layer.msg("添加失败！",{time:1000,anim:6});
				}
			})
		}
			
		//====================删除结束===================
		
		//====================添加权限=====================
		var menuId = "";
		function addPermission(id){
			menuId = id ; 
			$("#assignModal").modal({
				show:true,
				keyboard:false,
				backdrop:"static"
			})
			loadPermissionTree();
		}
		
		function loadPermissionTree(){
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
				$.fn.zTree.init($("#permissionTree"), setting, result);
				$.fn.zTree.getZTreeObj("permissionTree").expandAll(true);
				$.get("menu/listPermissionIdByMenuId",{menuId:menuId},function(result){
					$.each(result,function(){
						
						var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
						var node = treeObj.getNodeByParam("id", this, null);
						treeObj.checkNode(node, true, false);
					})
				})
			})
		}
		
		$("#assignBtn").click(function(){
			//获取选中的数据
			var json = {menuId:menuId,};
			var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
			var nodes = treeObj.getCheckedNodes(true);
			$.each(nodes,function(i,e){
				json['ids['+i+']'] = e.id;
			})
			console.log(json);
			if(Object.keys(json).length  ==  1){
				layer.msg("请选择权限后在分配",{time:1000})
			}else{
				$.ajax({
					type:'POST',
					url:'menu/assignPermissionToMenu',
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
			}
			
		})
		
	</script>
</body>
</html>
