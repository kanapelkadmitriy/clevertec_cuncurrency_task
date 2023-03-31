package clevertec_cuncurrency_task;

import clevertec_cuncurrency_task.client.Client;
import clevertec_cuncurrency_task.dto.Request;
import clevertec_cuncurrency_task.server.Server;
import clevertec_cuncurrency_task.threads.RequestThread;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationRunner {

    private Client client;
    private Server server;

    @SneakyThrows
    public void start() {
        final ExecutorService executorService = Executors.newFixedThreadPool(client.getCapacity());
        final List<RequestThread> threads = new ArrayList<>(client.getCapacity());

        for (int i = 0; i < client.getCapacity(); i++) {
            threads.add(new RequestThread(client, server));
        }

        final List<Future<Request>> futures = executorService.invokeAll(threads);
        futures.forEach(future -> {
                    try {
                        server.getRequest(client, future.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        executorService.shutdown();
    }
}
