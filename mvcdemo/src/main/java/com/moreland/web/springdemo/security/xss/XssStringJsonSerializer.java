package com.moreland.web.springdemo.security.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.unbescape.json.JsonEscape;

import java.io.IOException;

public class XssStringJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            return;
        }
        var encoded = JsonEscape.escapeJson(value);
        gen.writeString(encoded);
    }

    @Override
    public Class<String> handledType() {
        return String.class;
    }
}
