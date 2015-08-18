
package engine.core;

import engine.Views.cui.Utilities.CUITextTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class CommandInterpreter implements CommandExecute
{
    protected Map<String, Command>commands;
    
    public CommandInterpreter()
    {
        initCommands();
    }
    
    @Override
    public void fire(String commandRaw, Object instance)
    {
        List<String> paramList  =   new ArrayList<>(Arrays.asList(commandRaw.split("\\s")));
        String userCommand      =   paramList.get(0);
        Command command         =   commands.get(userCommand);
        
        if(command == null)
            unrecognizedCommand();
        else
        {
            paramList.remove(0);
            String[] params =   paramList.toArray(new String[paramList.size()]);
            command.call(params, instance);
        }
    }
    
    public void unrecognizedCommand()
    {
        int errorColour =   CUITextTools.RED;
        String message  =   "Command was not recognized!";
        message         =   CUITextTools.changeColour(message, errorColour);
        
        System.out.println(message);
    }
    
    public String showCommands()
    {
        Iterator<Command> commIter  =   commands.values().iterator();
        int colCount                =   0;
        final int MAX_COLS          =   3;
        String commandText          =   "\n";
        while(commIter.hasNext())
        {
            Command command         =   commIter.next();
            String commandName      =   CUITextTools.changeColour(command.getCommandName(), CUITextTools.MAGENTA);
            String commandDesc      =   CUITextTools.changeColour(command.getCommandDescription(), CUITextTools.PLAIN);
            String commandDisp      =   CUITextTools.keyText(commandName, commandDesc);
            
            commandText += commandDisp + "\t";
            colCount++;
            
            if(colCount == MAX_COLS)
            {
                commandText += "\n";
                colCount = 0;
            }
        }
        return commandText;
    }
    
    protected abstract String getCommandsFile();
    
    
    protected void initCommands()
    {
        String listenerFile     =   getCommandsFile();
        commands                =   CommandListener.loadFactory(listenerFile).getCommands();
    }
}