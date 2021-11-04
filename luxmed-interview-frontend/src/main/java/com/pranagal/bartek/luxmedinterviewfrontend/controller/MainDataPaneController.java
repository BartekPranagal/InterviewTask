package com.pranagal.bartek.luxmedinterviewfrontend.controller;

import com.pranagal.bartek.luxmedinterviewfrontend.dto.UserData;
import com.pranagal.bartek.luxmedinterviewfrontend.enums.EditMode;
import com.pranagal.bartek.luxmedinterviewfrontend.service.BackendService;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Objects;

public class MainDataPaneController extends AbstractDataPane {

    @FXML
    private TextField personalIdField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;

    private UserData selectedUser;
    private EditMode mainDataEditMode;
    private BackendService backendService;
    private DetailsDataPaneController detailsPaneController;

    public void initialize() {
        backendService = BackendService.getInstance();
        configurePane();
    }

    public void configureConnectionBetweenMainAndDetailsPane(DetailsDataPaneController paneController) {
        this.detailsPaneController = paneController;
        table.setRowFactory(tv -> {
            TableRow<UserData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    this.selectedUser = row.getItem();
                    this.detailsPaneController.selectUser(row.getItem());
                }
            });
            return row;
        });
    }

    public void search(String query) {
        backendService.search(query);
    }

    private void configurePane() {
        configureMainTable();
        configureMainButtons();
    }

    private void configureMainTable() {
        table.getColumns().add(this.<UserData>createColumnForTable("id", "id"));
        table.getColumns().add(this.<UserData>createColumnForTable("First Name", "firstName"));
        table.getColumns().add(this.<UserData>createColumnForTable("Last Name", "lastName"));
        table.getColumns().add(this.<UserData>createColumnForTable("Personal Id", "personalId"));

        configureSelectionModel();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(backendService.getUserData());
    }

    private void configureMainButtons() {
        editForm.setVisible(false);
        editButton.setDisable(true);
        deleteButton.setDisable(true);

        addButton.setOnMouseClicked(event -> {
                clearEditForm();
                editForm.setVisible(true);
                mainDataEditMode = EditMode.CREATE;
        });
        editButton.setOnMouseClicked(event -> {
            if (Objects.nonNull(selectedUser)) {
                editForm.setVisible(true);
                fillEditForm(selectedUser);
                firstNameField.setText(selectedUser.getFirstName());
                lastNameField.setText(selectedUser.getLastName());
                personalIdField.setText(selectedUser.getPersonalId());
                mainDataEditMode = EditMode.UPDATE;
            }
        });
        deleteButton.setOnMouseClicked(event -> {
            if (Objects.nonNull(selectedUser)) {
                clearEditForm();
                backendService.deleteUser(selectedUser.getId());
            }
        });
        formConfirmButton.setOnMouseClicked(click -> {
            switch (mainDataEditMode) {
                case CREATE:
                    backendService.addUser(firstNameField.getText(), lastNameField.getText(), personalIdField.getText());
                    break;
                case UPDATE:
                    backendService.editUser(selectedUser.getId(), firstNameField.getText(), lastNameField.getText(), personalIdField.getText());
                    break;
            }
            editForm.setVisible(false);
            mainDataEditMode = EditMode.NONE;
        });
    }

    private void clearEditForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        personalIdField.setText("");
    }

    private void fillEditForm(UserData selectedUser) {
        firstNameField.setText(selectedUser.getFirstName());
        lastNameField.setText(selectedUser.getLastName());
        personalIdField.setText(selectedUser.getPersonalId());
    }

    private void configureSelectionModel() {
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // just in case you didnt already set the selection model to multiple selection.
        table.getSelectionModel().getSelectedIndices().addListener((ListChangeListener<Integer>) change -> {
            if (change.getList().size() >= 1) {
                deleteButton.setDisable(false);
                if (change.getList().size() == 1) {
                    editButton.setDisable(false);
                }
            } else {
                deleteButton.setDisable(true);
                editButton.setDisable(true);
            }
        });
    }

}
