package me.gacl.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WebSocketTest {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();

	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		webSocketSet.add(this);     //加入set中
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){
		webSocketSet.remove(this);  //从set中删除
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		try {
			EScreate tmp = new EScreate();
			try{
				tmp.init();
			} catch (Exception a) {

			}
			String ans = new String();
			try{
				System.out.println("message is"+message);
				ans = tmp.QueryString(message);
				System.out.println(ans);
				Pattern p = Pattern.compile("\\\\\\\\n");
				Matcher m = p.matcher(ans);
				ans = m.replaceAll("\\\\n");
				System.out.println("test2\n"+ans);
				Pattern p2 = Pattern.compile("\\\\\\\\t");
				Matcher m2 = p2.matcher(ans);
				ans = m2.replaceAll("\\\\t");
				System.out.println("test2\n"+ans);
				if(ans.charAt(ans.length()-2)==','){
					StringBuilder strBuilder = new StringBuilder(ans);
					strBuilder.setCharAt(ans.length()-2, ']');
					strBuilder.setCharAt(ans.length()-1, ' ');
					ans=strBuilder.toString();
				}
			} catch (Exception e) {

			}
			session.getBasicRemote().sendText(ans);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
		//this.session.getAsyncRemote().sendText(message);
	}


}
