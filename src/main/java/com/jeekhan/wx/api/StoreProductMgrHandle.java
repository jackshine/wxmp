package com.jeekhan.wx.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeekhan.wx.utils.HttpUtils;

/**
 * 微信小店商品管理
 * @author Jee Khan
 *
 */
public class StoreProductMgrHandle {
	private static Logger log = LoggerFactory.getLogger(StoreProductMgrHandle.class);
	
	/**
	 * 添加商品
	 * @param obj	商品信息
	 * @return {"errcode": 0,"errmsg": "success","product_id": "pDF3iYwktviE3BzU3BKiSWWi9Nkw"}
	 * @throws JSONException 

	 */
	public static Object addProduct(JSONObject obj) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/create?access_token=" + token;
		log.info("添加商品(POST)：" + url+"，参数：" + obj);
		String ret = HttpUtils.doPostSSL(url, obj);
		log.info("添加商品(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 删除商品
	 * @param product_id	商品ID
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String deleteProduct(String product_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/del?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("product_id", product_id);
		log.info("删除商品(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除商品(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "商户商品失败！";
			}
		}
	}
	
	/**
	 * 修改商品
	 * 从未上架的商品所有信息均可修改，否则商品的名称(name)、商品分类(category)、商品属性(property)这三个字段不可修改
	 * @param obj
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static Object updateProduct(JSONObject obj) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/update?access_token=" + token;
		log.info("修改商品(POST)：" + url+"，参数：" + obj);
		String ret = HttpUtils.doPostSSL(url, obj);
		log.info("修改商品(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "商户商品失败！";
			}
		}
	}
	
	/**
	 * 查询商品
	 * @param product_id	商品ID
	 * @return {errcode:0,errmsg:"success",product_info:{...}}
	 * @throws JSONException 
	 */
	public static JSONObject getProduct(String product_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/get?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("product_id", product_id);
		log.info("查询商品(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("查询商品(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 获取指定状态的所有商品
	 * @param status	商品状态(0-全部, 1-上架, 2-下架)
	 * @return {errcode:0,errmsg:"success",products_info:[...]}
	 * @throws JSONException 
	 */
	public static JSONObject getProducts(int status) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/getbystatus?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", status);
		log.info("获取指定状态的所有商品(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取指定状态的所有商品(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 商品上下架
	 * @param product_id	商品ID
	 * @param status		商品上下架标识(0-下架, 1-上架)
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String modifyProductStatus(String product_id,int status) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/modproductstatus?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", status);
		jsonObj.put("product_id", product_id);
		log.info("商品上下架(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("商品上下架(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "商品上下架失败！";
			}
		}
	}

	/**
	 * 获取指定分类的所有子分类
	 * @param cate_id	大分类ID(根节点分类id为1)
	 * @return {"errcode": 0,"errmsg": "success","cate_list": [{"id": "537074292","name": "数码相机"},...]}
	 * @throws JSONException 
	 */
	public static JSONObject getSubCategory(String cate_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/category/getsub?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("cate_id", cate_id);
		log.info("获取指定分类的所有子分类(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取指定分类的所有子分类(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 获取指定子分类的所有SKU
	 * @param cate_id	商品子分类ID
	 * @return {"errcode": 0,"errmsg": "success","sku_table": [{"id": "1075741873","name": "颜色","value_list": [{"id": "1079742375", "name": "撞色"}...]}]}
	 * @throws JSONException 
	 */
	public static JSONObject getSubCateSKU(String cate_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/category/getsub?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("cate_id", cate_id);
		log.info("获取指定子分类的所有SKU(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取指定子分类的所有SKU(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 获取指定分类的所有属性
	 * @param cate_id	分类ID
	 * @return {"errcode": 0,"errmsg": "success","properties": [{"id": "1075741879","name": "品牌","property_value": [...]}]}
	 * @throws JSONException 
	 */
	public static JSONObject getSubCateProperty(String cate_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/category/getproperty?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("cate_id", cate_id);
		log.info("获取指定分类的所有属性(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取指定分类的所有属性(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 增加库存
	 * @param product_id	商品ID
	 * @param sku_info		sku信息,格式"id1:vid1;id2:vid2",如商品为统一规格，则此处赋值为空字符串即可
	 * @param quantity		增加的库存数量
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String addStock(String product_id,String sku_info,int quantity) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/stock/add?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("product_id", product_id);
		jsonObj.put("sku_info", sku_info);
		jsonObj.put("quantity", quantity);
		log.info("增加库存(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("增加库存(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "增加库存失败！";
			}
		}
	}
	
	/**
	 * 减少库存
	 * @param product_id	商品ID
	 * @param sku_info		sku信息,格式"id1:vid1;id2:vid2",如商品为统一规格，则此处赋值为空字符串即可
	 * @param quantity		增加的库存数量
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String reduceStock(String product_id,String sku_info,int quantity) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/stock/reduce?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("product_id", product_id);
		jsonObj.put("sku_info", sku_info);
		jsonObj.put("quantity", quantity);
		log.info("减少库存(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("减少库存(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "减少库存失败！";
			}
		}
	}
	
	/**
	 * 增加邮费模板
	 * @param obj	邮费模板
	 * @return
	 * @throws JSONException 
	 */
	public static Object addExpressTpl(JSONObject obj) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/express/add?access_token=" + token;
		log.info("增加邮费模板(POST)：" + url+"，参数：" + obj);
		String ret = HttpUtils.doPostSSL(url, obj);
		log.info("增加邮费模板(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	/**
	 * 删除邮费模板
	 * @param template_id
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String deleteExpressTpl(String template_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/express/del?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("template_id", template_id);
		log.info("删除邮费模板(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除邮费模板(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "删除邮费模板失败！";
			}
		}
	}
	
	/**
	 * 修改邮费模板
	 * @param obj	邮费模板
	 * @return
	 * @throws JSONException 
	 */
	public static Object updateExpressTpl(JSONObject obj) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/express/update?access_token=" + token;
		log.info("修改邮费模板(POST)：" + url+"，参数：" + obj);
		String ret = HttpUtils.doPostSSL(url, obj);
		log.info("修改邮费模板(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 获取指定ID的邮费模板
	 * @param template_id	邮费模板ID
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getExpressTpl(String template_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/express/getbyid?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("template_id", template_id);
		log.info("获取指定ID的邮费模板(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取指定ID的邮费模板(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 获取所有邮费模板
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getExpressTpl() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/express/getall?access_token=" + token;
		log.info("获取所有邮费模板(GET)：" + url);
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取所有邮费模板(GET)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 增加分组
	 * @param groupName		分组名称
	 * @param productList	商品ID集合
	 * @return {"errcode":0,"errmsg":"success","group_id": 19}
	 * @throws JSONException 

	 */
	public static JSONObject addGroup(String groupName,List<String> productList) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/group/add?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		JSONObject group_detail = new JSONObject();
		group_detail.put("group_name", groupName);
		JSONArray array = new JSONArray();
		for(String str:productList){
			array.put(str);
		}
		jsonObj.put("group_detail", group_detail);
		jsonObj.put("product_list", array);
		log.info("增加分组(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("增加分组(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 删除分组
	 * @param template_id
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String deleteGroup(int group_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/group/del?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("template_id", group_id);
		log.info("删除分组(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除分组(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "删除分组失败！";
			}
		}
	}
	
	/**
	 * 修改分组属性
	 * @param groupName		分组名称
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String updateGroup(int groupId,String groupName) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/group/propertymod?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("group_id", groupId);
		jsonObj.put("group_name", groupName);
		log.info("修改分组属性(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("修改分组属性(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "修改分组属性失败！";
			}
		}
	}
	
	/**
	 * 修改分组商品
	 * @param groupId		分组ID
	 * @param addProductList 新增商品列表
	 * @param delProductList 删除商品列表
	 * @return
	 * @throws JSONException 
	 */
	public static String updateGroup(int groupId,List<String> addProductList,List<String> delProductList) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/group/productmod?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("group_id", groupId);
		if(addProductList!=null && addProductList.size()>0){
			JSONArray addList = new JSONArray();
			for(String str:addProductList){
				JSONObject item = new JSONObject();
				item.put("product_id", str);
				item.put("mod_action", 1);
				addList.put(item);
			}
			
		}
		if(delProductList != null && delProductList.size()>0){
			JSONArray delList = new JSONArray();
			for(String str:delProductList){
				JSONObject item = new JSONObject();
				item.put("product_id", str);
				item.put("mod_action", 0);
				delList.put(item);
			}
		}
		log.info("修改分组商品(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("修改分组商品(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "修改分组商品失败！";
			}
		}
	}
	
	/**
	 * 获取所有分组
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getGroup() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/group/getall?access_token=" + token;
		log.info("获取所有分组(GET)：" + url);
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取所有分组(GET)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 根据分组ID获取分组信息
	 * @param groupId	分组ID
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getGroup(int groupId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/group/getbyid?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("group_id", groupId);
		
		log.info("根据分组ID获取分组信息(POST)：" + url + "，参数：" + jsonObj );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("根据分组ID获取分组信息(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 增加货架
	 * @param obj
	 * @return
	 * @throws JSONException 
	 */
	public static Object addShelf(JSONObject obj) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/shelf/add?access_token=" + token;
		log.info("增加货架(POST)：" + url+"，参数：" + obj);
		String ret = HttpUtils.doPostSSL(url, obj);
		log.info("增加货架(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 删除货架
	 * @param shelf_id	货架ID
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String deleteShelf(int shelf_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/shelf/del?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("shelf_id", shelf_id);
		log.info("删除货架(POST)：" + url+"，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除货架(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "删除货架失败！";
			}
		}
	}
	
	/**
	 * 修改货架
	 * @param obj
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String updateShelf(JSONObject obj) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/shelf/mod?access_token=" + token;
		log.info("修改货架(POST)：" + url+"，参数：" + obj);
		String ret = HttpUtils.doPostSSL(url, obj);
		log.info("修改货架(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "修改货架失败！";
			}
		}
	}
	
	/**
	 * 获取所有货架
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getShelf() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/shelf/getall?access_token=" + token;
		log.info("获取所有货架(GET)：" + url);
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取所有货架(GET)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 根据货架ID获取货架信息
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getShelf(int shelf_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/shelf/getbyid?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("shelf_id", shelf_id);
		log.info("根据货架ID获取货架信息(POST)：" + url + "，参数：" + jsonObj );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("根据货架ID获取货架信息(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 根据订单ID获取订单详情
	 * @param order_id	订单ID
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getOrder(int order_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/order/getbyid?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("order_id", order_id);
		log.info("根据订单ID获取订单详情(POST)：" + url + "，参数：" + jsonObj );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("根据订单ID获取订单详情(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 根据订单状态/创建时间获取订单详情
	 * @param status	订单状态(不带该字段-全部状态, 2-待发货, 3-已发货, 5-已完成, 8-维权中, )
	 * @param beginTime	订单创建时间起始时间(不带该字段则不按照时间做筛选)
	 * @param endTime	订单创建时间终止时间(不带该字段则不按照时间做筛选)
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getOrder(int status,String beginTime,String endTime) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/order/getbyfilter?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", status);
		jsonObj.put("begintime", beginTime);
		jsonObj.put("endtime", endTime);
		log.info("根据订单状态/创建时间获取订单详情(POST)：" + url + "，参数：" + jsonObj );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("根据订单状态/创建时间获取订单详情(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 设置订单发货信息
	 * @param order_id			订单ID
	 * @param delivery_company	物流公司ID(参考《物流公司ID》；当need_delivery为0时，可不填本字段；当need_delivery为1时，该字段不能为空；当need_delivery为1且is_others为1时，本字段填写其它物流公司名称)
	 * @param delivery_track_no	运单ID(当need_delivery为0时，可不填本字段；当need_delivery为1时，该字段不能为空；)
	 * @param need_delivery		商品是否需要物流(0-不需要，1-需要，无该字段默认为需要物流)
	 * @param is_others			是否为表之外的其它物流公司(0-否，1-是，无该字段默认为不是其它物流公司)
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject setOrderDelivery(int order_id,String delivery_company,String delivery_track_no,int need_delivery,String is_others) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/order/setdelivery?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("order_id", order_id);
		jsonObj.put("delivery_company", delivery_company);
		jsonObj.put("delivery_track_no", delivery_track_no);
		jsonObj.put("need_delivery", need_delivery);
		jsonObj.put("is_others", is_others);
		log.info("设置订单发货信息(POST)：" + url + "，参数：" + jsonObj );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("设置订单发货信息(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 关闭订单
	 * @param order_id	订单ID
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject closeOrder(int order_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/order/close?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("order_id", order_id);
		log.info("关闭订单(POST)：" + url + "，参数：" + jsonObj );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("关闭订单(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 上传图片
	 * @param file
	 * @return {"errcode":0,"errmsg":"success", "image_url": "http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2ibl4JWwwnW3icSJGqecVtRiaPxwWEIr99eYYL6AAAp1YBo12CpQTXFH6InyQWXITLvU4CU7kic4PcoXA/0"}
	 * @throws JSONException 
	 */
	public static JSONObject uploadImg(File file) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/merchant/common/upload_img?access_token=" + token;
		url += "&filename=" + file.getName();
		log.info("上传图片(POST)：" + url + "，文件名称：" + file.getName() );
		String ret = HttpUtils.uploadFileSSL(url, file, "", new HashMap<String,String>());
		log.info("上传图片(POST)返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
}
