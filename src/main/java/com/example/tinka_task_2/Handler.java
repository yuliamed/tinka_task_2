package com.example.tinka_task_2;

import java.time.Duration;

public interface Handler {
    Duration timeout();

    void performOperation();
}
