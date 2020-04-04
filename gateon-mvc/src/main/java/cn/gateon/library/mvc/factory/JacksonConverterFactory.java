package cn.gateon.library.mvc.factory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2.5
 */
public class JacksonConverterFactory {

    private ObjectMapper mapper = new ObjectMapper();


    public JacksonConverterFactory() {
        //字段不存在时是否报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //枚举解析失败时为null
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        //时区
        mapper.setTimeZone(TimeZone.getDefault());
        //线程安全问题，不建议使用单例对象
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.enable(MapperFeature.USE_ANNOTATIONS);
    }

    /**
     * 是否使注解生效 默认:true
     *
     * @param flag true | false
     * @return self
     */
    private JacksonConverterFactory useAnnotation(boolean flag) {
        mapper.configure(MapperFeature.USE_ANNOTATIONS, flag);
        return this;
    }

    public MappingJackson2HttpMessageConverter build() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(mapper);
        return mappingJackson2HttpMessageConverter;
    }

}
