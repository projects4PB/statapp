package statsapp.loaders;

import statsapp.data.TableData;

/**
 *
 * @author Adrian Olszewski
 */
public interface Loader
{
    abstract public TableData loadData(String fileName);
}
