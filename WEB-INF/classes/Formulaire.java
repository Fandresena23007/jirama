package form;

import java.lang.reflect.Field;

public class Formulaire {
	Champs[] chps;
	String action;
	String method;
	Object o;
	
	public Formulaire(Object o, String action, String method) {
		setO(o);
		setChamps();
		setAction(action);
		setMethod(method);
	}

	public void setO(Object o) {
		this.o = o;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Champs getChamp(String name) {
		for(int i=0; i < chps.length; i++) {
			if(chps[i].getName().compareToIgnoreCase(name) == 0) {
				return chps[i];
			}
		}

		return null;
	}

	public void setChamps() {
		Field[] f = getAllFields();
		chps = new Champs[f.length];

		for(int i=0; i < f.length; i++) {
			chps[i] = new Champs(f[i]);
		}
	}
	
	public String generateCode() {
		String code = "";
		
		for(int i=0; i < chps.length; i++) {
			code += "<div class='form-group row'>";
			code += chps[i].getHtml();
			code += "</div>";
		}
		
		return code;
	}
	
	public int countAllFields() {
		Class c = o.getClass();
		int n = 0;
		Object obj = new Object();
		
		while(c != obj.getClass()) {
			n += (c.getDeclaredFields()).length;
			c = c.getSuperclass();
		}
		
		return n;
	}
	
	public Field[] getAllFields() {
		int t = countAllFields();
		// System.out.println("taille == " + t);
		Field[] f = new Field[t];
		Object obj = new Object();
		Class c = o.getClass();
		int e = 0;
		
		while(e < t) {
			Field[] fi = c.getDeclaredFields();
			c = c.getSuperclass();
			
			for(int i=0; i < fi.length; i++) {
				// System.out.println("Field == " + fi[i].getName());
				// System.out.println("i == " + i);
				// System.out.println("e == " + e);
				f[e] = fi[i];
				e++;
			}
		}
		
		return f;
	}
	
	public String getHtml() {
		String code = "<form action='" + getAction() + "' method='" + getMethod() + "'>";
		code += generateCode();
		code += "<div class='form-group row'>";
		code += "<input type='hidden' name='class' value='"+ o.getClass().getName() +"'/>";
		System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkk : "+o.getClass().getName());
		code += "<div class='col-md-4 col-md-offset-2'><input type='submit' class='btn btn-primary' value='Valider'/></div><div class='col-md-4 col-md-offset-2'><button type='button' class='btn btn-inverse'>Annuler</button></div>";
		code += "</div>";
		code += "</form>";
		
		return code;
	}
}