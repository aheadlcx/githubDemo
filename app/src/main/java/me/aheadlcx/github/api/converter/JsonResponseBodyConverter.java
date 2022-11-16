package me.aheadlcx.github.api.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/15 3:35 下午
 */
class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    Gson mGson;
    private final TypeAdapter<T> adapter;

    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
        mGson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = mGson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }


    }
}
