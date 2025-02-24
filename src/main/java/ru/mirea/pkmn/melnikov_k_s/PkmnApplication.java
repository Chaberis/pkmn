package ru.mirea.pkmn.melnikov_k_s;

import com.fasterxml.jackson.databind.JsonNode;
import ru.mirea.pkmn.melnikov_k_s.web.http.PkmnHttpClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PkmnApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CardImport cardImport = new CardImport();
        CardExport cardExport = new CardExport();

        System.out.println("Выберите действие:");
        System.out.println("1. Загрузить карту из текстового файла");
        System.out.println("2. из .crd в .txt");

        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            if (choice == 1) {
                String filePath = "src/main/resources/my_card.txt";
                Card card = cardImport.loadCardFromFile(filePath);
                System.out.println("Загруженная карта: ");
                System.out.println(card);
                PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();
                JsonNode cardData = pkmnHttpClient.getPokemonCard(card.getName(), card.getNumber());
                saveJsonToFile(cardData, "src/main/resources/" + card.getName() + "_data.json");
                System.out.println("Полученная JSON структура:");
                System.out.println(cardData.toPrettyString());

                if (cardData.has("data") && !cardData.get("data").isEmpty()) {
                    JsonNode dataNode = cardData.get("data").get(0);
                    if (dataNode.has("attacks")) {
                        JsonNode attacksNode = dataNode.get("attacks");

                        for (AttackSkill skill : card.getSkills()) {
                            String skillName = skill.getName();
                            for (JsonNode attack : attacksNode) {
                                // Сравниваем названия атак
                                if (attack.get("name").asText().equals(skillName)) {
                                    // Извлекаем цену, урон и текст описания
                                    String attackCost = attack.has("cost") ? attack.get("cost").toString() : "";
                                    int attackDamage = attack.has("damage") ? Integer.parseInt(attack.get("damage").asText().replace("+", "")) : 0;
                                    String attackText = attack.has("text") ? attack.get("text").asText() : "";

                                    skill.setCost(attackCost);
                                    skill.setDamage(attackDamage);
                                    skill.setText(attackText);

                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("У данной карты нет атак.");
                    }
                } else {
                    System.out.println("Карта не найдена в API.");
                }

                cardExport.saveCardToFile(card);
                System.out.println("Карта успешно сохранена.");

            } else if (choice == 2) {
                System.out.print("Введите путь к файлу .crd: ");
                String filePath = scanner.nextLine();

                Card card = cardImport.loadCardFromSerializedFile(filePath);
                cardExport.exportCardToTxt(card);

                System.out.println("Карта успешно экспортирована в текстовый файл.");

            } else {
                System.out.println("Некорректный выбор.");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("программа выполнена");
        }
    }

    private static void saveJsonToFile(JsonNode jsonData, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonData.toPrettyString());
            writer.newLine();
        }
    }
}