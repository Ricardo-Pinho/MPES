package logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import bd.Hospital;

public class FileManager {
	
	private static File bdf;
	
	public FileManager()
	{
		bdf = new File("bd/");
		if(!bdf.exists() || !bdf.isDirectory())
		{
			if(Global.logicdebug)
				System.out.println("cria direct");
			bdf.mkdir();
		}
	}
	
	
	
	
	
	/**
	 * carreg a base de dados indicada no argumento e devolve o objecto pretendido
	 * @param name caminho para o ficheiro
	 * @return devolve null em caso de nao carregar com sucesso caso contr�rio devolve o objecto pretendido
	 */
	public Hospital getBD(String name){
		Hospital result = null;
		
		InputStream file = null;
		try {
			
			file = new FileInputStream("bd/" + name + ".bd");// por definir 
			InputStream buffer = new BufferedInputStream( file );
		    ObjectInput input = new ObjectInputStream ( buffer );
		    Object obj = input.readObject();
		    if(obj instanceof Hospital)
		    {
		    	result = (Hospital) obj;
		    	if(Global.logicdebug)
		    		System.out.println("name of the hospital : " + result.getName());
		    }
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	    
		
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace(); ignorar
		}catch(NullPointerException e){
			// ignorar 
		}
		
	      
		
		return result;
		
	}
	
	
	/**
	 * permite guardar  base de dados passada no argumento
	 * @param bd referencia � base de dados a guardar
	 * @return em caso de sucesso retorna true caso contr�rio devolve false
	 */
	public boolean saveBD(Hospital bd){
		
		if(bd == null)
		{
			if(Global.logicdebug)
				System.out.println(-1);
			return false;
		}
		
		File f = new File("bd/" + bd.getOldname() + ".bd");
		
		if(f.exists())
		{
			try{
				f.delete();
			}catch(SecurityException e)
			{
				if(Global.logicdebug)
					System.out.println(0);
				return false;
			}
			
		}
		
		OutputStream file, buffer;
		ObjectOutput output;
		try {
			if(Global.logicdebug)
				System.out.println("name: " + bd.getName());
			file = new FileOutputStream( "bd/" + bd.getName() + ".bd" );
			buffer = new BufferedOutputStream( file );
		    output = new ObjectOutputStream( buffer );
		    output.writeObject(bd);
		    bd.setOldname(bd.getName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(Global.logicdebug)
				System.out.println(1);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(Global.logicdebug)	
				System.out.println(2 + e.getMessage());
			return false;
		}
		
		
		try {
			output.close();
			buffer.close();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}catch(NullPointerException e)
		{
			// ignorar 
		}
	    
		
		return true;
	}
	
	
	
	public String[] getBds(){
		
		String[] result;
		Vector<String> files = new Vector<String>();
		
		File f = new File("bd/");
		
		
		for( int i = 0 ; i < f.listFiles().length; i++)
		{
			files.add(f.listFiles()[i].getName().replace(".bd", ""));
		}
		result = new String[files.size()+1];
		result[0] = "";
		for( int i = 0 , it = 1; i < files.size(); i++, it++){
			result[it] = files.get(i);
		}
		for(int i = 0 ; i < result.length;i++){
			if(Global.logicdebug)
				System.out.print(i + ":" + result[i] + ";");
		}
		
		return result;
	}





	public void deleteBD(Hospital bd) {
		// TODO Auto-generated method stub
		if(bd != null)
		{
			File f = new File("bd/" + bd.getFileName() + ".bd");
			
			if(f.exists())
			{
				try{
					f.delete();
				}catch(SecurityException e)
				{
					//System.out.println(0); ignorar 
					//return false;
				}
				
			}
		}
		
		
	}





	public void deleteBD(String oldname) {
		// TODO Auto-generated method stub
		File f = new File("bd/" + oldname + ".bd");
		
		if(f.exists())
		{
			try{
				f.delete();
			}catch(SecurityException e)
			{
				//System.out.println(0); ignorar 
				//return false;
			}
			
		}
	}
	
	

}
