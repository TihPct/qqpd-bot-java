package io.github.kloping.qqbot.api.qqpd.message.audited;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author github.kloping
 */
@lombok.Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class MessageAuditData {
    private MessageAudit messageAudit;
}