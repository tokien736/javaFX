package agenda.vista;

import agenda.control.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import agenda.modelo.Persona;
import agenda.control.MainApp;



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
        nombreColumna.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        apellidoColumna.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        
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
}
