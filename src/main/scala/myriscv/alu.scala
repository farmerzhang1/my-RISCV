package riscv

import chisel3._
import chisel3.util._
import chisel3.experimental._

object alu_op {
	val add = 0.U(4.W)
	val and = 1.U(4.W)
	val or = 2.U(4.W)
	val xor = 3.U(4.W)
	val srl = 4.U(4.W)
	val sra = 5.U(4.W)
	val sll = 6.U(4.W)
	val slt = 7.U(4.W)
	val mul = 10.U(4.W)
	val mulhu = 11.U(4.W)
	val sub = 12.U(4.W)
	val bsel = 13.U(4.W)
	val mulh = 14.U(4.W)
}
/*
Input Name	Bit Width	Description
A	32	Data to use for Input A in the ALU operation
B	32	Data to use for Input B in the ALU operation
ALUSel	4	Selects which operation the ALU should perform (see the list of operations with corresponding switch values below)
… and one output:

Output Name	Bit Width	Description
Result	32	Result of the ALU operation*/
class ALUIO extends Bundle {
    val a = Input (UInt (32.W))
    val b = Input (UInt (32.W))
    val op = Input (UInt (4.W))
    val res = Output (UInt (32.W))
}

class ALU extends Module {
    val io = IO (new ALUIO)
    io.res := MuxLookup (io.op, 0.U,
        Seq (
            alu_op.add -> (io.a + io.b),
            alu_op.and -> (io.a & io.b),
            alu_op.or -> (io.a | io.b),
            alu_op.xor -> (io.a ^ io.b),
            alu_op.srl -> (io.a >> io.b),
            alu_op.sra -> (io.a.asSInt >> io.b).asUInt,
            alu_op.sll -> (io.a << io.b),
            alu_op.slt -> (io.a.asSInt < io.b.asSInt),
            alu_op.mul -> (io.a.asSInt * io.b.asSInt).asUInt.apply(31, 0),
            alu_op.mulhu -> (io.a * io.b).apply(63, 32),
            alu_op.sub -> (io.a - io.b),
            alu_op.bsel -> io.b,
            alu_op.mulh -> (io.a.asSInt * io.b.asSInt).asUInt.apply(63, 32)
        )
    )
}