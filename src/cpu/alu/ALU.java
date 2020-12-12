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
    private final HashMap<Long,Long> memory;

    public ALU(HashMap<Long,Long> memory){
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
            case "Load":
                load(type,data);
                return;
            case "Store":
                store(data);
                return;
            case "Add":
                add(type, data);
                return;
            case "Sub":
                sub(type, data);
                return;
            case "Stop":
                System.out.println("Stop...");
        }
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
