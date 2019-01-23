package function;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import connexion.DbConnection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import exception.*;


public class Function
{

    public Function(){}

   public String majuscule(String s)
    {
        String ret=s;
        Character c=ret.charAt(0);
        c=c.toUpperCase(c);
        char[]ch=s.toCharArray();
        ch[0]=c;
        String mot=new String(ch);
        return mot;
    }

    public Object construireUnObjet(Class laClassDeLobjet,ResultSet re)throws Exception
    {
        ResultSetMetaData rsmd=re.getMetaData();
        int taille=rsmd.getColumnCount();
        int[]nbr=new int[1];nbr[0]=0;
        Object ret=laClassDeLobjet.newInstance();
        Class cs=laClassDeLobjet;
        while(1==1)
        {
            if(cs.getName().compareTo("java.lang.Object")==0)break;
            Field[]champ=cs.getDeclaredFields();
            for(int i=0;i<champ.length;i++)
            {
                Method mOb;//fonction maptiditra ny valeur ao @ object
                Method mVal;//fonction maka ny valeur ao@base;
                for(int j=nbr[0]+1;j<=taille;j++)
                {
                    if(champ[i].getName().compareToIgnoreCase(rsmd.getColumnLabel(j))==0)
                    {
                        mOb=laClassDeLobjet.getMethod("set"+majuscule(champ[i].getName()),champ[i].getType());
                        String[]tab=champ[i].getType().getName().split("\\.");
                        // mVal=re.getClass().getMethod("get"+majuscule(tab[tab.length-1]),tab[0].getClass());
                        // Object ob=mVal.invoke(re,champ[i].getName());
                        // mOb.invoke(ret,ob);
                        String nom=tab[tab.length-1];
                        if(nom.compareToIgnoreCase("int")==0)
                        {
                            mOb.invoke(ret,re.getInt(champ[i].getName()));
                        }
                        else if(nom.compareToIgnoreCase("string")==0)
                        {
                            mOb.invoke(ret,re.getString(champ[i].getName()));
                        }
                        else if(nom.compareToIgnoreCase("date")==0)
                        {
                            mOb.invoke(ret,re.getDate(champ[i].getName()));
                        }
                        else if(nom.compareToIgnoreCase("timestamp")==0)
                        {
                            mOb.invoke(ret,re.getTimestamp(champ[i].getName()));
                        }
                        else if(nom.compareToIgnoreCase("double")==0)
                        {
                            mOb.invoke(ret,re.getDouble(champ[i].getName()));
                        }
                        else if(nom.compareToIgnoreCase("float")==0)
                        {
                            mOb.invoke(ret,re.getFloat(champ[i].getName()));
                        }
                        else if(nom.compareToIgnoreCase("Object")==0)
                        {
                            mOb.invoke(ret,re.getObject(champ[i].getName()));
                        }
                        break;
                    }
                }
                System.out.println();
                if(nbr[0]==taille)break;
            }
            if(nbr[0]==taille)break;
            cs=cs.getSuperclass();
        }
        return ret;
    }
    String findWhere(Object o)throws Exception
    {
        int nbr=0;
        String cond="";
        Class c=o.getClass();
        Field[]f=c.getDeclaredFields();
        for(int i=0;i<f.length;i++)
        {
            Method m=c.getMethod("get"+majuscule(f[i].getName()));
            Class type=f[i].getType();
            Object ob=m.invoke(o);
            Number zero=new Integer(0);
            if(type.isPrimitive()==true && ob instanceof Number)
            {
                Number n=(Number)ob;
                Integer in=new Integer(n.intValue());
                if(in.intValue()!=0)
                {
                    if(nbr==0)cond+=" where ";
                    else cond+=" and ";
                    cond+=f[i].getName()+" like "+in.toString();
                    nbr++;
                }
            }
            else if(ob!=null)
            {
                
                if(nbr==0)cond+=" where ";
                else cond+=" and ";
                cond+=f[i].getName()+" like '"+ob.toString()+"'";
                nbr++;
            }
        }
        return cond;
    }

