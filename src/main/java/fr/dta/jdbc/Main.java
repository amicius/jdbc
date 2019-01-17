package fr.dta.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/jdbc";
		try (Connection co = DriverManager.getConnection(url, "formation", "abcdewxyz")) {
			co.setAutoCommit(false);

			try (Statement query = co.createStatement()) {
				Client client1 = new Client("Aria", "Asigard");
				Client client2 = new Client("Freya", "Asigard", Gender.F);
				Book book1 = new Book("Douluo Dalu : Combat continent","Tang jia san shao");
				Book book2 = new Book("Douluo Dalu 2 : Unrivalled Tang Sect","Tang jia san shao");
				

				query.executeUpdate("DROP TABLE IF EXISTS client, book, buy");
				query.executeUpdate("CREATE TABLE book(id serial PRIMARY KEY, " + "title varchar(255) NOT NULL, "
						+ "author varchar(255))");
				query.executeUpdate("CREATE TABLE client(id serial primary key, " + "lastname varchar(255) NOT NULL, "
						+ "firstname varchar(255) NOT NULL, gender varchar(1), favorite int, "
						+ "constraint favorite_book_fk foreign key (favorite) references book(id) )");
				query.executeUpdate("create table buy(id serial primary key, " + "id_client int ,id_book int , "
						+ "constraint buy_client_fk foreign key (id_client) references client(id), "
						+ "constraint buy_book_fk foreign key (id_client) references book(id)) ");
				
				System.out.println("création OK");
			//try {
				Services.createBook(co, book1 );
				Services.createBook(co, book2);
				Services.createBook(co, new Book("Douluo Dalu 3 : The Legend of The Dragon King","Tang jia san shao"));
				Services.createBook(co, new Book("Tales of Demons and Gods","Jiang Ruotai"));
				System.out.println("Ajout Book Ok");
				
				Services.createClient(co, client1);
				Services.createClient(co, client2);
				Services.createClient(co, new Client("Amicius", "Asigard", Gender.M));
				System.out.println("Ajout client OK");
				
				Services.purchases(co, client1, book1);
				Services.purchases(co, client1, book2);
				Services.purchases(co, client1, book1);
				Services.purchases(co, client2, book1);
				
				System.out.println("Ajout achat OK");
				
				System.out.println("liste livre achete par le client 1"); 
				List<Book> books = Services.clientPurchases(co, client1);
				for(Book book :books) {
					System.out.println(book.toString());
				}
				System.out.println("liste client ayant achete le livre 1");
				List<Client> clients = Services.purchasedBy(co, book1);
				for(Client client :clients) {
					System.out.println(client.toString());
				}
				
				System.out.println("liste client ayant achete au moins un livre");
				clients = Services.havePurhcased(co);
				for(Client client :clients) {
					System.out.println(client.toString());
				}
				
				co.commit();
			} catch (Exception e) {
				e.printStackTrace();
				co.rollback();
			} finally {
				co.setAutoCommit(true);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
