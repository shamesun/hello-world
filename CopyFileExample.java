
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class CopyFileExample
{
	static String LOG="log";
	static String FILES="files";
	InputStream inStream = null;
	OutputStream outStream = null;
	File sourceFile =null;
    File destFile =null,destDir=null,parenDir=null;
    byte[] buffer = new byte[1024];
    static int i=1;
    Map<String,String> extensionMap = new HashMap<String,String>();
     //String fullSourcePath=null, destinationPath=null;
    String source = "C:/WorkSpace_Mobility_CVS";
	String destination="D:/Code_Teansfer/iCRM_Mobility/MyPlan_SI-Tranfer_1";
	String fileName=null,extension=null,destDirPath=null;
    int length,lastIndex = 0;
    String log="",files="";
    
	
    private void processCopy()
    {
    	extensionMap.put("java", "java");
    	extensionMap.put("jsp", "jsp");
    	extensionMap.put("js", "js");
    	extensionMap.put("xml", "xml");
    	extensionMap.put("properties", "properties");
    	
    	try
    		{
    		String sCurrentLine;

    		BufferedReader br = new BufferedReader(new FileReader(destination+"/info/File_List.txt"));
    		
			while ((sCurrentLine = br.readLine()) != null) 
			{
				copyFile(new File(source+sCurrentLine),sCurrentLine);
//				log+=sCurrentLine);
			}
    		/*
	    		String fileName="";
	    		 */	    
    		}catch(IOException e)
    			{
    				e.printStackTrace();
    			}
    	
    }
    
    private void copyFile(  File sourceFile,String relativePath) throws IOException // i=serial no
    {
//    	sourceFile =new File(fullSourcePath);
    	String fullSourcePath = sourceFile.getAbsolutePath();
    	
    	
    	if(fullSourcePath.lastIndexOf("/") > 0)
    	{
    		lastIndex=fullSourcePath.lastIndexOf("/");
    	}else if(fullSourcePath.lastIndexOf("\\") > 0)
    	{
    		lastIndex =fullSourcePath.lastIndexOf("\\");
    	}
    	
//    	log+="Full Source Path: "+ fullSourcePath +" Last index :: "+ lastIndex+"\t";   	
    	fileName = fullSourcePath.substring(lastIndex);
    	
    	destDirPath=(destination+"/"+relativePath).substring(0, lastIndex);	
    	if(!sourceFile.isDirectory())
		{}
    	
    	destDir =new File(destDirPath); 
    	createDirectories(destDir);
					
		
		if(!sourceFile.isDirectory())
		{
			/* finding extension */
			lastIndex = fullSourcePath.lastIndexOf(".");
			extension = fullSourcePath.substring(lastIndex+1);
			if(! extensionMap.containsKey(extension))
			{
				log+="\n\tSource File Skipped :: "+ fullSourcePath+"\n ----------\n";
			}else{
				
			/* finding extension */
			
			files+="\nFile["+ i + "]:> "+ fileName.substring(1)+ "\t";
			log+="\t"+ fullSourcePath;
			log+="\t"+ destination+"/"+relativePath;
			
		 	
			
	    destFile =new File(destination+"/"+relativePath);
	    /* verify whether file exist in source location */
		if (!sourceFile.exists()) {
			log+="Source File Not Found!"+"\n";
			throw new IOException(" Stopped !");
		}else{
	    inStream = new FileInputStream(sourceFile);
	    outStream = new FileOutputStream(destFile);
	    while ((length = inStream.read(buffer)) > 0)
	    {

		    /*opy the file content in bytes*/
	    	outStream.write(buffer, 0, length);

	    }

	    inStream.close();
	    outStream.close();
	    i++;
		}}}
		else
		{
			log+="it is a directory ! "+ fullSourcePath+"\n";
			for(String str:sourceFile.list())
    		{
				log+="\n File Name under directory :"+source+"/"+str+"\n";
    			copyFile(new File(fullSourcePath+"/"+str),relativePath+"/"+str);
//        	    log+="File is copied successful!");
    		}
			
		}
		writeFile(log,LOG);
		writeFile(files,FILES);
		  
    }
    public static void main1(String[] args)
    {
		
    	new CopyFileExample().processCopy();
    }
    public void writeFile(String log,String option) throws IOException {
    	File fout =null;
    	if(option.equals(LOG)){
    		fout = new File(destination+"/info/logs.txt");
    	}else if(option.equals(FILES)){
    		fout = new File(destination+"/info/Final_File_List.txt");
    	}
    	FileOutputStream fos = new FileOutputStream(fout);     
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
       	
    		bw.write(log);
    		bw.newLine();
    		bw.close();
    }
    private void createDirectories(File destDire)
    {
    	//parenDir
    	if(!destDire.exists())
		{

		    log+="\n creating directory: " + destDire.getName();
		    boolean result = false;

		    try{
		        destDire.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}

    	
    }
    
}