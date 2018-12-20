package ru.inpleasure.brickchain;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        File bricksDirectory = new File(chainPath);
        File[] directoryFiles = bricksDirectory.listFiles();
        if (directoryFiles == null)
            return new ArrayList<>();
        List<Integer> brickNumbers = new ArrayList<>(directoryFiles.length);
        for (final File fileEntry : directoryFiles)
        {
            if (brickFilePattern.matcher(fileEntry.getName()).find()) {
                String fileNumber = fileEntry.getName().split(".")[0];
                brickNumbers.add(Integer.valueOf(fileNumber));
            }
        }
        Collections.sort(brickNumbers);
        return brickNumbers.stream()
                .map(n -> Integer.toString(n) + ".brick")
                .collect(Collectors.toList());
    }

    @Override
    public Brick getPreviousBrick() {
        List<String> bricksFilenames = getBricksFilenames();
        if (bricksFilenames.size() > 0)
            return loadBrick(bricksFilenames.get(bricksFilenames.size() - 1));
        return null;
    }

    @Override
    public Brick loadBrick(String filename) {
        return new Brick();
    }

    @Override
    public String buildHash(String guess) {
        return "";
    }
}
