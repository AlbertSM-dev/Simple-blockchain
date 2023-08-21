package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class Blockchain {

    private final List<Block> blocks;
    
    public Blockchain() {
        blocks = new LinkedList<>();
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    public void add(Block block) {
        blocks.add(block);
    }

    public int size() {
        return blocks.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {


        if (blocks.isEmpty()) {
            return true;
        }

        else if (blocks.size() == 1) {
            Block block = blocks.get(0);

            return isMined(block) && block.getHash().equals(block.calculatedHash());
        }

        else if (blocks.size() > 1) {
            Block currentBlock;
            Block lastBlock;

            for (int i = 1; i < blocks.size(); i++) {
                currentBlock = blocks.get(i);
                lastBlock = blocks.get(i - 1);

                if (!isMined(currentBlock) || !isMined(lastBlock))
                    return false;

                if (!currentBlock.getPreviousHash().equals(lastBlock.getHash()))
                    return false;

                if (!currentBlock.getHash().equals(currentBlock.calculatedHash()))
                    return false;

                if (!lastBlock.getHash().equals(lastBlock.calculatedHash()))
                    return false;
            }
        }

        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) throws NoSuchAlgorithmException {
        return minedBlock.getHash().startsWith("00");
    }
}