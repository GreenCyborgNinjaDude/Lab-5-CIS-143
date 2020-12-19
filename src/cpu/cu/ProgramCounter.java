/**
 * This program is meant simulate PEP8. 
 * This is where the CPU fetch the next instruction 
 * From memory.
 * @author KietTruong, Chris Ding,  Muhammad Shahid
 * @version 10/21/2020
 */
package cpu.cu;

import command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * PC  contains the address of the next instruction to be executed.
 */
public class ProgramCounter {
    private static final int length = 4;
    private long pc;

    public String getBinaryString(String inputs){
        StringBuilder binaryString = new StringBuilder();
        for(int i=0;i<inputs.length();i+=2){
            String temp = inputs.substring(i,i+2);
            String binaryPart = Long.toBinaryString(Long.parseLong(temp,16));
            while(binaryPart.length()<2*length){
                binaryPart = "0" + binaryPart;
            }
            binaryString.append(binaryPart);
        }
        System.out.println("The input hex transfer to binary: " + binaryString);
        System.out.println("------------------------------------");
        return binaryString.toString();
    }

    public List<Command> getCommand(String binaryString){
//        use list to store the commands.
        List<Command> commandList = new ArrayList<>();
//        Assume the start position is 0
        int start = 0;
        pc = 0;
        while(start<binaryString.length()){
//            PC address
            String address = Long.toBinaryString(pc);
            while(address.length()<4*length){
                address = "0" + address;
            }
//            the PC is incremented by 3
            if(!"0000".equals(binaryString.substring(start,start+4))){
                Command command = new Command(binaryString.substring(start,start+6*length));
//                command.setPc(address);
                commandList.add(command);
                start+=6*length;
                pc+=3;
            } else {
                Command command = new Command(binaryString.substring(start,start+2*length));
//                command.setPc(address);
                commandList.add(command);
                start+=2*length;
                pc+=1;
            }
        }
        return commandList;
    }
}
