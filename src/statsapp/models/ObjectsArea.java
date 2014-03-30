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

	public double calculateEuklidesLength(
			AreaObject obj1, AreaObject obj2)
	{
		float x1 = obj1.getXCoord();
		float y1 = obj1.getYCoord();
		
		float x2 = obj2.getXCoord();
		float y2 = obj2.getYCoord();
	
		return Math.sqrt(
				Math.pow((x1 - x2), 2) +
				Math.pow((y1 - y2), 2)
		);
	}

	public double calculateManhattanLength(
			AreaObject obj1, AreaObject obj2)
	{
		float x1 = obj1.getXCoord();
		float y1 = obj1.getYCoord();
		
		float x2 = obj2.getXCoord();
		float y2 = obj2.getYCoord();

		return Math.abs(x1 - x2)
			+ Math.abs(y1 - y2);
	}
	
	public double calculateLInfinityLength(
			AreaObject obj1, AreaObject obj2)
	{
		float x1 = obj1.getXCoord();
		float y1 = obj1.getYCoord();
		
		float x2 = obj2.getXCoord();
		float y2 = obj2.getYCoord();

		return Math.max(Math.abs(x1 - x2),
				Math.abs(y1 - y2));
	}
}
