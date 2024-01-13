package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

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
		System.out.println("---------------------------------------------");
		System.out.println("|                Menu Options               |");
		System.out.println("---------------------------------------------");
		System.out.println("|  1. Lookup a film by its ID               |");
		System.out.println("|  2. Lookup a film by a search keyword     |");
		System.out.println("|  3. Exit the application                  |");
		System.out.println("---------------------------------------------");
		System.out.println("Enter your choice: ");
	}
	
	private void showFilmSubmenu(Scanner input, Film film) {
	    System.out.println("1. Return to main menu");
	    System.out.println("2. View all film details");
	    System.out.print("Enter your choice: ");

	    int choice = input.nextInt();
	    input.nextLine();

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
		int choice = input.nextInt();
		input.nextLine();
		return choice;
	}

	private void lookupFilmById(Scanner input) {
		System.out.print("Enter the film ID: ");
		int filmId = input.nextInt();
		input.nextLine();

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
	    } else {
	        // Print basic details of the film
	        System.out.println("Title: " + film.getTitle());
	        System.out.println("Year: " + film.getReleaseYear());
	        System.out.println("Rating: " + film.getRating());
	        System.out.println("Description: " + film.getDescription());
	        System.out.println("Language: " + film.getLanguage());
	        System.out.println("Cast: " + film.getActors());
	       
	    }
	}
}
