/**
 * This program is meant simulate PEP8. 
 * This is where the the instruction and operand is specify
 * and send to other part of the CPU
 * The result
 * @author KietTruong, Chris Ding,  Muhammad Shahid
 * @version 10/21/2020
 */
package command;

import java.util.HashMap;

/**
 * Process input string conversion to instruction information
 * BR(0x04, 1, 3, BRANCH),
   BRLE(0x06, 1, 3, BRANCH),
   BRLT(0x08, 1, 3, BRANCH),
   BREQ(0x0A, 1, 3, BRANCH),
   BRNE(0x0C, 1, 3, BRANCH),
   BRGE(0x0E, 1, 3, BRANCH),
 */
public class Command {
    private String name;
    private int type;
    private String data;
    private String instruction;
    private String operand;
    private HashMap<Integer, Integer> rules;
    private HashMap<Integer, String> names;
    private HashMap<Integer, Integer> types;

    /**
     * Parsing input string correctly processing instructions
     * @param specifiers input string
     */
    public Command(String specifiers) {
        instruction = specifiers.substring(0, 8);
        operand = specifiers.substring(8);
        data = operand;
        String nameCode = instruction.substring(0,5);
        String typeCode = instruction.substring(5,8);
//        get addressing mode
        type = getType(typeCode);
//        get instruction type
        name = getName(nameCode);
        if ((type == -1 || ("Error").equals(name) || operand.length()!=16)
                && !specifiers.startsWith("0000")) {
            System.out.println("Instruction code is wrong, please check it...");
        }
    }
    
    public Command() {
		this.type = 0;
		this.rules = new HashMap<>();
	    this.names = new HashMap<>();
	    this.types = new HashMap<>();
	    //old command
		this.addrule(0xc0, 3, "Load", 0);
		this.addrule(0xe0, 3, "Store", 0);
		this.addrule(0x70, 3, "Add", 0);
		this.addrule(0x80, 3, "Sub", 0);
		this.addrule(0x48, 3, "In", 0); // 48, 0
		this.addrule(0x49, 3, "In", 1); // 49, 1
		this.addrule(0x50, 3, "Out", 0); // 50, 0
		this.addrule(0x51, 3, "Out", 1); // 51, 1
		this.addrule(0x00, 1, "Stop", 0);
		// add new command below:
		this.addrule(0x04, 3, "BR", 0);
		this.addrule(0x06, 3, "BRLE", 0);
		this.addrule(0x08, 3, "BRLT", 0);
		this.addrule(0x0A, 3, "BREQ", 0);
		this.addrule(0x0C, 3, "BRNE", 0);
		this.addrule(0x0E, 3, "BRGE", 0);
		//Kiet's rule
		this.addrule(0xA1, 3, "NEGR", 0);
		this.addrule(0x20, 3, "ROLX", 0);
		this.addrule(0x22, 3, "RORX", 0);
		this.addrule(0x1C, 3, "ASLX", 0);
		this.addrule(0x1E, 3, "ASRX", 0);
    }
    
    private void addrule(Integer op, Integer len, String string, Integer type) {
		// TODO Auto-generated method stub
    	this.rules.put(op, len);
    	this.names.put(op, string);
    	this.types.put(op, type);
	}

	public int fetch(Integer op) {
		int len = 0;
		try {
			len = this.rules.get(op);
			this.name = this.names.get(op);
			this.type = this.types.get(op);
		}catch(Exception e) {
            System.out.println("Instruction code is wrong, please check it...");
		}
    	return len;
    }
    
    public void decode(Integer[] op) {
    	// set data
    	int size = op.length;
    	this.data = "";
    	for(int i=1; i<size; i++) {
    		String str = Integer.toBinaryString(op[i]);
    		while(str.length() < 8) {
    			str = '0' + str;
    		}
        	this.data = this.data + str;
    	}
    }

    /**
     * get addressing type
     * @param typeCode type code
     * @return addressing type
     */
    private int getType(String typeCode){
        if (typeCode.equals("000")) {
            return 0;
        } else if (typeCode.equals("001")) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * get instruction type
     * @param nameCode type code
     * @return instruction type
     */
    private String getName(String nameCode) {
        switch (nameCode) {
            case "00000":
                return "Stop";
            case "11000":
                return "Load";
            case "11100":
                return "Store";
            case "01110":
                return "Add";
            case "10000":
                return "Sub";
            case "01001":
                return "In";
            case "01010":
                return "Out";
            default:
                return "Error";
        }
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getOperand() {
        return operand;
    }

}
