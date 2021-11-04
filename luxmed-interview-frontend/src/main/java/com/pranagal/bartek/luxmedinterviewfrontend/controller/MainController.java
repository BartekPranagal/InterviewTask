package com.pranagal.bartek.luxmedinterviewfrontend.controller;

import javafx.fxml.FXML;

public class MainController {

    @FXML
    private SearchPaneController searchPaneController;

    @FXML
    private MainDataPaneController mainPaneController;

    @FXML
    private DetailsDataPaneController detailsPaneController;


    public void initialize() {
        detailsPaneController.pane.setVisible(false);
        searchPaneController.configureConnectionBetweenSearchPaneAndMainPane(mainPaneController);
        mainPaneController.configureConnectionBetweenMainAndDetailsPane(detailsPaneController);
    }


}
