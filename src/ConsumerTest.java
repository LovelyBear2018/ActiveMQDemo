import javax.jms.TextMessage;

/**
 * Created by liuzhixiong on 2018/11/15.
 */
public class ConsumerTest {
    public static void main(String[] args){
        Consumer consumer = new Consumer();
        consumer.init();
        ConsumerTest testConsumer = new ConsumerTest();
        new Thread(testConsumer.new ConsumerMq(consumer)).start();
        new Thread(testConsumer.new ConsumerMq(consumer)).start();
        new Thread(testConsumer.new ConsumerMq(consumer)).start();
        new Thread(testConsumer.new ConsumerMq(consumer)).start();
    }

    private class ConsumerMq implements Runnable{
        Consumer comsumer;
        public ConsumerMq(Consumer comsumer){
            this.comsumer = comsumer;
        }

        @Override
        public void run() {
            while(true){
                try {
                    comsumer.getMessage("TestQueue");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
