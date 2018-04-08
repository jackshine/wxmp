package com.jeekhan.wx.service;

import java.util.List;

import com.jeekhan.wx.model.FansTag;

public interface FansTagService {
	
	/**
	 * 添加粉丝标签
	 * @param fansTag
	 * @return 标签ID或小于0的错误码
	 */
	public Integer add(FansTag fansTag);
	
	/**
	 * 更新粉丝标签
	 * @param fansTag
	 * @return 标签ID或小于0的错误码
	 */
	public Integer update(FansTag fansTag);
	
	/**
	 * 物理删除粉丝标签
	 * @param tagId
	 * @return 删除记录数
	 */
	public Integer delete(Integer tagId);
	
	/**
	 * 根据tagid获取标签信息
	 * @param tagId
	 * @return
	 */
	public FansTag get(Integer tagId);
	
	/**
	 * 根据所有标签数据
	 * @return
	 */
	public List<FansTag> getAll();

}
