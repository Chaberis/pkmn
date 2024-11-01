package ru.mirea.pkmn.melnikov_k_s;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardImport {

    public Card loadCardFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        PokemonStage stage = PokemonStage.valueOf(lines.get(0).toUpperCase());
        String name = lines.get(1);
        int hp = Integer.parseInt(lines.get(2));
        EnergyType pokemonType = EnergyType.valueOf(lines.get(3).toUpperCase());

        Card evolvesFrom = lines.get(4).equals("-") ? null : loadEvolvesFrom(lines.get(4));

        List<AttackSkill> skills = parseSkills(lines.get(5));
        EnergyType weaknessType = lines.get(6).equals("-") ? null : EnergyType.valueOf(lines.get(6).toUpperCase());
        EnergyType resistanceType = lines.get(7).equals("-") ? null : EnergyType.valueOf(lines.get(7).toUpperCase());

        String retreatCost = lines.get(8);
        String gameSet = lines.get(9);
        char regulationMark = lines.get(10).charAt(0);

        String[] ownerData = lines.get(11).split("/");
        Student owner = new Student(ownerData[1], ownerData[0], ownerData[2], ownerData[3]);

        String number = lines.size() > 12 ? lines.get(12) : "";

        return new Card(stage, name, hp, pokemonType, evolvesFrom, skills, weaknessType, resistanceType, retreatCost, gameSet, regulationMark, owner, number);
    }

    private Card loadEvolvesFrom(String evolvesFromName) {
        //тип загрузка карты эволюции
        return null;
    }

    private List<AttackSkill> parseSkills(String skillsLine) {
        List<AttackSkill> skills = new ArrayList<>();

        if (skillsLine.equals("-")) {
            return skills;
        }

        String[] skillsArray = skillsLine.split(",");

        for (String skill : skillsArray) {
            String[] parts = skill.split("/");
            if (parts.length < 3) {
                continue;
            }
            String cost = parts[0];
            String name = parts[1];
            int damage = Integer.parseInt(parts[2]);
            skills.add(new AttackSkill(name, cost, damage, ""));
        }

        return skills;
    }
    public Card loadCardFromSerializedFile(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Card) in.readObject();
        }
    }
}
