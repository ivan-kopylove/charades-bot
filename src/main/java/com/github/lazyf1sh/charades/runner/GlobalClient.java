package com.github.lazyf1sh.charades.runner;

import jakarta.ws.rs.client.ClientBuilder;

public class GlobalClient
{
    public static final jakarta.ws.rs.client.Client CLIENT = ClientBuilder.newClient();

    private GlobalClient() {}
}
