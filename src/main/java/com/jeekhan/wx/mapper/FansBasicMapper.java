package com.jeekhan.wx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeekhan.wx.model.FansBasic;
import com.jeekhan.wx.utils.PageCond;

public interface FansBasicMapper {
	
    int deleteById(String openId);

    int insert(FansBasic record);

    FansBasic selectById(String openId);

    int updateById(FansBasic record);
    
    /**
     * 根据条件分页查询数据
     * @param params	查询条件
     * @param pageCond	分页信息
     * @return
     */
    List<FansBasic> selectAll(@Param("params")Map<String,Object> params,@Param("pageCond")PageCond pageCond);
    
    /**
     * 根据条件统计数据条数
     * @param params	查询条件
     * @param params
     * @return
     */
    int countAll(@Param("params")Map<String,Object> params);

    /**
	 * 从粉丝用户身上收回指定标签
	 * @param tagId
	 * @return
	 */
    int removeTag(Integer tagId);
    
    /**
	 * 为粉丝用户打上指定标签
	 * @param openId
	 * @param tagId
	 * @return
	 */
    int addTag(@Param("openId")String openId,@Param("tagid")Integer tagId);
    
}