/*
 * TCSS 360 - Autumn  2020
 * Assignment 1 - PEP8/Simulator
 */

/**
 * This program is meant simulate PEP8. 
 * This is where the gui and the CPU work together to display
 * The result
 * @author KietTruong, Chris Ding,  Muhammad Shahid
 * @version 10/21/2020
 */
import command.Command;
import cpu.cu.ProgramCounter;
import cpu.alu.ALU;
import cpu.cu.ControlUnit;
import iou.IOUnit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main implements ActionListener{
	
  public static String objectCodeInput;
  public static JButton run;
  public static JTextField objectCode;
  public JTextField inputBox;
  public JTextField outputBox;
  public JTextField IS;
  public JTextField OS;
  public JTextField PC;
  public ControlUnit controlUnit;
//  public ALU alu;
//  public ProgramCounter pc;
//  public IOUnit ioUnit;
  public String instruction;
  public String operand;
  public String address;
  public String binaryString;
  
  public Main(){
		
	    HashMap<Long, Long> memory = new HashMap<>();
	    controlUnit = new ControlUnit(memory);
//	    alu = controlUnit.getAlu();
//	    pc = controlUnit.getPc();
//	    ioUnit = new IOUnit(memory);
	   
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel();
		JPanel topWestPanel = new JPanel();
		JPanel bottomWestPanel = new JPanel();
		JPanel topEastPanel = new JPanel();
		JPanel bottomEastPanel = new JPanel();
		
		objectCode = new JTextField("Enter Hex Code",30);
		objectCode.setBounds(50,200, 200,30);
		
		inputBox = new JTextField("Input Box", 30);
		outputBox = new JTextField("Output Box", 30);
		IS = new JTextField("Instruction Specifier", 30);
		IS.setBounds(50,200, 200,30);
		OS = new JTextField("Operand Specifier", 30);
		OS.setBounds(50,200, 200,30);
		PC = new JTextField("Program Counter", 30);
		PC.setBounds(50,200, 200,30);
		JLabel programCounter = new JLabel ("PC");
		JLabel register = new JLabel ("IR");
		JLabel oC = new JLabel("Object Code");
		oC.setForeground(Color.BLUE);
		JLabel iB = new JLabel("Input Box");
		JLabel oB = new JLabel("Output Box");
		JLabel cpu = new JLabel("CPU");
		cpu.setForeground(Color.RED);
	    run = new JButton("Run");
		run.setBounds(100, 190, 60, 30);
		run.addActionListener(this);
		
		frame.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("PEP8 - Simulator");
		frame.setSize(770, 570);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		mainPanel.setLayout(new GridLayout(2,2,20,20));
		mainPanel.add(topWestPanel);
		mainPanel.add(topEastPanel);
		mainPanel.add(bottomWestPanel);
		mainPanel.add(bottomEastPanel);
		
		topWestPanel.setLayout(new BorderLayout());
		topWestPanel.add(oC, BorderLayout.NORTH);
		topWestPanel.add(objectCode, BorderLayout.CENTER);
		objectCode.setBounds(0, 0, 32, 32);
		topWestPanel.add(run, BorderLayout.SOUTH);
		
		topEastPanel.setLayout(new BoxLayout(topEastPanel, BoxLayout.PAGE_AXIS));
		topEastPanel.add(cpu);
		topEastPanel.add(programCounter);
		topEastPanel.add(PC);
		topEastPanel.add(register);
		topEastPanel.add(IS);
		topEastPanel.add(OS);
		
		
		bottomWestPanel.setLayout(new BorderLayout());
		bottomWestPanel.add(iB,BorderLayout.NORTH);
		bottomWestPanel.add(inputBox,BorderLayout.CENTER);
		
		bottomEastPanel.setLayout(new BorderLayout());
		bottomEastPanel.add(oB,BorderLayout.NORTH);
		bottomEastPanel.add(outputBox,BorderLayout.CENTER);
		
		frame.pack();
	}

	private JButton JButton(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == run) {
			objectCodeInput = objectCode.getText();
//			ProgramCounter pc = new ProgramCounter();
//			binaryString = pc.getBinaryString(objectCodeInput);
//			List<Command> commandList = pc.getCommand(binaryString);
//			System.exit(0);
			controlUnit.stop = 0; // reset program;
			controlUnit.clearR();
			controlUnit.setCode(objectCodeInput);
			while(controlUnit.stop == 0) { // not stop
				controlUnit.fetch();
				controlUnit.decode();
				controlUnit.execute();
	            IS.setText("Instruction: " + controlUnit.current.getInstruction());
	            OS.setText("Operand: " + controlUnit.current.getOperand());
	            PC.setText("Program Counter: " + controlUnit.getPc());
	            outputBox.setText(controlUnit.getIO().getOutput());
			}
/*
//			JOptionPane.showMessageDialog(null, "Input " + objectCodeInput);
			binaryString = pc.getBinaryString(objectCodeInput);
			List<Command> commandList = pc.getCommand(binaryString);
	        for (Command command : commandList){	        	
//	        Select cpu.alu.ALU or I/O unit to execute
				instruction = command.getInstruction();
				operand = command.getOperand();
	            if(command.getName().equals("In") || command.getName().equals("Out")){
	                ioUnit.execute(command);
	            }else {
	                alu.execute(command); 
	            }
	            IS.setText("Instruction: " +instruction);
	            OS.setText("Operand: " + operand);
	            PC.setText("Program Counter: " + address);
	            outputBox.setText(ioUnit.getOutput());
//	            System.out.println("-------------------------------------------");
	        }
//	        System.exit(0);
 * */
		}
	}
	
    private String getBinaryString(String inputs) {
        StringBuilder binaryString = new StringBuilder();
        for(int i=0;i<inputs.length();i+=2){ // 16 bit code align
            String temp = inputs.substring(i,i+2);
            String binaryPart = Long.toBinaryString(Long.parseLong(temp,16));
            while(binaryPart.length()<8){
                binaryPart = "0" + binaryPart;
            }
            binaryString.append(binaryPart);
        }
        System.out.println("The input hex transfer to binary: " + binaryString);
        System.out.println("------------------------------------");
        return binaryString.toString();
	}

	public static void main(String[] args) {
//         Simply think of the hashmap as memory.
//         The key value pair is regarded as the mapping of address and data.
    	new Main();

//   	 example1. Input instruction array
//       String inputs = "49000f49001051001051000f00";

//       example2. write Hello
         
//       String inputs = "50004850006550006c50006c50006f00";
    	

//       String inputs = "50004850006550006c50006c50006f00";
         
    }
}
