package statsapp.panels;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import statsapp.data.TableData;
import statsapp.loaders.ExcelFileLoader;
import statsapp.loaders.TextFileLoader;
import statsapp.managers.DataManager;
import statsapp.popups.ChartPopup;
import statsapp.popups.DiscretizationPopup;
import statsapp.popups.StatisticsPopup;
import statsapp.savers.DataSaver;
import statsapp.tables.DataTable;

/**
 *
 * @author Adrian Olszewski
 */
public class RootPanel extends GridPane
{
    private final DataManager dManager = DataManager.getInstance();
    
    public RootPanel()
    {
        MenuBar menuBar = this.createMenuBar();
        
        this.add(menuBar, 0, 0);
    }
    
    private MenuBar createMenuBar()
    {
        MenuBar menuBar = new MenuBar();
        
        menuBar.setPrefSize(800, 25);
        
        Menu fileMenu = new Menu("Plik");
        
        MenuItem load = new MenuItem("Załaduj dane");
        load.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(
                        getScene().getWindow()
                );
                
                if(file != null)
                {
                    int index = file.getAbsolutePath().lastIndexOf('.');
                    
                    if(index > 0)
					{
                        String extension = file.getAbsolutePath()
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
                    }
					TableData tableData = DataManager
						.getInstance().loadData(
							file.getAbsolutePath()
					);
                    DataTable dataTabel = dManager
                            .createDataTable(tableData);

                    add(dataTabel, 0, 1);
                }               
            }
        });
        
        MenuItem save = new MenuItem("Zapisz");
        final Stage primaryStage = null;
        final DataSaver dataSaver = new DataSaver();
        save.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(primaryStage) ;   
                dataSaver.saveToFile(file.getAbsolutePath());
                
            }
        });
        
        MenuItem exit = new MenuItem("Zakończ");
        exit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        fileMenu.getItems().addAll(load, save, exit);
        
        Menu editMenu = new Menu("Edycja");
        
        MenuItem discretization = new MenuItem("Dyskretyzacja");
        discretization.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                DiscretizationPopup popup =
                        new DiscretizationPopup();

                popup.show(getScene().getWindow());
            }
        });
        
		MenuItem chart2D = new MenuItem("Wykres 2D");
        chart2D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                ChartPopup popup = new ChartPopup();

                popup.show(getScene().getWindow());
            }
        });

        MenuItem statistics = new MenuItem("Statystyki");
        statistics.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                StatisticsPopup popup =
                        new StatisticsPopup();

                popup.show(getScene().getWindow());
            }
        });
        
        editMenu.getItems().addAll(
				chart2D,
                discretization,
                statistics
                );
        
        menuBar.getMenus().addAll(fileMenu, editMenu);
        
        return menuBar;
    }
}
