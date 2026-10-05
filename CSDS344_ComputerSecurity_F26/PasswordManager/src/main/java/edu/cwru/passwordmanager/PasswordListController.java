package edu.cwru.passwordmanager;

import edu.cwru.passwordmanager.model.Password;
import edu.cwru.passwordmanager.model.PasswordModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PasswordListController implements Initializable {
    final private PasswordModel model = new PasswordModel();

    @FXML private ListView<Password> passwordListView;

    @FXML private TextField passwordLabel;

    @FXML private PasswordField passwordField;

    @FXML private Button deleteButton;
    @FXML private Button saveButton;

    public String passcode = null;

    public PasswordListController() {
    }

    private void enableFields() {
        passwordLabel.setDisable(false);
        passwordField.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(false);
    }
    private void disableFields() {
        passwordLabel.setDisable(true);
        passwordField.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordListView.setItems(model.getPasswords());
        passwordListView.setOnMouseClicked(mouseEvent -> {
            loadPasswordDetail();
        });
        passwordListView.setOnKeyPressed(keyEvent -> {
            loadPasswordDetail();
        });

       disableFields();
    }

    private void clearPasswordDetail() {
        passwordLabel.setText("");
        passwordField.setText("");

        disableFields();
    }

    private void loadPasswordDetail() {
        // Show the detail of the password
        int index = passwordListView.getSelectionModel().getSelectedIndex();

        Password selectedPassword = model.getPasswords().get(index);

        passwordLabel.setText(selectedPassword.getLabel());
        passwordField.setText(selectedPassword.getPassword());

        enableFields();
    }

    @FXML
    protected void copyPassword() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(passwordField.getText());
        clipboard.setContent(content);
    }

    @FXML
    protected void deleteButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this password?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> selected = alert.showAndWait();
        if (selected.isPresent() && selected.get().equals(ButtonType.YES)) {
            model.deletePassword(passwordListView.getSelectionModel().getSelectedIndex());
            clearPasswordDetail();
        }
    }

    @FXML
    protected void saveButtonClicked() {
        String label = passwordLabel.getText();
        String password = passwordField.getText();

        int selectedIndex = passwordListView.getSelectionModel().getSelectedIndex();

        model.updatePassword(new Password(label, password), selectedIndex);
    }

    @FXML
    protected void addPassword() {
        // Create new password and select last one, then load detail
        model.addPassword(new Password("New Password", ""));
        passwordListView.getSelectionModel().select(model.getPasswords().size() -1 );
        loadPasswordDetail();
    }
}
