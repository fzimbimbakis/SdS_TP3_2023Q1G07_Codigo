package FloatImpl.utils;

import FloatImpl.models.ParticleFloat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OvitoFloat {

    private static final String RESOURCES_PATH = "./src/main/resources/";

    public static String createFile(String name, String extension) {
        try {
            int count = 1;
            String file_path = RESOURCES_PATH + name + count + "." + extension;
            File file = new File(file_path);
            while (!file.createNewFile()) {
                count += 1;
                file_path = RESOURCES_PATH + name + count + "." + extension;
                file = new File(RESOURCES_PATH + name + count + "." + extension);
            }
            System.out.println("File created: " + file_path);
            return file_path;
        } catch (IOException e) {
            throw new RuntimeException("Error writing random particles to file (" + name + ") in ParticleUtils.createFile.");
        }
    }

    public static void writeParticlesToFileXyz(String filePath, Float time, List<ParticleFloat> particleFloats, List<ParticleFloat> fixed, ParticleFloat A, ParticleFloat B) {
        try {
            FileWriter myWriter = new FileWriter(filePath, true);
            myWriter.write((fixed.size() + particleFloats.size()) + "\n" + time + " " + (A != null ? A.getNumber() : "---") + " " + (B != null ? B.getNumber() : "---") + "\n");
            for (ParticleFloat particleFloat : fixed)
                myWriter.write(particleFloat.toString() + "\n");
            for (ParticleFloat particleFloat : particleFloats)
                myWriter.write(particleFloat.toString() + "\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing particles to file (" + filePath + ") in ParticlesUtils.writeParticlesToFile.");
        }
    }

    public static <T> void writeListToFIle(List<T> list, String file_path, boolean end) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString());
                if (!end || i < list.size() - 1) {
                    sb.append("\n");
                }
            }
            FileWriter myWriter = new FileWriter(file_path, true);
            myWriter.write(sb.toString());
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void writeToFIle(T o, String file_path) {
        try {
            FileWriter myWriter = new FileWriter(file_path, true);
            myWriter.write(o.toString());
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
