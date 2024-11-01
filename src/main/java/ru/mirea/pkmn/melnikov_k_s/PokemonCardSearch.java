package ru.mirea.pkmn.melnikov_k_s;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Scanner;

public class PokemonCardSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название покемона: ");
        String name = scanner.nextLine();
        System.out.print("Введите номер карты: ");
        String number = scanner.nextLine();
        try {
            JsonNode cardData = PokemonAPI.getCardFromAPI(name, number);
            if (cardData.has("data") && !cardData.get("data").isEmpty()) {
                System.out.println("Данные о карте:");
                System.out.println(cardData.toPrettyString());
            } else {
                System.out.println("Карта не найдена.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обращении к API: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}