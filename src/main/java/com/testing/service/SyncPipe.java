package com.testing.service;

import java.io.InputStream;
import java.io.OutputStream;

public class SyncPipe implements Runnable {
	
	private final OutputStream ostrm_;
	private final InputStream istrm_;
	
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
		istrm_ = istrm;
		ostrm_ = ostrm;
	}

	@Override
	public void run() {
		try {
			final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
				ostrm_.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
