
package cpu.cu;

import cpu.alu.ALU;
import iou.IOUnit;
import register.Register;

import java.util.ArrayList;
import java.util.HashMap;

import command.Command;

/**
 * control unit
 */
public class ControlUnit {
    private final ALU alu;
//    private final ProgramCounter pc;
//	public int stop;
    private Register pc;
    public Command current;
    private ArrayList<Integer> code_; // pc -> code
    private int length;
    
	private IOUnit ioUnit;
	public int stop;

    public ControlUnit(HashMap<Long,Long> memory){
    	this.stop = 0;
        this.alu = new ALU(memory); // this will cause warning: current ALU = ALU + memory
	    this.ioUnit = new IOUnit(memory);
	    this.code_ = new ArrayList<Integer>();
	    this.current = new Command();
	    this.pc = new Register();
    }
    
    public void setCode(String inputs) {
    	this.code_.clear();
        for(int i=0;i<inputs.length();i+=2){
        	code_.add(Integer.decode("0x"+inputs.substring(i, i+2)));
        }
    }

    public ALU getAlu() {
        return alu;
    }
    
    public IOUnit getIO() {
        return ioUnit;
    }

    public long getPc() {
        return this.pc.getValue();
    }
    
    void setR(Register k, long value) {
    	k.setValue(value);
    }

	public void clearR() {
		// TODO Auto-generated method stub
		this.setR(pc, 0);
	}

	public void fetch() {
		// TODO Auto-generated method stub
		this.length = this.current.fetch(this.code_.get((int) this.getPc()));
	}

	public void decode() {
		// TODO Auto-generated method stub
		Integer[] op = new Integer[this.length];
		for(int i=0; i<this.length; i++) {
			op[i] = this.code_.get((int) this.getPc()+i);
		}
		this.current.decode(op);
		this.setR(pc, this.getPc() + this.length);
	}

	public void execute() {
        if(this.current.getName().equals("In") || this.current.getName().equals("Out")){
            ioUnit.execute(this.current);
        }else {
            int code = alu.execute(this.current);
            if(code > 0) {//jump
            	this.setR(this.pc, code);
            }
            else if(code < 0) {
            	this.stop = 1;
            }
        }
	}
}
