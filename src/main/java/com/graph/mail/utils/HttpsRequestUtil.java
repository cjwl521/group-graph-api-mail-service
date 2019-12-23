package com.graph.mail.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
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
public class HttpsRequestUtil {

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
	public <T> T httpsGetRequest(String url, String token,Class<T> clazz) {
		T obj = null;
		long startHttpTime = System.currentTimeMillis();
		log.debug("Start send http get health request, url is:{}", url);

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(token);
			  HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			  ResponseEntity<T> result = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
			  obj = result.getBody();
			  log.info("httpGetRequest is: {}", obj);
		} catch (HttpServerErrorException e) {
			try {
				obj = new ObjectMapper().readValue(e.getResponseBodyAsString(), clazz);
			} catch (Exception e1) {
				log.error(e.getMessage(), e1);
			}
		} catch (ResourceAccessException e2) {
			throw e2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("httpGetRequest spend time is: {}ms", String.valueOf(System.currentTimeMillis() - startHttpTime));
		return obj;
	}
	
	/**
	 * 执行HTTP Post Request请求
	 * 
	 * @param url
	 *            request的url
	 * @param clazz
	 *            需要响应的类
	 * @param <T>
	 *            泛型类
	 * @return T 返回类型
	 */
	public <T> T httpsPostRequest(String url, MultiValueMap<String, String> body, Class<T> clazz) {
		T obj = null;
		long startHttpTime = System.currentTimeMillis();
		log.info("Start send http get health request, url is:{}", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);

			obj = restTemplate.postForObject(url, httpEntity, clazz);
		} catch (HttpServerErrorException e) {
			try {
				obj = new ObjectMapper().readValue(e.getResponseBodyAsString(), clazz);
			} catch (Exception e1) {
				log.error(e.getMessage(), e1);
			}
		} catch (ResourceAccessException e2) {
			throw e2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("httpsPostRequest spend time is: {}ms", String.valueOf(System.currentTimeMillis() - startHttpTime));
		return obj;
	}
	
}
