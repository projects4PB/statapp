package statsapp.managers;

import java.util.HashMap;

import statsapp.models.ObjectsArea;

/*
 * Klasa zarządzająca przestrzeniami obiektów
 */
public class AreasManager
{
    private static volatile AreasManager instance = null;
	
	private HashMap<String, ObjectsArea> areas;

	private AreasManager()
	{
		areas = new HashMap<>();
	}

    public static AreasManager getInstance()
    {
        if(instance == null)
        {
            synchronized(AreasManager.class)
            {
                if(instance == null)
                {
                    instance = new AreasManager();
                }
            }
        }
        return instance;
    }

	public void addArea(String colId, ObjectsArea area)
	{
		this.areas.put(colId, area);
	}

	public ObjectsArea getArea(String colId)
	{
		return this.areas.get(colId);
	}
}
