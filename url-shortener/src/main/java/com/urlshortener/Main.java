package com.urlshortener;

import com.urlshortener.api.UrlController;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(4567);
        try {
            UrlController.register();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Server running on port 4567");
    }
}