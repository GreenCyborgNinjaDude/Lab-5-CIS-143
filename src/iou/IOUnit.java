/**
 * This program is meant simulate PEP8. 
 * This is where the CPU is displaying the result of the operation
 * From memory (for now it can print letters).
 * @author KietTruong, Chris Ding,  Muhammad Shahid
 * @version 10/21/2020
 */
package iou;

import command.Command;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Using I/O unit to process I/O instructions
 */

public class IOUnit {

    private String output = "";
    private final HashMap<Long,Long> memory;
    public IOUnit(HashMap<Long,Long> memory) {
        this.memory = memory;
    }

   /**
     * Choose the right method to execute
     * @param command Instruction information
     */
    public void execute(Command command) {
        String name = command.getName();
        int type = command.getType();
        String data = command.getData();
        switch (name){
            case "In":
                input(data);
                return;
            case "Out":
                output(type, data);
                return;
            default:
        }
    }

    /**
     * Processing input instructions
     * @param data the address of keyboard input.
     */
    public void input(String data) {
        System.out.println("please enter an ASCII character in Hex:");
        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();
        memory.put(Long.parseLong(data), Long.parseLong(word,16));
        System.out.println("input data to address: " + data);
    }

    /**
     * Processing out instructions
     * @param type addressing mode
     * @param data data or address depends on the addressing mode
     */
    public void output(int type, String data) {
        if (type == 0) {
        	  System.out.println("output from data: " + (char) Long.parseLong(data,2));
        	  output = output + (char) Long.parseLong(data,2);
        } else {
            long address = Long.parseLong(data);
            long memoryValue = memory.get(address);
            System.out.println("out print data from address: " + address + ", data = " + (char) memoryValue);
            output = output + (char) memoryValue;
        }
    }

    public String getOutput() {
        return output;
    }
}
