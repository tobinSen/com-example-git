package com.example.spring.layui.cli;

import org.apache.commons.cli.*;

import java.security.SecureRandom;

/**
 * 作用，是解析命令行的的参数，接受作用，不是执行作用
 * <p>
 * CommandLineParser：接口，命令行参数解析器，根据 Options、String[] 解析出 CommandLine 结果对象。
 * DefaultParser：CommandLineParser 的实现类，CLI v1.3 版本后，这是唯一的命令行参数解析器实现类。
 * CommandLine：由 CommandLineParser 解析产生的结果对象，我们需要从该对象中获取当前的命令行参数。
 * HelpFormatter：命令行帮助信息格式化工具，使用其 printHelp() 方法可以打印出格式良好的帮助信息。
 * Option：代表一个命令行选项，是一个命令行选项的抽象表示，如选项名称，长名称，是否需要选项参数。
 * Option.Builder：Option 类的构建器，静态内部类，推荐使用构建器构建 Option 对象，而非构造函数。
 * Options：存储 Option 对象的容器，表示一组选项，是 CommandLineParser 解析器所需参数之一。
 * ParseException：发生任何解析异常时，此异常被抛出，它是下面几个异常类的基类。
 * MissingOptionException：如果某个必需的选项未提供，则此异常被抛出。
 * MissingArgumentException：如果某个选项缺少参数，则此异常被抛出。
 * UnrecognizedOptionException：如果遇到未知选项，则此异常被抛出。
 * <p>
 * 命令行参数解析分为 3 个阶段，分别是 定义、解析、询问 阶段
 */
public class CliDemo {

    public static void main(String[] args) {
        // 创建选项集
        Options options = new Options();
        // 添加选项 -l --level
        options.addOption(Option.builder("l") // 选项名称
                .longOpt("level") // 长选项名
                .hasArg() // 需要参数
                .argName("level") // 参数显示名称
                // 选项的描述、帮助信息
                .desc("randomization level (range: 1,2,3; default: 2)" +
                        "\nlevel=1 => digit [0-9]" +
                        "\nlevel=2 => alpha [a-zA-Z]" +
                        "\nlevel=3 => alnum [0-9a-zA-Z]")
                .build());
        // 添加选项 -n --length
        options.addOption(Option.builder("n") // 选项名称
                .longOpt("length") // 长选项名
                .hasArg() // 需要参数
                .argName("length") // 参数显示名称
                // 选项的描述、帮助信息
                .desc("random string length (range: >=0; default: 20)")
                .build());
        // 添加选项 -h --help
        options.addOption(Option.builder("h") // 选项名称
                .longOpt("help") // 长选项名
                // 选项的描述、帮助信息
                .desc("show this help message and exit program")
                .build());

        // 解析器
        CommandLineParser parser = new DefaultParser();
        // 格式器
        HelpFormatter formatter = new HelpFormatter();
        // 解析结果
        CommandLine result = null;

        try {
            // 尝试解析命令行参数
            result = parser.parse(options, args);
        } catch (ParseException e) {
            // 打印解析异常
            System.err.println(e.getMessage());
            // 打印帮助信息
            formatter.printHelp("RandomGenerator", options, true);
            // 退出程序，退出码为 1
            System.exit(1);
        }

        // 如果存在 -h --help 参数
        if (result.hasOption("h")) {
            formatter.printHelp("RandomGenerator", options, true);
            System.exit(0);
        }

        int level = 2; // default level
        int length = 20; // default length

        // 如果存在 -l --level 参数
        if (result.hasOption("l")) {
            try {
                level = Integer.parseInt(result.getOptionValue("l"));
            } catch (Exception e) {
                System.err.println(e.toString());
                formatter.printHelp("RandomGenerator", options, true);
                System.exit(1);
            }
        }
        // 如果存在 -n --length 参数
        if (result.hasOption("n")) {
            try {
                length = Integer.parseInt(result.getOptionValue("n"));
            } catch (Exception e) {
                System.err.println(e.toString());
                formatter.printHelp("RandomGenerator", options, true);
                System.exit(1);
            }
        }
        // 最后根据参数，执行 random() 方法
        System.out.println(random(level, length));
    }

    // 数字
    private static final String digit = "0123456789";
    // 字母
    private static final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    // 字母 + 数字
    private static final String alnum = digit + alpha;
    // 随机数发生器
    private static final SecureRandom rnd = new SecureRandom();

    // 随机字符串生成器
    public static String random(int level, int length) {
        StringBuilder buf = new StringBuilder(length);
        if (level == 1)
            for (int i = 0; i < length; i++)
                buf.append(digit.charAt(rnd.nextInt(digit.length())));
        else if (level == 2)
            for (int i = 0; i < length; i++)
                buf.append(alpha.charAt(rnd.nextInt(alpha.length())));
        else if (level == 3)
            for (int i = 0; i < length; i++)
                buf.append(alnum.charAt(rnd.nextInt(alnum.length())));
        return buf.toString();
    }

}
