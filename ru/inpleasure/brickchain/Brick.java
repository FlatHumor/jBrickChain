package ru.inpleasure.brickchain;

public class Brick
{
    protected int identificator;
    protected String headerHash;
    protected String previousBrickHash;
    protected String bits = "0000";
    protected int nonce = 0;
    protected long timestamp;
    protected Transaction transaction;

    public Brick() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public int getIdentificator() {
        return identificator;
    }
    
    public void setIdentificator(int identificator) {
        this.identificator = identificator;
    }

    public String getHeaderHash() {
        return headerHash;
    }

    public void setHeaderHash(String headerHash) {
        this.headerHash = headerHash;
    }

    public String getPreviousBrickHash() {
        return previousBrickHash;
    }

    public void setPreviousBrickHash(String previousBrickHash) {
        this.previousBrickHash = previousBrickHash;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getGuess() {
        return transaction.getGuess() + headerHash + Long.toString(timestamp);
    }
}
