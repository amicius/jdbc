package fr.dta.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Services {
	public static void createBook(Connection co, Book book) {

		try (PreparedStatement query = co.prepareStatement("INSERT INTO book(title, author) VALUES(?, ?);",
				Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, book.getTitle());
			query.setString(2, book.getAuthor());
			query.executeUpdate();
			ResultSet generatedKeys = query.getGeneratedKeys();
			generatedKeys.next();
			book.setId(generatedKeys.getInt("id"));
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void createClient(Connection co, Client client) {

		try (PreparedStatement query = co.prepareStatement(
				"INSERT INTO client(lastname, firstname, gender, favorite) VALUES(?, ?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, client.getlastname());
			query.setString(2, client.getfirstname());
			if (client.getGender() != null) {
				query.setString(3, client.getGender().toString());
			} else {
				query.setString(3, null);
			}
			if (client.getFavorite() != 0) {
				query.setLong(4, 1);// client.getFavorite());
			} else {
				query.setLong(4, 1);
			}
			query.executeUpdate();

			ResultSet generatedKeys = query.getGeneratedKeys();
			generatedKeys.next();
			client.setId(generatedKeys.getInt("id"));
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<Book> clientPurchases(Connection co, Client client) {
		List<Book> books = new ArrayList<>();
		try (PreparedStatement query = co.prepareStatement(
				"select book.title, book.author from book inner join buy on book.id = buy.id_book where buy.id_client = ?;")) {
			query.setInt(1, client.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				books.add(new Book(result.getString(1), result.getString(2)));
			}
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	public static void purchases(Connection co, Client client, Book book) {
		try (PreparedStatement query = co.prepareStatement("Insert into buy(id_book,id_client) values (?,?)")) {
			query.setInt(1, book.getId());
			query.setInt(2, client.getId());
			query.executeUpdate();
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Client> purchasedBy(Connection co, Book book) {
		List<Client> clients = new ArrayList<>();
		try (PreparedStatement query = co.prepareStatement(
				"select client.firstname, client.lastname from client inner join buy on client.id = buy.id_client where buy.id_book = ?;")) {
			query.setInt(1, book.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				clients.add(new Client(result.getString(1), result.getString(2)));
			}
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}
	
	public static List<Client> havePurhcased(Connection co){
		List<Client> clients = new ArrayList<>();
		try (PreparedStatement query = co.prepareStatement(
				"select distinct client.firstname, client.lastname from client inner join buy on client.id = buy.id_client;")) {
			
			ResultSet result = query.executeQuery();
			while (result.next()) {
				clients.add(new Client(result.getString(1), result.getString(2)));
			}
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return clients;
	}

}
