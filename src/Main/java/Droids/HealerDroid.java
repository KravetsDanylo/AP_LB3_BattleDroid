package Droids;
import BattleArena.TurnResult;
import BattleArena.ActionType;

import java.util.List;

public class HealerDroid extends Droid {
    protected int healPower;
    protected double healChance;

    public HealerDroid(String name) {
        super(name, 150, 30, 35, 0.7);
        this.healPower = 20;
        this.healChance = 0.5;
    }

    public int heal(Droid target){
        if(!isAlive()) return 0;
        if(!target.isAlive()) return 0;
        target.receiveHeal(this.healPower);
        return this.healPower;
    }

    @Override
    public TurnResult attack(Droid target) {
        if (!isAlive()) return new TurnResult(ActionType.ATTACK_MISS, 0);
        if(Math.random() < this.healChance && getHp() < getMaxHp() * 0.6){
            int healAmount = heal(this);
            return new TurnResult(ActionType.SELF_HEAL, healAmount);
        }
        return super.attack(target);
    }
    @Override
    public TurnResult performTeamAction(List<Droid> enemies, List<Droid> allies){
        Droid targetForHeal = null;
        double lowestHpPercent = 1.0;

        for (Droid ally : allies) {
            double currentHpPercent = (double) ally.getHp() / ally.getMaxHp();
            if (currentHpPercent < lowestHpPercent) {
                lowestHpPercent = currentHpPercent;
                targetForHeal = ally;
            }
        }

        if (targetForHeal != null && lowestHpPercent < 0.8) {
            int healedAmount = this.heal(targetForHeal);
            System.out.printf(">>> %s heals %s! <<<\n", this.getName(), targetForHeal.getName());
            return new TurnResult(ActionType.SELF_HEAL, healedAmount);
        }

        return super.performTeamAction(enemies, allies);


    }
}
