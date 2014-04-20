package statsapp.models;

import java.util.ArrayList;

public class ObjectsSet
{
	private ArrayList<AreaObject> areaObjects;

	private AreaObject setAveragePoint;

	public ObjectsSet(AreaObject setAveragePoint)
	{
		this.areaObjects = new ArrayList<>();

		this.setAveragePoint = setAveragePoint;
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

	public void setAveragePoint(AreaObject setAveragePoint)
	{
		this.setAveragePoint = setAveragePoint;
	}

	public AreaObject getAveragePoint()
	{
		return this.setAveragePoint;
	}
}
