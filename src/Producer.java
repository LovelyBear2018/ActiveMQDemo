import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ActiveMQ生产者
 * Created by liuzhixiong on 2018/11/15.
 */
public class Producer {
    //ActiveMq 的默认用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //ActiveMq 的默认登录密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //ActiveMQ 的链接地址
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    //链接工厂
    ActiveMQConnectionFactory connectionFactory;

    //链接对象
    Connection connection;

    //事务管理
    Session session;

    AtomicInteger count = new AtomicInteger(0);

    ThreadLocal<MessageProducer> producers = new ThreadLocal<MessageProducer>();

    public void init() {
        try {
            //创建一个链接工厂
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
            //从工厂中创建一个链接
            connection = connectionFactory.createConnection();
            //开启链接
            connection.start();
            //创建一个事务（这里通过参数可以设置事务的级别）
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String disname) {
        try {
            //创建一个消息队列
            Queue queue = session.createQueue(disname);
            //消息生产者
            MessageProducer messageProducer;
            if (producers.get() != null) {
                messageProducer = producers.get();
            } else {
                messageProducer = session.createProducer(queue);
                producers.set(messageProducer);
            }
            while (true) {
                Thread.sleep(1000);
                int num = count.getAndIncrement();
                //创建一条消息
                TextMessage msg = session.createTextMessage(Thread.currentThread().getName() +
                        "productor:我是生产者，我现在正在生产东西！,count:" + num);
                System.out.println(Thread.currentThread().getName() +
                        "productor:我是生产者，我现在正在生产东西！,count:" + num);
                //发送消息
                messageProducer.send(msg);
                //提交事务
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
