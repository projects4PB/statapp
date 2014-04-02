package statsapp.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.math.array.LinearAlgebra;
import statsapp.data.RecordData;
import statsapp.data.TableData;
import statsapp.data.records.TableRecord;

import statsapp.models.AreaObject;
import statsapp.models.MetricType;

import org.math.array.LinearAlgebra;

import statsapp.models.AreaObject;

/*
 * Klasa zarządzająca obiekami w przestrzeni obiektów
 */
public class AreaManager
{
    private static volatile AreaManager instance = null;

	private ArrayList<AreaObject> areaObjects;
        
    private DataManager dManager = DataManager.getInstance();

	private ArrayList<AreaObject> areaObjects;

	private AreaManager()
	{
		this.areaObjects = new ArrayList<>();
	}

    public static AreaManager getInstance()
    {
        if(instance == null)
        {
            synchronized(AreaManager.class)
            {
                if(instance == null)
                {
                    instance = new AreaManager();
                }
            }
        }
        return instance;
    }

	public void addAreaObject(AreaObject areaObj)
	{
		this.areaObjects.add(areaObj);
	}
	
	public void removeAreaObject(AreaObject areaObj)
	{
		this.areaObjects.remove(areaObj);
	}

	public ArrayList<AreaObject> getAreaObjects()
	{
		return this.areaObjects;
	}
        public void addTableDataToAreaObjects()
        {
            TableData tableData = dManager.getTableData();
            String[] colNames = tableData.getColumnsNames();
            ArrayList<Float> wektor = null;
            String classAreaObject = "";
            for(int i=0 ; i< dManager.getDataList().size(); i++)
            {
                TableRecord tableRecord = (TableRecord)dManager.getDataList().get(i);
                RecordData recordData = tableRecord.getRecordData();
                wektor = new  ArrayList<Float>();
                
                for (String colName : colNames){
                    if(colName.equals("class")== false && colName.contains("_") == false){
                        Object obj = recordData.getFields().get(colName);
                        if (obj instanceof Number)
                            wektor.add((float)obj); 
                    }
                    
                    if(colName.equals("class")){
                        Object obj = recordData.getFields().get(colName);
                        classAreaObject = obj.toString();
                    }
                }
                
                AreaObject areaObjectDataList  = new AreaObject(wektor);
                areaObjectDataList.setAreaObjectClass(classAreaObject);
                this.addAreaObject(areaObjectDataList);
            }
        }
        
        public HashMap calculateAllLengths(AreaObject areaObj, MetricType metricType)
        {
            HashMap hashMap = new HashMap();
            double length = 0;
            ArrayList<AreaObject> areaObjects = this.getAreaObjects();
            
            switch(metricType)
            {
                case METRIC_MANHATTAN :
                    
                    for(int i=0 ; i< areaObjects.size(); i++){
                        if(areaObjects.get(i).equals(areaObj) == false){
                            length = this.calculateManhattanLength(areaObjects.get(i), areaObj);
                            hashMap.put(areaObjects.get(i), length);
                        }
                    }
               
                break;
                    
                case METRIC_EUKLIDES :
                   for(int i=0 ; i< areaObjects.size(); i++){
                        if(areaObjects.get(i).equals(areaObj) == false){
                            length = this.calculateEuklidesLength(areaObjects.get(i), areaObj);
                            hashMap.put(areaObjects.get(i), length);
                        }
                   }

                break;  
                    
                case METRIC_L_INFINITY :
                     for(int i=0 ; i< areaObjects.size(); i++){
                        if(areaObjects.get(i).equals(areaObj) == false){
                            length = this.calculateLInfinityLength(areaObjects.get(i), areaObj);
                            hashMap.put(areaObjects.get(i), length);
                        }
                    }
                     
                break;  
                    
                case METRIC_MAHALANOBIS :
                for(int i=0 ; i< areaObjects.size(); i++){
                        if (areaObjects.get(i).equals(areaObj) == false) {
                            length = this.calculateMahalanobisLength(areaObjects.get(i), areaObj);
                            hashMap.put(areaObjects.get(i), length);
                        }
                    }
                
                break;       
            }
            
            return hashMap;
        }
        
