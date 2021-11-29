package com.kpkym.web.compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Service {
    public static Object start(String javacode, String method) throws Exception {
        Compile iRemoteCompileService = new Compile();

        CompileResult compileResult = iRemoteCompileService.compile(javacode);
        String className = compileResult.getClassName();
        byte[] compiledClassByteArray = compileResult.getCompiledClassByteArray();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        InputStream inputStream = new ByteArrayInputStream(compiledClassByteArray);
        UploadFileStreamClassLoader myClassLoader = new UploadFileStreamClassLoader(inputStream, className, classloader);
        Class<?> myDebugClass = myClassLoader.loadClass(className);

        Object debugClassInstance = myDebugClass.newInstance();
        return myDebugClass.getMethod(method).invoke(debugClassInstance);
    }
}
