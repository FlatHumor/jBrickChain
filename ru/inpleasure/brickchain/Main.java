package ru.inpleasure.brickchain;

import ru.inpleasure.brickchain.FileBased.FileRepository;

public class Main
{
	public static void main(String[] args)
	{
        String brickPath = "/home/mark/IdeaProjects/ru.inpleasure.brickchain/src/ru/inpleasure/brickchain/chain";
        Repository repository = new FileRepository(brickPath);
		Chain chain = new BrickChain(repository);
        Transaction transaction = new Transaction("sender", "receiver", "if you want to be okay");
        chain.addTransaction(transaction);
    }
    
    private static void updateBrick(Brick brick) {
        brick.setHeaderHash("bla-bla-bla");
    }
}
