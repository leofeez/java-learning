#JVM 调优

## 第一步，了解生产环境下的垃圾回收器组合

JVM参数分类：
- 标准：- 开头，所有的Hotspot都支持，如java -version
- 非标准：-X，特定版本HotSpot支持
- 不稳定：-XX开头，下个版本可能会取消

-XX:+PrintCommandLineFlag
-XX:+PrintFlagsFinal
-XX:+PrintFlagsInitial