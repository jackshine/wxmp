<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>微信菜单管理 微信公众平台管理系统</title>
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
<body style="background-color: #efefef;">  
<div style="height:18px;background-color:#E0E0E0 ;margin-bottom:5px;"></div>
<div class="container">
  <#include "/sysTopMenu.ftl" encoding="utf8"> 
  <div class="row">
	<div class="col-xs-12">
	  <div class="panel panel-info panel-body">
	    <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#aboutMenu"> 自定义菜单的相关说明 </button>
        <div id="aboutMenu" class="collapse in">
			<p>
			自定义菜单能够帮助公众号丰富界面，让用户更好更快地理解公众号的功能。<br>
			1、自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。<br>
			2、一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。<br>
			3、个性化菜单可让公众号的不同用户群体看到不一样的自定义菜单，可以通过以下条件来设置用户看到的菜单：<br>
			&nbsp;&nbsp;&nbsp;&nbsp;用户标签、性别、手机操作系统、地区（用户在微信客户端设置的地区）、语言（用户在微信客户端设置的语言）<br>
	    </div>
	  </div>
	</div>
	<!-- 左面默认菜单 -->
    <div class="col-xs-6" >
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
		  <h3 class="panel-title">默认菜单</h3>
        </div>
        <div class="panel-body">
      
        </div>
  	  </div>
	</div><!-- end of 左面 -->
    <!-- 右面个性化菜单 -->
    <div class="col-xs-6" >
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
		  <h3 class="panel-title">个性化菜单</h3>
        </div>
        <div class="panel-body">
      
        </div>
  	  </div>
	</div><!-- end of 右面 -->
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