package testUtils;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class ResorcesLoader {

    public  static <T> T loadResources(Object object, String file, Type type) throws Exception {
        InputStream inputStream = object.getClass().getResourceAsStream(file);
        return new Gson().fromJson(new InputStreamReader(inputStream), type);
    }
}
