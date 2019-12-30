package com.graph.mail.service.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graph.mail.common.Singleton;
import com.graph.mail.service.GraphApiMailService;
import com.graph.mail.utils.HttpsRequestUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GraphApiMailImpl implements GraphApiMailService {

	@Autowired
	private HttpsRequestUtil httpsRequestUtil;

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private ClientCredentialGrant clientCredentialGrant;

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMail() throws JsonGenerationException, JsonMappingException, IOException {
		String tokenPr = Singleton.getInstance().getMap().get("TOKEN");
		if (tokenPr == null) {
			try {
				clientCredentialGrant.getAccessTokenByClientCredentialGrant();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, String> map = Singleton.getInstance().getMap();
		String token = map.get("TOKEN");
		String url = "https://microsoftgraph.chinacloudapi.cn/v1.0/users/mnc.odc.ms.014@icsscn.cn/messages?$select=subject,bodyPreview,createdDateTime,sender,id,isRead";
		JSONObject object = httpsRequestUtil.httpsGetRequest(url, token, JSONObject.class);
		log.info(object.toJSONString());
		// JSONObject mapResult = objMapper.convertValue(object, JSONObject.class);
		List<Map<String, Object>> mapValues = (List<Map<String, Object>>) object.get("value");
		// Set<String> listString = new HashSet<String>();
		List<Map<String, Object>> mapValuesResult = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> valueBean : mapValues) {
			if ((boolean) valueBean.get("isRead") == false) {
				Map<String, Object> mapObj = new HashMap<>();
				String id = valueBean.get("id").toString();
				String createdDateTime = valueBean.get("createdDateTime").toString();
				String subject = valueBean.get("subject").toString();
				String bodyPreview = valueBean.get("bodyPreview").toString();
				String sender = valueBean.get("sender").toString();
				mapObj.put("subject", subject);
				mapObj.put("bodyPreview", bodyPreview);
				mapObj.put("sender", sender);
				mapObj.put("createdDateTime", createdDateTime);
				mapObj.put("id", id);
				mapValuesResult.add(mapObj);
			}
		}
		log.info(objMapper.writeValueAsString(mapValuesResult));
		return mapValuesResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<List<Map<String, String>>> getAttachments(List<Map<String, Object>> set) throws Exception {
		List<List<Map<String, String>>> listListMap = new ArrayList<>();
		Map<String, String> map = Singleton.getInstance().getMap();
		String token = map.get("TOKEN");
		for (Map<String, Object> mapMail : set) {
			String url = "https://microsoftgraph.chinacloudapi.cn/v1.0/users/mnc.odc.ms.014@icsscn.cn/messages/"
					+ mapMail.get("id") + "/attachments";
			JSONObject object = httpsRequestUtil.httpsGetRequest(url, token, JSONObject.class);
			log.info(object.toJSONString());
			List<Map<String, Object>> mapValues = (List<Map<String, Object>>) object.get("value");
			List<Map<String, String>> listMap = new ArrayList<>();
			for (Map<String, Object> valueBean : mapValues) {
				if (!(boolean) valueBean.get("isInline")) {
					Map<String, String> mapResult = new HashMap<>();
					String contentBytes = valueBean.get("contentBytes").toString();
					String name = valueBean.get("name").toString();
					String size = valueBean.get("size").toString();
					mapResult.put("name", name);
					mapResult.put("contentBytes", contentBytes);
					mapResult.put("size", size);
					listMap.add(mapResult);
					byte[] encodeBase64 = Base64.getDecoder().decode(contentBytes.getBytes("UTF-8"));
					writeText("C:\\Users\\jarries\\Desktop\\xpc\\"+name, encodeBase64);
				}
			}
			listListMap.add(listMap);
		}
		return listListMap;
	}

	@Override
	public List<List<Map<String, String>>> getEmailInfo() throws Exception {
		return getAttachments(getMail());
	}

	/* 写入Text文件操作 */
	public static void writeText(String filePath, byte[] content) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
			outputStream.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
