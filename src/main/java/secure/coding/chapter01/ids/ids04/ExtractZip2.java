package secure.coding.chapter01.ids.ids04;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractZip2 {

    private static final int BUFFER = 512;
    private static final long TOOBIG = 0x6400000; // 100MB;

    public final void unzip(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        try {
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("Extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];
                // Write the files to the disk, but only if the file is not too big
                if (entry.getSize() > TOOBIG) {
                    throw new IllegalStateException("File to be unzipped is huge.");
                }
                if (entry.getSize() == -1) {
                    throw new IllegalStateException("File to be unzipped might behuge.");
                }
                // Write the files to the disk
                FileOutputStream fos = new FileOutputStream(entry.getName());
                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
                zis.closeEntry();
            }

        } finally {
            zis.close();
        }
    }
}
