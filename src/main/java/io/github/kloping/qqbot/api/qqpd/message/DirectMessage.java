package io.github.kloping.qqbot.api.qqpd.message;

import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.data.MessagePacket;
import io.github.kloping.qqbot.api.qqpd.Member;
import io.github.kloping.qqbot.api.qqpd.interfaces.DeleteAble;
import io.github.kloping.qqbot.api.qqpd.interfaces.DirectSender;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import static io.github.kloping.qqbot.Resource.packet2pre;
import static io.github.kloping.qqbot.api.qqpd.Channel.MAP;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>消息 id</td></tr> <tr><td>channel_id</td> <td>string</td> <td>子频道 id</td></tr> <tr><td>guild_id</td> <td>string</td> <td>频道 id</td></tr> <tr><td>content</td> <td>string</td> <td>消息内容</td></tr> <tr><td>timestamp</td> <td>ISO8601 timestamp</td> <td>消息创建时间</td></tr> <tr><td>edited_timestamp</td> <td>ISO8601 timestamp</td> <td>消息编辑时间</td></tr> <tr><td>mention_everyone</td> <td>bool</td> <td>是否是@全员消息</td></tr> <tr><td>author</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a> 对象</td> <td>消息创建者</td></tr> <tr><td>attachments</td> <td><a href="#messageattachment">MessageAttachment</a> 对象数组</td> <td>附件</td></tr> <tr><td>embeds</td> <td><a href="#messageembed">MessageEmbed</a> 对象数组</td> <td>embed</td></tr> <tr><td>mentions</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a> 对象数组</td> <td>消息中@的人</td></tr> <tr><td>member</td> <td><a href="/wiki/develop/api/openapi/member/model.html#member" class="">Member</a> 对象</td> <td>消息创建者的member信息</td></tr> <tr><td>ark</td> <td><a href="#messageark">MessageArk</a> ark消息对象</td> <td>ark消息</td></tr> <tr><td>seq</td> <td>int</td> <td>用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序。(目前只在消息事件中有值，<code>2022年8月1日</code> 后续废弃)</td></tr> <tr><td>seq_in_channel</td> <td>string</td> <td>子频道消息 seq，用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序</td></tr> <tr><td>message_reference</td> <td><a href="#messagereference">MessageReference</a> 对象</td> <td>引用消息对象</td></tr> <tr><td>src_guild_id</td> <td>string</td> <td>用于私信场景下识别真实的来源频道id</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
public class DirectMessage extends Message
        implements DirectSender, DeleteAble {
    private String id;
    private String channelId;
    private String guildId;
    private String content;
    private String timestamp;
    private String editedTimestamp;
    private Boolean mentionEveryone;
    private User author;
    private MessageAttachment[] attachments;
    private Object embed;
    private User[] mentions;
    private Member member;
    private Object ark;
    private Integer seq;
    private String seqInChannel;
    private MessageReference messageReference;
    private String srcGuildId;

    public static DirectMessage messageAsDirectMessage(Message message) {
        return new DirectMessage().setId(message.getId()).setChannelId(message.getChannelId())
                .setGuildId(message.getGuildId()).setContent(message.getContent()).setTimestamp(message.getTimestamp())
                .setEditedTimestamp(message.getEditedTimestamp()).setMentionEveryone(message.getMentionEveryone())
                .setAuthor(message.getAuthor()).setAttachments(message.getAttachments()).setEmbed(message.getEmbed())
                .setMentions(message.getMentions()).setMember(message.getMember()).setArk(message.getArk()).setSeq(message.getSeq())
                .setSeqInChannel(message.getSeqInChannel()).setMessageReference(message.getMessageReference()).setSrcGuildId(message.getSrcGuildId());
    }

    @Override
    public MessageAudited sendDirect(String text) {
        return sendDirect(new MessagePacket().setContent(text));
    }

    @Override
    public MessageAudited sendDirect(String text, Message message) {
        return sendDirect(new MessagePacket().setContent(text).setReplyId(message.getId()));
    }

    @Override
    public MessageAudited sendDirect(MessagePacket packet) {
        PreMessage msg = new PreMessage();
        msg.setMsgId(DirectMessage.this.id);
        packet2pre(packet, msg);
        return Resource.dmsBase.send(DirectMessage.this.guildId, msg, MAP);
    }

    @Override
    public MessageAudited sendDirect(PreMessage msg) {
        return Resource.dmsBase.send(DirectMessage.this.guildId, msg, MAP);
    }

    @Override
    public Object delete() {
        if (Resource.starter.getBot().getInfo().getId() == this.getAuthor().getId())
            return Resource.dmsBase.delete(this.guildId, this.id, false);
        return "DELETE FAIL";
    }
}