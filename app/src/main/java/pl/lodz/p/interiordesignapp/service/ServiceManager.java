package pl.lodz.p.interiordesignapp.service;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.lodz.p.interiordesignapp.model.DesignObject;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceManager {
    private static DataService dataService;
    private static ServiceManager serviceManager;
    public final static String HOST_NAME = "https://interior-design-api.o-and-m.ovh/";

    private ServiceManager() {

        /*OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                return chain.proceed(requestBuilder.build());
            }
        });*/


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(httpClient)
                .build();

        dataService = retrofit.create(DataService.class);
    }

    public static ServiceManager getInstance() {
        if(serviceManager == null) {
            serviceManager = new ServiceManager();
        }

        return serviceManager;
    }

    public void getDesignObject(String url, Callback<DesignObject> callback) {
        dataService.getDesignObject(url).enqueue(callback);
    }

    public void getSubDesignObject(String url, Callback<List<DesignObject>> callback) {
        dataService.getSubDesignObject(url).enqueue(callback);
    }
}
