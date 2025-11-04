import java.io.*;
import java.util.*;

public class CampusGreenZoneFinder {

    // ----- Encapsulated Inner Class -----
    static class Zone {
        // Private fields (Encapsulation)
        private int zoneID;
        private String zoneName;
        private double soilQuality;
        private double sunlightHours;
        private double moistureLevel;
        private double existingGreenCover;

        // Constructor
        public Zone(int zoneID, String zoneName, double soilQuality, double sunlightHours,
                    double moistureLevel, double existingGreenCover) {
            this.zoneID = zoneID;
            this.zoneName = zoneName;
            this.soilQuality = soilQuality;
            this.sunlightHours = sunlightHours;
            this.moistureLevel = moistureLevel;
            this.existingGreenCover = existingGreenCover;
        }

        // Getters (Encapsulation: no setters to prevent direct modification)
        public int getZoneID() { return zoneID; }
        public String getZoneName() { return zoneName; }
        public double getSoilQuality() { return soilQuality; }
        public double getSunlightHours() { return sunlightHours; }
        public double getMoistureLevel() { return moistureLevel; }
        public double getExistingGreenCover() { return existingGreenCover; }

        // Calculate Green Suitability Score (Weighted formula)
        public double getGreenSuitabilityScore() {
            return (soilQuality * 0.4) + (moistureLevel * 0.3) + 
                   (sunlightHours * 0.2) - (existingGreenCover * 0.1);
        }

        @Override
        public String toString() {
            return zoneName + " (Score: " + String.format("%.2f", getGreenSuitabilityScore()) + ")";
        }
    }

    // ----- Main Program -----
    public static void main(String[] args) {
        List<Zone> zones = new ArrayList<>();
        String fileName = "green_zones.csv";

        // Read dataset from CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Zone z = new Zone(
                    Integer.parseInt(data[0].trim()),
                    data[1].trim(),
                    Double.parseDouble(data[2].trim()),
                    Double.parseDouble(data[3].trim()),
                    Double.parseDouble(data[4].trim()),
                    Double.parseDouble(data[5].trim())
                );
                zones.add(z);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Sort zones by descending Green Suitability Score
        zones.sort((z1, z2) -> Double.compare(z2.getGreenSuitabilityScore(), z1.getGreenSuitabilityScore()));

        // Display top recommended zones
        System.out.println("\n🌿 Top Recommended Green Zones for Planting 🌿\n");
        for (int i = 0; i < Math.min(3, zones.size()); i++) {
            System.out.println((i + 1) + ". " + zones.get(i));
        }

        // Write results to output file
        try (PrintWriter pw = new PrintWriter(new FileWriter("recommended_zones.txt"))) {
            pw.println("🌿 Top Recommended Green Zones for Planting 🌿");
            for (int i = 0; i < Math.min(3, zones.size()); i++) {
                pw.println((i + 1) + ". " + zones.get(i));
            }
            System.out.println("\n✅ Results saved to 'recommended_zones.txt'");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        // Display SDG impact
        System.out.println("\n🌍 This project supports SDG 13 – Climate Action by identifying zones suitable for new planting, promoting campus sustainability and carbon reduction.");
    }
}
