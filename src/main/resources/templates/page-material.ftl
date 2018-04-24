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
<div class="container" id="materailMain">
  <#include "/page-top-menu.ftl" encoding="utf8"> 
  <div class="row" style="padding:5px 0 0 0;">
	<!-- 左面功能菜单 -->
    <div class="col-xs-2" style="padding-left:0">
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
   	      多媒体素材管理
        </div>
        <div class="panel-body">
		    <ul class="nav nav-pills nav-stacked">
			  <li id="link_temp_image" v-for="func in metadata.funcs" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active'); ">
			    <a href="#" @click="initFunc(func)">{{func}}</a>
			  </li>
		    </ul>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-10" style="padding-right:0">
      <!-- 素材管理-->
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            <label>{{init.mode}}</label>
            <button type="button" class="btn btn-primary" id="add" style="margin-left:20px">&nbsp;&nbsp;添加素材&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <div class="col-xs-8">
              <div class="panel panel-info">
                <div class="panel-heading" style="margin:0" >
	              <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	                <input type="date" id="startDate" v-model="params.beginTime" placeholder="请输入开始时间">～
	                <input type="date" id="endDate" v-model="params.endTime"placeholder="请输入结束时间">
	              </div>
                  <button type="button" class="btn btn-info"  style="margin-left:10px" v-on:click="query">&nbsp;&nbsp;查 询&nbsp;&nbsp;</button>
		          <button type="button" class="btn btn-warning"  style="margin-left:10px" v-on:click="reset">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
                <div class="panel-body">
                  <table class="table table-striped  table-bordered table-hover ">
                    <thead>
   	                  <tr><th width="3%"></th><th width="25%">MEDIAID</th><th width="50%">URL</th><th width="25%">添加时间</th></tr>
                    </thead>
                    <tbody> 
                      <tr v-for="(item,index) in datas">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                      </tr>
                      <tr>
		                <td colspan="4">
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
            <div class="col-xs-3">
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
<script type="text/javascript">
var materailMainVue = new Vue({
	el:'#materailMain',
	data:{
		metadata:{
			funcs:['临时-图片素材','临时-缩略图素材','临时-语音素材','临时-视频素材','临时-图文素材','图文专用图片素材','永久-图片素材','永久-缩略图素材','永久-语音素材','永久-视频素材','永久-图文素材']
		},
		init:{
			mode : ''//素材操作类型
		},
		datas:[],//查询数据列表
		pageNo:0,
		pageCond:{begin:0,pageSize:20,count:0},//初始分页信息（点击查询时更新）
		searchPageCond:{begin:0,pageSize:20}, //
		params:{//初始化与查询参数
			isTemp:'1',mediaType:'',isNewImg:'0',beginTime:'',endTime:''
		}
	},
	methods:{
		initFunc: function(funcType){
			this.init.mode = funcType;
			if(funcType.indexOf('永久')){
				this.params.isTemp = '0';
			}else{
				this.params.isTemp = '1';
			}
			if(funcType.indexOf('图片')>=0){
				this.params.mediaType = 'image';
			}else if(funcType.indexOf('图文')>=0){
				this.params.mediaType = 'news';
			}else if(funcType.indexOf('缩略图')>=0){
				this.params.mediaType = 'thumb';
			}else if(funcType.indexOf('语音')>=0){
				this.params.mediaType = 'voice';
			}else if(funcType.indexOf('视频')>=0){
				this.params.mediaType = 'video';
			}else{
				this.params.mediaType = '';
			}
			if(funcType == '图文专用图片素材'){
				this.params.mediaType = 'image';
				this.params.isNewImg = '1';
				this.params.isTemp = '1';
			}
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
			this.params = {beginTime:'',endTime:''};
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
		url: '/material/search',
		data: {'pageSize':materailMainVue.searchPageCond.pageSize,'begin':materailMainVue.searchPageCond.begin,'jsonParams':JSON.stringify(materailMainVue.params)},
		success: function(jsonRet,status,xhr){
			if(jsonRet){
				if(0 == jsonRet.errcode){
					materailMainVue.datas = jsonRet.datas;
					materailMainVue.pageCond = jsonRet.pageCond;
					materailMainVue.pageNo = Math.ceil((fansMgrMainVue.pageCond.begin+1)/fansMgrMainVue.pageCond.pageSize)
					
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