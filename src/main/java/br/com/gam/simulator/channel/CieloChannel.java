package br.com.gam.simulator.channel;

import org.jpos.iso.channel.NACChannel;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

import java.io.IOException;

public class CieloChannel extends NACChannel {
	
	protected int getMessageLength() throws IOException {
		int    l = 0;
		byte[] b = new byte[2];
		while (l == 0) {
			serverIn.readFully(b, 0, 2);
			l = ((((int) b[0]) & 0xFF) << 8) | (((int) b[1]) & 0xFF);
			if (l == 0) {
				// serverOut.write(b);
				// serverOut.flush();
				Logger.log(new LogEvent(this, "poll"));
			}
		}
		return l;
	}
}
