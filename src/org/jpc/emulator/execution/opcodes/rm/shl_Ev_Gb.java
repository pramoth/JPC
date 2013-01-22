package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import static org.jpc.emulator.processor.Processor.*;

public class shl_Ev_Gb extends Executable
{
    final int op1Index;
    final int op2Index;
    final int size;

    public shl_Ev_Gb(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        size = parent.opr_mode;
        op1Index = Processor.getRegIndex(parent.operand[0].toString());
        op2Index = Processor.getRegIndex(parent.operand[1].toString());
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        Reg op2 = cpu.regs[op2Index];
        if (size == 16)
        {
        int shift = op2.get8() & 0x1f;
        if(shift != 0)
        {
            if (shift != 1)
            {
                cpu.of(cpu.of());
                cpu.flagStatus = SZAPC;
            }
            else
                cpu.flagStatus = OSZAPC;
            cpu.flagOp1 = op1.get16();
            cpu.flagOp2 = shift;
            cpu.flagResult = (short)(cpu.flagOp1 << cpu.flagOp2);
            op1.set16((short)cpu.flagResult);
            cpu.flagIns = UCodes.SHL16;
        }
        }
        else if (size == 32)
        {
        int shift = op2.get8() & 0x1f;
        if(shift != 0)
        {
            if (shift != 1)
            {
                cpu.of(cpu.of());
                cpu.flagStatus = SZAPC;
            }
            else
                cpu.flagStatus = OSZAPC;
            cpu.flagOp1 = op1.get32();
            cpu.flagOp2 = shift;
            cpu.flagResult = (cpu.flagOp1 << cpu.flagOp2);
            op1.set32(cpu.flagResult);
            cpu.flagIns = UCodes.SHL32;
        }
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