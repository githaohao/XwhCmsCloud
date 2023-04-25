package com.xwh.core.utils;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


@Slf4j
public class FindClassesByPackage {
    /**
     * 扫描包获取所有的接口, 接口介绍依赖于swagger
     *
     * @param pack    扫描的包路径例: com.xwh
     * @param service 服务访问路径例: user
     * @return
     */
    public static String findApiByPackage(String pack, String service, String servicveDesc) {
        List<String> list = new ArrayList<>();
        // 获取包的名字 并进行替换
        try (Stream<Class<?>> stream = FindClassesByPackage.getClasses(pack).stream()) {
            stream.filter(clazz -> clazz.getSimpleName().contains("Controller"))
                    .flatMap(e -> Arrays.stream(e.getMethods()))
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class)
                            || method.isAnnotationPresent(PostMapping.class)
                            || method.isAnnotationPresent(GetMapping.class)
                            || method.isAnnotationPresent(PutMapping.class)
                            || method.isAnnotationPresent(DeleteMapping.class))
                    .forEach(method -> {
                        JSONObject map = new JSONObject();
                        Operation[] apiOperation = method.getDeclaredAnnotationsByType(Operation.class);
                        for (Operation annotation : apiOperation) {
                            map.put("description", annotation.summary());
                        }

                        RequestMapping requestMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
                        Tag tag = method.getDeclaringClass().getAnnotation(Tag.class);
                        StringBuilder path = new StringBuilder("/" + service);
                        appendPath(path, requestMapping.value()[0]);

                        map.put("controllerDescription", tag.name());
                        map.put("service", service);
                        map.put("serviceDesc", servicveDesc);
                        map.put("controller", requestMapping.value()[0].replace("/", ""));
                        if (method.isAnnotationPresent(PostMapping.class)) {
                            PostMapping annotation = method.getAnnotation(PostMapping.class);
                            map.put("type", "post");
                            appendPath(path, annotation.value());
                        } else if (method.isAnnotationPresent(GetMapping.class)) {
                            GetMapping annotation = method.getAnnotation(GetMapping.class);
                            map.put("type", "get");
                            appendPath(path, annotation.value());
                        } else if (method.isAnnotationPresent(PutMapping.class)) {
                            PutMapping annotation = method.getAnnotation(PutMapping.class);
                            map.put("type", "put");
                            appendPath(path, annotation.value());
                        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                            DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
                            map.put("type", "delete");
                            appendPath(path, annotation.value());
                        }

                        Pattern p = Pattern.compile("\\{[a-zA-Z0-9]+\\}");
                        Matcher m = p.matcher(path);
                        while (m.find()) {
                            String tmplStr = m.group();
                            path = new StringBuilder(path.toString().replace(tmplStr, "%"));
                        }
                        map.put("path", path);
                        list.add(map.toString());
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(list.toString());
        return list.toString();
    }

    /**
     * 拼接路径
     *
     * @param path       路径
     * @param extensions 扩展
     */
    private static void appendPath(StringBuilder path, String... extensions) {
        for (String extension : extensions) {
            if (!extension.startsWith("/")) {
                extension = "/" + extension;
            }
            path.append(extension);
        }
    }

    /**
     * 获取指定包下所有的类，包括子孙包中的类
     *
     * @param packageName 包名
     * @return 所有的类
     * @throws IOException 获取包中的类时可能会抛出IOException异常
     */
    public static Set<Class<?>> getClasses(String packageName) throws IOException {
        // 存储所有类的集合
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 是否递归处理子孙包中的类
        boolean recursive = true;
        // 将包名转换为目录名
        String packageDirName = packageName.replace('.', '/');
        // 获取指定包下的所有资源（文件或者文件夹）
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        // 循环处理每个资源
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            // 获取资源所采用的协议
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                // 如果是以文件形式保存的，则直接处理该目录
                System.err.println("file类型的扫描");
                // 获取包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                // 扫描该目录下所有的class文件，加入到集合中
                findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
            } else if ("jar".equals(protocol)) {
                // 如果是以jar包的形式保存的，则需要从jar包中获取所有的类
                System.err.println("jar类型的扫描");
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                // 枚举jar包中所有的类文件
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    // 获取jar包中的一个实体（文件或者目录）
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) == '/') {
                        // 如果是以“/”开头，则去掉“/”
                        name = name.substring(1);
                    }
                    // 如果该实体不是在指定的包下，则忽略
                    if (!name.startsWith(packageDirName)) {
                        continue;
                    }
                    int idx = name.lastIndexOf('/');
                    // 如果实体本身是一个子目录，则更新当前的包名
                    if (idx != -1) {
                        packageName = name.substring(0, idx).replace('/', '.');
                    }
                    // 如果实体是一个类文件，则加入到集合中
                    if (name.endsWith(".class") && !entry.isDirectory()) {
                        String className = name.substring(idx + 1, name.length() - 6);
                        try {
                            classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return classes;
    }

    /**
     * 通过文件系统获取包下的所有类
     *
     * @param packageName 包名
     * @param packagePath 包路径
     * @param recursive   是否递归
     * @param classes     存储类的集合
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录，建立一个File对象
        File dir = new File(packagePath);
        // 如果不存在或者不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 获取目录下的所有文件和子目录，使用lambda表达式实现过滤条件
        File[] dirFiles = dir.listFiles((file) -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        // 循环处理每个文件
        for (File file : dirFiles) {
            // 如果是目录，则继续递归处理该目录
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是类文件，则加入到集合中
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

