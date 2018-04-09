package com.jeekhan.wx.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeekhan.wx.mapper.FansBasicMapper;
import com.jeekhan.wx.model.FansBasic;
import com.jeekhan.wx.service.FansBasicService;
import com.jeekhan.wx.utils.PageCond;

@Service
public class FansBaiscServieImpl implements FansBasicService {
	
	@Autowired
	private FansBasicMapper fansBasicMapper;
	
	/**
	 * 用户关注：添加粉丝记录
	 * @param fansBasic
	 * @return
	 */
	public String add(FansBasic fansBasic) {
		fansBasic.setUpdateTime(new Date());
		fansBasic.setIsBlack("0");
		fansBasicMapper.insert(fansBasic);
		return fansBasic.getOpenId();
	}
	
	/**
	 * 更新粉丝用户基本信息
	 * @param fansBasic
	 * @return
	 */
	public String update(FansBasic fansBasic) {
		fansBasic.setUpdateTime(new Date());
		fansBasicMapper.updateById(fansBasic);
		return fansBasic.getOpenId();
	}
	
	/**
	 * 用户取消关注：删除粉丝用户
	 * @param openId
	 * @return
	 */
	public String delete(String openId) {
		fansBasicMapper.deleteById(openId);
		return openId;
	}
	
	/**
	 * 根据openid获取粉丝用户信息
	 * @param openId
	 * @return
	 */
	public FansBasic get(String openId) {
		return fansBasicMapper.selectById(openId);
	}
	
	/**
	 * 根据查询条件统计粉丝用户数量
	 * @param params
	 * @return
	 */
	public int countAll(Map<String,Object> params) {
		return fansBasicMapper.countAll(params);
	}
	
	/**
	 * 根据查询条件分页获取粉丝用户数据
	 * @param params
	 * @param pageCond
	 * @return
	 */
	public List<FansBasic> getAll(Map<String,Object> params,PageCond pageCond) {
		return fansBasicMapper.selectAll(params, pageCond);
	}

	/**
	 * 从粉丝用户身上收回指定标签
	 * @param tagId
	 * @return
	 */
	public int removeTagFromFanses(Integer tagId) {
		return this.fansBasicMapper.removeTag(tagId);
	}
	
	/**
	 * 为粉丝用户打上指定标签
	 * @param openIds
	 * @param tagId
	 * @return
	 */
	public void addTag2Fanses(List<String> openIds,Integer tagId) {
		for(String openId:openIds) {
			this.fansBasicMapper.addTag(openId, tagId);
		}
	}

	/**
	 * 为粉丝用户更新备注
	 * @param openId
	 * @param remark
	 * @return 更新记录数
	 */
	public int updateRemark(String openId,String remark) {
		FansBasic basic = new FansBasic();
		basic.setUpdateTime(new Date());
		basic.setOpenId(openId);
		basic.setRemark(remark);
		return this.fansBasicMapper.updateById(basic);
	}
	
	/**
	 * 将粉丝用户移入黑名单
	 * @param openIds
	 * @return 更新记录数
	 */
	public void blackFans(List<String> openIds) {
		FansBasic basic = new FansBasic();
		for(String openId:openIds) {
			basic.setUpdateTime(new Date());
			basic.setOpenId(openId);
			basic.setIsBlack("1");
			this.fansBasicMapper.updateById(basic);
		}
	}
	
	/**
	 * 将粉丝用户移出黑名单
	 * @param openIds
	 * @return 更新记录数
	 */
	public void unBlackFans(List<String> openIds) {
		FansBasic basic = new FansBasic();
		for(String openId:openIds) {
			basic.setUpdateTime(new Date());
			basic.setOpenId(openId);
			basic.setIsBlack("0");
			this.fansBasicMapper.updateById(basic);
		}
	}
	
}
