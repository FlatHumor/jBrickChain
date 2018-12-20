package ru.inpleasure.brickchain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BrickChain extends Chain
{
    public BrickChain(String chainPath) {
        this.chainPath = chainPath;
    }

    @Override
    public boolean checkNonce(String headerHash, int nonce, String bits)
    {
        String guess = headerHash + Integer.toString(nonce);
        String hash = buildHash(guess);
        String firstCharsBits = hash.substring(0, bits.length());
        return firstCharsBits.equals(bits);
    }

    @Override
    public Brick calculateNonce(Brick brick) {
        int nonce = brick.getNonce();
        while (!checkNonce(brick.getHeaderHash(), nonce, brick.getBits()))
            nonce++;
        brick.setNonce(nonce);
        return brick;
    }

    @Override
    public List<String> getBricksFilenames() {
        Pattern brickFilePattern = Pattern.compile("\\d+?\\.brick");
        List<String> filenames = new ArrayList<>();
        File bricksDirectory = new File(chainPath);
        File[] directoryFiles = bricksDirectory.listFiles();
        if (directoryFiles == null)
            return filenames;
        for (final File fileEntry : directoryFiles)
            if (brickFilePattern.matcher(fileEntry.getName()).find())
                filenames.add(fileEntry.getName());
        

    }

    @Override
    public Brick getPreviousBrick() {

    }

    @Override
    public String buildHash(String guess) {
        return "";
    }
}
