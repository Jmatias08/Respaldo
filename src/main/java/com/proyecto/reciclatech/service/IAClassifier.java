package com.proyecto.reciclatech.service;

import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IAClassifier {

    private static final String GROQ_API_KEY = "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"; // 🔑 Reemplaza con tu key

    public static String clasificar(String nombreResiduo) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + GROQ_API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON del request
            JSONObject body = new JSONObject();
            body.put("model", "llama-3.3-70b-versatile");
            body.put("temperature", 0);

            body.put("messages", new org.json.JSONArray()
                    .put(new JSONObject()
                            .put("role", "system")
                            .put("content", "Eres un experto en reciclaje. Responde solo con una palabra: la categoría."))
                    .put(new JSONObject()
                            .put("role", "user")
                            .put("content", "Clasifica el siguiente residuo en una de las siguientes categorías: " +
                                    "[orgánico, plástico, vidrio, papel, metal, electrónico, peligroso, no reciclable]. " +
                                    "Solo responde con una palabra.\nResiduo: \"" + nombreResiduo + "\""))
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line.trim());
                }
                System.err.println("[Groq API] Error " + responseCode + ": " + errorResponse);
                return null;
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JSONObject jsonResponse = new JSONObject(response.toString());
            String content = jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            return content.trim();

        } catch (Exception e) {
            System.err.println("[Groq API] Error: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }
}
