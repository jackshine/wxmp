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
<div class="container" id="container">
  <#include "/page-top-menu.ftl" encoding="utf8"> 
  <div class="row" style="padding:5px 0 0 0;">
	<!-- 左面数据列表 -->
    <div class="col-xs-8" style="padding-left:0">
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
    	  	  <form class="form-inline" role="form" id="searchForm">
    	  	    <div class="row" style="margin-top:0px">
	          <div class="col-xs-4 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">场景值ID</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <input type="number" class="form-control" v-model.number="params.sceneId" name="sceneId" placeholder="请输入场景值ID">
	            </div>
	          </div>	
	          <div class="col-xs-4 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">TICKET</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <input type="text" class="form-control" v-model="params.ticket" name="ticket" placeholder="请输入二维码TICKET">
	            </div>
	          </div>	
	          <div class="col-xs-4 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">是否可用</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class=" form-control" v-model="params.isAvail" name="isAvail">
	                <option value="">请选择...</option>
                    <option value="0">否</option>
                    <option value="1">是</option>
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
	          <div class="col-xs-4 form-group" style="padding-left:0">
	            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">是否永久</label>
	            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	              <select class=" form-control" v-model="params.isPerm" name="isPerm">
	                <option value="">请选择...</option>
                    <option value="0">否</option>
                    <option value="1">是</option>
                  </select>
	            </div>
	          </div> 
	        </div>  
	        <div class="row" style="margin-top:2px">         
	            <div class="col-xs-4 form-group" style="padding-left:0">
                  <button type="button" class="btn btn-info" style="margin-left:20px" v-on:click="query">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-warning" style="margin-left:20px" v-on:click="reset">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
	            </div>
	        </div>
	      </form>	        	          
        </div>
        <div class="panel-body">
          <table class="table table-striped table-bordered table-hover ">
            <thead>
   	          <tr><th width="3%"><th width="8%">是否永久</th><th width="8%">是否可用</th><th width="10%">场景值ID</th><th>TICKET</th><th width="30%">创建时间</th></tr>
            </thead>
            <tbody> 
              <tr v-for="(item, index) in datas" v-on:click="getQrcode(item.ticket,item.localImgUrl)" onclick="$(this).addClass('active');$(this).siblings().removeClass('active')">
                <td>{{index+1}}</td>
                <td>{{item.isPerm=='1'?'是':'否'}}</td>
                <td>{{(item.createTime + item.expireSeconds)>new Date()?'否':'是'}}</td>
                <td>{{item.sceneId}}</td>
                <td v-bind:title="item.ticket">{{item.ticket.substring(0,30) + '......'}}</td>
                <td>{{item.createTime}}</td>
              </tr>
              <tr >
                <td colspan="6">
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
    </div><!-- end of 左面 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-4" style="padding-right:0">
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
          <button type="button" class="btn btn-primary" style="margin-left:20px" v-on:click="apply">&nbsp;&nbsp;申请新二维码&nbsp;&nbsp;</button>
        </div>
        <div class="panel-body">
          <img style="width:70%;margin:0 15%" v-bind:src="filename" alt="公众号二维码"/>
        </div>
      </div>  
    </div><!-- end of 右面详细信息 -->
  </div>  
</div>
<script type="text/javascript">
var container = new Vue({
	el:'#container',
	data:{
		datas:[],
		pageNo:0,
		pageCond:{begin:0,pageSize:20,count:0},
		searchPageCond:{begin:0,pageSize:20},
		params:{
			isPerm:'',
			isAvail:'',
			ticket:'',
			sceneId:'',
			beginTime:'',
			endTime:''
		},
		filename:''
	},
	methods:{
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
		getQrcode : function(ticket,localUrl){
			if(localUrl && localUrl.trim().length>10){
				container.filename = '${contextPath}/qrcode/show/' + localUrl;
				return;
			}
			$.ajax({
				url: '${contextPath}/qrcode/getqrcode',
				data: {'ticket':ticket},
				success: function(jsonRet,status,xhr){
					if(jsonRet){
						if(0 == jsonRet.errcode){
							//window.location.reload();
							container.filename = '${contextPath}/qrcode/show/' + jsonRet.filename;
						}else{//出现逻辑错误
							alert(jsonRet.errmsg);
						}
					}else{
						alert('系统数据访问失败！')
					}
				},
				dataType: 'json'
			});
		},
		query : function(){
			this.searchPageCond.begin = 0;
			this.searchPageCond.pageSize = this.pageCond.pageSize;
			search();
		},
		apply : function(){
			$('#applyModal').modal('show');
		},
		reset: function(){
			this.params = {isPerm:'',isAvail:'',ticket:'',sceneId:'',beginTime:'',endTime:''}
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
		url: '${contextPath}/qrcode/search',
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

<!-- 二维码申请模态对话框（Modal） -->
<div class="modal fade " id="applyModal" tabindex="-1" role="dialog" aria-labelledby="applyModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="applyModalLabel">申请微信公众号二维码</h4>
            <hr>
            <div class="form-group">
               	<label class="control-label"> 1、临时二维码有有效时间，是有过期时间的，最长可以设置为在二维码生成后的30天（即2592000秒）后过期；</label>
    	  	     	<label class="control-label"> 2、永久二维码是无过期时间的，但数量较少（目前为最多10万个）；</label>
    	  	     	<label class="control-label"> 3、场景值ID：永久二维码时最大值为100000（目前参数只支持1--100000）；临时二维码时为32位非0正整数；</label>
            </div>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" action="" role="form" >
			   <div class="form-group">
			      <label class="col-sm-2 control-label">场景值ID<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input class="form-control" v-model.number="sceneId" type="number" maxlength=10 required placeholder="请输入场景值ID..." >
			      </div>
			   </div>
               <div class="form-group">
			       <label class="col-sm-2 control-label">是否永久<span style="color:red" >*</span></label>
			        <div class="col-sm-10 radio">
			         <label class="radio-inline"><input type="radio" name="isPerm" v-model="isPerm" value="0" style="display:block"> 否</label>
			         <label class="radio-inline"><input type="radio" name="isPerm" v-model="isPerm" value="1" style="display:block"> 是</label>
			       </div>
			   </div>
			</form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="addUserSubmit" v-on:click="submit">提交</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
var applyQRcode = new Vue({
	el: '#applyModal',
	  data: {
		  sceneId: 0,
		  isPerm:'0'
	  },
	  methods: {
		  submit:function(){
			  if(this.isPerm == '0'){
				  if(!this.sceneId || this.sceneId<0 || this.sceneId>2147483647) {
					  alert("临时二维码场景值范围【1-2147483647】！");
					  return false;
				  }
			  }
			  if(this.isPerm == '1'){
				  if(!this.sceneId || this.sceneId<0 || this.sceneId>100000) {
					  alert("永久二维码场景值范围【1-100000】！");
					  return false;
				  }
			  }
			  var subUrl = (this.isPerm == "1"?"createPerm":"createTmp");
			  $.ajax({
				url: '${contextPath}/qrcode/' + subUrl,
				data: {'sceneId':this.sceneId},
				success: function(jsonRet,status,xhr){
					if(jsonRet){
						if(0 == jsonRet.errcode){
							alert((applyQRcode.isPerm == "1"?"永久":"临时") + "二维码【" + applyQRcode.sceneId + "】已成功申请！！");
							$("#applyModal").modal('hide');
							window.location.reload();
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
	  }
});
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