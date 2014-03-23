package statsapp.panels;

import java.io.File;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import statsapp.data.TableData;
import statsapp.managers.DataManager;
import statsapp.popups.DiscretizationPopup;
import statsapp.popups.StatisticsPopup;
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
                    TableData tableData =
                            dManager.loadData(
                                    file.getAbsolutePath()
                            );
                    DataTable dataTabel = dManager
                            .createDataTable(tableData);

                    add(dataTabel, 0, 1);
                }
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
        
        fileMenu.getItems().addAll(load, exit);
        
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
                discretization,
                statistics
                );
        
        menuBar.getMenus().addAll(fileMenu, editMenu);
        
        return menuBar;
    }
}
