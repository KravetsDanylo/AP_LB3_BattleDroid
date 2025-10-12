package Droids;
import BattleArena.TurnResult;

public class TitanDroid extends Droid {
    protected double rageThreshold;

    public TitanDroid(String name) {
        super(name, 300, 60, 70, 0.4);
        this.rageThreshold = 0.3;
    }

    @Override
    public TurnResult attack(Droid target) {
        if (!isAlive()) return super.attack((target));
        if (getHp() < getMaxHp() * rageThreshold) {
            int originalDamage = this.damage;
            try {
                this.damage *= 2;
                System.out.println(this.getName() + " is in RAGE mode! Damage is doubled!");
                return super.attack(target);
            } finally {
                this.damage = originalDamage;
            }
        } else {
            return super.attack(target);

        }

    }
}
