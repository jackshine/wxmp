package com.jeekhan.wx.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.jeekhan.wx.model.MediaMaterial;
import com.jeekhan.wx.utils.PageCond;

public interface MediaMaterialService {
	/**
	 * 添加素材
	 * @param material
	 * @return 记录ID或错误码
	 */
	public BigInteger add(MediaMaterial material);
	
	/**
	 * 更新素材
	 * @param material
	 * @return 记录ID或错误码
	 */
	public BigInteger update(MediaMaterial material);
	
	/**
	 * 删除素材
	 * @param id
	 * @return	删除记录数
	 */
	public int delete(BigInteger id);
	
	/**
	 * 根据id获取素材
	 * @param id
	 * @return
	 */
	public MediaMaterial get(BigInteger id);
	
	/**
	 * 根据查询条件统计素材数量
	 * @param params
	 * @return
	 */
	public int countAll(Map<String,Object> params);
	
	/**
	 * 根据查询条件分页获取素材数据
	 * @param params
	 * @param pageCond
	 * @return
	 */
	public List<MediaMaterial> getAll(Map<String,Object> params,PageCond pageCond);
	
}
