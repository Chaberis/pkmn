package ru.mirea.pkmn.melnikov_k_s;

import java.io.Serializable;

public class AttackSkill implements Serializable {
    public static final long serialVersionUID = 1L;

    private String name;
    private String cost;
    private int damage;
    private String text;

    public AttackSkill(String name, String cost, int damage, String text) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.text = text;
    }

    public AttackSkill() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "AttackSkill{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                ", damage=" + damage +
                ", text='" + text + '\'' +
                '}';
    }
}