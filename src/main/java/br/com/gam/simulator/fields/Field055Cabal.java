//package br.com.gam.simulator.fields;
//
//import org.jpos.iso.ISOMsg;
//import simulador.emv.GeraDE055;
//import simulador.emv.GeraDE055_Cabal;
//import simulador.emv.SKDerivationInterf;
//import simulador.emv.sessionKeys.v2.SKDerivation;
//import simulador.entidades.Card;
//import simulador.util.Log;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//import java.util.Optional;
//
//public class Field055Cabal extends Field055Default {
//
//	public Field055Cabal(final ISOMsg isoMessageRequest, final Card card) {
//		super(isoMessageRequest, card);
//	}
//
//	public String generate() {
//
//		try {
//
//			final GeraDE055     geraDE055  = getGeraDE055();
//			final StringBuilder cryptogram = new StringBuilder();
//
//			cryptogram.append(geraDE055.generateApplicationCryptogram());
//
//			ignorarATC(cryptogram);
//
//			return parseCriptogramHexString(cryptogram);
//
//		} catch (InvalidKeySpecException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException e) {
//
//			Log.print(getClass().getSimpleName(), e);
//		}
//		return null;
//	}
//
//	@Override
//	protected GeraDE055 getGeraDE055() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
//	                                          InvalidKeySpecException, BadPaddingException {
//		return new GeraDE055_Cabal(getSkDerivation(), cryptogram, getKeyIMK());
//	}
//
//	public String get9F10() {
//
//		final Optional<String> iad = Optional.ofNullable(card.getIad());
//
//		return iad.orElse("0FA501A03900140000000000000000000F02030203B818963060380000000000");
//	}
//
//	public SKDerivationInterf getSkDerivation() {
//		return new SKDerivation();
//	}
//
//	public String getKeyIMK() {
//		final Optional<String> imk = Optional.ofNullable(card.getKeyIMK());
//
//		return imk.orElse("101112131415161718191A1B1C1D1E1F");
//	}
//}
