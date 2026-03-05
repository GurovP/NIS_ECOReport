package eco.nis.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullableModule;

public final class ObjectMapperUtility {

    private static final ThreadLocal<ObjectMapper> om = ThreadLocal.withInitial(() -> {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        objectMapper.getSerializerProvider().setNullKeySerializer(new NullKeyMapSerializer());
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    });

    public static ObjectMapper getObjectMapper() {

        return om.get();
    }

    public static ObjectMapper getObjectMapperWithIgnoreUnknownProperties() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        objectMapper.getSerializerProvider().setNullKeySerializer(new NullKeyMapSerializer());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    }

    public static ObjectMapper getObjectMapperNonEscape() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        objectMapper.getSerializerProvider().setNullKeySerializer(new NullKeyMapSerializer());
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    }

    public static ObjectMapper getObjectMapperWithTyping() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        mapper.getSerializerProvider().setNullKeySerializer(new NullKeyMapSerializer());
        mapper.registerModule(new JsonNullableModule());
        return mapper;
    }
}
