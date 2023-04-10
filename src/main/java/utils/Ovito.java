package utils;

import models.Particle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Ovito {

    public static File createFile(String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                throw new IllegalStateException("Eliminar archivo " + path + " y correr devuelta.");
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Error writing random particles to file (" + path + ") in ParticleUtils.createFile.");
        }
    }

    public static void writeParticlesToFileXyz(String filePath, Double time, List<Particle> particles, List<Particle> fixed){
        try {
            FileWriter myWriter = new FileWriter(filePath, true);
            myWriter.write((fixed.size() + particles.size()) + "\n" + time + "\n");
            for (Particle particle: fixed)
                myWriter.write(particle.toString() + "\n");
            for (Particle particle: particles)
                myWriter.write(particle.toString() + "\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing particles to file (" + filePath + ") in ParticlesUtils.writeParticlesToFile.");
        }
    }



}
