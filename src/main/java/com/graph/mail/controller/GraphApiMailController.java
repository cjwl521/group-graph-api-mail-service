package com.graph.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.graph.mail.common.BaseResponse;
import com.graph.mail.service.impl.GraphApiMailImpl;

@RestController
@RequestMapping(value = "/api/v1")
public class GraphApiMailController {
	
	
	@Autowired
	private GraphApiMailImpl graphApiMail;

	
	@GetMapping(value = "/mail/info")
	@ResponseBody
	public BaseResponse getMail() throws Exception {

		return BaseResponse.getInstence().createBySuccessMessage(graphApiMail.getMail());
	}

	@GetMapping(value = "/mail/attachments")
	@ResponseBody
	public BaseResponse getMailInfo() throws Exception {

		return BaseResponse.getInstence().createBySuccessMessage(graphApiMail.getEmailInfo());
	}

}
