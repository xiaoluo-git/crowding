<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="GB18030">
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
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
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
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form id="queryForm" class="form-inline" role="form" style="float:left;" action="admin/index" method="post">

  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input id="condition" class="form-control has-success" name="condition" value="${param.condition }" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button  type="button" class="btn btn-warning" onclick="$('#queryForm').submit()"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button id ="deleteBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
          
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input id="selectAll" type="checkbox"></th>
                  <th>账号</th>
                  <th>名称</th>
                  <th>邮箱地址</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${page.list }" var="admin" varStatus="vs">
              	
	                <tr>
	                  <td>${vs.count}</td>
					  <td><input id="${admin.id }" type="checkbox"></td>
	                  <td>${admin.loginacct }</td>
	                  <td>${admin.username }</td>
	                  <td>${admin.email }</td>
	                  <td>
					      <button type="button"  class="btn btn-success btn-xs" onclick="window.location.href='admin/toAssign?id=${admin.id}'" ><i class=" glyphicon glyphicon-check"></i></button>
					      <button type="button" class=" btn btn-primary btn-xs" onclick="window.location.href='admin/toUpdate?pageNum=${page.pageNum}&id=${admin.id}'"><i class=" glyphicon glyphicon-pencil"></i></button>
						  <button type="button" id="${admin.id }" class="deletebtn btn btn-danger btn-xs" ><i class=" glyphicon glyphicon-remove"></i></button>
					  </td>
	                </tr>
              	</c:forEach>
                  
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
							<c:if test="${page.isFirstPage }">
								<li class="disabled"><a >上一页</a></li>
							</c:if>
							<c:if test="${!page.isFirstPage }">
								<li ><a href="admin/index?condition=${param.condition }&pageNum=${page.pageNum -1 }">上一页</a></li>
							</c:if>
								<c:forEach items="${page.navigatepageNums }" var="i">
									<c:if test="${page.pageNum == i }">
										<li class="active"><a href="admin/index?condition=${param.condition }&pageNum=${i }">${i } <span class="sr-only">(current)</span></a></li>
									</c:if>
									<c:if test="${page.pageNum != i }">
										<li><a href="admin/index?condition=${param.condition }&pageNum=${i }">${i }</a></li>
									</c:if>
								
								</c:forEach>
							<c:if test="${page.isLastPage }">
								<li class="disabled"><a >下一页</a></li>
							</c:if>
							<c:if test="${!page.isLastPage }">
								<li ><a href="admin/index?condition=${param.condition }&pageNum=${page.pageNum + 1 }">下一页</a></li>
							</c:if>
								
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
	
    	<%@include  file="/WEB-INF/jsp/common/js.jsp"%>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    
			    
			   $(".deletebtn").click(function(){
				   var id = $(this).attr("id");
				  var text = $(this).parents("tr").find("td:eq(3)").html();
				   layer.confirm('确定要删除'+ text +'吗？', {
				    	  btn: ['确定','取消'] 
				    	}, function(index){
				    		window.location.href="admin/delete?pageNum=${page.pageNum}&id=" + id;
				    		layer.close(index);
				    	}, function(index){
				    		layer.close(index);
				    	});
			   })
            });
          
            
            //模糊查询
            $("#likeBtn").click(function(){
            	var condition = $("#condition").val();
            })
            //全选
            $("#selectAll").click(function(){
            	var stut = this.checked;
            	$("tbody input").prop("checked",stut);
            })
            
            $("tbody input").click(function(){
            	
            	$("#selectAll").prop("checked",$("tbody input:checked").length == $("tbody input").length);
            })
            
            $("#deleteBtn").click(function(){
            	var str = "" ;
            	$.each($("tbody input:checked"),function(i,n){
            		if(i == 0){
            			str += n.id;
            		}else{
            			str += "," + n.id;
            		}
            	})
            	window.location.href = "admin/batchDelete?pageNum=${page.pageNum}&ids=" + str;
            })
        </script>
  </body>
</html>
    