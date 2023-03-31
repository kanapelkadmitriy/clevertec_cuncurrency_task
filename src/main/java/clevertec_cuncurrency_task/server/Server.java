package clevertec_cuncurrency_task.server;

import clevertec_cuncurrency_task.client.Client;
import clevertec_cuncurrency_task.dto.Request;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class Server {

    private static final int MAX_SLEEPING_TIME = 1000;
    private static final int MIN_SLEEPING_TIME = 100;

    private Random random;
    private ReentrantLock lock;
    private CopyOnWriteArrayList<Integer> serverData;


    public Server() {
        this.random = new Random();
        this.lock = new ReentrantLock();
        this.serverData = new CopyOnWriteArrayList<>();
    }

    public void getRequest(Client client, Request request) {
        lock.lock();
        try {
            Thread.sleep(random.nextInt(MAX_SLEEPING_TIME - MIN_SLEEPING_TIME + 1) + MIN_SLEEPING_TIME);
            serverData.add(request.getValue());
            client.getResponse();
        } catch (InterruptedException e) {
            throw new RuntimeException("exception occurred during sending request to client");
        } finally {
            lock.unlock();
        }
    }


}
