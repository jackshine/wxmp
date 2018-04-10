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
    
    <script src="${contextPath}/script/date-format.js" type="text/javascript"></script>
</head>
<body class="light-gray-bg">
<div style="height:18px;background-color:#E0E0E0 ;margin-bottom:5px;"></div>
<div class="container" >
  <#include "/page-top-menu.ftl" encoding="utf8"> 
  <div class="row" style="padding:5px 0 0 0;">
	<!-- 左面功能菜单 -->
    <div class="col-xs-1" style="padding-left:0">
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
   	      粉丝用户管理
        </div>
        <div class="panel-body">
		    <ul class="nav nav-pills nav-stacked">
		      <li id="link_memo" class="active" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=MgrMain]').hide();$('#memoMgrMain').show(); "><a href="#">功能说明</a></li>
			  <li id="link_tag" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=MgrMain]').hide();$('#tagMgrMain').show(); "><a href="#">标签管理</a></li>
			  <li id="link_fans" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=MgrMain]').hide();$('#fansMgrMain').show(); "><a href="#">粉丝管理</a></li>
		    </ul>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-11" style="padding-right:0">
      <!-- 功能说明-->
      <div class="row" id="memoMgrMain" style="padding:0 5px"> 
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            标签／粉丝管理说明
          </div>
          <div class="panel-body">

          </div>
        </div>
      </div>
      <!-- 标签管理-->
      <div class="row" id="tagMgrMain" style="display:none;padding:0 5px"> 
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
            <button type="button" class="btn btn-info" style="margin-left:20px" @click="queryAllTags">&nbsp;&nbsp;查询所有&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-primary" style="margin-left:20px" @click="createTag">&nbsp;&nbsp;创建标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-primary" style="margin-left:20px" @click="updateTag">&nbsp;&nbsp;更新标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-warning" style="margin-left:20px" @click="deleteTag">&nbsp;&nbsp;删除标签&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
            <table class="table table-bordered table-hover ">
              <thead>
   	            <tr><th width="15%">标签ID</th><th width="35%">标签名称</th><th width="50%">标签名称</th></tr>
              </thead>
              <tbody> 
                <tr v-for="item in tagList" @click="selected.tagId=item.tagId" onclick="$(this).addClass('active');$(this).siblings().removeClass('active')">
                  <td>{{item.tagId}}</td>
                  <td>{{item.tagName}}</td>
                  <td>{{item.tagDesc}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <!-- 粉丝管理-->
      <div class="row" id="fansMgrMain" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0" >
            <button type="button" class="btn btn-primary" style="margin-left:20px" @click="addTag">&nbsp;&nbsp;打上标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-warning" style="margin-left:20px" @click="removeTag">&nbsp;&nbsp;移除标签&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-primary" style="margin-left:20px" @click="updateRemark">&nbsp;&nbsp;修改备注&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-primary" style="margin-left:20px" @click="black">&nbsp;&nbsp;加入黑名单&nbsp;&nbsp;</button>
            <button type="button" class="btn btn-primary" style="margin-left:20px" @click="unBlack">&nbsp;&nbsp;移出黑名单&nbsp;&nbsp;</button>
          </div>
          <div class="panel-body">
              <div class="panel panel-info">
                <div class="panel-heading" style="margin:0" >
	              <form class="form-inline" role="form" id="searchForm">
		    	  	    <div class="row" style="margin-top:0px">
			          <div class="col-xs-3 form-group" style="padding-left:0">
			            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">性别</label>
			            <div class="col-xs-8" style="padding-left:5px;padding-right:5px"> 
			              <select class="form-control" v-model="params.sex" name="inout" >
			                <option value="">请选择...</option>
		                    <option value="0">保密</option>
		                    <option value="1">男</option>
		                    <option value="2">女</option>
		                  </select>
			            </div>
			          </div>
			          <div class="col-xs-3 form-group" style="padding-left:0">
			            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">省份</label>
			            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
			              <select class="form-control" v-model="params.province" >
			                <option value="">请选择...</option>
		                    <option v-for="(value,key) in metadata.province" v-bind:value="key">{{value}}</option>                    
		                  </select>
			            </div>
			          </div>	
			          <div class="col-xs-3 form-group" style="padding-left:0">
			            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">城市</label>
			            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
			              <select class="form-control" v-model="params.city" >
			                <option value="">请选择...</option>
		                    <option v-for="(value,key) in metadata.city" v-bind:value="key">{{value}}</option>                    
		                  </select>
			            </div>
			          </div>	
			          <div class="col-xs-3 form-group" style="padding-left:0">
			            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">标签</label>
			            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
			              <select class="form-control" v-model.number="params.tagid" >
			                <option value="0">请选择...</option>
		                    <option v-for="item in metadata.tagid" v-bind:value="item.tagId">{{item.tagId}}-{{item.tagName}}</option>                    
		                  </select>
			            </div>
			          </div>
			        </div>
			        <div class="row" style="margin-top:2px">
			          <div class="col-xs-3 form-group" style="padding-left:0">
			            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">关注渠道</label>
			            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
			              <select class="form-control" v-model="params.channel">
			                <option value="">请选择...</option>
		                    <option v-for="(value,key) in metadata.channel" v-bind:value="key">{{value}}</option>
		                  </select>
			            </div>
			          </div>
			          <div class="col-xs-3 form-group" style="padding-left:0">
			            <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">黑名单</label>
			            <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
			              <select class="form-control" v-model="params.isBlack">
			                <option value="">请选择...</option>
		                    <option value="0">否</option>
		                    <option value="1">是</option>
		                  </select>
			            </div>
			          </div>	
			          <div class="col-xs-3 form-group" style="padding-left:0">
	                    <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">OPENID</label>
	                    <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	                      <input type="text" class="form-control" v-model="params.openId" placeholder="请输入OPENID">
	                    </div>
	                  </div>				
			        </div>
			        <div class="row" style="margin-top:2px"> 
			          <div class="col-xs-6 form-group" style="padding-left:0;">
			            <label class="col-xs-2 control-label" style="padding-left:5px;padding-right:5px">关注时间</label>
			            <div class="col-xs-10" style="padding-left:5px;padding-right:5px">
			              <input type="date" class="form-control" v-model="params.subscribeTimeBegin" placeholder="请输入开始时间">~
			              <input type="date" class="form-control" v-model="params.subscribeTimeEnd" placeholder="请输入结束时间">
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
                  <table class="table table-bordered table-striped table-hover ">
                   <thead>
   	                <tr>
   	                  <th width="3%">&nbsp;&nbsp;<input type="checkbox" style="display:block;width:20px;height:20px"></th>
   	                  <th width="13%">OPENID</th><th width="6%">性别</th><th width="10%">省份</th><th width="13%">城市</th>
   	                  <th width="15%">标签列表</th><th width="13%">关注渠道</th><th width="6%">黑名单</th><th>关注时间</th>
   	                </tr>
                   </thead>
                   <tbody> 
                     <tr v-for="item in datas">
                       <td><input type="checkbox" style="display:block;width:20px;height:20px" v-model="selected[item.openId]"></td>
                       <td>{{item.openId}}</td>
                       <td>{{getSex(item.sex)}}</td>
                       <td>{{item.province}}</td>
                       <td>{{item.city}}</td>
                       <td>{{getTagidList(item.tagidList)}}</td>
                       <td>{{getChannel(item.subscribeScene)}}</td>
                       <td>{{item.isBlack=='1'?'是':'否'}}</td>
                       <td>{{item.subscribeTime}}</td>
                     </tr>
                     <tr>
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
      </div>      
    </div><!-- end of 右面详细信息 -->
  </div>
  
</div>
<script>
//=============================================================================================================================================
//-----------------标签管理主界面--------------------
//=============================================================================================================================================
var tagMgrMainVue = new Vue({
	el:'#tagMgrMain',
	data:{
		selected:{
			tagId:'',
		},
		tagList:[]
	},
	methods:{
		queryAllTags: function(){
			getAllTags();
			
		},
		createTag:function(){
			$('#tagMgrModal').modal('show');
			tagMgrVue.tagId = '';
			tagMgrVue.tagName = '';
			tagMgrVue.tagDesc = '';
		},
		updateTag: function(){
			if(!this.selected.tagId){
				 alert("请选择要修改的标签！");
				 return;
			 }
			 $("#tagMgrModal").modal('show');
			 for(var i=0;i<this.tagList.length;i++){
				 var u = this.tagList[i];
				 if(u.tagId == this.selected.tagId){
					 tagMgrVue.tagId = u.tagId;
					 tagMgrVue.tagName = u.tagName;
					 tagMgrVue.tagDesc = u.tagDesc;
				 }
			 }
		},
		deleteTag: function(){
			if(!this.selected.tagId){
				 alert("请选择要删除的标签！");
				 return;
			 }
			 if(confirm("如果删除则将收回用户身上的标签信息，您确定是要删除标签【" + this.selected.tagId + "】吗？")){
				 $.ajax({
						url: '${contextPath}/fans/tag/delete',
						data: {'tagId':this.selected.tagId},
						success: function(jsonRet,status,xhr){
							if(jsonRet){
								if(0 == jsonRet.errcode){
									alert("标签【" + tagMgrMainVue.selected.tagId + "】已成功删除！");
									getAllTags();
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
	}
});
function getAllTags(){
	 $.ajax({
		url: '${contextPath}/fans/tag/getAll',
		data: {},
		success: function(jsonRet,status,xhr){
			if(jsonRet){
				if(0 == jsonRet.errcode){
					tagMgrMainVue.tagList = jsonRet.data;
					fansMgrMainVue.metadata.tagid = tagMgrMainVue.tagList;
					fansTagMgrVue.metadata.tagid = tagMgrMainVue.tagList;
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
getAllTags();
</script>

<!-- 标签管理模态对话框（Modal） -->
<div class="modal fade " id="tagMgrModal" tabindex="-1" role="dialog" aria-labelledby="tagMgrModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="tagMgrModalLabel">标签管理</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" action="add" role="form" >
               <div class="form-group">
               </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">标签名<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input class="form-control" v-model.trim="tagName" type="text" maxlength=30 required placeholder="请输入标签名(3-30字符)..." >
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">标签描述</label>
			      <div class="col-sm-10">
			         <textarea class="form-control" v-model.trim="tagDesc" rows= 10 maxlength=600 placeholder="请输入标签描述..." ></textarea>
			      </div>
			   </div>
			</form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-primary" v-on:click="submit">提交</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
var tagMgrVue = new Vue({
	el: '#tagMgrModal',
	  data: {
		  tagId:'',
		  tagName: '',
		  tagDesc:''
	  },
	  methods: {
		  submit:function(){
				if(!this.tagName || this.tagName.length<3 || this.tagName.length>30){
					alert("标签名长度为3-30个字符！");
					return false;
				}
				if(!this.tagDesc && this.tagDesc.length>600){
					alert("标签描述最长为600个字符！");
					return false;
				}
				$.ajax({
					url: '${contextPath}/fans/tag/' + (this.tagId ? 'update':'create'),
					data: {'tagName':this.tagName,'tagDesc':this.tagDesc,'tagId':this.tagId},
					success: function(jsonRet,status,xhr){
						if(jsonRet){
							if(0 == jsonRet.errcode){
								alert("标签【" + tagMgrVue.tagName + "】已成功" + (tagMgrVue.tagId ? '修改':'添加') + "！！!");
								$("#tagMgrModal").modal('hide');
								getAllTags();	//重新获取所有标签
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

<script>
//=============================================================================================================================================
//-----------------粉丝管理主界面--------------------
//=============================================================================================================================================
var fansMgrMainVue = new Vue({
	el:'#fansMgrMain',
	data:{
		metadata:{//元数据
			province:[],
			city:[],
			channel:{'ADD_SCENE_SEARCH':'公众号搜索','ADD_SCENE_ACCOUNT_MIGRATION':'公众号迁移','ADD_SCENE_PROFILE_CARD':'名片分享','ADD_SCENE_QR_CODE':'扫描二维码','ADD_SCENEPROFILE_LINK':'图文页内名称点击','ADD_SCENE_PROFILE_ITEM':'图文页右上角菜单','ADD_SCENE_PAID':'支付后关注','ADD_SCENE_OTHERS':'其他'},
			tagid:[]	//取标签管理数据
		},
		datas:[],//查询数据列表
		pageNo:0,
		pageCond:{begin:0,pageSize:20,count:0},//初始分页信息（点击查询时更新）
		searchPageCond:{begin:0,pageSize:20}, //
		params:{//查询参数
			sex:'',province:'',city:'',channel:'',tagid:0,isBlack:'',openId:'',subscribeTimeBegin:'',subscribeTimeEnd:''
		},
		selected:{//选择的粉丝用户
			//'openId':0/1
		}
	},
	methods:{
		getSex: function(code){
			if(code == '0') return '保密';
			if(code == '1') return '男';
			if(code == '2') return '女';
			return code
		},
		getChannel: function(code){
			for(key in this.metadata.channel){
				if(key == code){
					return this.metadata.channel[key]
				}
			}
			return code;
		},
		getTagidList: function(val){
			var temp = val.substring(2);
			temp = temp.substring(0,temp.length-2);
			return temp;
		},
		goUpPage:function(event){
			this.searchPageCond.begin = this.pageCond.begin - this.pageCond.pageSize;
			this.searchPageCond.pageSize = this.pageCond.pageSize;
			searchFans();
		},
		goDownPage:function(){
			this.searchPageCond.begin = this.pageCond.begin + this.pageCond.pageSize;
			this.searchPageCond.pageSize = this.pageCond.pageSize;
			searchFans();
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
			 searchFans();
		},
		query : function(){
			this.searchPageCond.begin = 0;
			this.searchPageCond.pageSize = this.pageCond.pageSize;
			searchFans();
		},
		reset: function(){
			this.params = {sex:'',province:'',city:'',channel:'',tagid:0,isBlack:'',openId:'',subscribeTimeBegin:'',subscribeTimeEnd:''};
		},
		addTag: function(){
			var selectedArr = [];
			for(var openId in this.selected){
				if(this.selected[openId] == 1){
					selectedArr.push(openId);
				}
			}
			if(selectedArr.length<1){
				alert('请选择要打上标签的用户！');
				return;
			}
			$("#fansTagMgrModal").modal('show');
			fansTagMgrVue.mode = "add";
			fansTagMgrVue.params.tagId = 0;
			fansTagMgrVue.params.openIds = '';
			fansTagMgrVue.params.openIds = selectedArr.join(",");
		},
		removeTag: function(){
			var selectedArr = [];
			for(var openId in this.selected){
				if(this.selected[openId] == 1){
					selectedArr.push(openId);
				}
			}
			if(selectedArr.length<1){
				alert('请选择要移除标签的用户！');
				return;
			}
			$("#fansTagMgrModal").modal('show');
			fansTagMgrVue.mode = "move";
			fansTagMgrVue.params.tagId = 0;
			fansTagMgrVue.params.openIds = selectedArr.join(",");
		},
		updateRemark: function(){
			var selectedArr = [];
			for(var openId in this.selected){
				if(this.selected[openId] == 1){
					selectedArr.push(openId);
				}
			}
			if(selectedArr.length<1){
				alert('请选择要修改备注的用户！');
				return;
			}
		},
		black: function(){
			var selectedArr = [];
			for(var openId in this.selected){
				if(this.selected[openId] == 1){
					selectedArr.push(openId);
				}
			}
			if(selectedArr.length<1){
				alert('请选择要加入黑名单的用户！');
				return;
			}
		},
		unBlack: function(){
			var selectedArr = [];
			for(var openId in this.selected){
				if(this.selected[openId] == 1){
					selectedArr.push(openId);
				}
			}
			if(selectedArr.length<1){
				alert('请选择要移出黑名单的用户！');
				return;
			}
		}
	},
	computed:{
		pageCnt:function(){
			return Math.ceil(this.pageCond.count/this.pageCond.pageSize);
		}
	}
});
function searchFans(){
	$.ajax({
		url: '${contextPath}/fans/fans/search',
		data: {'pageSize':fansMgrMainVue.searchPageCond.pageSize,'begin':fansMgrMainVue.searchPageCond.begin,'jsonParams':JSON.stringify(fansMgrMainVue.params)},
		success: function(jsonRet,status,xhr){
			if(jsonRet){
				if(0 == jsonRet.errcode){
					fansMgrMainVue.datas = jsonRet.datas;
					fansMgrMainVue.pageCond = jsonRet.pageCond;
					fansMgrMainVue.pageNo = Math.ceil((fansMgrMainVue.pageCond.begin+1)/fansMgrMainVue.pageCond.pageSize)
					//初始化选择列表
					fansMgrMainVue.selected = {};
					for(var i=0;i<jsonRet.datas.length;i++){
						var openId = jsonRet.datas[i];
						fansMgrMainVue.selected[openId] = 0;
					}
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

<!-- 粉丝标签管理模态对话框（Modal） -->
<div class="modal fade " id="fansTagMgrModal" tabindex="-1" role="dialog" aria-labelledby="fansTagMgrModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="fansTagMgrModalLabel">粉丝标签管理</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" action="add" role="form" >
               <div class="form-group">
               </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">{{mode=="add"?"打上":"移除"}}标签<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <select class="form-control" v-model.number="params.tagId" >
			            <option value="0">请选择...</option>
		                <option v-for="item in metadata.tagid" v-bind:value="item.tagId">{{item.tagId}}-{{item.tagName}}</option>                    
		             </select>
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">已选择用户<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <textarea class="form-control" v-model="params.openIds" rows=15 readonly ></textarea>
			      </div>
			   </div>
			</form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-primary" v-on:click="submit">提交</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
var fansTagMgrVue = new Vue({
	el: '#fansTagMgrModal',
	  data: {
		  mode :'',//add、move
		  metadata:{
			  tagid:[] //使用标签管理查询列表数据
		  },
		  params:{
			  tagId:0,
			  openIds:''//选择的用户
		  },
	  },
	  methods: {
		  submit:function(){
				if(this.params.tagId < 1){
					alert("请选择要" + (this.mode=="add"?"添加":"移除") + "的标签！");
					return false;
				}
				if(!this.params.openIds || this.params.length<1){
					alert("请选择要" + (this.mode=="add"?"添加":"移除") + "标签的粉丝用户！");
					return false;
				}
				$.ajax({
					url: '${contextPath}/fans/fans/' + (this.mode=='add' ? 'addTag':'moveTag'),
					data: {'tagId':this.params.tagId,'openIds':this.params.openIds},
					success: function(jsonRet,status,xhr){
						if(jsonRet){
							if(0 == jsonRet.errcode){
								alert("标签【" + fansTagMgrVue.tagId + "】已成功" + (mode=="add"?"添加":"移除") + "！！!");
								$("#fansTagMgrModal").modal('hide');
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