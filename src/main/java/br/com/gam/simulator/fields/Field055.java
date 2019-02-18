//package br.com.gam.simulator.fields;
//
//import org.jpos.iso.ISOMsg;
//import simulador.entidades.Card;
//
//import static java.util.Objects.isNull;
//
//public class Field055 implements Field {
//
//	private final ISOMsg isoMessageRequest;
//	private final Card   card;
//
//	public Field055(final ISOMsg isoMessageRequest, final Card card) {
//		this.isoMessageRequest = isoMessageRequest;
//		this.card = card;
//	}
//
//	public String generate() {
//
//		final String aid = card.getAid();
//
//		if (isNull(aid)) {
//			return null;
//		}
//
//		final Field055Default field055Default;
//
//		if (aid.contains("CABAL")) {
//
//			field055Default = new Field055Cabal(isoMessageRequest, card);
//
//			//			System.out.println(field055Default.getSkDerivation().getClass().getSimpleName());
//			//			System.out.println(field055Default.getKeyIMK());
//
//			return field055Default.generate();
//		}
//
//		if (aid.contains("MASTER")) {
//
//			field055Default = new Field055MasterCard(isoMessageRequest, card);
//
//			//			System.out.println(field055Default.getSkDerivation().getClass().getSimpleName());
//			//			System.out.println(field055Default.getKeyIMK());
//
//			return field055Default.generate();
//		}
//
//		return null;
//	}
//
//	//	public static void main(String[] args) {
//	//
//	//			final String diretorioCard = "C:\\Users\\guilherme.mendes\\Desenvolvimento\\workSpaces\\neon\\json\\GUI-Simulator\\Cartões\\MCC_101_5413330002001015.json";
//	//			final Card   card          = LeitorJsonCartao.ler(diretorioCard);
//	//
//	//			final String   diretorioCaso = "C:\\Users\\guilherme.mendes\\Desenvolvimento\\workSpaces\\neon\\json\\GUI-Simulator\\Casos\\Mastercard\\CRÉDITO\\6500001\\065000100003.json";
//	//			final CasoJson lerJson       = JsonTools.ler(diretorioCaso);
//	//
//	//			final String      diretorioChannel = "C:\\Users\\guilherme.mendes\\Desenvolvimento\\workSpaces\\neon\\json\\GUI-Simulator\\Conexões\\mastercard.json";
//	//			final BaseChannel baseChannel      = LeitorJsonConexao.ler(diretorioChannel);
//	//
//	//			final Caso caso = new Caso().setCasoJson(lerJson) //
//	//			                            .setCard(card);
//	//
//	//			final MontaISOTemplate montaISOTemplate = new MontaISOTemplate(caso, caso.getCasoJson().getAutorizacao(),
//	//			                                                               baseChannel.getPackager());
//	//
//	//			final ISOMsg isoMsg = montaISOTemplate.get();
//	//
//	//			isoMsg.dump(System.out, "");
//	//
//	//			System.out.println(card.toString());
//	//		}
//
//}
