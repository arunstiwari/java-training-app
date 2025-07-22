package com.kcb.demoapp.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

public class GZipServletResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    private GZIPOutputStream gzipStream;
    private PrintWriter printWriter;
    private ServletOutputStream servletOutputStream;
    private HttpServletResponse response;

    public GZipServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.response = response;
        this.gzipStream = new GZIPOutputStream(byteStream);
        this.servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                // Not implemented
            }

            @Override
            public void write(int b) throws IOException {
                gzipStream.write(b);
            }
        };
        this.printWriter = new PrintWriter(new OutputStreamWriter(gzipStream, response.getCharacterEncoding()));
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (printWriter != null) {
            printWriter.flush();
        }
        if (servletOutputStream != null) {
            servletOutputStream.flush();
        }
        gzipStream.flush();
    }

    public void finish() throws IOException {
        if (printWriter != null) {
            printWriter.close();
        }
        if (servletOutputStream != null) {
            servletOutputStream.close();
        }
        gzipStream.finish();
        byte[] gzipData = byteStream.toByteArray();
        response.setContentLength(gzipData.length);
        response.getOutputStream().write(gzipData);
        response.getOutputStream().flush();
    }
}

