package org.owntracks.android.services;

import org.owntracks.android.model.messages.MessageBase;
import org.owntracks.android.preferences.types.ConnectionMode;
import org.owntracks.android.support.interfaces.ConfigurationIncompleteException;
import org.owntracks.android.support.interfaces.OutgoingMessageProcessor;

import java.io.IOException;

public abstract class MessageProcessorEndpoint implements OutgoingMessageProcessor {
    final MessageProcessor messageProcessor;

    MessageProcessorEndpoint(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    void onMessageReceived(MessageBase message) {
        message.setIncoming();
        message.setModeId(getModeId());
        messageProcessor.processIncomingMessage(onFinalizeMessage(message));
    }

    protected abstract MessageBase onFinalizeMessage(MessageBase message);

    abstract ConnectionMode getModeId();

    abstract void sendMessage(MessageBase m) throws ConfigurationIncompleteException, OutgoingMessageSendingException, IOException;
}

class OutgoingMessageSendingException extends Exception {
    OutgoingMessageSendingException(Exception e) {
        super(e);
    }
}
