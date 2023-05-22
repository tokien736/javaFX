package agenda.vista;

import agenda.control.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import agenda.modelo.Persona;
import agenda.control.MainApp;
import agenda.util.DateUtil;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class PersonOverviewController {
    @FXML
    private TableView<Persona> tablaPersona;
    @FXML
    private TableColumn<Persona, String> nombreColumna;
    @FXML
    private TableColumn<Persona, String> apellidoColumna;
    
    @FXML
    private Label nombreLable;
    @FXML
    private Label apellidoLable;    
    @FXML
    private Label calleLable;
    @FXML
    private Label codigoPostalLable;    
    @FXML
    private Label cuidadLable;
    @FXML
    private Label cumpleañosLable;      
    
    /**
    * Se llama cuando el usuario hace clic en el botón Eliminar.
    */
    @FXML
    private void handleDeletePerson(){
        int selectedIndex = tablaPersona.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tablaPersona.getItems().remove(selectedIndex);
        } else {
            // Nada seleccionado.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    /**
    * Se llama cuando el usuario hace clic en el botón nuevo. Abre un cuadro de diálogo para editar.
    * detalles para una nueva persona.
    */
    @FXML
    private void handleNewPerson() {
        Persona tempPerson = new Persona();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getDatoPersona().add(tempPerson);
        }
    }

    /**
    * Se llama cuando el usuario hace clic en el botón de edición. Abre un cuadro de diálogo para editar.
    * datos de la persona seleccionada.
    */
    @FXML
    private void handleEditPerson() {
        Persona selectedPerson = tablaPersona.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nada seleccionado.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    // Referencia a la aplicación principal.
    private MainApp mainApp;    
    
    /**
    * El constructor
    * Se llama al constructor antes del método initialize().
    */
    public PersonOverviewController(){        
    }
    
    /**
    * Inicializa la clase de controlador. Este método se llama automáticamente
    * después de que se haya cargado el archivo fxml.     
    */
    @FXML
    private void initialize(){
        //Inicialice la tabla de personas con las dos columnas.
        nombreColumna.setCellValueFactory(
                cellData -> cellData.getValue().nombreProperty());
        apellidoColumna.setCellValueFactory(
                cellData -> cellData.getValue().apellidoProperty());
        // Borrar detalles de la persona.
        showPersonDetails(null);
        // Escuche los cambios de selección y muestre los detalles de la persona cuando cambie.
        tablaPersona.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        
    }

    /**
    * Es llamado por la aplicación principal para devolver una referencia a sí mismo.
    *
    * @param mainApp
    */  
    
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        // Agregar datos de la lista observable a la tabla
        tablaPersona.setItems(mainApp.getDatoPersona());
    }
    
    /**
    * Rellena todos los campos de texto para mostrar detalles sobre la persona.
    * Si la persona especificada es nula, se borran todos los campos de texto.
    *
    * @param persona la persona o nula
      */    
    private void showPersonDetails(Persona persona){
        if(persona != null){
            // Rellena las etiquetas con información del objeto persona.
            nombreLable.setText(persona.getNombre());
            apellidoLable.setText(persona.getApellido());
            calleLable.setText(persona.getCalle());
            codigoPostalLable.setText(Integer.toString(persona.getCodigoPostal()));
            cuidadLable.setText(persona.getCuidad());
            // TODO: We need a way to convert the birthday into a String! 
            cumpleañosLable.setText(DateUtil.format(persona.getCumpleaños()));
        }else{
            // La persona es nula, elimina todo el texto.
            nombreLable.setText("");
            apellidoLable.setText("");
            calleLable.setText("");
            codigoPostalLable.setText("");
            cuidadLable.setText("");
            cumpleañosLable.setText("");
            
        }
    }
}
