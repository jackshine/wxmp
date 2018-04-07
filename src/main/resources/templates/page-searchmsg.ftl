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
    <link href="${contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${contextPath}/css/templatemo-style.css" rel="stylesheet">
    
</head>
<body class="light-gray-bg">
<div style="height:18px;background-color:#E0E0E0 ;margin-bottom:5px;"></div>
<div class="container" id="container">
  <#include "/page-top-menu.ftl" encoding="utf8"> 
  <div class="row" style="margin-top:5px">
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
    	  	  <form class="form-inline" role="form" id="searchForm">
    	  	    <div class="row" style="margin-top:0px">
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">消息方向</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px"> 
	              <select class="form-control" v-model="params.inout" name="inout" >
	                <option value="">请选择...</option>
                    <option v-for="(value,key) in inout" v-bind:value="key">{{value}}</option>
                  </select>
	            </div>
	          </div>
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">回复状态</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class="form-control" v-model="params.status" name="status">
	                <option value="">请选择...</option>
                    <option v-for="(value,key) in status" v-bind:value="key">{{value}}</option>                    
                  </select>
	            </div>
	          </div>	
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">是否群发</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class=" form-control" v-model="params.isMass" name="isMass">
	                <option value="">请选择...</option>
                    <option value="0">否</option>
                    <option value="1">是</option>
                  </select>
	            </div>
	          </div>
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">是否模板</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class="form-control" v-model="params.isTpl" name="isTpl">
	                <option value="">请选择...</option>
                    <option value="0">否</option>
                    <option value="1">是</option>
                  </select>
	            </div>
	          </div>        
	        </div>
	        <div class="row" style="margin-top:2px">
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">消息类型</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class="form-control" v-model="params.msgType" name="msgType">
	                <option value="">请选择...</option>
                    <option v-for="(value,key) in msgTP" v-bind:value="key">{{value}}</option>
                  </select>
	            </div>
	          </div>
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">发消息者</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <input type="text" class="form-control" v-model="params.fromUser" name="fromUser" placeholder="请输入发消息者">
	            </div>
	          </div>	
	          <div class="col-xs-3 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">事件类型</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class="form-control" v-model="params.eventType" name="eventType">
	                <option value="">请选择...</option>
                    <option v-for="(value,key) in eventTP" v-bind:value="key">{{value}}</option>
                  </select>
	            </div>
	          </div>	        
	
	        </div>
	        <div class="row" style="margin-top:2px"> 
	          <div class="col-xs-6 form-group" style="padding-left:0;">
	            <label class="col-xs-2 control-label" style="padding-left:5px;padding-right:5px">创建时间</label>
	            <div class="col-xs-10" style="padding-left:5px;padding-right:5px">
	              <input type="date" class="form-control" v-model="params.beginTime" name="beginTime" placeholder="请输入开始时间">~
	              <input type="date" class="form-control" v-model="params.endTime" name="endTime" placeholder="请输入结束时间">
	            </div>
	          </div> 
	          <div class="col-xs-3 form-group" style="padding-left:0">
                <button type="button" class="btn btn-info"  style="margin-left:20px" v-on:click="query">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
                <button type="button" class="btn btn-warning"  style="margin-left:20px" v-on:click="reset">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
	          </div>
	        </div>
	      </form>
        </div>
        <div class="panel-body">
          <table class="table table-striped  table-bordered table-hover ">
            <thead>
   	          <tr><th width="3%"><th width="10%">消息类型</th><th width="15%">事件类型</th><th width="10%">消息方向</th><th width="15%">发消息者</th><th width="10%">消息回复状态</th><th width="8%">是否模板</th><th width="8%">是否群发</th><th width="20%">消息创建时间</th></tr>
            </thead>
            <tbody> 
              <tr v-for="(item, index) in datas">
                <td>{{index+1}}</td>
                <td>{{getMsgType(item.msgType)}}</td>
                <td>{{getEventType(item.eventType)}}</td>
                <td>{{getInout(item.inout)}}</td>
                <td>{{item.fromUser}}</td>
                <td>{{getStatus(item.status)}}</td>
                <td>{{getYesNo(item.isTpl)}}</td>
                <td>{{getYesNo(item.isMass)}}</td>
                <td>{{item.createTime}}</td>
              </tr>
              <tr >
                <td colspan="9">
				  <ul class="pager" style="margin:0"> 
                    <li v-if="pageCond.begin>0" class="active"><a href="###" v-on:click="goUpPage">上一页</a></li>
                    <li v-if="pageCond.begin<=0" class="disabled"><a href="###" >上一页</a></li>
                    <li> 共有<span >{{pageCnt}}</span>页 
 	                  <input type="number" v-model="pageNo" maxLength=15 min=1 style='width:55px'>
 	                  <button class="button btn-primary" v-on:click="goPage">GO</button>
                    </li>
                    <li v-if="pageCond.begin+pageCond.pageSize<pageCond.count" class="active"><a href="#" v-on:click="goDownPage">下一页</a></li>
                    <li v-if="pageCond.begin+pageCond.pageSize>=pageCond.count" class="disabled"><a href="#">下一页</a></li>
                  </ul>
           	    </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>  
  </div>  
