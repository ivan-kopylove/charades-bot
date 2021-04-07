package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.telegram.api.From;
import com.github.lazyf1sh.charades.domain.telegram.api.Message;

public class StringFormatter
{
    public static String gotNewMessage(Message message)
    {
        From from = message.getFrom();
        String username = from.getUsername();
        String firstName = from.getFirst_name();
        String lastName = from.getLast_name();
        String text = message.getText();
        return String.format("New message from @%s %s %s. Text: %s", username, firstName, lastName, text);
    }
}
