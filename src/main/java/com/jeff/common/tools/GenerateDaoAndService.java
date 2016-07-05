package com.jeff.common.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jeff.common.utils.StringUtil;




/**
 * @className GenerateDaoAndService
 * @desc 自动生成Dao和Service层
 * @author Jeff Wang
 * @date 2015-11-15 下午8:11:39
 */
public class GenerateDaoAndService {
	
	
	private static final String LINE_BREAK = "\r\n";
	
	private static List<String> allEntityName = new ArrayList<>();
	private static final String mavenJavaPath= File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator;
	private String entityFilePath="";
	private String entityPackage="";
	
	private String daoFilePath="";
	private String daoPackage= "";
	private String daoImplFilePath = "";
	private String daoImplPackage = "";
	
	private String serviceFilePath="";
	private String servicePackage="";
	private String serviceImplFilePath="";
	private String serviceImplPackage="";
	
	private static final String projectPath = getProjectPosition();
	
	
	public static void main(String[] args) throws Exception {
		GenerateDaoAndService gds = new GenerateDaoAndService();
		gds.setEntityFilePath("com.jeff.modules.entity");
		gds.setDaoFilePath("com.jeff.modules.dao");
		gds.setServiceFilePath("com.jeff.modules.service");
		gds.getAllEntityName();
		gds.generateDaoAndService();
	}
	
	public GenerateDaoAndService(){
		
	}
	public GenerateDaoAndService(String entityFilePath,String daoFilePath, String serviceFilePath){
		this.entityFilePath = entityFilePath;
		this.daoFilePath = daoFilePath;
		this.serviceFilePath = serviceFilePath;
	}
	
	/**
	 * 
	 * @method getProjectPosition
	 * @param @return
	 * @return String
	 * @desc 获取当前项目地址
	 * @date 2015-11-15 下午8:11:50
	 */
	private static String getProjectPosition(){
		return System.getProperty("user.dir");
	}
	
	
	/**
	 * 
	 * @method getAllEntityName
	 * @param @param dir
	 * @return void
	 * @throws Exception 
	 * @desc entity目录下所有文件名
	 * @date 2015-11-15 下午8:12:22
	 */
	private void getAllEntityName() throws Exception{
		if("".equals(entityFilePath)){
			throw new Exception("entity路径不能为空");
		}
		File dir = new File(projectPath+mavenJavaPath+entityFilePath);
		File[] fs = dir.listFiles();
		for(int i=0; i<fs.length; i++){
			String name = fs[i].getName();
			allEntityName.add(name.substring(0, name.length()-5));
		}
	}
	
