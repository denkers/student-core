//====================================
//  KYLE RUSSELL
//  13831056
//  PDC Project
//====================================

package engine.models;

import com.google.gson.JsonArray;
import engine.core.ExceptionOutput;
import java.sql.SQLException;

public class AssessmentModel extends Model
{
    public AssessmentModel()
    {
        super();
    }
    
    public AssessmentModel(Object id)
    {
        super(id);
    }
    
    public static JsonArray getAssessmentsForClass(int classId)
    {
        try
        {
            JsonArray results = new AssessmentModel().builder().where("class_id", "=", "" + classId)
                    .select("id", "Assessment ID")
                    .select("name")
                    .select("description")
                    .select("weight", "Grade weight %")
                    .select("due_date", "Due date")
                    .get();
            return results;
        }
        
        catch(SQLException e)
        {
            ExceptionOutput.output("[SQL Exception] " + e.getMessage(), ExceptionOutput.OutputType.DEBUG);
            return new JsonArray();
        }
    }

    @Override
    protected void initTable() 
    {
        table       =   "assessment";
        primaryKey  =   "id";
    }
    
}
