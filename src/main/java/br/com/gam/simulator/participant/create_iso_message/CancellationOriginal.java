package br.com.gam.simulator.participant.create_iso_message;

import br.com.gam.simulator.entities.TestJson;

/**
 * Created by Guilherme Mendes on 17/02/2019
 */
class CancellationOriginal implements MountMessageField {
	
	private final TestJson previousTestJson;
	
	CancellationOriginal(final TestJson previousTestJson) {
		this.previousTestJson = previousTestJson;
	}
}