    public Object[]find(String table,Object o,String[]cond,Connection c)throws Exception
    {
        Vector vec=new Vector();
        boolean bool=false;
        if(c==null)
        {
            DbConnection dbc=new DbConnection();
            c=dbc.connected("jirama","jirama");
            bool=true;
        }
        Statement s=null;
        s=c.createStatement();
        String requette="select * from "+table;
        Field[]champ=o.getClass().getDeclaredFields();
        String w=findWhere(o);
        requette+=w;
        if(cond!=null)
        {
            for(int i=0;i<cond.length;i++)
            {
                requette+=" "+cond[i];
            }
        }
        System.out.println(requette);
        ResultSet res=s.executeQuery(requette);
        boolean b=res.next();
        System.out.println("B : "+b);
        // System.out.println(o.getClass());
        Class ss=o.getClass();
        while(b==true)
        {
            int[]j=new int[1];j[0]=0;
            Object ob=construireUnObjet(ss,res);
            vec.add(ob);
            b=res.next();
        }

        res.close();s.close();
        //if(bool==true)c.close();
        return vec.toArray();
    }
    
    Class getClass(Object ob)throws Exception
    {
        if(ob.getClass().getName().compareTo("int")==0)
        {
            return Class.forName("Integer");
        }
        else if(ob.getClass().getName().compareTo("double")==0)
        {
            return Class.forName("Double");
        }
        else if(ob.getClass().getName().compareTo("float")==0)
        {
            return Class.forName("Float");
        }
        else if(ob.getClass().getName().compareTo("Char")==0)
        {
            return Class.forName("Character");
        }
        return ob.getClass();
    }
    
    public void ajouter(Object o,String table,Connection c)throws Exception
    {
        Vector vec=new Vector();
        boolean bool=false;
         if(c==null)
        {
            DbConnection dbc=new DbConnection();
            c=dbc.connected("jirama","jirama");
            bool=true;
        }
        Statement s=null;
        s=c.createStatement();
        String request="insert into "+table;
        String colom="(";
        String value="(";
        int nbr=0;
        Field[]champ=o.getClass().getDeclaredFields();
        int j=0;
        for(int i=0;i<champ.length;i++)
        {
            Method m=o.getClass().getMethod("get"+majuscule(champ[i].getName()));
            Object ob=m.invoke(o);
            if(m.getReturnType().isPrimitive()==false)
            {
                if(champ[i].getName().startsWith("id")==true && ob==null)
                {
                    if(nbr!=0){colom+=",";value+=",";}
                    colom+=champ[i].getName();
                    value+="to_Char(sec"+majuscule(table)+majuscule(champ[i].getName())+".nextval)";
                    nbr++;
                }
                else if(ob!=null)
                {
                    
                    if(nbr!=0){colom+=",";value+=",";}
                    colom+=champ[i].getName();
                    // String obj = (String)ob;
                    // System.out.println("Voici la reponse : "+obj);
                    // System.out.println("");
                    // if(obj.startsWith("'") == true){
                    //     value+= ""+ob+"";
                    //     System.out.println("Je suis ici");
                    // }
                    // else{
                        value+="'"+ob+"'";
                    // }
                    nbr++;
                }
            }
            else
            {
                Number zero=new Integer(0);
                if(champ[i].getName().startsWith("id")==true && ob.equals(zero)==true)
                {
                    if(nbr!=0){colom+=",";value+=",";}
                    colom+=champ[i].getName();
                    value+="sec"+majuscule(table)+majuscule(champ[i].getName())+".nextval";
                    nbr++;
                }
                else if((ob instanceof Number && ob.equals(zero)==false))
                {
                    if(nbr!=0){colom+=",";value+=",";}
                    colom+=champ[i].getName();
                    value+=ob;
                    nbr++;
                }
                else
                {

                }
                
            }
            
        }
        colom=colom+" )";
        value=value+" )";
        request=request+colom+" values "+value;
        System.out.println(request);
        int res=s.executeUpdate(request);
        System.out.println(res);
        c.commit();s.close();
        //if(bool==true)c.close();
    }
    
    
    public void alter(String table,String colom,String value,String condition,String valeurCondition,Connection c)throws Exception
    {
        boolean bool=false;
         if(c==null)
        {
            DbConnection dbc=new DbConnection();
            c=dbc.connected("jirama","jirama");
            bool=true;
        }
        Statement s=null;
        s=c.createStatement();
        String request="update "+table+" set "+colom+"='"+value+"'  where  "+condition+"='"+valeurCondition+"'";
        System.out.println(request);
        int res=s.executeUpdate(request);
        System.out.println( table+" "+res);
        c.commit();s.close();
        //if(bool==true)c.close();
    }
    public void supprimer(String table,String condition,String valeurCondition,Connection c)throws Exception
    {
        boolean bool=false;
        if(c==null)
        {
            DbConnection dbc=new DbConnection();
            c=dbc.connected("jirama","jirama");
            bool=true;
        }
        Statement s=null;
        s=c.createStatement();
        String requette="delete from "+table+" where "+condition+" = "+valeurCondition;
        System.out.println(requette);
        int res=s.executeUpdate(requette);
        System.out.println(res);
        c.commit();s.close();
        //if(bool==true)c.close();
    }
    
