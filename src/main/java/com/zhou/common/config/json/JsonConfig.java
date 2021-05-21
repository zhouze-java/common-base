package com.zhou.common.config.json;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.zhou.common.config.constants.Constants.DATE_FORMATTER;
import static com.zhou.common.config.constants.Constants.DATE_TIME_FORMATTER;

/**
 * @author 周泽
 * @date Create in 14:33 2021/3/16
 * @Description
 */
@Configuration(proxyBeanMethods = false)
public class JsonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule simpleModule = new SimpleModule("simpleModule")
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER))
                .addSerializer(IPage.class, new PageSerializer())
                .addSerializer(DescribableEnum.class, new DescribableEnumSerializer());

        return new ObjectMapper()
                .findAndRegisterModules()
                .registerModules(simpleModule)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    @SuppressWarnings("rawtypes")
    private static class PageSerializer extends JsonObjectSerializer<IPage> {

        @Override
        protected void serializeObject(IPage page, JsonGenerator writer, SerializerProvider provider) throws IOException {
            writer.writeNumberField("current", page.getCurrent());
            writer.writeNumberField("size", page.getSize());
            writer.writeNumberField("total", page.getTotal());
            writer.writeNumberField("pages", page.getPages());
            writer.writeObjectField("records", page.getRecords());
        }

    }

    private static class DescribableEnumSerializer extends JsonObjectSerializer<DescribableEnum> {

        @Override
        protected void serializeObject(DescribableEnum value, JsonGenerator writer, SerializerProvider provider) throws IOException {
            writer.writeNumberField("value", ((Enum<?>) value).ordinal());
            writer.writeStringField("name", value.getDescription());
        }

    }

}