</div>
<script>
var container = new Vue({
	el:'#container',
	data:{
		msgTP:{'text':"文本消息",'image':"图片消息",'voice':"语音消息",'video':"视频消息",
			'shortvideo':"小视频消息",'location':"地理位置消息",'link':"链接消息",'event':"事件消息"
		},
		eventTP:{
			//关注相关事件
			'subscribe':"关注事件消息",'unsubscribe':"取消关注事件消息",'SCAN':"扫描公众号的带参数二维码事件",'LOCATION':"上报地理位置事件",
			//自定义菜单事件
			'CLICK':"自定义菜单-点击事件",'VIEW':"点击菜单跳转链接时的事件",'scancode_push':"扫码推事件的事件推送",'scancode_waitmsg':"扫码推事件且弹出“消息接收中”提示框的事件",
			'pic_sysphoto':"弹出系统拍照发图的事件",'pic_photo_or_album':"弹出拍照或者相册发图的事件",'pic_weixin':"弹出微信相册发图器的事件",'location_select':"弹出地理位置选择器的事件",
			//其他事件
			'MASSSENDJOBFINISH':"推送群发结果事件",'TEMPLATESENDJOBFINISH':"推送模板消息发送结果事件",
			'qualification_verify_success':"资质认证成功",'qualification_verify_fail':"资质认证失败",'naming_verify_success':"名称认证成功",'naming_verify_fail':"名称认证失败",
			'annual_renew':"年审通知 ",'verify_expired':"认证过期失效通知 ",
			'merchant_order':"订单付款通知",'user_pay_from_pay_cell':"买单推送事件",'user_consume_card':"核销事件",
			'user_get_card':"用户领取卡券事件",'card_pass_check':"卡券审核事件推送",'user_del_card':"用户删除卡券事件",
			'user_view_card':"进入会员卡事件",'user_enter_session_from_card':"从卡券进入公众号会话事件",'update_member_card':"会员卡内容更新事件",
			'card_sku_remind':"库存报警事件",'card_pay_order':"券点流水详情事件"
		},
		status:{'0':'待处理','1':'不用回复','2':'已回复','3':'其他'},
		inout:{'1':'发给公众号','2':'公众号发出'},
		datas:[],
		pageNo:0,
		pageCond:{begin:0,pageSize:20,count:0},
		searchPageCond:{begin:0,pageSize:20},
		params:{inout:'',msgType:'',eventType:'',status:'',isMass:'',isTpl:'',fromUser:'',beginTime:'',endTime:''}
	},
	methods:{
		 getYesNo:function(code){
			 if(code == '1'){
				 return '是';
			 }else if(code == '0'){
				 return '否';
			 }
		 },
		 getMsgType : function(code){
			for(key in this.msgTP){
				if(key == code.trim()){
					return this.msgTP[key];
				}
			}
			return code;
		},
		getEventType : function(code){
			if(code){
				for(key in this.eventTP){
					if(key== code.trim()){
						return this.eventTP[key];
					}
				}
				return code;
			}
			return '';
		},
		getStatus : function(code){
			for(key in this.status){
				if(key== code.trim()){
					return this.status[key];
				}
			}
			return code;
		},
		getInout : function(code){
			for(key in this.inout){
				if(key== code.trim()){
					return this.inout[key];
				}
			}
			return code;
		},
		goUpPage:function(event){
			this.searchPageCond.begin = this.pageCond.begin - this.pageCond.pageSize;
			this.searchPageCond.pageSize = this.pageCond.pageSize;
			search();
		},
		goDownPage:function(){
			this.searchPageCond.begin = this.pageCond.begin + this.pageCond.pageSize;
			this.searchPageCond.pageSize = this.pageCond.pageSize;
			search();
		},
		goPage:function(){
			if(!this.pageNo){
				 return false;
			 }
			 if(this.pageNo<1){
				 alert('小于最小页数（1）！');
				 this.pageNo = 1;
			 }
			 if(this.pageNo > this.pageCnt){
				 alert('超过最大页数（' + this.pageCnt + '）！');
				 this.pageNo = this.pageCnt;
				 return false;
			 }
			 this.searchPageCond.begin = this.pageCond.pageSize*(this.pageNo-1);
			 this.searchPageCond.pageSize = this.pageCond.pageSize;
			 search();
		},
		query : function(){
			this.searchPageCond.begin = 0;
			this.searchPageCond.pageSize = this.pageCond.pageSize;
			search();
		},
		reset: function(){
			this.params = {inout:'',msgType:'',eventType:'',status:'',isMass:'',isTpl:'',fromUser:'',beginTime:'',endTime:''};
		}
	},
	computed:{
		pageCnt:function(){
			return Math.ceil(this.pageCond.count/this.pageCond.pageSize);
		}
	}
});
function search(){
	$.ajax({
		url: '${contextPath}/searchmsg/search',
		data: {'pageCond':JSON.stringify(container.searchPageCond),'jsonParams':JSON.stringify(container.params)},
		success: function(jsonRet,status,xhr){
			if(jsonRet){
				if(0 == jsonRet.errcode){
					container.datas = jsonRet.datas;
					container.pageCond = jsonRet.pageCond;
					container.pageNo = Math.ceil((container.pageCond.begin+1)/container.pageCond.pageSize)
				}else{//出现逻辑错误
					alert(jsonRet.errmsg);
				}
			}else{
				alert('系统数据访问失败！')
			}
		},
		dataType: 'json'
	});
}
search();

</script>

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