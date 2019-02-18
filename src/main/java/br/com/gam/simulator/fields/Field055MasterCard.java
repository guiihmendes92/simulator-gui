//package br.com.gam.simulator.fields;
//
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.ISOUtil;
//import org.jpos.tlv.TLVList;
//import simulador.emv.GeraDE055;
//import simulador.emv.GeraDE055_Master;
//import simulador.emv.SKDerivationInterf;
//import simulador.emv.sessionKeys.mchip.SKDerivationMasterCard;
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
//public class Field055MasterCard extends Field055Default {
//
//	public Field055MasterCard(final ISOMsg isoMessageRequest, final Card card) {
//		super(isoMessageRequest, card);
//		this.cryptogram.set_9F34("420302");
//
//	}
//
//	public String generate() {
//
//		try {
//
//			final GeraDE055 geraDE055  = getGeraDE055();
//			StringBuilder   cryptogram = new StringBuilder();
//
//			cryptogram.append(geraDE055.generateApplicationCryptogram());
//
//			ignorarATC(cryptogram);
//
//			cryptogram = retiraTagsMasterAdquirer(cryptogram);
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
//
//		return new GeraDE055_Master(getSkDerivation(), cryptogram, getKeyIMK());
//	}
//
//	public String get9F10() {
//
//		final Optional<String> iad = Optional.ofNullable(card.getIad());
//
//		return iad.orElse("0110250000044000DAC10000000000000000");
//	}
//
//	private StringBuilder retiraTagsMasterAdquirer(final StringBuilder cryptogram) {
//
//		try {
//
//			final Integer TAG_PSN = 0x5F34;
//			final Integer TAG_PAN = 0x5A;
//			final TLVList tlvList = new TLVList();
//
//			tlvList.unpack(ISOUtil.hex2byte(cryptogram.toString()));
//
//			unsetTag(TAG_PSN, tlvList);
//			unsetTag(TAG_PAN, tlvList);
//
//			final String hexStringTlvList = ISOUtil.hexString(tlvList.pack());
//
//			return new StringBuilder(hexStringTlvList);
//
//		} catch (ISOException e) {
//			Log.print(this.getClass().getSimpleName(), e);
//		}
//
//		return cryptogram;
//	}
//
//	private void unsetTag(final Integer tag, final TLVList tlvList) {
//		if (tlvList.hasTag(tag)) {
//			tlvList.deleteByTag(tag);
//		}
//	}
//
//	public SKDerivationInterf getSkDerivation() {
//
//		final String _9F10 = cryptogram.get_9F10();
//
//		if ("14".equals(_9F10.substring(2, 4))) {
//			return new SKDerivation();
//		}
//
//		return new SKDerivationMasterCard();
//	}
//
//	public String getKeyIMK() {
//
//		final Optional<String> imk = Optional.ofNullable(card.getKeyIMK());
//
//		return imk.orElse("DF03333333330101DF03333333330202");
//	}
//}
