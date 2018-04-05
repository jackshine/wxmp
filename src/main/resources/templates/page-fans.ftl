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
   	      粉丝用户管理
        </div>
        <div class="panel-body">
		    <ul class="nav nav-pills nav-stacked">
			  <li id="link_tag" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#tagMgr').show(); "><a href="#">标签管理</a></li>
			  <li id="link_fans" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#fansMgr').show(); "><a href="#">粉丝-标签管理</a></li>
			  <li id="link_black" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#blackMgr').show(); "><a href="#">粉丝-黑名单管理</a></li>
		    </ul>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-9" style="padding-right:0">
      <!-- 标签管理-->
      <div class="row" id="tagMgr" style="display:none;padding:0 5px"> 
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            <button type="button" class="btn btn-primary" id="save" style="margin-left:20px">&nbsp;&nbsp;创建标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-primary" id="save" style="margin-left:20px">&nbsp;&nbsp;更新标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-warning" id="save" style="margin-left:20px">&nbsp;&nbsp;删除标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;查询所有&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <table class="table table-striped  table-bordered table-hover ">
              <thead>
   	            <tr><th width="25%">标签ID</th><th width="50%">标签名称</th><th width="25%">粉丝数量</th></tr>
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
      <!-- 粉丝管理-->
      <div class="row" id="fansMgr" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0" >
            <button type="button" class="btn btn-primary" id="save" style="margin-left:20px">&nbsp;&nbsp;添加标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-warning" id="save" style="margin-left:20px">&nbsp;&nbsp;移除标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-primary" id="save" style="margin-left:20px">&nbsp;&nbsp;修改备注&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <div class="col-xs-7">
              <div class="panel panel-info">
                <div class="panel-heading" style="margin:0" >
	                <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	                  <input type="text" class="form-control" id="name" name="name" value="" maxLength=38 required placeholder="请输入粉丝OPENID">
	                </div>
                    <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;取用户详情&nbsp;&nbsp;</button>
                </div>
                <div class="panel-body">
                用户详细信息
                </div>
              </div>
            </div>
            <div class="col-xs-5">
              <div class="panel panel-info">
                <div class="panel-heading" style="margin:0" >
                  用户标签信息
                </div>
                <div class="panel-body">
                  
                </div>
              </div> 
              <div class="panel panel-info">
                <div class="panel-heading" style="margin:0" >
                  用户备注信息
                </div>
                <div class="panel-body">
                  
                </div>
              </div>           
            </div>            
          </div>
        </div>
      </div>
      <!-- 黑名单管理-->
      <div class="row" id="blackMgr" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            <button type="button" class="btn btn-primary" id="save" style="margin-left:20px">&nbsp;&nbsp;移入黑名单&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-warning" id="save" style="margin-left:20px">&nbsp;&nbsp;移出黑名单&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <div class="panel panel-info">
              <div class="panel-heading" style="margin:0" >
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <input type="text" class="form-control" id="name" name="name" value="" maxLength=38 required placeholder="请输入粉丝OPENID">
	            </div>
                <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;取用户详情&nbsp;&nbsp;</button>
              </div>
              <div class="panel-body">
                用户详细信息
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