//package br.com.gam.simulator.fields;
//
//import simulador.entidades.Card;
//import simulador.util.HexadecimalUtils;
//import simulador.util.Log;
//import simulador.util.PinBlockGenerator;
//
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.DESKeySpec;
//import javax.crypto.spec.DESedeKeySpec;
//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.KeySpec;
//import java.util.Optional;
//
//public class Field052 implements Field {
//
//	private Card card;
//
//	public Field052(final Card card) {
//		this.card = card;
//	}
//
//	public String generate() {
//
//		try {
//
//			final Optional<String> chave = Optional.ofNullable(card.getKeyPIN());
//			if (chave.isPresent()) {
//
//				KeySpec           keySpec;
//				Key               key;
//				PinBlockGenerator generator;
//				Optional<String>  password;
//				Optional<String>  pan;
//
//				if (chave.get().length() <= 16) {
//
//					keySpec = new DESKeySpec(HexadecimalUtils.convert2bytes(chave.get()));
//					key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
//					generator = new PinBlockGenerator(key);
//					password = Optional.ofNullable(card.getPassword());
//					pan = Optional.ofNullable(card.getPan());
//
//					if (password.isPresent()) {
//						final String pin = generator.generate(password.get(), pan.orElse(""));
//						return pin;
//
//					}
//				}
//				final String componente1 = chave.get().substring(0, chave.get().length() / 2);
//				keySpec = new DESedeKeySpec(HexadecimalUtils.convert2bytes(chave.get() + componente1));
//				key = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
//				generator = new PinBlockGenerator(key);
//				password = Optional.ofNullable(card.getPassword());
//				pan = Optional.ofNullable(card.getPan());
//
//				if (password.isPresent()) {
//					final String pin = generator.generate(password.get(), pan.orElse(""));
//					return pin;
//
//				}
//			}
//
//		} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
//			Log.print(this.getClass().getSimpleName(), e);
//		}
//		return null;
//	}
//
//	//	public static void main(String[] args) {
//	//		//		AB506A1A7F396F73
//	//		// 8689D2B19A4C84FC
//	//
//	//		final Card card = LeitorJsonCartao.ler(
//	//				"C:\\Users\\guilherme.mendes\\Desenvolvimento\\workSpaces\\neon\\json\\GUI-Simulator\\Cartões\\1V12A_503396198916.json");
//	//
//	//		card.setKeyPIN(KeyPIN.CABAL2.getKey());
//	//
//	//		System.out.println(EncryptedPIN.extractAccountNumberPart(card.getPan()));
//	//
//	//		System.out.println(new Field052(card).generate());
//	//	}
//}
