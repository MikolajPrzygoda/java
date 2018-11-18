package base;

import dataFrame.Column;
import dataFrame.DataFrame;
import base.Util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class Controller{

    public ListView<String> columnListView;
    public Pane columnMenuPane;
    public TextField columnNameTextField;
    public ComboBox columnTypeComboBox;
    public Button loadFileBtn;
    public CheckBox fileWithNamesCheckBox;
    public Button loadColumnsFromFile;
    public Label noDataFrameLabel1;
    public Label noDataFrameLabel2;
    public TableView dataFrameTableView;
    public Tab dataFrameTab;
    public TabPane mainTabPane;
    public ProgressBar fileLoadProgressBar;
    public Button chooseFileButton;
    public Label currentFileLabel;

    private DataFrame df;
    private String fileName = "";
    private int colNumber = 1;
    private int currentColumnIndex = -1;
    private ArrayList<Pair<String, String>> columnPairs = new ArrayList<>();

    public void chooseFile(ActionEvent actionEvent) {
        File file = new FileChooser().showOpenDialog( columnMenuPane.getScene().getWindow() );
        if (file == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("File not chosen");
            errorAlert.show();
            loadColumnsFromFile.setDisable(true);
        }
        else{
            fileName = file.getAbsolutePath();
            currentFileLabel.setText(file.getName());
            loadColumnsFromFile.setDisable(false);
        }
    }

    public void addColumn(ActionEvent actionEvent){
        ObservableList<String> items = columnListView.getItems();
        items.add("Column " + colNumber);
        columnPairs.add(new Pair<>("Column " + colNumber, "Integer"));
        colNumber++;
        columnListView.setItems(items);
        columnListView.getSelectionModel().selectLast();
        setCurrentColumn(items.size() - 1);
    }

    public void removeColumn(ActionEvent actionEvent){
        ObservableList<String> items = columnListView.getItems();
        if(items.size() > 0 && currentColumnIndex > -1){
            columnPairs.remove(currentColumnIndex);
            items.remove(currentColumnIndex);

            columnListView.setItems(items);
            columnListView.getSelectionModel().selectLast();

            setCurrentColumn(items.size() - 1);

            //reset default column name number if we just emptied the list
            if(items.size() == 0)
                colNumber = 1;
        }
    }

    public void loadColumns(ActionEvent actionEvent){
        try{
            String[] lines = Util.loadFile(fileName, 2);
            String isWordRegEx = "\\A[a-zA-Z]+\\Z";

            // Check if the first line contains the column names.
            String[] names = lines[0].split(",");
            boolean error = false;
            for(String name : names)
                if(!name.matches(isWordRegEx))
                    error = true;

            // If that's the case check the column types.
            if(!error){
                String[] values = lines[1].split(",");
                Class[] types = new Class[values.length];

                for(int i = 0; i < values.length; i++){
                    types[i] = Util.inferType(values[i]);
                }

                // Build pairs and add items to the ListView
                columnPairs.clear();
                for(int i = 0; i < names.length; i++){
                    columnPairs.add(new Pair<>(names[i], Util.typeToString(types[i])));
                }
                columnListView.setItems(FXCollections.observableArrayList(names));
                setCurrentColumn(columnListView.getItems().size() - 1);
                fileWithNamesCheckBox.setSelected(true);

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Successfully loaded column data from file.");
                alert.setHeaderText("Make sure column types are correct!");
                alert.showAndWait();
            }
            else{ // non-word String on the first line (at least according to isWordRegEx).
                fileWithNamesCheckBox.setSelected(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something's fucky.");
                alert.setHeaderText("Couldn't load column data from file.");
                alert.setContentText("Make sure your file has column names in it.");
                alert.showAndWait();
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not found!");
            alert.setHeaderText("Check the name you supplied for any misspelling.");
            alert.setContentText("You can look inside of directories and have to include file extension.");
            alert.showAndWait();
        }
    }

    public void listClick(MouseEvent mouseEvent){
        if(columnListView.getSelectionModel().getSelectedIndex() < 0 && columnListView.getItems().size() > 0)
            setCurrentColumn(0);
        else if(columnListView.getSelectionModel().getSelectedIndex() >= columnListView.getItems().size())
            setCurrentColumn(columnListView.getItems().size() - 1);
        else
            setCurrentColumn(columnListView.getSelectionModel().getSelectedIndex());
    }

    private void setCurrentColumn(int selected){
        currentColumnIndex = selected;
        if(selected >= 0){
            columnMenuPane.setDisable(false);
            columnNameTextField.setText(columnPairs.get(selected).first);
            columnTypeComboBox.getSelectionModel().select(typeToIndex(columnPairs.get(selected).second));
        }
        else{
            columnMenuPane.setDisable(true);
            columnNameTextField.setText("");
            columnTypeComboBox.getSelectionModel().selectFirst();
        }

        if(selected >= 0 && !fileName.isEmpty())
            loadFileBtn.setDisable(false);
        else
            loadFileBtn.setDisable(true);
    }

    public void nameChange(KeyEvent actionEvent){
        String newName = columnNameTextField.getText();
        columnPairs.get(currentColumnIndex).first = newName;
        columnListView.getItems().set(currentColumnIndex, newName);
        columnListView.refresh();
    }

    public void typeChange(ActionEvent actionEvent){
        columnPairs.get(currentColumnIndex).second = columnTypeComboBox.getSelectionModel().getSelectedItem().toString();
    }

    private int typeToIndex(String type){
        for(int i = 0; i < columnTypeComboBox.getItems().size(); i++){
            if(columnTypeComboBox.getItems().get(i).equals(type))
                return i;
        }
        return 0;
    }

    public void loadFile(ActionEvent actionEvent){
        double progress = 0;

        String[] columnNames = new String[columnListView.getItems().size()];
        Class[] columnTypes = new Class[columnListView.getItems().size()];
        for(int i = 0; i < columnPairs.size(); i++){
            var pair = columnPairs.get(i);
            columnNames[i] = pair.first;
            columnTypes[i] = Util.stringToType(pair.second);
        }

        try{
            if(fileWithNamesCheckBox.isSelected())
                df = new DataFrame(fileName, columnTypes, progress);
            else
                df = new DataFrame(fileName, columnTypes, columnNames, progress);

            onDataFrameLoad();
        }
        catch(Exception e){
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error while loading data from file.");
            alert.setContentText("Make sure column settings match data in the file.");
            alert.showAndWait();
        }

    }

    private void onDataFrameLoad(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("DataFrame loaded from file.");
        alert.showAndWait();

        setupDataFramePane();
//        setupApplyPane();
        mainTabPane.getSelectionModel().select(dataFrameTab);
    }

    private void setupDataFramePane(){
        noDataFrameLabel1.setVisible(false);
        dataFrameTableView.setVisible(true);


        Column[] columns = df.getColumns();
        dataFrameTableView.getColumns().clear();
        for (Column column : columns) {
            TableColumn tableColumn = new TableColumn(column.getName());
            dataFrameTableView.getColumns().add(tableColumn);
        }
        dataFrameTableView.refresh();

    }

    private void setupApplyPane(){
        noDataFrameLabel2.setVisible(false);
    }


}