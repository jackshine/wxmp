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
    
    <link rel="stylesheet" href="${contextPath}/css/fileinput.min.css">  
    <script src="${contextPath}/script/fileinput.min.js"></script>
    <script src="${contextPath}/script/zh.js"></script>
</head>
<body class="light-gray-bg">
<div style="height:18px;background-color:#E0E0E0 ;margin-bottom:5px;"></div>
<div class="container" id="container">
  <#include "/page-top-menu.ftl" encoding="utf8"> 
  <div class="row" style="padding:5px 0 0 0;">
    <div class="panel panel-info">
      <div class="panel-heading" style="margin:0">
        <#if operator.roleLvl == "L9">
        <button type="button" class="btn btn-primary" style="margin-left:20px" v-on:click="addUser">&nbsp;&nbsp;新增用户&nbsp;&nbsp;</button>
        <button type="button" class="btn btn-warning" style="margin-left:20px" v-on:click="destroyUser">&nbsp;&nbsp;注销用户&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-primary" style="margin-left:20px" v-on:click="applyKF">&nbsp;&nbsp;申请客服&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-warning" style="margin-left:20px" v-on:click="destroyKF">&nbsp;&nbsp;注销客服&nbsp;&nbsp; </button>
        </#if>
        <button type="button" class="btn btn-primary" style="margin-left:20px" v-on:click="editUser">&nbsp;&nbsp;修改基本信息&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-primary" style="margin-left:20px" v-on:click="editPwd">&nbsp;&nbsp;修改密码&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-primary" style="margin-left:20px" v-on:click="editKF">&nbsp;&nbsp;修改客服信息&nbsp;&nbsp; </button>
        <button type="button" class="btn btn-primary" style="margin-left:20px" v-on:click="editKFImg">&nbsp;&nbsp;修改客服头像&nbsp;&nbsp; </button>
      </div>
      <div class="panel-body">
        <table class="table table-bordered table-hover ">
          <thead>
   	        <tr><th width="3%"><th width="15%">用户名称</th><th width="15%">客服昵称</th><th width="20%">邮箱</th><th width="8%">用户级别</th><th width="8%">状态</th><th width="10%">是否开通客服</th><th width="25%">更新时间</th></tr>
          </thead>
          <tbody> 
            <tr v-for="(user,index) in users" v-on:click="select_username = user.username" onclick="$(this).addClass('active');$(this).siblings().removeClass('active')">
              <td>{{index+1}}</td>
              <td>{{user.username}}</td>
              <td>{{user.nickname}}</td>
              <td>{{user.email}}</td>
              <td>{{user.roleLvl}}</td>
              <td>{{getStatus(user.status)}}</td>
              <td>{{getOpenKF(user.openKf)}}</td>
              <td>{{user.updateTime}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>  
  </div>  
  
</div>
<script type="text/javascript">
//页面数据初始化
 var usersVm = new Vue({
	 el:"#container",
	 data:{
		 users:[],
		 select_username:null
	 },
	 methods:{
		 getStatus:function(code){
			 if(code == '1'){
				 return '正常';
			 }else if(code == 'D'){
				 return '已注销';
			 }
		 },
		 getOpenKF:function(code){
			 if(code == '1'){
				 return '已开通';
			 }else if(code == '0'){
				 return '未开通';
			 }
		 },
		 <#if operator.roleLvl == "L9">
		 addUser:function(){
			$("#addUserModal").modal('show');
			$("#username").val('');
			$("#email").val('');
			$("#openKFFlag_true").val('');
		 },
		 destroyUser:function(){
			 if(this.select_username == null){
				 alert("请选择要注销的用户！");
				 return;
			 }
			 if(this.select_username == '${operator.loginUsername}'){
				 alert("您不可注销自己！");
				 return;
			 }
			 if(confirm("您确定是要注销用户" + this.select_username + "吗？用户注销的同时客服账号也将同时注销！！！")){
				 $.ajax({
						url: '${contextPath}/sys/user/destroy',
						data: {'username':usersVm.select_username},
						success: function(jsonRet,status,xhr){
							if(jsonRet){
								if(0 == jsonRet.errcode){
									alert("用户【" + usersVm.select_username + "】已成功注销！");
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
		 },
		 applyKF:function(){
			 if(this.select_username == null){
				 alert("请选择要申请微信客服的用户！");
				 return;
			 }
			 if(confirm("您确定是要为用户" + this.select_username + "申请微信客服吗？")){
				 $.ajax({
						url: '${contextPath}/kfaccount/add',
						data: {'account':usersVm.select_username,'nickname':usersVm.select_username,'pwd':'123456'},
						success: function(jsonRet,status,xhr){
							if(jsonRet){
								if(0 == jsonRet.errcode){
									alert("用户【" + usersVm.select_username + "】已成功申请微信客服！");
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
		 },
		 destroyKF:function(){
			 if(this.select_username == null){
				 alert("请选择要注销客服的用户！");
				 return;
			 }
			 if(confirm("您确定是要为用户" + this.select_username + "注销微信客服吗？")){
				 $.ajax({
						url: '${contextPath}/kfaccount/delete',
						data: {'account':usersVm.select_username},
						success: function(jsonRet,status,xhr){
							if(jsonRet){
								if(0 == jsonRet.errcode){
									alert("用户【" + usersVm.select_username + "】已成功注销微信客服！");
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
		 },
		 </#if>
		 editUser:function(){
			 $("#editUserModal").modal('show');
			 for(var i=0;i<this.users.length;i++){
				 var u = this.users[i];
				 if(u.username == '${operator.loginUsername}'){
					 editUser.username = u.username;
					 editUser.sex = u.sex;
					 editUser.email = u.email;
					 editUser.introduce = u.introduce;
				 }
			 }
		 },
		 editPwd :function(){
			 $("#editPwdModal").modal('show');
			 editPwd.oldPassword = '';
			 editPwd.newPassword = '';
			 editPwd.cPassword = '';
		 },
		 editKF:function(){
			 $("#editKFModal").modal('show');
			 for(var i=0;i<this.users.length;i++){
				 var u = this.users[i];
				 if(u.username == '${operator.loginUsername}'){
					 editKF.username = u.username;
					 editKF.kfPasswd = u.kfPasswd;
					 editKF.nickname = u.nickname;
				 }
			 }
		 },
		 editKFImg :function(){
			 $("#editKFImgModal").modal('show');
		 }
	 }
 });
 $.ajax({
		url: '${contextPath}/sys/user/get',
		data: {},
		success: function(jsonRet,status,xhr){
			if(jsonRet){
				if(0 == jsonRet.errcode){
					usersVm.users = jsonRet.datas;
				}else{//出现逻辑错误
					alert(jsonRet.errmsg);
				}
			}else{
				alert('系统数据访问失败！')
			}
		},
		dataType: 'json'
	});
</script>

<#if operator.roleLvl == "L9">
<!-- 新增用户模态对话框（Modal） -->
<div class="modal fade " id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="addUserModalLabel">新增系统用户</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" action="add" role="form" >
               <div class="form-group">
               </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">用户名<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input class="form-control" v-model="username" type="text" value="" maxlength=50 required placeholder="请输入用户名(3-20字符)..." >
			      </div>
			   </div>
			   <div class="form-group">
			      <label for="keywords" class="col-sm-2 control-label">邮箱<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input type="email" class="form-control" v-model="email" maxlength=100 required placeholder="请输入邮箱..." >
			      </div>
			   </div>
               <div id="openKFFlag" class="form-group">
			       <label class="col-sm-2 control-label">开通客服<span style="color:red" >*</span></label>
			        <div class="col-sm-10 radio">
			         <label class="radio-inline"><input type="radio" name="openKf" v-model="openKf" value="0" style="display:block"> 否</label>
			         <label class="radio-inline"><input type="radio" name="openKf" v-model="openKf" value="1" style="display:block"> 是</label>
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
var addUser = new Vue({
	el: '#addUserModal',
	  data: {
		  username: '',
		  email:'',
		  openKf:''
	  },
	  methods: {
		  submit:function(){
				if(!this.username || this.username.length<3 || this.username.length>20){
					alert("用户名长度为3-20个字符！");
					return false;
				}
				if(!this.email || this.email.length<3 || this.email.length>100){
					alert("邮箱长度为3-100个字符！");
					return false;
				}
				$.ajax({
					url: '${contextPath}/sys/user/add',
					data: {'username':this.username,'email':this.email,'openKf':this.openKf,'passwd':'123456'},
					success: function(jsonRet,status,xhr){
						if(jsonRet){
							if(0 == jsonRet.errcode){
								alert("用户【" + addUser.username + "】已成功添加！！");
								$("#addUserModal").modal('hide');
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
</#if>

<!-- 用户基本信息修改模态对话框（Modal） -->
<div class="modal fade " id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="editUserModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="editUserModalLabel">修改用户基本信息</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" action="" role="form" >
               <div class="form-group">
               </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">用户名</label>
			      <div class="col-sm-10">
			         <input type="text" class="form-control" v-model="username" maxlength=50 disabled >
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">邮箱<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input type="email" class="form-control" v-model="email" maxlength=100 required placeholder="请输入邮箱..." >
			      </div>
			   </div>
               <div class="form-group">
			       <label class="col-sm-2 control-label">性别<span style="color:red" >*</span></label>
			       <div class="col-sm-10 radio">
			         <label class="radio-inline"><input type="radio" name="sex" v-model="sex" value="0" style="display:block"> 保密</label>
			         <label class="radio-inline"><input type="radio" name="sex" v-model="sex" value="1" style="display:block"> 男</label>
			         <label class="radio-inline"><input type="radio" name="sex" v-model="sex" value="2" style="display:block"> 女</label>
			       </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">个人简介<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <textarea class="form-control" v-model="introduce" maxlength=600 required rows=5 placeholder="请输入个人简介..." ></textarea>
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
var editUser = new Vue({
	el:'#editUserModal',
	data:{
		username:'',
		email:'',
		sex:'0',
		introduce:''
	},
	methods:{
		submit:function(){
			if(!this.email || this.email.length<3 || this.email.length>100){
				alert("邮箱长度为3-100个字符！");
				return false;
			}
			if(!this.introduce || this.introduce.length>100){
				alert("个人简介长度为600个字符！");
				return false;
			}
			$.ajax({
				url: '${contextPath}/sys/user/update',
				data: {'username':this.username,'email':this.email,'sex':this.sex,'passwd':'123456','introduce':this.introduce,'openKf':0},
				success: function(jsonRet,status,xhr){
					if(jsonRet){
						if(0 == jsonRet.errcode){
							alert("用户【" + editUser.username + "】基本信息修改成功！！");
							$("#editUserModal").modal('hide');
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

<!-- 用户密码修改模态对话框（Modal） -->
<div class="modal fade " id="editPwdModal" tabindex="-1" role="dialog" aria-labelledby="editPwdModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="editPwdModalLabel">修改用户密码</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" action="add" role="form" >
               <div class="form-group">
               </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">用户名</label>
			      <div class="col-sm-10">
			         <input type="text" class="form-control" v-model="username" maxlength=50 disabled >
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">原密码<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input type="password" class="form-control" v-model="oldPassword" maxlength=20 required placeholder="请输入原密码..." >
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">新密码<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input type="password" class="form-control" v-model="newPassword" maxlength=20 required placeholder="请输入新密码..." >
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">确认密码<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input type="password" class="form-control" v-model="cPassword" v-on:change="confirmPwd" maxlength=20 required placeholder="请输入确认密码..." >
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
var editPwd = new Vue({
	el:'#editPwdModal',
	data:{
		username:'${operator.loginUsername}',
		oldPassword:'',
		newPassword:'',
		cPassword:''
	},
	methods:{
		confirmPwd:function(){
			if(this.newPassword != this.cPassword){
				cPassword = "";
				alert("新密码与确认密码不一致！");
			}
		},
		submit:function(){
			if(this.newPassword != this.cPassword){
				cPassword = "";
				alert("新密码与确认密码不一致！");
				return ;
			}
			if(!this.oldPassword || this.oldPassword.length<6 ||this.oldPassword.length>20){
				alert("原密码长度为6-20个字符！");
				return false;
			}
			if(!this.newPassword || this.newPassword.length<6 ||this.newPassword.length>20){
				alert("新密码长度为6-20个字符！");
				return false;
			}
			$.ajax({
				url: '${contextPath}/sys/user/updatePwd',
				data: {'username':this.username,'newPwd':this.newPassword,'oldPwd':this.oldPassword},
				success: function(jsonRet,status,xhr){
					if(jsonRet){
						if(0 == jsonRet.errcode){
							alert("用户【" + editPwd.username + "】密码信息修改成功！！");
							$("#editPwdModal").modal('hide');
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

<!-- 用户客服头像修改模态对话框（Modal） -->
<div class="modal fade " id="editKFImgModal" tabindex="-1" role="dialog" aria-labelledby="editKFImgModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="editKFImgModalLabel">修改客服头像</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" role="form" enctype='multipart/form-data' id="headImgForm">
               <div class="form-group">
               </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">客服账号</label>
			      <div class="col-sm-10">
			         <input type="text" class="form-control" name="account" value="${operator.loginUsername}" maxlength=20 readonly >
			      </div>
			   </div>
               <div class="form-group">
                 <label for="introduce" class="col-xs-2 control-label">客服头像<span style="color:red" >*</span></label>
                 <div class="col-xs-5">
                   <input id="picFile"  type="file" name="headImg" type="file" accept="image/jpg" class="file-loading">
                 </div>
                </div>
			</form>
         </div>
<!--          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" id="addUserSubmit" v-on:click="submit">提交</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div> -->
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
$(document).on('ready', function() {
    $("#picFile").fileinput({
    	language: 'zh', //设置语言
        uploadUrl: '${contextPath}/kfaccount/uploadImg', //上传的地址
        uploadAsync:true,
        showUpload: true, //是否显示上传按钮
        uploadExtraData:{"account":'${operator.loginUsername}'},
        previewFileType: "image",
        browseClass: "btn btn-success",
        browseLabel: "Pick Image",
        browseIcon: "<i class=\"glyphicon glyphicon-picture\"></i> ",
        removeClass: "btn btn-danger",
        removeLabel: "Delete",
        removeIcon: "<i class=\"glyphicon glyphicon-trash\"></i> ",
        uploadClass: "btn btn-info",
        uploadLabel: "Upload",
        uploadIcon: "<i class=\"glyphicon glyphicon-upload\"></i> "
    });
    //异步上传错误结果处理
    $('#picFile').on('fileerror', function(event, data, msg) {
		alert("文件上传失败！");
		$("#editKFImgModal").modal('hide');
		window.location.reload();
    });
    //异步上传成功结果处理
    $("#picFile").on("fileuploaded", function (event, data, previewId, index) {
    		var jsonRet = data.response;
    		if(jsonRet){
			if(0 == jsonRet.errcode){
				alert("用户【" + editUser.username + "】客服头像修改成功！！");
				$("#editKFImgModal").modal('hide');
				window.location.reload();
			}else{//出现逻辑错误
				alert(jsonRet.errmsg);
				$("#editKFImgModal").modal('hide');
				window.location.reload();
			}
		}else{
			alert('系统数据访问失败！')
		}
    });
});
</script>

<!-- 微信客服基本信息修改模态对话框（Modal） -->
<div class="modal fade " id="editKFModal" tabindex="-1" role="dialog" aria-labelledby="editKFModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="editKFModalLabel">修改微信客服基本信息</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" method ="post" action="add" role="form" >
               <div class="form-group">
               </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">账户号</label>
			      <div class="col-sm-10">
			         <input type="text" class="form-control" v-model="username" maxlength=50 disabled >
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">客服昵称<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input type="text" class="form-control" v-model="nickname" maxlength=20 required placeholder="请输入客服昵称..." >
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">客服密码<span style="color:red" >*</span></label>
			      <div class="col-sm-10">
			         <input type="password" class="form-control" v-model="kfPasswd" maxlength=20 required placeholder="请输入客服密码..." >
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
var editKF = new Vue({
	el:'#editKFModal',
	data:{
		username:'',
		nickname:'',
		kfPasswd:''
	},
	methods:{
		submit:function(){
			if(!this.nickname || this.nickname.length<3 || this.nickname.length>20){
				alert("客服昵称长度为3-20个字符！");
				return false;
			}
			if(!this.kfPasswd || this.kfPasswd.length<3 || this.kfPasswd.length>20){
				alert("客服密码长度为3-20个字符！");
				return false;
			}
			$.ajax({
				url: '${contextPath}/kfaccount/update',
				data: {'account':this.username,'nickname':this.nickname,'pwd':this.kfPasswd},
				success: function(jsonRet,status,xhr){
					if(jsonRet){
						if(0 == jsonRet.errcode){
							alert("用户【" + editUser.username + "】基本信息修改成功！！");
							$("#editKFModal").modal('hide');
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