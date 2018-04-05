<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>微信公众平台管理系统</title>
	<!-- Bootstrap -->
	<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!--Vue -->
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
    <!-- -->
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/templatemo-style.css" rel="stylesheet">
    
</head>
<body class="light-gray-bg">
<div style="height:18px;background-color:#E0E0E0 ;margin-bottom:5px;"></div>
<div class="container">
  <#include "/sysTopMenu.ftl" encoding="utf8"> 
  <div class="row" style="padding:5px 0 0 0;">
    <div class="panel panel-info">
      <div class="panel-heading" style="margin:0">
        <#if operator.roleLvl == "L9">
        <button type="button" class="btn btn-primary" id="add" style="margin-left:20px">&nbsp;&nbsp;新 增&nbsp;&nbsp;</button>
        <button type="button" class="btn btn-warning" id="update" style="margin-left:20px">&nbsp;&nbsp;注 销&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-primary" id="update" style="margin-left:20px">&nbsp;&nbsp;申请客服&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-warning" id="update" style="margin-left:20px">&nbsp;&nbsp;注销客服&nbsp;&nbsp; </button>
        </#if>
        <button type="button" class="btn btn-primary" id="update" style="margin-left:20px">&nbsp;&nbsp;修改基本&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-primary" id="update" style="margin-left:20px">&nbsp;&nbsp;修改客服&nbsp;&nbsp; </button>
      </div>
      <div class="panel-body">
        <table class="table table-striped  table-bordered table-hover ">
          <thead>
   	        <tr><th width="15%">用户名称</th><th width="15%">客服昵称</th><th width="25%">邮箱</th><th width="15%">用户级别</th><th width="15%">状态</th><th width="15%">更新时间</th></tr>
          </thead>
          <tbody> 
            <tr>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr >
              <td colspan="6">
				  分页信息
           	  </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>  
  </div>  
  
</div>

<#if errmsg??>
<!-- 错误提示模态框（Modal） -->
<div class="modal fade " id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorTitle" aria-hidden="false" data-backdrop="static">
	<div class="modal-dialog">
  		<div class="modal-content">
     		<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
        			<h4 class="modal-title" id="errorTitle" style="color:red">错误提示</h4>
     		</div>
     		<div class="modal-body">
       			<p> ${errmsg} </p><p/>
     		</div>
     		<div class="modal-footer">
     			<div style="margin-left:50px">
        			</div>
     		</div>
  		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
$("#errorModal").modal('show');
</script>
</#if>

</body>
</html>