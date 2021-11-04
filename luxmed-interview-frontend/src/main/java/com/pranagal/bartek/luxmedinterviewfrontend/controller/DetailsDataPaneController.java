package com.pranagal.bartek.luxmedinterviewfrontend.controller;

import com.pranagal.bartek.luxmedinterviewfrontend.dto.ShipmentData;
import com.pranagal.bartek.luxmedinterviewfrontend.dto.UserData;
import com.pranagal.bartek.luxmedinterviewfrontend.enums.EditMode;
import com.pranagal.bartek.luxmedinterviewfrontend.service.BackendService;
import javafx.collections.ListChangeListener;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.util.Objects;

public class DetailsDataPaneController extends AbstractDataPane {

    private EditMode detailsDataEditMode;
    public UserData selectedUser;
    private ShipmentData selectedShipment;
    private BackendService backendService;

    public void initialize() {
        backendService = BackendService.getInstance();
        configureDetailsPane();
    }

    private void configureDetailsPane() {
        configureTable();
        configureMainButtons();
    }

    private void configureTable() {
        table.getColumns().add(this.<ShipmentData>createColumnForTable("id", "id"));
        table.getColumns().add(this.<ShipmentData>createColumnForTable("Address", "address"));
        table.getColumns().add(this.<ShipmentData>createColumnForTable("Email", "email"));
        table.getColumns().add(this.<ShipmentData>createColumnForTable("Phone Number", "phoneNumber"));

        table.setRowFactory(tv -> {
            TableRow<ShipmentData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    this.selectedShipment = row.getItem();
                    System.out.println(selectedShipment);
                }
            });
            return row;
        });

        configureSelectionModel();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void configureMainButtons() {
        editForm.setVisible(false);
        addButton.setOnMouseClicked(event -> {
            if (Objects.nonNull(selectedShipment)) {
                editForm.setVisible(true);
                detailsDataEditMode = EditMode.CREATE;
            }
        });
        editButton.setOnMouseClicked(event -> {
            if (Objects.nonNull(selectedShipment)) {
                editForm.setVisible(true);
                detailsDataEditMode = EditMode.UPDATE;
            }
        });
        deleteButton.setOnMouseClicked(event -> {
            if (Objects.nonNull(selectedShipment)) {
                backendService.deleteShipmentAddress(selectedUser.getId(), selectedShipment.getId());
                if (table.getItems().size() == 0) {
                    this.pane.setVisible(false);
                }
            }
        });
        formConfirmButton.setOnMouseClicked(click -> {
            switch (detailsDataEditMode) {
                case CREATE:
                    backendService.addShipmentAddress(selectedUser.getId(), "firstName", "lastName", "personalId");
                    break;
                case UPDATE:
                    backendService.editShipmentAddress(selectedUser.getId(), selectedShipment.getId(), "newFirstName", "newLastName", "newPersonalId");
                    break;
            }
            ;
            editForm.setVisible(false);
            detailsDataEditMode = EditMode.NONE;
        });
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

    public void selectUser(UserData item) {
        this.selectedUser = item;
        table.setItems(backendService.getShipmentsForUser(selectedUser.getId()));
        pane.setVisible(true);

    }
}