        public String classifyObject(AreaObject areaObj, int numOfNeighbours, MetricType metricType)
        {
            HashMap hashMap = this.calculateAllLengths(areaObj,metricType);
            hashMap = this.sortByValues(hashMap);
            ArrayList<String> classNeigbours  = new  ArrayList<String>();
            
            int i = 0;

            Iterator iter = hashMap.keySet().iterator();
            
            while(iter.hasNext()) {
                AreaObject key = (AreaObject)iter.next();
                double val = (double)hashMap.get(key);
                
                if(i < numOfNeighbours)
                {
                    String classAreaObject = key.getAreaObjectClass();
                    classNeigbours.add(classAreaObject);
                }

                i++;                
            }
                        
            HashMap<Object, Integer> classAmounts =  new HashMap<>( );
                
            for(String obj : classNeigbours)
            {
                if(classAmounts.containsKey(obj))
                {
                    classAmounts.put(obj, classAmounts.get(obj) + 1);
                }
                
                else classAmounts.put(obj, 1);
            }
                       
            int max = Collections.max(classAmounts.values());
            String newObjectClass = "";
            
            for (Object objClass : classAmounts.keySet()) {
                if (classAmounts.get(objClass).equals(max))  {
                    newObjectClass = objClass.toString();
                }
            }
            
            return newObjectClass;
        }
        
        public float getClassifyQuantity(int numOfNeighbours, MetricType metricType)
        {
            String oldClass = "";
            String newClass = "";
            boolean IsCorrect = false;
            float quantity = 0 ;
            int countCorrect = 0;
            ArrayList<AreaObject> areaObjects = this.getAreaObjects();
            HashMap hashQuantity = new HashMap();
            
            for(int i=0 ; i< areaObjects.size(); i++)
            {
                oldClass = areaObjects.get(i).getAreaObjectClass();
                newClass = this.classifyObject(areaObjects.get(i), numOfNeighbours, metricType);
                
                if(oldClass.equals(newClass))
                {
                   IsCorrect = true;
                   countCorrect++;
                }
                
                else
                {
                    IsCorrect = false;
                }
                
              //hashQuantity.put(areaObjects.get(i), IsCorrect);
            }
            
           quantity = (float) countCorrect / (float) areaObjects.size();
             
            return quantity;
        }

       public static HashMap sortByValues(HashMap map) 
       { 
           List list = new LinkedList(map.entrySet());
           Collections.sort(list, new Comparator() {
               public int compare(Object o1, Object o2) 
               {
                   return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
                }
           });
           HashMap sortedHashMap = new LinkedHashMap();
           
           for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
           }
           
           return sortedHashMap;
       }

	public double calculateEuklidesLength(
			AreaObject obj1, AreaObject obj2)
	{
		double pows_amount = 0;

		for(int i = 0; i < obj1.getVars().size(); i++)
		{
			pows_amount += Math.pow(
					obj1.getVars().get(i) -
					obj2.getVars().get(i), 2);
		}
		return Math.sqrt(pows_amount);
	}

	public double calculateManhattanLength(
			AreaObject obj1, AreaObject obj2)
	{
		double abs_amount = 0;

		for(int i = 0; i < obj1.getVars().size(); i++)
		{
			abs_amount += Math.abs(
					obj1.getVars().get(i) -
					obj2.getVars().get(i));
		}
		return abs_amount;
	}
	
	public double calculateLInfinityLength(
			AreaObject obj1, AreaObject obj2)
	{
		double max_length = -1;

		for(int i = 0; i < obj1.getVars().size(); i++)
		{
			double length = Math.abs(
					obj1.getVars().get(i) -
					obj2.getVars().get(i));
			
			if(length > max_length)
			{
				max_length = length;
			}
		}
		return max_length;
	}
    
    public double calculateMahalanobisLength(
			AreaObject obj1, AreaObject obj2)
	{
		double[][] S;

		int dim = obj1.getVars().size();
    
        S = new double[dim][dim];

        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
                if(i == j)
                    S[i][j] = 1.0;
                else
                    S[i][j] = 0.0;
        
		double[][] diff = new double[1][dim];
        
		for(int i = 0; i < dim; i++)
            diff[0][i] = obj1.getVars().get(i)
				- obj2.getVars().get(i);
        
		double result[][] = LinearAlgebra.times(
				diff, LinearAlgebra.inverse(S)
		);
        result = LinearAlgebra.times(
				result, LinearAlgebra.transpose(diff)
		);
        
		return Math.sqrt(result[0][0]);
    }
}
