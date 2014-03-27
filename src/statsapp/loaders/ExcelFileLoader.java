package statsapp.loaders;

import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import statsapp.data.TableData;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Adrian Olszewski
 */
public class ExcelFileLoader implements Loader
{
    @Override
    public TableData loadData(String fileName)
    {
        Workbook workbook = null;
      
        try {
            workbook = Workbook.getWorkbook(new File(fileName));
        } catch (IOException ex) {
            Logger.getLogger(ExcelFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(ExcelFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Sheet sheet = workbook.getSheet(0);
        
//        Cell cell1 = sheet.getCell(0, 2);
//        System.out.println(cell1.getContents());
//        Cell cell2 = sheet.getCell(3, 4);
//        System.out.println(cell2.getContents());
//        workbook.close();
        return null;
    }
}
