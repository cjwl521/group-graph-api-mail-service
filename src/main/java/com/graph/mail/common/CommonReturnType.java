package com.graph.mail.common;

public class CommonReturnType {
    //表明对应请求的返回结果处理 为 SUCCESS 或者 Failed
    private String status;

    //若status=SUCCESS，则data内返回前端需要的json格式
    //若status=Failed，则data内使用通用的错误码格式
    private Object object;

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result,String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setObject(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
