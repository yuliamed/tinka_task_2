package com.example.tinka_task_2;

import java.time.Duration;
import java.util.List;

public class HandlerImpl implements Handler{

    private final Client client;

    public HandlerImpl(Client client) {
        this.client = client;
    }

    @Override
    public Duration timeout() {
        return Duration.ofMillis(100);
    }

    @Override
    public void performOperation() {
        while (true) {
            Event event = client.readData();
            if (event == null) {
                continue;
            }

            Payload payload = event.payload();
            List<Address> recipients = event.recipients();

            for (Address recipient : recipients) {
                Result result = client.sendData(recipient, payload);

                switch (result) {
                    case ACCEPTED:
                        break;
                    case REJECTED:
                        try {
                            Thread.sleep(timeout().toMillis());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        break;
                }
            }
        }
    }
}
