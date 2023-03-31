package clevertec_cuncurrency_task;

import clevertec_cuncurrency_task.client.Client;
import clevertec_cuncurrency_task.server.Server;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        final int capacity = 10;
        final Client client = new Client(capacity);
        final Server server = new Server();
        final ApplicationRunner runner = new ApplicationRunner(client, server);
        runner.start();
    }
}
