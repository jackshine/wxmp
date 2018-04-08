package com.jeekhan.wx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeekhan.wx.mapper.FansTagMapper;
import com.jeekhan.wx.model.FansTag;
import com.jeekhan.wx.service.FansTagService;

@Service
public class FansTagServiceImpl implements FansTagService {
	
	@Autowired
	private FansTagMapper fansTagMapper;
	
	/**
	 * 添加粉丝标签
	 * @param fansTag
	 * @return 标签ID或小于0的错误码
	 */
	public Integer add(FansTag fansTag) {
		fansTag.setUpdateTime(new Date());
		int cnt = this.fansTagMapper.insert(fansTag);
		if(cnt>0) {
			return fansTag.getTagId();
		}
		return -1;
	}
	
	/**
	 * 更新粉丝标签
	 * @param fansTag
	 * @return 标签ID或小于0的错误码
	 */
	public Integer update(FansTag fansTag) {
		fansTag.setUpdateTime(new Date());
		int cnt = this.fansTagMapper.updateById(fansTag);
		if(cnt>0) {
			return fansTag.getTagId();
		}
		return -1;
	}
	
	/**
	 * 物理删除粉丝标签
	 * @param tagId
	 * @return 删除记录数量
	 */
	public Integer delete(Integer tagId) {
		return this.fansTagMapper.deleteById(tagId);
	}
	
	/**
	 * 根据tagid获取标签信息
	 * @param tagId
	 * @return
	 */
	public FansTag get(Integer tagId) {
		return this.fansTagMapper.selectById(tagId);
	}
	
	/**
	 * 根据所有标签数据
	 * @return
	 */
	public List<FansTag> getAll(){
		return this.fansTagMapper.selectAll();
	}

}
