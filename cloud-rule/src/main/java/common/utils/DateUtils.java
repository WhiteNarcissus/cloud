package common.utils;

import com.gzcb.creditcard.gykdh.common.context.SpringBeanLoader;
import com.gzcb.creditcard.gykdh.dao.system.FestivalMapper;
import com.gzcb.creditcard.gykdh.entity.system.BizDate;
import com.gzcb.creditcard.gykdh.entity.system.Festival;
import com.gzcb.creditcard.gykdh.report.ReportContants;
import com.gzcb.creditcard.gykdh.service.system.BizDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
	
	private static Logger logger=LoggerFactory.getLogger(DateUtils.class);
	
	public final static String DEFAULT_TYPE = "PLM";
	
	public static final String DATE_PATTERN_S = "yyyy-MM-dd";
	
	private static final Map cutOverMap = Collections.synchronizedMap(new HashMap());
	
	private static final long CUT_OVER_INTERVAL=1000*60;
	
	private static volatile long lastTimeCutOver=0;
	
	/**
	 * 获取业务日期实体
	 * @param key
	 * @return
	 */
	private static BizDate getCutOver(String key) {
		if (!cutOverMap.containsKey(key)||System.currentTimeMillis()-lastTimeCutOver>CUT_OVER_INTERVAL) {
            reset(key);
        }
		return (BizDate) cutOverMap.get(key);
	}
	
	/**
	 * 重置业务日期
	 * @param key
	 */
	public static void reset(String key) {
		BizDateService bizDateService=(BizDateService)SpringBeanLoader.getBean("bizDateService");
		BizDate cutOver = bizDateService.queryBizDate(key);
		cutOverMap.put(cutOver.getDateKey(), cutOver);
		lastTimeCutOver=System.currentTimeMillis();
	}
	
	/**
	 * 获得caseType业务系统的系统日期,以Date对象返回
	 *
	 *            业务类型:aps,cc
	 * @return Date对象
	 */
	public static Date getNextBusinessDate(String key) {
		return getNextBusinessCalendar(key).getTime();
	}

	/**
	 * 获得caseType业务系统的上一个系统日期,以Date对象返回
	 *
	 *            业务类型:aps,cc
	 * @return Date对象
	 */
	public static Date getPreBusinessDate(String key) {
		return getPreBusinessCalendar(key).getTime();
	}
	
	/**
	 * 获得caseType业务系统的业务日期,以Calendar对象返回
	 *
	 * @return Calendar
	 */
	public  static Calendar getBusinessCalendar(String key) {
		BizDate cutover = getCutOver(key);
		Calendar businCalendar = Calendar.getInstance();
		businCalendar.setTime(cutover.getCurrDate());
		Calendar sysCalendar = getCalendar(getSysDate());
		businCalendar.set(Calendar.HOUR_OF_DAY, sysCalendar
				.get(Calendar.HOUR_OF_DAY));
		businCalendar.set(Calendar.MINUTE, sysCalendar.get(Calendar.MINUTE));
		businCalendar.set(Calendar.SECOND, sysCalendar.get(Calendar.SECOND));
		return businCalendar;
	}

	/**
	 * 获得当前系统的系统日期,以Date类型返回
	 * 
	 * @return Date对象
	 */
	public static Date getSysDate() {
		return new Date();
	}
	
	/**
	 * 以long型时间生成日历,以Calendar对象返回
	 *
	 * @return Calendar
	 */
	public static  Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 获得caseType业务系统的下一个业务日期,以Calendar对象返回
	 * @return Calendar
	 */
	public static  Calendar getNextBusinessCalendar(String key) {
		BizDate cutOver = getCutOver(key);
		Calendar businCalendar = Calendar.getInstance();
		businCalendar.setTime(cutOver.getNextDate());
		Calendar sysCalendar = getCalendar(getSysDate());
		businCalendar.set(Calendar.HOUR_OF_DAY, sysCalendar
				.get(Calendar.HOUR_OF_DAY));
		businCalendar.set(Calendar.MINUTE, sysCalendar.get(Calendar.MINUTE));
		businCalendar.set(Calendar.SECOND, sysCalendar.get(Calendar.SECOND));
		return businCalendar;
	}

	/**
	 * 获得caseType业务系统的上一个业务日期,以Calendar对象返回
	 *
	 * @return Calendar
	 */
	public static  Calendar getPreBusinessCalendar(String key) {
		BizDateService bizDateService=(BizDateService)SpringBeanLoader.getBean("bizDateService");
		BizDate cutOver = bizDateService.queryBizDate(key);

		Calendar businCalendar = Calendar.getInstance();
		businCalendar.setTime(cutOver.getPreDate());
		Calendar sysCalendar = getCalendar(getSysDate());
		businCalendar.set(Calendar.HOUR_OF_DAY, sysCalendar
				.get(Calendar.HOUR_OF_DAY));
		businCalendar.set(Calendar.MINUTE, sysCalendar.get(Calendar.MINUTE));
		businCalendar.set(Calendar.SECOND, sysCalendar.get(Calendar.SECOND));
		return businCalendar;
	}

	/**
	 * 获得caseType业务系统的系统日期,以Date对象返回

	 *            业务类型:aps,cc
	 * @return Date对象
	 */
	public static Date getBusinessDate(String caseTypeId) {
		return getBusinessCalendar(caseTypeId).getTime();
	}

	/**
	 * 获得caseType业务系统的业务日期时间,
	 *
	 * @param caseType
	 *            业务类型:aps,cc
	 * @return Timestamp对象
	 */
	public static Timestamp getBusinessTimestamp(String caseType) {
	  return new Timestamp(getBusinessCalendar(caseType).getTimeInMillis());
	}

	/**
	 * 获取Timestamp类型默认业务日期
	 * @return
	 */
	public static Timestamp getBusinessTimestamp(){
		return getBusinessTimestamp(DEFAULT_TYPE);
	}

	/**
	 * 获取Date类型默认业务日期
	 * @return
	 */
	public static Date getBusinessDate(){
		return getBusinessDate(DEFAULT_TYPE);
	}

	/**
	 * 按照pattern转换字符串为日期
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	/**
	 * 按照pattern所指示的样式格式化日期,以String类型返回
	 *
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		if(date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 按照pattern所指示的样式格式化日期,以String类型返回
	 *
	 * @param pattern
	 * @return String
	 */
	public static String format(Timestamp ts, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(ts);
	}

	/**
	 *
	 * 描述：计算时间与当前时间的差
	 * @param date
	 * @return
	 *
	 */
	public static long countDifferenceTime(Date date){
		if (date != null) {
			long haoSeconds = date.getTime();
			return (System.currentTimeMillis() - haoSeconds);
		} else {
			return 0;
		}
	}

	/**
	 *
	 * 描述：计算相差天数
	 * @param date1
	 * @param date2
	 * @return
	 *
	 */
	public static int countDifferenceDay(Date date1,Date date2){
		if (date1 != null && date2!=null) {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			long dateTime1=calendar.getTimeInMillis();

			calendar.setTime(date2);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			long dateTime2=calendar.getTimeInMillis();
			long day=(dateTime2-dateTime1)/(1000*60*60*24);
			return (int)day;
		} else {
			return 0;
		}
	}


	/**
	 *
	 * 描述：计算相差月数
	 * @param date1
	 * @param date2
	 * @return
	 *
	 */
	public static int countDifferenceMonth(Date date1,Date date2){
		int month=0;
		if (date1 != null && date2!=null) {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date1);
			long dateTime1=calendar.getTimeInMillis();
			int year1=calendar.get(Calendar.YEAR);
			int month1=calendar.get(Calendar.MONTH);

			calendar.setTime(date2);
			long dateTime2=calendar.getTimeInMillis();
			int year2=calendar.get(Calendar.YEAR);
			int month2=calendar.get(Calendar.MONTH);

			if(dateTime1>=dateTime2){
				if(year1==year2){
					month=month1-month2;
				}else{
					month=12*(year1-year2)+(month1-month2);
				}

			}else{
				if(year1==year2){
					month=month2-month1;
				}else{
					month=12*(year2-year1)+(month2-month1);
				}
			}
		}

		return month;
	}

	/**
	 *
	 * 描述：计算时间与当前时间的差
	 * @return
	 *
	 */
	public static Date timeStampToDate(Timestamp time){
		if (time != null) {
			long haoSeconds = time.getTime();
			return new Date(haoSeconds);
		} 
		
		return null;
	}
	
	public static int compareDate(Date date1,Date date2){
		int result=0;
		if(date1!=null && date1!=null){
			long date1Int=Long.parseLong(DateUtils.format(date1, "yyyyMMddHHmmss"));
			long date2Int=Long.parseLong(DateUtils.format(date2, "yyyyMMddHHmmss"));
			if(date1Int>date2Int){
				result=1;
			}
			
			if(date1Int<date2Int){
				result=-1;
			}
					
		}				
		return result;
		
	}
	
	
	
	/**
	 * 
	 * 描述：获取两个日期范围中所有日期
	 * @return
	 *
	 */
	public static Set<String> getTwoDateRange(Date queryDate1,Date queryDate2,String dateLength){			
		Set<String> dateSet = new TreeSet<String>();
		if (queryDate1 != null && queryDate2 != null && !StringUtils.isNullOrEmpty(dateLength)) {		
			if (ReportContants.DATE_LENGTH_DAY.equals(dateLength)) {
				dateSet=getTwoDateRange(queryDate1,queryDate2,dateLength,"yyyy-MM-dd");
			}

			if (ReportContants.DATE_LENGTH_WEEK.equals(dateLength)) {
				dateSet=getTwoDateRange(queryDate1,queryDate2,dateLength,"yyyyww");
		}
			if (ReportContants.DATE_LENGTH_MONTH.equals(dateLength)) {
				dateSet=getTwoDateRange(queryDate1,queryDate2,dateLength,"yyyyMM");				
			}
		}
				
		return dateSet;
	}
	
	
	/**
	 * 
	 * 描述：获取两个日期范围中所有日期
	 * @return
	 *
	 */
	public static Set<String> getTwoDateRange(Date queryDate1,Date queryDate2,String dateLength,String pattern){
		if(DateUtils.compareDate(queryDate1, queryDate2)>0){
			Date temp=null;;
			temp=queryDate1;
			queryDate1=queryDate2;
			queryDate2=temp;
		}
				
		Set<String> dateSet = new TreeSet<String>();
		if (queryDate1 != null && queryDate2 != null && !StringUtils.isNullOrEmpty(dateLength)) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(queryDate1);
			if (ReportContants.DATE_LENGTH_DAY.equals(dateLength)) {
				String date1 = DateUtils.format(queryDate1, pattern);
				String date2 = DateUtils.format(queryDate2, pattern);
				dateSet.add(date1);
				if (!date2.equals(date1)) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					String dateTemp = DateUtils.format(calendar.getTime(), pattern);
					while (!dateTemp.equals(date2)) {
						dateSet.add(dateTemp);
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						dateTemp = DateUtils.format(calendar.getTime(), pattern);
					}
					dateSet.add(date2);
				}
			}

			if (ReportContants.DATE_LENGTH_WEEK.equals(dateLength)) {
				String date1 = DateUtils.format(queryDate1, pattern);
				String date2 = DateUtils.format(queryDate2, pattern);
				dateSet.add(date1);
				if (!date2.equals(date1)) {
					calendar.add(Calendar.WEEK_OF_YEAR, 1);
					String dateTemp = DateUtils.format(calendar.getTime(), pattern);
					while (!dateTemp.equals(date2)) {
						dateSet.add(dateTemp);
						calendar.add(Calendar.WEEK_OF_YEAR, 1);
						dateTemp = DateUtils.format(calendar.getTime(), pattern);
					}
				}
				dateSet.add(date2);

			}
			if (ReportContants.DATE_LENGTH_MONTH.equals(dateLength)) {
				String date1 = DateUtils.format(queryDate1, pattern);
				String date2 = DateUtils.format(queryDate2, pattern);
				dateSet.add(date1);
				if (!date1.equals(date2)) {
					calendar.add(Calendar.MONTH, 1);
					String dateTemp = DateUtils.format(calendar.getTime(), pattern);
					while (!dateTemp.equals(date2)) {
						dateSet.add(dateTemp);
						calendar.add(Calendar.MONTH, 1);
						dateTemp = DateUtils.format(calendar.getTime(), pattern);
					}

					dateSet.add(date2);
				}

			}
		}
				
		return dateSet;
	}
	
	/**
	 * 
	 * 描述：获取周的日期范围
	 * @param week
	 * @param queryDate
	 * @return
	 * @throws ParseException
	 *
	 */
	public static String getWeekDateStr(String week,Date[] queryDate) throws ParseException{
		Date date1=DateUtils.parse(week, "yyyyw");
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date1);
		Date weekBegin=calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		Date weekEnd=calendar.getTime();
		if(DateUtils.compareDate(weekBegin, queryDate[0])<0){
			weekBegin=queryDate[0];
		}
		
		if(DateUtils.compareDate(weekEnd, queryDate[1])>0){
			weekEnd=queryDate[1];
		}
		String weekBeginStr=DateUtils.format(weekBegin, "yyyyMMdd");
		String weekEndStr=DateUtils.format(weekEnd, "yyyyMMdd");
		String weekDate=weekBeginStr+"-"+weekEndStr;
		return weekDate;
	}
	
	public static Date addMonths(Date date,int months){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		
		return calendar.getTime();
	}
	
	
	public static int getDateField(Date date,int filed){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(filed);
		
	}
	
	public static Date getLastDayOfMonth(int year,int month){
		if (year > 0 && month > 0) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			return cal.getTime();
		} else {
			return null;
		}
	}
	
	public static int getYearOfDate(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.YEAR);
		} else {
			return 0;
		}
	}

	public static int getMonthOfDate(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return (cal.get(Calendar.MONTH) + 1);
		} else {
			return 0;
		}
	}
	
	/**
	 * 
	 * 描述：获取某个日期的近或者后workDay个工作日
	 * @param workDay 多少个工作日
	 * @return 
	 *
	 */
	public static Date getNearlyWorkDay(Date date,int workDay){
		Date result = null;
		if(date==null ){
			return null;
		}
		try {
			FestivalMapper festivalMapper = (FestivalMapper) SpringBeanLoader.getBean("festivalMapper");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int defaultWorkDay = workDay * 3;
			Date endDate = null;
			Date beginDate = null;
			calendar.add(Calendar.DAY_OF_MONTH, defaultWorkDay);

			List<Festival> festivalList = null;
			if (workDay < 0) {
				endDate = date;
				beginDate = calendar.getTime();
			} else {
				endDate = calendar.getTime();
				beginDate = date;
			}

			festivalList = festivalMapper.selectFestivalByDate(beginDate, endDate);

			if (festivalList != null && festivalList.size() > 0 && workDay!=0) {
				Map<String, Festival> festivalMap = new HashMap<String, Festival>();
				for (Festival festival : festivalList) {
					festivalMap.put(format(festival.getCalenderDate(), "yyyyMMdd"), festival);
				}

				int checkWorkIndex = 0;
				int festivalListIndex = 0;
				calendar.setTime(date); // 将日期设置回当天
				while (checkWorkIndex < Math.abs(workDay) && festivalListIndex < festivalList.size()) {
					festivalListIndex++;
					Festival festival = festivalMap.get(format(calendar.getTime(), "yyyyMMdd"));
					if (festival != null && "W".equals(festival.getDayType())) {
						checkWorkIndex++;
						if (checkWorkIndex == Math.abs(workDay)) {
							result = festival.getCalenderDate();
						}
					}

					if (workDay > 0) {
						calendar.add(Calendar.DAY_OF_MONTH, 1);
					} else {
						calendar.add(Calendar.DAY_OF_MONTH, -1);
					}
				}

			} else {
				result = date;
			}
		} catch (Exception e) {
			logger.error("获取某个日期的工作工作日发生异常", e);
			result = date;
		}

		return result;
	}

	public static void main(String[] args){
		try{
			Date expireDateDate=DateUtils.parse("1702", "yyMM");
			int year=DateUtils.getDateField(expireDateDate, Calendar.YEAR);
			int month=DateUtils.getDateField(expireDateDate, Calendar.MONTH)+1;
			Date lastDate=DateUtils.getLastDayOfMonth(year, month);
		System.out.println(DateUtils.format(lastDate, "yyyy年MM月dd日"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
