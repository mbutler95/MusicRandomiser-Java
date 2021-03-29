package MusicPickerLib;

import java.nio.file.*;
import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MusicPicker implements ActionListener{
	
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
	
	static ProcessBuilder builder;
	static String MusicPath ="C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe";
	static String result = "";
	static Path path = Paths.get("D:\\Music");
	static java.util.List<String> completeSongs;
	static JButton button1, button2, button3, button4;
	static JLabel tf, randomDesc;
	static JTextField rf = new JTextField("");
	static JMenuBar mb;
	static JMenu menu;
	static JMenuItem options, exit;
	
	
	@Override
    public void actionPerformed(ActionEvent ae) {
        }
	
	public static void createGUI(){
		JFrame frame = new JFrame("Music Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(frame.getContentPane());
		addFunctions();
		frame.pack();
        frame.setVisible(true);
	}		
	
	public static void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
 
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			c.fill = GridBagConstraints.BOTH;
		}
	
		menu = new JMenu("Menu");
		options = new JMenuItem("Options");
		exit = new JMenuItem("Exit");
		menu.add(options);
		menu.add(exit);
		
		mb = new JMenuBar();
		mb.setBackground(new Color(0xb0d4d4));
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		mb.add(menu);
		pane.add(mb, c);
 
		button1 = new JButton("Random Band");
		c.ipady = 30;      
		c.ipadx = 100;
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(button1, c);
	
		button2 = new JButton("Random Album");
		c.ipady = 30;      
		c.ipadx = 100;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(button2, c);
 
		button3 = new JButton("Play");
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 50;      
		c.ipadx = 100;
		c.weightx = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		pane.add(button3, c);
	
	
		tf = new JLabel(" ",SwingConstants.CENTER);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 10;       
		c.weighty = 1;   
		c.insets = new Insets(5,0,5,0); 
		c.gridx = 0;       
		c.gridwidth = 2;   
		c.gridy = 2;       
		pane.add(tf, c);
	
		button4 = new JButton("Play Random");
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 20;      
		c.ipadx = 50;
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 4;
		pane.add(button4, c);
	
		randomDesc = new JLabel("Random Amount to Played:", SwingConstants.CENTER);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;       
		c.gridwidth = 1;   
		c.gridy = 5;
		pane.add(randomDesc, c);
		
		rf = new JTextField("0");
		rf.setHorizontalAlignment(JTextField.CENTER);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		c.weighty = 1;   
		c.gridx = 1;       
		c.gridwidth = 1;   
		c.gridy = 5;       
		pane.add(rf, c);
	}
	//////////////////////////Button Logic//////////////////////////
	public static void addFunctions(){
		button1.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				result = GetBand();
				tf.setText(result); 
				result += "--Band";		
			}  
		});
			
		button2.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				result = GetAlbum();
				tf.setText(result);   
			}  
		});  
	
		button3.addActionListener(new ActionListener(){  
		public void actionPerformed(ActionEvent e){  
				PlayMusic();		
			}  
		}); 

		button4.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				try{
					PlayRandomMusic(Integer.parseInt(rf.getText()));
				}catch(Exception ex){
					rf.setText("Enter a valid number");
				}
			}  
		}); 
    	
		rf.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
			}
		
			@Override
			public void focusGained(FocusEvent arg0) {
				rf.setText("");
			}
		});
	}
	
		
	
	////////Randomly Select Band////////////////////
	public static String GetBand(){
		java.util.List<String> list = new ArrayList<>();
		try{
			list = Files.walk(path,1)
			.filter(p -> Files.isDirectory(p) && p.toString().length()>8)
			.map(p -> p.getFileName().toString())
			.filter(p -> !(p.contains("Playlist") || p.contains("Nature")))
			.collect(Collectors.toList());
		}catch(IOException e){
			e.printStackTrace();
		}
		int randnum = (int)(Math.random()*list.size());
		return list.get(randnum);
	}
	
	////////Randomly Select Album////////////////////
	public static String GetAlbum(){
		java.util.List<String> list = new ArrayList<>();
		try{
			list = Files.walk(path,2)
			.filter(p -> Files.isDirectory(p) && p.toString().length()>8)
			.filter(p -> p.toString().indexOf("\\",10)!=-1)
			.map(p -> p.getName(1).toString() + " - " + p.getFileName().toString())
			.filter(p -> !(p.contains("Playlist") || p.contains("Nature")))
			.collect(Collectors.toList());
		}catch(IOException e){
			e.printStackTrace();
		}
		int randnum = (int)(Math.random()*list.size());
		return list.get(randnum);
	}
	
	////////Play Music////////////////////////////
	public static void PlayMusic(){
		java.util.List<String> commandList = new ArrayList<>();
		if (result.contains("--Band")){
			commandList = fetchBandSongs();
		}else{
			commandList = fetchAlbumSongs();
		}
		commandList.add(0, MusicPath);
		builder = new ProcessBuilder(commandList);
        try{
			builder.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void PlayRandomMusic(int num){
		System.out.println(num + " In PlayRandomMusic");
		java.util.List<String> commandList = new ArrayList<>();
		if(completeSongs == null){
			completeSongs = populateSonglist();
		}
		for(int i = 0;i < num; i++){
			int randnum = (int)(Math.random()*completeSongs.size());
			commandList.add(completeSongs.get(randnum));
		}
		commandList.add(0, MusicPath);
		builder = new ProcessBuilder(commandList);
        try{
			builder.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static java.util.List<String> fetchBandSongs(){
		String[] Band = result.split("-");
		Path bandPath = path.resolve(Paths.get(Band[0]));
		java.util.List<String> songlist = new ArrayList<>();
		try{
			songlist = Files.walk(bandPath)
			.filter(p -> Files.isRegularFile(p))
			.filter(p -> 	p.toString().contains(".mp3") |
							p.toString().contains(".m4a") |
							p.toString().contains(".wma"))
			.map(p -> p.toString())
			.collect(Collectors.toList());
		}catch(IOException e){
			e.printStackTrace();
		}
		//System.out.println(songlist);
		return songlist;
	}
	
	public static java.util.List<String> fetchAlbumSongs(){
		String[] Band = result.split(" - ");
		Path albumBandPath = (Paths.get(Band[0])).resolve(Paths.get(Band[1]));
		Path albumPath = path.resolve(albumBandPath);
		java.util.List<String> songlist = new ArrayList<>();
		try{
			songlist = Files.walk(albumPath)
			.filter(p -> Files.isRegularFile(p))
			.filter(p -> 	p.toString().contains(".mp3") |
							p.toString().contains(".m4a") |
							p.toString().contains(".wma"))
			.map(p -> p.toString())
			.collect(Collectors.toList());
		}catch(IOException e){
			e.printStackTrace();
		}
		//System.out.println(songlist);
		return songlist;
	}
	
	public static java.util.List<String> populateSonglist(){
		java.util.List<Path> pathList = new ArrayList<>();
		java.util.List<String> songlist = new ArrayList<>();
		try{
			pathList = Files.walk(path,1)
			.filter(p -> Files.isDirectory(p) && p.toString().length()>8)
			.map(p -> p.getFileName().toString())
			.filter(p -> !(p.contains("Playlist") || p.contains("Nature")))
			.map(p -> path.resolve(Paths.get(p)))
			.collect(Collectors.toList());
			
			for(int i = 0; i< pathList.size();i++){
				Files.walk(pathList.get(i))
				.filter(p -> Files.isRegularFile(p))
				.filter(p -> 	p.toString().contains(".mp3") |
								p.toString().contains(".m4a") |
								p.toString().contains(".wma"))
				.map(p -> p.toString())
				.forEach(p -> songlist.add(p));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return songlist;
	}
	
	public static void main(String[] args){
		createGUI();
	}
}








































