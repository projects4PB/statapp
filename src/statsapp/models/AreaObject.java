package statsapp.models;

/**
 * Klasa reprezentujaca obiekt w przestrzeni
 */
public class AreaObject
{
	// wspolrzedna X obiektu
	private float xCoord;
	
	// wspolrzedna Y obiektu
	private float yCoord;

	// klasa obiektu
	private String objectClass;

	public AreaObject(float x, float y)
	{
		this.xCoord = x;
		this.yCoord = y;
	}

	public float getXCoord()
	{
		return this.xCoord;
	}
	
	public float getYCoord()
	{
		return this.yCoord;
	}

	public String getAreaObjectClass()
	{
		return this.objectClass;
	}

	public void setAreaObjectClass(String objClass)
	{
		this.objectClass = objClass;
	}
}
