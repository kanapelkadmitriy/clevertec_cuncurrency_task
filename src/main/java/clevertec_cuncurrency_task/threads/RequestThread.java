package clevertec_cuncurrency_task.threads;

import clevertec_cuncurrency_task.client.Client;
import clevertec_cuncurrency_task.dto.Request;
import clevertec_cuncurrency_task.server.Server;
import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class RequestThread implements Callable<Request> {

    private Client client;
    private Server server;

    @Override
    public Request call() throws Exception {
        return client.sendRequest();
    }
}
