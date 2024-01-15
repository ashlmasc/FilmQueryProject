package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);
		boolean keepRunning = true;

		startUserInterface(input);

		while (keepRunning) {
			displayMenu();
			int userChoice = getUserChoice(input);

			switch (userChoice) {
			case 1:
				lookupFilmById(input);
				break;
			case 2:
				lookupFilmByKeyword(input);
				break;
			case 3:

				keepRunning = false;
				break;
			default:
				System.out.println("Invalid option. Please try again");
				break;
			}

		}

		input.close();
		System.out.println("Application exited. Goodbye!");
	}

	private void startUserInterface(Scanner input) {

	}

	private void displayMenu() {
		System.out.println("=========================================");
	    System.out.println("|              Film Query               |");
	    System.out.println("=========================================");
	    System.out.println("| Options:                              |");
	    System.out.println("|   1. Lookup a film by its ID          |");
	    System.out.println("|   2. Lookup a film by a search keyword|");
	    System.out.println("|   3. Exit                             |");
	    System.out.println("=========================================");
		
	}
	
	private void showFilmSubmenu(Scanner input, Film film) {
		System.out.println("---------------------------------");
		System.out.println("|        Sub-Menu Options       |");
		System.out.println("---------------------------------");
	    System.out.println("|   1. Return to main menu      |");
	    System.out.println("|   2. View all film details    |");
	    System.out.println("---------------------------------");
	    int choice = getValidIntegerInput(input, "Enter your choice: ");

	    switch (choice) {
	        case 1:
	            // Return to main menu
	            break;
	        case 2:
	            // Show all details of the film
	            displayFilmDetails(film, true);
	            break;
	        default:
	            System.out.println("Invalid option. Please try again.");
	            showFilmSubmenu(input, film); // Re-prompt if invalid option
	            break;
	    }
	}

	private int getUserChoice(Scanner input) {
		return getValidIntegerInput(input, "Enter your choice: ");
	}

	private void lookupFilmById(Scanner input) {
		int filmId = getValidIntegerInput(input, "Enter the film ID: ");

		Film film = new Film();
		try {
			film = db.findFilmById(filmId);
			if (film != null) {
	            displayFilmDetails(film, false); // Initially show basic details
	            showFilmSubmenu(input, film); // Show submenu for more options
	        } else {
	            System.out.println("Film not found.");
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error occurred while accessing the database.");
		}
	}

	private void lookupFilmByKeyword(Scanner input) throws SQLException {
		System.out.println("Enter a search keyword: ");
		String keyword = input.nextLine();

		List<Film> films = db.findFilmByKeyword(keyword);
		if (films.isEmpty()) {
			System.out.println("No films found with the keyword: " + keyword);
		} else {
			for (Film film : films) {
			    displayFilmDetails(film, false);
			    System.out.println(); 
			}
		}
	}
	
	private void displayFilmDetails(Film film, boolean showAllDetails) {
	    if (showAllDetails) {
	        // Print all details of the film
	        System.out.println(film);
	        System.out.println("Inventory Items:");
	        List<InventoryItem> inventoryItems = film.getInventoryItems();
	        if (inventoryItems != null && !inventoryItems.isEmpty()) {
	            for (InventoryItem item : inventoryItems) {
	                System.out.println("\t" + item);
	            }
	        } else {
	            System.out.println("\tNo inventory items found");
	        }
	        System.out.println();
	        System.out.println();
	    } else {
	        // Print basic details of the film
	        System.out.println("Title: " + film.getTitle());
	        System.out.println("Year: " + film.getReleaseYear());
	        System.out.println("Rating: " + film.getRating());
	        System.out.println("Description: " + film.getDescription());
	        System.out.println("Language: " + film.getLanguage());
	        System.out.print("Cast:");
	        if (film.getActors() != null && !film.getActors().isEmpty()) {
	            for (Actor actor : film.getActors()) {
	                System.out.print(actor); // uses the Actor class's toString() method but gets rid of commas and brackets
	            }
	        } else {
	            System.out.println("\tNo actors found");
	        }
	        System.out.println();
	        System.out.println();
	        System.out.println();
	    }
	}
	
	private int getValidIntegerInput(Scanner input, String promptMessage) {
	    System.out.print(promptMessage);
	    while (!input.hasNextInt()) {
	        System.out.println("Invalid input. Please enter a numeric value.");
	        input.next(); 
	        System.out.print(promptMessage);
	    }
	    int validInput = input.nextInt();
	    input.nextLine(); 
	    return validInput;
	}
}
