package clevertec_cuncurrency_task;

import clevertec_cuncurrency_task.client.Client;
import clevertec_cuncurrency_task.server.Server;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationRunnerTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void applicationStartTest(int capacity, int accumulator) {
        final Client client = new Client(capacity);
        final Server server = new Server();
        final ApplicationRunner runner = new ApplicationRunner(client, server);
        final CopyOnWriteArrayList<Integer> clientDataBefore = new CopyOnWriteArrayList<>(client.getClientData());

        runner.start();

        assertEquals(capacity, server.getServerData().size());
        assertEquals(accumulator, client.getAccumulator().get());
        assertEquals(clientDataBefore.size(), server.getServerData().stream().distinct().toList().size());
        assertTrue(client.getClientData().isEmpty());
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(8, 36),
                Arguments.of(10, 55),
                Arguments.of(50, 1275),
                Arguments.of(100, 5050)
        );
    }
}
