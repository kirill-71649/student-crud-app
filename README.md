# Book Catalogue CRUD

A small Java application for the Git and GitHub assignment. It uses Spring Boot, Vaadin, Spring Data JPA and an in-memory H2 database. The interface supports creating, viewing, editing and deleting books, as well as searching by title or author.

## Requirements

- Java JDK 21
- IntelliJ IDEA or another Java IDE
- Internet access on the first run so Maven can download dependencies

## Run in IntelliJ IDEA

1. Open this folder as a project.
2. When IntelliJ asks for a JDK, select or download JDK 21.
3. Wait for Maven to import the dependencies.
4. Open `StudentCrudAppApplication.java` and run `main`.
5. Open <http://localhost:8080> in a browser.

The application starts with three sample books. Changes are stored in memory and reset when the application restarts.

## Main parts of the project

- `Book` is the database entity.
- `BookRepository` provides database operations through Spring Data JPA.
- `MainView` defines the Vaadin screen and CRUD actions.
- `StudentCrudAppApplication` starts Spring Boot and adds sample data.

## Assignment evidence

The formatted evidence report, original screenshots and genuine startup-log excerpt are in [`doc/`](doc/README.md).
