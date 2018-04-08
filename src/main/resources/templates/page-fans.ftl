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
<script>
var container = new Vue({
	el:'#container',
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
						url: '${contextPath}/fans/deleteTag',
						data: {'tagId':this.selected.tagId},
						success: function(jsonRet,status,xhr){
							if(jsonRet){
								if(0 == jsonRet.errcode){
									alert("标签【" + container.selected.tagId + "】已成功删除！");
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
			url: '${contextPath}/fans/getAllTags',
			data: {},
			success: function(jsonRet,status,xhr){
				if(jsonRet){
					if(0 == jsonRet.errcode){
						container.tagList = jsonRet.data;
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

<!-- 创建标签模态对话框（Modal） -->
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
					url: '${contextPath}/fans/' + (this.tagId ? 'updateTag':'createTag'),
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