    public void ajouter(String[]values,String table,Connection c)throws Exception
    {
         Vector vec=new Vector();
         boolean bool=false;
         if(c==null)
        {
            DbConnection dbc=new DbConnection();
            c=dbc.connected("jirama","jirama");
            bool=true;
        }
        Statement s=null;
        s=c.createStatement();
        String request="insert into "+table+" values(";
        for(int i=0;i<values.length;i++)
        {
            if(i==0) request+=values[i];
            else request+=", "+values[i];
        }

        request+=")";
        System.out.println(request);
        int res=s.executeUpdate(request);
        System.out.println(res);
        c.commit();s.close();
        //if(bool==true)c.close();
    }

    public void resultObject(Object[] val) throws Exception{
        System.out.println("resultat: ");
        Class c = val[0].getClass();
        Field[] attribu = c.getDeclaredFields();
        for(int i=0; i < val.length; i++){
            for(int t=0; t< attribu.length; t++){
                String recupname = attribu[t].getName().toLowerCase();
                String function = "get" + majuscule(recupname);
                Method m = c.getMethod(function);
                String invoke = m.invoke(val[i]).toString();
                System.out.println("  "+ recupname + ": "+ invoke+"\t");
            }
            System.out.println("\n\n");
        }
    }

    public Object[] selectotp(Object[] table, String col, String valeur) throws Exception{
            col = col.toLowerCase();
            valeur = valeur.toLowerCase();
            String val = "get" + col.substring(0,1).toUpperCase()+col.substring(1);
            Class c = table[0].getClass();
            Method m = c.getMethod(val);
			Object[] result = new Object[0];
            for(int i=0; i < table.length; i++){
                String invoke = ((String)m.invoke(table[i])).toLowerCase();
                if(invoke.compareTo(valeur) == 0 || invoke.indexOf(valeur) != -1){
						result = addObj(result,table[i]);
						if(result != null){
							table[i]=null;
						}
                }
            }
            return result;
	}


    public Object[] selectOptimiser(String column, String value, Object[] ob_tab) throws Exception {
		Object[] res = new Object[0];
		
		for(int e=0; e < ob_tab.length; e++) {
            if(ob_tab[e] != null){   
                Method m = getFunction(ob_tab[e],column);
			    String str = m.invoke(ob_tab[e]).toString().toLowerCase();
            
                if(str.indexOf(value.toLowerCase()) != -1) {
                    res = addObj(res,ob_tab[e]);
                    ob_tab[e] = null;
                    // System.out.println("trouve");
                }
            }

		}
		
		return res;
	}
	
	public Method getFunction(Object objs, String att) throws Exception {
        String nomFonction = "get" + att.substring(0,1).toUpperCase() + att.substring(1);
        Class c = objs.getClass();
        Method m = c.getMethod(nomFonction);

        return m;
    }
	
	public Object[] addObj(Object[] tab, Object obj) {
		int n = tab.length;
		Object[] res = new Object[n+1];
		int i = 0;
		
		while(i < n) {
			res[i] = tab[i];
			i++;
		}
		res[n] = obj;
		
		return res;
	}

    public String makeDate(Date d){
		int day = d.getDate();
		int month = d.getMonth();
		int year = d.getYear();
		String valiny = day +"-"+month+"-"+ year;
		return valiny;
	}

    boolean verifierid(String nom,String nomClass){
        nomClass = nomClass.toLowerCase();
        String averifier = "id"+nomClass;
        boolean val = false;
        if(nom.indexOf(averifier)!=-1){
            val = true;
        }
        return val;
    }

