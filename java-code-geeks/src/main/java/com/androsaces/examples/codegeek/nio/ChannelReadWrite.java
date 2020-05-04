/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.examples.codegeek.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class ChannelReadWrite {
    private static final Logger log = LoggerFactory.getLogger(ChannelReadWrite.class);

    private static final String FILE_MODE = "rw";
    private static final int DEFAULT_BUFFER_SIZE = 48;
    private final String mFileLocation;
    private final int mBufferSize;
    private final String mMode;

    public ChannelReadWrite(String fileLocation, int bufferSize, String mode) {
        mFileLocation = fileLocation;
        mBufferSize = bufferSize;
        mMode = mode;
    }

    public ChannelReadWrite(String fileLocation, int bufferSize) {
        this(fileLocation, bufferSize, FILE_MODE);
    }

    public ChannelReadWrite(String fileLocation) {
        this(fileLocation, DEFAULT_BUFFER_SIZE);
    }

    public void readFile() {
        try (RandomAccessFile file = new RandomAccessFile(mFileLocation, mMode)) {
            log.info("created file channel and allocating buffer");
            var channel = file.getChannel();
            var buffer = ByteBuffer.allocate(mBufferSize);
            var read = channel.read(buffer);
            while (read != -1) {
                log.info("read {}", read);
                buffer.flip();

                var arrayWriter = new CharArrayWriter(mBufferSize);
                while (buffer.hasRemaining()) {
                    arrayWriter.append((char) buffer.get());
                }

                log.info("{}", arrayWriter.toString());

                arrayWriter.reset();
                buffer.clear();
                read = channel.read(buffer);
            }

        } catch (FileNotFoundException e) {
            log.warn("file {} does not exist", mFileLocation);
        } catch (IOException e) {
            log.info("an error occurred while trying to read the file {}", mFileLocation);
        }
    }

    public static void main(String[] args) {
        ChannelReadWrite channelReadWrite = new ChannelReadWrite("/repos/androsaces/sample-code/java-code-geeks/src/data.txt");
        channelReadWrite.readFile();
    }
}
