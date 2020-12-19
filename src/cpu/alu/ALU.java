/**
 * This program is meant simulate PEP8. 
 * This is where the ALU suppose to add/ subtract to numbers together 
 * However we didn't have time to support this feature.
 * The result should be in single digit.
 * @author KietTruong, Chris Ding,  Muhammad Shahid
 * @version 10/21/2020
 */
package cpu.alu;

import command.Command;
import register.Register;
import java.util.HashMap;

/**
 * Using ALU to process arithmetic operations.
 */
public class ALU {

    private final Register register = new Register();
    private Register Z;
    private Register C;
    private Register N;
    private Register V;
    private final HashMap<Long,Long> memory;

    public ALU(HashMap<Long,Long> memory){
        this.memory = memory;
	    this.Z = new Register();
	    this.C = new Register();
	    this.N = new Register();
	    this.V = new Register();
    }

    /**
     * Choose the right method to execute
     * @param command Instruction information
     */
    public int execute(Command command) {
    	int exitCode = 0;
        String name = command.getName();
        int type = command.getType();
        String data = command.getData();

        switch (name){
            case "Load":
                load(type, data);
                break;
            case "Store":
                store(data);
                break;
            case "Add":
                add(type, data);
                break;
            case "Sub":
                sub(type, data);
                break;
            case "BR":
            	exitCode = Immediate(data);
                break;
            case "BRLE":
            	exitCode = (this.N.getValue()==1|this.Z.getValue()==1) ? Immediate(data):0;
                break;
            case "BRLT":
            	exitCode = (this.N.getValue()!=0) ? Immediate(data):0;
                break;
            case "BREQ":
            	exitCode = (this.Z.getValue()!=0) ? Immediate(data):0;
                break;
            case "BRNE":
            	exitCode = (this.N.getValue()==0) ? Immediate(data):0;
                break;
            case "BRGE":
            	exitCode = (this.Z.getValue()==0) ? Immediate(data):0;
                break;
            case "NEGR":
            	negate(type, data);
                break;
            case "ROLX":
            	rotateLeft(type, data);
                break;
            case "RORX":
            	rotateRight(type, data);
                break;
            case "ASLX":
            	arithmeticShiftLeft(type, data);
                break;
            case "ASRX":
            	arithmeticShiftRight(type, data);
                break;       
            case "Stop":
            	exitCode = -1;
                System.out.println("Stop...");
        }
        return exitCode;
    }

	private int Immediate(String data) {
		// TODO Auto-generated method stub
		return (int) Long.parseLong(data);
	}
    
	private void arithmeticShiftRight(int type, String data) {
		int holder = Integer.valueOf(data);
		holder = holder >> 1;
		register.setValue(Long.parseLong(String.valueOf(holder),2));		
	}
	
	private void arithmeticShiftLeft(int type, String data) {
		int holder = Integer.valueOf(data);
		holder = holder << 1;
		register.setValue(Long.parseLong(String.valueOf(holder),2));
	}
	
	private void rotateLeft(int type, String data) {
	String holder = data.substring(1) + data.substring(0, 1);
	register.setValue(Long.parseLong(holder,2));
	}
	
	private void rotateRight(int type, String data) {
	String holder = data.substring(data.length() - 1) + data.substring(0, data.length() - 1);
		
    }
	
    /**
     * Processing negate instructions
     * @param type addressing mode
     * @param data data or address depends on the addressing mode
     */
    public void negate(int type, String data) {
    	String negatedBin;
    	char [] holder = data.toCharArray();
    	//Two Compliment
    	for(int i = 0; i < holder.length;i++) {
    		if (holder[i] == '0') {
    			holder[i] = '1';
    		}
    		else{
    			holder[i] = '0';
    		}
    	}
    	//System.out.println("Inverse binary: " + String.valueOf(holder));
    	
    	//Add 1
    	for(int i = holder.length - 1; i > 0; i--) {
    	   System.out.println(holder[i]);
    		if (holder[i] == '0') {
    			holder[i] = '1';
    			negatedBin = String.valueOf(holder);
    			break;
    		}
    		else {
    			holder[i] = '0';			
    			//System.out.println("Add 1 "+ String.valueOf(holder));
    		}
    	}
    	//set to register
    	negatedBin = holder.toString();
    	register.setValue(Long.parseLong(negatedBin,2));
    }
    
	/**
     * Processing add instructions
     * @param type addressing mode
     * @param data data or address depends on the addressing mode
     */
    public void add(int type, String data) {
        if(type == 0){
            register.setValue(register.getValue() + Long.parseLong(data,2));
            System.out.println("add value = " +  data + " to register");
        } else {
            long address = Long.parseLong(data);
            Long memoryValue = memory.get(address);
            if (memoryValue == null) {
                System.out.println("ERROR -> the address:" + data + "contains nothing.");
            } else {
                register.setValue(register.getValue() + memoryValue);
                System.out.println("add value to register from address: " + data);
            }
        }
    }

    /**
     * Processing subtract instructions
     * @param type addressing mode
     * @param data data or address depends on the addressing mode
     */
    public void sub(int type, String data) {
        if(type == 0){
            register.setValue(register.getValue() - Long.parseLong(data,2));
            System.out.println("subtract value = " + data + " to register");
        } else {
            long address = Long.parseLong(data);
            Long memoryValue = memory.get(address);
            if (memoryValue == null) {
                System.out.println("ERROR -> the address:" + data + "contains nothing.");
            } else {
                register.setValue(register.getValue() - memoryValue);
                System.out.println("subtract value to register from address: " + data);
            }
        }
    }

    /**
     * Processing load instructions
     * @param type addressing mode
     * @param data data or address depends on the addressing mode
     */
    public void load(int type, String data) {
        if(type == 0){
            register.setValue(Long.parseLong(data,2));
            System.out.println("load data = " + data + " to register");
        } else {
            long address = Long.parseLong(data);
            Long memoryValue = memory.get(address);
            if (memoryValue == null) {
                System.out.println("ERROR -> the address:" + data + "contains nothing.");
            } else {
                register.setValue(memoryValue);
                System.out.println("load value to register from address: " + data);
            }
        }
    }

    /**
     * Processing store instructions, store the data in the corresponding address.
     * @param data address
     */
    public void store(String data){
        memory.put(Long.parseLong(data), register.getValue());
        System.out.println("store data to address: " + data);
    }
}
