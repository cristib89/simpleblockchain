package simple.block;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONObject;

import simple.common.Constants;

public final class Block {

	private final LocalDateTime time = LocalDateTime.now();
	private final String previousHash;
	private final String base64EncodedData;
	public String hash;
	private int nonce;
	
	public String getBase64EncodedData() {
		return this.base64EncodedData;
	}
	
	public String getPreviousHash() {
		return this.previousHash;
	}
	
	public String getCurrentHash() {
		return this.hash;
	}
	
	protected Block(String previousHash, String base64EncodedData) {
		this.previousHash = previousHash;
		this.base64EncodedData = base64EncodedData;
		this.hash = calculateHash();
		this.nonce = 0;
	}
	
	private String calculateHash() {
		MessageDigest md;
		byte[] hash = null;
		try {
			md = MessageDigest.getInstance(Constants.HASHING_ALGORITHM);
			hash = md.digest(this.getPartialJsonObject().toJSONString().getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Hex.encodeHexString(hash);
	}
	
	public void mineBlock(int difficulty) { 
		String targetPrefix = String.format("%"+difficulty+"s", " ").replace(' ','0');
		while(!this.hash.substring(0, difficulty).equals(targetPrefix)) {
			this.nonce++;
			this.hash = calculateHash();
		}
	}

	public JSONObject getPartialJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put(Constants.BLK_PREV, previousHash);
		obj.put(Constants.BLK_TIME, time);
		obj.put(Constants.BLK_DATA, base64EncodedData);
		obj.put(Constants.BLK_NONCE, nonce);
		return obj;
	}
	
	public JSONObject getFullJsonObject() {
		JSONObject obj = getPartialJsonObject();
		obj.put(Constants.BLK_HASH, hash);
		return obj;
	}
	
}
