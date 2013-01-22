package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import static org.jpc.emulator.processor.Processor.*;

public class bt_Ev_Ib extends Executable
{
    final int op1Index;
    final int immb;
    final int size;

    public bt_Ev_Ib(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        size = parent.opr_mode;
        op1Index = Processor.getRegIndex(parent.operand[0].toString());
        immb = (byte)parent.operand[1].lval;
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        if (size == 16)
        {
        cpu.flagStatus &= NCF;
            cpu.cf = ((op1.get16() & (1 << (immb % 16))) != 0);
            cpu.flagOp1 = 0;
            cpu.flagOp2 = 0;
            cpu.flagResult = 0;
            cpu.flagIns = UCodes.ADD16;
        }
        else if (size == 32)
        {
        cpu.flagStatus &= NCF;
            cpu.cf = ((op1.get32() & (1 << (immb % 32))) != 0);
            cpu.flagOp1 = 0;
            cpu.flagOp2 = 0;
            cpu.flagResult = 0;
            cpu.flagIns = UCodes.ADD32;
        }        else throw new IllegalStateException("Unknown size "+size);
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}