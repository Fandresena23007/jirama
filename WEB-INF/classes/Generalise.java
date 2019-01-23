package general;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;

public class Generalise {
	public Method getFunction(Object objs, String att) throws Exception {
		// att = att.toLowerCase();
		String nomFonction = "get" + att.substring(0,1).toUpperCase() + att.substring(1);
		Class c = objs.getClass();
		Method m = c.getMethod(nomFonction);

		return m;
	}
		
	public Object[] findOpt(String column, String value, Object[] ob_tab) throws Exception {
		Object[] res = new Object[0];
		
		for(int e=0; e < ob_tab.length; e++) {
			if(ob_tab[e] != null) {
				Method m = getFunction(ob_tab[e],column);
				String str = m.invoke(ob_tab[e]).toString().toLowerCase();
				
				// System.out.println("str == " + str);
				// System.out.println("value == " + value);
				// System.out.println("comp == " + str.compareTo(value.toLowerCase()));
				
				if(str.indexOf(value.toLowerCase()) != -1) {
					res = ajoutObj(res,ob_tab[e]);
					ob_tab[e] = null;
					// System.out.println("trouve");
				}
			}
		}
		
		return res;
	}
	
	public Object[] ajoutObj(Object[] obj, Object o) {
		Object[] nouv = new Object[obj.length+1];
		int i = 0;
		
		while(i < obj.length) {
			nouv[i] = obj[i];
			
			i++;
		}
		
		nouv[i] = o;
		
		return nouv;
	}


	public Object[][] ajoutTable(Object[][] obj, Object[] o) {
		Object[][] nouv = new Object[obj.length+1][];
		int i = 0;
		
		while(i < obj.length) {
			nouv[i] = obj[i];
			
			i++;
		}
		
		nouv[i] = o;
		
		return nouv;
	}
	
	public String getTableFromClass(Class c) {
        String table_name = c.getName();
        int n = table_name.lastIndexOf('.') + 1;
        table_name = table_name.substring(n);
        
        return table_name;
    }
	
	public String FName(String type, String att) {
		String nomF = "";
		String[] table = att.split("\\.");
		att = table[table.length-1];

		if(type != null) {
			nomF += type;
		}	
		
		nomF += att.substring(0,1).toUpperCase() + att.substring(1);
		
		return nomF;
	}
	
	public Object[] select(Connection con, Object o, String where) throws Exception {
		Object[] res = new Object[0];
		Class c = o.getClass();
		String nt = getTableFromClass(c);
		String sql = "SELECT * FROM " + nt;
		
		if(where != null) {
			sql += " WHERE " + where;
		}
		
		System.out.println("general select sql " + sql);
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery(sql);
		Field[] f = c.getDeclaredFields();
		
		for(int p=0; rs.next(); p++) {
			Object obj = c.newInstance();
			
			for(int i=0; i < f.length; i++) {
				String att = f[i].getName();
				String nF = FName("set",att);
				Class type = f[i].getType();
				Method m = c.getMethod(nF,type);
				
				// System.out.println("type " + type.getName());
				
				String v = rs.getString(att);
				
				try {
					Object cast = type.cast(v);
					m.invoke(obj,cast);
				}
				catch(ClassCastException cce) {
					String parseF = "parse"+FName(null,type.getName());
					Method parseM = Generalise.class.getMethod(parseF,String.class);
					m.invoke(obj,parseM.invoke((new Generalise()),v));
				}
			}
			
			res = ajoutObj(res,obj);
		}
		
		rs.close();
		s.close();
		
		return res;
	}
	
