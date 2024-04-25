package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    static class Track {
        String artist;
        String album;
        String trackName;
        double danceability;
        double speechiness;
        String genre;

        public Track(String artist, String album, String trackName, double danceability, double speechiness, String genre) {
            this.artist = artist;
            this.album = album;
            this.trackName = trackName;
            this.danceability = danceability;
            this.speechiness = speechiness;
            this.genre = genre;
        }
    }

    // eliminates the comma within a value so that the CSV can be split by , later, creates .csv file
    // with only artists, album_name, track_name, danceability, speechiness, track_genre
    public static void noCommaWithin() {
        int firstLines = 40000;
        String inputFile = "/Users/sevinchnoori/Desktop/cs3500ta/dshw5/src/main/java/org/example/spotify.csv";
        String outputFile = "/Users/sevinchnoori/Desktop/cs3500ta/dshw5/src/main/java/org/example/spotify_no_comma.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            while (firstLines > 0) {
                String line = reader.readLine();
                if (line == null) break;
                StringBuilder newline = new StringBuilder();
                boolean quote = false;
                for (char c : line.toCharArray()) {
                    if (c == '\"') {
                        quote = !quote;
                    }
                    if (quote && c == ',') {
                        continue;
                    }
                    newline.append(c);
                }
                String l = String.valueOf(newline);
                String[] columns = l.split(",");
                String newerLine = columns[2] + "," + columns[3] + "," + columns[4] + ","
                        + columns[8] + "," + columns[13] + "," + columns[20];

                writer.write(newerLine + "\n"); // Write the reconstructed line to the output file
                firstLines--; // count decrement
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void similarities() {
        String inputFile = "/Users/sevinchnoori/Desktop/cs3500ta/dshw5/src/main/java/org/example/spotify_no_comma.csv";
        String outputFile = "/Users/sevinchnoori/Desktop/cs3500ta/dshw5/src/main/java/org/example/processedSongs.txt";
        List<Track> tracks = new ArrayList<Track>();

        try (LineNumberReader reader = new LineNumberReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            while (reader.getLineNumber() < 15000 && reader.readLine() != null) {
            }

            writer.write("artist1,album1,track1,artist2,album2,track2,difference_score\n");
                String line;
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split(",");
                    String artist = columns[0];
                    String album = columns[1];
                    String trackName = columns[2];
                    double danceability = Double.parseDouble(columns[3]);
                    double speechiness = Double.parseDouble(columns[4]);
                    String genre = columns[5];

                    Track track = new Track(artist, album, trackName, danceability, speechiness, genre);
                    tracks.add(track);
                }

            for (int i = 0; i < tracks.size(); i++) {
                Track currentTrack = tracks.get(i);
                for (int j = i + 1; j < tracks.size(); j++) {
                        Track otherTrack = tracks.get(j);
                        double danceDistance = Math.abs(currentTrack.danceability - otherTrack.danceability);
                        double speechDistance = Math.abs(currentTrack.speechiness - otherTrack.speechiness);

                        double genreDifferent;
                        if (currentTrack.genre.equals(otherTrack.genre)) {
                            genreDifferent = 0;
                        } else {
                            genreDifferent = 1;
                        }

                        // the lower the difference score, the more similar
                        double difference_score = danceDistance + speechDistance + genreDifferent;

                        if (difference_score < .002) {
                            String lineToWrite = currentTrack.artist + "," + currentTrack.album + "," + currentTrack.trackName +
                                    "," + otherTrack.artist + "," + otherTrack.album + "," + otherTrack.trackName +
                                    "," + difference_score + "\n";
                            writer.write(lineToWrite);
                        }
                }
            }

        } 
         catch(IOException e){
             e.printStackTrace();
        }

    } 
  
    public static void main(String[] args) {
        noCommaWithin();
        similarities();

    }
}
