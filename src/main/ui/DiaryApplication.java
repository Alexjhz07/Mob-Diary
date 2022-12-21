package ui;

import model.Category;
import model.Diary;
import model.Event;
import model.EventLog;
import model.Mob;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Class representing the Graphical User Interface for our Diary Application
// Parts of the code here is based off the simple drawing player application from lectures:
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
// The rest is from reading the documentation and examples provided by Oracle
// Visual Guide: https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
// Border Layout: https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
// JTextArea: https://docs.oracle.com/javase/7/docs/api/javax/swing/JTextArea.html#:~
// Buffered Image: https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
// The JSON Reader and Writer code is carried over from Phase 2, sourced and modified from provided examples.
// JSON Items: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// PHASE 3: Logging
// Window Listener: https://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
public class DiaryApplication extends JFrame implements ActionListener {
    public static final int WIDTH = 2600; // Width of window in px
    public static final int HEIGHT = 1800; // Height of window in px

    private static final String IMAGE = "./data/doggo.jpg"; // Easter egg image location :)
    private final String easterCode = "!Doggo"; // Code for Easter egg event
    private BufferedImage doggo;
    private JLabel dogPanel;

    private final Font largeText = new Font("Serif", Font.PLAIN, 50);
    private final Font mediumText = new Font("Serif", Font.PLAIN, 40);
    private static final String JSON_STORE = "./data/diary.json"; // Save location

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Diary diary; // Main diary
    private Category currentCategory; // Tracks current / last category we were / are in
    private Mob currentMob; // Tracks current / last mob we were / are in
    private String currentInput; // Tracks of the current user input

    private JPanel topPanel; // Contains everything in the top area for users
    private JPanel botPanel; // Contains the textArea
    private JLabel menu; // Tells us what level we're currently at
    private JTextField textField; // The input area for users, part of topPanel
    private int currentMenu; // 0 for Diary, 1 for Category, 2 for Mob

    // Diary level buttons
    private JButton save;   // Diary level
    private JButton load;   // Diary level
    private JButton create; // Diary and Category levels
    private JButton delete; // Diary and Category levels
    private JButton enter;  // Diary and Category levels

    // Category level additional buttons
    private JButton setTitle; // Category level
    private JButton back;     // Category and Mob level

    // Mob level additional buttons
    private JButton setName;        // Mob level
    private JButton setStats;       // Mob level
    private JButton setDescription; // Mob level
    private JButton addDrop;        // Mob level
    private JButton removeDrop;     // Mob level

    // Text area for displaying information
    private JTextArea textArea;     // All levels

