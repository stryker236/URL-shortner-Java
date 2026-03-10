package com.urlshortener;

import com.urlshortener.api.UrlController;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        port(4567);

        UrlController.register();

        System.out.println("Server running on port 4567");
    }
}