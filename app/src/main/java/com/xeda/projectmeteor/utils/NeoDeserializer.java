package com.xeda.projectmeteor.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.xeda.projectmeteor.models.Links;
import com.xeda.projectmeteor.models.MeteorObjects;
import com.xeda.projectmeteor.models.NEO;
import com.xeda.projectmeteor.models.NearEarthObjects;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Xeda on 21-09-2017.
 */

public class NeoDeserializer implements JsonDeserializer<NEO> {
    @Override
    public NEO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        NEO n = new NEO();
        final JsonObject jsonObject = json.getAsJsonObject();

        Integer elementCount = jsonObject.get("element_count").getAsInt();

        JsonObject linkObject = jsonObject.getAsJsonObject("links");
        Links links = new Links();
        links.setNext(linkObject.get("next").getAsString());
        links.setNext(linkObject.get("prev").getAsString());
        links.setNext(linkObject.get("self").getAsString());

        JsonObject nearEarthObjects = jsonObject.getAsJsonObject("near_earth_objects");

        List<NearEarthObjects> list = new ArrayList<NearEarthObjects>();

        for (Map.Entry<String, JsonElement> entry : nearEarthObjects.entrySet()){
            NearEarthObjects nO = new NearEarthObjects();
            String key = entry.getKey();
            JsonElement nearEarthObjectsJson = entry.getValue();
            List<MeteorObjects> mList = new ArrayList<MeteorObjects>();
            mList = new Gson().fromJson(nearEarthObjectsJson, new TypeToken<List<MeteorObjects>>(){}.getType());
            nO.setDate(key);
            nO.setMeteorObjects(mList);
            list.add(nO);
        }

        n.setElementCount(elementCount);
        n.setLinks(links);
        n.setNearEarthObjects(list);
        return n;
    }
}
