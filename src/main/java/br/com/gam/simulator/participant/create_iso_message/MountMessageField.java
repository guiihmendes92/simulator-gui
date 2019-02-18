package br.com.gam.simulator.participant.create_iso_message;

import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.TestJson;
import br.com.gam.simulator.fields.Field;
import org.jpos.iso.ISOMsg;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static br.com.gam.simulator.constants.FieldConstants.mountField;

/**
 * Created by Guilherme Mendes on 17/02/2019
 */
interface MountMessageField extends Consumer<TestJson> {
	
	default void accept(final TestJson testJson) {
		
		final Card                card              = testJson.getCard();
		final ISOMsg              isoMessageRequest = testJson.getIsoMessageRequest();
		final Map<String, String> jsonMap           = testJson.getJsonMap();
		
		jsonMap.forEach((key, value) -> {
			
			final Optional<Field> field = mountField(key, value, card);
			
			field.map(Field::generate) //
			     .ifPresent(IsoMessageValue -> isoMessageRequest.set(key, IsoMessageValue));
		});
	}
	
}
