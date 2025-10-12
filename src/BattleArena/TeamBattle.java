package BattleArena;

import Droids.Droid;
import java.util.ArrayList;
import java.util.List;

public class TeamBattle {
    private List<Droid> teamA;
    private List<Droid> teamB;
    private List<String> battleLog;

    public TeamBattle(List<Droid> teamA, List<Droid> teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.battleLog = new ArrayList<>();

    }
    public List<String> startBattle(){
        log("====== TEAM BATTLE START ======");
        log("Team A:" + getTeamNames(teamA));
        log("Team B:" + getTeamNames(teamB));
        log("---------------------------------");
        int round = 1;
        while(isTeamAlive(teamA) && isTeamAlive(teamB)) {
            log("\n----- Round " + round++ + " -----");
            performTeamTurn(teamA, teamB);
            performTeamTurn(teamB, teamA);
            log("--- End of Round Status ---");
            log("Team A: " + getTeamStatus(teamA));
            log("Team B: " + getTeamStatus(teamB));
        }
        log("\n====== BATTLE END ======");
        if (isTeamAlive(teamA)) {
            log("WINNER: Team A!");
        } else {
            log("WINNER: Team B!");
        }

        return battleLog;

    }
    private void performTeamTurn(List<Droid> attackingTeam, List<Droid> defendingTeam) {
        List<Droid> livingAttackers = getLivingDroids(attackingTeam);
        for (Droid attacker : livingAttackers) {
            if (!isTeamAlive(defendingTeam)) break;
            log(attacker.getName() + "'s turn:");
            TurnResult result = attacker.performTeamAction(getLivingDroids(defendingTeam), getLivingDroids(attackingTeam));
            String logEntry;
            switch(result.getAction()){
                case ATTACK_HIT:
                    logEntry = String.format("  -> %s attacks %s and deals %d damage!", attacker.getName(), result.getTarget().getName(), result.getValue());
                    break;
                case ATTACK_MISS:
                    logEntry = String.format("  -> %s attacks %s but misses!", attacker.getName(), result.getTarget().getName());
                    break;
                case SELF_HEAL:
                    logEntry = String.format("  -> %s restores %d HP!", attacker.getName(), result.getValue());
                    break;
                default:
                    logEntry = "An unknown action occurred.";
                    break;
            }
            log(logEntry);
        }
    }
    private boolean isTeamAlive(List<Droid> team){
        for(Droid d : team){
            if(d.isAlive()){
                return true;
            }
        }
        return false;
    }
    private List<Droid> getLivingDroids(List <Droid> team){
        List <Droid> livingDroids = new ArrayList<>();
        for(Droid d : team){
            if(d.isAlive()) {
                livingDroids.add(d);
            }
        }
        return livingDroids;

    }


    private void log(String message) {
        System.out.println(message);
        battleLog.add(message);
    }
    private String getTeamNames(List <Droid> team){
        String names = "";
        for(int i = 0; i < team.size(); i++){
            names += team.get(i).getName();
            if (i < team.size() - 1) {
                names += ", ";
            }
        }
        return names;
    }
    private String getTeamStatus(List <Droid> team){
        String statuses = "";
        for(int i = 0; i < team.size(); i++){
            statuses += team.get(i).getBattleStatus();
            if (i < team.size() - 1) {
                statuses += " | ";
            }
        }
        return statuses;
    }
}

