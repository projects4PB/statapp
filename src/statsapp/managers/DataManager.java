package statsapp.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import statsapp.data.TableData;
import statsapp.data.records.TableRecord;
import statsapp.loaders.TextFileLoader;
import statsapp.models.Division;
import statsapp.tables.DataTable;

/**
 *
 * @author Adrian Olszewski, Dariusz Obuchowski
 */
public class DataManager
{
    private static volatile DataManager instance = null;
    
    private final ObservableList dataList;
    
    private TableData tableData;
    
    private TextFileLoader dataLoader;
    
    private DataTable dataTable;
    
    private DataManager(TextFileLoader dataLoader)
    {
        this.dataLoader = dataLoader;
        
        this.dataList = FXCollections.observableArrayList();
    }
    
    public static DataManager getInstance()
    {
        if (instance == null)
        {
            synchronized(DataManager.class)
            {
                if(instance == null)
                {
                    instance = new DataManager(new TextFileLoader());
                }
            }
        }
        return instance;
    }
    
    public void setDataLoader(TextFileLoader dataLoader)
    {
        this.dataLoader = dataLoader;
    }
    
    public TextFileLoader getDataLoader()
    {
        return this.dataLoader;
    }
    
    public ObservableList getDataList()
    {
        return this.dataList;
    }
    
    public DataTable createDataTable(TableData tableData)
    {
        this.dataTable = new DataTable();
        
        String[] colNames = tableData.getColumnsNames();
        
        this.dataTable.setColumns(colNames);
        this.dataTable.setItems(dataList);
        
        return this.dataTable;
    }
    
    public DataTable getDataTable()
    {
        return this.dataTable;
    }
    
    public TableData loadData(String fileName)
    {
        this.tableData = this.dataLoader.loadData(fileName);
        
        this.dataList.addAll(tableData.getRecords());
        
        return tableData;
    }
    
    public ArrayList<Object> getColumnData(String col_id)
    {
        ArrayList<Object> columnData = new ArrayList<>();
        
        for(Object col : this.dataTable.getColumns())
        {
            TableColumn tableCol = ((TableColumn) col);
            
            if(tableCol.getId().equals(col_id))
            {
                for(int i = 0; i < this.dataList.size(); i++)
                {
                    columnData.add(tableCol.getCellData(i));
                }
            }
        }
        return columnData;
    }
    
    private void updateColumnData(String col_id, List<Object> colData)
    {
        for(int i = 0; i < this.dataList.size(); i++)
        {
            TableRecord tableRecord = (TableRecord) this.dataList.get(i);
            
            tableRecord.getRecordData().setFieldValue(col_id, colData.get(i));
        }
    }
    
    private ArrayList<Float> getNumbericData(List<Object> values)
    {
        ArrayList<Float> numbericData = new ArrayList<>();
        
        for(Object obj : values)
        {
            numbericData.add((float) obj);
        }
        return numbericData;
    }
    
    private boolean isNumbericColumn(String col_id)
    {
        for(Object col : this.dataTable.getColumns())
        {
            TableColumn tableCol = ((TableColumn) col);
            
            if(tableCol.getId().equals(col_id))
            {
                
            }
        }
        return true;
    }
    
    private boolean isTextDataColumn(String col_id)
    {
        for(Object col : this.dataTable.getColumns())
        {
            TableColumn tableCol = ((TableColumn) col);
            
            if(tableCol.getId().equals(col_id))
            {
                
            }
        }
        return true;
    }
    
    private ArrayList<Object> sortColumnValues(List<Object> colData)
    {
        ArrayList<Float> colValues = this.getNumbericData(colData);
        
        Collections.sort(colValues);
        
        ArrayList<Object> sortedData = new ArrayList<>();
        
        for(Float val : colValues)
        {
            sortedData.add((Object) val);
        }
        return sortedData;
    }
    
    public float getAverage(String col_id)
    {
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        if(!this.isNumbericColumn(col_id)) return -1;
        
        float amount = 0;
        
        for(Object obj : colData)
        {
            amount += (float) obj;
        }
        return amount / this.dataList.size();
    }
    
    public float getMedian(String col_id)
    {
        if(!this.isNumbericColumn(col_id)) return -1;
        
        ArrayList<Object> colData = this.getColumnData(col_id);

        ArrayList<Object> sortedData = this.sortColumnValues(colData);
        
        if(sortedData.size() % 2 == 0)
        { 
            int secondValueIndex = sortedData.size() / 2;
            int firstValueIndex = secondValueIndex - 1;
            
            float firstValue = (float) sortedData.get(firstValueIndex);
            
            float secondValue = (float) sortedData.get(secondValueIndex);
            
            return (firstValue + secondValue) / 2;
        }
        else return (float) sortedData.get(sortedData.size() / 2);
    }
    
    public float getMedian(List<Object> values)
    {
        ArrayList<Object> sortedData =
                this.sortColumnValues(values);
        
        if(sortedData.size() % 2 == 0)
        { 
            int secondValueIndex = sortedData.size() / 2;
            int firstValueIndex = secondValueIndex - 1;
            
            float firstValue = (float) sortedData.get(firstValueIndex);
            
            float secondValue = (float) sortedData.get(secondValueIndex);
            
            return (firstValue + secondValue) / 2;
        }
        else return (float) sortedData.get(sortedData.size() / 2);
    }
    
