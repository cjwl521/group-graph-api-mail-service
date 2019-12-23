package com.graph.mail.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Http request service
 * @author jarries
 *
 */
@Component
@Slf4j
public class HttpRequestUtil {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 执行HTTP Get Request请求
	 * 
	 * @param url
	 *            request的url
	 * @param clazz
	 *            需要响应的类
	 * @param <T>
	 *            泛型类
	 * @return T 返回类型
	 */
	public <T> T httpGetHealthRequest(String url, Class<T> clazz) {
		T obj = null;
		long startHttpTime = System.currentTimeMillis();
		log.info("Start send http get health request, url is:{}", url);

		try {
			obj = this.restTemplate.getForEntity(url, clazz).getBody();
		} catch (HttpServerErrorException e) {
			try {
				obj = new ObjectMapper().readValue(e.getResponseBodyAsString(), clazz);
			} catch (Exception e1) {
				log.info(e.getMessage(), e1);
			}
		} catch (ResourceAccessException e2) {
			throw e2;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		log.info("httpGetRequest spend time is: {}ms",
				String.valueOf(System.currentTimeMillis() - startHttpTime));
		return obj;
	}
}
