# SongRecommendationEngine

## Data used

Spotify data from Kaggle containing information on about 114,000 songs and
their musical properties such as danceabiliy, loudness, tempo, and 17 other scores.

This file was too large to include in this repository. I sampled 25,000 lines of data
out of the 114,000.


## Graph Database

Some of the Spotify data was modeled as a graph database. Nodes are songs and their 
properties. Edges connect similar songs, where I defined similarity based on danceability, 
speechiness, and song genre. I calculated the Euclidean distances between all songs in terms
of their danceability and speechiness. I then calculated whether the genres of all the songs 
were the same or not. If they were different, this category was given a 1, if they were the 
same, this category was given a 0. I then added the 2 distances and the genre calculation 
together to end up with a “difference_score”. The higher the score, the less similar the songs 
are. And vice versa, the lower the score, the more similar the songs are. Finally my algorithm 
only included relationships with a difference_score of less than 0.002.


The total size of my graph database was 7034 nodes and 10120 edges.


## How it works

This program is created as a general-purpose song recommendation system that could recommend 
songs based on liking other songs in the database. I then tested my approach by recommending
songs liking the band 'The Strokes'. 
