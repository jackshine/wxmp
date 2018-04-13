package com.jeekhan.wx.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeekhan.wx.mapper.MediaMaterialMapper;
import com.jeekhan.wx.model.MediaMaterial;
import com.jeekhan.wx.service.MediaMaterialService;
import com.jeekhan.wx.utils.PageCond;

@Service
public class MediaMaterialServiceImpl implements MediaMaterialService {
	
	@Autowired
	private MediaMaterialMapper mediaMaterialMapper;
	/**
	 * 添加素材
	 * @param material
	 * @return 记录ID或错误码
	 */
	public BigInteger add(MediaMaterial material) {
		material.setUpdateTime(new Date());
		int cnt = this.mediaMaterialMapper.insert(material);
		if(cnt>0) {
			return material.getId();
		}else {
			return new BigInteger("-1");
		}
	}
	
	/**
	 * 更新素材
	 * @param material
	 * @return 记录ID或错误码
	 */
	public BigInteger update(MediaMaterial material) {
		material.setUpdateTime(new Date());
		int cnt = this.mediaMaterialMapper.updateById(material);
		if(cnt>0) {
			return material.getId();
		}else {
			return new BigInteger("-1");
		}
	}
	
	/**
	 * 删除素材
	 * @param id
	 * @return	删除记录数
	 */
	public int delete(BigInteger id) {
		return this.mediaMaterialMapper.deleteById(id);
	}
	
	/**
	 * 根据id获取素材
	 * @param id
	 * @return
	 */
	public MediaMaterial get(BigInteger id) {
		return this.mediaMaterialMapper.selectById(id);
	}
	
	/**
	 * 根据查询条件统计素材数量
	 * @param params
	 * @return
	 */
	public int countAll(Map<String,Object> params) {
		return this.mediaMaterialMapper.countAll(params);
	}
	
	/**
	 * 根据查询条件分页获取素材数据
	 * @param params
	 * @param pageCond
	 * @return
	 */
	public List<MediaMaterial> getAll(Map<String,Object> params,PageCond pageCond){
		return this.mediaMaterialMapper.selectAll(params, pageCond);
	}
	
}
