package riscv

import chisel3._
import chisel3.util._
import chisel3.experimental._
/*
ra	32	Driven with the contents of ra (FOR TESTING)
sp	32	Driven with the contents of sp (FOR TESTING)
t0	32	Driven with the contents of t0 (FOR TESTING)
t1	32	Driven with the contents of t1 (FOR TESTING)
t2	32	Driven with the contents of t2 (FOR TESTING)
s0	32	Driven with the contents of s0 (FOR TESTING)
s1	32	Driven with the contents of s1 (FOR TESTING)
a0	32	Driven with the contents of a0 (FOR TESTING)
tohost	32	Driven with the contents of CSR 0x51E (FOR TESTING)
WRITE_ADDRESS	32	This output is used to select which address to read/write data from in data memory.
WRITE_DATA	32	This output is used to provide write data to data memory.
WRITE_ENABLE	4	This output is used to provide the write enable mask to data memory.
fetch_addr	32	This output is used to select which instruction is presented to the processor on the INSTRUCTION input.
*/
class DatapathIO extends Bundle {
    val ra = Output (UInt (32.W))
    val sp = Output (UInt (32.W))
    val t0 = Output (UInt (32.W))
    val t1 = Output (UInt (32.W))
    val t2 = Output (UInt (32.W))
    val s0 = Output (UInt (32.W))
    val s1 = Output (UInt (32.W))
    val a0 = Output (UInt (32.W))
    val tohost = Output (UInt (32.W))
    val write_addr = Output (UInt (32.W))
    val write_data = Output (UInt (32.W))
    val write_en = Output (Bool())
    val fetch_addr = Output (UInt (32.W))
}

class Datapath extends Module {
    val io = IO (new DatapathIO)
    val alu = new ALU
    val immgen = new ImmGen
    val regfile = new RegFile

}