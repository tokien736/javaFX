package agenda.control;

import agenda.modelo.Persona;
import agenda.util.DateUtil;
import com.sun.javafx.scene.control.skin.FXVK;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author PC-Jose
 */
public class PersonEditDialogController {
    @FXML
    private TextField nombreField;
    
    @FXML
    private TextField apellidoField;  
    
    @FXML
    private TextField calleField;  
    
    @FXML
    private TextField codigoPostalField;  
    
    @FXML
    private TextField cuidadField;    

    @FXML
    private TextField cumpleañosField;    
    
    private Stage dialogStage;
    private Persona persona;
    private boolean onClicked = false;
    
    /**
    * Inicializa la clase de controlador. Este método se llama automáticamente
    * después de que se haya cargado el archivo fxml.
    */
    
    private void initialize(){
        
    }
    
    /** 
    * Establece el escenario de este diálogo. 
    *
    * @param dialogStage de diálogo 
    */   
    
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    /**
    * Sets  la persona que se editará en el cuadro de diálogo.
    *
    * @param persona
    */    
    
    public void setPersona(Persona persona){
        this.persona = persona;
        
        nombreField.setText(persona.getNombre());
        apellidoField.setText(persona.getApellido());
        calleField.setText(persona.getCalle());
        codigoPostalField.setText(Integer.toString(persona.getCodigoPostal()));
        cuidadField.setText(persona.getCuidad());
        cumpleañosField.setText(DateUtil.format(persona.getCumpleaños()));
        cumpleañosField.setPromptText("dd.mm.yyyy");               
    }
    /**
    * Returns verdadero si el usuario hizo clic en Aceptar, falso en caso contrario.
    * 
    * @return
    */    
    public boolean isOkClicked(){
        return onClicked;
    }
    
    /**
    * Se llama cuando el usuario hace clic en Aceptar.
    */

    @FXML
    private void handleOK(){
        if(isInputValid()){
            persona.setNombre(nombreField.getText());
            persona.setApellido(apellidoField.getText());
            persona.setCalle(calleField.getText());
            persona.setCodigoPostal(Integer.parseInt(codigoPostalField.getText()));
            persona.setCuidad(cuidadField.getText());
            persona.setCumpleaños(DateUtil.parse(cumpleañosField.getText()));
            
            onClicked = true;
            dialogStage.close();
        }
    }
    
    /**
    * Se llama cuando el usuario hace clic en cancelar.
    */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    /**
    * Valida la entrada del usuario en los campos de texto.
    *
    * @return true si la entrada es válida
    */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nombreField.getText() == null || nombreField.getText().length() == 0) {
            errorMessage += "Sin nombre válido!\n"; 
        }
        if (apellidoField.getText() == null || apellidoField.getText().length() == 0) {
            errorMessage += "Sin apellido válido!\n"; 
        }
        if (calleField.getText() == null || calleField.getText().length() == 0) {
            errorMessage += "Sin calle válido!\n"; 
        }

        if (codigoPostalField.getText() == null || codigoPostalField.getText().length() == 0) {
            errorMessage += "Sin codigo postal válido!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(codigoPostalField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Código postal no válido (debe ser un número entero)!\n"; 
            }
        }

        if (cuidadField.getText() == null || cuidadField.getText().length() == 0) {
            errorMessage += "Sin cuidad válido!\n"; 
        }

        if (cumpleañosField.getText() == null || cumpleañosField.getText().length() == 0) {
            errorMessage += "Sin cumpleaños válido\n";
        } else {
            if (!DateUtil.validDate(cumpleañosField.getText())) {
                errorMessage += "Sin cumpleaños válido. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campos inválidos");
            alert.setHeaderText("Corrija los campos inválidos");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
