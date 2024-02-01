package dev.harsh.architect.user_service.security.mixin;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


class UnmodifiableSetDeserializer extends JsonDeserializer<Set> {
    @Override
    public Set deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);
        Set<Object> resultSet = new HashSet<Object>();
        if (node != null) {
            if (node instanceof ArrayNode arrayNode) {
                for (JsonNode elementNode : arrayNode) {
                    resultSet.add(mapper.readValue(elementNode.traverse(mapper), Object.class));
                }
            } else {
                resultSet.add(mapper.readValue(node.traverse(mapper), Object.class));
            }
        }
        return Collections.unmodifiableSet(resultSet);
    }


}