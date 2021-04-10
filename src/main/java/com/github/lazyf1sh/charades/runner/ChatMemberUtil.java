package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.telegram.api.ChatMemberStatus;
import com.github.lazyf1sh.charades.domain.telegram.api.GetChatMember;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatMemberUtil
{
    private static final Logger LOGGER = LogManager.getLogger(ChatMemberUtil.class);

    public static boolean isPowerUser(GetChatMember chatMember)
    {
        if (chatMember.isOk())
        {
            String status = chatMember.getResult().getStatus();
            return ChatMemberStatus.ADMIN.getValue().equals(status) || ChatMemberStatus.CREATOR.getValue().equals(status);
        }
        else
        {
            LOGGER.error("GetChatMember is not OK.");
            return false;
        }
    }
}
