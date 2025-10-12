package Droids;
import BattleArena.TurnResult;
public class AssasinDroid extends Droid {
    private double dodgeChance;   // шанс ухилення
    private double critChance;    // шанс критичного удару


    public AssasinDroid(String name) {
        super(name, 150, 40, 45, 0.8);
        this.dodgeChance = 0.3;
        this.critChance = 0.25;
    }

    @Override
    public TurnResult attack(Droid target) {
        if (!isAlive()) return super.attack(target);
        int originalDamage = this.damage;
        try {
            if (Math.random() < critChance) {
                this.damage *= 2;
                System.out.println("CRITICAL HIT!");
            }
            return super.attack(target);

        } finally {
            this.damage = originalDamage;
        }

    }

    @Override
    public int takeDamage(int damage) {
        if (Math.random() < dodgeChance) {
            System.out.println(this.getName() + " DODGED the attack!");
            return 0;
        }
        return super.takeDamage(damage);
    }
}
