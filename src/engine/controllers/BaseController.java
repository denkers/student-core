
package engine.controllers;

import engine.core.Path;
import engine.views.View;

public class BaseController extends Controller 
{
    public BaseController(Path path)
    {
        super(path);
    }
    
    public BaseController(ControllerMessage postData, Path path)
    {
        super(postData, path);
    }
    
    public BaseController(ControllerMessage postData, RequestType requestType, Path path)
    {
        super(postData, requestType, path);
    }
    
    
    public View getErrorView()
    {
        return prepareView(new engine.views.gui.layout.ViewNotFound());
    }
}