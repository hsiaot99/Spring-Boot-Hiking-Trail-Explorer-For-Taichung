# HikingTrail

This project is a Spring Boot command-line application designed to manage and interact with hiking trail data, specifically for the Taichung area.

## Building and Running the Application

To build the application, run the following command:

```bash
./mvnw compile
```

Then, execute the application using the following command:

```bash
./mvnw exec:java -Dexec.mainClass="com.scan.controller.MainApplication"
```

## Dependencies

The project uses Maven for dependency management. See the `pom.xml` file for a complete list of dependencies.

## Usage

Once the application is running, you will see a menu with the following options:

```
<-------------Menu------------->
0. 離開程式
1. Get HiKing Trail By 編號
2. Add Json HiKing Trail
3. Add HiKing Trail
4. Update HiKing Trail
5. Delete HiKing Trail
6. Get All HiKing Trails
7. Export Json
8. Export Csv
9. Export Xml
10. Delete All HiKing Trail
請輸入要使用的功能：
```

Enter the number corresponding to the desired function and follow the prompts.
