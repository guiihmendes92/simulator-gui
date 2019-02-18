//package br.com.gam.simulator.fields;
//
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOUtil;
//import simulador.entidades.Card;
//
//public class Field032 implements Field {
//
//	private Card card;
//
//	public Field032(final Card card) {
//		this.card = card;
//	}
//
//	public String generate() {
//		try {
//			return ISOUtil.zeropad(card.getBinExtended(), 11);
//
//		} catch (ISOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//}
