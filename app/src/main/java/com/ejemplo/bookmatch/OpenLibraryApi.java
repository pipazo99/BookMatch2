package com.ejemplo.bookmatch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenLibraryApi {
    @GET("search.json")
    Call<BookResponse> searchBooks(@Query("title") String title);
}

