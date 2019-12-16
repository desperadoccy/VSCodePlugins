package me.gacl.websocket;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.json.JSONObject;
import org.json.JSONArray;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EScreate {
    private TransportClient client;
    @Before
    public void init() throws Exception{
        Settings settings=Settings.builder()
                .put("cluster.name","elasticsearch")
                .build();
        client=new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
    }

    @Test
    //用于创建index
    public void ESclient() throws Exception{
        //设定index的名字
        client.admin().indices().prepareCreate("code_recommend").get();
        client.close();
    }

    private  String search(QueryBuilder queryBuilder) throws  Exception{
        //执行查询
        SearchResponse searchResponse =  client.prepareSearch("code_recommend")                                        //  设置查询的index
               // .setTypes("C")
                //  设置查询的type
                .setQuery(queryBuilder)
                .get();
        //取查询结果
        SearchHits searchHits = searchResponse.getHits();
        // 总记录数
        System.out.println("查询结果总记录数：" + searchHits.getTotalHits());
        long num = searchHits.getTotalHits();
        long check_num = 0;
        //查询结果列表
        Iterator<SearchHit> iterator = searchHits.iterator();
        String s;
        s = "[";
        while(iterator.hasNext()){
            SearchHit searchHit = iterator.next();
            //打印文档对象，以json格式输出

            if (check_num<num-1) {
                s = s + searchHit.getSourceAsString() + ",";
            }

            else
            {s = s + searchHit.getSourceAsString();}
            check_num ++;
//            searchHit.getSourceAsString();

            //取文档属性
//            System.out.println("-----------文档的属性");
//            Map<String ,Object> document  = searchHit.getSource();
//            System.out.println(document.get("whole_method_body"));                                                         // 设置return内容

            //实现生成一个json

        }
        s = s +"]";
        System.out.println(s);
        //关闭client
        client.close();
        return s;
    }

    String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/~?！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
    public String replaceBlank(String s) {
        String result = null;
        if (s == null) {
            return result;
        } else {
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(s);
            result = m.replaceAll(" ");
            p = Pattern.compile("[ ]+");
            m = p.matcher(result);
            result = m.replaceAll(" ");
            return result;
        }
    }
//    @Test
//    public void QueryByTerm() throws Exception{
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("return_value","void");
//        search(queryBuilder);
//    }
    public String QueryString(String user_string) throws Exception{
//        user_string="int h=x,r=y;";
        user_string=replaceBlank(user_string);
        System.out.println(user_string);
        QueryBuilder queryBuilder =QueryBuilders.queryStringQuery(user_string).defaultField("method_body");
        return search(queryBuilder);
    }
//    @Test
//    public void QueryString() throws Exception{
//        String user_string="sort";
//        user_string=replaceBlank(user_string);
//        System.out.println(user_string);
//        QueryBuilder queryBuilder =QueryBuilders.queryStringQuery(user_string).defaultField("method_body");
//        search(queryBuilder);
//    }

}
