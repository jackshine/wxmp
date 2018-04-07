package com.jeekhan.wx.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeekhan.wx.mapper.WXQRCodeMapper;
import com.jeekhan.wx.model.WXQRCode;
import com.jeekhan.wx.service.WXQRCodeService;
import com.jeekhan.wx.utils.PageCond;

@Service
public class WXQRCodeServiceImpl implements WXQRCodeService {

	@Autowired
	private WXQRCodeMapper wXQRCodeMapper;
	
	/**
	 * 添加二维码信息
	 * @param wxQRCode
	 * @return 二维码记录ID或错误码
	 */
	@Override
	public Integer add(WXQRCode wxQRCode) {
		wxQRCode.setCreateTime(new Date());
		int cnt = this.wXQRCodeMapper.insert(wxQRCode);
		if(cnt > 0) {
			return wxQRCode.getId();
		}
		return -1;
	}

	/**
	 * 更新二维码信息
	 * @param wxQRCode
	 * @return 二维码记录ID或错误码
	 */
	@Override
	public Integer update(WXQRCode wxQRCode) {
		int cnt = this.wXQRCodeMapper.updateById(wxQRCode);
		if(cnt > 0) {
			return wxQRCode.getId();
		}
		return -1;
	}

	/**
	 * 删除二维码信息
	 * @param wxQRCode
	 * @return 二维码记录ID或错误码
	 */
	@Override
	public Integer delete(WXQRCode wxQRCode) {
		int cnt = this.wXQRCodeMapper.deleteById(wxQRCode.getId());
		if(cnt > 0) {
			return wxQRCode.getId();
		}
		return -1;
	}

	/**
	 * 获取二维码信息
	 * @param id
	 * @return
	 */
	@Override
	public WXQRCode get(Integer id) {
		return this.wXQRCodeMapper.selectById(id);
	}

	/**
	 * 获取二维码信息
	 * @param isPerm		是否临时二维码
	 * @param sceneId	场景值ID
	 * @return
	 */
	@Override
	public WXQRCode get(String isPerm, Integer sceneId) {
		return this.wXQRCodeMapper.selectBySceneId(sceneId, isPerm);
	}

	/**
	 * 统计根据查询条件得到的条数
	 * @param condParams
	 * @return
	 */
	@Override
	public int countAll(Map<String, Object> condParams) {
		return this.wXQRCodeMapper.countAll(condParams);
	}

	/**
	 * 根据条件获取二维码
	 * @param condParams	查询条件
	 * @param pageCond 分页信息
	 * @return
	 */
	@Override
	public List<WXQRCode> getAll(Map<String, Object> condParams, PageCond pageCond) {
		return this.wXQRCodeMapper.selectAll(condParams, pageCond);
	}

	/**
	 * 获取二维码
	 * @param ticket
	 * @return
	 */
	public WXQRCode get(String ticket) {
		List<WXQRCode> list = this.wXQRCodeMapper.selectByTicket(ticket);
		if(list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
