package jt.web.test;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Reciever1 {
	private static final String QUEUE_NAME="test_queue_work";
	private final static String EXCHANGE_NAME = "jt_test_exchange_fanout";
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Connection connection=ConnectionUtil.getConnection();
		Channel channel=connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		  // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

		QueueingConsumer consumer=new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, false,consumer);
		while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            //休眠
            Thread.sleep(10);
            // 返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
		
	}
}
