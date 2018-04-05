<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>消息查询 微信公众平台管理系统</title>
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
  <div class="row" style="margin-top:5px">
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
    	  	  <input type="hidden" name="begin" value="0">
    	  	  <input type="hidden" name="pageSize" value="20">
    	  	  <input type="hidden" name="condParams" value="">
    	  	  <form class="form-inline" role="form">
    	  	    <div class="row" style="margin-top:0px">
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label for="name" class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">消息方向</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px"> 
	              <select class="form-control" id="type" name="income" >
	                <option value="">请选择...</option>
                    <option value="1">发给公众号</option>
                    <option value="2">公众号发出</option>
                  </select>
	            </div>
	          </div>
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label for="name" class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">回复状态</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class="form-control" id="type" name="status">
	                <option value="">请选择...</option>
                    <option value="0">待处理</option>
                    <option value="1">不用回复</option>
                    <option value="2">已回复</option>
                    <option value="3">其他</option>                    
                  </select>
	            </div>
	          </div>	
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label for="name" class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">是否群发</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class=" form-control" id="type" name="status">
	                <option value="">请选择...</option>
                    <option value="0">否</option>
                    <option value="1">是</option>
                  </select>
	            </div>
	          </div>
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label for="name" class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">是否模板</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class="form-control" id="type" name="status">
	                <option value="">请选择...</option>
                    <option value="0">否</option>
                    <option value="1">是</option>
                  </select>
	            </div>
	          </div>        
	        </div>
	        <div class="row" style="margin-top:2px">
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label for="name" class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">消息类型</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <input type="text" class="form-control" id="name" name="name" value="" maxLength=38 required placeholder="请输入标题">
	            </div>
	          </div>
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label for="name" class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">事件类型</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <input type="text" class="form-control" id="name" name="name" value="" maxLength=38 required placeholder="请输入标题">
	            </div>
	          </div>	        
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label for="name" class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">发消息者</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <input type="text" class="form-control" id="fromUser" name="fromUser" value="" maxLength=38 required placeholder="请输入发消息者">
	            </div>
	          </div>		
	        </div>
	        <div class="row" style="margin-top:2px"> 
	          <div class="col-xs-6 form-group" style="padding-left:0;">
	            <label for="name" class="col-xs-2 control-label" style="padding-left:5px;padding-right:5px">创建时间</label>
	            <div class="col-xs-10" style="padding-left:5px;padding-right:5px">
	              <input type="date" class="form-control" id="name" name="name" value=""  required placeholder="请输入开始时间">~
	              <input type="date" class="form-control" id="name" name="name" value="" required placeholder="请输入结束时间">
	            </div>
	          </div> 
	          <div class="col-xs-3 form-group" style="padding-left:0">
                <button type="button" class="btn btn-info" id="save" style="margin-left:20px">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
                <button type="button" class="btn btn-warning" id="reset" style="margin-left:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
	          </div>
	        </div>
	      </form>
        </div>
        <div class="panel-body">
          <table class="table table-striped  table-bordered table-hover ">
            <thead>
   	          <tr><th width="3%"></th><th width="15%">消息类型</th><th width="15%">事件类型</th><th width="15%">消息方向</th><th width="20%">发消息者</th><th width="15%">消息回复状态</th><th width="25%">消息创建时间</th></tr>
            </thead>
            <tbody> 
              <tr>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr >
                <td colspan="7">
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