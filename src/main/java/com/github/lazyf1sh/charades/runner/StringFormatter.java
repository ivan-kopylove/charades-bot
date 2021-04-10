package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.telegram.api.User;
import com.github.lazyf1sh.charades.domain.telegram.api.Message;

public class StringFormatter
{
    public static String gotNewMessage(Message message)
    {
        User user = message.getUser();
        String username = user.getUsername();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String text = message.getText();
        return String.format("New message from @%s %s %s. Text: %s", username, firstName, lastName, text);
    }
}
