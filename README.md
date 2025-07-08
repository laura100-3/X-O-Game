X and O Game with Among Us Theme

A classic Tic Tac Toe game with an Among Us theme featuring Crewmate and Impostor characters as the X and O pieces.

        Overview
This is a Java Swing implementation of the classic Tic Tac Toe game with a twist - it features an Among Us theme where players can choose to be either a Crewmate (green X) or an Impostor (red O). The game includes a welcome screen for name entry, animated space-themed backgrounds, and full game functionality.

  Features
- Among Us Theme: Green Crewmate and Red Impostor characters as game pieces
- Animated Background: space-themed with subtle animations
- Name Entry: Players can enter their names before starting the game
- Game Logic: Complete Tic Tac Toe implementation with win/draw detection
- User Interface: Clean, modern design with Among Us-inspired elements

   How to Play
1. Enter names for the Crewmate and Impostor players
2. Click "Start Game" to begin
3. Players take turns placing their characters on the 3x3 grid
4. The first player to get 3 in a row (horizontally, vertically, or diagonally) wins
5. If the board fills up with no winner, the game is a draw
6. After each game, you'll return to the welcome screen to play again

 Code Structure
The code is organized into several key components:

  Main Class: `XOGame`
- Entry point of the application
- Contains the main method that launches the welcome screen

  WelcomeScreen Class
- Displays the welcome screen with name entry fields
- Sets up the "Start Game" button with event handling
- Transitions to the game screen when the game starts

  GameScreen Class
- Contains the main game board and logic
- Handles player turns and game piece placement
- Implements win/draw detection
- Manages the game flow and UI updates

 Helper Methods
- `drawAmongUsBackground()`: Creates the space-themed background
- `createAmongUsIcon()`: Generates the Crewmate and Impostor character icons
- `checkWinner()`: Checks for winning combinations
- `isBoardFull()`: Detects draw conditions

 Visual Elements
- Background: Animated space background with stars and subtle Among Us elements
- Game Pieces: Green Crewmate (X) and Red Impostor (O) characters
- UI Components: Clean, modern interface with proper spacing and colors
- Status Display: Shows current player's turn and game results

Enjoy playing X and O with your favorite Among Us characters! 🚀
