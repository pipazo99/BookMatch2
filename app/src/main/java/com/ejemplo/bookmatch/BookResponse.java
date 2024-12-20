package com.ejemplo.bookmatch;

import java.util.List;

public class BookResponse {
    private List<Book> docs;

    public List<Book> getDocs() {
        return docs;
    }

    public void setDocs(List<Book> docs) {
        this.docs = docs;
    }
}

