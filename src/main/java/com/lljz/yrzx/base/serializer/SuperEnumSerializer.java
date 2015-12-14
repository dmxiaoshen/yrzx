package com.lljz.yrzx.base.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.lljz.yrzx.base.enums.SuperEnum;

public class SuperEnumSerializer extends JsonSerializer<SuperEnum> {

    @Override
    public void serialize(SuperEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeObject(value.getMap());

    }

}
