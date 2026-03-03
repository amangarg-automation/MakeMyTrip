    package CommonUtilities;

    import Base.Setup;
    import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;
    import org.testng.annotations.DataProvider;

    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.lang.reflect.Method;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.*;
    public class Dataprovider {
       @DataProvider(name="dataProvider")
        public Iterator<Object[]> dataProvider(Method method) {
            String sheetName= Setup.sheetName;
            String tcName= method.getName();
            List<Object[]> list=new ArrayList<>();
           DataFormatter dataFormatter=new DataFormatter();
            try(FileInputStream fis=new FileInputStream("src/test/resources/input.xlsx"); XSSFWorkbook wb=new XSSFWorkbook(fis))
            {
                Sheet sheet=wb.getSheet(sheetName);
                if (sheet == null) {
                    throw new RuntimeException("Sheet not found: " + sheetName);
                }
                Row headerRow=sheet.getRow(0);
                if (headerRow == null) {
                    throw new RuntimeException("Header row is missing in sheet: " + sheetName);
                }
                Map<String,Integer> map=new HashMap<>();
                for(Cell cell:headerRow)
                {
                    if(cell==null) continue;
                    map.put(dataFormatter.formatCellValue(cell),cell.getColumnIndex());
                }
                if(!map.containsKey("Test Case Name"))
                {
                    throw new RuntimeException("Test Case Name column is missing. Please recheck");
                }
                int rowCount=sheet.getLastRowNum();
                for(int i=1;i<=rowCount;i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    List<Object> objectList = new ArrayList<>();
                    int tcNameIndex = map.get("Test Case Name");
                    Cell tcCell = row.getCell(tcNameIndex);
                    if (tcCell == null) continue;
                    if (dataFormatter.formatCellValue(row.getCell(tcNameIndex)).equals(tcName)) {
                        for (Cell cell : row) {
                            if (cell.getColumnIndex() == tcNameIndex) continue;
                            objectList.add(dataFormatter.formatCellValue(cell));
                        }
                        list.add(objectList.toArray());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return list.iterator();
       }
    }