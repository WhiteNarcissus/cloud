package common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * poi工具类
 * 
 * @author gcb
 * 
 */
public class PoiUtils {
	private PoiUtils() {
	}

	/**
	 * 强制获取单元格内容的String类型值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getStringCellValueForced(Cell cell) {
		try {
			int type = cell.getCellType();
			if (type == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue().trim();
			} else if (type == Cell.CELL_TYPE_NUMERIC) {
				CellStyle cs = cell.getCellStyle();
				String dateFormat = cs.getDataFormatString();
				if (dateFormat.contains("y") || dateFormat.contains("m")
						|| dateFormat.contains("d")) {
					Date date = cell.getDateCellValue();
					dateFormat = dateFormat.replace("mm", "MM");
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
					return simpleDateFormat.format(date);
				} else {
					double d = cell.getNumericCellValue();
					return String.valueOf(d);
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 强制获取单元格内容的Double类型值
	 * 
	 * @param cell
	 * @return
	 */
	public static Double getNumericCellValue(Cell cell) {
		try {
			int type = cell.getCellType();
			if (type == Cell.CELL_TYPE_STRING) {
				return Double.parseDouble(cell.getStringCellValue());
			}else {
				return cell.getNumericCellValue();
			}
		} catch (Exception e) {
		}
		return null;
	}
}
