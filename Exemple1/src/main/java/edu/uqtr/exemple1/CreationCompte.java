package edu.uqtr.exemple1;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class CreationCompte {
    @FXML
    private TextField choixNomUtilisateur;

    @FXML
    private Text retroactionNomUtilisateur;

    @FXML
    private TextField choixMdp;

    @FXML
    private Text retroactionChoixMdp;

    @FXML
    private TextField confirmationMdp;

    @FXML
    private Text retroactionConfirmationMdp;

    @FXML
    private Text retroactionGlobale;

    @FXML
    private void onConfirmation(Event e) {
        retroactionGlobale.setText("Aucune erreur dans le formulaire");
        retroactionGlobale.getStyleClass().add("validation-retroaction");

        confirmerNomUtilisateur(e);
        confirmerMdp(e);
        confirmerConfirmationMdp(e);
    }

    @FXML
    private void confirmerNomUtilisateur(Event e) {
        if(choixNomUtilisateur.getText().length() < 5 || choixNomUtilisateur.getText().isBlank()) {
            retroactionGlobale.setText("Erreur dans le formulaire");
            retroactionGlobale.getStyleClass().remove("validation-retroaction");
            retroactionGlobale.getStyleClass().add("erreur-retroaction");
        }
    }

    @FXML
    private void confirmerMdp(Event e) {
        String mdp = choixMdp.getText();
        if(mdp.length() < 6 || mdp.isBlank() || mdp.matches("\\d") || mdp.matches("\\w")) {
            retroactionGlobale.setText("Erreur dans le formulaire");
            retroactionGlobale.getStyleClass().remove("validation-retroaction");
            retroactionGlobale.getStyleClass().add("erreur-retroaction");
        }
    }

    @FXML
    private void confirmerConfirmationMdp(Event e) {
        if(!choixMdp.getText().equals(confirmationMdp.getText())) {
            retroactionGlobale.setText("Erreur dans le formulaire");
            retroactionGlobale.getStyleClass().remove("validation-retroaction");
            retroactionGlobale.getStyleClass().add("erreur-retroaction");
        }
    }
}