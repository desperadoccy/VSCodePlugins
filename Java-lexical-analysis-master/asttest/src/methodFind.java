import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @Author: desperado
 * @Description:
 * @Date: Created in 20:09 2019/5/10
 * @Modified By:
 */
public class methodFind {
    public static Vector<List<String>> slice(List<String> test)
    {
        List<String> list=new ArrayList<String>();
        Vector<List<String>> ans=new Vector<List<String>>();
        boolean flag=true;
        int flagcnt=0;
        for (String i:test)
        {
            if(flagcnt==0)
            {
                flag=true;
            }
            if (i.contains("(")&&i.contains(")")&&flag==true)
            {
                flag=false;
            }
            if(flag==false&&i.contains("{"))
            {
                flagcnt++;
            }
            if (flag==false)
            {
                list.add(i);
            }
            if (flag==false&&i.contains("}"))
            {
                flagcnt--;
            }
            if(flagcnt==0&&flag==false)
            {
                if (!list.isEmpty()) {
                    List<String> list2 = new ArrayList<String>();
                    for (int len = 0; len < list.size(); len++) {
                        list2.add(list.get(len));     //开始复制一个list的内容到另外一个list
                    }
                    flag = true;
                    ans.add(list2);
                    list.clear();

                }
            }
        }
        return ans;
    }
    public static String deleteUseless(List<String> test)
    {
        String input=new String();
//        boolean flag=true;//检测方法开始
        for (String i:test)
        {
//            boolean flag1=false;//检测方法是否仅为声明
//            if (i.contains("(")&&i.contains(")")&&flag==true)
//            {
//                flag=false;
//            }
//            if(flag==false&&!i.contains("{")&&!i.contains("}")&&i.contains(";"))
//            {
//                if(i.contains("public")||i.contains("protected")||i.contains("private")||i.contains("default"))
//                {
//                    if(!i.contains("*")&&!i.contains("//"))
//                    {
//                        flag=true;
//                        flag1=true;
//                    }
//                }
//            }
//            if(flag=false&&i.contains("abstract"))
//            {
//                flag1=true;
//            }
//            if(i.contains("}"))
//            {
//                flag = true;
//            }
//            if(!flag1)
//            {
                input+=i+"\n";
//            }
        }
        return input;

    }

    public static boolean isAbstract(List<String> test)
    {
        for(String i:test)
        {
            if(i.contains("class")&&i.contains("abstract"))
            {
                return true;
            }
        }
        return false;
    }
}
