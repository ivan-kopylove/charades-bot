package com.github.kopylove.charades.runner;

import com.github.lazyf1sh.telegram.api.domain.Message;
import com.github.lazyf1sh.telegram.api.domain.User;

public final class StringFormatter
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
