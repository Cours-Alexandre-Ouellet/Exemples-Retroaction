package edu.uqtr.exemple3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.function.Function;

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
        boolean valeurValide = true;

        // Réinitialisation du texte de rétroaction
        retroactionNomUtilisateur.setText("");

        // Ne peut pas comporter que des blancs
        valeurValide &= validerTextField(choixNomUtilisateur, (str) -> !str.isBlank(), retroactionNomUtilisateur,
                "Le nom d'utilisateur ne doit pas être vide.");

        // Longueur minimale de nom d'utilisateur
        valeurValide &= validerTextField(choixNomUtilisateur, (str) -> str.length() >= MIN_CARACTERE_NOM_UTILISATEUR,
                retroactionNomUtilisateur, String.format("Le nom d'utilisateur doit avoir au moins %d caractères.",
                        MIN_CARACTERE_NOM_UTILISATEUR));

        if (valeurValide) {
            affecterStyleSucces(choixNomUtilisateur);
        }

        erreurDetectee |= !valeurValide;
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
        boolean valeurValide = true;

        // On efface les vieux message de rétroaction
        retroactionChoixMdp.setText("");

        // Condition sur les blancs
        valeurValide &= validerTextField(choixMdp, (str) -> !str.contains("(.*)\\s(.*)"), retroactionChoixMdp,
                "Le mot de passe ne doit pas contenir de caractères blancs.");

        // Conditions sur la longueur
        valeurValide &= validerTextField(choixMdp, (str) -> str.length() >= MIN_CARACTERE_MDP, retroactionChoixMdp,
                String.format("Le mot de passe ne voir au moins " +
                        MIN_CARACTERE_MDP + " caractères."));

        // Doit contenir un nombre
        valeurValide &= validerTextField(choixMdp, (str) -> str.matches("(.*)[0-9](.*)"), retroactionChoixMdp,
                "Le mot de passe doit comporter au moins un nombre");

        // Doit contenir une lettre
        valeurValide &= validerTextField(choixMdp, (str) -> str.matches("(.*)[a-zA-Z](.*)"), retroactionChoixMdp,
                "Le mot de passe doit comporter au moins une lettre");

        // Pas d'erreur
        if (valeurValide) {
            affecterStyleSucces(choixMdp);
        }

        // Opérateur ou égal (comme un +=, mais avec un opérateur booléen)
        erreurDetectee |= !valeurValide;
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

        // Vérification que les mots de passe sont identique
        boolean valeurValide = validerTextField(confirmationMdp, (str) -> choixMdp.getText().equals(str),
                retroactionConfirmationMdp,
                "Les mots de passe doivent être identiques");

        if (valeurValide) {
            affecterStyleSucces(confirmationMdp);
        }

        erreurDetectee |= !valeurValide;
    }

    /**
     * Valide la valeur d'un TextField selon une fonction indiquée en paramètres. En cas d'erreur, un message d'erreur
     * s'inscrit dans le champ texte spécifié.
     *
     * @param champ            Le champs TextField à valider.
     * @param condition        La fonction de validation à appliquer au contenu (String vers Boolean). True signifie
     *                         que la chaîne est valide, False qu'il y a une erreur.
     * @param champRetroaction Le champs dans lequel indiquer le message d'erreur.
     * @param messageErreur    Le message d'erreur correspondant à la vérification.
     * @return True si la valeur entrée est valide, False s'il y a une erreur. Cela correspond à la valeur retournée
     * par condition
     */
    private boolean validerTextField(TextField champ, Function<String, Boolean> condition, Text champRetroaction,
                                     String messageErreur) {
        String valeur = champ.getText();
        boolean valeurValide = condition.apply(valeur);

        if (!valeurValide) {
            ajouterLigneTexte(champRetroaction, messageErreur);
            affecterStyleErreur(champ);
        }

        return valeurValide;
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