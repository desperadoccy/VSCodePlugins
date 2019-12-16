//import java.io.BufferedInputStream;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTTest {
	public static String className = "";
	public static String projectName = "";


	public static void main(String[] args) {
		String path = "D:\\科创\\Code Recommendation\\ParseFiles\\JavaFiles";        //要遍历的路径
		File file = new File(path);        //获取其file对象
		File[] fs = file.listFiles();   //遍历path下的文件和目录，放在File数组中
		int cnt = 0;
		int cnt1=0;
		for (File filetmp : fs)    //遍历文件
		{
			writeFile.setName(filetmp.getName());
			List<String> list=readFile.read(filetmp.getPath());
			cnt1++;
			if(methodFind.isAbstract(list))
				continue;
			if (cnt == 200) break;	//测试数量
			cnt++;
			System.out.println("第"+cnt1+"测试\n\n");
			//Body测试
			Vector<List<String>> test2= methodFind.slice(list);      //分离方法
			for(List<String> i:test2)   //处理方法
			{
				//获取方法名称
				if (!i.isEmpty())
				{
					Pattern pattern = Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$]*[ ]*[(]");
					Matcher matcher = pattern.matcher(i.get(0));
					if(matcher.find())
					{
						String name = filetmp.getName().substring(0,filetmp.getName().length()-5)+"#"+matcher.group(0).substring(0, matcher.group().length() - 1);
						writeFile.writeBody(i,name);
					}
				}
			}
			String input=methodFind.deleteUseless(list);
			DemoVisitor visitor = new DemoVisitor();
			CompilationUnit comp = getCompilationUnit(input);
			comp.accept(visitor);
		}
	}

	public static CompilationUnit getCompilationUnit(String input) {
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(input.toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);

		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));

		return result;
	}
}
