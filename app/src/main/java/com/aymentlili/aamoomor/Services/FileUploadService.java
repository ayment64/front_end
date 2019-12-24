/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  okhttp3.MultipartBody
 *  okhttp3.MultipartBody$Part
 *  okhttp3.RequestBody
 *  okhttp3.ResponseBody
 *  retrofit2.Call
 *  retrofit2.http.DELETE
 *  retrofit2.http.Multipart
 *  retrofit2.http.POST
 *  retrofit2.http.PUT
 *  retrofit2.http.Part
 *  retrofit2.http.Path
 */
package com.aymentlili.aamoomor.Services;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FileUploadService {
    @DELETE(value="Estates/{name}")
    public Call<ResponseBody> deleteEstate(@Path(value = "name") String var1);

    @Multipart
    @PUT(value="upload")
    public Call<ResponseBody> upload(@Part(value = "Username") RequestBody var1, @Part MultipartBody.Part var2);

    @Multipart
    @POST(value="upload")
    public Call<ResponseBody> uploadd(@Part(value = "house_name") RequestBody var1, @Part MultipartBody.Part var2);

    @Multipart
    @PUT(value="uupload")
    public Call<ResponseBody> uupload(@Part(value = "name") RequestBody var1, @Part MultipartBody.Part var2);
}

