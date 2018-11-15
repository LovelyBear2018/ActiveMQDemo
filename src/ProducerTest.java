public class ProducerTest {

    public static void main(String[] args){
        Producer producter = new Producer();
        producter.init();
        ProducerTest testProducer = new ProducerTest();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Thread 1
        new Thread(testProducer.new ProductorMq(producter)).start();
        //Thread 2
        new Thread(testProducer.new ProductorMq(producter)).start();
        //Thread 3
        new Thread(testProducer.new ProductorMq(producter)).start();
        //Thread 4
        new Thread(testProducer.new ProductorMq(producter)).start();
        //Thread 5
        new Thread(testProducer.new ProductorMq(producter)).start();
    }

    private class ProductorMq implements Runnable{
        Producer producter;
        public ProductorMq(Producer producter){
            this.producter = producter;
        }

        @Override
        public void run() {
            while(true){
                try {
                    producter.sendMessage("TestQueue");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