    public String Insertinto(Object table) throws Exception{
        Class c = table.getClass();
        Field[] attribu = c.getDeclaredFields();
        int indexpoint = c.getName().lastIndexOf(".");
        int debutnouv = indexpoint + 1;
        String sql = "Insert into "+ c.getName().substring(debutnouv) + " values(";
        for(int i=0; i < attribu.length; i++){
            String recupname = attribu[i].getName();
            String function = "get" + majuscule(recupname);
            System.out.println("le fonction recuperer"+function);
            Method m = c.getMethod(function);
            String invoke = m.invoke(table).toString();
            if(m.invoke(table).getClass() == Date.class){
                invoke = makeDate((Date)m.invoke(table));
            }
                Class type = attribu[i].getType();
                Class ch = invoke.getClass();
                if(verifierid(recupname,c.getName().substring(debutnouv))){
                    invoke = invoke;
                }else if(type == ch || type==Date.class){
                    invoke = "'" + invoke + "'";
                }
                else{
                    invoke = "timestamp'" + invoke + "'";
                }
                sql = sql + invoke;
            if(i != (attribu.length-1)){
                sql = sql + ",";
            }
        }
        sql = sql + ")";
        System.out.print(sql);
        return sql;
    }

    
    public void insertbdd(Object obj,Connection c) throws Exception{
        boolean bool=false;
         if(c==null)
        {
            DbConnection dbc=new DbConnection();
            c=dbc.connected("jirama","jirama");
            bool=true;
        }
        Statement s=null;
        s=c.createStatement();
        String sql = Insertinto(obj);
        System.out.print(sql);
        ResultSet a = s.executeQuery(sql);
        a = s.executeQuery("commit");
        s.close();
    }

    public int[] modifHeure(double heure)throws Exception{
        int[] time = new int[3]; 

        for(int i = 0; i<time.length;i++){
            String heures = ""+heure;
            String[] heureTab = heures.split("\\.");
            String heure1 = heureTab[0];
            int hFinal = new Integer(heure1).intValue();

            time[i] = hFinal;

            double heure2 = heure - hFinal;
            heure = heure2 * 60;
        }

        return time;
    }

    public String afficheModifH(double heures)throws Exception{
        int[] time = modifHeure(heures);
        String heure = ""+time[0];
        String minute = ""+time[1];
        String seconde = ""+time[2];
        String valiny = heure+" h "+minute+" min "+seconde+" s";

        return valiny;
    }

    public Timestamp getNow(Connection c)throws Exception
    {
         boolean bool=false;
         if(c==null)
        {
            DbConnection dbc=new DbConnection();
            c=dbc.connected("airMadUpdated","airmad");
            bool=true;
        }
        Statement s=null;
        s=c.createStatement();

        ResultSet r1=null;
        String request1="select current_timestamp now from dual";
        Timestamp now = null;
        r1=s.executeQuery(request1);
        if(r1.next()){
            now = r1.getTimestamp(1);
        }
        s.close();
        return now;
    }

        public String transformDate(String dateD){
        String[] dates = new String[3];
        dates[0] = dateD.substring(0,2);
        dates[1] = dateD.substring(3,5);
        dates[2] = dateD.substring(6,10);
        
        String[] times = new String[2];
        times = dateD.split(" ");
        String temps = times[1];
    
        String s = dates[2]+"/"+dates[1]+"/"+dates[0]+" "+temps;

        return s;
    }
    
    public int getMonthId(String mois){
        String[] moisFr = new String[12];
        moisFr[0] = "Janvier";moisFr[1] = "Fevrier";moisFr[2] = "Mars";
        moisFr[3] = "Avril";moisFr[4] = "Mai";moisFr[5] = "Juin";moisFr[6] = "Juillet";moisFr[7] = "Aout";
        moisFr[8] = "Septembre";moisFr[9] = "Octobre";moisFr[10] = "Novembre";moisFr[11] = "Decembre";
        int m = 0;
        for(int i=0;i<moisFr.length;i++){
            if(mois.compareToIgnoreCase(moisFr[i]) == 0){
                m = i;
                break;
            }
            m = i;
        }
        return m;
    }

    public String[] getListeMois(){
        String[] moisFr = new String[12];
        moisFr[0] = "Janvier";moisFr[1] = "Fevrier";moisFr[2] = "Mars";
        moisFr[3] = "Avril";moisFr[4] = "Mai";moisFr[5] = "Juin";moisFr[6] = "Juillet";moisFr[7] = "Aout";
        moisFr[8] = "Septembre";moisFr[9] = "Octobre";moisFr[10] = "Novembre";moisFr[11] = "Decembre";

        return moisFr;
    }

