# Film Query Project
The FilmQueryProject is a dynamic Java application that provides users with an interactive experience to explore a film database. Utilizing Java's JDBC API for database connectivity, this project demonstrates effective data retrieval techniques in a user-friendly command-line interface. It's an ideal showcase of integrating Java with a MySQL database, highlighting key concepts in database manipulation and user interaction.


## Description
The FilmQueryProject is a Java-based command-line application designed to interact with a MySQL database containing film data. This application allows users to query the database to retrieve information about films based on specific criteria.

**Key Features:**

* **Search by Film ID:** Users can look up a film by its unique identifier to access detailed information about the film.
* **Keyword Search:** The application provides the functionality to search for films using keywords. This feature searches through film titles and descriptions, returning relevant results.
* **Detailed Film Information:** For each film, the application displays information such as title, description, release year, language, rating, and a list of actors.
* **Submenu for Additional Details:** When viewing a film's basic details, users can access a submenu to view more comprehensive information, including the film's category, inventory status, and more.

This project is an excellent example of implementing Java's JDBC API for database connectivity, showcasing CRUD (Create, Read, Update, Delete) operations in a relational database environment. The application's focus on user interaction via the command line demonstrates handling input and output in a text-based interface.

## Technologies Used

* **Java:** Core language for building the application.
* **Maven:** Used for project build automation and dependency management. The project is configured to use Maven for:
    * Automating the build process, ensuring consistent builds across environments.
    * Managing dependencies, notably the MySQL JDBC driver for database connectivity.
    * Compiling the project using Java.
* **MySQL:** Utilized for database management and storage, with connectivity managed through the MySQL JDBC driver. Serves as the database for storing and managing film data. The database schema would include tables for films, actors, categories, and other related data.
* **JDBC API:** Java Database Connectivity (JDBC) API used for connecting to the MySQL database from the Java application. Facilitates communication between the Java application and the MySQL database. Through JDBC, the application queries the database and retrieves information based on user inputs, such as searching for films by ID or keyword.
* **Git/GitHub:** For version control and source code management.
* **IDE:** Eclipse.

## Lessons Learned
* **Database Connectivity:** Learned how to connect a Java application to a MySQL database using JDBC and perform CRUD operations.
* **SQL Proficiency:** Enhanced understanding of SQL queries for data retrieval and manipulation.
* **Error Handling:** Gained experience in handling SQL exceptions and user input validation in Java.
* **Object-Oriented Programming:** Applied OOP concepts such as encapsulation and abstraction to structure the application effectively.
* **Code Modularity:** Learned the importance of writing modular code and separating concerns for easier maintenance and scalability.
* **User Interface Design:** Developed skills in creating user-friendly command-line interfaces and understanding user interaction flows.