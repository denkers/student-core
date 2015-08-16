
package engine.Views.cui.Utilities;

import java.text.MessageFormat;
import java.util.List;
import org.apache.commons.lang3.StringUtils;


public class CUITextTools 
{
    public final static int WHITE   = 37;
    public final static int CYAN    = 36;
    public final static int RED     = 31;
    public final static int GREEN   = 32;
    public final static int BLUE    = 34;
    public final static int YELLOW  = 33;
    
    
    public static String drawButton(String buttonText)
    {
        String button;
        final int horPadding      =   5;
        final int horizontalLen   =   buttonText.length() + horPadding * 2;
        String horizontalFrame    =   new String(new char[horizontalLen]).replace("\0", "-");
        String spaces             =   new String(new char[(horizontalLen / 2) - (buttonText.length()/2) - 1]).replace("\0", " ");
        
        button = horizontalFrame + "\n|" + spaces + buttonText + spaces + "|\n" + horizontalFrame;
        System.out.println(button);
        return button;
    }
    
    public static String drawLargeHeader(String headerText, String headerDescription, int borderColour)
    {
        String header;
        final int maxLength     =   25;
        final int padding       =   10;
        final int horizontalLen =   Math.max(headerDescription.length(), maxLength) + (padding * 2);
        
        String horizontalFrame       =   new String(new char[horizontalLen]).replace("\0", "* ");
        String spacesOuter           =   new String(new char[horizontalFrame.length() - 3]).replace("\0", " ");
        int lengthBeforeFormat       =   horizontalFrame.length();
        horizontalFrame              =   horizontalFrame.replace("* ", changeColour("* ", borderColour));

        header  =   horizontalFrame + "\n" + changeColour("*", borderColour) + spacesOuter  + changeColour("*", borderColour) + "\n";
        header  +=  changeColour("*", borderColour) + StringUtils.center(headerText, lengthBeforeFormat - 3) + changeColour("*", borderColour) +"\n";
        header  +=  changeColour("*", borderColour) + StringUtils.center(headerDescription, lengthBeforeFormat - 3) + changeColour("*", borderColour) + "\n";
        header  +=  changeColour("*", borderColour) + spacesOuter  + changeColour("*", borderColour) + "\n" + horizontalFrame;
       
        return header;
    }
    
    public static String underline(String text)
    {
        String underlinedText;
        final int length    =   text.length();
        String underline    =   new String(new char[length]).replace("\0", "-");
        underlinedText      =   text + "\n" + underline;
        
        return underlinedText;
    }
    
    public static String keyText(String text, String key)
    {
        String keyText  =   MessageFormat.format("<{0}> {1}", key, text);
        return keyText;
    }
    
    public static String keyTextBrackets(String text, String key)
    {
        String keyText  =   MessageFormat.format("[{0}] {1}", key, text);
        return keyText;
    }
    
    public static String changeColour(String text, int colour)
    {
        return ((char)27 + "[" + colour + "m"  + text + (char)27 + "[0m");
    }
    
    public static String drawTable(List<String> columnNames, int width)
    {
        return "";
    }
    
    public static void main(String[] args)
    {   
        String s = drawLargeHeader("Helasdasdlo", "this ", CUITextTools.GREEN);
        System.out.println(s);
      //  System.out.println(changeColour("Hello", CUITextTools.RED));
    }
}