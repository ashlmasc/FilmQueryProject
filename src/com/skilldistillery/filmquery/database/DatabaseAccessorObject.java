package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String USER = "student";
	private static final String PWD = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;

		Connection connection = DriverManager.getConnection(URL, USER, PWD);

		String sql = "SELECT film.*, language.name AS language_name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, filmId);
		ResultSet filmResult = statement.executeQuery();
		if (filmResult.next()) {
			film = new Film();
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getInt("release_year"));
			film.setLanguageId(filmResult.getInt("language_id"));
			film.setRentalDuration(filmResult.getInt("rental_duration"));
			film.setRentalRate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getInt("length"));
			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecialFeatures(filmResult.getString("special_features"));
			
			film.setActors(findActorsByFilmId(filmId));
			
            List<String> categories = findFilmCategories(filmId);
            film.setCategories(categories);
            
            film.setLanguage(filmResult.getString("language_name"));
            
            List<InventoryItem> inventoryItems = findInventoryByFilmId(filmId);
            film.setInventoryItems(inventoryItems);
		}
		filmResult.close();
		statement.close();
		connection.close();
		return film;
	}

	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;

		Connection connection = DriverManager.getConnection(URL, USER, PWD);

		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, actorId);
		ResultSet actorResult = statement.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
					actorResult.getString("last_name")); 
		}
		actorResult.close();
		statement.close();
		connection.close();
		return actor;
	}

	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<>();

		Connection connection = DriverManager.getConnection(URL, USER, PWD);

		String sql = "SELECT actor.id, actor.first_name, actor.last_name "
				+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id " + "WHERE film_actor.film_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, filmId);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Actor actor = new Actor(resultSet.getInt("id"), resultSet.getString("first_name"),
					resultSet.getString("last_name"));
			actors.add(actor);
		}
		resultSet.close();
		statement.close();
		connection.close();
		return actors;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) throws SQLException{
		List<Film> films = new ArrayList<>();
		Connection connection = DriverManager.getConnection(URL, USER, PWD);
		String sql = "SELECT * FROM film WHERE LOWER(title) LIKE ? OR LOWER(description) LIKE ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			String searchKeyword = "%" + keyword.toLowerCase() + "%";
			statement.setString(1, searchKeyword);
			statement.setString(2, searchKeyword);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Film film = new Film();
				
					film.setId(resultSet.getInt("id"));
					film.setTitle(resultSet.getString("title"));
					film.setDescription(resultSet.getString("description"));
					film.setReleaseYear(resultSet.getInt("release_year"));
					film.setLanguageId(resultSet.getInt("language_id"));
					film.setRentalDuration(resultSet.getInt("rental_duration"));
					film.setRentalRate(resultSet.getDouble("rental_rate"));
					film.setLength(resultSet.getInt("length"));
					film.setReplacementCost(resultSet.getDouble("replacement_cost"));
					film.setRating(resultSet.getString("rating"));
					film.setSpecialFeatures(resultSet.getString("special_features"));

					film.setActors(findActorsByFilmId(film.getId()));

					films.add(film);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		} 
		return films;
	}

	public List<String> findFilmCategories(int filmId) throws SQLException {
		List<String> categories = new ArrayList<>();
		Connection connection = DriverManager.getConnection(URL, USER, PWD);
		String sql = "SELECT category.name FROM category JOIN film_category ON category.id = film_category.category_id WHERE film_category.film_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, filmId);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			categories.add(resultSet.getString("name"));
		}
		resultSet.close();
		statement.close();
		connection.close();

		return categories;
	}
	
	 public List<InventoryItem> findInventoryByFilmId(int filmId) throws SQLException {
	        List<InventoryItem> inventoryItems = new ArrayList<>();
	        String sql = "SELECT id, media_condition FROM inventory_item WHERE film_id = ?";

	        try (Connection connnnection = DriverManager.getConnection(URL, USER, PWD);
	             PreparedStatement statement = connnnection.prepareStatement(sql)) {
	            
	        	statement.setInt(1, filmId);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String condition = resultSet.getString("media_condition");
	                InventoryItem item = new InventoryItem(id, condition);
	                inventoryItems.add(item);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();  
	        }
	        return inventoryItems;
	    }
}
