package com.jeekhan.wx.service;

import java.util.List;
import java.util.Map;

import com.jeekhan.wx.model.FansBasic;
import com.jeekhan.wx.utils.PageCond;

public interface FansBasicService {
	/**
	 * 用户关注：添加粉丝记录
	 * @param fansBasic
	 * @return
	 */
	public String add(FansBasic fansBasic);
	
	/**
	 * 更新粉丝用户基本信息
	 * @param fansBasic
	 * @return
	 */
	public String update(FansBasic fansBasic);
	
	/**
	 * 用户取消关注：删除粉丝用户
	 * @param openId
	 * @return
	 */
	public String delete(String openId);
	
	/**
	 * 根据openid获取粉丝用户信息
	 * @param openId
	 * @return
	 */
	public FansBasic get(String openId);
	
	/**
	 * 根据查询条件统计粉丝用户数量
	 * @param params
	 * @return
	 */
	public int countAll(Map<String,Object> params);
	
	/**
	 * 根据查询条件分页获取粉丝用户数据
	 * @param params
	 * @param pageCond
	 * @return
	 */
	public List<FansBasic> getAll(Map<String,Object> params,PageCond pageCond);
	
	/**
	 * 从粉丝用户身上收回指定标签
	 * @param tagId
	 * @return
	 */
	public int removeTagFromFanses(Integer tagId) ;
	
	/**
	 * 为粉丝用户打上指定标签
	 * @param openIds
	 * @param tagId
	 * @return
	 */
	public void addTag2Fanses(List<String> openIds,Integer tagId) ;
	
	/**
	 * 为粉丝用户更新备注
	 * @param openId
	 * @param remark
	 * @return 更新记录数
	 */
	public int updateRemark(String openId,String remark);
	
	/**
	 * 将粉丝用户移入黑名单
	 * @param openIds
	 * @return 更新记录数
	 */
	public void blackFans(List<String> openIds);
	
	/**
	 * 将粉丝用户移出黑名单
	 * @param openIds
	 * @return 更新记录数
	 */
	public void unBlackFans(List<String> openIds);
	
	

}
