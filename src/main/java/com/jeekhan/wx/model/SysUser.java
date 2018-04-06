package com.jeekhan.wx.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SysUser {
    private Integer id;

    @NotNull(message="用户名不可为空！")
    @Size(min=3,max=50,message="用户名长度为3-20个字符！")
	private String username;

    @NotNull(message="邮箱不可为空！")
    @Size(max=100,message="邮箱最长100个字符！")
    @Email(message="邮箱格式不正确！")
	private String email;

    @Pattern(regexp="^[012]$",message="性别取值只可为【0-保密，1-男，2-女！")
	private String sex;

    @NotNull(message="密码不可为空！")
    @Size(min=6,max=20,message="密码长度为6-20个字符！")
	private String passwd;

    @Size(max=600,message="个人简介最长为600个字符！")
	private String introduce;

	private Date updateTime;

	private String roleLvl;

	private String status;

	@NotNull(message="是否开通客服不可为空！")
	@Pattern(regexp="^[01]$",message="是否开通客服取值只可为【0-不开通，1-开通！")
	private String openKf;

	@Size(min=6,max=20,message="客服昵称长度为3-20个字符！")
	private String nickname;

	private String headImg;
	
	@Size(min=6,max=20,message="客服密码长度为6-20个字符！")
	private String kfPasswd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd == null ? null : passwd.trim();
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce == null ? null : introduce.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRoleLvl() {
		return roleLvl;
	}

	public void setRoleLvl(String roleLvl) {
		this.roleLvl = roleLvl == null ? null : roleLvl.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getOpenKf() {
		return openKf;
	}

	public void setOpenKf(String openKf) {
		this.openKf = openKf == null ? null : openKf.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg == null ? null : headImg.trim();
	}

	public String getKfPasswd() {
		return kfPasswd;
	}

	public void setKfPasswd(String kfPasswd) {
		this.kfPasswd = kfPasswd;
	}

}