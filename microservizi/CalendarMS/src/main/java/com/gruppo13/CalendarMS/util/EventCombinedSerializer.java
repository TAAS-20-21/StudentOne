package com.gruppo13.CalendarMS.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class EventCombinedSerializer {
    //CLASSE PER PERMETTERE LA LETTURA DI UN EVENTO TRAMITE JSON
    /*public static class UserJsonSerializer
            extends JsonSerializer<Event> {

        @Override
        public void serialize(Event event, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException,
                JsonProcessingException {

            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField(
                    "start", event.getStart());
            jsonGenerator.writeObjectField(
                    "end", event.getEnd());
            jsonGenerator.writeStringField(
                    "summary", event.getSummary());
            jsonGenerator.writeEndObject();
        }
    }

    public static class UserJsonDeserializer
            extends JsonDeserializer<Event> {

        @Override
        public Event deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext)
                throws IOException, JsonProcessingException {

            TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
            TextNode summary = (TextNode) treeNode.get(
                    "summary");
            Object start = new EventDateTime().setDateTime((DateTime) treeNode.get("start"));
            return new Event().setSummary(summary.toString()).setStart();
        }
    }*/
}
