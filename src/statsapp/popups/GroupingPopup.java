/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statsapp.popups;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import statsapp.data.TableData;
import statsapp.loaders.ExcelFileLoader;
import statsapp.loaders.TextFileLoader;
import statsapp.managers.AreaManager;
import statsapp.managers.DataManager;
import statsapp.models.MetricType;
import statsapp.panels.RootPanel;
import statsapp.tables.DataTable;

/**
 *
 * @author admin
 */
public class GroupingPopup extends BasePopup {
      private static final String METRIC_EUKLIDES =
            "odległość Euklidesowa";
    private static final String METRIC_MAHALANOBIS =
            "odległość Mahalanobisa";
    private static final String METRIC_L_INFINITY =
            "metryka L_nieskończoność";
    private static final String METRIC_MANHATTAN =
            "metryka Manhattan"; 

    private MetricType metricChoice ;
    
    private String metodGrouping = "";
    private String columnName = "";
    private int countClass ;
    private static File choosedFile;
    
    String numberOfNeighbours = "";
    
    private final DataManager dManager = DataManager.getInstance();
    private final AreaManager areaManager = AreaManager.getInstance();
    
    public GroupingPopup()
    {
        super(new GridPane(), "Grupowanie");

        GridPane contentPane = (GridPane) this.getRootNode();
        
        contentPane.setPrefSize(400, 200);
        
        contentPane.getStyleClass().add("create-object");
               
        Label metodGroupingLabel = new Label("Wybierz metode grupowania:");
        metodGroupingLabel.setPrefSize(230, 25);
        contentPane.add(metodGroupingLabel, 0, 2);
        
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> comboItems = comboBox.getItems();
        
        comboItems.add("k-srednia");
        comboItems.add("k-medoidow");
        
	comboBox.setPrefSize(250, 25);
        
        comboBox.valueProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue ov, String t, String t1)
            {
                switch(t1)
                {
                    case "k-srednia" :
                        metodGrouping = "k-srednia";
                    break;

                    case "k-medoidow":
                         metodGrouping = "k-medoidow";
                    break;                            
                }   
            }    
        });
        
        contentPane.add(comboBox, 1, 2);
            
        Label countClassLabel = new Label("Podaj ilość klas:");
        countClassLabel.setPrefSize(230, 25);
        contentPane.add(countClassLabel, 0, 3);
        
        TextField textFielCountClass = new TextField();
        textFielCountClass.setPrefSize(100, 25);
        contentPane.add(textFielCountClass, 1, 3);
        
        
        textFielCountClass.textProperty().addListener(
                        new ChangeListener<String>()
        {
                @Override
                public void changed(ObservableValue<? extends String> ov,
                        String oldVal, String newVal)
                {
                    countClass = Integer.parseInt(newVal);
                }
        });
        
        
        
        Label columnNameClassLabel = new Label("Podaj nazwę kolumny z klasą");
        columnNameClassLabel.setPrefSize(230, 25);
        contentPane.add(columnNameClassLabel, 0, 4);
        
        TextField textFieldcolumnNameClass = new TextField();
        textFieldcolumnNameClass.setPrefSize(100, 25);
        contentPane.add(textFieldcolumnNameClass, 1, 4);  
        
        textFieldcolumnNameClass.textProperty().addListener(
                        new ChangeListener<String>()
        {
                @Override
                public void changed(ObservableValue<? extends String> ov,
                        String oldVal, String newVal)
                {
                    columnName = newVal;
                }
        });
                  
        
        final Label choseFileName = new Label("");
        choseFileName.setPrefSize(200, 25);
        contentPane.add(choseFileName, 1, 1);
          
        Button chooseFileButton = new Button("wybierz plik");
        
        chooseFileButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                FileChooser fileChooser = new FileChooser();
                choosedFile = fileChooser.showOpenDialog(
                        getScene().getWindow()
                );
                
                if(choosedFile != null)
                {
                    int index = choosedFile.getAbsolutePath().lastIndexOf('.');
                    
                    if(index > 0)
					{
                        String extension = choosedFile.getAbsolutePath()
								.substring(index + 1);
						
						if(extension.equals("xlsx")
							|| extension.equals("xls"))
						{
							DataManager.setDataLoader(
									new ExcelFileLoader()
							);
						}
						else
						{
							DataManager.setDataLoader(
									new TextFileLoader()
							);
						}
                        choseFileName.setText(choosedFile.getName());
                    }
                }               
            }
        });
        
        

        
        
        Button loadDataButton = new Button("wczytaj dane");
        loadDataButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                TableData tableData = DataManager
                .getInstance().loadData(
                        choosedFile.getAbsolutePath(), true, columnName
                                    );
                DataTable dataTable = dManager
                        .createDataTable(tableData);

                                    areaManager.addTableDataToAreaObjects();

                RootPanel panelInstance =  RootPanel.panelInstance;                     
                panelInstance.add(dataTable, 0, 1); 
            }
        });
        
        Button cancelButton = new Button("anuluj");
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                hide();
            }
        });
        
        
        chooseFileButton.setPrefSize(185, 25); 
        loadDataButton.setPrefSize(185, 25); 
        cancelButton.setPrefSize(185, 25); 
        contentPane.add(chooseFileButton, 0, 1);
        contentPane.add(loadDataButton, 0, 5);
        contentPane.add(cancelButton, 1, 5);
        this.getContent().add(contentPane);
    }  
}
