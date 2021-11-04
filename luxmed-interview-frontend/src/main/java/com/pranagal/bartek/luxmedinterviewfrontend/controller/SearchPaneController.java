package com.pranagal.bartek.luxmedinterviewfrontend.controller;

import com.pranagal.bartek.luxmedinterviewfrontend.service.BackendService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SearchPaneController {
    @FXML
    private TextField searchQuery;

    MainDataPaneController mainDataPane;

    public void initialize() {
        searchQuery.setOnKeyTyped(event -> {
            String query = searchQuery.getText();
            mainDataPane.search(query);
        });
    }


    public void configureConnectionBetweenSearchPaneAndMainPane(MainDataPaneController mainPaneController) {
        this.mainDataPane = mainPaneController;
    }
}
