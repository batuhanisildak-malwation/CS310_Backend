package com.fitplanner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitplanner.payload.Message;
import com.fitplanner.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/gpt")
public class FitPlannerGpt {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/send-to-gpt")
    public Message sendToGpt(@RequestParam String prompt) throws IOException {
        URL url = new URL(
                "https://malwation-test-openai.openai.azure.com/openai/deployments/malwation-model/chat/completions?api-version=2023-03-15-preview");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("api-key", "72979ba4511541abaa460acee9cfeb7f");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        String jsonInputString = String.format(
                """
                            {"messages":[{"role": "system", "content": "You are an AI assistant that helps people find information."}, {"role": "user", "content": "%s"}]}
                        """,
                prompt);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JSONObject data_obj = new JSONObject(response.toString());
            JSONArray data_arr = data_obj.getJSONArray("choices");
            JSONObject data_obj2 = data_arr.getJSONObject(0);
            JSONObject data_obj3 = data_obj2.getJSONObject("message");
            String x = data_obj3.getString("content");

            Message message = new Message(x, 200);
            return message;
        }
    }
}
