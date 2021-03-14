package riscv

import chisel3._
import chisel3.util._
import chisel3.experimental._

/*
Clock	1	Input providing the clock.
RegWEn	1	Determines whether data is written to the register file on the next rising edge of the clock.
Read Register 1 (rs1)	5	Determines which register’s value is sent to the Read Data 1 output, see below.
Read Register 2 (rs2)	5	Determines which register’s value is sent to the Read Data 2 output, see below.
Write Register (rd)	5	Determines which register to set to the value of Write Data on the next rising edge of the clock, assuming that RegWEn is a 1.
Write Data (wb)	32	Determines what data to write to the register identified by the Write Register input on the next rising edge of the clock, assuming that RegWEn is 1.
*/

class RegFileIO extends Bundle {
    val RegWEn = Input (Bool())
    val rs1 = Input (UInt (5.W))
    val rs2 = Input (UInt (5.W))
    val rd = Input (UInt (5.W))
    val wb = Input (UInt (32.W))
    val out1 = Output (UInt (32.W))
    val out2 = Output (UInt (32.W))
}

class RegFile extends Module {
    val io = IO (new RegFileIO)
    val regs = Mem (32, UInt (32.W))
    io.out1 := regs(io.rs1)
    io.out2 := regs(io.rs2)
    regs(io.rd) := Mux (io.RegWEn && (io.rd =/= 0.U), io.wb, 0.U)
}