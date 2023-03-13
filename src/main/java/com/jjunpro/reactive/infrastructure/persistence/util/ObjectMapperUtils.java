package com.jjunpro.reactive.infrastructure.persistence.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

/**
 * 일반적으로 ObjectMapperUtils utils 파일은 infrastructure 레이어에 위치시키는 것이 적합합니다. 이유는 다음과 같습니다.
 * ObjectMapperUtils utils 파일은 데이터 변환에 관련된 로직을 처리하므로, 데이터 저장소와 직접적인 관련이 있습니다.
 * 따라서 persistence 패키지 내부에 위치시키는 것이 자연스러울 수 있습니다.
 * ObjectMapperUtils utils 파일은 domain 레이어와 관련이 없고, web 레이어에서 직접적으로 사용하는 파일도 아닙니다.
 * 따라서 application 레이어나 web 레이어에 위치시키는 것은 의미가 없습니다.
 * infrastructure 레이어는 persistence 패키지 이외에도, 외부와의 통신, 즉 API 호출 등을 처리하는 데 사용될 수 있습니다.
 * 이에 따라 ObjectMapperUtils utils 파일도 infrastructure 레이어 내부에서 공유되어 사용될 수 있기 때문에, 이 레이어에 위치시키는 것이 적절합니다.
 * 따라서, ObjectMapperUtils utils 파일은 infrastructure 패키지 내부에 위치시키는 것이 좋습니다.
 */
@UtilityClass
public class ObjectMapperUtils {

    private final ObjectMapper mapper = createObjectMapper();

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public <T> T objectMapper(Object obj, Class<T> contentClass){
        return mapper.convertValue(obj, contentClass);
    }
}