package simple.chain;

import org.junit.Test;

import java.util.Arrays;

import org.junit.Assert;

public class ChainTests {

	@Test
	public void checkChainFactory() {
		
		Chain chain = ChainFactory.get(3, 0);
		Assert.assertTrue(chain.blocks.size()==1);
		
		chain.pendingUnsubmittedData.addAll(Arrays.asList("gugu","mumu","bubu","zuzu"));
		chain.minePendingTransactions();
		try {
			chain.printBlockChain();
		} catch(Exception e) {
			Assert.fail();
		}
		
		Assert.assertTrue(chain.checkChain());
		Assert.assertTrue(chain.blocks.size()==5);
		
	}
	
}
