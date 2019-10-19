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
							<i class="glyphicon glyphicon-th"></i>许可权限管理
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
        <h4 class="modal-title">添加权限</h4>
      </div>
      <div class="modal-body">
      		<input type="hidden" id="pid">
        <div class="form-group">
            <label for="recipient-name" class="control-label">name:</label>
            <input type="text" class="form-control" id="name">
          </div>
        <div class="form-group">
            <label for="recipient-name" class="control-label">title:</label>
            <input type="text" class="form-control" id="title">
          </div>
        <div class="form-group">
            <label for="recipient-name" class="control-label">icon:</label>
            <input type="text" class="form-control" id="icon">
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button id="addBtn" type="button" class="btn btn-primary">添加</button>
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
        <h4 class="modal-title">修改权限</h4>
      </div>
      <div class="modal-body">
      		<input type="hidden" id="id">
        <div class="form-group">
            <label for="recipient-name" class="control-label">name:</label>
            <input type="text" class="form-control" id="name">
          </div>
        <div class="form-group">
            <label for="recipient-name" class="control-label">title:</label>
            <input type="text" class="form-control" id="title">
          </div>
        <div class="form-group">
            <label for="recipient-name" class="control-label">icon:</label>
            <input type="text" class="form-control" id="icon">
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button id="updateBtn" type="button" class="btn btn-primary">修改</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	
	
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
			loadTree();
		});
	
		
	//加载树
	
	function loadTree(){
		var setting = {
				data: {
					simpleData: {enable: true,pIdKey: "pid"},
					key:{name:"title"}
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
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addPermission('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
						} else if ( treeNode.level == 1 ) {
							 s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updatePermission('+ treeNode.id +')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							if (treeNode.children.length == 0) {
							 	s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deletePermission('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							}
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addPermission('+ treeNode.id +')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>'; 
							
						} else if ( treeNode.level == 2 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updatePermission('+ treeNode.id +')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deletePermission('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							
						}
		
						s += '</span>';
						aObj.after(s);
					},
					removeHoverDom: function(treeId, treeNode){
						$("#btnGroup"+treeNode.tId).remove();
					}
				}
			};
		var zcodes;
		$.get("premission/loadTree",function(result){
			zcodes = result;
			zcodes.push({ id:0, title:"权限菜单",icon:"glyphicon glyphicon-list"});
			$.fn.zTree.init($("#treeDemo"), setting, zcodes);
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			treeObj.expandAll(true);	
		})
	
	}
		
	function addPermission(pid){
		$("#addModal input[id='pid']").val(pid);
		$("#addModal").modal({
			show:true,
			keyboard:false,
			backdrop:"static"
		})
	}
	
	$("#addBtn").click(function(){
		var pid = $("#addModal input[id='pid']").val();
		var name = $("#addModal input[id='name']").val();
		var icon = $("#addModal input[id='icon']").val();
		var title = $("#addModal input[id='title']").val();
		$.post("permission/add",{pid:pid,name:name,icon:icon,title:title},function(result){
			if("ok" == result){
				layer.msg("添加成功",{icon:6,time:1000});
				$("#addModal input[id='pid']").val("");
				$("#addModal input[id='name']").val("");
				$("#addModal input[id='icon']").val("");
				 $("#addModal input[id='title']").val("");
				 $("#addModal").modal('hide');
				 loadTree();
			}else{
				$("#addModal input[id='pid']").val("");
				$("#addModal input[id='name']").val("");
				$("#addModal input[id='icon']").val("");
				 $("#addModal input[id='title']").val("");
				layer.msg("添加失败",{time:1000,icon:5});
			}
			
		})
	})
	
	//修改
	function updatePermission(id){
		$("#updateModal").modal({
			show:true,
			keyboard:false,
			backdrop:"static"
		});
		$.get("permission/toUpdate",{id:id},function(result){
			
			$("#updateModal input[id='id']").val(result.id);
			$("#updateModal input[id='name']").val(result.name);
			$("#updateModal input[id='icon']").val(result.icon);
			 $("#updateModal input[id='title']").val(result.title);
		})
	}
	
	$("#updateBtn").click(function(){
		var id = $("#updateModal input[id='id']").val();
		var name = $("#updateModal input[id='name']").val();
		var icon = $("#updateModal input[id='icon']").val();
		var title = $("#updateModal input[id='title']").val();
		$.post("permission/doUpdate",{id:id,name:name,icon:icon,title:title},function(msg){
			if("ok" == msg){
				layer.msg("更新成功",{tiem:1000,icon:6,anim:6});
				$("#updateModal").modal('hide');
				loadTree();
			}else{
				layer.msg("更新失败",{tiem:1000,icon:5,anim:6});
				$("#updateModal").modal('hide');
			}
		})
	})
	//删除
	function deletePermission(id){
		layer.confirm("确定要删除吗？",{btn:['确定','取消']},function(index){
			$.post("permission/delete",{id:id},function(msg){
				if("ok" == msg){
					layer.msg("删除成功",{time:1000,icon:6,anim:6});
					loadTree();
				}else{
					layer.msg("删除失败",{time:1000,icon:5,anim:6});
				}
			})
			layer.close(index);
		},function(index){
			layer.close(index);
		})	
	}
	</script>
</body>
</html>
