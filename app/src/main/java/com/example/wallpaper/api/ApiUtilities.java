package com.example.wallpaper.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    public static final String Base_URL ="https://api.unsplash.com/";
    public static final String API_KEY ="15x2sE77KHwm3Z779ELolVbDUB85XY7MplcJCLloOes";

    public static Retrofit retrofit = null;

    public static ApiInterface getApiInterface(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


}
