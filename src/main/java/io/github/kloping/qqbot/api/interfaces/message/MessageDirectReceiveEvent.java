package io.github.kloping.qqbot.api.interfaces.message;

import io.github.kloping.qqbot.api.data.MessagePacket;
import io.github.kloping.qqbot.api.qqpd.Guild;
import io.github.kloping.qqbot.api.qqpd.interfaces.DirectSender;
import io.github.kloping.qqbot.api.qqpd.message.DirectMessage;
import io.github.kloping.qqbot.api.qqpd.message.Message;
import io.github.kloping.qqbot.api.qqpd.message.PreMessage;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;

/**
 * 私信消息接收事件
 *
 * @author github.kloping
 */
public interface MessageDirectReceiveEvent extends MessageReceiveEvent, DirectSender {
    /**
     * 来源guild
     *
     * @return
     */
    String getSrcGuildId();

    /**
     * 来源guild
     *
     * @return
     */
    Guild getSrcGuild();

    /**
     * 获取私信消息
     *
     * @return
     */
    DirectMessage getDirectMessage();

    /**
     * 替换默认
     *
     * @param text
     * @return
     */
    @Override
    default MessageAudited send(String text) {
        return sendDirect(text);
    }

    /**
     * 替换默认
     *
     * @param text
     * @param message
     * @return
     */
    @Override
    default MessageAudited send(String text, Message message) {
        return sendDirect(text, message);
    }

    /**
     * 替换默认
     *
     * @param packet
     * @return
     */
    @Override
    default MessageAudited send(MessagePacket packet) {
        return sendDirect(packet);
    }

    /**
     * 替换默认
     *
     * @param msg
     * @return
     */
    @Override
    default MessageAudited send(PreMessage msg) {
        return sendDirect(msg);
    }
}
