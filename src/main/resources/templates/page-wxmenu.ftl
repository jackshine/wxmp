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
	<!-- 左面功能菜单 -->
    <div class="col-xs-3" style="padding-left:0">
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
   	      微信自定义菜单管理
        </div>
        <div class="panel-body">
		    <ul class="nav nav-pills nav-stacked">
			  <li id="link_meno" class="active" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#menoMgr').show(); "><a href="#">微信菜单说明</a></li>
			  <li id="link_default" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#defaultMgr').show(); "><a href="#">默认菜单</a></li>
			  <li id="link_conditional" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id$=Mgr]').hide();$('#conditionalMgr').show(); "><a href="#">个性化菜单</a></li>
		    </ul>
	   	</div>
  	  </div>
    </div><!-- end of 左面功能菜单 -->
    <!-- 右面详细信息 -->
    <div class="col-xs-9" style="padding-right:0">
      <!-- 微信自定义菜单说明-->
      <div class="row" id="menoMgr" style="padding:0 5px"> 
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0">
          微信自定义菜单说明
          </div>
          <div class="panel-body">
			<p>
			自定义菜单能够帮助公众号丰富界面，让用户更好更快地理解公众号的功能。<p>
			1、自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。<p>
			2、一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。<p>
			3、个性化菜单可让公众号的不同用户群体看到不一样的自定义菜单，可以通过以下条件来设置用户看到的菜单：<p>
			&nbsp;&nbsp;&nbsp;&nbsp;用户标签、性别、手机操作系统、地区（用户在微信客户端设置的地区）、语言（用户在微信客户端设置的语言）<p>
          </div>
        </div>
      </div>
      <!-- 默认菜单-->
      <div class="row" id="defaultMgr" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0" >
            默认菜单管理(JSON格式)
          </div>
          <div class="panel-body">
          <div class="row">
	        <form class="form-horizontal" id="defaultMenuForm"  method ="post" autocomplete="on" enctype="multipart/form-data" role="form" > 
              <div class="form-group">
                <div class="col-xs-offset-1 col-xs-10">
                  <textarea class="form-control" v-model="params.defaultMenuData"  maxLength=1000 rows=20 required ></textarea>
                </div>
              </div>
              <div class="form-group">
                <div class="col-xs-offset-4 col-xs-10">
                  <button type="button" class="btn btn-info"  style="margin:20px" v-on:click="saveDefault">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-warning"  style="margin:20px" v-on:click="resetDefault">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
              </div>      
	        </form>
           </div>
          </div>            
        </div>
      </div>
      <!-- 个性化菜单管理-->
      <div class="row" id="conditionalMgr" style="display:none;padding:0 5px">
        <div class="panel panel-info">
          <div class="panel-heading" style="margin:0" >
            个性化菜单管理
          </div>
          <div class="panel-body">
          <div class="row">
	        <form class="form-horizontal" id="conditionalMenuForm"  method ="post" autocomplete="on" enctype="multipart/form-data" role="form" >       
	        	  <div class="col-xs-offset-1 row" >
	            <div class="col-xs-6 form-group" style="padding-left:0">
	              <label class="col-xs-4 control-label" style="padding-left:5px;padding-right:5px">已有菜单</label>
	              <div class="col-xs-8" style="padding-left:5px;padding-right:5px">
	                <select class="form-control" v-model="params.menuId" name="msgType">
	                  <option value="">请选择...</option>
                      <option v-for="value in initData.menuIdList" v-bind:value="value">{{value}}</option>
                    </select>
	              </div>
	            </div>
	            <div class="col-xs-6 form-group" style="padding-left:0">
                  <button type="button" class="btn btn-warning"  style="margin-left:20px" v-on:click="updCondi" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');">&nbsp;&nbsp;修 改&nbsp;&nbsp; </button>
                  <button type="button" class="btn btn-info"  style="margin-left:20px" v-on:click="addCondi" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');">&nbsp;&nbsp;新 增&nbsp;&nbsp;</button>
	            </div>	        
	          </div>
              <div class="form-group">
                <div class="col-xs-offset-1 col-xs-10">
                  <textarea class="form-control" v-model="params.conditionalMenuData" maxLength=1000 rows=18 required ></textarea>
                </div>
              </div>
              <div class="form-group">
                <div class="col-xs-offset-4 col-xs-10">
                  <button type="button" class="btn btn-info"  style="margin:20px" v-on:click="saveCondi">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
                  <button type="button" class="btn btn-warning"  style="margin:20px" v-on:click="resetCondi">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
                </div>
              </div>      
	        </form>
           </div>
          </div>            
        </div>
      </div>       
    </div><!-- end of 右面详细信息 -->
    
  </div>  
