package statsapp.popups;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import statsapp.data.records.TableRecord;
import statsapp.managers.DataManager;

/**
 *
 * @author Adrian Olszewski
 */
public class DiscretizationPopup extends BasePopup
{
    public DiscretizationPopup()
    {
        super(new GridPane(), "DYSKRETYZACJA");

        GridPane contentPane = (GridPane) this.getRootNode();
        
        contentPane.setPrefSize(400, 100);
        
        contentPane.getStyleClass().add("discretization");
        
        Label titleLabel = this.getTitleLabel();
        
        titleLabel.setPrefSize(380, 25);
        
        contentPane.add(titleLabel, 0, 0, 2, 1);
        
        Label fieldLabel = new Label(
                "Podaj ilość przedziałów:"
        );
        fieldLabel.setPrefSize(185, 25);
        
        TextField textField = new TextField();
        
        textField.setPrefSize(185, 25);
        
        contentPane.add(fieldLabel, 0, 1);
        contentPane.add(textField, 1, 1);

        Button okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                DataManager dManager = DataManager.getInstance();

				ArrayList<Object> colData = new ArrayList<>();
				for(int i = 0; i < dManager.getDataList().size(); i++)
				{
					colData.add(3.0f);
				}
				dManager.setLastColumnData("std_", colData);

				dManager.getDataTable().addColumn("std_");

                
                hide();
            }
        });
        okButton.setPrefSize(185, 25);
        
        Button cancelButton = new Button("ANULUJ");
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                hide();
            }
        });
        cancelButton.setPrefSize(185, 25);
        
        contentPane.add(okButton, 0, 2);
        contentPane.add(cancelButton, 1, 2);
        
        this.getContent().add(contentPane);
        
        DataManager dManager = DataManager.getInstance();
        
        //dManager.digitizeValues("values");
        
        //dManager.divideValues("values", 3);
        
        //dManager.getStandardization("seplen");
          dManager.mapColumValues("seplen", 3.0f, 5.0f);
    }
}
