package com.skilldistillery.filmquery.database;

import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

  @Override
  public Film findFilmById(int filmId) {
	 Film film = null;
	 
	  
	  
	  
	  /* 
	  example from class:
	  public Actor findActorById(int actorId) {
		  Actor actor = null;
		  //...
		  String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		  PreparedStatement stmt = conn.prepareStatement(sql);
		  stmt.setInt(1,actorId);
		  ResultSet actorResult = stmt.executeQuery();
		  if (actorResult.next()) {
		    actor = new Actor(); // Create the object
		    // Here is our mapping of query columns to our object fields:
		    actor.setId(actorResult.getInt(1));
		    actor.setFirstName(actorResult.getString(2));
		    actor.setLastName(actorResult.getString(3));
		    actor.setFilms(findFilmsByActorId(actorId)); // An Actor has Films
		  }
		  //...
		  return actor;
		}
	  */
    return null;
  }

}
