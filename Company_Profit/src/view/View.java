package view;

import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

import exceptions.NameException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import listeners.ViewListenable;
import model.BasicAndSalesEmployee;
import model.BasicSalaryEmployee;
import model.Department;
import model.HourlySalaryEmployee;
import model.Employee.eEmployeeType;
import model.Preference;
import model.Role;
import model.Preference.eType;

public class View {

	public final int MAX_NUMBER = 1000000;

	private Vector<ViewListenable> allListeners;

	private BorderPane bpMain;

	public View(Stage primaryStage) {
		primaryStage.setTitle("Company Experiment");
		this.allListeners = new Vector<ViewListenable>();
		this.bpMain = new BorderPane();
		this.bpMain.setBackground(
				new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(0), Insets.EMPTY)));

		Scene scene = new Scene(bpMain, 1100, 700);

		// ------------------------------------------------------------- Profit

		BorderPane bpAvgProfit = new BorderPane();
		bpAvgProfit.setBackground(
				new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(0), Insets.EMPTY)));

		VBox vbAvg = new VBox();
		Scene sceneAvgProfit = new Scene(bpAvgProfit, 400, 200);

		Label lblAvarageProfit1 = new Label("What is the Company's avarage");
		Label lblAvarageProfit2 = new Label("profit per employee?");
		lblAvarageProfit1.setFont(Font.font("Calibri", 24));
		lblAvarageProfit2.setFont(Font.font("Calibri", 24));

		TextField tfAvarageProfit = new TextField();

		Button btnSubmit = new Button("Submit");
		btnSubmit.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		vbAvg.setAlignment(Pos.CENTER);
		vbAvg.setSpacing(10);
		vbAvg.setPadding(new Insets(10));
		vbAvg.getChildren().addAll(lblAvarageProfit1, lblAvarageProfit2, tfAvarageProfit, btnSubmit);

		bpAvgProfit.setCenter(vbAvg);

		primaryStage.setScene(sceneAvgProfit);

		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (tfAvarageProfit.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "You must submit a number");
				} else {
					for (ViewListenable l : allListeners) {
						double avgProfit = 0;
						try {
							avgProfit = Double.parseDouble(tfAvarageProfit.getText());
							l.setAvgProfit(avgProfit);
							l.loadFromDB();
							primaryStage.setScene(scene);
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Avarage profit must be numbers");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				}
			}
		});

		Button addEmployee = new Button("Add an Employee");
		addEmployee.setPrefSize(175, 50);
		addEmployee.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		addEmployee.setTextFill(Color.DARKSLATEBLUE);
		Button addRole = new Button("Add a Role");
		addRole.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		addRole.setPrefSize(175, 50);
		addRole.setTextFill(Color.DARKSLATEBLUE);
		Button addDepartment = new Button("Add a Department");
		addDepartment.setPrefSize(175, 50);
		addDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		addDepartment.setTextFill(Color.DARKSLATEBLUE);
		Button showEmployees = new Button("Show all Employees");
		showEmployees.setPrefSize(175, 50);
		showEmployees.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		showEmployees.setTextFill(Color.DARKSLATEBLUE);

		Button showRoles = new Button("Show all Roles");
		showRoles.setPrefSize(175, 50);
		showRoles.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		showRoles.setTextFill(Color.DARKSLATEBLUE);

		Button showDepartments = new Button("Show all Departments");
		showDepartments.setPrefSize(175, 50);
		showDepartments.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		showDepartments.setTextFill(Color.DARKSLATEBLUE);

		Button updateMethodRole = new Button("Update Role");
		updateMethodRole.setPrefSize(175, 50);
		updateMethodRole.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		updateMethodRole.setTextFill(Color.DARKSLATEBLUE);

		Button updateMethodDepartment = new Button("Update Department");
		updateMethodDepartment.setPrefSize(175, 50);
		updateMethodDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		updateMethodDepartment.setTextFill(Color.DARKSLATEBLUE);

		Button showProfit = new Button("Show Profit");
		showProfit.setPrefSize(175, 50);
		showProfit.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		showProfit.setTextFill(Color.DARKSLATEBLUE);

		Button updateMethodEmoloyee = new Button("Update Employee");
		updateMethodEmoloyee.setPrefSize(175, 50);
		updateMethodEmoloyee.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		updateMethodEmoloyee.setTextFill(Color.DARKSLATEBLUE);

		Button deleteMethodEmployee = new Button("Delete Employee");
		deleteMethodEmployee.setPrefSize(175, 50);
		deleteMethodEmployee.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		deleteMethodEmployee.setTextFill(Color.DARKSLATEBLUE);

		Button exit = new Button("Exit");
		exit.setPrefSize(175, 50);
		exit.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		exit.setTextFill(Color.DARKSLATEBLUE);

		VBox vbSelection = new VBox();
		vbSelection.setAlignment(Pos.CENTER_LEFT);
		vbSelection.setSpacing(10);
		vbSelection.setPadding(new Insets(10));
		vbSelection.getChildren().addAll(addEmployee, addRole, addDepartment, showEmployees, showRoles, showDepartments,
				updateMethodRole, updateMethodDepartment, updateMethodEmoloyee, showProfit, deleteMethodEmployee, exit);
		bpMain.setLeft(vbSelection);
		GridPane gpAddEmployee = new GridPane();
		Label lblName = new Label("Please enter a name:");
		lblName.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Label lblId = new Label("Please enter an ID:");
		lblId.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Label lblWorkType = new Label("Choose preferred work type:");
		lblWorkType.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Label lblPreferredStart = new Label("Preferred starting hour:");
		lblPreferredStart.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Label lblDepartment = new Label("Choose a department:");
		lblDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Label lblRole = new Label("Choose a role:");
		lblRole.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

		TextField tfName = new TextField();
		TextField tfId = new TextField();

		ComboBox<String> cmbWorkType = new ComboBox<String>();
		cmbWorkType.getItems().addAll("Early", "Late", "Regular", "Home");
		ComboBox<String> cmbPreferredStart = new ComboBox<String>();

		ComboBox<String> cmbDepartment = new ComboBox<String>();
		ComboBox<String> cmbRole = new ComboBox<String>();
		cmbRole.setVisible(false);
		lblRole.setVisible(false);

		ComboBox<String> cmbEmployeeType = new ComboBox<String>();
		Label lblEmployeeType = new Label("Choose payment method");
		lblEmployeeType.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		cmbEmployeeType.getItems().addAll("Basic", "Basic and Sales", "Hourly Payment");

		Label lblHrSalary = new Label("Enter hourly payment");
		lblHrSalary.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

		Label lblSalary = new Label("Enter Monthly Salary");
		lblSalary.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

		TextField tfSalary = new TextField();
		Button btnAddEmployee = new Button("Add");
		btnAddEmployee.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		btnAddEmployee.setTextFill(Color.ROYALBLUE);

		Label lblSales = new Label("Enter Sales Revenue: ");
		lblSales.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		TextField tfSales = new TextField();
		lblSales.setVisible(false);
		tfSales.setVisible(false);

		gpAddEmployee.setPadding(new Insets(10));
		gpAddEmployee.setHgap(15);
		gpAddEmployee.setVgap(15);
		gpAddEmployee.add(lblName, 0, 0);
		gpAddEmployee.add(tfName, 1, 0);
		gpAddEmployee.add(lblId, 0, 1);
		gpAddEmployee.add(tfId, 1, 1);
		gpAddEmployee.add(lblWorkType, 0, 2);
		gpAddEmployee.add(cmbWorkType, 1, 2);
		gpAddEmployee.add(lblPreferredStart, 0, 3);
		gpAddEmployee.add(cmbPreferredStart, 1, 3);
		gpAddEmployee.add(lblDepartment, 0, 4);
		gpAddEmployee.add(cmbDepartment, 1, 4);
		gpAddEmployee.add(lblRole, 0, 5);
		gpAddEmployee.add(cmbRole, 1, 5);
		gpAddEmployee.add(lblEmployeeType, 0, 6);
		gpAddEmployee.add(cmbEmployeeType, 1, 6);
		gpAddEmployee.add(lblSalary, 0, 7);
		gpAddEmployee.add(lblHrSalary, 0, 7);
		gpAddEmployee.add(tfSalary, 1, 7);
		gpAddEmployee.add(lblSales, 2, 7);
		gpAddEmployee.add(tfSales, 3, 7);
		gpAddEmployee.add(btnAddEmployee, 0, 8);
		cmbPreferredStart.setVisible(false);
		lblPreferredStart.setVisible(false);
		lblHrSalary.setVisible(false);
		lblSalary.setVisible(false);
		tfSalary.setVisible(false);

		addEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (ViewListenable l : allListeners) {
					cmbRole.setVisible(false);
					lblRole.setVisible(false);
					cmbDepartment.getItems().clear();
					for (int i = 0; i < l.getAllDepartments().size(); i++) {
						cmbDepartment.getItems().add(l.getAllDepartments().elementAt(i).getName());
					}
				}
				bpMain.setCenter(gpAddEmployee);
			}
		});

		cmbDepartment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				cmbRole.setVisible(true);
				lblRole.setVisible(true);
				Department tempDepartment = null;
				for (ViewListenable l : allListeners) {
					cmbRole.getItems().clear();
					if (cmbDepartment.getValue() != null) {
						for (int i = 0; i < l.getAllDepartments().size(); i++) {
							if (l.getAllDepartments().elementAt(i).getName().equals(cmbDepartment.getValue())) {
								tempDepartment = l.getAllDepartments().elementAt(i);
							}
						}

						for (int i = 0; i < tempDepartment.getNumOfRoles(); i++) {
							cmbRole.getItems().add(tempDepartment.getAllRoles().elementAt(i).getName());
						}
					}
				}
			}
		});

		cmbWorkType.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				if (!cmbWorkType.getValue().equals("Home") || !cmbWorkType.getValue().equals("Regular")) {
					cmbPreferredStart.setVisible(true);
					lblPreferredStart.setVisible(true);
				}
				cmbPreferredStart.getItems().clear();
				if (cmbWorkType.getValue().equals("Early")) {
					for (int i = 0; i < 8; i++) {
						cmbPreferredStart.getItems().add(i + ":00");
					}
				} else if (cmbWorkType.getValue().equals("Late")) {
					for (int i = 9; i < 24; i++) {
						cmbPreferredStart.getItems().add(i + ":00");
					}
				} else {
					cmbPreferredStart.setVisible(false);
					lblPreferredStart.setVisible(false);
				}
			}
		});

		cmbEmployeeType.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				tfSalary.setVisible(true);
				if (cmbEmployeeType.getValue().equals("Hourly Payment")) {
					lblHrSalary.setVisible(true);
					lblSalary.setVisible(false);
				} else {
					lblHrSalary.setVisible(false);
					lblSalary.setVisible(true);
				}
				if (cmbEmployeeType.getValue().equals("Basic and Sales")) {
					lblSales.setVisible(true);
					tfSales.setVisible(true);
				} else {
					lblSales.setVisible(false);
					tfSales.setVisible(false);
				}
			}
		});
		btnAddEmployee.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				if (tfName.getText().isEmpty() || tfId.getText().isEmpty() || cmbWorkType.getValue() == null
						|| cmbDepartment.getValue() == null || cmbRole.getValue() == null
						|| tfSalary.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "You must fill all fields.");
				} else if (!cmbWorkType.getValue().equals("Home") && !cmbWorkType.getValue().equals("Regular")
						&& cmbPreferredStart.getValue() == null) {
					JOptionPane.showMessageDialog(null, "You must fill all fields.");
				} else if (cmbEmployeeType.getValue().equals("Basic and Sales") && tfSales.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "You must fill all fields.");
				} else {
					for (ViewListenable l : allListeners) {
						int startingHr = 8;
						int endingHr = 17;
						if (cmbWorkType.getValue().equals("Early") || cmbWorkType.getValue().equals("Late")) {
							startingHr = Integer.parseInt(cmbPreferredStart.getValue().split(":")[0]);
							endingHr = startingHr + 9;
							if (endingHr > 24) {
								endingHr = endingHr - 24;
							}
						}

						String stringType = cmbEmployeeType.getValue().toUpperCase().replace(" ", "_");
						eEmployeeType type = eEmployeeType.valueOf(stringType);
						eType workType = eType.valueOf(cmbWorkType.getValue());
						Preference newPreference = new Preference(startingHr, endingHr, workType);
						Department department = null;
						Role role = null;
						for (int i = 0; i < l.getAllDepartments().size(); i++) {
							if (cmbDepartment.getValue().equals(l.getAllDepartments().elementAt(i).getName())) {
								department = l.getAllDepartments().elementAt(i);
							}
						}
						for (int j = 0; j < department.getNumOfRoles(); j++) {
							if (cmbRole.getValue().equals(department.getAllRoles().elementAt(j).getName())) {
								role = department.getAllRoles().elementAt(j);
							}
						}
						double salary = 0;
						double sales = 0;
						String error = "Salary";
						try {
							salary = Double.parseDouble(tfSalary.getText());
							error = "Sales";
							if (cmbEmployeeType.getValue().equals("Basic and Sales")) {
								sales = Double.parseDouble(tfSales.getText());
							}
							if (!l.addEmployee(tfName.getText(), newPreference, role, department, tfId.getText(),
									salary, sales, type)) {
								JOptionPane.showMessageDialog(null, "Employee already exists");
							} else {
								if (department.isSyncable()) {
									department.sync(department.getStartingHr(), department.getEndingHr());
								} else if (department.isChangable() && role.isChangable()) {
									department.change();
								}
								JOptionPane.showMessageDialog(null, "Employee added successfully");
							}
						} catch (NameException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, error + " must be numbers");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}

					}
				}

			}
		});

		// -------------------------------------------------------- add role

		GridPane gpAddRole = new GridPane();

		gpAddRole.setPadding(new Insets(10));
		gpAddRole.setHgap(15);
		gpAddRole.setVgap(15);

		Label lblRoleName = new Label("Enter a role name: ");
		lblRoleName.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		TextField tfRoleName = new TextField();

		CheckBox chIsChangable = new CheckBox("Is Changable");
		chIsChangable.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		Label lblChooseDepartment = new Label("Choose a department");
		lblChooseDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		ComboBox<String> cmbRoleDepartment = new ComboBox<String>();

		Button btnAddRole = new Button("Add");
		btnAddRole.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		btnAddRole.setTextFill(Color.ROYALBLUE);

		gpAddRole.add(lblRoleName, 0, 0);
		gpAddRole.add(tfRoleName, 1, 0);
		gpAddRole.add(lblChooseDepartment, 0, 2);
		gpAddRole.add(cmbRoleDepartment, 1, 2);
		gpAddRole.add(chIsChangable, 0, 4);
		gpAddRole.add(btnAddRole, 0, 6);

		addRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpAddRole);
				for (ViewListenable l : allListeners) {
					cmbRoleDepartment.getItems().clear();
					for (int i = 0; i < l.getAllDepartments().size(); i++) {
						cmbRoleDepartment.getItems().add(l.getAllDepartments().elementAt(i).getName());
					}
				}
			}
		});

		btnAddRole.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				if (tfRoleName.getText().isEmpty() || cmbRoleDepartment.getValue() == null) {
					JOptionPane.showMessageDialog(null, "You must fill all fields.");
				} else {
					for (ViewListenable l : allListeners) {
						Department department = null;
						for (int i = 0; i < l.getAllDepartments().size(); i++) {
							if (l.getAllDepartments().elementAt(i).getName().equals(cmbRoleDepartment.getValue())) {
								department = l.getAllDepartments().elementAt(i);
							}
						}
						try {
							if (!l.addRole(tfRoleName.getText(), chIsChangable.isSelected(), department)) {
								JOptionPane.showMessageDialog(null, "The role already exists");
							} else {
								JOptionPane.showMessageDialog(null, "Role added successfully");
							}
						} catch (NameException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				}
			}
		});

		// -------------------------------------------------------- add department

		GridPane gpAddDepartment = new GridPane();

		gpAddDepartment.setPadding(new Insets(10));
		gpAddDepartment.setHgap(15);
		gpAddDepartment.setVgap(15);

		Label lblDepartmentName = new Label("Enter a department name: ");
		lblDepartmentName.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		TextField tfDepartmentName = new TextField();

		CheckBox chIsSyncable = new CheckBox("Is Syncable");
		chIsSyncable.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		CheckBox chChangable = new CheckBox("Is Changable");
		chChangable.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		Label lblStrHr = new Label("Starting hour ");
		lblStrHr.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		ComboBox<String> cmbStrHr = new ComboBox<String>();
		for (int i = 0; i < 24; i++) {
			cmbStrHr.getItems().add(i + ":00");
		}
		Button btnAddDepartment = new Button("Add");
		btnAddDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		btnAddDepartment.setTextFill(Color.ROYALBLUE);

		gpAddDepartment.add(lblDepartmentName, 0, 0);
		gpAddDepartment.add(tfDepartmentName, 1, 0);
		gpAddDepartment.add(chIsSyncable, 0, 2);
		gpAddDepartment.add(lblStrHr, 1, 2);
		gpAddDepartment.add(cmbStrHr, 2, 2);
		gpAddDepartment.add(chChangable, 0, 4);
		gpAddDepartment.add(btnAddDepartment, 0, 6);

		cmbStrHr.setVisible(false);
		lblStrHr.setVisible(false);

		addDepartment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpAddDepartment);
			}
		});

		chIsSyncable.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (chIsSyncable.isSelected()) {
					cmbStrHr.setVisible(true);
					chChangable.setSelected(false);
					chChangable.setDisable(true);
					lblStrHr.setVisible(true);
				} else {
					cmbStrHr.setVisible(false);
					chChangable.setDisable(false);
					lblStrHr.setVisible(false);
				}
			}
		});

		btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (tfDepartmentName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "You must fill all fields.");
				} else if (chIsSyncable.isSelected() && cmbStrHr.getValue() == null) {
					JOptionPane.showMessageDialog(null, "You must fill all fields.");
				} else {
					for (ViewListenable l : allListeners) {
						int startingHr = 8;
						int endingHr = 17;
						if (chIsSyncable.isSelected()) {
							startingHr = Integer.parseInt(cmbStrHr.getValue().split(":")[0]);
							endingHr = startingHr + 9;
						}
						try {
							if (!l.addDepartment(tfDepartmentName.getText(), chIsSyncable.isSelected(),
									chChangable.isSelected(), startingHr, endingHr)) {
								JOptionPane.showMessageDialog(null, "The department already exists");
							} else {
								JOptionPane.showMessageDialog(null, "Department added successfully");
							}
						} catch (NameException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				}
			}
		});

		// -------------------------------------------------------- Show Employee

		GridPane gpShowEmployee = new GridPane();

		gpShowEmployee.setPadding(new Insets(10));
		gpShowEmployee.setHgap(10);
		gpShowEmployee.setVgap(10);

		Label lblShowEmployee = new Label("The Company Employees");

		lblShowEmployee.setFont(Font.font("Calibri", FontWeight.BOLD, 35));

		TextArea taShowEmployee = new TextArea();

		taShowEmployee.setPrefSize(650, 500);
		taShowEmployee.setEditable(false);
		taShowEmployee.setFont(Font.font("Calibri", 18));

		gpShowEmployee.add(lblShowEmployee, 0, 0);
		gpShowEmployee.add(taShowEmployee, 0, 2);

		showEmployees.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				bpMain.setCenter(gpShowEmployee);
				for (ViewListenable l : allListeners) {
					taShowEmployee.setText(l.showEmployees());
				}
			}
		});

		// -------------------------------------------------------- Show Role

		GridPane gpShowRoles = new GridPane();
		gpShowRoles.setPadding(new Insets(10));
		gpShowRoles.setHgap(10);
		gpShowRoles.setVgap(10);

		Label lblShowRoles = new Label("The Company Roles");
		lblShowRoles.setFont(Font.font("Calibri", FontWeight.BOLD, 35));

		TextArea taShowRoles = new TextArea();
		taShowRoles.setFont(Font.font("Calibri", 18));
		taShowRoles.setEditable(false);
		taShowRoles.setPrefSize(650, 500);

		gpShowRoles.add(lblShowRoles, 0, 0);
		gpShowRoles.add(taShowRoles, 0, 2);

		showRoles.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpShowRoles);
				for (ViewListenable l : allListeners) {
					taShowRoles.setText(l.showRoles());
				}
			}
		});

		// -------------------------------------------------------- Show Department

		GridPane gpShowDepartments = new GridPane();
		gpShowDepartments.setPadding(new Insets(10));
		gpShowDepartments.setHgap(10);
		gpShowDepartments.setVgap(10);

		Label lblShowDepartments = new Label("The Company Departments");
		lblShowDepartments.setFont(Font.font("Calibri", FontWeight.BOLD, 35));

		TextArea taShowDepartments = new TextArea();
		taShowDepartments.setFont(Font.font("Calibri", 18));
		taShowDepartments.setEditable(false);
		taShowDepartments.setPrefSize(650, 500);

		gpShowDepartments.add(lblShowDepartments, 0, 0);

		gpShowDepartments.add(taShowDepartments, 0, 2);

		showDepartments.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpShowDepartments);
				for (ViewListenable l : allListeners) {
					taShowDepartments.setText(l.showDeparments());
				}
			}
		});

		// -------------------------------------------------------- Update Role Method

		GridPane gpUpdateRole = new GridPane();

		gpUpdateRole.setPadding(new Insets(10));
		gpUpdateRole.setHgap(15);
		gpUpdateRole.setVgap(15);

		Label lblRoles = new Label("Select a Role");
		lblRoles.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		Label lblStatus = new Label();
		lblStatus.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		lblStatus.setVisible(false);

		ComboBox<String> cmbUpdateRoles = new ComboBox<>();
		CheckBox chUpdateChange = new CheckBox("Is Changable");
		chUpdateChange.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		Button updateRole = new Button("Update");
		updateRole.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		updateRole.setTextFill(Color.ROYALBLUE);

		gpUpdateRole.add(lblRoles, 0, 1);
		gpUpdateRole.add(cmbUpdateRoles, 1, 1);
		gpUpdateRole.add(lblStatus, 0, 3);
		gpUpdateRole.add(chUpdateChange, 0, 5);
		gpUpdateRole.add(updateRole, 0, 7);

		updateMethodRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpUpdateRole);
				lblStatus.setVisible(false);
				for (ViewListenable l : allListeners) {
					cmbUpdateRoles.getItems().clear();
					for (int i = 0; i < l.getAllRoles().size(); i++) {
						cmbUpdateRoles.getItems().add(l.getAllRoles().elementAt(i).getName());
					}
				}
			}
		});

		cmbUpdateRoles.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				lblStatus.setVisible(true);
				for (ViewListenable l : allListeners) {
					for (int i = 0; i < l.getAllRoles().size(); i++) {
						if (l.getAllRoles().elementAt(i).getName().equals(cmbUpdateRoles.getValue())) {
							String str = l.getAllRoles().elementAt(i).isChangable() ? "Changable" : "Not Changable";
							lblStatus.setText("Current status: " + str);
						}
					}
				}
			}
		});

		updateRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (cmbUpdateRoles.getValue() == null) {
					JOptionPane.showMessageDialog(null, "You must fill all fields");

				} else {
					for (ViewListenable l : allListeners) {
						try {
							l.updateRoles(cmbUpdateRoles.getValue(), chUpdateChange.isSelected());
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
						for (int i = 0; i < l.getAllRoles().size(); i++) {
							if (l.getAllRoles().elementAt(i).getName().equals(cmbUpdateRoles.getValue())) {
								String str = l.getAllRoles().elementAt(i).isChangable() ? "Changable" : "Not Changable";
								lblStatus.setText("Current status: " + str);
							}
						}
					}
					JOptionPane.showMessageDialog(null, cmbUpdateRoles.getValue() + " Updated");
				}
			}
		});

		// -------------------------------------------------------- Update Department Method

		GridPane gpUpdateDepartment = new GridPane();

		gpUpdateDepartment.setPadding(new Insets(10));
		gpUpdateDepartment.setHgap(15);
		gpUpdateDepartment.setVgap(15);

		Label lblDepartments = new Label("Select a Department");
		lblDepartments.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		Label lblDepartmentStatus = new Label();
		lblDepartmentStatus.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		lblDepartmentStatus.setVisible(false);

		ComboBox<String> cmbUpdateDepartments = new ComboBox<String>();

		CheckBox chUpdateChangeDepartment = new CheckBox("Is Changable");
		chUpdateChangeDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		CheckBox chUpdateSyncDepartment = new CheckBox("Is Syncable");
		chUpdateSyncDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		Label lblStartingHr = new Label("Starting hour ");
		lblStartingHr.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		lblStartingHr.setVisible(false);

		ComboBox<String> cmbStartingHr = new ComboBox<String>();
		for (int i = 0; i < 24; i++) {
			cmbStartingHr.getItems().add(i + ":00");
		}

		cmbStartingHr.setVisible(false);

		Button updateDepartment = new Button("Update");
		updateDepartment.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		updateDepartment.setTextFill(Color.ROYALBLUE);

		gpUpdateDepartment.add(lblDepartments, 0, 1);
		gpUpdateDepartment.add(cmbUpdateDepartments, 1, 1);
		gpUpdateDepartment.add(lblDepartmentStatus, 0, 3);
		gpUpdateDepartment.add(chUpdateSyncDepartment, 0, 5);
		gpUpdateDepartment.add(chUpdateChangeDepartment, 1, 5);
		gpUpdateDepartment.add(lblStartingHr, 0, 7);
		gpUpdateDepartment.add(cmbStartingHr, 1, 7);
		gpUpdateDepartment.add(updateDepartment, 0, 9);

		updateMethodDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpUpdateDepartment);
				for (ViewListenable l : allListeners) {
					cmbUpdateDepartments.getItems().clear();
					for (int i = 0; i < l.getAllDepartments().size(); i++) {
						cmbUpdateDepartments.getItems().add(l.getAllDepartments().elementAt(i).getName());
					}
				}
			}
		});

		chUpdateSyncDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (chUpdateSyncDepartment.isSelected()) {
					lblStartingHr.setVisible(true);
					chUpdateChangeDepartment.setSelected(false);
					chUpdateChangeDepartment.setDisable(true);
					cmbStartingHr.setVisible(true);
				} else {
					lblStartingHr.setVisible(false);
					cmbStartingHr.setVisible(false);
					chUpdateChangeDepartment.setDisable(false);
				}

			}
		});

		cmbUpdateDepartments.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				lblDepartmentStatus.setVisible(true);
				for (ViewListenable l : allListeners) {
					for (int i = 0; i < l.getAllDepartments().size(); i++) {
						if (l.getAllDepartments().elementAt(i).getName().equals(cmbUpdateDepartments.getValue())) {
							String sync = l.getAllDepartments().elementAt(i).isSyncable() ? "Syncable" : "Not Syncable";
							String change = l.getAllDepartments().elementAt(i).isChangable() ? "Changable"
									: "Not Changable";
							lblDepartmentStatus.setText("Current status: " + sync + ", " + change);
						}
					}
				}
			}
		});

		updateDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (cmbUpdateDepartments.getValue() == null) {
					JOptionPane.showMessageDialog(null, "You must fill all fields");

				} else if (chUpdateSyncDepartment.isSelected() && cmbStartingHr.getValue() == null) {
					JOptionPane.showMessageDialog(null, "You must fill all fields");
				} else {
					for (ViewListenable l : allListeners) {
						int startingHr = 8;
						int endingHr = 17;
						if (chUpdateSyncDepartment.isSelected()) {
							startingHr = Integer.parseInt(cmbStartingHr.getValue().split(":")[0]);
							endingHr = startingHr + 9;
						}
						try {
							l.updateDepartments(cmbUpdateDepartments.getValue(), chUpdateChangeDepartment.isSelected(),
									chUpdateSyncDepartment.isSelected(), startingHr, endingHr);
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
						for (int i = 0; i < l.getAllDepartments().size(); i++) {
							if (l.getAllDepartments().elementAt(i).getName().equals(cmbUpdateDepartments.getValue())) {
								String sync = l.getAllDepartments().elementAt(i).isSyncable() ? "Syncable"
										: "Not Syncable";
								String change = l.getAllDepartments().elementAt(i).isChangable() ? "Changable"
										: "Not Changable";
								lblDepartmentStatus.setText("Current status: " + sync + ", " + change);
							}
						}

					}
					JOptionPane.showMessageDialog(null, cmbUpdateDepartments.getValue() + " Updated");
				}
			}
		});

		// -------------------------------------------------------- revenue

		GridPane gpRevenue = new GridPane();

		Label lblEmployeesRevenue = new Label("The Employees Profit");
		lblEmployeesRevenue.setFont(Font.font("Calibri", FontWeight.BOLD, 35));

		TextArea taEmployeesRevenue = new TextArea();
		taEmployeesRevenue.setFont(Font.font("Calibri", 18));
		taEmployeesRevenue.setEditable(false);
		taEmployeesRevenue.setPrefSize(650, 200);

		Label lblDepartmentsRevenue = new Label("The Departments Profit");
		lblDepartmentsRevenue.setFont(Font.font("Calibri", FontWeight.BOLD, 35));

		TextArea taDepartmentsRevenue = new TextArea();
		taDepartmentsRevenue.setFont(Font.font("Calibri", 18));
		taDepartmentsRevenue.setEditable(false);
		taDepartmentsRevenue.setPrefSize(650, 200);

		Label lblCompanyRevenue = new Label("The Company Revenue (per day)");

		lblCompanyRevenue.setFont(Font.font("Calibri", FontWeight.BOLD, 35));

		TextArea taCompanyRevenue = new TextArea();
		taCompanyRevenue.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
		taCompanyRevenue.setEditable(false);
		taCompanyRevenue.setPrefSize(650, 100);

		gpRevenue.add(lblEmployeesRevenue, 0, 0);
		gpRevenue.add(taEmployeesRevenue, 0, 1);

		gpRevenue.add(lblDepartmentsRevenue, 0, 3);
		gpRevenue.add(taDepartmentsRevenue, 0, 4);

		gpRevenue.add(lblCompanyRevenue, 0, 6);
		gpRevenue.add(taCompanyRevenue, 0, 7);

		showProfit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpRevenue);
				for (ViewListenable l : allListeners) {
					taEmployeesRevenue.setText(l.showEmployeeRevenue());
					taDepartmentsRevenue.setText(l.showDepartmentRevenue());
					taCompanyRevenue.setText(l.showCompanyRevenue());
				}
			}
		});
		// -------------------------------------------------------- Update employee

		GridPane gpUpdateEmployee = new GridPane();
		ComboBox<String> cmbAllEmployess = new ComboBox<String>();
		Label lblEmployee = new Label("Choose employee:");
		lblEmployee.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Label lblPreferenceStart = new Label("Preferred Starting Hour:");
		lblPreferenceStart.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Label lblPrefType = new Label("Choose Preferred Work Type:");
		lblPrefType.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		Button btnUpdateEmployee = new Button("Update");
		btnUpdateEmployee.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		ComboBox<String> cmbPrefernce = new ComboBox<String>();
		cmbPrefernce.getItems().addAll("Early", "Late", "Regular", "Home");
		ComboBox<String> cmbStartingTime = new ComboBox<String>();
		Label lblMonthSalary = new Label("Enter Month Salary:");
		lblMonthSalary.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

		Label lblHourlyPayment = new Label("Enter Hourly Payment:");
		lblHourlyPayment.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

		Label lblNumOfSales = new Label("Enter Sales Revenue:");
		lblNumOfSales.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

		TextField tfMonthSalary = new TextField();
		TextField tfNumOfSales = new TextField();
		lblMonthSalary.setVisible(false);
		lblNumOfSales.setVisible(false);
		lblHourlyPayment.setVisible(false);
		tfMonthSalary.setVisible(false);
		tfNumOfSales.setVisible(false);

		gpUpdateEmployee.setPadding(new Insets(10));
		gpUpdateEmployee.setHgap(15);
		gpUpdateEmployee.setVgap(15);
		gpUpdateEmployee.add(lblEmployee, 0, 1);
		gpUpdateEmployee.add(cmbAllEmployess, 1, 1);
		gpUpdateEmployee.add(lblPrefType, 0, 2);
		gpUpdateEmployee.add(cmbPrefernce, 1, 2);
		gpUpdateEmployee.add(lblPreferenceStart, 0, 3);
		gpUpdateEmployee.add(cmbStartingTime, 1, 3);
		gpUpdateEmployee.add(lblMonthSalary, 0, 4);
		gpUpdateEmployee.add(tfMonthSalary, 1, 4);
		gpUpdateEmployee.add(lblHourlyPayment, 0, 4);
		gpUpdateEmployee.add(lblNumOfSales, 0, 6);
		gpUpdateEmployee.add(tfNumOfSales, 1, 6);
		gpUpdateEmployee.add(btnUpdateEmployee, 0, 7);

		updateMethodEmoloyee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpUpdateEmployee);
				for (ViewListenable l : allListeners) {
					cmbAllEmployess.getItems().clear();
					for (int i = 0; i < l.getEmployess().size(); i++) {
						cmbAllEmployess.getItems().add(l.getEmployess().elementAt(i).getName());
					}
				}
			}
		});

		btnUpdateEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (cmbAllEmployess.getValue() == null || cmbPrefernce.getValue() == null
						|| tfMonthSalary.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "You must fill all fields");
				} else {
					for (ViewListenable l : allListeners) {
						for (int i = 0; i < l.getEmployess().size(); i++) {
							if (cmbAllEmployess.getValue().equals(l.getEmployess().elementAt(i).getName())) {
								if (l.getEmployess().elementAt(i).getEmployeeType() == eEmployeeType.BASIC_AND_SALES
										&& tfNumOfSales.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null, "You must fill all fields");
								}
								int startingHr = 8;
								int endingHr = 17;
								if (cmbPrefernce.getValue().equals("Early") || cmbPrefernce.getValue().equals("Late")) {
									startingHr = Integer.parseInt(cmbStartingTime.getValue().split(":")[0]);
									endingHr = startingHr + 9;
									if (endingHr > 24) {
										endingHr = endingHr - 24;
									}
								}
								eType workType = eType.valueOf(cmbPrefernce.getValue());
								l.getEmployess().elementAt(i).setPreference(startingHr, endingHr, workType.ordinal());
								eEmployeeType temp = l.getEmployess().elementAt(i).getEmployeeType();
								if (temp == eEmployeeType.HOURLY_PAYMENT) {
									try {
										((HourlySalaryEmployee) l.getEmployess().elementAt(i))
												.setHourlyPayment(Integer.parseInt(tfMonthSalary.getText()));
										l.updateEmployee(l.getEmployess().elementAt(i));
										JOptionPane.showMessageDialog(null, "Employee Updated Successfully");
									} catch (Exception e) {
										JOptionPane.showMessageDialog(null, e.getMessage());
									}
								} else if (temp == eEmployeeType.BASIC_AND_SALES) {
									try {
										if (Integer.parseInt(tfMonthSalary.getText()) < 0
												|| Integer.parseInt(tfNumOfSales.getText()) < 0) {
											JOptionPane.showMessageDialog(null, "Numbers must be positive!");
										} else {
											((BasicAndSalesEmployee) l.getEmployess().elementAt(i))
													.setMonthSalary(Integer.parseInt(tfMonthSalary.getText()));
											((BasicAndSalesEmployee) l.getEmployess().elementAt(i))
													.setSales(Integer.parseInt(tfNumOfSales.getText()));
											l.updateEmployee(l.getEmployess().elementAt(i));
											JOptionPane.showMessageDialog(null, "Employee Updated Successfully");
										}
									} catch (Exception e) {
										JOptionPane.showMessageDialog(null, e.getMessage());
									}
								} else {
									try {
										((BasicSalaryEmployee) l.getEmployess().elementAt(i))
												.setMonthSalary(Integer.parseInt(tfMonthSalary.getText()));
										l.updateEmployee(l.getEmployess().elementAt(i));
										JOptionPane.showMessageDialog(null, "Employee Updated Successfully");
									} catch (NumberFormatException e) {
										JOptionPane.showMessageDialog(null, e.getMessage());
									} catch (Exception e) {
										JOptionPane.showMessageDialog(null, e.getMessage());
									}
								}
								break;
							}
						}
					}
				}

			}
		});

		cmbPrefernce.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				if (!cmbPrefernce.getValue().equals("Home") || !cmbPrefernce.getValue().equals("Regular")) {
					cmbStartingTime.setVisible(true);
					lblPreferenceStart.setVisible(true);
				}
				cmbStartingTime.getItems().clear();
				if (cmbPrefernce.getValue().equals("Early")) {
					for (int i = 0; i < 8; i++) {
						cmbStartingTime.getItems().add(i + ":00");
					}
				} else if (cmbPrefernce.getValue().equals("Late")) {
					for (int i = 9; i < 24; i++) {
						cmbStartingTime.getItems().add(i + ":00");
					}
				} else {
					cmbStartingTime.setVisible(false);
					lblPreferenceStart.setVisible(false);
				}
			}
		});

		cmbAllEmployess.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				eEmployeeType temp = null;
				for (ViewListenable l : allListeners) {
					for (int i = 0; i < l.getEmployess().size(); i++) {
						if (cmbAllEmployess.getValue() != null
								&& cmbAllEmployess.getValue().equals(l.getEmployess().elementAt(i).getName())) {
							temp = l.getEmployess().elementAt(i).getEmployeeType();
						}
					}
				}
				tfMonthSalary.setVisible(true);
				if (temp == eEmployeeType.HOURLY_PAYMENT) {
					lblHourlyPayment.setVisible(true);
					lblMonthSalary.setVisible(false);
				} else {
					lblHourlyPayment.setVisible(false);
					lblMonthSalary.setVisible(true);
				}
				if (temp == eEmployeeType.BASIC_AND_SALES) {
					lblNumOfSales.setVisible(true);
					tfNumOfSales.setVisible(true);
				} else {
					lblNumOfSales.setVisible(false);
					tfNumOfSales.setVisible(false);
				}
			}
		});

		// -------------------------------------------------------- delete employee

		GridPane gpDelete = new GridPane();
		Label lblDelete = new Label("Choose employee to delete:");
		lblDelete.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		ComboBox<String> cmbDeleteEmployee = new ComboBox<String>();
		Button deleteEmployee = new Button("Delete");
		deleteEmployee.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		gpDelete.setPadding(new Insets(10));
		gpDelete.setHgap(15);
		gpDelete.setVgap(15);
		gpDelete.add(lblDelete, 0, 1);
		gpDelete.add(cmbDeleteEmployee, 1, 1);
		gpDelete.add(deleteEmployee, 0, 3);

		deleteMethodEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				bpMain.setCenter(gpDelete);
				for (ViewListenable l : allListeners) {
					cmbDeleteEmployee.getItems().clear();
					for (int i = 0; i < l.getEmployess().size(); i++) {
						cmbDeleteEmployee.getItems().add(l.getEmployess().elementAt(i).getName());
					}
				}
			}
		});

		deleteEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (cmbDeleteEmployee.getValue() == null) {
					JOptionPane.showMessageDialog(null, "You must fill all fields");
				} else {
					for (ViewListenable l : allListeners) {
						if (l.getEmployess().size() == 0) {
							JOptionPane.showMessageDialog(null, "There are no employees in the company");
						}
						for (int i = 0; i < l.getEmployess().size(); i++) {
							if (cmbDeleteEmployee.getValue().equals(l.getEmployess().elementAt(i).getName())) {
								try {
									l.deleteEmployee(l.getEmployess().elementAt(i));
									JOptionPane.showMessageDialog(null, "Employee Deleted Successfully");
								} catch (SQLException e) {
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
								cmbDeleteEmployee.getItems().clear();
								break;
							}
						}
					}
				}
			}
		});

		// -------------------------------------------------------- exit
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				primaryStage.close();
				for (ViewListenable l : allListeners) {
					try {
						l.closeConn();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}
		});
		primaryStage.show();
	}

	public void registerListener(ViewListenable l) {
		allListeners.add(l);
	}
}
