import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.*;

/**
 * @Author: desperado
 * @Description:
 * @Date: Created in 0:08 2019/5/11
 * @Modified By:
 */
public class writeFile {
    public static void writeBody(List<String> method,String name)
    {
        String data="";
        for (String i:method)
        {
            data += i;
            data += "\n";
        }
        try
        {

            File file =new File("D:\\科创\\Code Recommendation\\test\\methodBody\\"+name + "@methodBody.txt");
            Writer outFile =new FileWriter(file,true);
            outFile.write(data);
            outFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeReturnValue(Type data,String name)
    {
        try
        {
            File file =new File("D:\\科创\\Code Recommendation\\test\\returnValue\\"+name + "@returnBody.txt");
            Writer outFile =new FileWriter(file,true);
            outFile.write(data.toString());
            outFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public  static void  writeParameterList(List method,String name)
    {
       String test= method.toString();
       test=test.substring(1,test.length()-1);
       String data="";
       String datatype="";
       String dataname="";
       String tmp[]=test.split("[ ]");
       for(int i=0;i<tmp.length;i++)
       {
           if(i%2==0)
           {
               data += tmp[i] + " ";
               datatype += tmp[i]+" ";
           }
           else
           {
               for (int j = 0; j < tmp[i].length(); j++)
               {
                   char ch=tmp[i].charAt(j);
                    if (ch <= 'Z' && ch >= 'A') {
                        data += " " + (char)(ch+'a'-'A');
                        dataname += " " + (char)(ch+'a'-'A');
                    } else {
                        data += ch;
                        dataname += ch;
                    }
                }
           }
       }
       try
        {
            File file =new File("D:\\科创\\Code Recommendation\\test\\parameterList\\"+name + "@parameterList.txt");
            Writer outFile =new FileWriter(file,true);
            File filetype =new File("D:\\科创\\Code Recommendation\\test\\variableType\\"+name + "@variableType.txt");
            Writer outFiletype =new FileWriter(filetype,true);
            File filename =new File("D:\\科创\\Code Recommendation\\test\\variableName\\"+name + "@variableName.txt");
            Writer outFilename =new FileWriter(filename,true);
            outFile.write(data);
            outFiletype.write(datatype);
            outFilename.write(dataname);
            outFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeVariableName(String data,String name)
    {


        try
        {
            File file =new File("D:\\科创\\Code Recommendation\\test\\variableName\\"+name + "@variableName.txt");
            Writer outFile =new FileWriter(file,true);
            outFile.write(data);
            outFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writevariableType(String data,String name)
    {
        try
        {
            File file =new File("D:\\科创\\Code Recommendation\\test\\variableType\\"+name + "@variableType.txt");
            Writer outFile =new FileWriter(file,true);
            outFile.write(data);
            outFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeAPIslnvocation(String method,String name)
    {
        String data="";
        method=method.replaceAll("[.]"," ");
        method=method.replaceAll("[(][^)]*[)]","");
        for(int i=0;i<method.length();i++)
        {
            char ch=method.charAt(i);
            if (ch <= 'Z' && ch >= 'A')
            {
                data += " " + (char)(ch+'a'-'A');
            }
            else data += ch;
        }
        try
        {
            File file =new File("D:\\科创\\Code Recommendation\\test\\APIslnvocation\\"+name + "@APIslnvocation.txt");
            Writer outFile =new FileWriter(file,true);
            outFile.write(data);
            outFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String filename="";
    public static void setName(String name)
    {
        filename=name;
    }

}
