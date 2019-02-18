//package br.com.gam.simulator.fields;
//
//import org.jpos.iso.ISODate;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.ISOUtil;
//import org.jpos.space.JESpace;
//import org.jpos.space.Space;
//import org.jpos.space.SpaceUtil;
//import simulador.emv.Cryptogram;
//import simulador.emv.GeraDE055;
//import simulador.emv.SKDerivationInterf;
//import simulador.entidades.Card;
//import simulador.enums.AID;
//import simulador.enums.SequencesDIR;
//import simulador.ui.SimulatorUI;
//import simulador.util.Log;
//import simulador.util.ParseBit55;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//import java.util.Date;
//import java.util.Random;
//
//import static org.jpos.iso.ISOUtil.zeropad;
//
//public abstract class Field055Default {
//
//	protected final ISOMsg     isoMessageRequest;
//	protected final Card       card;
//	protected       Cryptogram cryptogram = new Cryptogram();
//
//	public Field055Default(final ISOMsg isoMessageRequest, final Card card) {
//		this.isoMessageRequest = isoMessageRequest;
//		this.card = card;
//
//		cryptogram.set_5A(card.getPan()) //
//		          .set_5F34(card.getPsn()) //
//		          .set_5F2A("0986") //
//		          .set_82("5800") //
//		          .set_84(AID.get(card.getAid())) //
//		          .set_95("4080000000") //
//		          .set_9A(getDataLocal()) //
//		          .set_9C(isoMessageRequest.getString(3).substring(0, 2)) //
//		          .set_9F1A("0076") //
//		          .set_9F02(isoMessageRequest.getString(4)) //
//		          .set_9F03(get_9F03()) //
//		          .set_9F10(get9F10()) //
//		          .set_9F36(getAtc()) //
//		          .set_9F37(get9F37()) //
//		          .set_9F27("80"); //
//
//	}
//
//	private String get_9F03() {
//		final String valorAdicional = isoMessageRequest.getString(54);
//		if (isoMessageRequest.hasField(54) && valorAdicional.length() == 20) {
//			return valorAdicional.substring(8);
//		}
//		return "000000000000";
//	}
//
//	protected abstract String get9F10();
//
//	protected abstract SKDerivationInterf getSkDerivation();
//
//	protected abstract String generate();
//
//	protected abstract GeraDE055 getGeraDE055() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
//	                                                   InvalidKeySpecException, BadPaddingException;
//
//	protected String getDataLocal() {
//		final Date date = ISODate.parseISODate(isoMessageRequest.getString(13) + isoMessageRequest.getString(12));
//		return ISODate.formatDate(date, "YYMMdd");
//	}
//
//	public abstract String getKeyIMK();
//
//	protected String get9F37() {
//
//		try {
//
//			final String randomDigits        = ISOUtil.getRandomDigits(new Random(), 8, 9);
//			final int    randomDigitsInteger = Integer.parseInt(randomDigits);
//			final String _9F37               = Integer.toHexString(randomDigitsInteger);
//
//			return zeropad(_9F37, 8).toUpperCase();
//
//		} catch (ISOException e) {
//
//			Log.print(getClass().getSimpleName(), e);
//		}
//
//		return null;
//	}
//
//	protected void ignorarATC(final StringBuilder cryptogram) {
//		if (SimulatorUI.isIgnorarATC()) {
//
//			final String ignorarAtc = "DF0F01FC";
//
//			cryptogram.append(ignorarAtc) //
//			          .append(cryptogram);
//		}
//	}
//
//	protected String parseCriptogramHexString(final StringBuilder cryptogram) {
//
//		final String criptograma = cryptogram.toString().toUpperCase();
//
//		if (SimulatorUI.isNotParseCriptogramHexString()) {
//			return criptograma;
//		}
//
//		return ParseBit55.converteBit55(criptograma);
//	}
//
//	protected String getAtc() {
//
//		try {
//
//			final String pan      = card.getPan();
//			final Space  spaceAtc = JESpace.getSpace("atc", SequencesDIR.ATC.getDir());
//
//			synchronized (spaceAtc) {
//
//				final Long   nextLong      = SpaceUtil.nextLong(spaceAtc, pan);
//				final String atcHexadecial = nextLong.toString();
//
//				return zeropad(atcHexadecial, 4).toUpperCase();
//			}
//		} catch (ISOException e) {
//			Log.print(getClass().getSimpleName(), e);
//		}
//
//		return null;
//	}
//
//	//	public static void main(String[] args) {
//	////		final String pan  = "9223372036854775807";
//	//		final String pan = "6854775807";
//	//
//	//		final Space spaceAtc = JESpace.getSpace("atc", SequencesDIR.ATC.getDir());
//	//
//	//		System.out.println(SpaceUtil.nextLong(spaceAtc, pan));
//	//
//	//	}
//}
