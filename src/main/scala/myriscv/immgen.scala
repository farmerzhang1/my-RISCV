package riscv

import chisel3._
import chisel3.util._
import chisel3.experimental._

// immediate generator

/*
Signal Name	Direction	Bit Width	Description
inst	Input	32	The instruction being executed
ImmSel	Input	3	Value determining how to reconstruct the immediate
imm	Output	32	Value of the immediate in the instruction
*/
object IMMs {
    val I_IMM = 0.U(3.W),
    val S_IMM = 1.U(3.W),
    val B_IMM = 2.U(3.W),
    val U_IMM = 3.U(3.W),
    val J_IMM = 4.U(3.W)
}
class ImmGenIO extends Bundle {
    val inst = Input (UInt (32.W))
    val immsel = Input (UInt (3.W))
    val imm = Output (UInt (32.W))
}

class ImmGen extends Module {
    val io = IO (ImmGenIO)
    val I_imm = io.inst (31, 20)
    val S_imm = Cat (io.inst (31, 25), io.inst (11, 7))
    val B_imm = Cat (io.inst (31), io.inst (7), io.inst (30, 25), io.inst (11, 8), 0.U(1.W))
    val U_imm = Cat (io.inst (31, 12), 0.U(1.W))
    val J_imm = Cat (io.inst (31), io.inst (19, 12), io.inst (20), io.inst (30, 21), 0.U(1.W))
    io.imm := MuxLookup(io.immsel, 0.U,
        Seq (
            I_IMM -> I_imm,
            S_IMM -> S_imm,
            B_IMM -> B_imm,
            U_IMM -> U_imm,
            J_IMM -> J_imm
        )).asUInt
}