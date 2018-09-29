package simple.block;

import org.apache.commons.codec.binary.Base64;

public final class BlockFactory {

	public static final Block get(String prevHash, byte[] data) {
		return new Block(prevHash, Base64.encodeBase64String(data));
	}
	
	public static final Block get(String prevHash, String data) {
		return get(prevHash, data.getBytes());
	}
	
}
