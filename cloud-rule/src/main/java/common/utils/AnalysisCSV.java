package common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisCSV {
   @SuppressWarnings({"rawtypes", "unchecked" })
public static Map analysis(File managerFile) throws IOException{
	    List<String[]> insertList = new ArrayList<String[]>();
		List deleteList = new ArrayList();
		Map map = new HashMap();
		String[] strs =null;
		int line = 0;
		CSVReader cr = null;
		try {
			 cr = new CSVReader(managerFile, false);
				while((strs = cr.readLine())!=null){
					if (line == 0) {
						line++;
						continue;
					} else {
					insertList.add(strs);
					deleteList.add(strs[0]);
					line++;
				}
					
			  }
				map.put("insertList", insertList);
				map.put("deleteList", deleteList);
				map.put("line", line);
		} catch (Exception ex) {
			throw ex;
		}finally{
			if (cr != null) {
				try {
					cr.close();
				} catch (IOException iex) {
					iex.printStackTrace();
				}
			}
		}
		return map;
   }
}
