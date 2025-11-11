package BattleArena;
import Droids.*;
import java.util.ArrayList;
import java.util.List;

public class Duel {
    private Droid droid1;
    private Droid droid2;
    private Droid winner;
    private List<String> battleLog;

    public Duel(Droid droid1, Droid droid2) {
        this.droid1 = droid1;
        this.droid2 = droid2;
        this.battleLog = new ArrayList<>();
    }
    public List<String> startDuel (){
        String initialStatus = String.format("====== BATTLE START: %s vs %s ======", droid1.getName(), droid2.getName());
        System.out.println("\n" + initialStatus);
        battleLog.add(initialStatus);
        System.out.println("Initial Status: " + droid1.getBattleStatus() + " | " + droid2.getBattleStatus());
        System.out.println("--------------------------------------------------");
        Droid attacker = droid1;
        Droid defender = droid2;
        int round = 1;
        while(droid1.isAlive() && droid2.isAlive()){
            String roundHeader = String.format("\n----- Round %d -----", round++);
            System.out.println(roundHeader);
            battleLog.add(roundHeader);
            TurnResult result = attacker.attack(defender);
            String logEntry;
            switch(result.getAction()){
                case ATTACK_HIT:
                    logEntry = String.format("%s attacks %s and deals %d damage!", attacker.getName(), defender.getName(), result.getValue());
                    break;
                case ATTACK_MISS:
                    logEntry = String.format("%s attacks %s but misses!", attacker.getName(), defender.getName());
                    break;
                case SELF_HEAL:
                    logEntry = String.format("%s heals himself for %d HP!", attacker.getName(), result.getValue());
                    break;
                default:
                    logEntry = "An unknown action occurred.";
                    break;
            }
            System.out.println(logEntry);
            battleLog.add(logEntry);
            String statusUpdate = "Current Status: " + droid1.getBattleStatus() + " | " + droid2.getBattleStatus();
            System.out.println(statusUpdate);
            battleLog.add(statusUpdate);
            if(defender.isAlive()){
                Droid temp = attacker;
                attacker = defender;
                defender = temp;
            }
        }
        this.winner = droid1.isAlive() ? droid1 : droid2;
        String finalMessage = "\n================== BATTLE END ==================\n" +
                "The winner is: " + winner.getName() + "!\n" +
                "Final Status: " + winner.getBattleStatus();

        System.out.println(finalMessage);
        battleLog.add(finalMessage);

        return battleLog;
    }
}
