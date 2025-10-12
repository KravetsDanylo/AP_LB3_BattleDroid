package Main;
import Droids.*;
import BattleArena.*;

import java.io.*;
import java.util.*;


public class Main {
    public static List <Droid> userDroids = new ArrayList<>();
    public static List <String> fightLog = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean count = true;
        String userChoice;
        while(count){
            menu();
            userChoice = scanner.nextLine().trim();
            switch(userChoice){
                case "1":
                    createDroid();
                    break;
                case "2":
                    showDroids();
                    break;
                case "3":
                    runBattle();
                    break;
                case "4":
                    runTeamBattle();
                    break;
                case "5":
                    recordLogToFile();
                    break;
                case "6":
                    replayBattleFromFile();
                    break;
                case "7":
                    count = false;
                    break;
                default:
                    System.out.println("Invalid choice");

            }
        }
        scanner.close();
    }

    public static void menu(){
        System.out.println("\n====== GAME <DROID BATTLE> ======");
        System.out.println("1) Create Droid");
        System.out.println("2) Show my droids");
        System.out.println("3) Fight 1 vs 1");
        System.out.println("4) Fight Team vs Team");
        System.out.println("5) Record a last battle in file");
        System.out.println("6) Replay a last battle from file");
        System.out.println("7) Exit");
        System.out.println("Your choice:");

    }
    public static void createDroid(){
        String type;
        String droidName;
        System.out.println("Choose a type of droid:");
        System.out.println("1) Healer Droid");
        System.out.println("2) Titan droid");
        System.out.println("3) Assassin Droid");
        System.out.println("Which droid do you choose:");
        type = scanner.nextLine();
        System.out.println("Enter a name od droid:");
        droidName = scanner.nextLine().trim();
        Droid droid = null;
        switch(type){
            case "1":
                droid = new HealerDroid(droidName);
                break;
            case "2":
                droid = new TitanDroid(droidName);
                break;
            case "3":
                droid = new AssasinDroid(droidName);
                break;
            default:
                System.out.println("Invalid type of droid");
                return;

        }
        userDroids.add(droid);
        System.out.println("Successfully created: " + droid);
    }
    public static void showDroids(){
        if(userDroids.isEmpty()){
            System.out.println("Droid list is empty. Firstly create droids!");
            return;
        }
        System.out.println("\n===== Your Droids =====");
        int index = 1;
        for (Droid d : userDroids) {
            System.out.println(index++ + ") " + d);
        }
    }

    public static void runBattle(){
        if(userDroids.size() < 2){
            System.out.println("There should be at least 2 droids in the list");
            return;
        }
        showDroids();
        int firstDroid, secondDroid;
        try {
            System.out.println("Choose the first droid (enter number):");
            firstDroid = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return;
        }
        try {
            System.out.println("Choose the second droid (enter number):");
            secondDroid = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return;
        }
        if(firstDroid == secondDroid){
            System.out.println("A droid cannot fight itself. Please choose two different droids.");
            return;
        }
        if(firstDroid < 1 || firstDroid > userDroids.size() || secondDroid < 1 || secondDroid > userDroids.size()){
            System.out.println("Invalid index");
            return;
        }
        Droid droid1 = cloneDroid(userDroids.get(firstDroid - 1));
        Droid droid2 = cloneDroid(userDroids.get(secondDroid - 1));
        Duel duel = new Duel(droid1, droid2);
        fightLog = duel.startDuel();

    }
    private static Droid cloneDroid(Droid droid){
        if(droid instanceof HealerDroid){ return new HealerDroid(droid.getName());}
        if(droid instanceof TitanDroid){ return new TitanDroid(droid.getName());}
        if(droid instanceof AssasinDroid){ return new AssasinDroid(droid.getName());}
        return new AssasinDroid(droid.getName());
    }


    public static void runTeamBattle() {
        if (userDroids.size() < 2) {
            System.out.println("There should be at least 2 droids in the list");
            return;
        }


        List<Droid> availableDroids = new ArrayList<>(userDroids);


        System.out.println("\n--- Forming Team A ---");
        List<Droid> teamA = createTeamFromInput(availableDroids);
        if (teamA.isEmpty()) {
            System.out.println("Team A cannot be empty! Battle canceled.");
            return;
        }
        System.out.println("Team A is formed!");


        System.out.println("\n--- Forming Team B ---");

        List<Droid> teamB = createTeamFromInput(availableDroids);
        if (teamB.isEmpty()) {
            System.out.println("Team B cannot be empty! Battle canceled.");
            return;
        }
        System.out.println("Team B is formed!");

        System.out.println("\nTeams are ready. Starting battle!");
        TeamBattle teamBattle = new TeamBattle(teamA,teamB);
        fightLog = teamBattle.startBattle();
    }
    public static List<Droid> createTeamFromInput(List<Droid> availableDroids) {
        List<Droid> team = new ArrayList<>();
        List<Integer> chosenIndexes = new ArrayList<>(); // Щоб не можна було обрати одного дроїда двічі в одну команду

        // Показуємо доступних дроїдів
        System.out.println("Available droids:");
        for (int i = 0; i < userDroids.size(); i++) {
            // Перевіряємо, чи цей дроїд ще доступний
            if (availableDroids.contains(userDroids.get(i))) {
                System.out.println((i + 1) + ") " + userDroids.get(i));
            }
        }
        System.out.println("Choose droids by numbers (separated by space):");

        String line = scanner.nextLine().trim();
        String[] indexesAsStrings = line.split(" ");

        for (String s : indexesAsStrings) {
            try {
                int index = Integer.parseInt(s);

                if (index < 1 || index > userDroids.size()) {
                    System.out.println("Warning: Index " + index + " is out of bounds. Skipping.");
                    continue;
                }


                if (chosenIndexes.contains(index)) {
                    System.out.println("Warning: Droid with index " + index + " has already been chosen for this team. Skipping.");
                    continue;
                }
                Droid originalDroid = userDroids.get(index - 1);

                if (!availableDroids.contains(originalDroid)) {
                    System.out.println("Warning: Droid '" + originalDroid.getName() + "' is not available. Skipping.");
                    continue;
                }


                team.add(cloneDroid(originalDroid));
                availableDroids.remove(originalDroid);
                chosenIndexes.add(index);

            } catch (NumberFormatException e) {
                System.out.println("Warning: '" + s + "' is not a valid number. Skipping.");
            }
        }
        return team;
    }
    public static void recordLogToFile(){
        if(fightLog.isEmpty()){
            System.out.println("There is no battle log to save. Fight first!");
            return;
        }
        String filename;
        System.out.println("Enter the file name:");
        filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("File name cannot be empty. Operation canceled.");
            return;
        }
        filename += ".txt";
        try(BufferedWriter w = new BufferedWriter(new FileWriter(filename))){
            for(String line : fightLog){
                w.write(line);
                w.newLine();
            }
            System.out.println("Battle log successfully saved to file: " + filename);
        } catch (IOException e){
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static void replayBattleFromFile(){
        String filename;
        String line;
        System.out.println("Enter the file name from which you want to replay the fight:");
        filename = scanner.nextLine().trim();
        try(BufferedReader r = new BufferedReader(new FileReader(filename))){
            while((line = r.readLine()) != null){
                System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");

        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file:" + e.getMessage());
        }


    }




}
