package edu.stu.cipher.model;

public abstract class CipherBase {
    public abstract byte[] encrypt(byte[] data);
    public abstract byte[] decrypt(byte[] data); 
}
