package com.cactt4ck.neuronalnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class FileGesture {

    private FileGesture(){}

    public static byte[] read(final File userFile) {
        byte[] finalByteArray = null;

        try (FileInputStream xtraReader = new FileInputStream(userFile)) {
            FileChannel readerChannel = xtraReader.getChannel();
            int size = (int)readerChannel.size();
            ByteBuffer bBuff = ByteBuffer.allocate(size);
            readerChannel.read(bBuff);
            bBuff.flip();
            finalByteArray = bBuff.array();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalByteArray;
    }

    public static void write(final File userFile, final byte[] textBytes) {
        try (FileOutputStream xtraWriter = new FileOutputStream(userFile)) {
            FileChannel writerChannel = xtraWriter.getChannel();
            ByteBuffer bBuff = ByteBuffer.wrap(textBytes);
            writerChannel.write(bBuff);
            bBuff.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