    public Object[] addObject(Object[] tab, Object o) {
		Object[] nouv = new Object[tab.length+1];
		for(int i=0; i < tab.length; i++) {
			nouv[i] = tab[i];
		}
		nouv[tab.length] = o;
		return nouv;
	}

    public int getFinDuMois(int mois,int annee){
        String[] moisFr = new String[12];
        moisFr[0] = "Janvier";moisFr[1] = "Fevrier";moisFr[2] = "Mars";
        moisFr[3] = "Avril";moisFr[4] = "Mai";moisFr[5] = "Juin";moisFr[6] = "Juillet";moisFr[7] = "Aout";
        moisFr[8] = "Septembre";moisFr[9] = "Octobre";moisFr[10] = "Novembre";moisFr[11] = "Decembre";

        int[] finDuMois = new int[12];

        if(annee % 4 == 0 ){
            finDuMois[1] = 29;  
        }

        finDuMois[0] = 31; finDuMois[1] = 28; finDuMois[2] = 31; finDuMois[3] = 30; 
        finDuMois[4] = 31; finDuMois[5] = 30; finDuMois[6] = 31; finDuMois[7] = 31; 
        finDuMois[8] = 30; finDuMois[9] = 31; finDuMois[10] = 30; finDuMois[11] = 31; 
        
        return finDuMois[mois - 1];
    }

    public String format(String date) throws DateException{ 
        String[] date1 = new String[4];
        date1 = date.split(" ");
        String jour = date1[0];
        String mois = date1[1];
        String annee = date1[2];
        

        int m = getMonthId(mois);
        m = m+1;
        String newMois = ""+m;

        String valiny = newMois+"/"+jour+"/"+annee;

        if(date1.length > 3){
            String temps = date1[3];
            valiny += " "+temps;
        } 
        
        return valiny;
    }

    public Timestamp controllDate(String dates)throws DateException{
        Timestamp d = null;
        int c = dates.indexOf(".");
        if(c != -1){
            dates = dates.substring(0,c);
        }
        String dt = dates.replace("-","/");
        // System.out.println("La date dt : "+dt);
        // System.out.println("date apres substring "+dates);

        try{
            transformDate(dt);
            d = new Timestamp(Timestamp.parse(dt));
            System.out.println("Date try1 : "+d);

        }
        catch(Exception e){
            try{
                d =  new Timestamp(Timestamp.parse(dates));
                System.out.println("Date catch1 : "+d);
            }
            catch(Exception ex){
                try{
                    dt = format(dates);
                    d = new Timestamp(Timestamp.parse(dt));
                    System.out.println("Date try2 : "+d);
                }
                catch(Exception exc){
                    throw new DateException("Date invalide !");
                }
            }
        }
        System.out.println("Final date : "+d);
        return d;
    }

    public double format2(double d){
        DecimalFormat df = new DecimalFormat("0.00");
        String s = new String(df.format(d));
        s = s.replace(",",".");
        double db = (new Double(s)).doubleValue();

        return db;
    }

    public String change(String d) {
		String resultat = d;
		int i = resultat.indexOf("E");
		
		if(i != -1) {
			int indice = resultat.indexOf(".");
			String milieu = resultat.substring((indice+1),i);
			int p = (new Integer(resultat.substring(i+1))).intValue() - milieu.length();
			String nb = resultat.substring(0,indice) + milieu;
			resultat = nb;
			
			for(int j=0; j < p; j++) {
				resultat += "0";
			}
		}
        	
		return resultat;
	}
	
	public String formatage(double d, String separateur) {
        String resultat = "";

        if(d == 0){
            resultat = ""+d;
            return resultat;
        }

        d = format2(d);
		String s = change(""+d);
        System.out.println("String s : "+s);
		int indice = s.indexOf(".");
		int apresSep = 0;
		String avantSep = s;
		
		if(indice != -1) {
			apresSep = (new Integer(s.substring(indice+1))).intValue();
			avantSep = s.substring(0,indice);
		}
		
		int j = avantSep.length() / 3;
		int debut = avantSep.length()-3;
		String avant = "";
		
		for(int i=0; i < j; i++) {
			avant = avantSep.substring(0,debut);
			resultat = avantSep.substring(debut,debut+3) + resultat;
			
			System.out.println("avant " + avant);
			
			if(separateur != null && avant.equals("") == false) {
				resultat = separateur + resultat;
			}
			
			debut -= 3;
		}
		
		if(avant != "") {
			resultat = avant + resultat;
		}
		
		if(apresSep > 0) {
			resultat += "." + apresSep;
		}
		
		return resultat;
	}

