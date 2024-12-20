package com.ejemplo.bookmatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ejemplo.bookmatch.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private HuggingFaceService api;
    private BookAdapter bookAdapter;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-inference.huggingface.co/") // Base URL de Hugging Face
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(HuggingFaceService.class);

        // Configurar RecyclerView
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList);
        binding.recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewBooks.setAdapter(bookAdapter);

        // Configurar el botón
        binding.btnRecommend.setOnClickListener(v -> {
            String userInput = binding.etUserInput.getText().toString().trim();
            if (!userInput.isEmpty()) {
                getBookRecommendations(userInput);
            } else {
                Toast.makeText(this, "Por favor, escribe algo.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBookRecommendations(String userInput) {
        binding.progressBar.setVisibility(View.VISIBLE); // Mostrar el ProgressBar
        HuggingFaceRequest request = new HuggingFaceRequest(userInput);

        // Realizar la solicitud a la API
        Call<HuggingFaceResponse> call = api.generateText(request);
        call.enqueue(new Callback<HuggingFaceResponse>() {
            @Override
            public void onResponse(Call<HuggingFaceResponse> call, Response<HuggingFaceResponse> response) {
                binding.progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                if (response.isSuccessful() && response.body() != null) {
                    List<String> recommendations = response.body().getGeneratedText();
                    if (recommendations != null && !recommendations.isEmpty()) {
                        updateBookList(recommendations);
                    } else {
                        Toast.makeText(HomeActivity.this, "No se encontraron recomendaciones.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HuggingFaceResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                Toast.makeText(HomeActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBookList(List<String> recommendations) {
        bookList.clear();
        for (String recommendation : recommendations) {
            // Crear un libro con el título y dejar el autor vacío
            bookList.add(new Book(recommendation, "Autor desconocido"));
        }
        bookAdapter.notifyDataSetChanged(); // Actualizar la lista del adaptador
    }
}




