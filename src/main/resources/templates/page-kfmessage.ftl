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
   	      发送客服消息
        </div>
        <div class="panel-body">
		&nbsp;&nbsp;&nbsp;&nbsp;当用户和公众号产生特定动作的交互时（具体动作列表见下），可在48小时内发送客服消息给普通用户。<br>
        &nbsp;&nbsp;&nbsp;&nbsp;目前允许的动作列表如下（不同动作触发后，允许的客服接口下发消息条数不同，下发条数达到上限后，会遇到错误返回码）：<br>
		&nbsp;&nbsp;&nbsp;&nbsp;	1、用户发送信息<br>
		&nbsp;&nbsp;&nbsp;&nbsp;	2、点击自定义菜单事件（仅有点击推事件、扫码推事件、扫码推事件且弹出“消息接收中”提示框这3种菜单事件）<br>
		&nbsp;&nbsp;&nbsp;&nbsp;	3、关注公众号<br>
		&nbsp;&nbsp;&nbsp;&nbsp;	4、扫描二维码<br>
		&nbsp;&nbsp;&nbsp;&nbsp;	5、支付成功<br>
		&nbsp;&nbsp;&nbsp;&nbsp;	6、用户维权<br>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-9" style="padding-right:0">
        <div class="row panel panel-info">
          <div class="panel-heading" style="margin:0">
	          <div class="col-xs-6" style="padding-left:5px;padding-right:5px">
	            	 <select class="form-control" id="msgType" name="msgType" >
	                <option value="">请选择...</option>
                    <option value="text">text-文本</option>
                    <option value="news">news-图文内链</option>
                    <option value="voice">voice-语音</option>
                    <option value="image">image-图片</option>
                    <option value="video">video-视频</option>
                    <option value="card">card-卡券</option>
                    <option value="mpnews">mpnews-图文外链</option>
                    <option value="music">music-音乐</option>
                    <option value="miniprogram">miniprogram-小程序卡片</option>
                    <option value="typing">typing-输入状态</option>
                  </select>
	          </div>
              <button type="button" class="btn btn-info" id="selectMsgType" style="margin-left:20px">&nbsp;&nbsp;选择消息类型&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <form class="form-horizontal" id="kfmsgForm" action="" method ="post" autocomplete="on" role="form" >
            	  <div class="form-group">
	            <label for="fromUser" class="col-xs-2 control-label">OPENID<span style="color:red">*</span></label>
	            <div class="col-xs-8">
	              <input type="text" class="form-control" id="fromUser" name="fromUser" pattern="\w{1,50}" maxLength=50 required placeholder="请输入OPENID（1-50个字符）">
	            </div>
	          </div>
	          <div class="form-group">
	            <label for="content" class="col-xs-2 control-label">文本内容<span style="color:red">*</span></label>
	            <div class="col-xs-8">
	              <textarea class="form-control" id="content" name="content" rows="15" maxLength=1000 required placeholder="请输入文本内容；发送文本消息时，支持插入跳小程序的文字链文本内容<a href='http://www.qq.com' data-miniprogram-appid='appid' data-miniprogram-path='pages/index/index'>点击跳小程序</a>"></textarea>
	            </div>
	          </div>
	          
	          <div class="form-group">
                <div class="col-sm-offset-4 col-sm-10">
                  <button type="button" class="btn btn-primary" id="save" style="margin:20px">&nbsp;&nbsp;发 送&nbsp;&nbsp;</button>
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