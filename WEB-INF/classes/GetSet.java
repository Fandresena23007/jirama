package function;

import java.lang.reflect.Field;

public class GetSet {

	public GetSet(){}

	public String getT(String s) {
		String[] types = {"int","double","float","long","char"};
		String[] t = {"I","D","F","J","C"};
		
		for(int i=0; i < t.length; i++) {
			if(s.compareTo(t[i]) == 0) {
				return types[i];
			}
		}
		
		// System.out.println("length " + s.length());
		String type = s.substring(s.lastIndexOf(".")+1,s.length()-1);
		
		return type;
	}
	
	public String getType(Class c) {
		String res = "";
		String className = c.getName();
		
		if(c.isArray()) {
			while(className.indexOf("[") > -1) {
				className = className.substring(className.indexOf("[")+1);
				res += "[]";
			}
			// System.out.println("className " + className);
			res = getT(className) + res; 
		}
		else {
			int p = c.getName().lastIndexOf(".");
			res = c.getName().substring(p+1);
		}
		
		return res;
	}
	
	public String getSetString(Field f) {
		String res = "";
		String name = f.getName();
		String att = name.substring(0,1).toUpperCase() + name.substring(1);
		Class c = f.getType();
		System.out.println(c.getName());
		String type = getType(c);
		System.out.println(type);
		
		res += "<p>public void set"+att+"("+ type + " " + name + ") { </p>";
		res += "<p>this."+ name + " = " + name + ";</p>";
		res += "<p> } </p>";
		
		return res;
	}
	
	public String getGetString(Field f) {
		String res = "";
		String name = f.getName();
		String att = name.substring(0,1).toUpperCase() + name.substring(1);
		Class c = f.getType();
		String type = getType(c);
		
		res += "<p>public " + type + " get"+att+"() { </p>";
		res += "<p>return this."+ name +";</p>";
		res += "<p> } </p>";
		
		return res;
	}
	
	public String createAllInClass(Object o) throws Exception {
		Class c = o.getClass();
		Field[] f = c.getDeclaredFields();
		String res = "";
		
		for(int i=0; i < f.length; i++) {
			res += getSetString(f[i]);
			res += getGetString(f[i]);
		}
		
		// System.out.println(res);
		
		return res;
	}
}