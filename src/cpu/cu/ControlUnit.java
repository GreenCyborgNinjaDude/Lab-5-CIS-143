
package cpu.cu;

import cpu.alu.ALU;
import java.util.HashMap;

/**
 * control unit
 */
public class ControlUnit {
    private final ALU alu;
    private final ProgramCounter pc;

    public ControlUnit(HashMap<Long,Long> memory){
        this.alu = new ALU(memory);
        this.pc = new ProgramCounter();
    }

    public ALU getAlu() {
        return alu;
    }

    public ProgramCounter getPc() {
        return pc;
    }
}
