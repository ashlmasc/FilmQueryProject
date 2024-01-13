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
		System.out.println("|  1. Lookup a film by its ID              |");
		System.out.println("|  2. Lookup a film by a search keyword    |");
		System.out.println("|  3. Exit the application                  |");
		System.out.println("---------------------------------------------");
		System.out.println("Enter your choice: ");
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (film != null) {
			System.out.println(film);
		} else {
			System.out.println("Film not found.");
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
				System.out.println(film);
			}
		}
	}
}
