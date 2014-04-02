package statsapp.managers;

import java.util.ArrayList;

import org.math.array.LinearAlgebra;

import statsapp.models.AreaObject;

/*
 * Klasa zarządzająca obiekami w przestrzeni obiektów
 */
public class AreaManager
{
    private static volatile AreaManager instance = null;

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

	public void addAreaObject(AreaObject obj)
	{
		this.areaObjects.add(obj);
	}
	
	public void removeAreaObject(AreaObject areaObj)
	{
		this.areaObjects.remove(areaObj);
	}

	public ArrayList<AreaObject> getAreaObjects()
	{
		return this.areaObjects;
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
		// the covariance matrix
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
