# Developer's Mob Diary

Ideas are hard to keep track of, especially when you have a lot of them!<br>
This diary is a tool for role-playing game developers to organize their monster ideas,<br>
providing an easy way to categorize, edit, and view your creations.

As someone who wants to create a role-playing game for friends,<br>
I've always had some trouble with keeping track of everything on sheets of scrap paper.<br>
Hopefully, this interactive diary will help make creative process of game design a tad bit easier!

## User Stories

The target audience will be developers:
- As a user, I want to be able to **add** multiple categories to a list of categories
- As a user, I want to be able to **remove** a category from a list of categories
- As a user, I want to be able to **edit** a category in a list of categories
- As a user, I want to be able to **view** all categories in a list of categories
- As a user, I want to be able to **select** a category in a list of categories and view it
- As a user, I want to be able to **add** multiple mobs to a category
- As a user, I want to be able to **edit** a mob once I select it
- As a user, I want to be able to **remove** a mob from a category
- As a user, I want to be able to **edit** a mob in a category
- As a user, I want to be able to **select** a mob in a category and view it

## Phase 2 user stories:
- As a user, I want to be able to **save** all my categories and mobs to a file
- As a user, I want to be able to **load** all my categories and mobs from a file

## Phase 4: Task 2

Tue Mar 29 15:01:17 PDT 2022\
Diary Loaded\
Tue Mar 29 15:01:17 PDT 2022\
Added drop Stick to mob Tree\
Tue Mar 29 15:01:17 PDT 2022\
Added mob Tree to category Woodlands\
Tue Mar 29 15:01:17 PDT 2022\
Added new category Woodlands\
Tue Mar 29 15:01:17 PDT 2022\
Added new category Ocean\
Tue Mar 29 15:01:17 PDT 2022\
Added mob Goat to category Mountain\
Tue Mar 29 15:01:17 PDT 2022\
Added drop Scales to mob Dragon\
Tue Mar 29 15:01:17 PDT 2022\
Added drop Dragon Meat to mob Dragon\
Tue Mar 29 15:01:17 PDT 2022\
Added mob Dragon to category Mountain\
Tue Mar 29 15:01:17 PDT 2022\
Added new category Mountain\
Tue Mar 29 15:01:22 PDT 2022\
Removed category Woodlands\
Tue Mar 29 15:01:30 PDT 2022\
Added new category Sealite\
Tue Mar 29 15:01:44 PDT 2022\
Changed category name from Mountain to Caves\
Tue Mar 29 15:02:00 PDT 2022\
Changed Dragon stats to Spooky\
Tue Mar 29 15:02:07 PDT 2022\
Removed drop Scales from mob Dragon\
Tue Mar 29 15:02:13 PDT 2022\
Added drop Dragon Skin to mob Dragon\
Tue Mar 29 15:02:30 PDT 2022\
Changed mob name Dragon to Wyvern

## Phase 4: Task 3

One of the things that stood out to me when looking back at my UML diagram<br>
is the triple reference from my DiaryApplication class in my ui package to the<br>
Diary, Category, and Mob classes in my model package.<br>

The Diary, Category, and Mob classes could perhaps be reduced by the Composite pattern<br>
to one class where the Composite is a Category and the Leaf is a Mob.<br>
The new structure would resemble a file system and allow for sub-categories.<br>

The Diary class would then no longer be needed, and the DiaryApplication would<br>
only need to reference one parent Category that functions as the former Diary class.<br>

In point form:

- Remove Diary class
- Apply the Composite pattern to Category (Composite) and Mob (Leaf)
- DiaryApplication would now only reference one Category

Outside these changes, my DiaryApplication class is a quite a mess and would look much<br>
better with some proper refactoring. Some tasks, such as event handling and UI switching,<br>
would likely look a lot better if they were in separate classes.<br>

Currently, my buttons hide themselves when not in use and reveal themselves when in use.<br>
I think I would prefer a design that made a separate panel for each view: Diary, Mob, and Category.<br>
Separating the panels from the main app makes it much easier to edit and expand in the future.

