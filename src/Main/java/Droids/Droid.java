package Droids;
import java.util.List;
import java.util.Random;
import BattleArena.TurnResult;
import BattleArena.ActionType;

public abstract class Droid {
    protected String name;
    protected int maxHp;
    protected int hp;
    protected double armor;
    protected int damage;
    protected double accuracy;

    public Droid(String name, int maxHp, double armor, int damage, double accuracy) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.armor = armor;
        this.damage = damage;
        this.accuracy = accuracy;
    }

    public String getName() { return name; }
    public int getMaxHp() { return maxHp; }
    public int getHp() { return hp; }
    public double getArmor() { return armor; }
    public int getDamage() { return damage; }
    public double getAccuracy() { return accuracy; }

    public boolean isAlive() { return this.hp > 0; }

    public int takeDamage(int damage) {
        int finalDamage = Math.max(0, damage - (int)this.armor);
        this.hp -= finalDamage;
        if (this.armor > 0){
            this.armor -= damage * 0.15;
            if(this.armor < 0){ this.armor = 0; }
        }
        if (this.hp < 0) this.hp = 0;
        return finalDamage;
    }

    public TurnResult attack(Droid target) {
        if (!isAlive()) return new TurnResult(ActionType.ATTACK_MISS, 0);
        if (Math.random() < this.accuracy) {
            int finalDamage = target.takeDamage(this.damage);
            return new TurnResult(ActionType.ATTACK_HIT, finalDamage, target);
        }
        return new TurnResult(ActionType.ATTACK_MISS, 0,target);
    }

    public void receiveHeal(int heal) {
        if (!isAlive()) return;
        this.hp = Math.min(this.hp + heal, maxHp);
    }


    public String getBattleStatus() {
        return String.format("%s [HP: %d/%d | Armor: %.1f]", name, hp, maxHp, armor);
    }

    public TurnResult performTeamAction(List<Droid> enemies, List<Droid> allies){
        if(!enemies.isEmpty()){
            Droid randomTarget = enemies.get(new Random().nextInt(enemies.size()));
            return this.attack(randomTarget);
        }
        return new TurnResult(ActionType.ATTACK_MISS, 0);
    }


    @Override
    public String toString() {
        return "Droid{" +
                "name='" + name + '\'' +
                ", hp=" + hp + "/" + maxHp +
                ", armor=" + armor +
                ", damage=" + damage +
                ", accuracy=" + accuracy +
                '}';
    }
}
