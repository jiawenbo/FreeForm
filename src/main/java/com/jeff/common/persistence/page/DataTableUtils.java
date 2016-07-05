package com.jeff.common.persistence.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.Table;
import org.springframework.util.StringUtils;


public class DataTableUtils {
	
	
	/**
	 * 
	 * @author jeffwcx
	 * @method getReturn
	 * @param @param data
	 * @param @param draw
	 * @param @param recordsTotal
	 * @param @param recordsFiltered
	 * @param @return
	 * @return Map<String,Object>
	 * @description 获得datatables插件的回调Map对象，返回对象没有错误参数
	 * @date 2016年1月10日 下午1:22:42
	 */
	public static Map<String,Object> getReturn(Object data,int draw, int recordsTotal, int recordsFiltered){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		map.put("draw", draw);
		map.put("recordsTotal", recordsTotal);
		map.put("recordsFiltered", recordsFiltered);
		return map;
	}
	
	/**
	 * 
	 * @author jeffwcx
	 * @method getReturn
	 * @param @param data
	 * @param @param draw
	 * @param @param recordsTotal
	 * @param @param recordsFiltered
	 * @param @param error
	 * @param @return
	 * @return Map<String,Object>
	 * @description  获得datatables回调的Map对象，返回参数包含错误参数
	 * @date 2016年1月10日 下午1:23:24
	 */
	public static Map<String,Object> getReturn(Object data,int draw, int recordsTotal, int recordsFiltered,String error){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		map.put("draw", draw);
		map.put("recordsTotal", recordsTotal);
		map.put("recordsFiltered", recordsFiltered);
		map.put("error", error);
		return map;
	}
	
	/***
	 * 
	 * @author jeffwcx
	 * @method getReturn
	 * @param @param pageInfo
	 * @param @return
	 * @return Map<String,Object>
	 * @description  通过pageInfo返回信息
	 * @date 2016年1月10日 下午3:37:33
	 */
	public static Map<String,Object> getReturn(PageInfo<?> pageInfo){
		Map<String, Object> map = new HashMap<String, Object>();
		if(pageInfo!=null){
			map.put("data", pageInfo.getData());
			map.put("draw", pageInfo.getDraw());
			map.put("recordsTotal", pageInfo.getRecordsTotal());
			map.put("recordsFiltered", pageInfo.getRecordsFiltered());
			if(!StringUtils.isEmpty(pageInfo.getError())){
				map.put("error", pageInfo.getError());
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @author jeffwcx
	 * @method changeToPage
	 * @param @param request
	 * @param @return
	 * @return Page
	 * @description 把参数转换成Page类
	 * @date 2016年1月10日 下午3:41:48
	 */
	private final static String COLUMN_DATA="[data]";
	private final static String COLUMN_NAME="[name]";
	private final static String COLUMN_ORDERABLE="[orderable]";
	private final static String COLUMN_SEARCHABLE="[searchable]";
	private final static String COLUMN_SEARCH_VALUE="[search][value]";
	private final static String COLUMN_SEARCH_REGEX="[search][regex]";
	private final static String DRAW="draw";
	private final static String ORDER_COLUMN="[column]";
	private final static String ORDER_DIR="[dir]";
	private final static String START = "start";
	private final static String LENGTH="length";
	private final static String SEARCH_VALUE="search[value]";
	private final static String SEARCH_REGEX="search[regex]";
	
	public static Page changeToPage(HttpServletRequest request){
		Enumeration<String> en = request.getParameterNames();
		Pattern pattern = Pattern.compile("^order\\[[\\d]*\\]\\[column\\]$");
		Pattern pattern2 = Pattern.compile("^order\\[[\\d]*\\]\\[dir\\]$");
		Order order = new Order();
		while (en.hasMoreElements()) {
			String string = (String) en.nextElement();
			Matcher matcher = pattern.matcher(string);
			Matcher matcher2 = pattern2.matcher(string);
			if(matcher.matches()){
				Integer column = Integer.valueOf(request.getParameter(string));
				if(column==null){
					order=null;
					break;
				}
				order.setColumn(column);
				if(!StringUtils.isEmpty(order.getDir())){
					break;
				}
			}
			if(matcher2.matches()){
				String dir = request.getParameter(string);
				if(dir==null){
					order=null;
					break;
				}
				order.setDir(dir);
				if(order.getColumn()!=null){
					break;
				}
			}
		}
		List<Column> columns = new ArrayList<>();
		List<String> tables = new ArrayList<>();
		String tempTable=null;
		Boolean hasManyTable=true;
		Page page = new Page();
		for(int i=0; ;i++){
			Column column = new Column();
			String data = request.getParameter(columnsStr(i, COLUMN_DATA));
			column.setData(StringUtils.isEmpty(data)?null:data);
			String name = request.getParameter(columnsStr(i, COLUMN_NAME));
			//前台就算数据类型为空，但是name值也不能为空，为空会导致无法进行遍历
			if(StringUtils.isEmpty(name)){
				break;
			}
			column.setName(StringUtils.isEmpty(name)?null:name);
			if(order!=null){
				if(order.getColumn()==i){
					order.setName(name);
				}
			}
			if(hasManyTable){
				String[] nowTable = name.split("\\.");
				if(nowTable.length<=1){
					hasManyTable=false;
				}
				else
				{
					if(tempTable==null){
						tempTable=nowTable[0];
						tables.add(tempTable);
					}
					else
					{
						Boolean isSame=false;
						for(String temp : tables){
							if(temp.equals(nowTable[0])){
								isSame = true;
								break;
							}
						}
						if(!isSame){
							tables.add(nowTable[0]);
						}
					}
					
				}
			}
			Boolean orderable = Boolean.valueOf(request.getParameter(columnsStr(i, COLUMN_ORDERABLE)));
			column.setOrderable(orderable);
			Boolean searchable = Boolean.valueOf(request.getParameter(columnsStr(i, COLUMN_SEARCHABLE)));
			column.setSearchable(searchable);
			String searchValue = request.getParameter(columnsStr(i, COLUMN_SEARCH_VALUE));
			column.setSearchValue(StringUtils.isEmpty(searchValue)?null:searchValue);
			Boolean searchRegex = Boolean.valueOf(request.getParameter(columnsStr(i, COLUMN_SEARCH_REGEX)));
			column.setSearchRegex(searchRegex);
			columns.add(column);
		}
		if(tables.size()==0){
			page.setTables(null);
		}
		else{
			page.setTables(tables);
		}
		page.setColumns(columns);
		page.setDraw(Integer.valueOf(request.getParameter(DRAW)));
		page.setLength(Integer.valueOf(request.getParameter(LENGTH)));
		page.setSearchRegex(Boolean.valueOf(request.getParameter(SEARCH_REGEX)));
		String searchValue=request.getParameter(SEARCH_VALUE);
		page.setSearchValue(StringUtils.isEmpty(searchValue)?null:searchValue);
		page.setOrder(order);
		page.setStart(Integer.valueOf(request.getParameter(START)));
		return page;
		
	}
	
	
	private static String columnsStr(int index, String type){
		return "columns["+String.valueOf(index)+"]"+type;
	}
	public static void main(String[] args) {
		System.out.println(Arrays.toString("User.name".split("\\.")));
	}
	
	
}

