package agenda.control;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import agenda.modelo.Persona;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author PC-Jose
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;    

    /**
    * Los datos como listado observable de Personas.
    */    
    private ObservableList<Persona> datoPersona = FXCollections.observableArrayList();

    /**
     * Cosntructor
     */
    
    public MainApp(){
        //Agregando algunos datos de muestra        
        datoPersona.add(new Persona("Hans", "Muster"));
	datoPersona.add(new Persona("Ruth", "Mueller"));
	datoPersona.add(new Persona("Heinz", "Kurz"));
	datoPersona.add(new Persona("Cornelia", "Meier"));
	datoPersona.add(new Persona("Werner", "Meyer"));
	datoPersona.add(new Persona("Lydia", "Kunz"));
	datoPersona.add(new Persona("Anna", "Best"));
	datoPersona.add(new Persona("Stefan", "Meier"));
	datoPersona.add(new Persona("Martin", "Mueller"));
    }
    /**
     * Devuelve los datos como una lista observable de Personas.
     * @return
     */
    public ObservableList<Persona> getDatoPersona(){
        return datoPersona;
    }
    
    
    
    
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AgendaApp");
        
        initRootLayout();
        showPersonOverview();
    }
    /**
    * Inicializa el diseño raíz.
    */
    private void initRootLayout() {
        try {          
            // Carga el diseño raíz desde el archivo fxml. 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/agenda/vista/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Muestra la escena que contiene el diseño raíz.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * Muestra la descripción general de la persona dentro del diseño raíz.
    */    

    private void showPersonOverview() {
        try {
            // Cargar descripción general de la persona.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/agenda/vista/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Establecer la descripción general de la persona en el centro del diseño raíz.
            rootLayout.setCenter(personOverview); 
            //Dale al controlador acceso a la aplicación principal.
            PersonOverviewController controlador = loader.getController();
            controlador.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    * Devuelve la preferencia de archivo de persona, es decir, el archivo que se abrió por última vez.
    * La preferencia se lee desde el registro específico del sistema operativo. si no hay tal
    * se puede encontrar la preferencia, se devuelve nulo.
    *
    * @return
    */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    /**
    * Establece la ruta del archivo del archivo actualmente cargado. El camino se mantiene en
    * el registro específico del sistema operativo.
    *
    * @param file el archivo o nulo para eliminar la ruta
    */

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }
    
    /**
    * Abre un cuadro de diálogo para editar los detalles de la persona especificada. Si el usuario
    * hace clic en Aceptar, los cambios se guardan en el objeto de persona proporcionado y son verdaderos
    * es regresado.
    *
    * @param persona el objeto persona a editar
    * @return verdadero si el usuario hizo clic en Aceptar, falso en caso contrario.
    */
    
    public boolean showPersonEditDialog(Persona persona){
        try{
            // Cargue el archivo fxml y cree una nueva etapa para el cuadro de diálogo emergente.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/agenda/vista/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Crear el escenario de diálogo.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

           // Establecer la persona en el controlador.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPersona(persona);

            // Mostrar el diálogo y esperar hasta que el usuario lo cierre
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            e.printStackTrace();
            return false;            
        }
    }
   
        /**
        * Vuelve al escenario principal.
        * @return
        */
        public Stage getPrimaryStage(){
            return primaryStage;
        }
    public static void main(String[] args) {
        launch(args);
    } 
}
