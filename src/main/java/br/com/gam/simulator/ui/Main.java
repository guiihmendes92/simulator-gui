package br.com.gam.simulator.ui;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.constants.TransactionManagerConstants;
import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.SwitchJsonCase;
import br.com.gam.simulator.utilities.JsonTools;
import br.com.gam.simulator.utilities.space.SimpleSpaceOperation;
import lombok.Getter;
import org.jpos.transaction.Context;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	
	private JTabbedPane   tabbedPane;
	@Getter
	private JPanel        mainPanel;
	private JTable        tableComunication;
	private JButton       cardButton;
	private JButton       caseButton;
	private JButton       executeButton;
	private JList<String> listCase;
	@Getter
	private JScrollPane   jScrollPaneExecute;
	private JPanel        jPanelExecute;
	private FileFilter    filter;
	private JFileChooser  jFileChooserCase;
	private JFileChooser  jFileChooserCard;
	
	public Main() {
		createUIComponents();
	}
	
	private void createUIComponents() {
		
		final String dir       = System.getProperty("user.dir");
		final String separator = System.getProperty("file.separator");
		
		filter = new FileNameExtensionFilter("File JSON", "json");
		jFileChooserCase = new JFileChooser(createDir(dir + separator + "TestJson"));
		jFileChooserCard = new JFileChooser(createDir(dir + separator + "Card"));
		
		loadCard();
		loadCase();
		execute();
		
	}
	
	private String createDir(final String dir) {
		
		return Optional.of(new File(dir)) //
		               .filter(File::mkdirs) //
		               .map(File::getPath) //
		               .orElse(dir);
	}
	
	private void execute() {
		executeButton.addActionListener(e -> {
			
			final SimpleSpaceOperation simpleSpaceOperation = new SimpleSpaceOperation();
			
			loadCase().forEach(isoMessageCase -> {
				
				final Context context = new Context();
				
//				context.put(Constants.TIMESTAMP, LocalDateTime.now());
				context.put(Constants.SWITCH_CASE, isoMessageCase);
				context.put(Constants.CARD_SWITCH_CASE, loadCard());
				
				simpleSpaceOperation.queue(TransactionManagerConstants.MAIN, context, 60000L);
				
			});
		});
	}
	
	private Card loadCard() {
		
		cardButton.addActionListener(e -> {
			
			jFileChooserCard.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jFileChooserCard.addChoosableFileFilter(filter);
			//jFileChooser.setMultiSelectionEnabled(true);
			
			int selectTheCard = jFileChooserCard.showDialog(null, "Select the card");
			
			if (selectTheCard == JFileChooser.APPROVE_OPTION) {
				
				final File   file       = jFileChooserCard.getSelectedFile();
				final String name       = file.getName();
				final String nomeCartao = name.substring(0, name.length() - 5);
				
				final TitledBorder titledBorder = new TitledBorder(nomeCartao);
				
				titledBorder.setTitleColor(Color.BLUE);
				
				jScrollPaneExecute.setBorder(titledBorder);
			}
		});
		
		return Optional.ofNullable(jFileChooserCard.getSelectedFile()) //
		               .map(File::getPath) //
		               .map(path -> (Card) JsonTools.read(path, Card.class)) //
		               .orElse(new Card()); //
		
	}
	
	@SuppressWarnings("BoundFieldAssignment")
	private Set<SwitchJsonCase> loadCase() {
		
		caseButton.addActionListener(e -> {
			
			jFileChooserCase.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jFileChooserCase.addChoosableFileFilter(filter);
			jFileChooserCase.setMultiSelectionEnabled(true);
			
			final int selectTheCase = jFileChooserCase.showDialog(null, "Select the case");
			
			if (selectTheCase == JFileChooser.APPROVE_OPTION) {
				
				listCase = Optional.of(new DefaultListModel<String>()) //
				
				                   .map(defaultListModel -> {
					                   Stream.of(jFileChooserCase.getSelectedFiles()) //
					                         .forEach(file -> {
						
						                         final String nameCase = file.getName().substring(0, file.getName().length() - 5);
						
						                         defaultListModel.addElement(nameCase);
					                         }); //
					
					                   return defaultListModel;
				                   }) //
				                   .map(JList::new) //
				                   .orElseGet(JList::new);
				//
				
				//				 = new JList();
				final TitledBorder titledBorder = new TitledBorder("Cases");
				titledBorder.setTitleColor(Color.GREEN);
				listCase.setBorder(titledBorder);
				//				final Object[] objects = Stream.of(jFileChooserCase.getSelectedFiles()) //
				//				                               .map(file -> {
				//
				//					                               final String nameCase = file.getName().substring(0, file.getName().length() - 5);
				//					                               defaultListModel.addElement(nameCase);
				//					                               return nameCase;
				//				                               }) //
				//				                               .distinct() //
				//				                               .toArray();
				
				//				listCase.setListData(objects);
				listCase.setVisibleRowCount(-1);
				listCase.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				listCase.setLayoutOrientation(JList.VERTICAL);
				
				final JScrollPane listScroller = new JScrollPane(listCase);
				listScroller.setPreferredSize(new Dimension(250, 80));
				
			}
		});
		
		final List<File> selectedValuesList = Arrays.asList(jFileChooserCase.getSelectedFiles());
		
		return selectedValuesList.stream() //
		                         .map(fileList -> (SwitchJsonCase) JsonTools.read(fileList.getPath(), SwitchJsonCase.class)) //
		                         .collect(Collectors.toSet());
	}
}
