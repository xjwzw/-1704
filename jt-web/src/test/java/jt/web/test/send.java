package jt.web.test;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class send {
	private static final String QUEUE_NAME="test_queue_work";
	private final static String EXCHANGE_NAME = "jt_test_exchange_fanout";
	public static void main(String[] args) throws IOException, InterruptedException {
		Connection connection=ConnectionUtil.getConnection();
		Channel channel=connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		for(int i=0;i<100;i++){
			String message="hello world"+i;
			
			channel.basicPublish(EXCHANGE_NAME,"",null, message.getBytes());
			System.out.println("sent"+message);
		}
		
		channel.close();
		connection.close();
	}
}
