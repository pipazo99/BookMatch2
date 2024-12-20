package com.ejemplo.bookmatch;

import java.util.List;

public class HuggingFaceResponse {
    private List<String> generated_text;

    public List<String> getGeneratedText() {
        return generated_text;
    }

    public void setGeneratedText(List<String> generated_text) {
        this.generated_text = generated_text;
    }
}

