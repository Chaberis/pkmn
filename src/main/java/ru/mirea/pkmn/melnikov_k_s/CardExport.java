package ru.mirea.pkmn.melnikov_k_s;

import java.io.*;

public class CardExport {


    public void saveCardToFile(Card card) throws IOException {
        String fileName = "src/main/resources/" + card.getName() + ".crd";

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(card);
        }
    }

    public void exportCardToTxt(Card card) throws IOException {
        String fileName = "src/main/resources/" + card.getName() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(card.getPokemonStage().toString());
            writer.newLine();
            writer.write(card.getName());
            writer.newLine();
            writer.write(String.valueOf(card.getHp()));
            writer.newLine();
            writer.write(card.getPokemonType().toString());
            writer.newLine();
            writer.write(card.getEvolvesFrom() != null ? card.getEvolvesFrom().getName() : "-");
            writer.newLine();

            if (card.getSkills() != null && !card.getSkills().isEmpty()) {
                StringBuilder skillsBuilder = new StringBuilder();
                for (AttackSkill skill : card.getSkills()) {
                    skillsBuilder.append(skill.getCost()).append("/")
                            .append(skill.getName()).append("/")
                            .append(skill.getDamage()).append("/")
                            .append(skill.getText()).append(","); // Формируем строку
                }
                if (!skillsBuilder.isEmpty()) {
                    skillsBuilder.setLength(skillsBuilder.length() - 1);
                }
                writer.write(skillsBuilder.toString());
            } else {
                writer.write("-");
            }
            writer.newLine();
            writer.write(card.getWeaknessType() != null ? card.getWeaknessType().toString() : "-");
            writer.newLine();
            writer.write(card.getResistanceType() != null ? card.getResistanceType().toString() : "-");
            writer.newLine();
            writer.write(card.getRetreatCost());
            writer.newLine();
            writer.write(card.getGameSet());
            writer.newLine();
            writer.write(String.valueOf(card.getRegulationMark()));
            writer.newLine();
            writer.write(card.getNumber());
            writer.newLine();
            Student owner = card.getPokemonOwner();
            if (owner != null) {
                writer.write(owner.getSurName() + "/" + owner.getFirstName() + "/" + owner.getFamilyName() + "/" + owner.getGroup());
            }
        }
    }
}