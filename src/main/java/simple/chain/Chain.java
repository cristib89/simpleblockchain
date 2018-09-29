package simple.chain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.json.simple.parser.ParseException;

import simple.block.Block;
import simple.block.BlockFactory;
import simple.common.Constants;

public class Chain {

	protected List<Block> blocks = new ArrayList<Block>();
	private final int difficulty;
	protected List<String> pendingUnsubmittedData = new ArrayList<String>();
	private final double rewardPercentage;
	
	protected Chain(int difficulty, double rewardPercentage) {
		this.difficulty = difficulty;
		this.rewardPercentage = rewardPercentage;
		this.createGenesisBlock();
	}
	
	public void createGenesisBlock() {
		blocks.add(BlockFactory.get(Constants.EMPTY, Constants.EMPTY));
	}
	
	public Block getLatestBlock() {
		return blocks.get(blocks.size()-1);
	}
	
	public boolean checkChain() {
		boolean isChainValid = true;
		for(int i=0; i<blocks.size()-1; i++) {
			if(!blocks.get(i).getCurrentHash().equals(blocks.get(i+1).getPreviousHash())) {
				isChainValid = false;
				return isChainValid;
			}
		}
		return isChainValid;
	}
	
	public void minePendingTransactions() {
		for(String someData : this.pendingUnsubmittedData) {
			Block newBlock = BlockFactory.get(this.getLatestBlock().hash, someData);
			System.out.println("Started mining @ "+LocalDateTime.now());
			newBlock.mineBlock(difficulty);
			System.out.println("Stopped mining @ "+LocalDateTime.now());
			blocks.add(newBlock);
		}
		this.pendingUnsubmittedData.clear();
	}
	
	public void printBlockChain() throws ParseException, DecoderException {
		for(Block b : blocks) {
			System.out.println(b.getFullJsonObject());
		}
	}
	
}