    public Object[] find1(Connection c, String table, Object objinstance, String where) throws Exception{
		//retourne un TABLEAU D obj qui est soit une tab de Dept ou de Emp
		Statement st = c.createStatement();
		String sql =  "SELECT * FROM "+table+" WHERE "+where;
		System.out.print(sql);
        st.execute("ALTER SESSION SET NLS_TIMESTAMP_FORMAT = 'YYYY-MM-DD HH24:MI:SS.ff'");
		ResultSet resSet = st.executeQuery(sql);
		//conversion
		int nbrCol = resSet.getMetaData().getColumnCount();
		Object[] valiny = new Object[0]; //VA
		Class cls = objinstance.getClass();
		//Object obj = cons.newInstance(); //possible
		Object obj = cls.newInstance();
		Field[] attribut = obj.getClass().getFields(); //on pourra avoir les noms des cols ici!!!

		String[] typeCols = new String[attribut.length];
		for(int i=0; i < attribut.length; i++) {
			String type = attribut[i].getType().getName();
			typeCols[i] = type;
		}
		
		while(resSet.next()) {
			Object oo = cls.newInstance(); //aucun arg
			for(int i=0; i < attribut.length; i++) {
				String nomAttribut = attribut[i].getName();
					// System.out.println(nomAttribut);
				String type = typeCols[i];
				// System.out.println("LE TYPE EST "+type);
				if(type.equals("int")) {
					attribut[i].set(oo, resSet.getInt(nomAttribut));	
				} else if(type.equals("double")){
					attribut[i].set(oo, resSet.getDouble(nomAttribut));					
				} else if(type.equals("float")){
					attribut[i].set(oo, resSet.getFloat(nomAttribut));					
				} else if(type.equals("java.sql.Date")){
					attribut[i].set(oo, resSet.getDate(nomAttribut));					
				} else if(type.equals("java.sql.Timestamp")) {
					attribut[i].set(oo, resSet.getTimestamp(nomAttribut));
				} else {
					attribut[i].set(oo, resSet.getString(nomAttribut));
				}
			}
			valiny = addObject(valiny, oo);
		}
		resSet.close();
		st.close();
		return valiny;		
	}

    public String getSequenceCurrVal(Connection con,String sequenceName) throws Exception {
        String sql = "SELECT " + sequenceName + ".currVal val FROM DUAL";
        Statement stat = con.createStatement();
        ResultSet res = stat.executeQuery(sql);
        res.next();

        String n = res.getString("val");
        
        res.close();
        stat.close();
 
        return n;
    }

    public String getSequenceNextVal(Connection con,String sequenceName) throws Exception {
        String sql = "SELECT " + sequenceName + ".nextVal val FROM DUAL";
        Statement stat = con.createStatement();
        ResultSet res = stat.executeQuery(sql);
        res.next();

        String n = res.getString("val");
        
        res.close();
        stat.close();
 
        return n;
    }

    public boolean checkTime(Timestamp dateDebut,Timestamp now,Timestamp dateFin)throws Exception{
        now = getNow(null);
        if(now.before(dateFin) == true && now.after(dateDebut)== true){
            System.out.println("hhhhhhhhhhhh");
            return true;
        }
        else{
            return false;
        }
    }

    public int diffEnJours(Timestamp t1, Timestamp t2) {
		long diff = Math.abs(t1.getTime()-t2.getTime());
		long d = 86400000l;
		// System.out.println("t1" + t1);
		// System.out.println("t1" + t2);
		// System.out.println("t1" + t1.getYear());
		// System.out.println("t1" + t2.getYear());
		// System.out.println("t1" + t1.getTime());
		// System.out.println("t2" + t2.getTime());
		int res = new Integer(""+diff/d).intValue();
		
		return res;
	}

