package jt.web.test;

import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	public static Connection getConnection() throws IOException{
		ConnectionFactory factory=new ConnectionFactory();
		 //设置服务地址
        factory.setHost("192.168.247.30");
        //端口，HTTP管理端口默认15672，访问端口默认5672
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/jt");		//注意这个值，vh配置如果加了斜杠，这里也必须加
        factory.setUsername("sysdebug");
        factory.setPassword("123456");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;

	}
}
