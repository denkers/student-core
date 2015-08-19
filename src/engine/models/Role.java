
package engine.models;


public class Role extends Model
{
    public Role()
    {
        super();
    }
    
    public Role(String id)
    {
        super(id);
    }

    @Override
    protected void initTable() 
    {
        table       =   "role";
        primaryKey  =   "id";   
    }
}
