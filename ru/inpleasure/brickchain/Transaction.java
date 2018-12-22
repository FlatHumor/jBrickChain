package ru.inpleasure.brickchain;

public class Transaction
{
    protected String sender;
    protected String receiver;
    protected String content;
    protected long timestamp;

    public Transaction(String sender, String receiver, String content)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
    
    public Transaction() { }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGuess() {
        return sender + receiver + content + Long.toString(timestamp);
    }
}