    // MODIFIES: this
    // EFFECTS: Constructs a new diary, initializes GUI and runs the program
    public DiaryApplication() {
        super("Mob Diary");
        initializeClosing();
        initializeGraphics();
        initializeDiaryMenu();
        initializeTextArea();
        initializeEasterEgg();
        diary = new Diary();
        currentCategory = null;
        currentMob = null;
        currentInput = "";
        currentMenu = 0;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Allows program to log all events upon closing frame
    private void initializeClosing() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: Adds Starting JLabel and JComboBox to the window for Diary View
    private void initializeDiaryMenu() {
        topPanel = new JPanel();
        this.add(topPanel, BorderLayout.NORTH);

        menu = new JLabel("Diary View");
        menu.setFont(largeText);
        menu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        topPanel.add(menu);

        textField = new JTextField("Enter a category to start!");
        textField.setFont(mediumText);
        textField.setColumns(20);
        textField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        textField.addActionListener(this);
        topPanel.add(textField);

        initializeDiaryButtons();
    }

    // MODIFIES: this
    // EFFECTS: Adds Starting JButtons to the window for Diary View
    private void initializeDiaryButtons() {
        save = new JButton("Save");
        save.setFont(mediumText);
        save.addActionListener(this);
        topPanel.add(save);

        load = new JButton("Load");
        load.setFont(mediumText);
        load.addActionListener(this);
        topPanel.add(load);

        create = new JButton("Create");
        create.setFont(mediumText);
        create.addActionListener(this);
        topPanel.add(create);

        delete = new JButton("Delete");
        delete.setFont(mediumText);
        delete.addActionListener(this);
        topPanel.add(delete);

        initializeHiddenButtons();
    }

    // MODIFIES: this
    // EFFECTS: Adds Starting JButtons to the window for Category and Mob mixed view
    private void initializeHiddenButtons() {
        setTitle = new JButton("Set Title");
        setTitle.setFont(mediumText);
        setTitle.addActionListener(this);
        topPanel.add(setTitle);
        setTitle.setVisible(false);

        setName = new JButton("Set Name");
        setName.setFont(mediumText);
        setName.addActionListener(this);
        topPanel.add(setName);
        setName.setVisible(false);

        setDescription = new JButton("Set Description");
        setDescription.setFont(mediumText);
        setDescription.addActionListener(this);
        topPanel.add(setDescription);
        setDescription.setVisible(false);

        setStats = new JButton("Set Stats");
        setStats.setFont(mediumText);
        setStats.addActionListener(this);
        topPanel.add(setStats);
        setStats.setVisible(false);

        initializeHiddenButtonsTwo();
    }

    // MODIFIES: this
    // EFFECTS: Adds Starting JButtons to the window for Category and Mob mixed view + Enter (Diary / Cat)
    private void initializeHiddenButtonsTwo() {
        addDrop = new JButton("Add Drop");
        addDrop.setFont(mediumText);
        addDrop.addActionListener(this);
        topPanel.add(addDrop);
        addDrop.setVisible(false);

        removeDrop = new JButton("Remove Drop");
        removeDrop.setFont(mediumText);
        removeDrop.addActionListener(this);
        topPanel.add(removeDrop);
        removeDrop.setVisible(false);

        enter = new JButton("Enter");
        enter.setFont(mediumText);
        enter.addActionListener(this);
        topPanel.add(enter);

        back = new JButton("Back");
        back.setFont(mediumText);
        back.addActionListener(this);
        topPanel.add(back);
        back.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Initializes bottom panel and text area
    private void initializeTextArea() {
        botPanel = new JPanel();
        this.add(botPanel, BorderLayout.CENTER);

        textArea = new JTextArea("No categories to display");
        textArea.setFont(mediumText);
        textArea.setColumns(60);
        botPanel.add(textArea);

    }

    // MODIFIES: this
    // EFFECTS: Instantiates the doggo Easter egg image
    private void initializeEasterEgg() {
        try {
            doggo = ImageIO.read(new File(IMAGE));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        dogPanel = new JLabel(new ImageIcon(doggo));
        this.add(dogPanel, BorderLayout.SOUTH);
        dogPanel.setVisible(false);
    }

    // COMMAND ITEMS
    // =================================================================================

    // MODIFIES: this
    // EFFECTS: Listens to events from buttons and other components in the Frame for ActionEvent
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(save)) {
            saveDiary();
        } else if (e.getSource().equals(load)) {
            loadDiary();
        } else if (e.getSource().equals(create)) {
            create();
        } else if (e.getSource().equals(delete)) {
            delete();
        } else if (e.getSource().equals(enter)) {
            enter();
        } else {
            actionPerformedTwo(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: Listens to additional events from buttons and other components for ActionEvent
    private void actionPerformedTwo(ActionEvent e) {
        if (e.getSource().equals(back)) {
            back();
        } else if (e.getSource().equals(setTitle)) {
            setTitle();
        } else if (e.getSource().equals(setName)) {
            setName();
        } else if (e.getSource().equals(setStats)) {
            setStats();
        } else if (e.getSource().equals(setDescription)) {
            setDescription();
        } else if (e.getSource().equals(addDrop)) {
            addDrop();
        } else if (e.getSource().equals(removeDrop)) {
            removeDrop();
        } else if (e.getActionCommand().equals(easterCode)) {
            easterEggToggle();
        } else {
            updateInput(e.getActionCommand());
        }
    }

    // REQUIRES: currentCategory is not null
    // MODIFIES: this
    // EFFECTS: Creates a new category or mob depending on the current layer
    private void create() {
        if (currentMenu == 0) { // Diary -> Create Category
            diary.addCategory(currentInput);
            updateDiaryView();
            textField.setText("New Category " + currentInput + " added!");
        } else { // Category -> Create Mob
            currentCategory.addMob(currentInput, "", "");
            updateCategoryView();
            textField.setText("New Mob " + currentInput + " created!");
        }
    }

    // REQUIRES: currentCategory is not null
    // MODIFIES: this
    // EFFECTS: Deletes a new category or mob depending on the current layer
    private void delete() {
        if (currentMenu == 0) { // Diary -> Delete Category
            diary.removeCategory(currentInput);
            updateDiaryView();
            textField.setText("Removed Category " + currentInput + "!");
        } else { // Category -> Delete Mob
            currentCategory.removeMob(currentInput);
            updateCategoryView();
            textField.setText("Removed Mob " + currentInput + "!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters a new category or mob depending on the current layer
    private void enter() {
        if (currentMenu == 0) { // Diary -> Category
            currentCategory = findCategory();
            if (currentCategory == null) {
                textField.setText("Error: no category selected!");
            } else {
                enterCategoryView();
                updateCategoryView();
                currentInput = "";
            }
        } else { // Category -> Mob
            currentMob = findMob();
            if (currentMob == null) {
                textField.setText("Error: no mob selected!");
            } else {
                enterMobView();
                updateMobView();
                currentInput = "";
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Backs up to a diary or category view depending on the current layer
    private void back() {
        if (currentMenu == 1) { // Category -> Diary
            enterDiaryView();
            updateDiaryView();
            currentCategory = null;
        } else { // Mob -> Category
            enterCategoryView();
            updateCategoryView();
            currentMob = null;
        }
        currentInput = "";
    }

    // REQUIRES: currentCategory != null
    // MODIFIES: this
    // EFFECTS: Updates the currentCategory title and display
    private void setTitle() {
        currentCategory.setTitle(currentInput);
        textField.setText("Updated category title!");
        updateCategoryView();
    }

    // REQUIRES: currentMob != null
    // MODIFIES: this
    // EFFECTS: Updates the currentMob name and display
    private void setName() {
        currentMob.setName(currentInput);
        textField.setText("Updated mob name!");
        updateMobView();
    }

    // REQUIRES: currentMob != null
    // MODIFIES: this
    // EFFECTS: Updates the currentMob stats and display
    private void setStats() {
        currentMob.setStats(currentInput);
        textField.setText("Updated mob stats!");
        updateMobView();
    }

    // REQUIRES: currentMob != null
    // MODIFIES: this
    // EFFECTS: Updates the currentMob description and display
    private void setDescription() {
        currentMob.setDescription(currentInput);
        textField.setText("Updated mob description!");
        updateMobView();
    }

    // REQUIRES: currentMob != null
    // MODIFIES: this
    // EFFECTS: Adds a drop to the current mob and updates display
    private void addDrop() {
        currentMob.addDrop(currentInput);
        textField.setText("Added new mob drop " + currentInput);
        updateMobView();
    }

    // REQUIRES: currentMob != null
    // MODIFIES: this
    // EFFECTS: Removes a drop from the current mob and updates display
    private void removeDrop() {
        currentMob.removeDrop(currentInput);
        textField.setText("Removed mob drop " + currentInput);
        updateMobView();
    }

    // MODIFIES: this
    // EFFECTS: Toggles the Easter egg on or off, updates textField
    private void easterEggToggle() {
        dogPanel.setVisible(!dogPanel.isVisible());
        textField.setText("Oh ho ho, you've found my secret!");
        boolean secretExists = false;
        for (Category c : diary.getCategories()) {
            if (c.getTitle().equals("Secret")) {
                secretExists = true;
                break;
            }
        }
        if (!secretExists) {
            Category secret = new Category("Secret");
            Mob tobs = new Mob("Tobs", "A good boy!", "Very cute dog");
            tobs.addDrop("Well done steak!");
            tobs.addDrop("T-Bone");
            secret.addMob(tobs);
            diary.addCategory(secret);
        }
        if (currentMenu == 0) {
            updateDiaryView();
        }
    }

    // HELPER FUNCTIONS
    // =================================================================================

    // MODIFIES: this
    // EFFECTS: Updates the current input and updates a feedback message to the user
    private void updateInput(String input) {
        if (currentMenu == 0) { // Diary, Enter category name
            textField.setText("Category with title " + input + " inputted!");
        } else { // Category or mob, input can be many things
            textField.setText("Input " + input + " received!");
        }
        currentInput = input;
    }

    // EFFECTS: returns a pointer to category if found from title, null otherwise
    private Category findCategory() {
        for (Category c : diary.getCategories()) {
            if (c.getTitle().equals(currentInput)) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: currentCategory cannot be null
    // EFFECTS: returns a pointer to mob if found by name from currentCategory, null otherwise
    private Mob findMob() {
        for (Mob m : currentCategory.getMobs()) {
            if (m.getName().equals(currentInput)) {
                return m;
            }
        }
        return null;
    }

    // UTILITY
    // =================================================================================

    // MODIFIES: this
    // EFFECTS: Updates topPanel components to be in Diary view
    private void enterDiaryView() {
        menu.setText("Diary View");
        textField.setText("Enter a category title to start!");
        save.setVisible(true);
        load.setVisible(true);
        setTitle.setVisible(false);
        back.setVisible(false);
        currentMenu = 0;
    }

    // MODIFIES: this
    // EFFECTS: Updates botPanel textArea with all categories in this diary
    private void updateDiaryView() {
        String categoryList = "Available Categories:\n\n";
        for (Category c : diary.getCategories()) {
            categoryList += "- " + c.getTitle() + "\n\n";
        }
        if (categoryList.equals("Available Categories:\n\n")) {
            categoryList = "No Categories To Show!";
        }
        textArea.setText(categoryList);
    }

    // MODIFIES: this
    // EFFECTS: Updates topPanel components to be in Category view
    private void enterCategoryView() {
        textField.setText("Enter a mob name to start!");
        save.setVisible(false);
        load.setVisible(false);
        create.setVisible(true);
        delete.setVisible(true);
        enter.setVisible(true);
        setTitle.setVisible(true);
        back.setVisible(true);
        setName.setVisible(false);
        setStats.setVisible(false);
        setDescription.setVisible(false);
        addDrop.setVisible(false);
        removeDrop.setVisible(false);
        currentMenu = 1;
    }

    // MODIFIES: this
    // EFFECTS: Updates botPanel textArea with all mobs in this category
    private void updateCategoryView() {
        menu.setText("Category View: " + currentCategory.getTitle());
        String mobList = "Available Mobs:\n\n";
        for (Mob m : currentCategory.getMobs()) {
            mobList += "- " + m.getName() + "\n\n";
        }
        if (mobList.equals("Available Mobs:\n\n")) {
            mobList = "No Mobs To Show!";
        }
        textArea.setText(mobList);
    }

    // MODIFIES: this
    // EFFECTS: Updates topPanel components to be in Mob view
    private void enterMobView() {
        create.setVisible(false);
        delete.setVisible(false);
        enter.setVisible(false);
        setTitle.setVisible(false);
        setName.setVisible(true);
        setStats.setVisible(true);
        setDescription.setVisible(true);
        addDrop.setVisible(true);
        removeDrop.setVisible(true);
        currentMenu = 2;
    }

    // MODIFIES: this
    // EFFECTS: Updates botPanel textArea with the mob information for the current mob
    private void updateMobView() {
        menu.setText("Mob View: " + currentMob.getName());
        String mobInfo = "Mob information for: " + currentMob.getName() + "\n\n";
        mobInfo += "Description: " + currentMob.getDescription() + "\n\n";
        mobInfo += "Stats: " + currentMob.getStats() + "\n\n";
        mobInfo += "Drops: " + currentMob.getDrops();
        textArea.setText(mobInfo);
    }

    // EFFECTS: saves the diary to a file
    private void saveDiary() {
        try {
            jsonWriter.open();
            jsonWriter.write(diary);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file " + JSON_STORE);
        }
        textField.setText("Diary saved!");
    }

    // MODIFIES: this
    // EFFECTS: loads the diary from a file
    private void loadDiary() {
        try {
            diary = jsonReader.read();
            currentCategory = null;
            currentMob = null;
            currentInput = "";
        } catch (IOException e) {
            System.out.println("Could not find file " + JSON_STORE);
        }
        updateDiaryView();
        dogPanel.setVisible(false);
        textField.setText("Diary loaded!");
    }
}
