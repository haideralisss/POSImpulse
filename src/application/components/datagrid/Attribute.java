package application.components.datagrid;

public class Attribute 
{
    private String attribute, type, dbAttribute, dbType, fAttribute, fCollection;
    private boolean search, hide, input, foreign;

    public Attribute(String attribute, String type, String dbAttribute, String dbType, boolean search, boolean hide, boolean input) {
        this.attribute = attribute;
        this.type = type;
        this.dbAttribute = dbAttribute;
        this.dbType = dbType;
        this.search = search;
        this.hide = hide;
        this.input = input;
    }
    
    public Attribute(String attribute, String type, String dbAttribute, String dbType, boolean search, boolean hide, boolean input, boolean foreign, String fAttribute, String fCollection) {
		this.attribute = attribute;
		this.type = type;
		this.dbAttribute = dbAttribute;
		this.dbType = dbType;
		this.search = search;
		this.hide = hide;
		this.input = input;
		this.foreign = foreign;
		this.fAttribute = fAttribute;
		this.fCollection = fCollection;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getType() {
        return type;
    }

    public String getDbAttribute() {
        return dbAttribute;
    }

    public String getDbType() {
        return dbType;
    }

    public boolean isSearch() {
        return search;
    }

    public boolean isHide() {
        return hide;
    }

    public boolean isInput() {
        return input;
    }
    
    public boolean isForeign() {
        return foreign;
    }
    
    public String getForeignCollection() {
    	return fCollection;
    }
    
    public String getForeignAttribute() {
    	return fAttribute;
    }
    
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDbAttribute(String dbAttribute) {
        this.dbAttribute = dbAttribute;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public void setInput(boolean input) {
        this.input = input;
    }
}
