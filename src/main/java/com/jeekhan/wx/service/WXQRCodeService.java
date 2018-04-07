package com.jeekhan.wx.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeekhan.wx.model.WXQRCode;
import com.jeekhan.wx.utils.PageCond;

/**
 * 微信公众号二维码管理服务
 * @author jeekhan
 *
 */
public interface WXQRCodeService {
	
	/**
	 * 添加二维码信息
	 * @param wxQRCode
	 * @return
	 */
	Integer add(WXQRCode wxQRCode);
	
	/**
	 * 更新二维码信息
	 * @param wxQRCode
	 * @return
	 */
	Integer update(WXQRCode wxQRCode);
	
	/**
	 * 删除二维码信息
	 * @param wxQRCode
	 * @return
	 */
	Integer delete(WXQRCode wxQRCode);
	
	/**
	 * 获取二维码信息
	 * @param id
	 * @return
	 */
	WXQRCode get(Integer id);
	
	/**
	 * 获取二维码信息
	 * @param isPerm		是否临时二维码
	 * @param sceneId	场景值ID
	 * @return
	 */
	WXQRCode get(String isPerm,Integer sceneId);
	
	/**
	 * 统计根据查询条件得到的条数
	 * @param condParams
	 * @return
	 */
	int countAll(@Param("params")Map<String,Object> condParams);
	
	/**
	 * 根据条件获取二维码
	 * @param condParams	查询条件
	 * @param pageCond 分页信息
	 * @return
	 */
	List<WXQRCode> getAll(Map<String,Object> condParams,PageCond pageCond);
	
	/**
	 * 获取二维码
	 * @param ticket
	 * @return
	 */
	WXQRCode get(String ticket);
	
}