    public int controllMonth(String mois)throws Exception{
        int moisFinal = 0;
        try{
            int moisCores = new Integer(mois).intValue();
            System.out.println("Exception 1 : moisCores = "+moisCores);
            if(moisCores > 0 && moisCores < 13){
                moisFinal = moisCores;
            }
        }
        catch(Exception e){
            try{
                System.out.println("Exception 2");

                String[] listeMois = getListeMois();
                System.out.println("listeMois.length : "+listeMois.length);

                for(int i = 0; i < listeMois.length; i++){

                    if(listeMois[i].compareToIgnoreCase(mois) == 0){
                        moisFinal = i+1;
                        System.out.println("Mois correspondant trouve");
                        break;
                    }
                }

                    if(moisFinal == 0){
                        throw new Exception("Mois inserer invalide");
                    }
            }
            catch(Exception ex){
                throw new Exception("Mois inserer invalide");
            }
                         
        }

        System.out.println("Mois Final : "+moisFinal);
        return moisFinal;
    }

    public int controllYear(String annee)throws Exception{
        int yearFinal = 0;
        try{
            int anneeCores = new Integer(annee).intValue();
            System.out.println("Exception : anneeCores = "+anneeCores);
            if(anneeCores > 2015 && anneeCores < 2020){
                yearFinal = anneeCores;
            }
        }
        catch(Exception e){
            throw new Exception("Annee inserer invalide");             
        }

        System.out.println("Annee Final : "+yearFinal);
        return yearFinal;
    }

    
	public String nb(int index) {
		String[] lettres = new String[3];
		lettres[0] = "mille";
		lettres[1] = "millions";
		lettres[2] = "milliards";

		return lettres[index];
	}

	public String nb1(int index) {
		String[] lettres = {"","un","deux","trois","quatre","cinq","six","sept","huit","neuf","dix","onze","douze","treize","quatorze","quinze","seize","dix sept","dix huit","dix neuf","vingt","vingt un","vingt deux","vingt trois","vingt quatre","vingt cinq","vingt six","vingt sept","vingt huit","vingt neuf","trente","trente un","trente deux","trente trois","trente quatre","trente cinq","trente six","trente sept","trente huit","trente neuf","quarante","quarante un","quarante deux","quarante trois","quarante quatre","quarante cinq","quarante six","quarante sept","quarante huit","quarante neuf","cinquante","cinquante un","cinquante deux","cinquante trois","cinquante quatre","cinquante cinq","cinquante six","cinquante sept","cinquante huit","cinquante neuf","soixante","soixante un","soixante deux","soixante trois","soixante quatre","soixante cinq","soixante six","soixante sept","soixante huit","soixante neuf","soixante dix","soixante onze","soixante douze","soixante treize","soixante quatorze","soixante quinze","soixante seize","soixante dix sept","soixante dix huit","soixante dix neuf","quatre vingt","quatre vingt un","quatre vingt deux","quatre vingt trois","quatre vingt quatre","quatre vingt cinq","quatre vingt six","quatre vingt sept","quatre vingt huit","quatre vingt neuf","quatre vingt dix","quatre vingt onze","quatre vingt douze","quatre vingt treize","quatre vingt quatorze","quatre vingt quinze","quatre vingt seize","quatre vingt dix sept","quatre vingt dix huit","quatre vingt dix neuf"};
		
		return lettres[index];
	}

	public String enLettre(int nb) {
		int d = nb;
		String res = "";
		int n = -1;
		System.out.println("nb" + nb);
		while(d > 0) {
			int r = d % 100;
			// System.out.println("r == " + r);
			// System.out.println("n ------> " + n);
			// System.out.println("d == " + d);
			// System.out.println("lettre r == " + nb1(r));

			if(n > -1 && d%1000 > 0) {
				// System.out.println("r" + r);
				res = nb(n) + " " + res;
			}
			// System.out.println(d/10);
			
			if((d%1000) > 1 || n != 0) {
				res = nb1(r) + " " + res;
			}
			
			d = d / 100;

			int r2 = d % 10;
			// System.out.println("r2 == " + r2);
			// System.out.println("lettre r2 == " + nb1(r2));
			if(r2 != 0) {
				res = "cent " + res;

				if(r2 != 1) {
					res = nb1(r2) + " " + res;
				}
			}

			// System.out.println("res avant mille " + res);
			
			d = d /10;

			if(n == 2) {
				n = -1;
			}

			n++;
		}

		return res;
	}





}