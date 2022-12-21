package ui;

import model.Category;
import model.Diary;
import model.Mob;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// The class representing our diary application
public class OldDiaryApp {
    private static final String JSON_STORE = "./data/diary.json"; //Folder location for storage and reading
    private Diary diary; // Main diary
    private Scanner input;             // Scanner for user input
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Constructs an empty list of categories for this diary and runs it
    public OldDiaryApp() {
        diary = new Diary();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runDiaryApp();
    }

    // MODIFIES: this
    // EFFECTS: Runs the diary
    private void runDiaryApp() {
        while (true) {
            System.out.println("Welcome to your mob diary!\n");
            listDiaryCategories();
            listDiaryCommands();
            handleDiaryCommands(userInput());
        }
    }

    // EFFECTS: Lists all categories available to user in Diary
    private void listDiaryCategories() {
        if (diary.getCategories().size() == 0) {
            System.out.println("You have no categories at the moment\n");
        } else {
            System.out.println("Available categories:");
            for (Category category : diary.getCategories()) {
                System.out.println("- " + category.getTitle());
            }
            System.out.println();
        }
    }

    // EFFECTS: Lists all commands available to user at the Diary level
    private void listDiaryCommands() {
        System.out.println("Valid Commands:");
        System.out.println("cc -> Create category");
        System.out.println("dc -> Delete category");
        System.out.println("ec -> Enter category");
        System.out.println("sd -> Save Diary");
        System.out.println("ld -> Load Diary");
        System.out.println("q  -> Quit Program");
    }

    // MODIFIES: this
    // EFFECTS: Prompts the user and handles Diary commands from user
    private void handleDiaryCommands(String command) {
        if (command.equals("q")) {
            quitDiary();
        } else if (command.equals("cc")) {
            System.out.println("Please input a title for your new category:");
            diary.addCategory(userInput());
        } else if (command.equals("dc")) {
            System.out.println("Please input the title of the category you want to delete:");
            diary.removeCategory(userInput());
        } else if (command.equals("ec")) {
            System.out.println("Please input the title of the category you want to enter");
            enterCategory(userInput());
        } else if (command.equals("sd")) {
            saveDiary();
        } else if (command.equals("ld")) {
            loadDiary();
        } else {
            System.out.println("Error: " + command + " is an invalid diary command\n");
        }
    }

    // EFFECTS: Enters a category and runs it if matching title is found,
    //          Otherwise returns to runDiary() with indication that no category was found
    private void enterCategory(String title) {
        for (Category category: diary.getCategories()) {
            if (category.getTitle().equals(title)) {
                runCategory(category);
            }
        }
        System.out.println("No category titled " + title + " found\n");
    }

    // EFFECTS: Runs a category
    private void runCategory(Category category) {
        while (true) {
            System.out.println("Now in category " + category.getTitle() + "\n");
            listCategoryMobs(category);
            listCategoryCommands();
            handleCategoryCommands(category, userInput());
        }
    }

    // EFFECTS: Lists all mobs available in a category
    private void listCategoryMobs(Category category) {
        if (category.getMobs().size() == 0) {
            System.out.println("You have no mobs in " + category.getTitle() + " at the moment\n");
        } else {
            System.out.println("Available mobs in " + category.getTitle() + ":");
            for (Mob mob : category.getMobs()) {
                System.out.println("- " + mob.getName());
            }
            System.out.println();
        }
    }

    // EFFECTS: Lists all commands available in a category
    private void listCategoryCommands() {
        System.out.println("Valid Commands:");
        System.out.println("ct -> Change category title");
        System.out.println("cm -> Create mob");
        System.out.println("dm -> Delete mob");
        System.out.println("em -> Enter mob");
        System.out.println("ex -> Exit category");
        System.out.println("q  -> Quit Program");
    }