	/**
	 * 
	 * @method generateDao
	 * @param 
	 * @return void
	 * @throws Exception 
	 * @desc 生成Dao文件
	 * @date 2015-11-15 下午9:17:25
	 */
	private void generateDao() throws Exception{
		if("".equals(daoFilePath)){
			throw new Exception("dao路径不能为空");
		}
		for(String entity : allEntityName){
			try {
				File f = new File(projectPath+mavenJavaPath+daoFilePath+File.separator+entity+"Dao.java");
				FileWriter fw = new FileWriter(f);
				fw.write("package "+daoPackage+";"
				+LINE_BREAK+LINE_BREAK+LINE_BREAK
				+"import com.jeff.common.persistence.BaseDao;"
				+LINE_BREAK
				+"import "+entityPackage+"."+entity+";"
				+LINE_BREAK+LINE_BREAK
				+"public interface "+entity+"Dao extends BaseDao<"+entity+"> {}");
				fw.flush();
				fw.close();
				System.out.println(entity+"Dao.java"+"生成成功！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("***************************************");
		
	}
	
	
	/**
	 * 
	 * @method generateDaoImpl
	 * @param 
	 * @return void
	 * @desc 生成DaoImpl文件
	 * @date 2015-11-15 下午10:19:29
	 */
	private void generateDaoImpl(){
		if("".equals(daoImplPackage)){
			daoImplPackage = daoPackage+".daoimpl";
			daoImplFilePath = returnProperPath(daoImplPackage);
		}
		for(String entity : allEntityName){
			try {
				File f = new File(projectPath+mavenJavaPath+daoImplFilePath+File.separator+entity+"DaoImpl.java");
				FileWriter fw = new FileWriter(f);
				fw.write("package "+daoImplPackage+";"
				+LINE_BREAK+LINE_BREAK+LINE_BREAK
				+"import org.springframework.beans.factory.annotation.Autowired;"
				+LINE_BREAK
				+"import org.springframework.stereotype.Repository;"
				+LINE_BREAK
				+"import org.springframework.beans.factory.annotation.Qualifier;"
				+LINE_BREAK
				+"import com.jeff.common.persistence.BaseDao;"
				+LINE_BREAK
				+"import com.jeff.common.persistence.BaseDaoImpl;"
				+LINE_BREAK
				+"import "+daoPackage+"."+entity+"Dao;"
				+LINE_BREAK
				+"import "+entityPackage+"."+entity+";"
				+LINE_BREAK+LINE_BREAK
				+"@Repository(\""+StringUtil.toLowerCaseFirstLetter(entity)+"Dao\")"
				+LINE_BREAK
				+"public class "+entity+"DaoImpl extends BaseDaoImpl<"+entity+"> implements "+entity+"Dao{"
				+LINE_BREAK
				+"	@Autowired"
				+LINE_BREAK
				+"	@Qualifier(\"baseDao\")"
				+LINE_BREAK
				+"	private BaseDao baseDao;"
				+LINE_BREAK
				+LINE_BREAK
				+"}"
				);
				
				fw.flush();
				fw.close();
				System.out.println(entity+"DaoImpl.java"+"生成成功！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		System.out.println("***************************************");
	}
	
	
	/**
	 * 
	 * @method generateService
	 * @param 
	 * @return void
	 * @throws Exception 
	 * @desc 生成service文件
	 * @date 2015-11-15 下午10:19:50
	 */
	private void generateService() throws Exception{
		if("".equals(serviceFilePath)){
			throw new Exception("service路径不能为空");
		}
		for(String entity : allEntityName){
			try {
				File f = new File(projectPath+mavenJavaPath+serviceFilePath+File.separator+entity+"Service.java");
				FileWriter fw = new FileWriter(f);
				fw.write("package "+servicePackage+";"
				+LINE_BREAK+LINE_BREAK+LINE_BREAK
				+"import "+entityPackage+"."+entity+";"
				+LINE_BREAK+LINE_BREAK
				+"public interface "+entity+"Service {}");
				fw.flush();
				fw.close();
				System.out.println(entity+"Service.java"+"生成成功！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("***************************************");
		
	}
	
	
	/**
	 * 
	 * @method generateServiceImpl
	 * @param 
	 * @return void
	 * @desc 生成serviceImpl文件
	 * @date 2015-11-15 下午10:20:07
	 */
	private void generateServiceImpl(){
		if("".equals(serviceImplPackage)){
			serviceImplPackage = servicePackage+".serviceimpl";
			serviceImplFilePath = returnProperPath(serviceImplPackage);
		}
		for(String entity : allEntityName){
			try {
				File f = new File(projectPath+mavenJavaPath+serviceImplFilePath+File.separator+entity+"ServiceImpl.java");
				FileWriter fw = new FileWriter(f);
				fw.write("package "+serviceImplPackage+";"
				+LINE_BREAK+LINE_BREAK+LINE_BREAK
				+"import org.springframework.beans.factory.annotation.Autowired;"
				+LINE_BREAK
				+"import org.springframework.stereotype.Service;"
				+LINE_BREAK
				+"import "+daoPackage+"."+entity+"Dao;"
				+LINE_BREAK
				+"import "+entityPackage+"."+entity+";"
				+LINE_BREAK
				+"import "+servicePackage+"."+entity+"Service;"
				+LINE_BREAK+LINE_BREAK
				+"@Service(\""+StringUtil.toLowerCaseFirstLetter(entity)+"Service\")"
				+LINE_BREAK
				+"public class "+entity+"ServiceImpl implements "+entity+"Service {"
				+LINE_BREAK
				+"	@Autowired"
				+LINE_BREAK
				+"	private "+entity+"Dao "+StringUtil.toLowerCaseFirstLetter(entity)+"Dao;"
				+LINE_BREAK
				+LINE_BREAK
				+"}"
				);
				
				fw.flush();
				fw.close();
				System.out.println(entity+"ServiceImpl.java"+"生成成功！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("***************************************");
	}
	
	
	
	
	private void generateDaoAndService() throws Exception{
		this.generateDao();
		this.generateDaoImpl();
		this.generateService();
		this.generateServiceImpl();
	}
	
	/**
	 * 
	 * @method returnProperPath
	 * @param @param path
	 * @param @return
	 * @return String
	 * @desc 返回正确的路径
	 * @date 2015-11-15 下午8:51:27
	 */
	private static String returnProperPath(String path){
		String[] dirs =  path.split("\\.");
		String result = "";
		for(int i=0; i<dirs.length; i++){
			if(i<dirs.length-1){
				result+=dirs[i]+File.separator;
			}
			else{
				result+=dirs[i];	
			}
		}
		return result;
	}

	public String getEntityFilePath() {
		return entityFilePath;
	}

	public void setEntityFilePath(String entityFilePath) {
		this.entityPackage = entityFilePath;
		this.entityFilePath = returnProperPath(entityFilePath);
	}

	public String getDaoFilePath() {
		return daoFilePath;
	}

	public void setDaoFilePath(String daoFilePath) {
		this.daoPackage = daoFilePath;
		this.daoFilePath = returnProperPath(daoFilePath);
	}

	public String getServiceFilePath() {
		return serviceFilePath;
	}

	public void setServiceFilePath(String serviceFilePath) {
		this.servicePackage = serviceFilePath;
		this.serviceFilePath = returnProperPath(serviceFilePath);
	}

	public String getDaoImplFilePath() {
		return daoImplFilePath;
	}

	public void setDaoImplFilePath(String daoImplFilePath) {
		this.daoImplPackage = daoImplFilePath;
		this.daoImplFilePath = returnProperPath(daoImplFilePath);
	}

	public String getServiceImplFilePath() {
		return serviceImplFilePath;
	}

	public void setServiceImplFilePath(String serviceImplFilePath) {
		this.serviceImplPackage = serviceImplFilePath;
		this.serviceImplFilePath = returnProperPath(serviceImplFilePath);
	}
	
	
}
