package com.ejemplo.bookmatch;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HuggingFaceService {
    @Headers({
            "Authorization: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", // Reemplaza con tu clave API
            "Content-Type: application/json"
    })
    @POST("models/gpt2") // Cambia el modelo si necesitas uno específico
    Call<HuggingFaceResponse> generateText(@Body HuggingFaceRequest request);
}

