package site.qiuyuan.library.amqp;

import site.qiuyuan.library.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.util.StringUtils;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Slf4j
public class GateonConverter extends AbstractMessageConverter {

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        String s = JsonUtil.toJson(object);
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentLength(s.length());
        messageProperties.setHeader("clazz", object.getClass().getName());
        return new Message(s.getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        MessageProperties properties = message.getMessageProperties();
        byte[] body = message.getBody();
        String clazzName = (String) properties.getHeaders().get("clazz");
        if (StringUtils.isEmpty(clazzName)) {
            return new String(body);
        }
        try {
            return JsonUtil.fromJson(new String(body), Class.forName(clazzName));
        } catch (ClassNotFoundException e) {
            log.error("json解析失败:", e);
            return body;
        }
    }
}
