package com.graph.mail.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * response 基础类
 *
 */
public class BaseResponse implements Serializable {
	private static final long serialVersionUID = 1403218278293175897L;
	private String code = "200";
	
	public static String UNKNOWERRORCODE = "500";
	
	private String messages;
	private Object result;

	public static BaseResponse baseResponse = null;

	public static synchronized BaseResponse getInstence() {
		if (baseResponse == null) {
			baseResponse = new BaseResponse();
		}
		return baseResponse;
	}
	
    public BaseResponse() {
	}

	private BaseResponse(String code) {
		this.code = code;
	}

	private BaseResponse(String code, Object responseBody) {
		this.code = code;
		this.result = responseBody;
	}

	private BaseResponse(String status, String msg) {
		this.code = status;
		this.messages = msg;
	}

	private BaseResponse(String status, String msg, Object responseBody) {
		this.code = status;
		this.result = responseBody;
		this.messages = msg;
	}

	public BaseResponse createBySuccess() {
		return new BaseResponse(ResponseCode.SUCCESS.getCode());
	}

	public BaseResponse createBySuccessMessage(String msg) {
		return new BaseResponse(ResponseCode.SUCCESS.getCode(), msg);
	}

	public BaseResponse createBySuccessMessage(Object data) {
		return new BaseResponse(ResponseCode.SUCCESS.getCode(), data);
	}

	public BaseResponse createBySuccessMessage(List<Object> list) {
		return new BaseResponse(ResponseCode.SUCCESS.getCode(), list);
	}

	public BaseResponse createBySuccess(String msg, Object data) {
		return new BaseResponse(ResponseCode.SUCCESS.getCode(), msg, data);
	}

	public BaseResponse createByError() {
		return new BaseResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
	}

	public BaseResponse createByErrorMessage(String errorMessage) {
		return new BaseResponse(ResponseCode.ERROR.getCode(), errorMessage);
	}

	public BaseResponse createByErrorMessage(String errorCode, String errorMessage) {
		return new BaseResponse(errorCode, errorMessage);
	}
	
	public BaseResponse createByErrorMessage(String exception,String errorCode, String errorMessage) {
        return new BaseResponse(exception,errorCode, errorMessage);
    }

	@Override
	public String toString() {
		return "[code=" + code + ", data=" + result + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String status) {
		this.code = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(List<Object> responseBody) {
		this.result = responseBody;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String msg) {
		this.messages = msg;
	}
}
