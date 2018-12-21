
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.security.*;

public class BrickChain extends Chain
{
    public BrickChain(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void linkRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    void unlinkRepository() {
        // TODO: Implement
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
    public void calculateNonce(Brick brick) 
    {
        int nonce = brick.getNonce();
        while (!checkNonce(brick.getHeaderHash(), nonce, brick.getBits()))
            nonce++;
        brick.setNonce(nonce);
    }

    @Override
    public Brick getPreviousBrick()
    {
        final List<Integer> bricksIdentificators = repository.getIdentificators();
        if (bricksIdentificators == null || bricksIdentificators.size() == 0)
            return null;
        Brick previousBrick = repository
            .loadBrick(bricksIdentificators.get(bricksIdentificators.size() - 1));
        return previousBrick;
    }

    @Override
    public String buildHash(String guess)
    {
        try 
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(guess.getBytes());
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    @Override
    public void addTransaction(Transaction transaction)
    {
        int previousBrickNumber = 0;
        String previousHash = DEFAULT_HASH;
        Brick previousBrick = getPreviousBrick();
        if (previousBrick != null)
        {
            previousBrickNumber = previousBrick.getIdentificator();
            previousHash = previousBrick.getHeaderHash();
        }
        Brick currentBrick = new Brick();
        currentBrick.setIdentificator(previousBrickNumber + 1);
        currentBrick.setPreviousBrickHash(previousHash);
        currentBrick.setTransaction(transaction);
        calculateNonce(currentBrick);
        String headerHash = buildHash(currentBrick.getGuess());
        currentBrick.setHeaderHash(headerHash);
        repository.saveBrick(currentBrick);
    }
}
