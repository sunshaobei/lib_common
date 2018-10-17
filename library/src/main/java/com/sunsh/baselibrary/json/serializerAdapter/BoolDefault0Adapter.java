package com.sunsh.baselibrary.json.serializerAdapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by sunsh on 2017/6/7.
 */

public class BoolDefault0Adapter implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsInt() == 0 || json.getAsString().equals("0")) {
                return false;
            } else if(json.getAsInt() == 1 || json.getAsString().equals("1")){
                return true;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsBoolean();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
