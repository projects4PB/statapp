package statsapp.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import statsapp.data.RecordData;
import statsapp.data.TableData;

/**
 *
 * @author Adrian Olszewski
 */
public class TextFileLoader implements Loader
{
    @Override
    public TableData loadData(String filePath)
    {
        TableData tableData = new TableData();
        
        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader(filePath);
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace(System.err);
        }
        if(fileReader == null) return null;
        
        BufferedReader buffReader = new BufferedReader(fileReader);
        try
        {
            String fileLine = buffReader.readLine();
            
            while(fileLine.trim().startsWith("#"))
            {
                fileLine = buffReader.readLine();
            }
            
            String[] colNames = this.splitValues(fileLine);
            
            tableData.setColumnsNames(colNames);
            
            while((fileLine = buffReader.readLine()) != null)
            {
                if(fileLine.trim().startsWith("#")) continue;
                
                String[] splittedValues = this.splitValues(fileLine);
                
                RecordData recordData = new RecordData(
                        this.parseData(colNames, splittedValues)
                );
                tableData.addRecord(recordData);
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace(System.err);
        }
        try
        {
            fileReader.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace(System.err);
        }
        return tableData;
    }
    
    private String[] splitValues(String fileLine)
    {
        return fileLine.split(",");
    }
    
    private HashMap<String, Object> parseData(
            String[] colNames, String[] values)
    {
        if(colNames.length != values.length) return null;
        
        HashMap<String, Object> parsedData = new HashMap<>();
        
        for(int i = 0; i < values.length; i++)
        {
            if(values[i].matches("\\d+\\.\\d+"))
            {
                parsedData.put(colNames[i], Float.parseFloat(values[i]));
            }
            else parsedData.put(colNames[i], values[i]);
        }
        return parsedData;
    }
}
