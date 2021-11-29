package com.kpkym.web.compiler;


import java.util.Map;

public class Compile{

    DynamicCompiler dynamicCompiler = new DynamicCompiler(this.getClass().getClassLoader());

    public synchronized CompileResult compile(String javaSource) {
        String package_ = "package ";
        String public_class_ = "public class ";

        String packageName = javaSource.substring(javaSource.indexOf(package_) + package_.length(), javaSource.indexOf(";"));

        String className = javaSource.substring(javaSource.indexOf(public_class_) + public_class_.length(), javaSource.indexOf("{")).trim();

        String fullName = packageName + "." + className;

        dynamicCompiler.addSource(fullName, javaSource);

        Map<String, byte[]> byteCodes = dynamicCompiler.buildByteCodes();
        byte[] bytes = byteCodes.get(fullName);

        CompileResult result = new CompileResult();
        result.setClassName(fullName);
        result.setCompiledClassByteArray(bytes);

        return result;
    }
}
