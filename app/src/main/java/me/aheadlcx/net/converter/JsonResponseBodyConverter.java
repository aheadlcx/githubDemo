package me.aheadlcx.net.converter;

import android.util.Log;

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
    private static final String TAG = "JsonResponseBodyConvert";

    private T t;
    Gson mGson;
    private final TypeAdapter<T> adapter;

    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
        mGson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = mGson.newJsonReader(value.charStream());
        T read = null;
        try {
            read = adapter.read(jsonReader);
            return read;
//            return ((T)new ApiResult.BizSuccess(0, "success", adapter.read(jsonReader)));
        } catch (Exception e){
            Log.i(TAG, "convert: ");
            return null;
        }
        finally {
            value.close();
        }
    }
}
