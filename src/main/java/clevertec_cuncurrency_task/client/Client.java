package clevertec_cuncurrency_task.client;

import clevertec_cuncurrency_task.dto.Request;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class Client {
    private int capacity;
    private AtomicInteger counter;
    private AtomicInteger accumulator;
    private ReentrantLock lock;
    private Random random;
    private CopyOnWriteArrayList<Integer> clientData;


    public Client(int capacity) {
        this.capacity = capacity;
        this.counter = new AtomicInteger(0);
        this.accumulator = new AtomicInteger(0);
        this.lock = new ReentrantLock();
        this.random = new Random();
        this.clientData = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            this.clientData.add(i);
        }
    }

    public Request sendRequest() {
        lock.lock();
        try {
            final int idx = random.nextInt(clientData.size());
            final int value = clientData.remove(idx);
            return Request.builder()
                    .value(value)
                    .build();
        } finally {
            lock.unlock();
        }

    }

    public void getResponse() {
        lock.lock();
        try {
            counter.getAndIncrement();
            int counterValue = counter.get();
            accumulator.set((counterValue + 1) * (counterValue / 2));
        } finally {
            lock.unlock();
        }
    }
}
