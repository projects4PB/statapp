package statsapp.popups;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CreateObjectPopup extends BasePopup
{
	private static final String METRIC_EUKLIDES =
		"odległość Euklidesowa";
	private static final String METRIC_MAHALANOBIS =
		"odległość Mahalanobisa";
	private static final String METRIC_L_INFINITY =
		"metryka L_nieskończoność";
	private static final String METRIC_MANHATTAN =
		"metryka Manhattan";

	public CreateObjectPopup()
	{
		super(new GridPane(), "NOWY OBIEKT");

        GridPane contentPane = (GridPane) this.getRootNode();
        
        contentPane.setPrefSize(500, 400);
        
        contentPane.getStyleClass().add("create-object");
        
        Label titleLabel = this.getTitleLabel();
        
        titleLabel.setPrefSize(480, 25);
        
        contentPane.add(titleLabel, 0, 0, 2, 1);

		ScrollPane scrollPane = new ScrollPane();

		scrollPane.setPrefSize(480, 300);

		scrollPane.setContent(new Label("LABEL"));

		contentPane.add(scrollPane, 0, 1, 2, 1);

		Label metricType = new Label("Metryka:");

		metricType.setPrefSize(200, 25);

        ComboBox<String> comboBox = new ComboBox<>();
        
        ObservableList<String> comboItems = comboBox.getItems();
        
        comboItems.add(METRIC_EUKLIDES);
        comboItems.add(METRIC_MAHALANOBIS);
        comboItems.add(METRIC_MANHATTAN);
        comboItems.add(METRIC_L_INFINITY);
        
		comboBox.setPrefSize(200, 25);
        
        comboBox.valueProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue ov, String t, String t1)
            {                
            }    
        });
		contentPane.add(metricType, 0, 2);
		contentPane.add(comboBox, 1, 2);
        
		Label neighbourNumber = new Label(
				"Ilość sąsiadujących obiektów:");

		neighbourNumber.setPrefSize(300, 25);

		TextField textField = new TextField();

		textField.setPrefSize(100, 25);

		contentPane.add(neighbourNumber, 0, 3);
		contentPane.add(textField, 1, 3);

		this.getContent().add(contentPane);
	}
}
