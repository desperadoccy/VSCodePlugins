import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: desperado
 * @Description:
 * @Date: Created in 17:54 2019/5/10
 * @Modified By:
 */

public class readFile {
    public static List<String> read(String filePath) {
        List<String> list=new ArrayList<String>();
        try {
            File file=new File(filePath);
            if(file.isFile()&&file.exists())
            {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }
}

