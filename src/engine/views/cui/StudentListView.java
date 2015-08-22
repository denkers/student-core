
package engine.views.cui;

import com.bethecoder.ascii_table.ASCIITable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import engine.controllers.ControllerMessage;
import engine.core.Agent;
import engine.core.RouteHandler;
import engine.models.User;
import engine.views.AbstractView;
import engine.views.cui.Utilities.CUITextTools;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StudentListView extends AbstractView
{
    private int currentPage;
    private int numPages;
    private static final int NUM_RESULTS = 5;
    
    public StudentListView()
    {
        this(new ControllerMessage());
    }
    
    public StudentListView(ControllerMessage messages)
    {
        super
        (
                messages, 
                "Students", 
                "Find, remove, modify students in the school", 
                "/" + Agent.getActiveSession().getUser().get("USERNAME").getNonLiteralValue() + "/students/"
        );
        
        currentPage =   1;
        numPages    =   new User().builder().getNumPages(NUM_RESULTS);
    }
    
    @Override
    public void display()
    {
        super.display();
        showList(1, null);
       
    }
    
    public void showList(int page, ResponseDataView results)
    {
        String pageListTitle    =   MessageFormat.format("Student list - page {0}/{1}", page, numPages);
        System.out.println("\n" + CUITextTools.changeColour(CUITextTools.underline(pageListTitle), CUITextTools.MAGENTA) + "\n");
        
        if(results == null)
            results = (ResponseDataView) RouteHandler.go("getStudentList", new Object[] { page, NUM_RESULTS }, new Class<?>[] { Integer.class, Integer.class }, null);
        
        if(results.getResponseStatus())
        {
            
            JsonArray response  =   results.getResponseData().getData();
            if(response == null) return;
            
            CUITextTools.responseToTable(response);
        }
        
        else System.out.println(results.getResponseMessage());
    }
    
    public void deleteStudent()
    {
        List<String> fieldTitles    =   new ArrayList<>();
        fieldTitles.add(CUITextTools.createFormField("Delete username", "The username of the user you wish to delete"));
        
        List<String> fieldKeys      =   new ArrayList<>();
        fieldKeys.add("removeUsername");
        
        String[] headers    =   { "Delete username" };
        Map<String, String> inputData   =   CUITextTools.getFormInput(fieldTitles, fieldKeys, headers);
        
        ControllerMessage postData      =   new ControllerMessage();
        postData.addAll(inputData);
        
        ResponseDataView response   =   (ResponseDataView) RouteHandler.go("postRemoveStudent", postData);
        
        System.out.println(response.getResponseMessage());
        
    }
    
    public void modifyStudent()
    {
        List<String> fieldTitles    =   new ArrayList<>();
        fieldTitles.add(CUITextTools.createFormField("Modify username", "The username of the user you wish to modify"));
        fieldTitles.add(CUITextTools.createFormField("Modify attribute", "What is the attribute you want to modify?"));
        fieldTitles.add(CUITextTools.createFormField("New value", "The new attribute value to change"));
        
        List<String> fieldKeys      =   new ArrayList<>();
        fieldKeys.add("modifyUsername");
        fieldKeys.add("modifyAttribute");
        fieldKeys.add("modifyValue");
        
        
        String[] headers    =   { "Modify Username", "Attribute name", "New value" };
        Map<String, String> inputData   =   CUITextTools.getFormInput(fieldTitles, fieldKeys, headers);
        
        ControllerMessage postData      =   new ControllerMessage();
        postData.addAll(inputData);
        
        ResponseDataView response   =   (ResponseDataView) RouteHandler.go("postModifyStudent", postData);
        
        if(response != null)
            System.out.println(response.getResponseMessage());
    }
    
    public void findStudent()
    {
       List<String> fieldTitles    =   new ArrayList<>();
        fieldTitles.add(CUITextTools.createFormField("Search attribute", "What is the attribute you want to search for?"));
        fieldTitles.add(CUITextTools.createFormField("Search operator", "What is the operator condition?"));
        fieldTitles.add(CUITextTools.createFormField("Search value", "The attributes value you are searching for"));
        
        List<String> fieldKeys      =   new ArrayList<>();
        fieldKeys.add("searchAttribute");
        fieldKeys.add("searchOperator");
        fieldKeys.add("searchValue");
        
        
        String[] headers    =   { "Search attribute", "Search value", "Search operator" };
        Map<String, String> inputData   =   CUITextTools.getFormInput(fieldTitles, fieldKeys, headers); 
        
        ControllerMessage postData      =   new ControllerMessage();
        postData.addAll(inputData);
             
        ResponseDataView response   =   (ResponseDataView) RouteHandler.go("postSearchStudent", postData);
        System.out.println(response.getResponseMessage());
        
        JsonArray responseData  =   response.getResponseData().getData();
        CUITextTools.responseToTable(responseData);
    }
    
    public void nextPage()
    {
        if(currentPage < numPages)
            showList(currentPage++, null);
        else
            System.out.println(CUITextTools.changeColour("You are already on the last page", CUITextTools.RED));
                    
    }
    
    public void prevPage()
    {
        if(currentPage > 1)
            showList(currentPage--, null);
        else
            System.out.println(CUITextTools.changeColour("You are already on the first page", CUITextTools.RED));
    }

    @Override
    protected String getCommandsFile() 
    {
        return "/engine/config/listeners/StudentListListener.json";
    }
    
    public static void main(String[] args)
    {
      //  ResponseDataView results = (ResponseDataView) RouteHandler.go("getStudentList", new Object[] { 1 }, new Class<?>[] { Integer.class }, null);
        StudentListView view    =   new StudentListView();
        //view.display();
        //view.findStudent();
      view.deleteStudent();
    }
    
}