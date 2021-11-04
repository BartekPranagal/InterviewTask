package com.pranagal.bartek.luxmedinterviewfrontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class AbstractDataPane {
    @FXML
    protected TableView table;

    @FXML
    protected Pane pane;

    @FXML
    protected Button formConfirmButton;

    @FXML
    protected VBox editForm;

    @FXML
    protected Button addButton;

    @FXML
    protected Button editButton;

    @FXML
    protected Button deleteButton;

    protected <T> TableColumn<T, String> createColumnForTable(String columnText, String propertyName) {
        TableColumn<T, String> column = new TableColumn<>(columnText);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
    }

}
