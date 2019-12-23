package com.graph.mail.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface GraphApiMailService {

	List<Map<String, Object>> getMail () throws JsonGenerationException, JsonMappingException, IOException;

	List<List<Map<String, String>>> getEmailInfo() throws Exception;

	List<List<Map<String, String>>> getAttachments(List<Map<String, Object>> set) throws Exception;
}
