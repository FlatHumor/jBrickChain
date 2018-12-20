package ru.inpleasure.brickchain;

public class Brick
{
    private String headerHash;
    private String previousBrickHash;
    private String bits = "0000";
    private String filename;
    private int nonce = 0;
    private long timestamp;
    private Transaction transaction;

    public Brick() {
        this.timestamp = System.currentTimeMillis();
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
