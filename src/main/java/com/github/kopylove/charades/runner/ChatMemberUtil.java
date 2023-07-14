package com.github.kopylove.charades.runner;

import com.github.lazyf1sh.telegram.api.domain.ChatMemberStatus;
import com.github.lazyf1sh.telegram.api.domain.GetChatMember;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ChatMemberUtil
{
    private static final Logger LOGGER = LogManager.getLogger(ChatMemberUtil.class);

    private ChatMemberUtil() {}

    public static boolean isPowerUser(final GetChatMember chatMember)
    {
        if (chatMember.isOk())
        {
            final String status = chatMember.getResult()
                                            .getStatus();
            return ChatMemberStatus.ADMIN.getValue()
                                         .equals(status) || ChatMemberStatus.CREATOR.getValue()
                                                                                    .equals(status);
        }
        else
        {
            LOGGER.error("GetChatMember is not OK.");
            return false;
        }
    }
}
