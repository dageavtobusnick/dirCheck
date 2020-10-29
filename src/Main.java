import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        BufferedReader console=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите название директории");
        String fileName= null;
        try {
            fileName = console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = Path.of(fileName);
        try {
            Path finalPath = path;
            String logName=String.format("../dirCheck/dirCheck by %s .txt", new SimpleDateFormat("yyyy.MM.dd 'at' HH mm ss").format(new Date()));
            Files.createFile(Path.of(logName));
            FileWriter writer=new FileWriter(logName);
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    int count = dir.getNameCount() - finalPath.getNameCount() + 1;
                    if(dir.toString().compareTo("C:\\")==0)
                    {
                        System.out.println("C:");
                        writer.write("C:");
                        writer.write("\r\n");
                        return FileVisitResult.CONTINUE;
                    }
                    count += dir.getFileName().toString().length();
                    String text = String.format("%" + count + "s", dir.getFileName());
                    text = text.replaceAll("[\\s]", "-");
                    System.out.println(text);
                    writer.write(text);
                    writer.write("\r\n");
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