	public Object[] selectOpt(Connection con, Object o, String where) throws Exception {
		Object[] res = new Object[0];
		Class c = o.getClass();
		String nt = getTableFromClass(c);
		String sql = "SELECT * FROM " + nt;
		
		if(where != null) {
			sql += " WHERE " + where;
		}
		
		System.out.println("general select sql " + sql);
		Statement s = con.createStatement();
		s.execute("ALTER SESSION SET nls_Timestamp_format = 'YYYY-MM-DD HH24:MI:SS.ff'");
		s.execute("ALTER SESSION SET nls_Date_format = 'YYYY-MM-DD HH24:MI:SS'");
		ResultSet rs = s.executeQuery(sql);
		Field[] f = c.getDeclaredFields();
		
		for(int p=0; rs.next(); p++) {
			Object obj = c.newInstance();
			
			for(int i=0; i < f.length; i++) {
				String att = f[i].getName();
				String nF = FName("set",att);
				Class type = f[i].getType();
				String dbGet = FName("get",type.getName());
				Method rSMethod = ResultSet.class.getMethod(dbGet,int.class);
				
				try {
					Method m = c.getMethod(nF,type);
					
					// System.out.println("type " + type.getName());
					
					
					Modifier mo = new Modifier();
					
					if(mo.isPublic(rSMethod.getModifiers())) {
						m.invoke(obj,rSMethod.invoke(rs,(i+1)));
					}
				}
				catch(Exception e) {
					throw new Exception("La fonction " + nF + "(" + FName(null,type.getName()) + " " + att +") n'existe pas dans la classe " + nt + " ou elle n'est pas public");
				}
			}
			
			res = ajoutObj(res,obj);
		}
		
		rs.close();
		s.close();
		
		return res;
	}
	
	public void setAll(Object o, String[] valeur, Connection con) throws Exception {
        Class c = o.getClass();
        Field[] f = c.getDeclaredFields();

        for(int i=0; i < f.length; i++) {
			if(valeur[i] != null) {
				String att = f[i].getName();
				String nomFonction = FName("set",att);
				// System.out.println("fonction " + m);
				Method m = null;
				
				//try {
					try {
						Class[] cls = new Class[2];
						cls[0] = String.class;
						cls[1] = Connection.class;
						m = c.getMethod(nomFonction,cls);
						m.invoke(o,valeur[i],con);
					}
					catch(Exception ex) {
						m = c.getMethod(nomFonction,String.class);
						m.invoke(o,valeur[i]);
					}
					
					System.out.println("method " + m);
					
				//} catch(Exception e) {
				//	throw new Exception("La colonne " + att + " a pour valeur " + e.getMessage());
				//}
			}
        }
    }

	public int parse(double d) {
		int i = (new Integer((""+d).substring(0,(""+d).indexOf(".")))).intValue();
		
		return i;
	}
	
	public int parseInt(String s) {
		return (new Integer(s)).intValue();
	}
	
	public double parseDouble(String s) {
		return (new Double(s)).doubleValue();
	}
	
	public double parseLong(String s) {
		return (new Long(s)).longValue();
	}

	public Timestamp parseTimestamp(String d) throws Exception{
		return controlDate(d);		
	}

    public int insert(Connection con, Object o) throws Exception {
        System.out.println("avant");
        String sql = getInsertSQL(o);
        System.out.println("SQL for insert : " + sql);
        Statement s = con.createStatement();
		int n = 0;
		s.execute("ALTER SESSION SET nls_Timestamp_format = 'YYYY-MM-DD HH24:MI:SS.ff'");
		s.execute("ALTER SESSION SET nls_Date_format = 'YYYY-MM-DD HH24:MI:SS'");
        n = s.executeUpdate(sql);
        s.execute("commit");
        s.close();
		
		return n;
    }
	
	public double getSum(Object[] o, String att) throws Exception {
        String nomFonction = "get" + att.substring(0,1).toUpperCase() + att.substring(1);
        double res = 0;
        System.out.println(nomFonction);
        
        for(int i=0; i < o.length; i++) {
            Class c = o[i].getClass();
            Method m = c.getMethod(nomFonction);
            System.out.println(m.invoke(o[i]).toString());
            double n = (new Double(m.invoke(o[i]).toString())).doubleValue();
            res  = res + n;
        }
        
        return res;
    }
	