    public float getMinValue(String col_id)
    {
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        if(!this.isNumbericColumn(col_id)) return -1;
        
        float minValue = (float) colData.get(0);
        
        for(Object obj : colData)
        {
            if(((float) obj) < minValue) minValue = (float) obj;
        }
        return minValue;
    }
    
    public float getMaxValue(String col_id)
    {
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        if(!this.isNumbericColumn(col_id)) return -1;
        
        float maxValue = (float) colData.get(0);
        
        for(Object obj : colData)
        {
            if(((float) obj) > maxValue) maxValue = (float) obj;
        }
        return maxValue;
    }
    
    public float getQ1Value(String col_id)
    {
        if(!this.isNumbericColumn(col_id)) return -1;
        
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        ArrayList<Object> sortedData =
                this.sortColumnValues(colData);
        
        List<Object> subListData = new ArrayList<>();
        
        if(this.dataList.size() % 2 == 0)
        {
            subListData = sortedData.subList(0,
                    this.dataList.size() / 2);
            
            return this.getMedian(subListData);
        }
        else
        {
            subListData = colData.subList(
                    0, (this.dataList.size() / 2) + 1
            );
            return this.getMedian(subListData);
        }
    }
    
    public float getQ3Value(String col_id)
    {
        if(!this.isNumbericColumn(col_id)) return -1;
        
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        ArrayList<Object> sortedData =
                this.sortColumnValues(colData);
        
        List<Object> subListData = new ArrayList<>();
        
        if(this.dataList.size() % 2 == 0)
        {
            subListData = sortedData.subList(
                    this.dataList.size() / 2,
                    this.dataList.size());
            
            return this.getMedian(subListData);
        }
        else
        {
            subListData = colData.subList(
                    (this.dataList.size() / 2),
                    this.dataList.size()
            );
            return this.getMedian(subListData);
        }
    }
    
    public float getPercentileValue(String col_id, float cValue)
    {
        ArrayList<Object> colData = this.getColumnData(col_id);
        ArrayList<Object> sortedValues =
                this.sortColumnValues(colData);

        float tValue = cValue * (sortedValues.size() - 1);

        int iValue = (int) tValue;
        
        return (iValue - tValue + 1)
                * (float) sortedValues.get(iValue)
                + (tValue - iValue)
                * (float) sortedValues.get(iValue + 1);
    }
    
    public void digitizeValues(String col_id)
    {
        if(!this.isTextDataColumn(col_id)) return;
        
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        HashMap<String, Integer> mappedValues = new HashMap<>();
        
        ArrayList<Object> resultData = new ArrayList<>();
        
        for(Object obj : colData)
        {
            if(!mappedValues.containsKey(obj.toString()))
            {
                if(mappedValues.isEmpty())
                {
                    mappedValues.put(obj.toString(), 1);
                }
                else
                {
                    int lastValue = (Collections.max(mappedValues.values()));
                    
                    mappedValues.put(obj.toString(), lastValue + 1);
                }
            }
            resultData.add(mappedValues.get(obj.toString()));
        }
        this.updateColumnData(col_id, resultData);
    }
    
    public void divideValues(String col_id, int numOfDivisions)
    {
        ArrayList<Division> divisions = new ArrayList<>();
        
        float maxValue = this.getMaxValue(col_id);
        float minValue = this.getMinValue(col_id);
        
        float divisionSize = (maxValue - minValue) / numOfDivisions;
        
        for(int i = 0; i < numOfDivisions; i++)
        {
            float startValue = minValue;
            
            if(i != 0) startValue = minValue + (i * divisionSize);
            
            float endValue = startValue + divisionSize;
            
            if(i == numOfDivisions - 1) endValue = maxValue;
            
            divisions.add(new Division(i + 1, startValue, endValue));
        }
        
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        for(Object obj : colData)
        {
            for(Division division : divisions)
            {
                float value = (float) obj;
                
                if(value >= division.getStartValue()
                        && value < division.getEndValue())
                {
                    System.out.println(division.getDivisionID());
                }
            }
        }
    }
    
    public void groupByValueAmount(String col_id, int numOfgroups)
    {
        ArrayList<Object> colData = this.getColumnData(col_id);
        
        ConcurrentHashMap<Object, Integer> valuesAmounts =
                new ConcurrentHashMap<>();
        
        for(Object obj : colData)
        {
            if(valuesAmounts.containsKey(obj))
            {
                valuesAmounts.put(obj, valuesAmounts.get(obj) + 1);
            }
            else valuesAmounts.put(obj, 1);
        }
        
        HashMap<Object, Integer> groupsIDs = new HashMap<>();

        int groupID = 0;
        
        Iterator<Object> iterator = valuesAmounts.keySet().iterator();
        
        while(iterator.hasNext() && groupID <= numOfgroups)
        {
            Object obj = iterator.next();
            
            int maxValueAmount = Collections.max(
                    valuesAmounts.values()
            );
       
            if(valuesAmounts.get(obj) == maxValueAmount)
            {
                groupsIDs.put(obj, ++groupID);
                
                valuesAmounts.remove(obj);
            }
        }
        
        ArrayList<Object> resultData = new ArrayList<>();
        
        for(Object obj : colData)
        {
            if(groupsIDs.containsKey(obj))
            {
                resultData.add(groupsIDs.get(obj));
            }
            else resultData.add(numOfgroups + 1);
        }
        
        for(Object obj : resultData)
        {
            System.out.println(obj.toString());
        }
    }
}
