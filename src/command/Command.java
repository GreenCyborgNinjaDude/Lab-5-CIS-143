/**
 * This program is meant simulate PEP8. 
 * This is where the the instruction and operand is specify
 * and send to other part of the CPU
 * The result
 * @author KietTruong, Chris Ding,  Muhammad Shahid
 * @version 10/21/2020
 */
package command;

/**
 * Process input string conversion to instruction information
 */
public class Command {
    private final String name;
    private final int type;
    private final String data;
    private final String instruction;
    private final String operand;
    private String pc;

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

    public String getPc(){
        return this.pc;
    }

    public void setPc(String pc){
        this.pc = pc;
    }
}
