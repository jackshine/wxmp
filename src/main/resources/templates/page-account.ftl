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
	<!-- 左面功能菜单 -->
    <div class="col-xs-3" style="padding-left:0">
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
   	      公众账号管理---永久二维码scene
        </div>
	   	<ul class="list-group">
	       <li class="list-group-item"><a class="go_link" href=''>SCENEID-1</a></li>
	       <li class="list-group-item"><a class="go_link" href=''>SCENEID-2</a></li>
	       <li class="list-group-item"><a class="go_link" href=''>SCENEID-3</a></li>
	       <li class="list-group-item"><a class="go_link" href=''>SCENEID-4</a></li>
	       <li class="list-group-item"><a class="go_link" href=''>SCENEID-5</a></li>
	       <li class="list-group-item"><a class="go_link" href=''>SCENEID-6</a></li>
	   	</ul>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-9" style="padding-right:0">
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
          <p>二维码ticket：</p>
          <p>二维码URL：</p>
        </div>
        <div class="panel-body">
          <img style="width:50%;margin:0 25%" src="${contextPath}/images/tmp.jpg" alt="公众号二维码"/>
        </div>
      </div>  
    </div><!-- end of 右面详细信息 -->
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