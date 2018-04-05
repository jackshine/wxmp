<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>欢迎登陆 微信公众平台管理系统</title>
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
	<div class="templatemo-content-widget templatemo-login-widget white-bg">
		<header class="text-center">
          <div class="square"></div>
          <h1>欢迎</h1>
        </header>
        <form action="login" class="templatemo-login-form" method="post">
        	<div class="form-group">
        		<div class="input-group">
	        		<div class="input-group-addon"><i class="fa fa-user fa-fw"></i></div>	        		
	            <input class="form-control" type="text" name="username" value= "${username!""}" required placeholder="用户名">           
	        </div>	
        	</div>
        	<div class="form-group">
        		<div class="input-group">
	        		<div class="input-group-addon"><i class="fa fa-key fa-fw"></i></div>	        		
	            <input class="form-control" type="password" name="password"  required placeholder="******">           
	        </div>	
        	</div>
			<div class="form-group">
				<button type="submit" class="templatemo-blue-button width-100">登录</button>
			</div>
        </form>
	</div>
	<div class="templatemo-content-widget templatemo-login-widget templatemo-register-widget white-bg">
		<p>还未有账号? 请向管理员索取</p>
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