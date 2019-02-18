package br.com.gam.simulator.channel;

import org.jpos.iso.BaseChannel;

import java.io.IOException;

@SuppressWarnings("unused")
public class SEChannel extends BaseChannel {
	public SEChannel() {
		super();
	}
	
	protected void sendMessageLength(int len) throws IOException {
		serverOut.write(len);
		serverOut.write(len >> 8);
	}
	
	protected int getMessageLength() throws IOException {
		byte[] b = new byte[2];
		serverIn.readFully(b, 0, 2);
		return ((((int) b[1]) & 0xFF) << 8) | (((int) b[0]) & 0xFF);
	}
}