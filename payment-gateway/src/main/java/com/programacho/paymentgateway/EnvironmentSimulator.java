package com.programacho.paymentgateway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnvironmentSimulator {

    public static String node() {
        List<String> list = new ArrayList<>(List.of(
                "node-1",
                "node-2",
                "node-3",
                "node-4",
                "node-5"
        ));

        Collections.shuffle(list);

        return list.get(0);
    }
}
