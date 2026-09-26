package com.telecom.controller;

import com.telecom.model.*;
import com.telecom.repository.DataStore;
import com.telecom.service.*;
import com.telecom.util.BillGenerator;
import com.telecom.util.ValidationException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class DashboardController {
    private final Stage stage;
    private final BorderPane root = new BorderPane();
    private final VBox content = new VBox(18);
    private final Label title = new Label("Dashboard");
    private final CustomerService customerService = new CustomerService();
    private final SIMService simService = new SIMService();
    private final UsageService usageService = new UsageService();
    private final BillingService billingService = new BillingService();
    private final PaymentService paymentService = new PaymentService();

    public DashboardController(Stage stage) {
        this.stage = stage;
        root.getStyleClass().add("root");
        root.setLeft(sidebar());
        root.setCenter(centerShell());
        showDashboard();
    }

    public BorderPane getView() {
        return root;
    }

    private Node centerShell() {
        VBox shell = new VBox(18);
        shell.getStyleClass().add("main-shell");
        HBox top = new HBox(title);
        top.setAlignment(Pos.CENTER_LEFT);
        top.getStyleClass().add("topbar");
        shell.getChildren().addAll(top, content);
        VBox.setVgrow(content, Priority.ALWAYS);
        return shell;
    }

    private VBox sidebar() {
        VBox side = new VBox(10);
        side.getStyleClass().add("sidebar");
        side.setPadding(new Insets(24, 16, 24, 16));
        Label logo = new Label("Telecom BSS");
        logo.getStyleClass().add("logo");
        Label sub = new Label("Business • Support • System");
        sub.getStyleClass().add("sidebar-sub");
        side.getChildren().addAll(logo, sub, new Separator());

        side.getChildren().addAll(
                nav("Dashboard", this::showDashboard),
                nav("Customers", this::showCustomers),
                nav("SIM Registration", this::showSIM),
                nav("Plans", this::showPlans),
                nav("Usage Tracking", this::showUsage),
                nav("Bill Generation", this::showBilling),
                nav("Payments", this::showPayments),
                nav("Reports", this::showReports)
        );
        return side;
    }

    private Button nav(String text, Runnable action) {
        Button b = new Button(text);
        b.getStyleClass().add("nav-button");
        b.setMaxWidth(Double.MAX_VALUE);
        b.setOnAction(e -> action.run());
        return b;
    }

    private void setTitle(String text) {
        title.setText(text);
    }

    private void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    private VBox page(Node... nodes) {
        VBox box = new VBox(16, nodes);
        box.getStyleClass().add("page");
        VBox.setVgrow(box, Priority.ALWAYS);
        return box;
    }

    private Label section(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("section-title");
        return l;
    }

    private VBox stat(String number, String label) {
        Label n = new Label(number);
        n.getStyleClass().add("stat-number");
        Label l = new Label(label);
        l.getStyleClass().add("stat-label");
        VBox box = new VBox(5, n, l);
        box.getStyleClass().add("stat-card");
        HBox.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    private void showDashboard() {
        setTitle("Dashboard");
        long active = DataStore.sims.stream().filter(SIMCard::isActive).count();
        double total = DataStore.bills.values().stream().mapToDouble(Bill::getTotalAmount).sum();

        HBox stats = new HBox(14,
                stat(String.valueOf(DataStore.customers.size()), "Customers"),
                stat(String.valueOf(active), "Active SIMs"),
                stat(String.valueOf(DataStore.plans.size()), "Plans"),
                stat(String.format("₹%.0f", total), "Generated Bills"));
        stats.setFillHeight(true);

        TableView<Customer> table = customerTable(DataStore.customers);
        table.setPrefHeight(420);

        setContent(page(
                new Label("Telecom Operations Overview") {{ getStyleClass().add("hero-title"); }},
                new Label("Manage customers, SIM activation, plans, usage, billing and payments from one place.") {{ getStyleClass().add("muted"); }},
                stats,
                section("Recent Customers"),
                table
        ));
    }

    private TableView<Customer> customerTable(List<Customer> data) {
        TableView<Customer> table = new TableView<>(FXCollections.observableArrayList(data));
        TableColumn<Customer, String> name = new TableColumn<>("Customer");
        name.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        TableColumn<Customer, String> phone = new TableColumn<>("Phone");
        phone.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPhone()));
        TableColumn<Customer, String> sim = new TableColumn<>("SIM");
        sim.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getSimCard() == null ? "-" : c.getValue().getSimCard().getSimNumber()));
        TableColumn<Customer, String> plan = new TableColumn<>("Plan");
        plan.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getPlan() == null ? "-" : c.getValue().getPlan().getPlanCode()));
        TableColumn<Customer, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getSimCard() != null && c.getValue().getSimCard().isActive() ? "ACTIVE" : "INACTIVE"));
        table.getColumns().addAll(name, phone, sim, plan, status);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return table;
    }

    private void showCustomers() {
        setTitle("Customer Management");
        TextField search = new TextField();
        search.setPromptText("Search by name, phone or SIM...");
        Button add = new Button("+ Add Customer");
        add.getStyleClass().add("primary-button");

        TableView<Customer> table = customerTable(DataStore.customers);
        search.textProperty().addListener((obs, old, val) ->
                table.setItems(FXCollections.observableArrayList(customerService.search(val))));

        add.setOnAction(e -> customerDialog(null, table));
        Button edit = new Button("Edit");
        Button delete = new Button("Delete");
        edit.setOnAction(e -> {
            Customer c = table.getSelectionModel().getSelectedItem();
            if (c != null) customerDialog(c, table);
        });
        delete.setOnAction(e -> {
            Customer c = table.getSelectionModel().getSelectedItem();
            if (c != null) {
                customerService.delete(c);
                table.setItems(FXCollections.observableArrayList(DataStore.customers));
            }
        });

        HBox actions = new HBox(10, search, add, edit, delete);
        HBox.setHgrow(search, Priority.ALWAYS);
        setContent(page(actions, table));
    }

    private void customerDialog(Customer existing, TableView<Customer> table) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(existing == null ? "Add Customer" : "Edit Customer");
        GridPane g = formGrid();
        TextField name = field(existing == null ? "" : existing.getName());
        TextField email = field(existing == null ? "" : existing.getEmail());
        TextField phone = field(existing == null ? "" : existing.getPhone());
        TextField address = field(existing == null ? "" : existing.getAddress());
        g.addRow(0, new Label("Name"), name);
        g.addRow(1, new Label("Email"), email);
        g.addRow(2, new Label("Phone"), phone);
        g.addRow(3, new Label("Address"), address);
        dialog.getDialogPane().setContent(g);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(bt -> bt);
        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                try {
                    if (existing == null) customerService.add(name.getText().trim(), email.getText().trim(), phone.getText().trim(), address.getText().trim());
                    else customerService.update(existing, name.getText().trim(), email.getText().trim(), phone.getText().trim(), address.getText().trim());
                    table.setItems(FXCollections.observableArrayList(DataStore.customers));
                } catch (ValidationException ex) { alert("Validation Error", ex.getMessage()); }
            }
        });
    }

    private void showSIM() {
        setTitle("SIM Registration & Activation");
        ComboBox<Customer> customer = new ComboBox<>(FXCollections.observableArrayList(DataStore.customers));
        ComboBox<Plan> plan = new ComboBox<>(FXCollections.observableArrayList(DataStore.plans));
        TextField sim = new TextField();
        sim.setPromptText("10 digit SIM number");
        Button activate = new Button("Activate SIM");
        activate.getStyleClass().add("primary-button");

        activate.setOnAction(e -> {
            try {
                simService.activate(customer.getValue(), sim.getText().trim(), plan.getValue());
                alert("SIM Activated", "SIM " + sim.getText() + " has been activated successfully.");
            } catch (Exception ex) { alert("Activation Error", ex.getMessage()); }
        });

        GridPane g = formGrid();
        g.addRow(0, new Label("Customer"), customer);
        g.addRow(1, new Label("SIM Number"), sim);
        g.addRow(2, new Label("Plan"), plan);
        setContent(page(section("New SIM Activation"), g, activate,
                new Label("Validation: SIM number must contain exactly 10 digits and cannot already be registered.") {{ getStyleClass().add("muted"); }}));
    }

    private void showPlans() {
        setTitle("Plan Management");
        TableView<Plan> table = new TableView<>(FXCollections.observableArrayList(DataStore.plans));
        TableColumn<Plan, String> code = new TableColumn<>("Code");
        code.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPlanCode()));
        TableColumn<Plan, String> name = new TableColumn<>("Plan");
        name.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPlanName()));
        TableColumn<Plan, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPlanType().name()));
        TableColumn<Plan, String> price = new TableColumn<>("Monthly Rent");
        price.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.format("₹%.2f", c.getValue().getMonthlyRent())));
        TableColumn<Plan, String> data = new TableColumn<>("Data");
        data.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.format("%.1f GB", c.getValue().getDataLimit())));
        table.getColumns().addAll(code, name, type, price, data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Button create = new Button("+ Create Plan");
        create.getStyleClass().add("primary-button");
        create.setOnAction(e -> planDialog(table));

        setContent(page(new HBox(10, create), table));
    }

    private void planDialog(TableView<Plan> table) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Plan");
        GridPane g = formGrid();
        TextField code = field(""); TextField name = field(""); TextField rent = field("");
        TextField data = field(""); TextField calls = field(""); TextField sms = field("");
        ComboBox<PlanType> type = new ComboBox<>(FXCollections.observableArrayList(PlanType.values()));
        g.addRow(0, new Label("Plan Code"), code);
        g.addRow(1, new Label("Plan Name"), name);
        g.addRow(2, new Label("Type"), type);
        g.addRow(3, new Label("Monthly Rent"), rent);
        g.addRow(4, new Label("Data Limit (GB)"), data);
        g.addRow(5, new Label("Call Limit (min)"), calls);
        g.addRow(6, new Label("SMS Limit"), sms);
        dialog.getDialogPane().setContent(g);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                try {
                    com.telecom.util.Validator.validatePlanCode(code.getText());
                    Plan p = new Plan(code.getText().trim(), name.getText().trim(), type.getValue(),
                            Double.parseDouble(rent.getText()), Double.parseDouble(data.getText()),
                            Integer.parseInt(calls.getText()), Integer.parseInt(sms.getText()));
                    DataStore.plans.add(p);
                    table.setItems(FXCollections.observableArrayList(DataStore.plans));
                } catch (Exception ex) { alert("Plan Error", ex.getMessage()); }
            }
        });
    }

    private void showUsage() {
        setTitle("Usage Tracking");
        ComboBox<Customer> customer = new ComboBox<>(FXCollections.observableArrayList(DataStore.customers));
        DatePicker date = new DatePicker(LocalDate.now());
        TextField calls = field("0"), sms = field("0"), data = field("0");
        Button add = new Button("Add Usage");
        add.getStyleClass().add("primary-button");

        TableView<Usage> table = usageTable();
        add.setOnAction(e -> {
            try {
                Customer c = customer.getValue();
                if (c == null || c.getSimCard() == null) throw new ValidationException("Select a customer with an active SIM.");
                usageService.add(c.getSimCard().getSimNumber(), date.getValue(),
                        Integer.parseInt(calls.getText()), Integer.parseInt(sms.getText()), Double.parseDouble(data.getText()));
                table.setItems(FXCollections.observableArrayList(DataStore.usageHistory));
                alert("Usage Added", "Usage record saved successfully.");
            } catch (Exception ex) { alert("Usage Error", ex.getMessage()); }
        });

        GridPane g = formGrid();
        g.addRow(0, new Label("Customer"), customer);
        g.addRow(1, new Label("Date"), date);
        g.addRow(2, new Label("Call Minutes"), calls);
        g.addRow(3, new Label("SMS Count"), sms);
        g.addRow(4, new Label("Data Used (GB)"), data);
        setContent(page(g, add, section("Usage History"), table));
    }

    private TableView<Usage> usageTable() {
        TableView<Usage> table = new TableView<>(FXCollections.observableArrayList(DataStore.usageHistory));
        TableColumn<Usage, String> sim = new TableColumn<>("SIM");
        sim.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSimNumber()));
        TableColumn<Usage, String> date = new TableColumn<>("Date");
        date.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDate().toString()));
        TableColumn<Usage, String> calls = new TableColumn<>("Calls");
        calls.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getCallMinutes())));
        TableColumn<Usage, String> sms = new TableColumn<>("SMS");
        sms.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getSmsCount())));
        TableColumn<Usage, String> data = new TableColumn<>("Data");
        data.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.format("%.2f GB", c.getValue().getDataUsed())));
        table.getColumns().addAll(sim, date, calls, sms, data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return table;
    }

    private void showBilling() {
        setTitle("Bill Generation");
        ComboBox<Customer> customer = new ComboBox<>(FXCollections.observableArrayList(DataStore.customers));
        TextField month = field(java.time.Month.from(LocalDate.now()).name() + " " + LocalDate.now().getYear());
        Button generate = new Button("Generate Bill");
        generate.getStyleClass().add("primary-button");
        TextArea invoice = new TextArea();
        invoice.setEditable(false);
        invoice.setPrefRowCount(18);

        generate.setOnAction(e -> {
            Customer c = customer.getValue();
            if (c == null || c.getPlan() == null) { alert("Billing Error", "Select a customer with a plan."); return; }
            Bill bill = billingService.generate(c, month.getText());
            invoice.setText(BillGenerator.generate(c, bill));
        });

        GridPane g = formGrid();
        g.addRow(0, new Label("Customer"), customer);
        g.addRow(1, new Label("Billing Month"), month);
        setContent(page(g, generate, section("Invoice Preview"), invoice));
        VBox.setVgrow(invoice, Priority.ALWAYS);
    }

    private void showPayments() {
        setTitle("Payment Recording");
        ComboBox<Bill> billBox = new ComboBox<>(FXCollections.observableArrayList(DataStore.bills.values()));
        billBox.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Bill b) { return b == null ? "" : b.getBillId() + " • ₹" + String.format("%.2f", b.getTotalAmount()) + " • " + b.getPaymentStatus(); }
            public Bill fromString(String s) { return null; }
        });
        ComboBox<String> method = new ComboBox<>(FXCollections.observableArrayList("UPI", "CARD", "CASH", "NET BANKING"));
        Button pay = new Button("Record Payment");
        pay.getStyleClass().add("primary-button");
        pay.setOnAction(e -> {
            try {
                Payment p = paymentService.pay(billBox.getValue(), method.getValue());
                alert("Payment Successful", "Transaction: " + p.getPaymentId());
                billBox.setItems(FXCollections.observableArrayList(DataStore.bills.values()));
            } catch (Exception ex) { alert("Payment Error", ex.getMessage()); }
        });
        GridPane g = formGrid();
        g.addRow(0, new Label("Bill"), billBox);
        g.addRow(1, new Label("Payment Method"), method);
        setContent(page(g, pay, section("Payment History"), paymentTable()));
    }

    private TableView<Payment> paymentTable() {
        TableView<Payment> table = new TableView<>(FXCollections.observableArrayList(DataStore.payments));
        TableColumn<Payment, String> id = new TableColumn<>("Transaction");
        id.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPaymentId()));
        TableColumn<Payment, String> bill = new TableColumn<>("Bill");
        bill.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBillId()));
        TableColumn<Payment, String> amount = new TableColumn<>("Amount");
        amount.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.format("₹%.2f", c.getValue().getAmount())));
        TableColumn<Payment, String> method = new TableColumn<>("Method");
        method.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMethod()));
        table.getColumns().addAll(id, bill, amount, method);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return table;
    }

    private void showReports() {
        setTitle("Billing Reports");
        List<Bill> sorted = DataStore.bills.values().stream()
                .sorted(Comparator.comparingDouble(Bill::getTotalAmount).reversed()).toList();
        TableView<Bill> table = new TableView<>(FXCollections.observableArrayList(sorted));
        TableColumn<Bill, String> id = new TableColumn<>("Bill ID");
        id.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBillId()));
        TableColumn<Bill, String> date = new TableColumn<>("Date");
        date.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBillDate().toString()));
        TableColumn<Bill, String> amount = new TableColumn<>("Amount");
        amount.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.format("₹%.2f", c.getValue().getTotalAmount())));
        TableColumn<Bill, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPaymentStatus().name()));
        table.getColumns().addAll(id, date, amount, status);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Label note = new Label("Bills are sorted by amount in descending order using Java sorting and the TreeMap date index.");
        note.getStyleClass().add("muted");
        setContent(page(note, table));
    }

    private GridPane formGrid() {
        GridPane g = new GridPane();
        g.setHgap(14);
        g.setVgap(14);
        g.getStyleClass().add("form-card");
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(150);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        g.getColumnConstraints().addAll(c1, c2);
        return g;
    }

    private TextField field(String value) {
        TextField f = new TextField(value);
        f.setPrefHeight(38);
        return f;
    }

    private void alert(String header, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Telecom Billing System");
        a.setHeaderText(header);
        a.setContentText(message);
        a.showAndWait();
    }
}
