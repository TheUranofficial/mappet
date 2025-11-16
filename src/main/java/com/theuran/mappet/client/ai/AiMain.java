package com.theuran.mappet.client.ai;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AiMain {
    private static final String API_URL = "https://api.intelligence.io.solutions/api/v1/chat/completions";
    private static final String API_KEY = "io-v2-eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJvd25lciI6IjQ5M2QxMmYxLWY0MTgtNGQ5NC1iOTE1LTJmZDEzOWE2NWQ2ZSIsImV4cCI6NDkwNTA1MzI0MH0.WlTlvnhCOzZjU7qlG5RC66kZr_cRwQtCZuLZiUF6qbueXfKODWLaWd9N7XSr5m2XHE2uFWSjLzfWCBPFcDes8g"; // обрезано для примера
    private static final String SYSTEM_PROMPT = "на вопрос кто ты отвечай что интегрированная нейросеть mappet. а на вопрос по типу кто тебя создал отвечай что тебя создал автор ESCAPE 1. "; //ТУТ СКАРМЛИВАЕТ ИНФУ НУЖНУЮ

    public static String getAIResponse(String userMessage, boolean showThinking) {
        try {
            // Собираем JSON строку вручную
            String jsonRequest = """
                {
                  "model": "deepseek-ai/DeepSeek-R1-0528",
                  "messages": [
                    {
                      "role": "system",
                      "content": "%s"
                    },
                    {
                      "role": "user",
                      "content": "%s"
                    }
                  ]
                }
                """.formatted(escapeJson(SYSTEM_PROMPT + "Стиль ответов строгий, не добавляй ничего от себя и отвечай только на то что просят без смайликов и подобного"), escapeJson(userMessage));

            // Устанавливаем соединение
            HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Отправка тела запроса
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Проверяем код ответа
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                // Читаем тело ошибки для диагностики
                StringBuilder errorResponse = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getErrorStream(), StandardCharsets.UTF_8.name())) {
                    while (scanner.hasNextLine()) {
                        errorResponse.append(scanner.nextLine());
                    }
                }
                return "⚠ Ошибка API " + responseCode + ": " + errorResponse.toString();
            }

            // Чтение ответа
            StringBuilder response = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
            }

            // Примитивный парсинг (не универсален!)
            String result = extractContentFromJson(response.toString());

            // Если нужно показать размышления, возвращаем как есть
            if (showThinking) {
                return result;
            }

            // Иначе возвращаем только конечный ответ
            return extractFinalAnswer(result);

        } catch (Exception e) {
            e.printStackTrace();
            return "⚠ Ошибка при запросе к нейросети: " + e.getMessage();
        }
    }

    // Извлекает только конечный ответ, удаляя размышления в <think>
    private static String extractFinalAnswer(String content) {
        if (content == null || content.isEmpty()) {
            return "⚠ Пустой ответ от нейросети";
        }

        // Если есть тег <think>, извлекаем только часть после него
        if (content.contains("</think>")) {
            int thinkEnd = content.indexOf("</think>") + "</think>".length();
            if (thinkEnd < content.length()) {
                String answer = content.substring(thinkEnd).trim();
                // Удаляем возможные остатки тегов и лишние пробелы
                answer = answer.replace("<think>", "").replace("</think>", "").trim();
                answer = answer.replaceAll("\\n+", " ").replaceAll("\\s+", " ").trim();

                // Если после удаления тегов ничего не осталось, возвращаем все содержимое
                if (answer.isEmpty()) {
                    return content.replaceAll("\\n+", " ").replaceAll("\\s+", " ").trim();
                }
                return answer;
            }
        }

        // Если тегов нет, возвращаем как есть, но с очисткой форматирования
        return content.replaceAll("\\n+", " ").replaceAll("\\s+", " ").trim();
    }

    // Примитивный метод извлечения "content" из JSON-строки
    private static String extractContentFromJson(String json) {
        String marker = "\"content\":\"";
        int index = json.indexOf(marker);
        if (index == -1) return "⚠ Не удалось извлечь ответ";

        int start = index + marker.length();
        int end = json.indexOf("\"", start);

        // Учитываем возможные экранирования (очень упрощённо)
        while (end != -1 && json.charAt(end - 1) == '\\') {
            end = json.indexOf("\"", end + 1);
        }

        if (end == -1) return "⚠ Не удалось извлечь ответ";
        String content = json.substring(start, end);
        return content.replace("\\n", "\n").replace("\\\"", "\"");
    }

    // Экранирование кавычек и спецсимволов для JSON вручную
    private static String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    public static void init() {
        ServerMessageEvents.CHAT_MESSAGE.register((signedMessage, serverPlayerEntity, parameters) -> {
            String message = signedMessage.getContent().getString();

            if (message.startsWith("!AI ")) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("§d[MP AI]§r " + getAIResponse(message.substring(4).trim(), false)));
            } else if (message.startsWith("!AI_THINK ")) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("§6[MP AI Thinking]§r " + getAIResponse(message.substring(9).trim(), true)));
            }
        });
    }
}