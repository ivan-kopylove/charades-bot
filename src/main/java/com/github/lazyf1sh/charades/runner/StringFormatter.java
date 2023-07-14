package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.telegram.api.Message;
import com.github.lazyf1sh.charades.domain.telegram.api.User;

public class StringFormatter
{
    private StringFormatter() {}

    public static String gotNewMessage(final Message message)
    {
        final User user = message.getUser();
        final String username = user.getUsername();
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final String text = message.getText();
        return String.format("New message from @%s %s %s. Text: %s", username, firstName, lastName, text);
    }
}
