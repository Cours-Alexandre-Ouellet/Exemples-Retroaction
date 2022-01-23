package edu.uqtr.exemple2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Formulaire de création de compte d'utilisateur
 */
public class CreationCompte {
    // Constantes - Nombres de caractères requis
    private static final int MIN_CARACTERE_NOM_UTILISATEUR = 5;
    private static final int MIN_CARACTERE_MDP = 6;

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

    // Indique lors de la validation globale si une erreur est détecteé
    private boolean erreurDetectee;

    /**
     * Méthode d'initilisation de javaFX
     */
    @FXML
    private void initialize() {
        // On n'a pas d'événement pour gérer le focus, donc on doit l'implémenter manuellement
        // Avec implémentation de classe anonyme
        choixNomUtilisateur.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    validerNomUtilisateur(null);
                }
            }
        });

        // Avec lambda expression
        choixMdp.focusedProperty().addListener((observale, oldValue, newValue) -> {
            if (!newValue) validerMdp(null);
        });
        confirmationMdp.focusedProperty().addListener((observale, oldValue, newValue) ->
        {
            if (!newValue) validerConfirmationMdp(null);
        });
    }

    // Note de sécurité : il est recommandée de toujours tout revalider lors de la soumission de données.

    /**
     * Gestionnaire d'événement lors de la soumission du formulaire. Revalide tous les champs.
     *
     * @param e paramètres d'événement
     */
    @FXML
    private void onSoumission(Event e) {
        // Réinitialise la détection d'erreur
        erreurDetectee = false;

        // Validation de chaque champ
        validerNomUtilisateur(e);
        validerMdp(e);
        validerConfirmationMdp(e);

        // Affichage d'une rétroaction pour le clic sur le bouton soumission
        if (!erreurDetectee) {
            retroactionGlobale.setText("Aucune erreur dans le formulaire");
        } else {
            retroactionGlobale.setText("Veuillez corrgier les erreurs avant de soumettre le formulaire");
        }
    }

    /**
     * Valide le nom d'utilisateur. Ce dernier doit satisfaire les conditions suivantes :
     * - Ne pas être composé uniquement d'espaces
     * - Être de longueur minimale définie par {@link #MIN_CARACTERE_NOM_UTILISATEUR}
     *
     * @param e paramètre d'événement
     */
    @FXML
    private void validerNomUtilisateur(Event e) {
        String nomUtilisateur = choixNomUtilisateur.getText();
        boolean erreurLocale = false;

        // Réinitialisation du texte de rétroaction
        retroactionNomUtilisateur.setText("");

        if (nomUtilisateur.isBlank()) {
            ajouterLigneTexte(retroactionNomUtilisateur,
                    String.format("Le nom d'utilisateur ne doit pas être vide.", MIN_CARACTERE_NOM_UTILISATEUR));
            affecterStyleErreur(choixNomUtilisateur);
            erreurLocale = true;
        } else if (nomUtilisateur.length() < MIN_CARACTERE_NOM_UTILISATEUR) {
            ajouterLigneTexte(retroactionNomUtilisateur,
                    String.format("Le nom d'utilisateur doit avoir au moins %d caractères.", MIN_CARACTERE_NOM_UTILISATEUR));
            affecterStyleErreur(choixNomUtilisateur);
            erreurLocale = true;
        }

        if (!erreurLocale) {
            affecterStyleSucces(choixNomUtilisateur);
        }
        erreurDetectee |= erreurLocale;
    }

    /**
     * Valide le mot de passe. Un mot de passe doit respecter les éléments suivants :
     * - Ne pas être comportés de caractères blancs
     * - Avoir une longueur tel que définie par {@link #MIN_CARACTERE_MDP }
     * - Comporter au moins un nombre
     * - Comporter au moins une lettre
     *
     * @param e paramètre d'événement
     */
    @FXML
    private void validerMdp(Event e) {
        String mdp = choixMdp.getText();
        boolean erreurLocale = false;

        // On efface les vieux message de rétroaction
        retroactionChoixMdp.setText("");

        // Condition sur les blancs
        if (mdp.contains("(.*)\\s(.*)")) {
            ajouterLigneTexte(retroactionChoixMdp, "Le mot de passe ne doit pas contenir de caractères blancs.");
            affecterStyleErreur(choixMdp);
            erreurLocale = true;
        }
        // Conditions sur la longueur
        if (mdp.length() < MIN_CARACTERE_MDP) {
            ajouterLigneTexte(retroactionChoixMdp, String.format("Le mot de passe ne voir au moins " +
                    MIN_CARACTERE_MDP + " caractères."));
            affecterStyleErreur(choixMdp);
            erreurLocale = true;
        }
        // Doit contenir un nombre
        if (!mdp.matches("(.*)[0-9](.*)")) {
            ajouterLigneTexte(retroactionChoixMdp, "Le mot de passe doit comporter au moins un nombre");
            affecterStyleErreur(choixMdp);
            erreurLocale = true;
        }
        // Doit contenir une lettre
        if (!mdp.matches("(.*)[a-zA-Z](.*)")) {
            ajouterLigneTexte(retroactionChoixMdp, "Le mot de passe doit comporter au moins une lettre");
            affecterStyleErreur(choixMdp);
            erreurLocale = true;
        }

        // Pas d'erreur
        if (!erreurLocale) {
            affecterStyleSucces(choixMdp);
        }

        // Opérateur ou égal (comme un +=, mais avec un opérateur booléen)
        erreurDetectee |= erreurLocale;
    }

    /**
     * Validation du champ de confirmation du mot de passe.
     *
     * @param e paramètres d'événement
     */
    @FXML
    private void validerConfirmationMdp(Event e) {
        // Réinitialisation des messages
        retroactionConfirmationMdp.setText("");

        // Une seule erreur possible: les mots de passe diffères
        if (!choixMdp.getText().equals(confirmationMdp.getText())) {
            ajouterLigneTexte(retroactionConfirmationMdp, "Les mots de passe doivent être identiques");
            affecterStyleErreur(confirmationMdp);
            erreurDetectee = true;
        } else {
            affecterStyleSucces(confirmationMdp);
        }
    }

    /**
     * Effecte le style d'erreur au noeud (erreur-retroaction).
     *
     * @param noeud le noeud auquel affecter le style.
     */
    private void affecterStyleErreur(Node noeud) {
        noeud.getStyleClass().remove("validation-retroaction");
        noeud.getStyleClass().add("erreur-retroaction");
    }

    /**
     * Effecte le style de succès au noeud (succès-retroaction).
     *
     * @param noeud le noeud auquel affecter le style.
     */
    private void affecterStyleSucces(Node noeud) {
        noeud.getStyleClass().remove("erreur-retroaction");
        noeud.getStyleClass().add("validation-retroaction");
    }

    /**
     * Ajoute une ligne au message de rétroaction pour le formulaire. Permet d'avoir plusieurs messages en même temps.
     *
     * @param texte   le texte de rétroaction (objet FXML).
     * @param message le message de rétroaction à ajouter.
     */
    private void ajouterLigneTexte(Text texte, String message) {
        // Pas de message
        if (texte.getText().isEmpty()) {
            texte.setText(message);
        } else {
            // Il y a déjà des messages, donc on fait un saut de ligne.
            texte.setText(texte.getText() + '\n' + message);
        }
    }
}