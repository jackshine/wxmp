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
    <link href="/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/templatemo-style.css" rel="stylesheet">
    
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
   	      群发消息管理
        </div>
        <div class="panel-body">
		    <ul class="nav nav-pills nav-stacked">
			  <li id="link_bytag" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=mass]').hide();$('#massByTag').show(); "><a href="#">根据标签群发</a></li>
			  <li id="link_byopenids" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=mass]').hide();$('#massByOpenId').show(); "><a href="#">根据指定用户群发</a></li>
			  <li id="link_preview" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=mass]').hide();$('#massPreivew').show(); "><a href="#">群发消息预览</a></li>
			  <li id="link_delete" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=mass]').hide();$('#massDelete').show(); "><a href="#">删除群发图文视频消息</a></li>
			  <li id="link_getstatus" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=mass]').hide();$('#massStatus').show(); "><a href="#">获取群发状态</a></li>
		    </ul>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-9" style="padding-right:0">
      <!-- 根据标签群发-->
      <div class="row" id="massByTag" style="display:none;padding:0 5px"> 
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
	          <div class="col-xs-6" style="padding-left:5px;padding-right:5px">
	            	 <select class="form-control" id="msgType" name="msgType" >
	                <option value="">请选择...</option>
                    <option value="text">text-文本消息</option>
                    <option value="news">news-图文消息</option>
                    <option value="voice">voice-语音消息</option>
                    <option value="image">image-图片消息</option>
                    <option value="video">video-视频消息</option>
                    <option value="card">card-卡券消息</option>
                  </select>
	          </div>
              <button type="button" class="btn btn-info" id="selectMsgType" style="margin-left:20px">&nbsp;&nbsp;选择消息类型&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <form class="form-horizontal" id="formMassByTag" action="" method ="post" autocomplete="on" role="form" >
            	  <div class="form-group">
	            <label for="username" class="col-xs-2 control-label">标签ID<span style="color:red">*</span></label>
	            <div class="col-xs-8">
	              <input type="text" class="form-control" id="tagId" name="tagId" pattern="\w{1,50}" maxLength=50 required placeholder="请输入标签ID（1-50个字符）">
	            </div>
	          </div>
	          <div class="form-group">
                <div class="col-sm-offset-3 col-sm-10">
                  <button type="button" class="btn btn-primary" id="save" style="margin:20px">&nbsp;&nbsp;发 送&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      <!-- 根据指定用户群发-->
      <div class="row" id="massByOpenId" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
	          <div class="col-xs-6" style="padding-left:5px;padding-right:5px">
	            	 <select class="form-control" id="type" name="income" >
	                <option value="">请选择...</option>
                    <option value="text">text-文本消息</option>
                    <option value="news">news-图文消息</option>
                    <option value="voice">voice-语音消息</option>
                    <option value="image">image-图片消息</option>
                    <option value="video">video-视频消息</option>
                    <option value="card">card-卡券消息</option>
                  </select>
	          </div>
              <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;选择消息类型&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <form class="form-horizontal" id="formMassByOpenIds" action="" method ="post" autocomplete="on" role="form" >
            	  <div class="form-group">
	            <label for="username" class="col-xs-2 control-label">用户ID列表<span style="color:red">*</span></label>
	            <div class="col-xs-8">
	              <textarea class="form-control" id="openIdList" name="openIdList" style="width:100%" maxLength=60000 rows="20" required placeholder="请输入OPENID列表，使用逗号分隔"></textarea>
	            </div>
	          </div>
	          <div class="form-group">
                <div class="col-sm-offset-3 col-sm-10">
                  <button type="button" class="btn btn-primary" id="save" style="margin:20px">&nbsp;&nbsp;发 送&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      <!-- 群发消息预览-->
      <div class="row" id="massPreivew" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
	          <div class="col-xs-6" style="padding-left:5px;padding-right:5px">
	            	 <select class="form-control" id="type" name="income" >
	                <option value="">请选择...</option>
                    <option value="text">text-文本消息</option>
                    <option value="news">news-图文消息</option>
                    <option value="voice">voice-语音消息</option>
                    <option value="image">image-图片消息</option>
                    <option value="video">video-视频消息</option>
                    <option value="card">card-卡券消息</option>
                  </select>
	          </div>
              <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;选择消息类型&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <form class="form-horizontal" id="formMassPreview" action="" method ="post" autocomplete="on" role="form" >
            	  <div class="form-group">
	            <label for="content" class="col-xs-2 control-label">文本内容<span style="color:red">*</span></label>
	            <div class="col-xs-8">
	              <input type="text" class="form-control" id="content" name="tagId" pattern="\w{1,500}" maxLength=50 required placeholder="请输入文本内容（1-500个字符）">
	            </div>
	          </div>
	          <div class="form-group">
                <div class="col-sm-offset-3 col-sm-10">
                  <button type="button" class="btn btn-primary" id="save" style="margin:20px">&nbsp;&nbsp;发 送&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div> 
      <!--删除群发图文视频消息-->
      <div class="row" id="massDelete" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            <div class="col-xs-6" style="padding-left:5px;padding-right:5px">
	          <input type="date" id="startDate" placeholder="请输入开始时间">～<input type="date" id="endDate" placeholder="请输入结束时间">
	        </div>
            <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <table class="table table-striped  table-bordered table-hover ">
              <thead>
   	            <tr><th width="15%">消息ID</th><th width="25%">消息类型</th><th width="25%">消息状态</th><th width="25%">发送时间</th><th width="10%">操作</th></tr>
              </thead>
              <tbody> 
                <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td><button type="button" class="btn btn-warning" id="save" >&nbsp;&nbsp;删 除&nbsp;&nbsp;</button></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div> 
      <!-- 获取发送状态-->
      <div class="row" id="massStatus" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            <div class="col-xs-6" style="padding-left:5px;padding-right:5px">
	          <input type="date" id="startDate" placeholder="请输入开始时间">～<input type="date" id="endDate" placeholder="请输入结束时间">
	        </div>
            <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <table class="table table-striped  table-bordered table-hover ">
              <thead>
   	            <tr><th width="15%">消息ID</th><th width="25%">消息类型</th><th width="25%">消息状态</th><th width="25%">发送时间</th><th width="10%">操作</th></tr>
              </thead>
              <tbody> 
                <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td><button type="button" class="btn btn-info" id="save">&nbsp;&nbsp;获 取&nbsp;&nbsp;</button></td>
                </tr>
              </tbody>
            </table>
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