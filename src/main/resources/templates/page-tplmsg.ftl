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
    <link href="${contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${contextPath}/css/templatemo-style.css" rel="stylesheet">
    
</head>
<body class="light-gray-bg">
<div style="height:18px;background-color:#E0E0E0 ;margin-bottom:5px;"></div>
<div class="container">
  <#include "/page-top-menu.ftl" encoding="utf8"> 
  <div class="row" style="padding:5px 0 0 0;">
	<!-- 左面功能菜单 -->
    <div class="col-xs-3" style="padding-left:0">
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
   	      模版消息管理
        </div>
        <div class="panel-body">
		    <ul class="nav nav-pills nav-stacked">
			  <li id="link_ind" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#indMgr').show(); "><a href="#">模板行业管理</a></li>
			  <li id="link_tpl" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#tplMgr').show(); "><a href="#">模版管理</a></li>
			  <li id="link_msgSend" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#msgSendMgr').show(); "><a href="#">发送模板消息</a></li>
		    </ul>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-9" style="padding-right:0">
      <!-- 模板行业管理-->
      <div class="row" id="indMgr" style="display:none;padding:0 5px"> 
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
          行业查询设置
          </div>
          <div class="panel-body">
	        <form class="form-horizontal" id="indForm" action="" method ="post" autocomplete="on" role="form" >
	          <div class="form-group">
	            <label for="username" class="col-xs-2 control-label">行业1代码<span style="color:red">*</span></label>
	            <div class="col-xs-5">
	              <input type="text" class="form-control" id="ind1" name="ind1" pattern="\w{1,50}" title="1-50个字符组成" maxLength=20 value="" required placeholder="请输入行业1代码（1-50个字符）">
	            </div>
	          </div>
	          <div class="form-group">
	            <label for="username" class="col-xs-2 control-label">行业2代码<span style="color:red">*</span></label>
	            <div class="col-xs-5">
	              <input type="text" class="form-control" id="ind2" name="ind2" pattern="\w{1,50}" title="1-50个字符组成" maxLength=20 value="" required placeholder="请输入行业2代码（1-50个字符）">
	            </div>
	          </div>	 
              <div class="form-group">
                <div class="col-sm-offset-3 col-sm-10">
                  <button type="button" class="btn btn-info" id="save" style="margin:20px">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-primary" id="save" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
              </div>         
	        </form>
          </div>
          
        </div>
      </div>
      <!-- 模板管理-->
      <div class="row" id="tplMgr" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0" >
            <button type="button" class="btn btn-primary" id="save" style="margin-left:20px">&nbsp;&nbsp;添加模版&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-warning" id="save" style="margin-left:20px">&nbsp;&nbsp;删除模版&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;查询所有&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <div class="col-xs-7 panel panel-info panel-body">
              <table class="table table-striped  table-bordered table-hover ">
              <thead>
   	            <tr><th width="50%">模版ID</th><th width="50%">模版标题</th></tr>
              </thead>
              <tbody> 
                <tr>
                  <td></td>
                  <td></td>
                </tr>
              </tbody>
              </table>
            </div>
            <div class="col-xs-5 panel panel-info panel-body">
              模版ID：<p></p>
              模版标题：<p></p>
              模版行业：<p></p>
              模版次行业：<p></p>
              模版内容：<p></p>
              消息示例：<p></p>
            </div>
          </div>            
        </div>
      </div>
      <!-- 发送消息-->
      <div class="row" id="msgSendMgr" style="display:none;padding:0 5px">
        <div class="panel-body panel panel-info">
	      <form class="form-horizontal" id="basicForm" action="updateBasic" method ="post" autocomplete="on" role="form" >
	      	<div class="form-group">
	          <label for="username" class="col-xs-2 control-label">OPENID<span style="color:red">*</span></label>
	          <div class="col-xs-5">
	            <input type="text" class="form-control" id="openId" pattern="\w{3,50}" maxLength=50 value="" required placeholder="请输入接收者OPENID（3-50个字符）">
	          </div>
	        </div>
	        <div class="form-group">
	          <label for="username" class="col-xs-2 control-label">模板ID<span style="color:red">*</span></label>
	          <div class="col-xs-5">
	            <input type="text" class="form-control" id="tplId" pattern="\w{3,50}" maxLength=50 value="" required placeholder="请输入模板ID（3-50个字符）">
	          </div>
	        </div>
	        <div class="form-group">
	          <label for="username" class="col-xs-2 control-label">消息标题<span style="color:red">*</span></label>
	          <div class="col-xs-5">
	            <input type="text" class="form-control" id="titleName" pattern="\w{3,50}" maxLength=50 required placeholder="请输入消息标题（3-50个字符）">
	          </div>
	          <label for="profession" class="col-xs-2 control-label">颜色<span style="color:red">*</span></label>
              <div class="col-xs-3">
                 <input class="form-control" id="titleColor"  maxLength=100  required placeholder="请输入标题颜色"  value="">
              </div>
	        </div>
	        <div class="form-group">
	          <label for="username" class="col-xs-2 control-label">关键词1<span style="color:red">*</span></label>
	          <div class="col-xs-5">
	            <input type="text" class="form-control" id="keynote1" pattern="\w{3,50}" maxLength=50 required placeholder="请输入关键词1（3-50个字符）">
	          </div>
	          <label for="profession" class="col-xs-2 control-label">颜色<span style="color:red">*</span></label>
              <div class="col-xs-3">
                 <input class="form-control" id="keynote1Color"  maxLength=100  required placeholder="请输入关键词1颜色"  value="">
              </div>
	        </div>	  	    
	        <div class="form-group">
	          <label for="username" class="col-xs-2 control-label">关键词2<span style="color:red">*</span></label>
	          <div class="col-xs-5">
	            <input type="text" class="form-control" id="keynote2" pattern="\w{3,50}" maxLength=50 required placeholder="请输入关键词2（3-50个字符）">
	          </div>
	          <label for="profession" class="col-xs-2 control-label">颜色<span style="color:red">*</span></label>
              <div class="col-xs-3">
                 <input class="form-control" id="keynote2Color"  maxLength=100  required placeholder="请输入关键词2颜色"  value="">
              </div>
	        </div>
	        <div class="form-group">
	          <label for="username" class="col-xs-2 control-label">关键词3<span style="color:red">*</span></label>
	          <div class="col-xs-5">
	            <input type="text" class="form-control" id="keynote3" pattern="\w{3,50}" maxLength=50 required placeholder="请输入关键词3（3-50个字符）">
	          </div>
	          <label for="profession" class="col-xs-2 control-label">颜色<span style="color:red">*</span></label>
              <div class="col-xs-3">
                 <input class="form-control" id="keynote3Color"  maxLength=100  required placeholder="请输入关键词3颜色"  value="">
              </div>
	        </div>
	        <div class="form-group">
	          <label for="username" class="col-xs-2 control-label">备注<span style="color:red">*</span></label>
	          <div class="col-xs-5">
	            <input type="text" class="form-control" id="remark" pattern="\w{3,50}" maxLength=50 required placeholder="请输入备注（3-50个字符）">
	          </div>
	          <label for="profession" class="col-xs-2 control-label">颜色<span style="color:red">*</span></label>
              <div class="col-xs-3">
                 <input class="form-control" id="remarkColor"  maxLength=100  required placeholder="请输入备注颜色"  value="">
              </div>
	        </div>	        	         
            <div class="form-group">
               <div class="col-sm-offset-4 col-sm-10">
                 <button type="submit" class="btn btn-info" id="save" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
                 <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
            </div>
	      </form>
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