</div>
<script type="text/javascript">
var container = new Vue({
	el:'#container',
	data:{
		initData:{//初始化数据(应用于重置回复)
			menuIdList:[],
			defaultMenuData:'',
			menuId:'',
			conditionalMenuData:''
		},
		params:{//修改后的数据
			defaultMenuData:'',
			menuId:'',//要修改的个性化菜单ID
			conditionalMenuData:''
		}
	},
	methods:{
		addCondi : function(){
			this.params.menuId = '';
			this.params.conditionalMenuData = '';
		},
		updCondi : function(event){
			if(!this.params.menuId){
				alert("请先选择要修改的个性化菜单！");
				$(event.target).removeClass('active');
				return;
			}
			this.params.conditionalMenuData = '';
			getMenuData(false,this.params.menuId);//获取个性化菜单
		},
		resetDefault: function(){
			this.params.defaultMenuData = this.initData.defaultMenuData;
		},
		resetCondi: function(){
			if(!this.params.menuId){
				this.params.menuId = '';
				this.params.conditionalMenuData = '';
			}else{
				//获取数据
				this.params.conditionalMenuData = this.initData.conditionalMenuData;
			}
		},
		saveDefault: function(){
			if(confirm("您确定要保存对默认菜单的修改吗？")){
				saveMenuData(true,this.params.defaultMenuData,null)
			}
		},
		saveCondi: function(){
			var flag = false;
			if(this.params.menuId && confirm("您确定要保存对个性化菜单【" +  this.params.menuId +"】的修改吗？")){
				flag = true;
			}else if(!this.params.menuId && confirm("您确定要新增该个性化菜单吗？")){
				flag = true;
			}
			if(flag){
				saveMenuData(false,this.params.conditionalMenuData,this.params.menuId)
			}else{
				return;
			}
		}
	}
});
/**
 * 获取所有个性化菜单ID
 */
function getCondiMenuIdList(){
	$.ajax({
		url:'${contextPath}/wxmenu/getCondiMenuIdList',
		data:{},
		success: function(jsonRet,status,xhr){
			if(jsonRet) {
				if(0 == jsonRet.errcode) {
					container.initData.menuIdList = jsonRet.data;
				}else{//出现逻辑错误
					alert(jsonRet.errmsg);
				}
			}else{
				alert('系统数据访问失败！');
			}
		},
		dataType: 'json'
	});
}
getCondiMenuIdList();
/**
 * isDefault：是否为默认菜单
 * menuId：个性化菜单ID
 */
function getMenuData(isDefault,menuId){
	var geturl = '${contextPath}/wxmenu/';
	if(isDefault){
		geturl = geturl + 'getDefaultMenu';
	}else{
		geturl = geturl + 'getConditionalMenu/' + menuId;
	}
	$.ajax({
		url: geturl,
		data:{},
		success: function(jsonRet,status,xhr){
			if(jsonRet){
				if(0 == jsonRet.errcode){
					if(isDefault){
						container.initData.defaultMenuData = jsonRet.data;//用户重置备用
						container.params.defaultMenuData = jsonRet.data;//用于显示待修改
					}else{
						container.initData.menuId = menuId;//用户重置备用
						container.initData.conditionalMenuData = jsonRet.data;//用户重置备用
						container.params.menuId = menuId;//用于显示待修改
						container.params.conditionalMenuData = jsonRet.data;//用于显示待修改
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
getMenuData(true,null); //获取默认菜单
/**
 * isDefault：是否为默认菜单
 * menuData：菜单数据
 * menuId：个性化菜单ID
 */
function saveMenuData(isDefault,menuData,menuId){
	var saveurl = '${contextPath}/wxmenu/';
	if(isDefault){
		saveurl = saveurl + 'saveDefaultMenu';
	}else{
		saveurl = saveurl + 'saveConditionalMenu';
	}
	$.ajax({
		url:saveurl,
		data:{'jsonMenuStr':menuData,'menuId':menuId},
		success: function(jsonRet,status,xhr){
			if(jsonRet){
				if(0 == jsonRet.errcode){
					//更新初始化数据
					if(isDefault){
						container.initData.defaultMenuData = menuData;//更新重置备用信息
					}else{
						for(var i=0;i<container.initData.menuIdList.length;i++){
							if(container.initData.menuIdList[i] == menuId){
								container.initData.menuIdList.splice(i,1);
							}
						}
						container.initData.menuIdList.unshift(jsonRet.menuid);//更新重置备用信息
						container.initData.menuId = jsonRet.menuid;//更新重置备用信息
						container.initData.conditionalMenuData = menuData;//更新重置备用信息
						container.params.menuId = jsonRet.menuid;//使用新的menuId
					}
					alert('微信菜单信息保存成功！！！');
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