package statsapp.models;

import java.util.ArrayList;

/**
 * Przestrzen X - Y zawierajaca obiekty
 */
public class ObjectsArea
{
	private ArrayList<AreaObject> areaObjects;

	public ObjectsArea()
	{
		this.areaObjects = new ArrayList<>();
	}

	public ArrayList<AreaObject> getAreaObjects()
	{
		return this.areaObjects;
	}

	public void addAreaObject(AreaObject areaObj)
	{
		this.areaObjects.add(areaObj);
	}
	
	public void removeAreaObject(AreaObject areaObj)
	{
		this.areaObjects.remove(areaObj);
	}
}