    // MODIFIES: this
    // EFFECTS: Handles all category level commands
    private void handleCategoryCommands(Category category, String command) {
        if (command.equals("q")) {
            quitDiary();
        } else if (command.equals("ct")) {
            System.out.println("Please input a new title for your category " + category.getTitle() + ":");
            category.setTitle(userInput());
        } else if (command.equals("cm")) {
            System.out.println("Please input a name for your new mob:");
            String name = userInput();
            System.out.println("Please input the stats for your new mob " + name + ":");
            String stats = userInput();
            System.out.println("Please input a description for your new mob " + name + ":");
            category.addMob(name, stats, userInput());
        } else if (command.equals("dm")) {
            System.out.println("Please input the name of the mob you want to delete:");
            category.removeMob(userInput());
        } else if (command.equals("em")) {
            enterMob(category);
        } else if (command.equals("ex")) {
            System.out.println("Exiting category");
            runDiaryApp();
        } else {
            System.out.println("Error: " + command + " is an invalid category command\n");
        }
    }

    // EFFECTS: Enters a mob within a category and runs it if found,
    //          goes back to the category if no mob is found
    private void enterMob(Category category) {
        System.out.println("Please input the name of the mob you want to enter:");
        String name = userInput();
        for (Mob mob: category.getMobs()) {
            if (mob.getName().equals(name)) {
                runMob(mob, category);
            }
        }
        System.out.println("No mob named " + name + " in category " + category.getTitle() + " found\n");
    }

    // EFFECTS: Runs a mob
    private void runMob(Mob mob, Category category) {
        while (true) {
            System.out.println("Now in mob " + mob.getName() + " within category " + category.getTitle() + "\n");
            listMobCommands();
            String command = userInput();
            if (command.equals("q")) {
                quitDiary();
            } else {
                handleMobCommands(mob, category, command);
            }
        }
    }

    // EFFECTS: Lists all commands available for mobs
    private void listMobCommands() {
        System.out.println("Valid Commands:");
        System.out.println("vm -> View mob");
        System.out.println("cn -> Change name");
        System.out.println("cs -> Change stats");
        System.out.println("cd -> Change description");
        System.out.println("ad -> Add drop");
        System.out.println("rd -> Remove drop");
        System.out.println("ex -> Exit mob");
        System.out.println("q  -> Quit Program");
    }

    // MODIFIES: this
    // EFFECTS: Handles all commands for mobs
    private void handleMobCommands(Mob mob, Category category, String command) {
        if (command.equals("vm")) {
            viewMob(mob);
        } else if (command.equals("cn")) {
            System.out.println("Please input a new name for your mob " + mob.getName() + ":");
            mob.setName(userInput());
        } else if (command.equals("cs")) {
            System.out.println("Please input the new stats for your mob " + mob.getName() + ":");
            mob.setStats(userInput());
        } else if (command.equals("cd")) {
            System.out.println("Please input a new description for your mob " + mob.getName() + ":");
            mob.setDescription(userInput());
        } else if (command.equals("ad")) {
            System.out.println("Please input the name of a new drop for your mob " + mob.getName() + ":");
            mob.addDrop(userInput());
        } else if (command.equals("rd")) {
            System.out.println("Please input the name of a drop to remove from your mob " + mob.getName() + ":");
            mob.removeDrop(userInput());
        } else if (command.equals("ex")) {
            System.out.println("Exiting mob " + mob.getName() + " to category " + category.getTitle());
            runCategory(category);
        } else {
            System.out.println("Error: " + command + " is an invalid mob command\n");
        }
    }

    // EFFECTS: Displays information about a mob to the user
    private void viewMob(Mob mob) {
        System.out.println("Mob name: " + mob.getName());
        System.out.println("Stats: " + mob.getStats());
        System.out.println("Description: " + mob.getDescription());
        if (mob.getDrops().size() == 0) {
            System.out.println("Drops: None\n");
        } else {
            System.out.println("Drops: " + String.join(", ", mob.getDrops()) + "\n");
        }
    }

    // EFFECTS: Prints a prompt before asking for user input, then returns the input as a String
    private String userInput() {
        System.out.print("\n> ");
        return input.nextLine();
    }

    // EFFECTS: saves the diary to a file
    private void saveDiary() {
        try {
            jsonWriter.open();
            jsonWriter.write(diary);
            jsonWriter.close();
            System.out.println("Successfully saved this diary to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the diary from a file
    private void loadDiary() {
        try {
            diary = jsonReader.read();
            System.out.println("Successfully loaded diary from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Could not find file " + JSON_STORE);
        }
    }

    // EFFECTS: Quits the diary program
    private void quitDiary() {
        System.out.println("Closing Diary");
        System.exit(0);
    }
}
