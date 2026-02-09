# The Forgotten Altar of Verdea

## Project overview

**The Forgotten Altar of Verdea** is a 2D adventure RPG game developed
in Java as part of the PAOO (Object-Oriented Programming Advanced)
project.\
The game combines exploration, farming simulation, combat mechanics, and
story-driven gameplay.

The player controls **Ryo**, a young adventurer who must restore a
magical altar and protect the village of Verdea from invading goblins.

This project follows the design and implementation requirements
described in the official documentation:
*[Echipa04_Etapa01-03_ProiectPAOO.pdf](Echipa04_Etapa01-03_ProiectPAOO.pdf)*

------------------------------------------------------------------------

## Game features

-    Farming and resource management
-    Combat system with enemies (Goblins, Boss)
-    Multiple levels and maps
-    Inventory and item system
-    Save & Load system using a database
-    Keyboard input and camera system
-    Graphical User Interface (menus, HUD)

------------------------------------------------------------------------

## Project structure
The project respects a layered architecture separating resources, game logic, rendering, and persistence modules.

    ProjectRoot/
    │
    ├── res/                       # Game resources (assets)
    │   ├── maps/                  # Map files
    │   ├── monster/               # Enemy sprites
    │   ├── npc/                   # NPC sprites
    │   ├── objects/               # Items and object sprites
    │   ├── player/                # Player sprites
    │   ├── sound/                 # Audio files
    │   ├── tiles/                 # Tile sprites
    │   └── titles/                # Title/menu images
    │
    ├── src/
    │   └── paoo/game/             # Main Java package
    │       ├── database/          # Database connection & save system
    │       ├── entity/            # Player, enemies, NPC logic
    │       ├── handler/           # Input and event handlers
    │       ├── main/              # Main class & game launcher
    │       ├── monster/           # Monster AI and behavior
    │       ├── object/            # Game objects and items
    │       ├── panel/             # GamePanel, rendering loop
    │       ├── tile/              # Tile and map management
    │       └── util/              # Utility/helper classes
    │
    ├── Echipa04_Etapa01-03_ProiectPAOO.pdf     # Project documentation
    ├── game.db                    # SQLite database for saving game progress
    │
    └──README.md

------------------------------------------------------------------------

##  Technologies used

-   Java SE
-   Java Swing / AWT (for rendering and UI)
-   Object-Oriented Programming (OOP)
-   Design Patterns:
    -   Singleton (GamePanel)
    -   Factory Method (AssetSetter)
    -   Flyweight (Tile management)
-   Relational Database (for saving game progress)
-   UML Diagrams & Software Architecture Documentation

------------------------------------------------------------------------

##  Documentation

The project is fully documented in the PDF:

*[Echipa04_Etapa01-03_ProiectPAOO.pdf](Echipa04_Etapa01-03_ProiectPAOO.pdf)*

The documentation includes: - Game design and story
- Gameplay mechanics
- Level design
- UML diagrams
- System architecture
- Database design
- Testing strategy

### Javadoc

The source code is fully documented using **Javadoc**, providing detailed descriptions for classes, methods, and packages.  
This documentation helps understand the architecture, responsibilities of each module, and the interaction between game components.

The generated Javadoc HTML pages can be accessed from the following entry point: 
**[Javadoc Main Page](Documentatie%20JavaDoc/)**


------------------------------------------------------------------------

##  How to Run the Game

1.  Open the project in IntelliJ IDEA / Eclipse / NetBeans
2.  Make sure Java is installed (JDK 8+ recommended)
3.  Run the `Main.java` file
4.  Use keyboard controls to move and interact

------------------------------------------------------------------------

## Authors

-   Solomon Anastasia
-   Diaconu Adina-Iuliana

Group: 1212B\
Project Team: 04

------------------------------------------------------------------------

## License

This project is developed for educational purposes as part of the PAOO
course.
