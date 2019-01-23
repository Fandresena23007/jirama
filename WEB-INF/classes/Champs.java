package form;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import general.*;

public class Champs {
    boolean visible;
    boolean isDisabled;
    String valeurParDef;
    String label;
    String name;
    Class classe;
    String html;

    public Champs(Field f) {
        setClass(f);
        setIsDisabled(false);
        setVisible(true);
        setValeurParDef("");
        setLabel(f.getName());
        setName(f.getName());
    }

    public String checkVisibility(boolean visible) {
        String res = "";

        if(!visible) {
            res = "hidden";
        }

        return res;
    }

    public String checkIfDisabled(boolean d) {
        String res = "";

        if(getIsDisabled()) {
            res = "disabled";
        }

        return res;
    }

    public String getHtml() {
        if(this.html == null) {
            createChamps();
        }

        return this.html;
    }

    public void createChamps() {
        String res = "<label for='" + name + "'"+ checkVisibility(visible) +" class='col-md-4 col-md-offset-3 col-form-label'>" + label + "</label>";
        res += "<div class='col-md-4'><input type='text' value=\"" + valeurParDef + "\" name='" + name + "'" + checkVisibility(visible) + checkIfDisabled(getIsDisabled()) +"/></div>";

        this.html = res;
    }

    public String checkIfSelected(String value) {
        String res = "";

        if(value.compareTo(getValeurParDef()) == 0) {
            System.out.println("valeur " + value);
            res = "selected";
        }

        System.out.println("res sel " + res);
        System.out.println("valeur par def sel " + getValeurParDef());

        return res;
    }

    public void createListe(Connection conex, String tableN) throws Exception {
        String sql = "SELECT "+ name +" FROM " + tableN + " GROUP BY " + name;
        System.out.print(sql);
		Statement stat = conex.createStatement();
        ResultSet res = stat.executeQuery(sql);
        String html = "<label for='" + name + "'"+ checkVisibility(visible) +" class='col-md-4 col-md-offset-3 col-form-label'>" + label + "</label>";
        html += "<select "+ checkVisibility(visible) +" name="+ getName() + ">";

        while(res.next()) {
            html += "<option value='" + res.getString(name) +"'>" + res.getString(name) + "</option>";
        }
		
		res.close();
		stat.close();
		
        html += "</select>";

        this.html = html;
    }

    public void setVisible(boolean v) {
        this.visible = v;
    }

    public void setIsDisabled(boolean v) {
        this.isDisabled = v;
    }

    public void setClass(Field f) {
        this.classe = f.getDeclaringClass();
    }

    public boolean getVisible() {
        return this.visible;
    }

    public boolean getIsDisabled() {
        return this.isDisabled;
    }

    public void setValeurParDef(String valeur) {
        this.valeurParDef = valeur;
    }


    public String getValeurParDef() {
        return this.valeurParDef;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}