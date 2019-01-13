package pl.lodz.p.interiordesignapp.service;

import java.util.List;

import pl.lodz.p.interiordesignapp.model.DesignObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface DataService {
    @Headers({"Accept: application/json"})
    @GET
    Call<DesignObject> getDesignObject(@Url String url);

    @Headers({"Accept: application/json"})
    @GET
    Call<List<DesignObject>> getSubDesignObject(@Url String url);
}
