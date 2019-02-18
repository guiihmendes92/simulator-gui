package br.com.gam.simulator.participant.create_iso_message;

import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.TestJson;
import br.com.gam.simulator.fields.Field;
import org.jpos.iso.ISOMsg;

import java.util.Map;
import java.util.Optional;

import static br.com.gam.simulator.constants.FieldConstants.mountField;

/**
 * Created by Guilherme Mendes on 17/02/2019
 */
class AuthorizationOriginal implements MountMessageField {
	
	private final TestJson previousTestJson;
	
	AuthorizationOriginal(final TestJson previousTestJson) {
		this.previousTestJson = previousTestJson;
	}
	
	@Override
	public void accept(final TestJson testJson) {
		
		final Card                card              = testJson.getCard();
		final ISOMsg              isoMessageRequest = testJson.getIsoMessageRequest();
		final Map<String, String> jsonMap           = testJson.getJsonMap();
		
		jsonMap.forEach((key, value) -> {
			
			final Optional<Field> field = mountField(key, value, card);
			
			field.map(Field::generate) //
			     .flatMap(IsoMessageValue -> isoMessageRequest.set(key, IsoMessageValue)) //
					.
		});
	}
}
}
