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
   	      多媒体素材管理
        </div>
        <div class="panel-body">
		    <ul class="nav nav-pills nav-stacked">
			  <li id="link_temp_image" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">临时-图片素材</a></li>
			  <li id="link_temp_thumb" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">临时-缩略图素材</a></li>
			  <li id="link_temp_voice" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">临时-语音素材</a></li>
		    	  <li id="link_temp_video" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">临时-视频素材</a></li>
			  <li id="link_temp_news" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">临时-图文素材</a></li>
			  <li id="link_perm_image" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">永久-图片素材</a></li>
		    	  <li id="link_perm_thumb" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">永久-缩略图素材</a></li>
			  <li id="link_perm_voice" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">永久-语音素材</a></li>
			  <li id="link_perm_video" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">永久-视频素材</a></li>
		      <li id="link_perm_news" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); "><a href="#">永久-图文素材</a></li>
		    </ul>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-9" style="padding-right:0">
      <!-- 素材管理-->
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            <button type="button" class="btn btn-primary" id="add" style="margin-left:20px">&nbsp;&nbsp;添加素材&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <div class="col-xs-8">
              <div class="panel panel-info">
                <div class="panel-heading" style="margin:0" >
	              <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	                <input type="date" id="startDate" placeholder="请输入开始时间">～<input type="date" id="endDate" placeholder="请输入结束时间">
	              </div>
                  <button type="button" class="btn btn-info" id="search" style="margin-left:10px">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
                </div>
                <div class="panel-body">
                  <table class="table table-striped  table-bordered table-hover ">
                    <thead>
   	                  <tr><th width="25%">MEDIAID</th><th width="50%">URL</th><th width="25%">添加时间</th></tr>
                    </thead>
                    <tbody> 
                      <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="col-xs-4">
              <div class="panel panel-info">
                <div class="panel-heading" style="margin:0" >
                 详情展示
                </div>
                <div class="panel-body">
                  
                </div>
              </div>           
            </div>            
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