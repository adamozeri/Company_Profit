package starter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import controller.Controller;
import exceptions.NameException;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Company;
import view.View;

public class Program extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaStage) throws NameException, Exception {
		Connection conn = getConnection();
		View theView = new View(primaStage);
		Company theModel = new Company(conn);
		Controller controller = new Controller(theModel, theView);
	}
	
	public static Connection getConnection() throws ClassNotFoundException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbUrlString = "jdbc:mysql://localhost:3306/company";
			conn = DriverManager.getConnection(dbUrlString, "root", "adamesti1204");
			return conn;
		}catch (SQLException e) {
			System.out.println("SQLExeption: " + e.getMessage());
		}
		return null;
	}
}
