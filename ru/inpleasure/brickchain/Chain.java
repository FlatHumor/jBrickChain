package ru.inpleasure.brickchain;

import java.util.List;

public abstract class Chain
{
    public static final String DEFAULT_HASH = "E175E69A8312A66A7011C1DCCFC2CF69823BC2C52F58CA516F60698F02DAA1D6";
    String chainPath;

    abstract boolean checkNonce(String headerHash, int nonce, String bits);
    abstract Brick calculateNonce(Brick brick);
    abstract String buildHash(String guess);
    abstract Brick getPreviousBrick();
    abstract void saveBrick(Brick brick);
    abstract Brick loadBrick(String filename);
    abstract List<String> getBricksFilenames();
}
