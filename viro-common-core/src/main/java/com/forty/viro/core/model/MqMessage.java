package com.forty.viro.core.model;


import com.forty.viro.core.enums.ErrorCode;
import com.forty.viro.core.exception.BusinessException;
import com.forty.viro.core.utils.Preconditions;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.Serializable;

@Data
@Builder
public class MqMessage implements Serializable {

    private static final long serialVersionUID = 13359425334L;

    private String topic;
    private String tag = "*";
    private String key;
    private String body;
    public MqMessage(String topic, String body) {
        this.topic = topic;
        this.body = body;
    }
    public MqMessage(String topic, String key, String body) {
        this(topic, body);
        this.key = key;
    }
    public MqMessage(String topic, String key, String tag, String body) {
        this(topic, key, body);
        this.tag = tag;
    }


    public static void checkMessage(String topic, String tag, String body) {
        Preconditions.checkArgument(!StringUtils.isEmpty(topic), "发送消息失败, 消息主题不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(tag), "发送消息失败, 消息标签不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(body), "发送消息失败, 消息体不能为空");
    }

    public Message buildMessage() {
        Message message = new Message();
        MqMessage.checkMessage(this.topic, this.tag, this.body);
        message.setTopic(topic);
        message.setTags(tag);
        if (StringUtils.isEmpty(key)) message.setKeys(key);
        try {
            message.setBody(body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        return message;
    }

}
