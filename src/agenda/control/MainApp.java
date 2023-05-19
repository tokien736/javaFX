package agenda.control;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import agenda.modelo.Persona;
import agenda.vista.PersonOverviewController;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