	public String[] getFRMonth() {
		String[] m = {"Janvier","Fevrier","Mars","Avril","May","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};

		return m;
	}
	
	public String format(String dt) {
		// System.out.println("to format " + dt);
		String[] m = getFRMonth();
		String[] d = dt.split(" ");
		// System.out.println("length " + d.length);
		String res = "";

		for(int i=0; i < m.length; i++) {
			if(d[1].compareToIgnoreCase(m[i]) == 0) {
				res += (i+1);
				// System.out.println("res " + res);
				break;
			}
		}
		// System.out.println("res a " + res);
		res += "/" + d[0] + "/" + d[2];
		
		if(d.length > 3) {
			res += " " + d[3];
		}
		
		// System.out.println("res " + res);
		
		return res;
	}

	public String transformDate(String d) {
		String[] dt = d.split("/");
		String res = dt[1] + "/" + dt[0] + "/" + dt[2];
		
		return res;
	}

	public Timestamp controlDate(String date) throws Exception {
		Timestamp d = null;
		// System.out.println("date " + date);
		String dt = date.replace("-","/");

		try {
			dt = transformDate(dt);
			// System.out.println("dt tra " + dt);
			d = new Timestamp(Timestamp.parse(dt));
			// System.out.println("d ---> " + d);
		}catch(Exception e) {
			try {
				d = new Timestamp(Timestamp.parse(date));
				// System.out.println("month d " + d.getMonth());
				// System.out.println("date d " + d.getDate());
			}
			catch(Exception ex) {
				try {
					dt = format(date);
					// System.out.println("format " + dt);
					d = new Timestamp(Timestamp.parse(dt));
				}
				catch(Exception exc) { 
					// System.out.println("exc " + e.getMessage());
					throw new Exception("Date Invalide !");
				}
			}
		}
		
		return d;
	}
	
	public Timestamp now(Connection con) throws Exception {
		String sql = "SELECT current_timestamp FROM DUAL";
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery(sql);
		res.next();
		
		Timestamp t = res.getTimestamp(1);
		
		res.close();
		stat.close();
		
		return t;
	}
	
	public int update(Connection con, String table, String value, String cond) throws Exception {
		String sql = "UPDATE " + table + " SET " + value + " WHERE " + cond;
		System.out.println("update general " + sql);
		Statement stat = con.createStatement();
		stat.execute("ALTER SESSION SET nls_Timestamp_format = 'YYYY-MM-DD HH24:MI:SS.ff'");
		stat.execute("ALTER SESSION SET nls_Date_format = 'YYYY-MM-DD HH24:MI:SS'");
		int n = stat.executeUpdate(sql);
		stat.execute("commit");
		stat.close();
		
		return n;
	}
	
	public int diffEnJours(Timestamp t1, Timestamp t2) {
		long diff = Math.abs(t1.getTime()-t2.getTime());
		long d = 86400000l;
		System.out.println("t1" + t1);
		System.out.println("t1" + t2);
		System.out.println("t1" + t1.getYear());
		System.out.println("t1" + t2.getYear());
		System.out.println("t1" + t1.getTime());
		System.out.println("t2" + t2.getTime());
		System.out.println("diff j" + (diff/d));
		
		return 0;
	}

	public String getInsertSQL(Object o) throws Exception {
        Class c = o.getClass();
        
		String table_name = getTableFromClass(c);
        
        // System.out.println(table_name);
        String sql = "INSERT INTO " + table_name + " VALUES(";
        Field[] f = c.getDeclaredFields();
        
        // System.out.println("length " + f.length);
        for(int i=0; i < f.length; i++) {
            String att = f[i].getName();
            String nomFonction = FName("get",att);
            Method m = c.getMethod(nomFonction);
            Object obj = m.invoke(o);
            String res = "";
            
            if(obj == null) {
                res = "null";
            }
            else {
                res = obj.toString();
            }
			
			// System.out.println("to String : " + res);
            String seq = "id" + table_name + ".nextVal";
			
			if(obj != null && !checkSequence(seq,res)) {
				if(f[i].getType() == res.getClass() || f[i].getType() == Date.class || f[i].getType() == Timestamp.class) {
					res = "'" + res +"'";
				}
			}
			
            sql += res;
            
            if(i != (f.length-1)) {
                sql += ",";
            }

            // System.out.println(sql);
        }
        
        sql += ")";
        
        return sql;
    }
	
	public boolean checkSequence(String seq, String res) {
		boolean r = false;
		int c = res.indexOf("|");
		
		if(c != -1) {
			if(seq.compareTo(res.substring(c+1)) == 0) {
				r = true;
			}
		}
		
		return r;
	}
}