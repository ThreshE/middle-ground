package com.cloud.notification.castlers.message;

import com.alibaba.fastjson.JSONObject;
import com.cloud.notification.tuna.client.api.MessageProcessException;
import com.cloud.notification.tuna.client.httpcb.HttpCbMessage;
import com.cloud.notification.tuna.client.httpcb.HttpCbMessageHandler;
import com.cloud.notification.tuna.client.httpcb.TunaHttpCbClient;
import com.cloud.notification.castlers.message.entity.ErrorMessage;
import com.cloud.notification.castlers.message.entity.ExceptionMessage;
import com.cloud.notification.castlers.message.entity.SuccessMessage;
import com.cloud.notification.castlers.message.entity.YesStyleRemote;
import com.cloud.notification.castlers.message.mapper.ErrorMessageMapper;
import com.cloud.notification.castlers.message.mapper.ExceptionMessageMapper;
import com.cloud.notification.castlers.message.mapper.SuccessMessageMapper;
import com.cloud.notification.castlers.message.mapper.YesStyleRemoteMapper;
import com.cloud.notification.utils.HttpClientRemoteUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @description: YesStyle 消息推送处理
 * @author: 廖权名
 * @date: 2020/9/9 15:01
 */
@Component
public class MessageClientHandle {

    @Resource
    SuccessMessageMapper successMessageMapper;

    @Resource
    ExceptionMessageMapper exceptionMessageMapper;

    @Resource
    ErrorMessageMapper errorMessageMapper;

    @Resource
    YesStyleRemoteMapper yesStyleRemoteMapper;

    private final String URL = "https://t7le55wjdc.execute-api.ap-east-1.amazonaws.com/1688-api-callback";
    private final String api_key = "3ac19edc-1e70-45ce-86dd-63aa80db4de5";



    public void onMessage() {

        /*
         * 1. 创建 Client。参数说明：
         *  > port
         *  > 是否启用 SSL
         */
        TunaHttpCbClient client = new TunaHttpCbClient(18080, false);

        // 2. 创建 消息处理 Handler
        HttpCbMessageHandler messageHandler = new HttpCbMessageHandler<HttpCbMessage, Void>() {

            /**
             * 应用密钥
             */
            public String getSignKey() {
                return "cvXaLI11zWx5";
            }

            /**
             * 为了防止消息篡改，开放平台推送的数据包含签名信息。
             * 字段名为 _aop_signature，假设值为 serverSign。
             *
             * 接收到消息后，SDK 首先会使用秘钥对接收到的内容进行签名，
             * 假设值为 clientSign。
             *
             * 1. 若 serverSign 与 clientSign 相同，则直接调用 {@link #(Object)} 方法。
             * 2. 若 serverSign 与 clientSign 不同，则调用该方法。若该方法返回 true，则继续
             * 	调用 {@link #(Object)} 方法；否则直接返回状态码 401。
             */
            public boolean continueOnSignatureValidationFailed(String clientSign, String serverSign) {
                //1.验签失败入库
                ErrorMessage message = new ErrorMessage();
                message.setMessage(String.format("验签失败:s:%s-> %s != %s",getSignKey(),clientSign,serverSign));
                errorMessageMapper.insert(message);
                return false;
            }

            /**
             * 消费消息。
             * @throws MessageProcessException 消息消费不成功，如未达重试次数上限，开放平台将会择机重发消息
             */
            public Void onMessage(HttpCbMessage message) throws MessageProcessException {
                System.out.println("message: " + message);
                // 真正的业务逻辑在这。如果消费失败或异常，请抛出 MessageProcessException 异常
                boolean ret = false;
                try {

                    //1.数据入库
                    SuccessMessage successMessage = new SuccessMessage();
                    successMessage.setMessage(message.toString());
                    successMessage.setBizKey(message.getBizKey());
                    successMessage.setGmtBorn(String.valueOf(message.getGmtBorn()));
                    successMessage.setMsgId(message.getMsgId());
                    successMessage.setType(message.getType());
                    successMessage.setUserInfo(message.getUserInfo());
                    successMessageMapper.insert(successMessage);

                    //2.推送，走原来异常处理描述
                    ret = true;
                    Map<String, String> header = new HashMap<String, String>();
                    header.put("Content-Type", "application/x-www-form-urlencoded");

                    String jsonString = JSONObject.toJSONString(message);//将java对象转换为json对象
                    Map<String, String> form = new HashMap<String, String>();
                    form.put("'message",jsonString);
                    form.put("api-key",api_key);
                    YesStyleRemote yesStyleRemote = HttpClientRemoteUtil.SendRemotePost(header, form, URL);
                    yesStyleRemoteMapper.insert(yesStyleRemote);

                } catch (Exception e) {
                    ExceptionMessage exceptionMessage = new ExceptionMessage();
                    exceptionMessage.setMessage(e.getMessage().length() > 5000 ? e.getMessage().substring(0, 4999) : e.getMessage());
                    if (!ret) {
                        exceptionMessage.setTypeDescription("数据入库异常");
                        exceptionMessage.setType(0);
                    } else {
                        exceptionMessage.setTypeDescription("推送错误，没有得到响应");
                        exceptionMessage.setType(1);
                    }
                    exceptionMessageMapper.insert(exceptionMessage);
                    throw new MessageProcessException(e.getMessage());
                }
                return null;
            }
        };
        // 注册 Handler
        client.registerMessageHandler("/callback", messageHandler);

        // 3. 启动 Client
        client.start();

        // 4. 在应用关闭时，关闭客户端
        // client.shutdown();
    }
}
