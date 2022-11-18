package com.muthadevs;
import java.util.concurrent.Semaphore;

//Final Working Code

class Queue {

    int item;//-------------------------------------------------------------Refers to the item in the queue
    static Semaphore semProducer = new Semaphore(1);//---------------Producer semaphore with permits initially (To ensure putItem() is executed first)
    static Semaphore semaphoreConsumer = new Semaphore(0);//---------Consumer semaphore with nor permits initially
    static String b;
    void getItem(int consumerNumber)
    {
        try {
            System.out.println("Waiting for the Consumer "+consumerNumber+" to acquire permission (for item : "+item+").......");
            semaphoreConsumer.acquire();//----------------------------------Acquiring the permit from semConsumer before consumer consume the item from the queue
            System.out.println("Permit Granted to consumer "+consumerNumber+" (for item : "+item+") ");
            b="";
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }  //---------------------------------------------------------------Surrounded with try catch block to handle the exceptions
        System.out.println("--------------------------------");
        System.out.println("Consumer "+consumerNumber+" consumed item : -> " + item);//--------Printing the statements
        System.out.println("Buffer :-");
        System.out.println("---------");
        System.out.println("  | "+b+" |  ");
        System.out.println("---------");
        System.out.println("--------------------------------");
        semProducer.release();//--------------------------------------------Releasing the hold and signaling PRODUCER
    }

    void putItem(int item,int producerNumber)
    {
        try {
            System.out.println("Waiting for the Producer "+producerNumber+" to acquire permission (for item : "+item+").......");
            semProducer.acquire();//---------------------------------------Acquiring the permit from semProducer before producer put the item in the queue
            System.out.println("Permit Granted to producer "+producerNumber+" (for item :"+item+")");
            b = String.valueOf(item);
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }//----------------------------------------------------------------Surrounded with try catch block to handle the exceptions
        this.item = item;//------------------------------------------------Assigning the produced item in the Buffer/Queue
        System.out.println("--------------------------------");
        System.out.println("Producer "+producerNumber+" produced item : -> " + item);
        System.out.println("Buffer :-");
        System.out.println("---------");
        System.out.println("  | "+b+" |  ");
        System.out.println("---------");
        System.out.println("--------------------------------");
        semaphoreConsumer.release();//------------------------------------Releasing the hold and signaling CONSUMER
    }
}

class Producer implements Runnable {//-------------------------------------Producer implementing runnable so it can execute on a thread
    Queue queue;
    int producerNumber;
    Producer(Queue queue,int producerNumber)
    {
        this.queue = queue;
        this.producerNumber = producerNumber;
        new Thread(this, "Producer").start();//----------------Creating a new thread and calling start method
    }
    public void run()
    {
        for (int i = 0; i < 5; i++)
            queue.putItem(i,producerNumber);//--------------------------------------------Putting the items in the QUEUE
    }
}

class Consumer implements Runnable {//-------------------------------------Consumer implementing runnable so it can execute on a thread
    Queue queue;
    int consumerNumber;
    Consumer(Queue queue,int consumerNumber)
    {
        this.queue = queue;
        this.consumerNumber = consumerNumber;
        new Thread(this, "Consumer").start();//----------------Creating a new thread and calling start method
    }
    public void run()
    {
        for (int i = 0; i < 5; i++)//-------------------------------------Consumer implementing runnable so it can execute on a thread
            queue.getItem(consumerNumber);//--------------------------------------------Removing / Consuming the items from the queue
    }
}

class Main {//------------------------------------------------------------Driver Class for the program
    public static void main(String[] args)
    {
        Queue queue = new Queue();//--------------------------------------Creating a Queue
        new Producer(queue,1);//--------------------------------------------Instantiating Producer
        new Consumer(queue,1);//--------------------------------------------Instantiating Consumer

        /*//2nd pair
        new Producer(queue,2);//--------------------------------------------Instantiating Producer
        new Consumer(queue,2);//--------------------------------------------Instantiating Consumer*/
    }
}



















//References : https://www.geeksforgeeks.org/producer-consumer-solution-using-semaphores-java/
