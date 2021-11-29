package com.kpkym.web.compiler;


import lombok.Data;

@Data
public class CompileResult {
    private String className;

    private byte[] compiledClassByteArray;
}
